package com.nptu.dse.shaking.activity;

import java.util.List;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.alarm.AlarmReceiver;
import com.nptu.dse.shaking.main.NotificationManager;
import com.nptu.dse.shaking.manager.PowerManager;
import com.nptu.dse.shaking.manager.RingManager;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class DialogActivity extends Activity{

	private static String TAG = DialogActivity.class.getSimpleName();
	
	private Context context = null;
	private AlertDialog.Builder builder = null;
	private AlertDialog dialog = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;

//		LinearLayout transparantLayout = new LinearLayout(context);
//		transparantLayout.setBackgroundColor(Color.TRANSPARENT);
//		setContentView(transparantLayout);
		
		Bundle bundle = getIntent().getExtras();
		String message = bundle.getString(AlarmReceiver.BUNDLE_KEY_ALARM_MESSAGE);
		
		LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		
		View dialogLayout = layoutInflater.inflate(R.layout.activity_dialog, null);
		View customTitleView = dialogLayout.findViewById(R.id.dialog_titleLayout);
		View customContentView = dialogLayout.findViewById(R.id.dialog_contentLayout);

		TextView messageTextView = (TextView)dialogLayout.findViewById(R.id.dialog_messageTextView);
		messageTextView.setText(message);
		
		Button okButton = (Button)dialogLayout.findViewById(R.id.dialog_okButton);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RingManager.getInstance().stopRing();
				PowerManager.getInstance().lightScreenOn();
				NotificationManager.getInstance().removeAllNotification();
				
				Intent intent = new Intent();
				intent.setClass(context, SelectSportActivity.class);
				startActivity(intent);
				finish();				
			}
		});
		
		Button laterButton = (Button)dialogLayout.findViewById(R.id.dialog_laterButton);

		laterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RingManager.getInstance().stopRing();
				PowerManager.getInstance().lightScreenOn();
				NotificationManager.getInstance().removeAllNotification();

				finish();				
			}
		});
		
		((ViewGroup)customTitleView.getParent()).removeView(customTitleView);
		((ViewGroup)customContentView.getParent()).removeView(customContentView);

		builder = new AlertDialog.Builder(this);
		dialog = builder.setCustomTitle(customTitleView)
			   .setView(customContentView)
			   .setCancelable(false).create();
		
	    dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
		dialog.show();

	}

	@Override
	protected void onDestroy() {
		
		if(dialog!=null && dialog.isShowing()){
			dialog.dismiss();
		}
		
		if(isOnlyActivity()){
			System.exit(0);
		}
		super.onDestroy();
	}
	
	private boolean isOnlyActivity(){
		ActivityManager mngr = (ActivityManager) getSystemService( ACTIVITY_SERVICE );

		List<ActivityManager.RunningTaskInfo> taskList = mngr.getRunningTasks(10);

	    Log.i(TAG, "Activity number = "+taskList.get(0).numActivities);
	    Log.i(TAG, "Top Activity = "+taskList.get(0).topActivity.getClassName());

		if(taskList.get(0).numActivities == 1 &&
		   taskList.get(0).topActivity.getClassName().equals(this.getClass().getName())) {
		    Log.i(TAG, "This is last activity in the stack");
		    return true;
		}
		return false;
	}
}
