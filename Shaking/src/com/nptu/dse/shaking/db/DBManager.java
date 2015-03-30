package com.nptu.dse.shaking.db;

import com.nptu.dse.shaking.main.ApplicationManager;

import android.database.sqlite.SQLiteDatabase;

public class DBManager {

	private static String TAG = DBManager.class.getSimpleName();
	private  static DBManager instance = null;
	private SQLiteDataBaseHelper mSQLiteDataBaseHelper = null;
	
	private DBManager(){
		mSQLiteDataBaseHelper = new SQLiteDataBaseHelper(ApplicationManager.getInstance().getContext());
	}
	
	public static DBManager getInstance(){
		if(instance == null){
			instance = new DBManager();
		}
		return instance;
	}
	
	public SQLiteDatabase getWritableDataBase(){
		SQLiteDatabase db = mSQLiteDataBaseHelper.getWritebleDataBase();
		return db;
	}
}
