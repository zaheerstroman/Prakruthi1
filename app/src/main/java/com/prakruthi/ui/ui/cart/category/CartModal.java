package com.prakruthi.ui.ui.cart.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.prakruthi.Response.GetCartDetailsResponse;

import java.util.ArrayList;

public class CartModal {

    @SerializedName("cart_data")
    @Expose
    private ArrayList<GetCartDetailsResponse.CartData> cartData;
    @SerializedName("status_code")
    @Expose
    private Boolean statusCode;
    @SerializedName("message")
    @Expose
    private String message;

    public ArrayList<GetCartDetailsResponse.CartData> getCartData() {
        return cartData;
    }

    public void setCartData(ArrayList<GetCartDetailsResponse.CartData> cartData) {
        this.cartData = cartData;
    }

    public Boolean getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Boolean statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public class CartData {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("user_id")
        @Expose
        private Integer userId;
        @SerializedName("product_id")
        @Expose
        private Integer productId;
        @SerializedName("quantity")
        @Expose
        private Integer quantity;
        @SerializedName("logged_date")
        @Expose
        private String loggedDate;
        @SerializedName("created_by")
        @Expose
        private Integer createdBy;
        @SerializedName("updated_by")
        @Expose
//        private Object updatedBy;
        private String updatedBy;

        @SerializedName("updated_date")
        @Expose
//        private Object updatedDate;
        private String updatedDate;

        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("customer_price")
        @Expose
        private String customerPrice;
        @SerializedName("color")
        @Expose
        private String color;
        @SerializedName("size")
        @Expose
        private String size;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("attachment")
        @Expose
        private String attachment;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getUserId() {
            return userId;
        }

        public void setUserId(Integer userId) {
            this.userId = userId;
        }

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public String getLoggedDate() {
            return loggedDate;
        }

        public void setLoggedDate(String loggedDate) {
            this.loggedDate = loggedDate;
        }

        public Integer getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(Integer createdBy) {
            this.createdBy = createdBy;
        }

        public String getUpdatedBy() {
            return updatedBy;
        }

        public void setUpdatedBy(String updatedBy) {
            this.updatedBy = updatedBy;
        }

        public String getUpdatedDate() {
            return updatedDate;
        }

        public void setUpdatedDate(String updatedDate) {
            this.updatedDate = updatedDate;
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

        public String getAttachment() {
            return attachment;
        }

        public void setAttachment(String attachment) {
            this.attachment = attachment;
        }
    }


}
