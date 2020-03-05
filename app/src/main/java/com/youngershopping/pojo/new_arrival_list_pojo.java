package com.youngershopping.pojo;

import androidx.annotation.NonNull;

import java.util.Comparator;

public class new_arrival_list_pojo implements Comparable  {

    String category_id;
    String childcategory_id;
    int cprice;
    int price;
    String product_id;
    String product_image;
    String product_name;
    String subcategory_id;
    double rating;
    int stock;

    public new_arrival_list_pojo(String category_id, String childcategory_id, int cprice, int price, String product_id, String product_image, String product_name, String subcategory_id, double rating, int stock) {
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

    public int getCprice() {
        return cprice;
    }

    public int getPrice() {
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

    @Override
    public int compareTo(Object o) {
        int compareage=((new_arrival_list_pojo)o).getCprice();
        /* For Ascending order*/
        return this.cprice-compareage;
    }

    @NonNull
    @Override
    public String toString() {
        return ""+cprice;
    }

    /*Comparator for sorting the list by roll no*/
    public static Comparator<new_arrival_list_pojo> StuRollno = new Comparator<new_arrival_list_pojo>() {

        public int compare(new_arrival_list_pojo s1, new_arrival_list_pojo s2) {

            int rollno1 = s1.getCprice();
            int rollno2 = s2.getCprice();


            /*For ascending order*/
            return rollno2-rollno1;

            /*For descending order*/
            //rollno2-rollno1;
        }};
}
