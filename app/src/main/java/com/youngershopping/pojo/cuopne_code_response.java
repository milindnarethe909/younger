package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class cuopne_code_response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("data")
    List<cuopne_code_Res> list;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<cuopne_code_Res> getList() {
        return list;
    }

    public void setList(List<cuopne_code_Res> list) {
        this.list = list;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
