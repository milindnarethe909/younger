package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class offer_response {
    @SerializedName("status")
    String status;

    @SerializedName("data")
    List<offer_code_data> offerCodeData;


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<offer_code_data> getOfferCodeData() {
        return offerCodeData;
    }

    public void setOfferCodeData(List<offer_code_data> offerCodeData) {
        this.offerCodeData = offerCodeData;
    }
}


//{"status":true,"msg":"valid","data":[{"id":"9","code":"testtest","type":"1","price":"20","times":null,"used":"0","status":"1","start_date":"2020-01-29","end_date":"2020-02-29"},{"id":"10","code":"pp","type":"1","price":"100","times":null,"used":"0","status":"1","start_date":"2020-02-12","end_date":"2020-02-26"},{"id":"11","code":"1111Test","type":"1","price":"100","times":"100 rupees","used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-24"},{"id":"12","code":"antest","type":"1","price":"50","times":"15","used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-29"},{"id":"13","code":"anujatest","type":"1","price":"70","times":null,"used":"0","status":"1","start_date":"2020-02-17","end_date":"2020-02-29"},{"id":"14","code":"100","type":"0","price":"50","times":null,"used":"0","status":"1","start_date":"2020-02-18","end_date":"2020-02-27"},{"id":"15","code":"101","type":"1","price":"10000","times":null,"used":"0","status":"1","start_date":"2020-02-18","end_date":"2020-02-25"}]}