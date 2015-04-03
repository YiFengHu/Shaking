package com.nptu.dse.shaking.alarm;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

import com.nptu.dse.shaking.db.AlarmEntity;
import com.nptu.dse.shaking.db.DBManager;
import com.nptu.dse.shaking.db.DBRepository;
import com.nptu.dse.shaking.db.SQLiteDataBaseHelper;
import com.nptu.dse.shaking.main.MainActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CountDownTimer;
import android.util.Log;

public class AlarmAgent {

	private static final String TAG = AlarmAgent.class.getSimpleName();

	private static final String INTENT_KEY_ID = DBRepository.ID; 
	private static final String INTENT_KEY_MESSAGE = DBRepository.MESSAGE; 
	private static final String INTENT_KEY_HOUR = DBRepository.ALARM_HOUR; 
	private static final String INTENT_KEY_MINUTE = DBRepository.ALARM_MINUTE; 
	
	private static final long ONE_DAY_MILLISECONDS = 24*60*60*1000;
	
	public String intentTag = "message";
	public String intentValue = "Time Out";

	private Context context = null;
	private AlarmManager alarmManager = null;
	private Calendar calendar = null;
	private PendingIntent pendingIntent = null;
	private AlarmDataListener alarmDataListener = null;
	private CountDownTimer countDownTimer = null;
	
	private ArrayList<AlarmEntity> mAlarmList = null;

	public interface AlarmDataListener {
		public void onAlarmDataReady(ArrayList<AlarmEntity> mAlarmList);
		public void onCountDouwUpdate(String countDown);
		public void onCountDownFinish();
		public void onCountDownCancel();
	}

	public void setAlarmDataListener(AlarmDataListener alarmDataListener) {
		this.alarmDataListener = alarmDataListener;
	}

	public AlarmAgent(Context context) {
		this.context = context;
		if(isAlarmExist()){
			mAlarmList = getAlarms(mAlarmList);
		}
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		calendar = Calendar.getInstance();
	}

	public void requestAlarmData(){
		if(mAlarmList == null){
			mAlarmList = new ArrayList<AlarmEntity>();
			mAlarmList = getAlarms(mAlarmList);
		}

		if(alarmDataListener!=null){
			alarmDataListener.onAlarmDataReady(mAlarmList);
		}
		
		requestCountDown();
	}
	
	public void requestCountDown(){
		mAlarmList = getAlarms(mAlarmList);
		
		long nowTime = System.currentTimeMillis();
		long remainTime = ONE_DAY_MILLISECONDS;
		AlarmEntity targetEntity = null;

		if(mAlarmList!=null && !mAlarmList.isEmpty()){
			//Target the alarm to count down
			for(int i=0; i<mAlarmList.size(); i++){
				AlarmEntity entity = mAlarmList.get(i);
				
				if(entity.getAlarm_time()>nowTime){
					if(remainTime>(entity.getAlarm_time()-nowTime)){
						targetEntity = entity;
						remainTime = entity.getAlarm_time()-nowTime;
					}
				}
			}
			
			if(targetEntity!=null){
				requestCountDown(targetEntity.getAlarm_time());
			}else{
				if(alarmDataListener!=null){
					alarmDataListener.onCountDouwUpdate("00:00:00");
				}
			}
		}
		
	}
	
	private void requestCountDown(long targetTime) {
		
		long timeLeft = targetTime - System.currentTimeMillis();
		
		if(countDownTimer!=null){
			countDownTimer.cancel();
			countDownTimer = null;
		}
		
		countDownTimer = new CountDownTimer(timeLeft, 1000) {

			public void onTick(long millisUntilFinished) {
				if (alarmDataListener != null) {
					String hms = String
							.format("%02d:%02d:%02d",
									TimeUnit.MILLISECONDS
											.toHours(millisUntilFinished),
									TimeUnit.MILLISECONDS
											.toMinutes(millisUntilFinished)
											- TimeUnit.HOURS
													.toMinutes(TimeUnit.MILLISECONDS
															.toHours(millisUntilFinished)),
									TimeUnit.MILLISECONDS
											.toSeconds(millisUntilFinished)
											- TimeUnit.MINUTES
													.toSeconds(TimeUnit.MILLISECONDS
															.toMinutes(millisUntilFinished)));
					alarmDataListener.onCountDouwUpdate(hms);
				}
			}

			public void onFinish() {
				if (alarmDataListener != null) {
					alarmDataListener.onCountDownFinish();
				}
			}
		}.start();
	}

	public void cancelCountDown() {
		if(countDownTimer!=null){
			countDownTimer.cancel();
		}
		if(alarmDataListener!=null){
			alarmDataListener.onCountDownCancel();
		}
	}

