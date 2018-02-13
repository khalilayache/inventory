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
  private String supplier_name;

  //Email of product supplier.
  private String supplier_email;

  //Phone of product supplier.
  private String supplier_phone;

  private Product() {}

  public Product(String name, String description, Double price, Integer quantity, String photo, String supplier_name, String
      supplier_email, String supplier_phone) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.quantity = quantity;
    this.photo = photo;
    this.supplier_name = supplier_name;
    this.supplier_email = supplier_email;
    this.supplier_phone = supplier_phone;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Integer getQuantity() {
    return quantity;
  }

  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  public String getPhoto() {
    return photo;
  }

  public void setPhoto(String photo) {
    this.photo = photo;
  }

  public String getSupplier_name() {
    return supplier_name;
  }

  public void setSupplier_name(String supplier_name) {
    this.supplier_name = supplier_name;
  }

  public String getSupplier_email() {
    return supplier_email;
  }

  public void setSupplier_email(String supplier_email) {
    this.supplier_email = supplier_email;
  }

  public String getSupplier_phone() {
    return supplier_phone;
  }

  public void setSupplier_phone(String supplier_phone) {
    this.supplier_phone = supplier_phone;
  }
}
