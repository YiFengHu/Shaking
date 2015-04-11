package com.nptu.dse.shaking.activity;

import android.os.Bundle;

import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.google.android.youtube.player.YouTubePlayer.Provider;
import com.nptu.dse.shaking.R;
import com.nptu.dse.shaking.resource.DeveloperKey;

public class LegSportActivity extends YouTubeFailureRecoveryActivity {

	private static final String TAG = LegSportActivity.class.getSimpleName();

	private static final String videoId = "rmOt-7RQAxU";
	private YouTubePlayerView youtubePlayerView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_leg_sport);

		youtubePlayerView = (YouTubePlayerView) findViewById(R.id.legSport_youtubeView);
		youtubePlayerView.initialize(DeveloperKey.DEVELOPER_KEY, this);
	}

	@Override
	public void onInitializationSuccess(YouTubePlayer.Provider provider,
			YouTubePlayer player, boolean wasRestored) {
		if (!wasRestored) {
			player.cueVideo(videoId);
			player.play();
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