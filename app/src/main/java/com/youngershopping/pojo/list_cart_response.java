package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class list_cart_response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("amount")
    List<list_of_cart> list_of_cart;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<com.youngershopping.pojo.list_of_cart> getList_of_cart() {
        return list_of_cart;
    }

    public void setList_of_cart(List<com.youngershopping.pojo.list_of_cart> list_of_cart) {
        this.list_of_cart = list_of_cart;
    }
}
