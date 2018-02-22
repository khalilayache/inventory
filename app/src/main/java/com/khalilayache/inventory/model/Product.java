package com.khalilayache.inventory.model;

public class Product {

  private String name;
  private String description;
  private Double price;
  private Integer quantity;
  private String photo;
  private String supplierName;
  private String supplierEmail;
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
