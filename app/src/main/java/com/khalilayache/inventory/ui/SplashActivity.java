package com.khalilayache.inventory.ui;

import android.os.Bundle;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.ui.base.BaseActivity;

public class SplashActivity extends BaseActivity {


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_splash);

    startInventoryActivityAfter(SPLASH_INTENT_TIME_DELAY);
  }

  private void startInventoryActivityAfter(Integer milliseconds) {
    new java.util.Timer().schedule(
        new java.util.TimerTask() {
          @Override
          public void run() {
            startInventoryActivity();
          }
        },
        milliseconds
    );
  }

  private void startInventoryActivity() {
    startActivity(DetailsActivity.createIntent(SplashActivity.this));
    finish();
  }


}
