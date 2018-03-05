package com.khalilayache.inventory.utils;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE;

import android.content.ContentValues;

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
   * Method to create a Product object from a Content Value variable
   *
   * @param values that will used to create a Product object.
   *
   * @return the Product variable
   */
  public static Product getProductFromContentValues(ContentValues values) {

    String name = null;
    String description = null;
    Double price = null;
    Integer quantity = null;
    String photo = null;
    String supplierName = null;
    String supplierEmail = null;
    String supplierPhone = null;

    if (values.containsKey(COLUMN_NAME)) {
      name = values.getAsString(COLUMN_NAME);
    }

    if (values.containsKey(COLUMN_DESCRIPTION)) {
      description = values.getAsString(COLUMN_DESCRIPTION);
    }

    if (values.containsKey(COLUMN_PRICE)) {
      price = values.getAsDouble(COLUMN_PRICE);
    }

    if (values.containsKey(COLUMN_QUANTITY)) {
      quantity = values.getAsInteger(COLUMN_QUANTITY);
    }

    if (values.containsKey(COLUMN_PHOTO)) {
      photo = values.getAsString(COLUMN_PHOTO);
    }

    if (values.containsKey(COLUMN_SUPPLIER_NAME)) {
      supplierName = values.getAsString(COLUMN_SUPPLIER_NAME);
    }

    if (values.containsKey(COLUMN_SUPPLIER_EMAIL)) {
      supplierEmail = values.getAsString(COLUMN_SUPPLIER_EMAIL);
    }

    if (values.containsKey(COLUMN_SUPPLIER_PHONE)) {
      supplierPhone = values.getAsString(COLUMN_SUPPLIER_PHONE);
    }

    return new Product(name, description, price, quantity, photo, supplierName, supplierEmail, supplierPhone);
  }

  /**
   * Method to guarantee if a Product is valid or not
   *
   * @param product that will be validate.
   *
   * @return if valid = true or false if not
   */
  public static boolean productValidation(Product product) {

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
