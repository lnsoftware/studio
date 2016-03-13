package com.imxiaomai.convenience.store.scan.util;

import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class AudioPlayer {

	private final static String TAG = "AudioPlayer";

	public final static int STATE_MSG_ID = 0x0010;

	private Handler mHandler;

	private AudioParam mAudioParam; // 音频参数

	private byte[] mData; // 音频数据

	private AudioTrack mAudioTrack; // AudioTrack对象

	private boolean isInit = false; // 播放源是否就绪

	private PlayAudioThread mPlayAudioThread; // 播放线程

	public AudioPlayer(Handler handler) {}

	public AudioPlayer(Handler handler, AudioParam audioParam) {
		mHandler = handler;
		setAudioParam(audioParam);
	}

	/*
	 * 设置音频参数
	 */
	public void setAudioParam(AudioParam audioParam) {
		mAudioParam = audioParam;
	}

	/*
	 * 设置音频源
	 */
	public void setDataSource(byte[] data) {
		mData = data;
	}

	/*
	 * 就绪播放源
	 */
	public boolean init() {
		if (isInit == true) {
			return true;
		}

		if (mAudioParam == null) {
			return false;
		}

		try {
			setPlayState(PlayState.MPS_PREPARE);
			Log.d(TAG, "init.");
			createAudioTrack();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

		isInit = true;
		return true;
	}

	/*
	 * ����
	 */
	public boolean play() {
		if (isInit == false || mData == null) {
			return false;
		}

		switch (mPlayState) {
		case PlayState.MPS_PREPARE:
			mPlayOffset = 0;
			setPlayState(PlayState.MPS_PLAYING);
			startThread();
			break;
		case PlayState.MPS_PAUSE:
			setPlayState(PlayState.MPS_PLAYING);
			startThread();
			break;
		}

		return true;
	}

	/*
	 * ��ͣ
	 */
	public boolean pause() {

		if (isInit == false) {
			return false;
		}

		if (mPlayState == PlayState.MPS_PLAYING) {
			setPlayState(PlayState.MPS_PAUSE);
			stopThread();
		}

		return true;
	}

	/*
	 * ֹͣ
	 */
	public boolean stop() {
		if (isInit == false) {
			return false;
		}

		setPlayState(PlayState.MPS_PREPARE);
		stopThread();
		Log.d(TAG, "stop.");
		return true;

	}

	/*
	 * �ͷŲ���Դ
	 */
	public boolean release() {
		stop();

		releaseAudioTrack();

		isInit = false;

		setPlayState(PlayState.MPS_UNINIT);
		Log.d(TAG, "release.");
		return true;
	}

	private synchronized void setPlayState(int state) {
		mPlayState = state;

		if (mHandler != null) {
			Message msg = mHandler.obtainMessage(STATE_MSG_ID);
			msg.obj = mPlayState;
			msg.sendToTarget();
		}
	}

	private void createAudioTrack() throws Exception {

		// 获得构建对象的最小缓冲区大小
		int minBufSize = AudioTrack
				.getMinBufferSize(mAudioParam.mFrequency, mAudioParam.mChannel, mAudioParam.mSampBit);

		mPrimePlaySize = minBufSize * 2;
		Log.d(TAG, "mPrimePlaySize = " + mPrimePlaySize);

		//		         STREAM_ALARM：警告声
		//		         STREAM_MUSCI：音乐声，例如music等
		//		         STREAM_RING：铃声
		//		         STREAM_SYSTEM：系统声音
		//		         STREAM_VOCIE_CALL：电话声音
		mAudioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, mAudioParam.mFrequency, mAudioParam.mChannel,
				mAudioParam.mSampBit, minBufSize, AudioTrack.MODE_STREAM);
		//				AudioTrack中有MODE_STATIC和MODE_STREAM两种分类。
		//      		STREAM的意思是由用户在应用程序通过write方式把数据一次一次得写到audiotrack中。
		//				这个和我们在socket中发送数据一样，应用层从某个地方获取数据，例如通过编解码得到PCM数据，然后write到audiotrack。
		//				这种方式的坏处就是总是在JAVA层和Native层交互，效率损失较大。
		//				而STATIC的意思是一开始创建的时候，就把音频数据放到一个固定的buffer，然后直接传给audiotrack，
		//				后续就不用一次次得write了。AudioTrack会自己播放这个buffer中的数据。
		//				这种方法对于铃声等内存占用较小，延时要求较高的声音来说很适用。

	}

	private void releaseAudioTrack() {
		if (mAudioTrack != null) {
			mAudioTrack.stop();
			mAudioTrack.release();
			mAudioTrack = null;
		}

	}

	private void startThread() {
		mAudioTrack.play();
		if (mPlayAudioThread == null) {
			mPlayAudioThread = new PlayAudioThread();
			mPlayAudioThread.start();
			mThreadExitFlag = false;
			Log.d(TAG, "start audio thread[" + mPlayAudioThread.getName() + "]");
		}
	}

	private void stopThread() {
		mAudioTrack.stop();
		mThreadExitFlag = true;
		if (mPlayAudioThread != null) {
			Log.d(TAG, "stop audio thread[" + mPlayAudioThread.getName() + "]");
			mPlayOffset = 0;
			mPlayAudioThread = null;
		}
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private boolean mThreadExitFlag = false; // 线程退出标志

	private int mPrimePlaySize = 0; // 较优播放块大小

	private int mPlayOffset = 0; // 当前播放位置

	private int mPlayState = 0; // 当前播放状态״̬

	/*
	 * 播放音频的线程
	 */
	class PlayAudioThread extends Thread {

		@Override
		public void run() {
			Log.d(TAG, "PlayAudioThread[" + this.getName() + "] run mPlayOffset = " + mPlayOffset);

			
			Log.d(TAG, "mAudioTrack play.");

			while (mThreadExitFlag != true) {
				try {
					mAudioTrack.write(mData, mPlayOffset, mPrimePlaySize);
					if (mThreadExitFlag != true) mPlayOffset += mPrimePlaySize;
				} catch (Exception e) {
					e.printStackTrace();
					AudioPlayer.this.onPlayComplete();
					break;
				}

				if (mPlayOffset >= mData.length) {
					AudioPlayer.this.onPlayComplete();
					break;
				}

			}

			Log.d(TAG, "mAudioTrack stop.");
			mAudioTrack.stop();
		}

	}

	public void onPlayComplete() {
		if (mPlayState != PlayState.MPS_PAUSE) {
			setPlayState(PlayState.MPS_PREPARE);
		}

	}

	public static class AudioParam {

		int mFrequency; // 采样率

		int mChannel; // 声道

		int mSampBit; // 采样精度

	}

	public interface PlayState {

		public static final int MPS_UNINIT = 0; // 未就绪

		public static final int MPS_PREPARE = 1; // 准备就绪(停止)

		public static final int MPS_PLAYING = 2; // 播放中

		public static final int MPS_PAUSE = 3; // 暂停
	}

}
