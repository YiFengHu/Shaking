package com.nptu.dse.shaking.activity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.resource.DeveloperKey;
import com.nptu.dse.shaking.resource.IntentKey;

public class SportActivity extends YouTubeFailureRecoveryActivity {

	private static final String TAG = SportActivity.class.getSimpleName();

//	private static final String videoId = "ALAaIcLTS4Y";
	private YouTubePlayerView youtubePlayerView = null;
	
	private ImageView animationImageView = null;
	private AnimationDrawable animationDrawable = null;
	
	private Intent intent = null;
	private String videoId = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sport);
		intent = getIntent();
		if(intent!=null){
			if(intent.getStringExtra(IntentKey.INTENT_KEY_YUOTUBE_VIDEO_ID)!=null){
				videoId = intent.getStringExtra(IntentKey.INTENT_KEY_YUOTUBE_VIDEO_ID);
			}else{
				videoId = "";
			}
		}
		
		animationImageView = (ImageView)findViewById(R.id.sport_animation);
		animationDrawable = (AnimationDrawable)animationImageView.getBackground();
		animationDrawable.start();
		
		youtubePlayerView = (YouTubePlayerView) findViewById(R.id.sport_youtubeView);
		youtubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(videoId);
		}
	}

	@Override
	protected Provider getYouTubePlayerProvider() {
		return (YouTubePlayerView) findViewById(R.id.sport_youtubeView);
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

}
