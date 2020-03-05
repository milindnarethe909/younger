package com.youngershopping.pojo;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class list_of_cart {
    @SerializedName("cart_id")
    String cart_id;

    @SerializedName("user_id")
    String user_id;

    @SerializedName("qty")
    String qty;

    @SerializedName("size")
    String size;

    @SerializedName("color")
    String color;

    @SerializedName("stock")
    String stock;

    @SerializedName("price")
    String price;

    @SerializedName("id")
    String id;

    @SerializedName("name")
    String name;

    @SerializedName("photo")
    String photo;

    @SerializedName("cprice")
    String cprice;
    @SerializedName("product_stock")
    String product_stock;

    @SerializedName("type")
    String type;

    @SerializedName("measure")
    String measure;

    @SerializedName("license_qty")
    String license_qty;

    @SerializedName("gst")
    String gst;

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCprice() {
        return cprice;
    }

    public void setCprice(String cprice) {
        this.cprice = cprice;
    }

    public String getProduct_stock() {
        return product_stock;
    }

    public void setProduct_stock(String product_stock) {
        this.product_stock = product_stock;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getLicense_qty() {
        return license_qty;
    }

    public void setLicense_qty(String license_qty) {
        this.license_qty = license_qty;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString();
    }
}


// "cart_id": "1",
//         "user_id": "1",
//         "qty": "2",
//         "size": "M",
//         "color": "#ff9904",
//         "stock": "10",
//         "price": "599.00",
//         "id": "136",
//         "name": "Full sleeve",
//         "photo": "",
//         "cprice": "599.00",
//         "product_stock": "10",
//         "type": "0",
//         "file": "0",
//         "link": "0",
//         "license": "0",
//         "license_qty": "0",
//         "measure": "units",
//         "created_at": "2020-02-07 12:05:00"