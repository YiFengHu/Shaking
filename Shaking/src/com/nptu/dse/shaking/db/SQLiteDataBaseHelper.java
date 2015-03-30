package com.nptu.dse.shaking.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLiteDataBaseHelper extends SQLiteOpenHelper {
	
	private  String TAG = SQLiteDataBaseHelper.class.getSimpleName();
	private  SQLiteDatabase sqlDataBase;
	private static final String DATABASE_NAME = "alarm.db";
	private static final int DATABASE_VERSION = 1;

	public SQLiteDataBaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase dataBase) {
		sqlDataBase = dataBase;
		createDetailTable();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + DBRepository.DATABASE_TABLE);
		onCreate(db);
	}
	
	private void createDetailTable(){
		Log.d(TAG, "Create Table: "+DATABASE_NAME);
		sqlDataBase.execSQL(DBRepository.createDBSyntax());
	}
	
	public SQLiteDatabase getWritebleDataBase(){
		sqlDataBase = getWritableDatabase();
		return sqlDataBase;
	}
	
	public void closeDataBase(){
		this.close();
	}
	
}
