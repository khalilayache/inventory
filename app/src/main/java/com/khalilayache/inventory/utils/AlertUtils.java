package com.khalilayache.inventory.utils;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class AlertUtils {

  private Context context;
  private Toast toast;
  private AlertDialog dialog;

  public AlertUtils(Context context) {
    this.context = context;
  }

  public void showToast(String message) {
    if (toast != null) { toast.cancel(); }

    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
    toast.show();
  }

  public void showAlertDialog(
      String message,
      String positiveString,
      String negativeString,
      String neutralString,
      OnClickListener positiveListener,
      OnClickListener negativeListener,
      OnClickListener neutralListener) {
    dialog = new AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton(positiveString, positiveListener)
        .setNegativeButton(negativeString, negativeListener)
        .setNeutralButton(neutralString, neutralListener)
        .create();
    dialog.show();
  }

  public void showAlertDialog(
      String message,
      String positiveString,
      String negativeString,
      OnClickListener positiveListener,
      OnClickListener negativeListener) {
    dialog = new AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton(positiveString, positiveListener)
        .setNegativeButton(negativeString, negativeListener)
        .create();
    dialog.show();
  }

}
