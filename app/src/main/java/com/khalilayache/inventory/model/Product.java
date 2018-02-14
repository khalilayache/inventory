package com.khalilayache.inventory.model;

public class Product {

  //Product's name.
  private String name;

  //Product's description.
  private String description;

  //Product's price.
  private Double price;

  //Product's quantity in stock.
  private Integer quantity;

  //Product's photo.
  private String photo;

  //Name of product supplier.
  private String supplierName;

  //Email of product supplier.
  private String supplierEmail;

  //Phone of product supplier.
  private String supplierPhone;

  private Product() {}

  public Product(String name, String description, Double price, Integer quantity, String photo, String supplierName, String
      supplierEmail, String supplierPhone) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.photo = photo;
    this.supplierName = supplierName;
    this.supplierEmail = supplierEmail;
    this.supplierPhone = supplierPhone;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Double getPrice() {
    return price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public String getPhoto() {
    return photo;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public String getSupplierEmail() {
    return supplierEmail;
  }

  public String getSupplierPhone() {
    return supplierPhone;
  }
}
