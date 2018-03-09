package com.khalilayache.inventory.ui;

import static com.khalilayache.inventory.utils.Constants.SPLASH_INTENT_TIME_DELAY;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.khalilayache.inventory.R;

public class SplashActivity extends AppCompatActivity {

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
    startActivity(InventoryActivity.createIntent(SplashActivity.this));
    finish();
  }

}
