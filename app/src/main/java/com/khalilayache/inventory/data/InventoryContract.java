package com.khalilayache.inventory.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public final class InventoryContract {

  static final String CONTENT_AUTHORITY = "com.khalilayache.inventory";
  // base content URI
  static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
  // path to table name
  static final String PATH_PRODUCTS = "products";

  // To prevent someone from accidentally instantiating the contract class,
  // give it an empty constructor.
  private InventoryContract() {}

  /**
   * Inner class that defines constant values for the products database table.
   * Each entry in the table represents a single product.
   */
  public static final class ProductEntry implements BaseColumns {

    public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_PRODUCTS);
    /**
     * Unique ID number for the product (only for use in the database table).
     * Type: INTEGER
     */
    public final static String _ID = BaseColumns._ID;
    /**
     * Name of the product.
     * Type: TEXT
     */
    public final static String COLUMN_NAME = "name";
    /**
     * Description of the product.
     * Type: TEXT
     */
    public final static String COLUMN_DESCRIPTION = "description";
    /**
     * Price of the product.
     * Type: REAL
     */
    public final static String COLUMN_PRICE = "price";
    /**
     * Quantity of the product in stock.
     * Type: INTEGER
     */
    public final static String COLUMN_QUANTITY = "quantity";
    /**
     * Photo of the product.
     * Type: TEXT
     */
    public final static String COLUMN_PHOTO = "photo";
    /**
     * Name of product supplier.
     * Type: TEXT
     */
    public final static String COLUMN_SUPPLIER_NAME = "supplier_name";
    /**
     * Email of product supplier.
     * Type: TEXT
     */
    public final static String COLUMN_SUPPLIER_EMAIL = "supplier_email";
    /**
     * Phone of product supplier.
     * Type: TEXT
     */
    public final static String COLUMN_SUPPLIER_PHONE = "supplier_phone";
    static final String CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
    static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_PRODUCTS;
    /** Name of database table for products */
    final static String TABLE_NAME = "products";
  }
}
