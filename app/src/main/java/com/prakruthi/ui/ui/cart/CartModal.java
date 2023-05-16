package com.prakruthi.ui.ui.cart;


import java.util.ArrayList;

public class CartModal {
    private int id;
    private int productId;
    private int quantity;
    private String name;
    private String description;
    private String customerPrice;
    private String attachment;

    // Constructor
    public CartModal(int id, int productId, int quantity, String name, String description, String customerPrice, String attachment) {
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.name = name;
        this.description = description;
        this.customerPrice = customerPrice;
        this.attachment = attachment;
    }

    // Getters
    public int getId() {
        return id;
    }



    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCustomerPrice() {
        return customerPrice;
    }


    public String getAttachment() {
        return attachment;
    }
}


