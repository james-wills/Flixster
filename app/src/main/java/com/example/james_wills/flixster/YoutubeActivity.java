package com.example.james_wills.flixster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class YoutubeActivity extends YouTubeBaseActivity {
  String trailerKey;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_youtube);

    trailerKey = getIntent().getStringExtra("trailerkey");

    YouTubePlayerView youTubePlayerView = (YouTubePlayerView) findViewById(R.id.player);

    youTubePlayerView.initialize(this.getResources().getString(R.string.youtube_api_key),
        new YouTubePlayer.OnInitializedListener() {
          @Override
          public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                              YouTubePlayer youTubePlayer, boolean b) {

            // do any work here to cue video, play video, etc.
            youTubePlayer.cueVideo(trailerKey);
          }
          @Override
          public void onInitializationFailure(YouTubePlayer.Provider provider,
                                              YouTubeInitializationResult youTubeInitializationResult) {

          }
        });
  }
}
