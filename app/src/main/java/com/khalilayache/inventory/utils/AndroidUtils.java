package com.khalilayache.inventory.utils;

import android.content.ContentResolver;
import android.content.Context;

public class AndroidUtils {

  // Get URI String of a Drawable Resource
  public static String getUriStringOfDrawable(int drawableId, Context context) {
    return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
        context.getResources().getResourcePackageName(drawableId) + '/' +
        context.getResources().getResourceTypeName(drawableId) + '/' +
        context.getResources().getResourceEntryName(drawableId);
  }
}
