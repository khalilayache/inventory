package com.khalilayache.inventory.ui;

import static android.view.View.GONE;
import static android.view.View.OnClickListener;
import static android.view.View.VISIBLE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_EMAIL;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_SUPPLIER_PHONE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.CONTENT_URI;
import static com.khalilayache.inventory.utils.AlertUtils.showAlertDialog;
import static com.khalilayache.inventory.utils.AlertUtils.showToast;
import static com.khalilayache.inventory.utils.Constants.EDIT_PRODUCT_LOADER_CODE;
import static com.khalilayache.inventory.utils.Constants.PHOTO_INTENT_CODE;
import static com.khalilayache.inventory.utils.Constants.STORAGE_REQUEST_CODE;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

import android.Manifest;
import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.khalilayache.inventory.R;
import com.khalilayache.inventory.model.Product;
import com.khalilayache.inventory.utils.AndroidUtils;
import com.khalilayache.inventory.utils.ProductUtils;

public class DetailsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

  EditText nameEditText;
  EditText descriptionEditText;
  EditText priceEditText;
  EditText quantityEditText;
  EditText supplierNameEditText;
  EditText supplierEmailEditText;
  EditText supplierPhoneEditText;
  ImageView photoImageView;
  RelativeLayout photoContainer;
  LinearLayout orderMoreContainer;
  private Uri photoUri = null;
  private Uri productUri = null;
  private Product product = null;

  public static Intent createIntentNewProduct(Context context) {
    return new Intent(context, DetailsActivity.class);
  }

  public static Intent createIntentEditProduct(Context context, Uri productUri) {
    Intent intent = new Intent(context, DetailsActivity.class);

    intent.setData(productUri);

    return intent;
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_details);

    productUri = getIntent().getData();

    initActivity();
    initListeners();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu_details, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_save:
        saveProduct();
        return true;
      case R.id.action_delete:
        showDeleteConfirmation();
        return true;
      case android.R.id.home:
        showUnsavedChanges();
        return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onPrepareOptionsMenu(Menu menu) {
    super.onPrepareOptionsMenu(menu);
    if (productUri == null) {
      MenuItem menuItem = menu.findItem(R.id.action_delete);
      menuItem.setVisible(false);
    }
    return true;
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode == STORAGE_REQUEST_CODE) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        openImageSelector();
      } else {
        boolean showRationale = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0]);

        if (!showRationale) {
          showStorageRationaleSnackBar();
        } else {
          showStoragePermissionDeniedSnackBar();
        }
      }
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == PHOTO_INTENT_CODE && resultCode == Activity.RESULT_OK) {
      if (data != null) {
        photoUri = data.getData();

        photoImageView.setImageURI(photoUri);
        photoImageView.invalidate();
      }
    } else {
      showToast(this, getString(R.string.action_canceled));
    }

  }

  @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this,
        productUri,
        null,
        null,
        null,
        null);
  }

  @Override
  public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
    if (cursor == null || cursor.getCount() < 1) {
      return;
    }

    if (cursor.moveToFirst()) {
      product = getProductFromCursor(cursor);

      String placeholderUri = AndroidUtils.getUriStringOfDrawable(R.drawable.item_list_placeholder, this);

      if (product.getPhoto().equalsIgnoreCase(placeholderUri)) {
        photoUri = Uri.parse(AndroidUtils.getUriStringOfDrawable(R.drawable.photo_placeholder, this));
      } else {
        photoUri = Uri.parse(product.getPhoto());
      }

      nameEditText.setText(product.getName());

      descriptionEditText.setText(product.getDescription());
      priceEditText.setText(String.format(Locale.getDefault(), getString(R.string.price_format), product.getPrice()));

      quantityEditText.setText(String.format(Locale.getDefault(), getString(R.string.quantity_format), product.getQuantity()));
      photoImageView.setImageURI(photoUri);

      supplierNameEditText.setText(product.getSupplierName());
      supplierEmailEditText.setText(product.getSupplierEmail());
      supplierPhoneEditText.setText(product.getSupplierPhone());
    }
  }

  @Override
  public void onLoaderReset(Loader<Cursor> loader) {
    nameEditText.setText("");
    descriptionEditText.setText("");
    priceEditText.setText("");
    quantityEditText.setText("");
    photoImageView.setImageResource(R.drawable.photo_placeholder);
    supplierNameEditText.setText("");
    supplierEmailEditText.setText("");
    supplierPhoneEditText.setText("");
  }

  @Override
  public void onBackPressed() {
    showUnsavedChanges();
  }

  private void initActivity() {
    initActionBar();
    initViews();

    if (productUri != null) {
      orderMoreContainer.setVisibility(VISIBLE);

      getLoaderManager().initLoader(EDIT_PRODUCT_LOADER_CODE, null, this);
    } else {
      orderMoreContainer.setVisibility(GONE);
    }
  }

  private void initActionBar() {
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);

      if (productUri != null) {
        getSupportActionBar().setTitle(R.string.edit_product);
      } else {
        getSupportActionBar().setTitle(R.string.add_new_product);
      }
    }
  }

  private void initViews() {
    nameEditText = findViewById(R.id.product_name);
    descriptionEditText = findViewById(R.id.product_description);
    priceEditText = findViewById(R.id.product_price);
    quantityEditText = findViewById(R.id.product_quantity);
    supplierNameEditText = findViewById(R.id.supplier_name);
    supplierEmailEditText = findViewById(R.id.supplier_email);
    supplierPhoneEditText = findViewById(R.id.supplier_phone);
    photoImageView = findViewById(R.id.product_photo);
    photoContainer = findViewById(R.id.photo_container);
    orderMoreContainer = findViewById(R.id.order_more_container);
  }

  private void initListeners() {
    photoContainer.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkReadStoragePermission();
      }
    });

    orderMoreContainer.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkHowOrderMore();
      }
    });
  }

  private void checkReadStoragePermission() {
    if (ContextCompat.checkSelfPermission(this,
        Manifest.permission.READ_EXTERNAL_STORAGE)
        != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(this,
          new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_REQUEST_CODE);
      return;
    }
    openImageSelector();
  }

  private void openImageSelector() {
    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT)
        .addCategory(Intent.CATEGORY_OPENABLE)
        .setType(getString(R.string.intent_image_type));
    startActivityForResult(Intent.createChooser(intent, getString(R.string.select_image)), PHOTO_INTENT_CODE);
  }

  private void showStorageRationaleSnackBar() {
    Snackbar.make(findViewById(android.R.id.content), R.string.storage_permission_not_ask_true,
        Snackbar.LENGTH_SHORT)
        .setAction(getString(R.string.settings), new OnClickListener() {
          @Override
          public void onClick(View v) {
            Uri uri = Uri.fromParts(getString(R.string.scheme_package), DetailsActivity.this.getPackageName(), null);
            startActivity(new Intent().setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(uri));
          }
        })
        .show();
  }

  private void showStoragePermissionDeniedSnackBar() {
    Snackbar.make(findViewById(android.R.id.content), R.string.storage_permission_not_granted,
        Snackbar.LENGTH_SHORT)
        .setAction(getString(R.string.retry), new OnClickListener() {
          @Override
          public void onClick(View v) {
            checkReadStoragePermission();
          }
        })
        .show();
  }

  private void saveProduct() {
    ArrayList<String> errorsList = ProductUtils.productValidation(getProduct(), this);

    if (errorsList.size() == 0) {
      if (hasDiffOrNewProduct()) {
        if (product != null) {
          updateProduct();
        } else {
          saveNewProduct();
          finish();
        }
      } else {
        showToast(this, getString(R.string.no_changes_made));
        finish();
      }
    } else {
      showProductInputErrors(errorsList);
    }
  }

  private void updateProduct() {
    ContentValues contentValues = ProductUtils.getContentValuesFromProduct(getProduct());

    long id = ContentUris.parseId(productUri);

    Uri updateURI = ContentUris.withAppendedId(CONTENT_URI, id);
    int rowUpdated = getContentResolver().update(updateURI, contentValues, null, null);
    if (rowUpdated == 1) {
      showToast(this, getString(R.string.product_updated));
      finish();
    } else { showToast(this, getString(R.string.product_not_updated));}
  }

  private void saveNewProduct() {
    ContentValues values = ProductUtils.getContentValuesFromProduct(getProduct());

    Long newProductWasAdded = ContentUris.parseId(getContentResolver().insert(CONTENT_URI, values));

    if (newProductWasAdded != -1) {
      showToast(this, getString(R.string.new_add_success));
    } else {
      showToast(this, getString(R.string.new_add_error));
    }
  }

  private void showDeleteConfirmation() {
    AlertDialog dialog = new AlertDialog.Builder(this)
        .setMessage(R.string.want_delete_item)
        .setPositiveButton(R.string.yes, clickDeleteConfirm())
        .setNegativeButton(R.string.no, clickCancel())
        .create();
    dialog.show();
  }

  private void showUnsavedChanges() {
    if (hasDiffOrNewProduct()) {
      AlertDialog dialog = new AlertDialog.Builder(this)
          .setMessage(R.string.want_discard_changes)
          .setPositiveButton(R.string.yes, clickDiscardChanges())
          .setNegativeButton(R.string.no, clickCancel())
          .create();
      dialog.show();
    } else {
      super.onBackPressed();
    }
  }

  private void showProductInputErrors(ArrayList<String> errorsList) {
    String errorMessage = "";
    for (int i = 0; i < errorsList.size(); i++) {
      if (i == 0) {
        errorMessage = getString(R.string.invalid_inputs) + errorsList.get(i);
      } else {
        errorMessage = errorMessage.concat("\n" + errorsList.get(i));
      }
    }
    showToast(this, errorMessage);
  }

  private void checkHowOrderMore() {
    Product contactProduct = getProduct();

    if (!contactProduct.getSupplierPhone().isEmpty() && !contactProduct.getSupplierEmail().isEmpty()) {
      showAlertDialog(this,
          getString(R.string.order_more_by_phone_or_email),
          getString(R.string.email),
          getString(R.string.call),
          getString(R.string.cancel),
          clickOrderEmail(),
          clickOrderCall(),
          clickCancel());
    } else if (!contactProduct.getSupplierEmail().isEmpty()) {
      showAlertDialog(this,
          getString(R.string.order_more_by_email),
          getString(R.string.yes),
          getString(R.string.no),
          clickOrderEmail(),
          clickCancel());
    } else if (!contactProduct.getSupplierPhone().isEmpty()) {
      showAlertDialog(this,
          getString(R.string.order_more_by_phone),
          getString(R.string.yes),
          getString(R.string.no),
          clickOrderCall(),
          clickCancel());
    } else {
      showToast(this, getString(R.string.no_supplier_contact));
    }
  }

  private void orderMoreByPhone(String phoneNumber) {
    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(getString(R.string.intent_phone_type, phoneNumber)));
    startActivity(intent);
  }

  private void orderMoreByEmail(String email) {
    Intent intent = new Intent(android.content.Intent.ACTION_SENDTO);
    intent.setType(getString(R.string.intent_email_type));
    intent.setData(Uri.parse(getString(R.string.intent_email_mail_to, email)));
    intent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.email_subject, getProduct().getName()));
    String message = getString(
        R.string.email_message,
        getProduct().getSupplierName(),
        getRandomIntNumber(),
        getProduct().getName());
    intent.putExtra(android.content.Intent.EXTRA_TEXT, message);
    startActivity(intent);
  }

  private boolean hasDiffOrNewProduct() {
    String itemPlaceholderUri = AndroidUtils.getUriStringOfDrawable(R.drawable.item_list_placeholder, this);

    if (product == null) {
      Product newProduct = getProduct();

      return !(newProduct.getName().isEmpty() &&
          newProduct.getDescription().isEmpty() &&
          newProduct.getPrice() == 0.0 &&
          newProduct.getQuantity() == 0 &&
          newProduct.getPhoto().equals(itemPlaceholderUri) &&
          newProduct.getSupplierName().isEmpty() &&
          newProduct.getSupplierEmail().isEmpty() &&
          newProduct.getSupplierPhone().isEmpty());
    }

    Product diffProduct = getProduct();
    String placeholderUri = AndroidUtils.getUriStringOfDrawable(R.drawable.photo_placeholder, this);

    return !diffProduct.getName().equals(product.getName()) ||
        !diffProduct.getDescription().equals(product.getDescription()) ||
        !diffProduct.getPrice().equals(product.getPrice()) ||
        !diffProduct.getQuantity().equals(product.getQuantity()) ||
        (!(diffProduct.getPhoto().equals(placeholderUri) && product.getPhoto().equals(itemPlaceholderUri)) && !diffProduct
            .getPhoto().equals(product.getPhoto())) ||
        !diffProduct.getSupplierName().equals(product.getSupplierName()) ||
        !diffProduct.getSupplierEmail().equals(product.getSupplierEmail()) ||
        !diffProduct.getSupplierPhone().equals(product.getSupplierPhone());

  }

  private int getRandomIntNumber() {
    return new Random().nextInt(100);
  }

  private DialogInterface.OnClickListener clickOrderCall() {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        String phoneNumber = getProduct().getSupplierPhone().trim();

        if (Patterns.PHONE.matcher(phoneNumber).matches()) {
          orderMoreByPhone(phoneNumber);
        } else {
          showToast(DetailsActivity.this, getString(R.string.invalid_number));
        }
      }
    };
  }

  private DialogInterface.OnClickListener clickOrderEmail() {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        String email = getProduct().getSupplierEmail().trim();

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
          orderMoreByEmail(email);
        } else {
          showToast(DetailsActivity.this, getString(R.string.invalid_email));
        }
      }
    };
  }

  private DialogInterface.OnClickListener clickDiscardChanges() {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        DetailsActivity.super.onBackPressed();
      }
    };
  }

  private DialogInterface.OnClickListener clickCancel() {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (dialog != null) {
          dialog.dismiss();
        }
      }
    };
  }

  private DialogInterface.OnClickListener clickDeleteConfirm() {
    return new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        if (productUri != null) {
          int rowsDeleted = getContentResolver().delete(productUri, null, null);

          if (rowsDeleted == 1) {
            showToast(DetailsActivity.this, getString(R.string.delete_success));
            finish();
          } else {
            showToast(DetailsActivity.this, getString(R.string.delete_error));
          }
        }
      }
    };
  }

  private Product getProduct() {
    if (photoUri == null) {
      photoUri = Uri.parse(AndroidUtils.getUriStringOfDrawable(R.drawable.item_list_placeholder, this));
    }

    String name = nameEditText.getText().toString();
    String description = descriptionEditText.getText().toString();
    String photo = String.valueOf(photoUri);
    String supplierName = supplierNameEditText.getText().toString();
    String supplierEmail = supplierEmailEditText.getText().toString();
    String supplierPhone = supplierPhoneEditText.getText().toString();

    String priceString = priceEditText.getText().toString();
    String quantityString = quantityEditText.getText().toString();

    Double price = 0.0;
    Integer quantity = 0;

    if (!priceString.isEmpty()) {
      if (priceString.contains(",")) {
        priceString = priceString.replace(",", ".");
      }

      price = Double.parseDouble(priceString);
    }

    if (!quantityString.isEmpty()) {
      quantity = Integer.parseInt(quantityString);
    }

    return new Product(name, description, price, quantity, photo, supplierName, supplierEmail, supplierPhone);
  }

  private Product getProductFromCursor(Cursor cursor) {
    return new Product(
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
        Double.parseDouble(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE))),
        Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY))),
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO)),
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUPPLIER_NAME)),
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUPPLIER_EMAIL)),
        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUPPLIER_PHONE))
    );
  }
}
