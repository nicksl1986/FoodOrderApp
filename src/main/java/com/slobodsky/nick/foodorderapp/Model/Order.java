package com.slobodsky.nick.foodorderapp.Model;

public class Order {

    private String ProductID, ProductName, Quantity, Price;

    public Order() {


    }

    public Order(String productID, String productName, String quantity, String price) {

        this.ProductID = productID;

        this.ProductName = productName;

        this.Quantity = quantity;

        this.Price = price;
    }

    public String getProductID() {

        return ProductID;
    }

    public void setProductID(String productID) {

        this.ProductID = productID;
    }

    public String getProductName() {

        return ProductName;
    }

    public void setProductName(String productName) {

        this.ProductName = productName;
    }

    public String getQuantity() {

        return Quantity;
    }

    public void setQuantity(String quantity) {

        this.Quantity = quantity;
    }

    public String getPrice() {

        return Price;
    }

    public void setPrice(String price) {

        this.Price = price;
    }
}
