package com.khalilayache.inventory.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.model.Product;
import com.khalilayache.inventory.ui.base.BaseActivity;
import com.khalilayache.inventory.utils.AndroidUtils;

public class DetailsActivity extends BaseActivity {

  private Uri photoUri = null;

  public static Intent createIntent(Context context) {
    return new Intent(context, DetailsActivity.class);
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    initListeners();
    initActivity();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu options from the res/menu/menu_catalog.xml file.
    // This adds menu items to the app bar.
    getMenuInflater().inflate(R.menu.menu_details, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // User clicked on a menu option in the app bar overflow menu
    switch (item.getItemId()) {
      // Respond to a click on the "Save Product" menu option
      case R.id.action_save:
        saveProduct();
        return true;
      // Respond to a click on the "Delete" menu option
      case R.id.action_delete:
        deleteProduct();
        return true;
      case android.R.id.home:
        super.onBackPressed();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void deleteProduct() {
    //    boolean productWasDeleted = true;
    //    //dbManager.deleteAllProducts();
    //
    //    if (productWasDeleted) {
    //      showToast(getString(R.string.products_delete_sucess));
    //    } else {
    //      showToast(getString(R.string.products_delete_error));
    //    }
  }

  private void saveProduct() {
    //    try {
    //      boolean productWasAdded = true;
    //      //dbManager.insertProduct(getProduct());
    //
    //      if (productWasAdded) {
    //        showToast(getString(R.string.product_add_success));
    //        finish();
    //      } else {
    //        showToast(getString(R.string.product_add_error));
    //      }
    //    } catch (IllegalArgumentException e) {
    //      showToast(e.getMessage());
    //    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == STORAGE_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        openImageSelector();
      } else {
        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);

        if (!showRationale) {
          showRationaleSnackBar();
        } else {
          showPermissionDeniedSnackBar();
        }
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PHOTO_INTENT_CODE && resultCode == Activity.RESULT_OK) {
      if (data != null) {
        photoUri = data.getData();

        ImageView photoView = findViewById(R.id.product_photo);
        photoView.setImageURI(photoUri);
        photoView.invalidate();
      }
    } else {
      showToast(getString(R.string.action_canceled));
    }

  }

  // Method to initialize all listeners there is on Activity
  private void initListeners() {
    RelativeLayout photoContainer = findViewById(R.id.photo_container);

    photoContainer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        checkReadStoragePermission();
      }
    });
  }

  // Method to initialize some items that need a special handling on Activity
  private void initActivity() {

    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
  }

  // Method to check if Read Storage Permission was granted
  public void checkReadStoragePermission() {
    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
      return;
    }
    openImageSelector();
  }

  // Open the Image selector intent to get a product image
  private void openImageSelector() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT)
        .addCategory(Intent.CATEGORY_OPENABLE)
        .setType(getString(R.string.intent_image_type));
    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), PHOTO_INTENT_CODE);
  }

  // Get all fields and return a Product
  private Product getProduct() {
    if (photoUri == null) {
      photoUri = Uri.parse(AndroidUtils.getUriStringOfDrawable(R.drawable.item_list_placeholder, this));
    }

    EditText productName = findViewById(R.id.product_name);
    EditText productDescription = findViewById(R.id.product_description);
    EditText productPrice = findViewById(R.id.product_price);
    EditText productQuantity = findViewById(R.id.product_quantity);
    EditText productSupplierName = findViewById(R.id.supplier_name);
    EditText productSupplierEmail = findViewById(R.id.supplier_email);
    EditText productSupplierPhone = findViewById(R.id.supplier_phone);

    String name = productName.getText().toString();
    String description = productDescription.getText().toString();
    String photo = String.valueOf(photoUri);
    String supplierName = productSupplierName.getText().toString();
    String supplierEmail = productSupplierEmail.getText().toString();
    String supplierPhone = productSupplierPhone.getText().toString();

    String priceString = productPrice.getText().toString();
    String quantityString = productQuantity.getText().toString();

    Double price = 0.0;
    Integer quantity = 0;

    if (!priceString.isEmpty()) {
      price = Double.parseDouble(priceString);
    }

    if (!quantityString.isEmpty()) {
      quantity = Integer.parseInt(quantityString);
    }

    return new Product(name, description, price, quantity, photo, supplierName, supplierEmail, supplierPhone);
  }

  private void showRationaleSnackBar() {
    Snackbar.make(findViewById(android.R.id.content), R.string.storage_permission_not_ask_true,
        Snackbar.LENGTH_SHORT)
        .setAction(getString(R.string.settings), new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            Uri uri = Uri.fromParts(getString(R.string.scheme_package), DetailsActivity.this.getPackageName(), null);
            startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri));
          }
        })
        .show();
  }

  private void showPermissionDeniedSnackBar() {
    Snackbar.make(findViewById(android.R.id.content), R.string.storage_permission_not_granted,
        Snackbar.LENGTH_SHORT)
        .setAction(getString(R.string.retry), new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            checkReadStoragePermission();
          }
        })
        .show();
  }
}
