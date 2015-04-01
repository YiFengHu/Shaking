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
		public void onCountDouwUpdate(String countDown);

		public void onCountDownFinish();
	}

	public void setAlarmDataListener(AlarmDataListener alarmDataListener) {
		this.alarmDataListener = alarmDataListener;
	}

	public AlarmAgent(Context context) {
		this.context = context;
		if(isAlarmExist()){
			mAlarmList = getAlarms();
		}
		alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		calendar = Calendar.getInstance();
	}

	public void requestCountDown(long targetTime) {
		
		long timeLeft = targetTime - System.currentTimeMillis();

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
		countDownTimer.cancel();
	}

	public void setAlarm(final int hoursOfDay, final int minute) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				calendar = Calendar.getInstance();
				calendar.set(Calendar.HOUR_OF_DAY, hoursOfDay);
				calendar.set(Calendar.MINUTE, minute);
				calendar.set(Calendar.SECOND, 00);
				
				Log.d(TAG, "set alarm: hour=" + calendar.get(Calendar.HOUR_OF_DAY)+", minute="+calendar.get(Calendar.MINUTE));

				AlarmEntity alarmEntity = new AlarmEntity();
				alarmEntity.setId(System.currentTimeMillis());
				alarmEntity.setMessage("Time to Shaking");
				alarmEntity.setAlarm_hour(calendar.get(Calendar.HOUR_OF_DAY));
				alarmEntity.setAlarm_minute(calendar.get(Calendar.MINUTE));
				alarmEntity.setAlarm_time(calendar.getTimeInMillis());
				
				insertAlarmToDB(alarmEntity.getId(), alarmEntity.getMessage(), alarmEntity.getAlarm_hour(), alarmEntity.getAlarm_minute(), alarmEntity.getAlarm_time());
				
				PendingIntent pendingIntent = createPendingIntent(context, alarmEntity);
				alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
				mAlarmList = getAlarms();
				
				long nowTime = System.currentTimeMillis();
				long remainTime = 24 * 60 * 60 * 1000;
				AlarmEntity targetEntity = null;

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
				
				requestCountDown(targetEntity.getAlarm_time());				
			}
		}).start();
	}

	public void cancelAlarm(Date date) {
		int requestCode = (int) date.getTime();

		calendar.setTime(date);
		Log.d(TAG, "set alarm: " + calendar);

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("msg", "TIME OUT!!!");

		pendingIntent = PendingIntent.getBroadcast(context, requestCode,
				intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
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
	 
		return PendingIntent.getService(context, (int) alarmEntity.getId(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	private int getAlarmCount(){
		String sqlString = DBRepository.getAllAlarmCountSyntax();

		SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
		Cursor cursor = db.rawQuery(sqlString, null);
		int count = 0;

		if (cursor != null) {
			if(cursor.getCount()>0){
				count = cursor.getInt(0);
			}
		}
		
		return count;
	}
	
	private void insertAlarmToDB(long id, String alarmMessage ,int hoursOfDay, int minute, long time){
		try{
			String sqlSytax = DBRepository.getInsertSyntax(id, alarmMessage, hoursOfDay, minute, time);
		
			SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
			Cursor cursor = db.rawQuery(sqlSytax, null);
		}catch(Exception e){
			e.printStackTrace();
			Log.e(TAG, "Exception while insert data: e="+e);
		}
	}
	
	private ArrayList<AlarmEntity> getAlarms(){
		ArrayList<AlarmEntity> alarmList = new ArrayList<AlarmEntity>();
		
		String sqlSytax = DBRepository.getAllAlarmDataSyntax();
		
		SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
		Cursor cursor = db.rawQuery(sqlSytax, null);

		if (cursor != null) {
			if(cursor.getCount()>0){
				AlarmEntity alarmEntity = new AlarmEntity();
				alarmEntity.init(cursor);
				alarmList.add(alarmEntity);
			}
		}
		
		return alarmList;
	}
}
