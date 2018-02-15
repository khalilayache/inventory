package com.khalilayache.inventory.data;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.TABLE_NAME;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.khalilayache.inventory.model.Product;

public class InventoryDbManager {

  /**
   * {@link InventoryDbHelper variable}.
   */
  private InventoryDbHelper inventoryDb;

  /**
   * Constructor of the class.
   *
   * @param context of the app
   */
  public InventoryDbManager(Context context) {
    this.inventoryDb = new InventoryDbHelper(context);
  }

  /**
   * Helper method to insert a product.
   *
   * @param product Product that will be inserted
   *
   * @return true if product was inserted successfully. False if not
   */
  public boolean insertProduct(Product product) {
    ContentValues values;

    //Check if there is a valid product if not throw an exception
    if (productValidation(product)) {
      values = getProductContentValues(product);
    } else {
      throw new IllegalArgumentException("Invalid inputs");
    }

    return insert(values) != -1L;
  }

  /**
   * Method to insert a product.
   *
   * @param values Content value variable with Product that will be inserted
   *
   * @return a Long with the number of lines that was inserted. Return -1 if got an error
   */
  private Long insert(ContentValues values) {
    //Get a writable instance of DB
    SQLiteDatabase productsTable = inventoryDb.getWritableDatabase();

    return productsTable.insert(TABLE_NAME, null, values);
  }

  /**
   * Method to return all Products in products table.
   *
   * @return An ArrayList of Products with all Products inside
   */
  public ArrayList<Product> selectAllFromProducts() {
    //Product Array List variable
    ArrayList<Product> productList = new ArrayList<>();

    //Get a readable instance of db
    SQLiteDatabase productTable = inventoryDb.getReadableDatabase();

    //Cursor instance with response of select query
    Cursor cursor = productTable.query(false,
        TABLE_NAME, // table name
        null, // all columns so parameter is null
        null, // no where clause
        null, // no where arguments
        null, // no groupBy clause
        null, // no having clause
        null, //no orderBy clause
        null); // no limits items

    while (cursor.moveToNext()) {
      String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
      String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
      Double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
      Integer quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
      String photo = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO));
      String supplierName = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_NAME));
      String supplierEmail = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_EMAIL));
      String supplierPhone = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_PHONE));

      productList.add(new Product(name, description, price, quantity, photo, supplierName, supplierEmail, supplierPhone));
    }

    cursor.close();

    return productList;
  }

  /**
   * Method to delete all Products of products table.
   *
   * @return if command ran correctly return true, return false if not
   */
  public boolean deleteAllProducts() {
    //Get a writable instance of DB
    SQLiteDatabase productsTable = inventoryDb.getWritableDatabase();

    return productsTable.delete(TABLE_NAME, null, null) != -1;

  }

  /**
   * Method to get a Content Value variable of a Product .
   *
   * @param product that will be inserted in content value variable
   *
   * @return a content value variable with a Product info inside
   */
  private ContentValues getProductContentValues(Product product) {
    ContentValues values = new ContentValues();

    values.put(COLUMN_NAME, product.getName());
    values.put(COLUMN_DESCRIPTION, product.getDescription());
    values.put(COLUMN_PRICE, product.getPrice());
    values.put(COLUMN_QUANTITY, product.getQuantity());
    values.put(COLUMN_PHOTO, product.getPhoto());
    values.put(COLUMN_SUPPLIER_NAME, product.getSupplierName());
    values.put(COLUMN_SUPPLIER_EMAIL, product.getSupplierEmail());
    values.put(COLUMN_SUPPLIER_PHONE, product.getSupplierPhone());

    return values;
  }

  /**
   * Method to guarantee if a Product is valid or not
   *
   * @param product that will be validate.
   *
   * @return if valid = true or false if not
   */
  private boolean productValidation(Product product) {

    //check if product object is null
    if (product != null) {

      //check if product's name is empty or null
      if (product.getName().isEmpty() ||
          product.getName() == null) { return false; }

      //check if product's price is null or minor than 0
      if (product.getPrice() == null ||
          product.getPrice() <= 0) { return false; }

      //check if product's price is null or minor than 0
      if (product.getQuantity() == null ||
          product.getQuantity() < 0) { return false; }

      //check if only supplier name is empty or null when email or phone is not null or empty
      if ((product.getSupplierName().isEmpty() || product.getSupplierName() == null) &&
          (!(product.getSupplierPhone().isEmpty() || product.getSupplierPhone() == null) ||
              !(product.getSupplierEmail().isEmpty() || product.getSupplierEmail() == null))) {
        return false;
      }

      //check if only supplier name is not empty when contacts fields are blank
      if ((!product.getSupplierName().isEmpty() && product.getSupplierName() != null) &&
          ((product.getSupplierPhone().isEmpty() || product.getSupplierPhone() == null) &&
              (product.getSupplierEmail().isEmpty() || product.getSupplierEmail() == null))) {
        return false;
      }

    } else {
      return false;
    }

    return true;
  }

}
