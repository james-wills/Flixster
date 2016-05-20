package com.example.james_wills.flixster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.james_wills.flixster.models.Movie;
import com.example.james_wills.flixster.utils.DeviceDimensionsHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends AppCompatActivity {
  Movie movie;
  String trailerID;
  ImageButton playButton;
  private AsyncHttpClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (DeviceDimensionsHelper.isLandscape(this)) {
      setContentView(R.layout.activity_detail_landscape);
    } else {
      setContentView(R.layout.activity_detail);
    }

    movie = (Movie) getIntent().getSerializableExtra("movie");

    TextView title = (TextView) findViewById(R.id.tvDetailTitle);
    title.setText(movie.getOriginalTitle());

    TextView overview = (TextView) findViewById(R.id.tvDetailOverview);
    overview.setText(movie.getOverview());

    TextView releaseDate = (TextView) findViewById(R.id.tvReleaseDate);
    releaseDate.setText(movie.getReleaseDate());

    RatingBar popularityBar = (RatingBar) findViewById(R.id.rbPopularity);
    popularityBar.setRating((float) movie.getPopularity());

    ImageView backdrop = (ImageView) findViewById(R.id.ivBackdrop);
    Picasso.with(this).load(movie.getBackdropPath())
        .placeholder(R.drawable.backdropplaceholder)
        .into(backdrop);

    playButton = (ImageButton) findViewById(R.id.ibPlayButton);
    playButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Log.i("JB", "You clicked play!");
        navigateToVideo();
      }
    });


    client = new AsyncHttpClient();
    fetchTrailerIDAsync();
  }

  private void fetchTrailerIDAsync() {
    String url = String.format(this.getResources().getString(R.string.tmdb_video), movie.getId());
    client.get(url, new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        JSONArray movieJsonResults = null;

        try {
          movieJsonResults = response.getJSONArray("results");
          if (movieJsonResults.length() > 0) {
            JSONObject trailer = movieJsonResults.getJSONObject(0);
            trailerID = trailer.getString("key");
            playButton.setVisibility(View.VISIBLE);
          } else {
            playButton.setVisibility(View.INVISIBLE);
          }
        } catch (JSONException e) {
          e.printStackTrace();
        }
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });
  }

  private void navigateToVideo() {
    Intent i = new Intent(DetailActivity.this, YoutubeActivity.class);
    i.putExtra("trailerkey", trailerID);
    startActivity(i);
  }
}
