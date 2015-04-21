package com.nptu.dse.shaking.activity;

import java.util.ArrayList;

import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.main.MainActivity;
import com.nptu.dse.shaking.manager.RingManager;
import com.nptu.dse.shaking.resource.IntentKey;

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

	private static final String VIDEO_ID_HEAD = "iQq8ypCfevo";
	private static final String VIDEO_ID_WAIST = "vwFTzRUHO2o";
	private static final String VIDEO_ID_HAND = "K3q-oFJilQk";
	private static final String VIDEO_ID_FOOT = "VMc2gtWWnFg";
	
	private ImageView headImageView = null;
	private ImageView waistImageView = null;
	private ImageView leftHandImageView = null;
	private ImageView rightHandImageView = null;
	private ImageView footImageView = null;
	
	private Button headHintButton = null;
	private Button waistHintButton = null;
	private Button handHintButton = null;
	private Button footHintButton = null;
	
	private ArrayList<Button> buttonList = null;
	private ArrayList<ImageView> imageViewList = null;
	private ArrayList<Integer> colorImageIdList = null;
	private ArrayList<Integer> blackImageIdList = null;

	private Intent intent = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		RingManager.getInstance().stopRing();
		
		setContentView(R.layout.activity_select_video);
		buttonList = new ArrayList<Button>();
		imageViewList = new ArrayList<ImageView>();
		colorImageIdList = new ArrayList<Integer>();
		blackImageIdList = new ArrayList<Integer>();
		
		headImageView = (ImageView) findViewById(R.id.select_childHeadImageView);
		waistImageView = (ImageView) findViewById(R.id.select_childWaistImageView);
		leftHandImageView = (ImageView) findViewById(R.id.select_childLeftHandImageView);
		rightHandImageView = (ImageView) findViewById(R.id.select_childRightHandImageView);
		footImageView = (ImageView) findViewById(R.id.select_childFootImageView);
		
		headHintButton = (Button)findViewById(R.id.select_childHeadHintButton);
		waistHintButton = (Button)findViewById(R.id.select_childWaistHintButton);
		handHintButton = (Button)findViewById(R.id.select_childHandHintButton);
		footHintButton = (Button)findViewById(R.id.select_childFootHintButton);

		imageViewList.add(headImageView);
		imageViewList.add(waistImageView);
		imageViewList.add(leftHandImageView);
		imageViewList.add(rightHandImageView);
		imageViewList.add(footImageView);
		
		colorImageIdList.add(R.drawable.ic_child_head);
		colorImageIdList.add(R.drawable.ic_child_waist);
		colorImageIdList.add(R.drawable.ic_child_left_hand);
		colorImageIdList.add(R.drawable.ic_child_right_hand);
		colorImageIdList.add(R.drawable.ic_child_foot);

		blackImageIdList.add(R.drawable.ic_child_head_black);
		blackImageIdList.add(R.drawable.ic_child_waist_black);
		blackImageIdList.add(R.drawable.ic_child_left_hand_black);
		blackImageIdList.add(R.drawable.ic_child_right_hand_black);
		blackImageIdList.add(R.drawable.ic_child_foot_black);
		
		buttonList.add(headHintButton);
		buttonList.add(waistHintButton);
		buttonList.add(handHintButton);
		buttonList.add(footHintButton);

		headImageView.setOnClickListener(this);
		waistImageView.setOnClickListener(this);
		leftHandImageView.setOnClickListener(this);
		rightHandImageView.setOnClickListener(this);
		footImageView.setOnClickListener(this);
		
		headHintButton.setOnClickListener(this);
		waistHintButton.setOnClickListener(this);
		handHintButton.setOnClickListener(this);
		footHintButton.setOnClickListener(this);

	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent();
		intent.setClass(this, MainActivity.class);
		startActivity(intent);
		finish();
//		super.onBackPressed();
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

	private void goPage(Class<?> cls, String videoId) {
		intent = new Intent();
		intent.putExtra(IntentKey.INTENT_KEY_YUOTUBE_VIDEO_ID, videoId);
		intent.setClass(this, cls);
		startActivity(intent);
	}
	
	private void setImage(ImageView imageView, int blackDrawableId, int colorDrawableId, Button hintButton){
		
		int indexOfList = imageViewList.indexOf(imageView);
		boolean isHand = (indexOfList==imageViewList.indexOf(leftHandImageView))||(indexOfList==imageViewList.indexOf(rightHandImageView));
		
		for(int i=0; i<imageViewList.size(); i++){
			if(i==indexOfList){
				imageViewList.get(i).setImageResource(colorImageIdList.get(i));
			}else{
				
				if(isHand){
					if(imageView == leftHandImageView){
						rightHandImageView.setImageResource(R.drawable.ic_child_right_hand);
						imageViewList.get(i).setImageResource(blackImageIdList.get(i));

					}else{
						leftHandImageView.setImageResource(R.drawable.ic_child_left_hand);
						imageViewList.get(i).setImageResource(blackImageIdList.get(i));

					}
				}else{
					imageViewList.get(i).setImageResource(blackImageIdList.get(i));
				}
			}
		}
		
		for(Button button:buttonList){
			if(button == hintButton){
				button.setVisibility(View.VISIBLE);
				button.setClickable(true);
			}else{
				button.setVisibility(View.INVISIBLE);
				button.setClickable(false);
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.select_childHeadImageView:
			
			setImage(headImageView, R.drawable.ic_child_head_black, R.drawable.ic_child_head, headHintButton);
			break;
			
		case R.id.select_childWaistImageView:

			setImage(waistImageView, R.drawable.ic_child_waist_black, R.drawable.ic_child_waist, waistHintButton);
			break;
			
		case R.id.select_childLeftHandImageView:
			
			setImage(leftHandImageView, R.drawable.ic_child_left_hand_black, R.drawable.ic_child_left_hand, handHintButton);
//			setImage(rightHandImageView, R.drawable.ic_child_right_hand_black, R.drawable.ic_child_right_hand, handHintButton);
			break;
			
		case R.id.select_childRightHandImageView:

			setImage(rightHandImageView, R.drawable.ic_child_right_hand_black, R.drawable.ic_child_right_hand, handHintButton);
//			setImage(leftHandImageView, R.drawable.ic_child_left_hand_black, R.drawable.ic_child_left_hand, handHintButton);
			break;
			
		case R.id.select_childFootImageView:

			setImage(footImageView, R.drawable.ic_child_foot_black, R.drawable.ic_child_foot, footHintButton);
			break;
			
		case R.id.select_childHeadHintButton:

			goPage(SportActivity.class, VIDEO_ID_HEAD);
			break;
			
		case R.id.select_childWaistHintButton:
			
			goPage(SportActivity.class, VIDEO_ID_WAIST);
			break;
			
		case R.id.select_childHandHintButton:
			
			goPage(SportActivity.class, VIDEO_ID_HAND);
			break;
			
		case R.id.select_childFootHintButton:
			
			goPage(SportActivity.class, VIDEO_ID_FOOT);
			break;

		default:
			break;
		}
	}

}
