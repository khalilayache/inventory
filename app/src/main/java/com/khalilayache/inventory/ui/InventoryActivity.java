package com.khalilayache.inventory.ui;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.CONTENT_URI;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry._ID;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.adapter.ProductCursorAdapter;
import com.khalilayache.inventory.model.Product;
import com.khalilayache.inventory.ui.base.BaseActivity;
import com.khalilayache.inventory.utils.ProductUtils;

public class InventoryActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor> {

  private ProductCursorAdapter productCursorAdapter = new ProductCursorAdapter(this);

  public static Intent createIntent(Context context) {
    return new Intent(context, InventoryActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inventory);

    initListeners();
    initList();
    initLoader();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu options from the res/menu/menu_inventory.xml file.
    // This adds menu items to the app bar.
    getMenuInflater().inflate(R.menu.menu_inventory, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // User clicked on a menu option in the app bar overflow menu
    switch (item.getItemId()) {
      // Respond to a click on the "Insert dummy product" menu option
      case R.id.action_insert_dummy_product:
        insertDummyProduct();
        return true;
      // Respond to a click on the "Delete all products" menu option
      case R.id.action_delete_all_products:
        deleteAllProducts();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
    //    displayAllDatabaseInfo();
  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    String[] projection = {
        _ID,
        COLUMN_NAME,
        COLUMN_DESCRIPTION,
        COLUMN_PHOTO,
        COLUMN_PRICE,
        COLUMN_QUANTITY
    };

    return new CursorLoader(this,
        CONTENT_URI,
        projection,
        null,
        null,
        null
    );
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    productCursorAdapter.swapCursor(cursor);
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    productCursorAdapter.swapCursor(null);
  }

  private void initListeners() {
    ImageView fabImageView = findViewById(R.id.add_new_item);

    fabImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(DetailsActivity.createIntent(InventoryActivity.this));
      }
    });
  }

  private void initList() {
    ListView productsList = findViewById(R.id.inventory_list);

    View emptyView = findViewById(R.id.empty_view);
    productsList.setEmptyView(emptyView);
    productsList.setAdapter(productCursorAdapter);
  }

  private void initLoader() {
    getSupportLoaderManager().initLoader(PRODUCT_LOADER_CODE, null, this);
  }

  private void deleteAllProducts() {
    //    boolean productsWasDeleted = true;
    //    //dbManager.deleteAllProducts();
    //
    //    if (productsWasDeleted) {
    //      showToast(getString(R.string.dummy_delete_success));
    //      //          displayAllDatabaseInfo();
    //    } else {
    //      showToast(getString(R.string.dummy_delete_error));
    //    }
  }

  private void insertDummyProduct() {

    ContentValues values = ProductUtils.getProductContentValues(getDummyProduct());

    Long dummyProductWasAdded = ContentUris.parseId(getContentResolver().insert(CONTENT_URI, values));

    if (dummyProductWasAdded != -1) {
      showToast(getString(R.string.dummy_add_sucess));
    } else {
      showToast(getString(R.string.dummy_add_error));
    }
  }

  private Product getDummyProduct() {

    // Get Uri for example photo from drawable resource
    Uri imageUri = Uri.parse(getUriStringOfDrawable(R.drawable.notebook_example));

    String name = getString(R.string.product_example_name);
    String description = getString(R.string.product_example_description);
    Double price = Double.parseDouble(getString(R.string.product_example_price));
    Integer quantity = Integer.parseInt(getString(R.string.product_example_quantity));
    String photo = String.valueOf(imageUri);
    String supplierName = getString(R.string.product_example_supplier_name);
    String supplierEmail = getString(R.string.product_example_supplier_email);
    String supplierPhone = getString(R.string.product_example_supplier_phone);

    return new Product(name, description, price, quantity, photo, supplierName, supplierEmail, supplierPhone);
  }
}
