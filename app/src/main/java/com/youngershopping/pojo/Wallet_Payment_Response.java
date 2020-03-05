package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class Wallet_Payment_Response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;


    @SerializedName("data")
    String data;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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
}
