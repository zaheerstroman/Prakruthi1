package com.prakruthi.ui.ui.wishlist;

public class WishListModal {

    private int wishlistId;

    private String name;

    private String description;

    private String customerPrice;

    private String color;

    private String size;

    private String type;

    private String date;

    public WishListModal(int wishlistId, String name, String description, String customerPrice, String color, String size, String type, String date) {
        this.wishlistId = wishlistId;
        this.name = name;
        this.description = description;
        this.customerPrice = customerPrice;
        this.color = color;
        this.size = size;
        this.type = type;
        this.date = date;
    }

    public int getWishlistId() {
        return wishlistId;
    }

    public void setWishlistId(int wishlistId) {
        this.wishlistId = wishlistId;
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

    public String getCustomerPrice() {
        return customerPrice;
    }

    public void setCustomerPrice(String customerPrice) {
        this.customerPrice = customerPrice;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}