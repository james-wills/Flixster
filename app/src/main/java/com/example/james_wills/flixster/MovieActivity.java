package com.example.james_wills.flixster;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.james_wills.flixster.adapters.MovieArrayAdapter;
import com.example.james_wills.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {
  ArrayList<Movie> movies;
  MovieArrayAdapter movieAdapter;
  ListView lvItems;

  private SwipeRefreshLayout swipeContainer;
  private AsyncHttpClient client;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_movie);

    lvItems = (ListView) findViewById(R.id.lvMovies);
    movies = new ArrayList<>();
    movieAdapter = new MovieArrayAdapter(this, movies);
    lvItems.setAdapter(movieAdapter);

    client = new AsyncHttpClient();

    initClickListeners();
    initSwipeContainer();
    fetchTimelineAsync();
  }

  private void initClickListeners() {
    lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(MovieActivity.this, DetailActivity.class);
        // put "extras" into the bundle for access in the second activity
        i.putExtra("movie", movieAdapter.getItem(position));
        startActivity(i);
      }
    });
  }

  private void initSwipeContainer() {
    swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
    swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
      @Override
      public void onRefresh() {
        fetchTimelineAsync();
      }
    });
    swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
        android.R.color.holo_green_light,
        android.R.color.holo_orange_light,
        android.R.color.holo_red_light);
  }

  private void fetchTimelineAsync() {
    client.get(this.getResources().getString(R.string.tmdb_url), new JsonHttpResponseHandler() {
      @Override
      public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        JSONArray movieJsonResults = null;

        try {
          movieJsonResults = response.getJSONArray("results");
          movies.addAll(Movie.fromJSONArray(movieJsonResults));
          movieAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
          e.printStackTrace();
        } finally {
          if(swipeContainer != null) {
            swipeContainer.setRefreshing(false);
          }
        }
      }

      @Override
      public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        super.onFailure(statusCode, headers, responseString, throwable);
      }
    });
  }

}
