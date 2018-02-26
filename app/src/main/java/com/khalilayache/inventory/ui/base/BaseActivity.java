package com.khalilayache.inventory.ui.base;

import android.content.ContentResolver;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class BaseActivity extends AppCompatActivity {

  public static final Integer SPLASH_INTENT_TIME_DELAY = 2000;

  public static final Integer STORAGE_REQUEST_CODE = 700;

  public static final Integer PHOTO_INTENT_CODE = 701;

  public static final Integer PRODUCT_LOADER_CODE = 300;

  // Get URI String of a Drawable Resource
  public String getUriStringOfDrawable(int drawableId) {
    return ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
        getResources().getResourcePackageName(drawableId) + '/' +
        getResources().getResourceTypeName(drawableId) + '/' +
        getResources().getResourceEntryName(drawableId);
  }

  public void showToast(String message) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
  }
}
