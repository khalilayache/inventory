package com.khalilayache.inventory.adapter;

import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_DESCRIPTION;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_NAME;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PHOTO;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_PRICE;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry.COLUMN_QUANTITY;
import static com.khalilayache.inventory.data.InventoryContract.ProductEntry._ID;

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
  private SellItemListClick soldItemClick;

  public ProductCursorAdapter(Context context, SellItemListClick soldItemClick) {
    super(context, null, 0);
    this.soldItemClick = soldItemClick;
  }

  @Override
  public View newView(Context context, Cursor cursor, ViewGroup parent) {
    return LayoutInflater.from(context).inflate(R.layout.inventory_list_item, parent, false);
  }

  @Override
  public void bindView(View view, Context context, final Cursor cursor) {
    CircleImageView image = view.findViewById(R.id.item_image);
    TextView name = view.findViewById(R.id.item_name);
    TextView description = view.findViewById(R.id.item_description);
    TextView quantity = view.findViewById(R.id.item_quantity);
    TextView price = view.findViewById(R.id.item_price);
    ImageView buy = view.findViewById(R.id.item_sold);

    final int id = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
    final int quantityValue = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
    double priceValue = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE));

    name.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)));
    description.setText(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)));
    quantity.setText(String.format(context.getString(R.string.quantity_mask), quantityValue));
    price.setText(String.format(context.getString(R.string.price_mask), priceValue));
    image.setImageURI(Uri.parse(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHOTO))));

    buy.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        soldItemClick.itemClick(id, quantityValue);
      }
    });
  }

  public interface SellItemListClick {
    void itemClick(int id, int quantityInStock);
  }
}
