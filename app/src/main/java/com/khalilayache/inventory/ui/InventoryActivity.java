package com.khalilayache.inventory.ui;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE;

import java.util.ArrayList;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.data.InventoryDbManager;
import com.khalilayache.inventory.model.Product;

public class InventoryActivity extends AppCompatActivity {

  private static final String LOG_TAG = "InventoryActivity";
  private InventoryDbManager dbManager = new InventoryDbManager(this);

  public static Intent createIntent(Context context) {
    return new Intent(context, InventoryActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_inventory);

    initListeners();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu options from the res/menu/menu_catalog.xml file.
    // This adds menu items to the app bar.
    getMenuInflater().inflate(R.menu.menu_inventory, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // User clicked on a menu option in the app bar overflow menu
    switch (item.getItemId()) {
      // Respond to a click on the "Insert dummy data" menu option
      case R.id.action_insert_dummy_data:
        if (dbManager.insertProduct(getDummyProduct())) {
          Toast.makeText(this, "Dummy Product has been added successfully", Toast.LENGTH_SHORT).show();
          displayAllDatabaseInfo();
        } else {
          Toast.makeText(this, "Error while Dummy Product has been added", Toast.LENGTH_SHORT).show();
        }
        return true;
      // Respond to a click on the "Delete all entries" menu option
      case R.id.action_delete_all_entries:
        if (dbManager.deleteAllProducts()) {
          Toast.makeText(this, "All Products has been deleted successfully", Toast.LENGTH_SHORT).show();
          displayAllDatabaseInfo();
        } else {
          Toast.makeText(this, "Error while all products has been deleted", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  protected void onStart() {
    super.onStart();
    displayAllDatabaseInfo();
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

  private Product getDummyProduct() {

    // Get Uri for example photo from drawable resource
    Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
        getResources().getResourcePackageName(R.drawable.notebook_example) + '/' +
        getResources().getResourceTypeName(R.drawable.notebook_example) + '/' +
        getResources().getResourceEntryName(R.drawable.notebook_example));

    Log.i(LOG_TAG, "Example photo uri: " + String.valueOf(imageUri));

    String name = getString(R.string.product_example_name);
    String description = getString(R.string.product_example_description);
    Double price = Double.parseDouble(getString(R.string.product_example_price));
    Integer quantity = Integer.parseInt(getString(R.string.product_example_quantity));
    String photo = String.valueOf(imageUri);
    String supplier_name = getString(R.string.product_example_supplier_name);
    String supplier_email = getString(R.string.product_example_supplier_email);
    String supplier_phone = getString(R.string.product_example_supplier_phone);

    return new Product(name, description, price, quantity, photo, supplier_name, supplier_email, supplier_phone);
  }

  private void displayAllDatabaseInfo() {
    displayDatabaseInfo(dbManager.selectAllFromPets());
  }

  private void displayDatabaseInfo(ArrayList<Product> products) {

    TextView textProductInfo = findViewById(R.id.textProductInfo);

    textProductInfo.setText(getString(R.string.product_info_header, products.size()));

    for (Product product : products) {
      textProductInfo.append(COLUMN_NAME.toUpperCase() + "\t-\t" + product.getName() + "\n" +
          COLUMN_DESCRIPTION.toUpperCase() + "\t-\t" + product.getDescription() + "\n" +
          COLUMN_PRICE.toUpperCase() + "\t-\t" + product.getPrice() + "\n" +
          COLUMN_QUANTITY.toUpperCase() + "\t-\t" + product.getQuantity() + "\n" +
          COLUMN_SUPPLIER_NAME.toUpperCase() + "\t-\t" + product.getSupplier_name() + "\n" +
          COLUMN_SUPPLIER_EMAIL.toUpperCase() + "\t-\t" + product.getSupplier_email() + "\n" +
          COLUMN_SUPPLIER_PHONE.toUpperCase() + "\t-\t" + product.getSupplier_phone() + "\n\n");
    }
  }
}
