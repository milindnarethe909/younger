package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class Get_Wallet_Response {
    @SerializedName("status")
    String status;

    @SerializedName("msg")
    String msg;

    @SerializedName("amount")
    String amount;

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
