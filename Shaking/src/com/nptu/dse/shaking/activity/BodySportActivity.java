package com.nptu.dse.shaking.activity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.resource.DeveloperKey;

public class BodySportActivity extends YouTubeFailureRecoveryActivity {

	private static final String TAG = BodySportActivity.class.getSimpleName();

	private static final String videoId = "yFpBWgbgGAE";
	private YouTubePlayerView youtubePlayerView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_body_sport);

		youtubePlayerView = (YouTubePlayerView) findViewById(R.id.bodySport_youtubeView);
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
		return (YouTubePlayerView) findViewById(R.id.handSport_youtubeView);
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
