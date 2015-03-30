package com.nptu.dse.shaking.main;

import android.content.Context;

public class ApplicationManager {

	private static String TAG = ApplicationManager.class.getSimpleName();
	
	private static ApplicationManager instance = null;
	private Context context = null;

	ApplicationManager(){
		
	}
	
	public static ApplicationManager getInstance(){
		if(instance == null){
			instance = new ApplicationManager();
		}
		return instance;
	}
	
	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}
	
	
}
