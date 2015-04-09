package com.nptu.dse.shaking.manager;

import android.content.Context;
import com.nptu.dse.shaking.main.ApplicationManager;

public class PowerManager {

	private static final String TAG = PowerManager.class.getSimpleName();
	
	private static PowerManager instance = null;
	private android.os.PowerManager powerManager = null;
	private android.os.PowerManager.WakeLock wakeLock= null;
	private Context context = null;
	
	private PowerManager(){
		context = ApplicationManager.getInstance().getContext();
		powerManager = (android.os.PowerManager)context.getSystemService(Context.POWER_SERVICE);
		wakeLock = powerManager.newWakeLock(android.os.PowerManager.PARTIAL_WAKE_LOCK, "");
	}
	
	public static PowerManager getInstance(){
		if(instance == null){
			instance = new PowerManager();
			
		}
		return instance;
	}
	
	public void lightScreenOn(){
		if(!powerManager.isScreenOn()){
			wakeLock.acquire();
		}
	}
	
	public void lightScreenOff(){
		if(powerManager.isScreenOn()){
			wakeLock.release();
		}
	}
}
