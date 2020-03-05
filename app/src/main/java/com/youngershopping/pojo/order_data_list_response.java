package com.youngershopping.pojo;

import com.google.gson.annotations.SerializedName;

public class order_data_list_response {
    @SerializedName("id")
    String id;

    @SerializedName("user_id")
    String user_id;

    @SerializedName("method")
    String method;

    @SerializedName("shipping")
    String shipping;

    @SerializedName("pickup_location")
    String pickup_location;

    @SerializedName("totalQty")
    String totalQty;

    @SerializedName("pay_amount")
    String pay_amount;

    @SerializedName("txnid")
    String txnid;

    @SerializedName("charge_id")
    String charge_id;

    @SerializedName("order_number")
    String order_number;

    @SerializedName("payment_status")
    String payment_status;

    @SerializedName("customer_email")
    String customer_email;

    @SerializedName("customer_name")
    String customer_name;

    @SerializedName("customer_country")
    String customer_country;

    @SerializedName("customer_phone")
    String customer_phone;

    @SerializedName("customer_address")
    String customer_address;

    @SerializedName("customer_city")
    String customer_city;

    @SerializedName("customer_zip")
    String customer_zip;

    @SerializedName("shipping_name")
    String shipping_name;

    @SerializedName("shipping_country")
    String shipping_country;

    @SerializedName("shipping_email")
    String shipping_email;

    @SerializedName("shipping_phone")
    String shipping_phone;

    @SerializedName("shipping_address")
    String shipping_address;

    @SerializedName("shipping_city")
    String shipping_city;

    @SerializedName("shipping_zip")
    String shipping_zip;

    @SerializedName("order_note")
    String order_note;

    @SerializedName("coupon_code")
    String coupon_code;

    @SerializedName("coupon_discount")
    String coupon_discount;

    @SerializedName("status")
    String status;

    @SerializedName("currency_sign")
    String currency_sign;

    @SerializedName("currency_value")
    String currency_value;

    @SerializedName("shipping_cost")
    String shipping_cost;

    @SerializedName("tax")
    String tax;

    @SerializedName("dp")
    String dp;

    @SerializedName("gst")
    String gst;

    @SerializedName("used_wallet")
    String used_wallet;

    @SerializedName("replace_reason")
    String replace_reason;

    @SerializedName("replace_note")
    String replace_note;

    @SerializedName("created_at")
    String created_at;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    //
//    @SerializedName("")
//    String ;
//
//    @SerializedName("")
//    String ;
//
//    @SerializedName("")
//    String ;
//
//    @SerializedName("")
//    String ;
//
//    @SerializedName("")
//    String ;
//
//    @SerializedName("")
//    String ;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPickup_location() {
        return pickup_location;
    }

    public void setPickup_location(String pickup_location) {
        this.pickup_location = pickup_location;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getTxnid() {
        return txnid;
    }

    public void setTxnid(String txnid) {
        this.txnid = txnid;
    }

    public String getCharge_id() {
        return charge_id;
    }

    public void setCharge_id(String charge_id) {
        this.charge_id = charge_id;
    }

    public String getOrder_number() {
        return order_number;
    }

    public void setOrder_number(String order_number) {
        this.order_number = order_number;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_country() {
        return customer_country;
    }

    public void setCustomer_country(String customer_country) {
        this.customer_country = customer_country;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_city() {
        return customer_city;
    }

    public void setCustomer_city(String customer_city) {
        this.customer_city = customer_city;
    }

    public String getCustomer_zip() {
        return customer_zip;
    }

    public void setCustomer_zip(String customer_zip) {
        this.customer_zip = customer_zip;
    }

    public String getShipping_name() {
        return shipping_name;
    }

    public void setShipping_name(String shipping_name) {
        this.shipping_name = shipping_name;
    }

    public String getShipping_country() {
        return shipping_country;
    }

    public void setShipping_country(String shipping_country) {
        this.shipping_country = shipping_country;
    }

    public String getShipping_email() {
        return shipping_email;
    }

    public void setShipping_email(String shipping_email) {
        this.shipping_email = shipping_email;
    }

    public String getShipping_phone() {
        return shipping_phone;
    }

    public void setShipping_phone(String shipping_phone) {
        this.shipping_phone = shipping_phone;
    }

    public String getShipping_address() {
        return shipping_address;
    }

    public void setShipping_address(String shipping_address) {
        this.shipping_address = shipping_address;
    }

    public String getShipping_city() {
        return shipping_city;
    }

    public void setShipping_city(String shipping_city) {
        this.shipping_city = shipping_city;
    }

    public String getShipping_zip() {
        return shipping_zip;
    }

    public void setShipping_zip(String shipping_zip) {
        this.shipping_zip = shipping_zip;
    }

    public String getOrder_note() {
        return order_note;
    }

    public void setOrder_note(String order_note) {
        this.order_note = order_note;
    }

    public String getCoupon_code() {
        return coupon_code;
    }

    public void setCoupon_code(String coupon_code) {
        this.coupon_code = coupon_code;
    }

    public String getCoupon_discount() {
        return coupon_discount;
    }

    public void setCoupon_discount(String coupon_discount) {
        this.coupon_discount = coupon_discount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCurrency_sign() {
        return currency_sign;
    }

    public void setCurrency_sign(String currency_sign) {
        this.currency_sign = currency_sign;
    }

    public String getCurrency_value() {
        return currency_value;
    }

    public void setCurrency_value(String currency_value) {
        this.currency_value = currency_value;
    }

    public String getShipping_cost() {
        return shipping_cost;
    }

    public void setShipping_cost(String shipping_cost) {
        this.shipping_cost = shipping_cost;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getGst() {
        return gst;
    }

    public void setGst(String gst) {
        this.gst = gst;
    }

    public String getUsed_wallet() {
        return used_wallet;
    }

    public void setUsed_wallet(String used_wallet) {
        this.used_wallet = used_wallet;
    }

    public String getReplace_reason() {
        return replace_reason;
    }

    public void setReplace_reason(String replace_reason) {
        this.replace_reason = replace_reason;
    }

    public String getReplace_note() {
        return replace_note;
    }

    public void setReplace_note(String replace_note) {
        this.replace_note = replace_note;
    }
}
