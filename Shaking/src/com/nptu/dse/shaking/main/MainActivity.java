package com.nptu.dse.shaking.main;

import java.util.Date;

import com.example.shaking.R;
import com.nptu.dse.shaking.alarm.AlarmAgent;
import com.nptu.dse.shaking.alarm.AlarmAgent.AlarmDataListener;

import android.app.Activity;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends Activity implements OnClickListener, AlarmDataListener, OnTimeSetListener{
	
	private static final String TAG = MainActivity.class.getSimpleName();

	private static final int ID_COUNT_DOWN_TITLE_TEXTVIEW = R.id.main_countDownTitleTextView;
	private static final int ID_COUNT_DOWN_TIMER_TEXTVIEW = R.id.main_countDownTimerTextView;
	private static final int ID_SET_BUTTON = R.id.main_setAlarmButton;
	private static final int ID_CANCEL_BUTTON = R.id.main_cancelAlarmButton;

	private Context context = null;
	private AlarmAgent alarmAgent = null;
	private Button setButton = null;
	private Button cancelButton = null;
	private TextView titleTextView = null;
	private TextView timerTextView = null;

	private TimePickerDialog timePickerDialog = null;
	
	private Date date = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		alarmAgent = new AlarmAgent(this);
		alarmAgent.setAlarmDataListener(this);
		
		timePickerDialog = new TimePickerDialog(context, null, 0, 0, false);
		
		titleTextView = (TextView)findViewById(ID_COUNT_DOWN_TITLE_TEXTVIEW);
		timerTextView = (TextView)findViewById(ID_COUNT_DOWN_TIMER_TEXTVIEW);
		
		setButton = (Button)findViewById(ID_SET_BUTTON);
		setButton.setOnClickListener(this);
		cancelButton = (Button)findViewById(ID_CANCEL_BUTTON);
		cancelButton.setOnClickListener(this);
		
		setTextView();
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
			
			timePickerDialog.show();
//			date = new Date(System.currentTimeMillis()+5000);
//			alarmAgent.setAlarm(date);
//			alarmAgent.requestCountDown();

			break;

		case ID_CANCEL_BUTTON:
			
			alarmAgent.cancelAlarm(date);
			break;
			
		default:
			break;
		}
	}

	@Override
	public void onCountDouwUpdate(String countDown) {
		if(timerTextView.getVisibility() != View.VISIBLE){
			timerTextView.setVisibility(View.VISIBLE);
		}
		
		titleTextView.setText(getString(R.string.main_shaking_count_down));
		timerTextView.setText(countDown);
	}

	@Override
	public void onCountDownFinish() {
		timerTextView.setText("00:00:00");
//		alarmAgent.requestCountDown();
	}

	@Override
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		
		alarmAgent.setAlarm(hourOfDay, minute);
	}
	
}
