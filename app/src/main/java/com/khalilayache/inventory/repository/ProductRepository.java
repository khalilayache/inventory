package com.khalilayache.inventory.repository;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;

import com.khalilayache.inventory.data.ProductFactory;
import com.khalilayache.inventory.model.Product;

public class ProductRepository {
  private Context context;

  public ProductRepository(Context context) {
    this.context = context;
  }

  public Product getRandomProduct() {
    ArrayList<Product> productsList = ProductFactory.createProductList(context);

    return productsList.get(new Random().nextInt(productsList.size()));
  }
}
