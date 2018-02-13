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

  private InventoryDbHelper inventoryDb;

  public InventoryDbManager(Context context) {
    this.inventoryDb = new InventoryDbHelper(context);
  }

  public boolean insertProduct(Product product) {
    ContentValues values;

    if (productValidation(product)) {
      values = getProductContentValues(product);
    } else {
      throw new IllegalArgumentException("Invalid inputs");
    }

    return insert(values) != -1L;
  }

  private Long insert(ContentValues values) {
    SQLiteDatabase productsTable = inventoryDb.getWritableDatabase();

    return productsTable.insert(TABLE_NAME, null, values);
  }

  public ArrayList<Product> selectAllFromPets() {
    ArrayList<Product> productList = new ArrayList<>();

    SQLiteDatabase productTable = inventoryDb.getReadableDatabase();

    Cursor cursor = productTable.query(false,
        TABLE_NAME,
        null,
        null,
        null,
        null,
        null,
        null,
        null);

    while (cursor.moveToNext()) {
      String name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME));
      String description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION));
      Double price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE));
      Integer quantity = cursor.getInt(cursor.getColumnIndex(COLUMN_QUANTITY));
      String photo = cursor.getString(cursor.getColumnIndex(COLUMN_PHOTO));
      String supplier_name = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_NAME));
      String supplier_email = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_EMAIL));
      String supplier_phone = cursor.getString(cursor.getColumnIndex(COLUMN_SUPPLIER_PHONE));

      productList.add(new Product(name, description, price, quantity, photo, supplier_name, supplier_email, supplier_phone));
    }

    cursor.close();

    return productList;
  }

  public boolean deleteAllProducts() {
    SQLiteDatabase productsTable = inventoryDb.getWritableDatabase();

    return productsTable.delete(TABLE_NAME, null, null) != -1;

  }

  private ContentValues getProductContentValues(Product product) {
    ContentValues values = new ContentValues();

    values.put(COLUMN_NAME, product.getName());
    values.put(COLUMN_DESCRIPTION, product.getDescription());
    values.put(COLUMN_PRICE, product.getPrice());
    values.put(COLUMN_QUANTITY, product.getQuantity());
    values.put(COLUMN_PHOTO, product.getPhoto());
    values.put(COLUMN_SUPPLIER_NAME, product.getSupplier_name());
    values.put(COLUMN_SUPPLIER_EMAIL, product.getSupplier_email());
    values.put(COLUMN_SUPPLIER_PHONE, product.getSupplier_phone());

    return values;
  }

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
      if ((product.getSupplier_name().isEmpty() || product.getSupplier_name() == null) &&
          (!(product.getSupplier_phone().isEmpty() || product.getSupplier_phone() == null) ||
              !(product.getSupplier_email().isEmpty() || product.getSupplier_email() == null))) {
        return false;
      }

      //check if only supplier name is not empty when contacts fields are blank
      if ((!product.getSupplier_name().isEmpty() && product.getSupplier_name() != null) &&
          ((product.getSupplier_phone().isEmpty() || product.getSupplier_phone() == null) &&
              (product.getSupplier_email().isEmpty() || product.getSupplier_email() == null))) {
        return false;
      }

      //TODO(PHOTO VALIDATION)

    } else {
      return false;
    }

    return true;
  }

}
