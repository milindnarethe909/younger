package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class Add_To_Cart_Response {

//    {"status":true,"msg":"success","data":"inserted"}

    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("data")
    String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
