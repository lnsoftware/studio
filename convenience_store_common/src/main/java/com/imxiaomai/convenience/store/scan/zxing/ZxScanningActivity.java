package com.imxiaomai.convenience.store.scan.zxing;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Vector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.imxiaomai.convenience.store.common.R;
import com.imxiaomai.convenience.store.scan.zxing.camera.CameraManager;
import com.imxiaomai.convenience.store.scan.zxing.decoding.CaptureActivityHandler;
import com.imxiaomai.convenience.store.scan.zxing.decoding.InactivityTimer;
import com.imxiaomai.convenience.store.scan.zxing.image.RGBLuminanceSource;
import com.imxiaomai.convenience.store.scan.zxing.view.ViewfinderView;

public class ZxScanningActivity extends Activity implements Callback,
        View.OnClickListener {

    private static final String TAG = "CaptureActivity";
    public static final String INTENT_SCAN = "intent_scan";
    public static final int INTENT_CODE_SCAN = 100;

    private ViewfinderView viewfinderView;
    private ImageView titleBackIv;

    private CaptureActivityHandler handler;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private static final int REQUEST_CODE = 100;
    private static final int PARSE_BARCODE_SUC = 300;
    private static final int PARSE_BARCODE_FAIL = 303;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zxinglib_scanning);
        CameraManager.init(ZxScanningActivity.this);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);
        init();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.title_back) {
            finish();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mProgress.dismiss();
            switch (msg.what) {
            case PARSE_BARCODE_SUC:
                onResultHandler((String) msg.obj, scanBitmap);
                break;
            case PARSE_BARCODE_FAIL:
                Toast.makeText(ZxScanningActivity.this, (String) msg.obj,
                        Toast.LENGTH_LONG).show();
                break;
            }
        }
    };

    /**
     * 回调处理相册中取图片，暂时不用
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
            case REQUEST_CODE:
                Cursor cursor = getContentResolver().query(data.getData(),
                        null, null, null, null);
                if (cursor.moveToFirst()) {
                    photo_path = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Images.Media.DATA));
                }
                cursor.close();

                mProgress = new ProgressDialog(ZxScanningActivity.this);
                mProgress.setMessage("正在扫描");
                mProgress.setCancelable(false);
                mProgress.show();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Result result = scanningImage(photo_path);
                        if (result != null) {
                            Message m = mHandler.obtainMessage();
                            m.what = PARSE_BARCODE_SUC;
                            m.obj = result.getText();
                            mHandler.sendMessage(m);
                        } else {
                            Message m = mHandler.obtainMessage();
                            m.what = PARSE_BARCODE_FAIL;
                            m.obj = "Scan failed!";
                            mHandler.sendMessage(m);
                        }
                    }
                }).start();

                break;

            }
        }
    }

    /**
     * 扫描二维码图片
     */
    public Result scanningImage(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        Hashtable<DecodeHintType, String> hints = new Hashtable<DecodeHintType, String>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF8");
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        options.inJustDecodeBounds = false;
        int sampleSize = (int) (options.outHeight / (float) 200);
        if (sampleSize <= 0)
            sampleSize = 1;
        options.inSampleSize = sampleSize;
        scanBitmap = BitmapFactory.decodeFile(path, options);
        RGBLuminanceSource source = new RGBLuminanceSource(scanBitmap);
        BinaryBitmap bitmap1 = new BinaryBitmap(new HybridBinarizer(source));
        QRCodeReader reader = new QRCodeReader();
        try {
            return reader.decode(bitmap1, hints);
        } catch (NotFoundException e) {
            e.printStackTrace();
        } catch (ChecksumException e) {
            e.printStackTrace();
        } catch (FormatException e) {
            e.printStackTrace();
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onResume() {
        super.onResume();
        SurfaceView surfaceView = (SurfaceView) findViewById(R.id.scanning_sv);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }

    /**
     * 处理扫描结果
     */
    public void handleDecode(Result obj, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        if (null== obj) {
            return;
        }

        String scanText = obj.getText();
        Log.d(TAG, "CaptureActivity handleDecode scanText = " + scanText);

        Intent intent = new Intent();
        intent.putExtra(INTENT_SCAN, scanText);
        setResult(RESULT_OK, intent);
        finish();;

        playBeepSoundAndVibrate();
        this.finish();
    }

    private void onResultHandler(String resultString, Bitmap bitmap) {
        if (TextUtils.isEmpty(resultString)) {
            Toast.makeText(ZxScanningActivity.this, "Scan failed!",
                    Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }

        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
            int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.zxing_beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

    private void init() {
        viewfinderView = (ViewfinderView) findViewById(R.id.scanning_show);
        titleBackIv = (ImageView) findViewById(R.id.title_back);

        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        titleBackIv.setOnClickListener(this);
    }

}