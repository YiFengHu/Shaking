package com.nptu.dse.shaking.db;

import android.database.Cursor;

public class AlarmEntity {

	private long id;
	private String message;
	private int alarm_hour;
	private int alarm_minute;
	private long alarm_time;
	
	public void init(Cursor cursor){
		if(cursor!=null){
			if(cursor.getCount()>0){
				id = cursor.getLong(cursor.getColumnIndex(DBRepository.ID));
				message = cursor.getString(cursor.getColumnIndex(DBRepository.MESSAGE));
				alarm_hour = cursor.getInt(cursor.getColumnIndex(DBRepository.ALARM_HOUR));
				alarm_minute = cursor.getInt(cursor.getColumnIndex(DBRepository.ALARM_MINUTE));
				alarm_time = cursor.getInt(cursor.getColumnIndex(DBRepository.ALARM_TIME));
			}
		}
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getAlarm_hour() {
		return alarm_hour;
	}

	public void setAlarm_hour(int alarm_hour) {
		this.alarm_hour = alarm_hour;
	}

	public int getAlarm_minute() {
		return alarm_minute;
	}

	public void setAlarm_minute(int alarm_minute) {
		this.alarm_minute = alarm_minute;
	}
	
	public long getAlarm_time() {
		return alarm_time;
	}

	public void setAlarm_time(long alarm_time) {
		this.alarm_time = alarm_time;
	}
}
