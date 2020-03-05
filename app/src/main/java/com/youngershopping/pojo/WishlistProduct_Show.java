package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class WishlistProduct_Show {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
