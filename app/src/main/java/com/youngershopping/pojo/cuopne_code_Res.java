package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class cuopne_code_Res {
    @SerializedName("code")
    String code;

    @SerializedName("type")
    String type;

    @SerializedName("price")
    String price;

    @SerializedName("times")
    String times;

    @SerializedName("used")
    String used;

    @SerializedName("status")
    String status;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

//{
//        "status": true,
//        "msg": "success",
//        "data": {
//        "id": "9",
//        "code": "testtest",
//        "type": "1",
//        "price": "20",
//        "times": null,
//        "used": "0",
//        "status": "1",
//        "start_date": "2020-01-29",
//        "end_date": "2020-02-29"
//        }
//        }