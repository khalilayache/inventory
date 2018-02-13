package com.khalilayache.inventory.data;

import android.provider.BaseColumns;

public final class InventoryContract {

  // To prevent someone from accidentally instantiating the contract class,
  // give it an empty constructor.
  private InventoryContract() {}

  /**
   * Inner class that defines constant values for the products database table.
   * Each entry in the table represents a single product.
   */
  public static final class ProductEntry implements BaseColumns {

    /** Name of database table for products */
    public final static String TABLE_NAME = "products";

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
    public final static String COLUMN_PHONE = "phone";
  }
}
