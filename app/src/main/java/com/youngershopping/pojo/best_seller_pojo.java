package com.youngershopping.pojo;

public class best_seller_pojo {

    String category_id;
    String childcategory_id;
    String cprice;
    String price;
    String product_id;
    String product_image;
    String product_name;
    String subcategory_id;
    double rating;
    int stock;

    public best_seller_pojo(String category_id, String childcategory_id, String cprice, String price, String product_id, String product_image, String product_name, String subcategory_id, double rating, int stock) {
        this.category_id = category_id;
        this.childcategory_id = childcategory_id;
        this.cprice = cprice;
        this.price = price;
        this.product_id = product_id;
        this.product_image = product_image;
        this.product_name = product_name;
        this.subcategory_id = subcategory_id;
        this.rating = rating;
        this.stock = stock;
    }

    public String getCategory_id() {
        return category_id;
    }

    public String getChildcategory_id() {
        return childcategory_id;
    }

    public String getCprice() {
        return cprice;
    }

    public String getPrice() {
        return price;
    }

    public String getProduct_id() {
        return product_id;
    }

    public String getProduct_image() {
        return product_image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getSubcategory_id() {
        return subcategory_id;
    }

    public double getRating() {
        return rating;
    }

    public int getStock() {
        return stock;
    }
}
