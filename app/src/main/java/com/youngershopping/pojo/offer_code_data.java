package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class offer_code_data {
    @SerializedName("code")
    String code;

    @SerializedName("price")
    String price;

    @SerializedName("start_date")
    String start_date;

    @SerializedName("end_date")
    String end_date;

    @SerializedName("type")
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
//{"status":true,"msg":"valid","data":[{"id":"9","code":"testtest","type":"1","price":"20","times":null,"used":"0","status":"1","start_date":"2020-01-29","end_date":"2020-02-29"},{"id":"10","code":"pp","type":"1","price":"100","times":null,"used":"0","status":"1","start_date":"2020-02-12","end_date":"2020-02-26"},{"id":"11","code":"1111Test","type":"1","price":"100","times":"100 rupees","used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-24"},{"id":"12","code":"antest","type":"1","price":"50","times":"15","used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-29"},{"id":"13","code":"anujatest","type":"1","price":"70","times":null,"used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-29"},{"id":"14","code":"100","type":"0","price":"50","times":null,"used":"0","status":"1","start_date":"2020-02-18","end_date":"2020-02-27"},{"id":"15","code":"101","type":"1","price":"10000","times":null,"used":"0","status":"1","start_date":"2020-02-18","end_date":"2020-02-25"}]}