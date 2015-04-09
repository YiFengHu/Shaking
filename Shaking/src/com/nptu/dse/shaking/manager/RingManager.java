package com.nptu.dse.shaking.manager;

import java.io.IOException;

import com.nptu.dse.shaking.main.ApplicationManager;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

public class RingManager {

	private static final String TAG = RingManager.class.getSimpleName();

	private static RingManager instance = null;

	private Context context = null;
	private Uri soundResourceUri = null;
	private MediaPlayer mediaPlayer = null;
	private RingtoneManager ringToneManager = null;

	private RingManager() {
		context = ApplicationManager.getInstance().getContext();
		mediaPlayer = new MediaPlayer();
		ringToneManager = new RingtoneManager(context);
	}

	public static RingManager getInstance() {
		if (instance == null) {
			instance = new RingManager();
		}
		
		return instance;
	}

	public boolean setDefaultRingTone() {
		soundResourceUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
		try {
			mediaPlayer.setDataSource(context, soundResourceUri);
		} catch (IllegalArgumentException e) {
			Log.e(TAG, "IllegalArgumentException occurred while setRing(): e="+e);
			e.printStackTrace();
			return false;

		} catch (SecurityException e) {
			Log.e(TAG, "SecurityException occurred while setRing(): e="+e);
			e.printStackTrace();
			return false;

		} catch (IllegalStateException e) {
			Log.e(TAG, "IllegalStateException occurred while setRing(): e="+e);
			e.printStackTrace();
			return false;

		} catch (IOException e) {
			Log.e(TAG, "IOException occurred while setRing(): e="+e);
			e.printStackTrace();
			return false;

		}
		return true;
	}

	public void startRing() {
		try {
//			int mNumberOfRingtones = ringToneManager.getCursor().getCount();
//			Uri mRingToneUri = ringToneManager.getRingtoneUri((int) (Math.random() * mNumberOfRingtones));
			mediaPlayer.prepare();
			mediaPlayer.setLooping(true);
			mediaPlayer.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void pauseRing(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.pause();
		}
	}
	
	public void stopRing(){
		if(mediaPlayer!=null && mediaPlayer.isPlaying()){
			mediaPlayer.stop();
		}
	}
}
