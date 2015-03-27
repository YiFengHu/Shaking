package com.nptu.dse.shaking.alarm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.nptu.dse.shaking.main.MainActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmAgent {

	private static final String TAG = AlarmAgent.class.getSimpleName();
	
	private Context context = null;
	private AlarmManager alarmManager = null;
	private Calendar calendar = null;
	private PendingIntent pendingIntent = null;
	
	public AlarmAgent(Context context){
		this.context = context;
		alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		calendar = Calendar.getInstance();
	}
	
	public void setAlarm(Date date){
		int requestCode = (int)date.getTime();
		
		calendar.setTime(date);
		Log.d(TAG, "set alarm: " + calendar);

		Intent intent = new Intent(context, AlarmReceiver.class);
		intent.putExtra("msg", "TIME OUT!!!");

		pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
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
	
}