	public void setAlarm(final int hoursOfDay, final int minute) {
		Log.d(TAG, "setAlarm("+hoursOfDay+", "+minute+")");
//		new Thread(new Runnable() {
//			
//			@Override
//			public void run() {
				calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, hoursOfDay);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, 00);
				
				Log.d(TAG, "set alarm: hour=" + calendar.get(Calendar.HOUR_OF_DAY)+", minute="+calendar.get(Calendar.MINUTE));

				AlarmEntity alarmEntity = new AlarmEntity();
				alarmEntity.setId((int)calendar.getTimeInMillis());
				alarmEntity.setMessage("Time to Shaking");
				alarmEntity.setAlarm_hour(calendar.get(Calendar.HOUR_OF_DAY));
				alarmEntity.setAlarm_minute(calendar.get(Calendar.MINUTE));
				alarmEntity.setAlarm_time(calendar.getTimeInMillis());
				
				insertAlarmToDB(alarmEntity.getId(), alarmEntity.getMessage(), alarmEntity.getAlarm_hour(), alarmEntity.getAlarm_minute(), alarmEntity.getAlarm_time());
				
				PendingIntent pendingIntent = createPendingIntent(context, alarmEntity);
				alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), ONE_DAY_MILLISECONDS, pendingIntent);
				
				requestCountDown();
				requestAlarmData();
//			}
//		}).start();
	}

	public void cancelAlarm() {
				
		mAlarmList = getAlarms(mAlarmList);

		for(AlarmEntity alarmEntity:mAlarmList){
			PendingIntent pendingIntent = createPendingIntent(context, alarmEntity);
			alarmManager.cancel(pendingIntent);
			Log.e(TAG, "Delete alarm: message="+alarmEntity.getMessage()+", "+alarmEntity.getAlarm_hour()+":"+alarmEntity.getAlarm_minute());
		}
		
		try{
			String sqlSytax = DBRepository.getDeleteSyntax();
			Log.d(TAG, "delete table data: "+sqlSytax);

			SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
			db.execSQL(sqlSytax);
			db.close();

		}catch(Exception e){
			e.printStackTrace();
			Log.e(TAG, "Exception while delete data: e="+e);
		}
		
		cancelCountDown();
		requestAlarmData();
	}

	public boolean isAlarmExist() {
		boolean flag = false;
		flag = getAlarmCount()>0;
		return flag;
	}
	
	private static PendingIntent createPendingIntent(Context context, AlarmEntity alarmEntity) {
		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(INTENT_KEY_ID, alarmEntity.getId());
		intent.putExtra(INTENT_KEY_MESSAGE, alarmEntity.getMessage());
		intent.putExtra(INTENT_KEY_HOUR, alarmEntity.getAlarm_hour());
		intent.putExtra(INTENT_KEY_MINUTE, alarmEntity.getAlarm_minute());
	 
		return PendingIntent.getService(context, (int)alarmEntity.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	private int getAlarmCount(){
		String sqlString = DBRepository.getAllAlarmCountSyntax();

		SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
		Cursor cursor = db.rawQuery(sqlString, null);
		int count = 0;

		if (cursor != null) {
			if (cursor.getCount() > 0) {
				cursor.moveToFirst();
				do {
					count = cursor.getInt(0);
				} while (cursor.moveToNext());

			}
		}
		db.close();
		
		return count;
	}
	
	private void insertAlarmToDB(int id, String alarmMessage ,int hoursOfDay, int minute, long time){
		try{
			String sqlSytax = DBRepository.getInsertSyntax(id, alarmMessage, hoursOfDay, minute, time);
			Log.d(TAG, "insertAlarmToDB: "+sqlSytax);

			SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
			db.execSQL(sqlSytax);
			db.close();

		}catch(Exception e){
			e.printStackTrace();
			Log.e(TAG, "Exception while insert data: e="+e);
		}
	}
	
	private ArrayList<AlarmEntity> getAlarms(ArrayList<AlarmEntity> alarmList){
		if(alarmList == null){
			alarmList = new ArrayList<AlarmEntity>();
		}
		
		alarmList.clear();
		
		String sqlSytax = DBRepository.getAllAlarmDataSyntax();
		
		SQLiteDatabase db = DBManager.getInstance().getReadableDataBase();
		Cursor cursor = db.rawQuery(sqlSytax, null);

		Log.i(TAG, "cursor.getCount()="+cursor.getCount());
		if (cursor != null) {
			if(cursor.getCount()>0){
				cursor.moveToFirst();
				do{
					AlarmEntity alarmEntity = new AlarmEntity();
					alarmEntity.init(cursor);
					alarmList.add(alarmEntity);
					Log.i(TAG, "alarmList.add("+alarmEntity.getAlarm_hour()+", "+alarmEntity.getAlarm_minute()+")");

				}while(cursor.moveToNext());
				
			}
		}
		db.close();

		Log.i(TAG, "alarmList= "+alarmList);
		return alarmList;
	}
}
