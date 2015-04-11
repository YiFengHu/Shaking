package com.nptu.dse.shaking.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.activity.SelectSportActivity;
import com.nptu.dse.shaking.alarm.AlarmAgent;
import com.nptu.dse.shaking.alarm.AlarmAgent.AlarmDataListener;
import com.nptu.dse.shaking.db.AlarmEntity;
import com.nptu.dse.shaking.ui.adapter.AlarmListAdapter;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener, AlarmDataListener, OnTimeSetListener{
	
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int ID_COUNT_DOWN_TITLE_TEXTVIEW = R.id.main_countDownTitleTextView;
	private static final int ID_COUNT_DOWN_TIMER_TEXTVIEW = R.id.main_countDownTimerTextView;
	private static final int ID_SET_BUTTON = R.id.main_setAlarmButton;
	private static final int ID_CANCEL_BUTTON = R.id.main_cancelAlarmButton;
	private static final int ID_SHAKE_NOW_BUTTON = R.id.main_shakeNowButton;

	private Context context = null;
	private AlarmAgent alarmAgent = null;
	private Button setButton = null;
	private Button cancelButton = null;
	private Button shakeNowButton = null;
	private TextView titleTextView = null;
	private TextView timerTextView = null;
	private TimePickerDialog timePickerDialog = null;
	private ListView alarmListView = null;
	private AlarmListAdapter adapter = null;
	
	private Date date = null;
	private ArrayList<AlarmEntity> mAlarmList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		alarmAgent = AlarmAgent.getInstance(this);
		alarmAgent.setAlarmDataListener(this);
		mAlarmList = new ArrayList<AlarmEntity>();
		adapter = new AlarmListAdapter(this, mAlarmList);
		
		timePickerDialog = new TimePickerDialog(context, this, 0, 0, false);
		
		titleTextView = (TextView)findViewById(ID_COUNT_DOWN_TITLE_TEXTVIEW);
		timerTextView = (TextView)findViewById(ID_COUNT_DOWN_TIMER_TEXTVIEW);
		
		setButton = (Button)findViewById(ID_SET_BUTTON);
		setButton.setOnClickListener(this);
		cancelButton = (Button)findViewById(ID_CANCEL_BUTTON);
		cancelButton.setOnClickListener(this);
		shakeNowButton = (Button)findViewById(ID_SHAKE_NOW_BUTTON);
		shakeNowButton.setOnClickListener(this);
		alarmListView = (ListView)findViewById(R.id.main_alarmListView);
		alarmListView.setAdapter(adapter);
		
		setTextView();
		alarmAgent.requestAlarmData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return super.onOptionsItemSelected(item);
	}
	
	private void setTextView() {
		if(alarmAgent.isAlarmExist()){
//			alarmAgent.requestCountDown();
			titleTextView.setText(getString(R.string.main_add_an_alarm_to_shake));
			timerTextView.setVisibility(View.INVISIBLE);
		}else{
			titleTextView.setText(getString(R.string.main_shaking_count_down));
			timerTextView.setVisibility(View.INVISIBLE);
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case ID_SET_BUTTON:
			
			Calendar calender = Calendar.getInstance();
			calender.setTimeInMillis(System.currentTimeMillis());

			timePickerDialog.updateTime(calender.get(Calendar.HOUR), calender.get(Calendar.MINUTE));
			timePickerDialog.show();
//			date = new Date(System.currentTimeMillis()+5000);
//			alarmAgent.setAlarm(date);
//			alarmAgent.requestCountDown();

			break;

		case ID_CANCEL_BUTTON:
			
			alarmAgent.cancelAlarm();
			break;
			
		case ID_SHAKE_NOW_BUTTON:
			
			Intent intent = new Intent();
			intent.setClass(this, SelectSportActivity.class);
			startActivity(intent);
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onCountDouwUpdate(final String countDown) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				if(timerTextView.getVisibility() != View.VISIBLE){
					timerTextView.setVisibility(View.VISIBLE);
				}
				
				titleTextView.setText(getString(R.string.main_shaking_count_down));
				timerTextView.setText(countDown);				
			}
		});
		
	}

	@Override
	public void onCountDownFinish() {
		runOnUiThread(new Runnable() {

			@Override
			public void run() {
				timerTextView.setText("00:00:00");
			}
		});
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		alarmAgent.setAlarm(hourOfDay, minute, false);
	}

	@Override
	public void onAlarmDataReady(ArrayList<AlarmEntity> mAlarmList) {
		this.mAlarmList = mAlarmList;
		adapter.updateData(mAlarmList);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCountDownCancel() {
		timerTextView.setText("00:00:00");
	}
	
}
