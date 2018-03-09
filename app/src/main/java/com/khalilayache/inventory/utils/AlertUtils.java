package com.khalilayache.inventory.utils;

import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

public class AlertUtils {

  private static Toast toast;

  public static void showToast(Context context, String message) {
    if (toast != null) { toast.cancel(); }

    toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
    toast.show();
  }

  public static void showAlertDialog(Context context,
      String message,
      String positiveString,
      String negativeString,
      String neutralString,
      OnClickListener positiveListener,
      OnClickListener negativeListener,
      OnClickListener neutralListener) {
    AlertDialog dialog = new AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton(positiveString, positiveListener)
        .setNegativeButton(negativeString, negativeListener)
        .setNeutralButton(neutralString, neutralListener)
        .create();
    dialog.show();
  }

  public static void showAlertDialog(Context context,
      String message,
      String positiveString,
      String negativeString,
      OnClickListener positiveListener,
      OnClickListener negativeListener) {
    AlertDialog dialog = new AlertDialog.Builder(context)
        .setMessage(message)
        .setPositiveButton(positiveString, positiveListener)
        .setNegativeButton(negativeString, negativeListener)
        .create();
    dialog.show();
  }

}
