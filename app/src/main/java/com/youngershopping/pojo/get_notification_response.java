package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class get_notification_response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("data")
    List<get_data_notification> data;

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

    public List<get_data_notification> getData() {
        return data;
    }

    public void setData(List<get_data_notification> data) {
        this.data = data;
    }
}
