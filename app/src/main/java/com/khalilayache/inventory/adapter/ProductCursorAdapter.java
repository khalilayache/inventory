package com.khalilayache.inventory.adapter;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import com.khalilayache.inventory.R;

public class ProductCursorAdapter extends CursorAdapter {

  public ProductCursorAdapter(Context context) {
    super(context, null, 0);
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
  }

  @Override
  public void bindView(View view, Context context, Cursor cursor) {
    CircleImageView image = view.findViewById(R.id.item_image);
    TextView name = view.findViewById(R.id.item_name);
    TextView description = view.findViewById(R.id.item_description);
    TextView stock = view.findViewById(R.id.item_stock);
    TextView price = view.findViewById(R.id.item_price);
    ImageView buy = view.findViewById(R.id.item_buy);

    name.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
    description.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
    stock.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY)));
    price.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)));
    image.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO))));
  }
}
