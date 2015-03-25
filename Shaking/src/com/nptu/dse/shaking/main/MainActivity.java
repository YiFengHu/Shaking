package com.nptu.dse.shaking.main;

import java.util.Date;

import com.example.shaking.R;
import com.nptu.dse.shaking.alarm.AlarmAgent;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	
	private static final String TAG = MainActivity.class.getSimpleName();

	private Context context = null;
	private AlarmAgent alarmAgent = null;
	private Button setButton = null;
	private Button cancelButton = null;
	
	private Date date = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		alarmAgent = new AlarmAgent(this);
		
		setButton = (Button)findViewById(R.id.set);
		setButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				date = new Date(System.currentTimeMillis()+5000);
				alarmAgent.setAlarm(date);
			}
		});
		cancelButton = (Button)findViewById(R.id.cancel);
		cancelButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				alarmAgent.cancelAlarm(date);
			}
		});
		
		
		
		
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
	
}
