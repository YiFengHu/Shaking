package com.nptu.dse.shaking.activity;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectSportActivity extends Activity implements OnClickListener {

	private static final String TAG = SelectSportActivity.class.getSimpleName();

	private Button handButton = null;
	private Button legButton = null;
	private Button bodyButton = null;
	
	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_video);

		handButton = (Button) findViewById(R.id.select_handTextView);
		legButton = (Button) findViewById(R.id.select_legTextView);
		bodyButton = (Button) findViewById(R.id.select_bodyTextView);
		handButton.setOnClickListener(this);
		legButton.setOnClickListener(this);
		bodyButton.setOnClickListener(this);

	}
	
	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	
	private void goPage(Class<?> cls){
		intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.select_handTextView:

			goPage(HandSportActivity.class);
			break;

		case R.id.select_legTextView:

			goPage(LegSportActivity.class);
			break;
			
		case R.id.select_bodyTextView:

			goPage(BodySportActivity.class);
			break;

		default:
			break;
		}
	}

}
