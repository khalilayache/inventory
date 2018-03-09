package com.khalilayache.inventory.ui;

import static com.khalilayache.inventory.adapter.ProductCursorAdapter.SellItemListClick;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.CONTENT_URI;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry._ID;
import static com.khalilayache.inventory.utils.AlertUtils.showToast;
import static com.khalilayache.inventory.utils.Constants.PRODUCT_LOADER_CODE;

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
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.adapter.ProductCursorAdapter;
import com.khalilayache.inventory.repository.ProductRepository;
import com.khalilayache.inventory.utils.ProductUtils;

public class InventoryActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, SellItemListClick {

  private ListView productsList;

  private ProductCursorAdapter productCursorAdapter;

  public static Intent createIntent(Context context) {
    return new Intent(context, InventoryActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inventory);

    initActivity();
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

  @Override
  public void itemClick(int id, int quantityInStock) {
    sellListItem(id, quantityInStock);
  }

  private void initFabListener() {
    ImageView fabImageView = findViewById(R.id.add_new_item);

    fabImageView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(DetailsActivity.createIntentNewProduct(InventoryActivity.this));
      }
    });

  }

  private void initActivity() {
    initFabListener();
    initList();
  }

  private void initList() {
    productsList = findViewById(R.id.inventory_list);
    View emptyView = findViewById(R.id.empty_view);

    productCursorAdapter = new ProductCursorAdapter(this, this);

    productsList.setEmptyView(emptyView);
    productsList.setAdapter(productCursorAdapter);

    productsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        openEditProduct(id);
      }
    });
  }

  private void openEditProduct(long id) {
    Uri productUri = ContentUris.withAppendedId(CONTENT_URI, id);
    startActivity(DetailsActivity.createIntentEditProduct(this, productUri));
  }

  private void initLoader() {
    getSupportLoaderManager().initLoader(PRODUCT_LOADER_CODE, null, this);
  }

  private void deleteAllProducts() {
    int productsWasDeleted = getContentResolver().delete(CONTENT_URI, null, null);

    if (productsWasDeleted != -1) {
      showToast(this, getString(R.string.products_delete_success));
    } else {
      showToast(this, getString(R.string.products_delete_error));
    }
  }

  private void insertDummyProduct() {
    ProductRepository repository = new ProductRepository(this);

    ContentValues values = ProductUtils.getContentValuesFromProduct(repository.getRandomProduct());

    Long dummyProductWasAdded = ContentUris.parseId(getContentResolver().insert(CONTENT_URI, values));

    if (dummyProductWasAdded != -1) {
      showToast(this, getString(R.string.dummy_add_success));
    } else {
      showToast(this, getString(R.string.dummy_add_error));
    }
  }

  private void sellListItem(int id, int quantityInStock) {
    if (quantityInStock == 0) {
      showToast(this, getString(R.string.product_out_stock));
      return;
    }

    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_QUANTITY, --quantityInStock);

    Uri updateURI = ContentUris.withAppendedId(CONTENT_URI, id);
    int rowsUpdated = getContentResolver().update(updateURI, contentValues, null, null);

    if (rowsUpdated < 1) {
      showToast(this, getString(R.string.product_sold_error));
    } else {
      showToast(this, getString(R.string.product_sold_success));
    }
  }
}
