package com.khalilayache.inventory.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Database helper for Inventory app. Manages database creation and version management.
 */
public class InventoryDbHelper extends SQLiteOpenHelper {

  /** Name of the database file */
  private static final String DATABASE_NAME = "inventory";
  /**
   * Database version. If you change the database schema, you must increment the database version.
   */
  private static final int DATABASE_VERSION = 1;
  // Create a String that contains the SQL statement to create the products table
  private String SQL_CREATE_PRODUCTS_TABLE = "CREATE TABLE " +
      InventoryContract.ProductEntry.TABLE_NAME + "(" +
      InventoryContract.ProductEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
      InventoryContract.ProductEntry.COLUMN_NAME + " TEXT NOT NULL, " +
      InventoryContract.ProductEntry.COLUMN_DESCRIPTION + " TEXT, " +
      InventoryContract.ProductEntry.COLUMN_PRICE + " REAL NOT NULL, " +
      InventoryContract.ProductEntry.COLUMN_QUANTITY + " INTEGER DEFAULT 0," +
      InventoryContract.ProductEntry.COLUMN_PHOTO + " TEXT, " +
      InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME + " TEXT, " +
      InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL, " +
      InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL);";

  /**
   * Constructs a new instance of {@link InventoryDbHelper}.
   *
   * @param context of the app
   */
  InventoryDbHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  /**
   * This is called when the database is created for the first time.
   */
  @Override
  public void onCreate(SQLiteDatabase db) {
    // Execute the SQL statement
    db.execSQL(SQL_CREATE_PRODUCTS_TABLE);
  }

  @Override
  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    // The database is still at version 1, so there's nothing to do be done here.
  }
}
