package com.khalilayache.inventory.utils;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.model.Product;

public class ProductUtils {

  /**
   * Method to get a Content Value variable of a Product .
   *
   * @param product that will be inserted in content value variable
   *
   * @return a content value variable with a Product info inside
   */
  public static ContentValues getContentValuesFromProduct(Product product) {
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
  public static ArrayList<String> productValidation(Product product, Context context) {

    ArrayList<String> errorString = new ArrayList<>();


    //check if product object is null
    if (product != null) {

      //check if product's name is empty or null
      if (product.getName().isEmpty() ||
          product.getName() == null) { errorString.add(context.getString(R.string.empty_name));}

      //check if product's price is null or minor than 0
      if (product.getPrice() == null ||
          product.getPrice() <= 0) { errorString.add(context.getString(R.string.empty_price));}

      //check if product's price is null or minor than 0
      if (product.getQuantity() == null ||
          product.getQuantity() < 0) { errorString.add(context.getString(R.string.empty_quantity)); }

      //check if only supplier name is empty or null when email or phone is not null or empty
      if ((product.getSupplierName().isEmpty() || product.getSupplierName() == null) &&
          (!(product.getSupplierPhone().isEmpty() || product.getSupplierPhone() == null) ||
              !(product.getSupplierEmail().isEmpty() || product.getSupplierEmail() == null))) {
        errorString.add(context.getString(R.string.empty_supplier_name));
      }

      //check if only supplier name is not empty when contacts fields are blank
      if ((!product.getSupplierName().isEmpty() && product.getSupplierName() != null) &&
          ((product.getSupplierPhone().isEmpty() || product.getSupplierPhone() == null) &&
              (product.getSupplierEmail().isEmpty() || product.getSupplierEmail() == null))) {
        errorString.add(context.getString(R.string.empty_supplier_email_phone));
      }

    } else {
      errorString.add(context.getString(R.string.invalid_product));
    }

    return errorString;
  }
}
