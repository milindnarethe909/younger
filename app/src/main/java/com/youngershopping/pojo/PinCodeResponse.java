package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PinCodeResponse {
    @SerializedName("status")
    String status;

    @SerializedName("pincode")
    List<pincode> pincodes;

    public List<pincode> getPincodes() {
        return pincodes;
    }

    public void setPincodes(List<pincode> pincodes) {
        this.pincodes = pincodes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
