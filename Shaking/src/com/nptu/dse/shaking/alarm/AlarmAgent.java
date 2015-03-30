package com.nptu.dse.shaking.alarm;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.SimpleFormatter;

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
	
	public String intentTag = "message";
	public String intentValue = "Time Out";
	
	private Context context = null;
	private AlarmManager alarmManager = null;
	private Calendar calendar = null;
	private PendingIntent pendingIntent = null;
	private AlarmDataListener alarmDataListener = null;
	private CountDownTimer countDownTimer= null;
	
	public interface AlarmDataListener{
		public void onCountDouwUpdate(String countDown);
		public void onCountDownFinish();
	}
	
	public void setAlarmDataListener(AlarmDataListener alarmDataListener) {
		this.alarmDataListener = alarmDataListener;
	}
	
	public AlarmAgent(Context context){
		this.context = context;
		alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		calendar = Calendar.getInstance();
	}
	
	public void requestCountDown(){
		countDownTimer = new CountDownTimer(30000, 1000) {

			String countDownMessage = "";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");
			
		     public void onTick(long millisUntilFinished) {
		         if(alarmDataListener!=null){
		        	 
		        	 	countDownMessage = simpleDateFormat.format(new Date(millisUntilFinished));
		        	 	alarmDataListener.onCountDouwUpdate(countDownMessage);
		         }
		     }

		     public void onFinish() {
				if (alarmDataListener != null) {
					alarmDataListener.onCountDownFinish();
				}
		     }
		  }.start();
	}
	
	public void cancelCountDown(){
		countDownTimer.cancel();
	}
	
	public void setAlarm(Date date){
		int requestCode = (int)date.getTime();
		
		calendar.setTime(date);
		Log.d(TAG, "set alarm: " + calendar);

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra(intentTag, intentValue);

		pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
	}
	
	public void cancelAlarm(Date date){
		int requestCode = (int)date.getTime();
		
		calendar.setTime(date);
		Log.d(TAG, "set alarm: " + calendar);

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("msg", "TIME OUT!!!");

		pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.cancel(pendingIntent);
	}
	
	public boolean isSetAlarm(){
		boolean flag = false;
		
		String sqlString = DBRepository.getAllDetailDescSyntax();
		
		SQLiteDatabase db = DBManager.getInstance().getWritableDataBase();
		Cursor cursor = db.rawQuery(sqlString, null);
		int count = 0;
		
		if(cursor!=null){
			count = cursor.getCount();
		}
		
		flag = count>0;
		
		return flag;
	}
	
}
