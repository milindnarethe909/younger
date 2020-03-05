package com.youngershopping.retrofit;

import com.youngershopping.pojo.AddWalletAmount_Response;
import com.youngershopping.pojo.Add_To_Cart_Response;
import com.youngershopping.pojo.Get_Wallet_Response;
import com.youngershopping.pojo.PinCodeResponse;
import com.youngershopping.pojo.Wallet_Payment_Response;
import com.youngershopping.pojo.Wishaddresponse;
import com.youngershopping.pojo.WishlistProduct_Show;
import com.youngershopping.pojo.cuopne_code_response;
import com.youngershopping.pojo.feedback_response;
import com.youngershopping.pojo.get_notification_response;
import com.youngershopping.pojo.list_cart_deteted_response;
import com.youngershopping.pojo.list_cart_response;
import com.youngershopping.pojo.offer_response;
import com.youngershopping.pojo.order_list_respones;
import com.youngershopping.pojo.serach_details_gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("young_apie/index.php/post/wish")
    public void wishListAdd(@Field("user_id") String user_id,
                            @Field("product_id") String product_id,
                            @Field("flag") String flag,
                            Callback<Wishaddresponse> wishaddresponseCallback);


    @FormUrlEncoded
    @POST("young_apie/index.php/post/wish")
    Call<Wishaddresponse> wishlistAdd(@Field("user_id") String user_id,@Field("product_id") String product_id ,@Field("flag") String flag);


    @FormUrlEncoded
    @POST("young_apie/index.php/post/showwishlist")
    Call<WishlistProduct_Show> wishlist(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/addCart")
    Call<Add_To_Cart_Response> AddCart(@Field("user_id") String user_id,
                                       @Field("qty") String qty,
                                       @Field("size") String size,
                                       @Field("color") String color,
                                       @Field("stock") String stock,
                                       @Field("price") String price,
                                       @Field("id") String id,
                                       @Field("name") String name,
                                       @Field("cprice") String cprice,
                                       @Field("product_stock") String product_stock,
                                       @Field("type") String type,
                                       @Field("file") String file,
                                       @Field("link") String link,
                                       @Field("license") String license,
                                       @Field("license_qty") String license_qty,
                                       @Field("measure") String measure,
                                       @Field("photo") String photo,
                                       @Field("gst") String gst
                                       );

    @FormUrlEncoded
    @POST("young_apie/index.php/post/addmoney")
    Call<AddWalletAmount_Response> AddWallet(@Field("id") String id,@Field("wallet") String wallet,@Field("trxId") String trxId);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/shippingcharge")
    Call<PinCodeResponse> checkPinCode(@Field("pincode") String pincode);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/addmoney")
    Call<Wallet_Payment_Response> walletAddMoney(@Field("id") String id, @Field("wallet") String wallet, @Field("trxId") String trxId, @Field("payment_status") String payment_status);


    @FormUrlEncoded
    @POST("young_apie/index.php/post/wallet_amount")
    Call<Get_Wallet_Response> getWalletAmount(@Field("id") String id);

    @GET("young_apie/index.php/get/get_notifications")
    Call<get_notification_response> getNotification();

    @FormUrlEncoded
    @POST("young_apie/index.php/post/cart_list")
    Call<list_cart_response> getCartList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/user_feedback")
    Call<feedback_response> sendFeedBack(@Field("product_id") String product_id,
                                         @Field("store_ambience") String store_ambience,
                                         @Field("product_range") String product_range,
                                         @Field("product_availability") String product_availability,
                                         @Field("staff_attitude") String staff_attitude,
                                         @Field("product_knowledge_staff") String product_knowledge_staff,
                                         @Field("bill_payment") String bill_payment,
                                         @Field("cus_fullname") String cus_fullname,
                                         @Field("mobile") String mobile,
                                         @Field("comment") String comment,
                                         @Field("time_to_reach_you") String time_to_reach_you,
                                         @Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/delete_item_cart")
    Call<list_cart_deteted_response> deleted_cart(@Field("user_id") String user_id,@Field("id") String id);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/applyCoupen")
    Call<cuopne_code_response> applyCoupone(@Field("code") String code);

    @GET("young_apie/index.php/get/get_applyCoupenOfferList")
    Call<offer_response> getOffercode();

    @FormUrlEncoded
    @POST("young_apie/index.php/post/orderList")
    Call<order_list_respones> getOderList(@Field("user_id") String user_id);

    @FormUrlEncoded
    @POST("young_apie/index.php/post/searchMain")
    Call<serach_details_gson> searchData(@Field("product_name") String product_name);


}


