package com.khalilayache.inventory.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.ui.base.BaseActivity;

public class DetailsActivity extends BaseActivity {

  public static Intent createIntent(Context context) {
    return new Intent(context, DetailsActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);
  }
}
