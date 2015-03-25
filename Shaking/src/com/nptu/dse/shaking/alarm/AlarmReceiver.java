package com.nptu.dse.shaking.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String TAG = AlarmReceiver.class.getSimpleName();
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "onReceive");
		Log.d(TAG, intent.toString());
		String msg = intent.getStringExtra("msg");
		Log.d(TAG, "msg=" + msg);
		Toast.makeText(context, "alarm:" + msg, Toast.LENGTH_SHORT).show();
	}

}
