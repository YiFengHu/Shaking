package com.nptu.dse.shaking.activity;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.main.MainActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class SelectSportActivity extends Activity implements OnClickListener {

	private static final String TAG = SelectSportActivity.class.getSimpleName();

	private ImageView headImageView = null;
	private ImageView waistImageView = null;
	private ImageView leftHandImageView = null;
	private ImageView rightHandImageView = null;
	private ImageView footImageView = null;

	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_video);

		headImageView = (ImageView) findViewById(R.id.select_childHeadImageView);
		waistImageView = (ImageView) findViewById(R.id.select_childWaistImageView);
		leftHandImageView = (ImageView) findViewById(R.id.select_childLeftHandImageView);
		rightHandImageView = (ImageView) findViewById(R.id.select_childRightHandImageView);
		footImageView = (ImageView) findViewById(R.id.select_childFootImageView);
		
		headImageView.setOnClickListener(this);
		waistImageView.setOnClickListener(this);
		leftHandImageView.setOnClickListener(this);
		rightHandImageView.setOnClickListener(this);
		footImageView.setOnClickListener(this);

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

	private void goPage(Class<?> cls) {
		intent = new Intent();
		intent.setClass(this, cls);
		startActivity(intent);
	}
	
	private void setImage(ImageView imageView, int blackDrawableId, int colorDrawableId){
		if(imageView.getTag() == null){
			imageView.setTag(Boolean.valueOf(true));
			imageView.setBackgroundResource(colorDrawableId);
			Log.i("test", "set background normal");

		}else{
			if(((Boolean)imageView.getTag()).booleanValue()){
				
				Toast.makeText(this, "Change page", Toast.LENGTH_SHORT).show();
				imageView.setTag(Boolean.valueOf(false));
				imageView.setBackgroundResource(blackDrawableId);
				Log.i("test", "set background black");

			}else{
				imageView.setTag(Boolean.valueOf(true));
				imageView.setBackgroundResource(colorDrawableId);
				Log.i("test", "set background normal");

			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.select_childHeadImageView:
			
			setImage(headImageView, R.drawable.ic_child_head_black, R.drawable.ic_child_head);
			
			break;
			
		case R.id.select_childWaistImageView:

			
			break;
			
		case R.id.select_childLeftHandImageView:
			
			
			break;
			
		case R.id.select_childRightHandImageView:

			
			break;
			
		case R.id.select_childFootImageView:

			
			break;

		default:
			break;
		}
	}

}
