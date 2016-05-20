package com.example.james_wills.flixster.adapters;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.james_wills.flixster.R;
import com.example.james_wills.flixster.models.Movie;
import com.example.james_wills.flixster.utils.DeviceDimensionsHelper;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 * Created by james_wills on 5/17/16.
 */
public class MovieArrayAdapter extends ArrayAdapter<Movie> {
  private static class ViewHolder {
    ImageView ivImage;
    TextView tvOverview;
    TextView tvTitle;
    int imageWidth;
    int placeholderID;

  }

  private boolean isLandscape;

  public MovieArrayAdapter(Context context, List<Movie> movies) {
    super(context, android.R.layout.simple_list_item_1, movies);
    isLandscape = DeviceDimensionsHelper.isLandscape(context);
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    Movie movie = getItem(position);

    final ViewHolder viewHolder;
    if(convertView == null) {
      viewHolder = new ViewHolder();
      LayoutInflater inflater = LayoutInflater.from(getContext());
      if (isLandscape) {
        convertView = inflater.inflate(R.layout.item_movie_landscape, parent, false);
        viewHolder.imageWidth = Movie.getBackdropWidthPx(getContext());
        viewHolder.placeholderID = R.drawable.backdropplaceholder;
      } else {
        convertView = inflater.inflate(R.layout.item_movie, parent, false);
        viewHolder.imageWidth = Movie.getPosterWidthPx(getContext());
        viewHolder.placeholderID = R.drawable.posterplaceholder;
      }

      viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivMovieImage);
      viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvDetailTitle);
      viewHolder.tvOverview = (TextView) convertView.findViewById(R.id.tvDetailOverview);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    String imagePath = isLandscape ? movie.getBackdropPath() : movie.getPosterPath();

    viewHolder.ivImage.setImageResource(0);

    viewHolder.tvTitle.setText(movie.getOriginalTitle());
    viewHolder.tvOverview.setText(movie.getOverview());

    Picasso.with(getContext())
      .load(imagePath)
      .resize(viewHolder.imageWidth, 0)
      .placeholder(viewHolder.placeholderID)
      .tag(getContext())
      .into(viewHolder.ivImage);

    return convertView;
  }
}
