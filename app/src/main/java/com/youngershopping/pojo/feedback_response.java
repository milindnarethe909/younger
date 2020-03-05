package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class feedback_response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("save")
    String save;


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

    public String getSave() {
        return save;
    }

    public void setSave(String save) {
        this.save = save;
    }
}

