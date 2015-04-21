package com.nptu.dse.shaking.alarm;

import com.nptu.dse.shaking.activity.AnimationDialogActivity;
import com.nptu.dse.shaking.db.AlarmEntity;
import com.nptu.dse.shaking.main.NotificationManager;
import com.nptu.dse.shaking.manager.PowerManager;
import com.nptu.dse.shaking.manager.RingManager;
import com.nptu.dse.shaking.resource.IntentKey;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = AlarmReceiver.class.getSimpleName();
	
	public static final String BUNDLE_KEY_ALARM_MESSAGE = "BUNDLE_KEY_ALARM_MESSAGE"; 
	private Context context = null;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		this.context = context;
		Log.i(TAG, "onReceive");
		Log.d(TAG, intent.toString());
		String message = intent.getStringExtra(IntentKey.INTENT_KEY_MESSAGE);
		Log.d(TAG, "msg=" + message);
//		Toast.makeText(ApplicationManager.getInstance().getContext(), "alarm:" + message, Toast.LENGTH_SHORT).show();
		
		long triggerMillis = intent.getLongExtra(IntentKey.INTENT_KEY_TIME, 0L);
		long nextTriggerMillis = triggerMillis+AlarmManager.INTERVAL_DAY;
		
		AlarmEntity alarmEntity = new AlarmEntity();
		alarmEntity.setAlarm_hour(intent.getIntExtra(IntentKey.INTENT_KEY_HOUR, 0));
		alarmEntity.setAlarm_minute(intent.getIntExtra(IntentKey.INTENT_KEY_MINUTE, 0));
		alarmEntity.setAlarm_time(nextTriggerMillis);
		alarmEntity.setId((int)System.currentTimeMillis());
		AlarmAgent alrmAgent = AlarmAgent.getInstance(context);
		
		alrmAgent.deleteFromDB(alarmEntity.getId());
		alrmAgent.setAlarm(intent.getIntExtra(IntentKey.INTENT_KEY_HOUR, 0), intent.getIntExtra(IntentKey.INTENT_KEY_MINUTE, 0), true);

		NotificationManager.getInstance().sendNotification(context, message);
		
		goToDialogActivity(message);
		
		RingManager.getInstance().setDefaultRingTone();
		RingManager.getInstance().startRing();
		
		PowerManager.getInstance().lightScreenOn();
	}
	
	private void goToDialogActivity(String message){
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString(BUNDLE_KEY_ALARM_MESSAGE, message);
		intent.putExtras(bundle);
		
		intent.setClass(context, AnimationDialogActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}

}
