package com.nptu.dse.shaking.activity;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.alarm.AlarmReceiver;
import com.nptu.dse.shaking.alarm.RingAgent;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DialogActivity extends Activity{

	private Context context = null;
	
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
		
		View dialogView = layoutInflater.inflate(R.layout.activitty_dialog, null);
		View customTitleView = dialogView.findViewById(R.id.dialog_titleLayout);
		View customContentView = dialogView.findViewById(R.id.dialog_contentLayout);

		TextView messageTextView = (TextView)dialogView.findViewById(R.id.dialog_messageTextView);
		messageTextView.setText(message);
		
		Button okButton = (Button)dialogView.findViewById(R.id.dialog_okButton);
		okButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RingAgent.getInstance().stopRing();

				Intent intent = new Intent();
				intent.setClass(context, SelectSportActivity.class);
				startActivity(intent);
				finish();				
			}
		});
		
		Button laterButton = (Button)dialogView.findViewById(R.id.dialog_laterButton);

		laterButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				RingAgent.getInstance().stopRing();

				finish();				
			}
		});
		
		((ViewGroup)customTitleView.getParent()).removeView(customTitleView);
		((ViewGroup)customContentView.getParent()).removeView(customContentView);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCustomTitle(customTitleView)
			   .setView(customContentView)
			   .setCancelable(false).create().show();
	}
	
	
}
