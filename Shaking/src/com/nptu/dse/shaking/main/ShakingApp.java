package com.nptu.dse.shaking.main;

import com.nptu.dse.shaking.db.DBManager;
import com.nptu.dse.shaking.manager.RingManager;

import android.app.Application;

public class ShakingApp extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		
		ApplicationManager.getInstance().setContext(this);
		DBManager.getInstance();
	}

	
}
