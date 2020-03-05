package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class serach_details_gson {
    @SerializedName("status")
    String status  ;

    @SerializedName("msg")
    String msg;

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
