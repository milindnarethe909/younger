package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class Wishaddresponse {
    @SerializedName("status")
    String status;
    @SerializedName("msg")
    String msg;
    @SerializedName("add")
    String add;

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

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }
}
