package com.imxiaomai.convenience.store.scan.util;

import java.io.IOException;
import java.io.InputStream;

import com.imxiaomai.convenience.store.scan.util.AudioPlayer.AudioParam;

import android.content.Context;
import android.media.AudioFormat;

public class TtsPlayer {
	private static final String TAG = "TtsPlayer";
	private AudioPlayer mAudioPlayer;
	private Context context;

	public TtsPlayer(Context context) {
		this.context = context;
		// 获取音频参数
		AudioParam audioParam = getAudioParam();
		mAudioPlayer = new AudioPlayer(null,audioParam);
		mAudioPlayer.init();
	}

	public void pause() {
		mAudioPlayer.pause();
	}

	public void stop() {
		mAudioPlayer.stop();
	}

	public void realese() {
		mAudioPlayer.release();
	}

	public void playText(String fileName) {
//		L.d(TAG, "play text fileName="+fileName);
		mAudioPlayer.stop();

		// 获取音频数据
		byte[] data = getPCMData(fileName);
		mAudioPlayer.setDataSource(data);

		// 音频源就绪
		mAudioPlayer.play();
	}

	/*
	 * 获得PCM音频数据参数
	 */
	public AudioParam getAudioParam() {
		AudioParam audioParam = new AudioParam();
		audioParam.mFrequency = 9500;//44100;
		audioParam.mChannel = AudioFormat.CHANNEL_CONFIGURATION_STEREO;
		audioParam.mSampBit = AudioFormat.ENCODING_PCM_16BIT;

		return audioParam;
	}

	/*
	 * 获得PCM音频数据
	 */
	public byte[] getPCMData(String fileName) {
		byte[] data_pack = null;
		InputStream inStream = null;
		try {
			inStream = context.getAssets().open(fileName+".pcm");
			if (inStream == null) return null;
			
			long size = inStream.available();
			data_pack = new byte[(int) size];
			inStream.read(data_pack);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (inStream != null) inStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return data_pack;
	}
}
