package com.example.james_wills.flixster.models;

import android.content.Context;
import android.util.Log;

import com.example.james_wills.flixster.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by james_wills on 5/17/16.
 */
public class Movie implements Serializable {
  String posterPath;
  String backdropPath;
  String originalTitle;
  String overview;
  String releaseDate;

  double popularity;
  int id;

  public Movie(JSONObject jsonObject) throws JSONException {
    this.posterPath = jsonObject.getString("poster_path");
    this.backdropPath = jsonObject.getString("backdrop_path");
    this.originalTitle = jsonObject.getString("original_title");
    this.overview = jsonObject.getString("overview");
    this.releaseDate = jsonObject.getString("release_date");
    this.id = jsonObject.getInt("id");
    this.popularity = jsonObject.getDouble("popularity");
  }

  public String getOverview() {
    return overview;
  }

  public String getPosterPath() {
    return getPath(342, posterPath);
  }

  public String getBackdropPath() {
    return getPath(600, backdropPath);
  }

  public double getPopularity() {
    return popularity;
  }

  public int getId() {
    return id;
  }

  public String getReleaseDate() {
    return releaseDate;
  }

  public String getOriginalTitle() {
    return originalTitle;
  }

  public static ArrayList<Movie> fromJSONArray(JSONArray array) {
    Log.i("JB", array.toString());
    ArrayList<Movie> results = new ArrayList<>();
    for (int i = 0; i < array.length(); i++) {
      try {
        results.add(new Movie(array.getJSONObject(i)));
      } catch (JSONException e) {
        e.printStackTrace();
      }
    }

    return results;
  }

  private static String getPath(int width, String relativePath) {
    return String.format("https://image.tmdb.org/t/p/w%d/%s", width, relativePath);
  }

  public static int getPosterWidthPx(Context context) {
    return (int) context.getResources().getDimension(R.dimen.poster_width);
  }

  public static int getPosterHeightPx(Context context) {
    return (int) context.getResources().getDimension(R.dimen.poster_height);
  }

  public static int getBackdropWidthPx(Context context) {
    return (int) context.getResources().getDimension(R.dimen.backdrop_width);
  }

  public static int getBackdropHeightPx(Context context) {
    return (int) context.getResources().getDimension(R.dimen.backdrop_height);
  }
}
