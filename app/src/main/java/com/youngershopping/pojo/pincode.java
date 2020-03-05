package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class pincode {
    @SerializedName("shipping_charges")
    String shipping_charges;

    public String getShipping_charges() {
        return shipping_charges;
    }

    public void setShipping_charges(String shipping_charges) {
        this.shipping_charges = shipping_charges;
    }
}

//{

//        }"status": true,
//        "message": "1 RowFound",
//        "pincode": [
//        {
//        "id": "8176",
//        "shipping_pincode": "444806",
//        "shipping_charges": "73",
//        "shipping_days": "7",
//        "created_at": "2019-12-18 12:52:27",
//        "updated_at": "2019-12-18 12:52:27"
//        }
//        ]