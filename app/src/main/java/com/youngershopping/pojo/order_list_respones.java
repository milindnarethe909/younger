package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class order_list_respones {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    List<order_data_list_response> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<order_data_list_response> getList() {
        return list;
    }

    public void setList(List<order_data_list_response> list) {
        this.list = list;
    }
}
