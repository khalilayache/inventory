package com.khalilayache.inventory.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class ProductProvider extends ContentProvider {

  public static final int PRODUCTS_LIST_URI_CODE = 800;
  public static final int PRODUCT_ITEM_URI_CODE = 801;
  private final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
  private InventoryDbHelper inventoryDbHelper;

  @Override
  public boolean onCreate() {
    inventoryDbHelper = new InventoryDbHelper(getContext());
    uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS, PRODUCTS_LIST_URI_CODE);
    uriMatcher.addURI(InventoryContract.CONTENT_AUTHORITY, InventoryContract.PATH_PRODUCTS + "/#", PRODUCT_ITEM_URI_CODE);

    return true;
  }

  @Nullable
  @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs,
      @Nullable String sortOrder) {

    SQLiteDatabase database = inventoryDbHelper.getReadableDatabase();

    Cursor cursor;

    int match = uriMatcher.match(uri);
    switch (match) {
      case PRODUCTS_LIST_URI_CODE:
        cursor = database.query(InventoryContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        break;
      case PRODUCT_ITEM_URI_CODE:
        selection = InventoryContract.ProductEntry._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};

        cursor = database.query(InventoryContract.ProductEntry.TABLE_NAME, projection, selection, selectionArgs,
            null, null, sortOrder);
        break;
      default:
        throw new IllegalArgumentException("Cannot query unknown URI " + uri);
    }

    if (getContext() != null) {
      cursor.setNotificationUri(getContext().getContentResolver(), uri);
    }

    return cursor;
  }

  @Nullable
  @Override
  public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final int match = uriMatcher.match(uri);
    switch (match) {
      case PRODUCTS_LIST_URI_CODE:
        return insertProduct(uri, values);
      default:
        throw new IllegalArgumentException("Insertion is not supported for " + uri);
    }
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
    final int match = uriMatcher.match(uri);
    switch (match) {
      case PRODUCTS_LIST_URI_CODE:
        return updateProduct(uri, values, selection, selectionArgs);
      case PRODUCT_ITEM_URI_CODE:
        selection = InventoryContract.ProductEntry._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        return updateProduct(uri, values, selection, selectionArgs);
      default:
        throw new IllegalArgumentException("Update is not supported for " + uri);
    }
  }

  @Override
  public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
    SQLiteDatabase database = inventoryDbHelper.getWritableDatabase();

    int rowsDeleted;

    final int match = uriMatcher.match(uri);
    switch (match) {
      case PRODUCTS_LIST_URI_CODE:
        rowsDeleted = database.delete(InventoryContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
        break;
      case PRODUCT_ITEM_URI_CODE:
        selection = InventoryContract.ProductEntry._ID + "=?";
        selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
        rowsDeleted = database.delete(InventoryContract.ProductEntry.TABLE_NAME, selection, selectionArgs);
        break;

      default:
        throw new IllegalArgumentException("Deletion is not supported for " + uri);
    }
    if (rowsDeleted > 0) {
      if (getContext() != null) {
        getContext().getContentResolver().notifyChange(uri, null);
      }
    }

    return rowsDeleted;
  }

  @Nullable
  @Override
  public String getType(@NonNull Uri uri) {
    final int match = uriMatcher.match(uri);
    switch (match) {
      case PRODUCT_ITEM_URI_CODE:
        return InventoryContract.ProductEntry.CONTENT_ITEM_TYPE;
      case PRODUCTS_LIST_URI_CODE:
        return InventoryContract.ProductEntry.CONTENT_LIST_TYPE;
      default:
        throw new IllegalArgumentException("Unknown URI " + uri + " with match " + match);
    }
  }

  private Uri insertProduct(Uri uri, ContentValues values) {
    SQLiteDatabase database = inventoryDbHelper.getWritableDatabase();

    long id = database.insert(InventoryContract.ProductEntry.TABLE_NAME, null, values);

    if (id == -1) {
      return null;
    }

    if (getContext() != null) {
      getContext().getContentResolver().notifyChange(uri, null);
    }

    return ContentUris.withAppendedId(uri, id);
  }

  private int updateProduct(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
    SQLiteDatabase database = inventoryDbHelper.getWritableDatabase();

    int rowsUpdated = database.update(InventoryContract.ProductEntry.TABLE_NAME, values, selection, selectionArgs);

    if (rowsUpdated > 0) {
      if (getContext() != null) { getContext().getContentResolver().notifyChange(uri, null); }
    }
    return rowsUpdated;

  }
}
