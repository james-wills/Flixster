package com.example.james_wills.flixster.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public class DeviceDimensionsHelper {
  // DeviceDimensionsHelper.getDisplayWidth(context) => (display width in pixels)
  public static boolean isLandscape(Context context) {
    return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
  }
}