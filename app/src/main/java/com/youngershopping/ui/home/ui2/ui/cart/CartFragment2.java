package com.youngershopping.ui.home.ui2.ui.cart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.adapter.Balance_sheet_adapter;
import com.youngershopping.adapter.cart.CartProductAdapter;
import com.youngershopping.databinding.FragmentCart2Binding;
import com.youngershopping.pojo.Get_Wallet_Response;
import com.youngershopping.pojo.PinCodeResponse;
import com.youngershopping.pojo.Wallet_Payment_Response;
import com.youngershopping.pojo.cuopne_code_response;
import com.youngershopping.pojo.list_cart_response;
import com.youngershopping.pojo.list_of_cart;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.WalletActivity;
import com.youngershopping.ui.account.address.AddressListActivity;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.home.ui2.HomeActivity2;
import com.youngershopping.utils.AppEnvironment;
import com.youngershopping.utils.Constants;
import com.youngershopping.view.stepview.StateProgressBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment2 . OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment2# newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment2 extends BaseAppFragment implements View.OnClickListener {
    private FragmentCart2Binding binding;
    private Activity activity;
    private CartProductAdapter cartProductAdapter;
    private List<Integer> listCartIcon;
    private List<String> listCartLable;
    private int step = 1;
    ApiInterface apiInterface;
    List<list_of_cart> list;

    double com = 0.0;

    private  String shipping_charges = "";

    String wallet_status = "false";
    int w_a = 0;
    double w_com = 0.0;


    String payment_mode = "Online";

    double w_aa = 0.0;
    double c_aa = 0.0;

    String percedntage_coupen = "";
    String wallet_Amount_use = "";

    String tran_id = "";

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;


    public CartFragment2() {
        // Required rating_empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart2, container, false);
        binding.relProgress.setVisibility(View.GONE);
        binding.btnNext.setVisibility(View.GONE);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        init();
        setlistner();
        fillData();
        return binding.getRoot();
    }

    private void setlistner() {
        binding.rd1.setOnClickListener(this);
        binding.rd2.setOnClickListener(this);
        binding.rd3.setOnClickListener(this);
        binding.rd4.setOnClickListener(this);
        binding.rd5.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        binding.imgEditAddress.setOnClickListener(this);
        binding.checkWallet.setOnClickListener(this);

    }

    private void init() {
        activity = (HomeActivity) getActivity();
        getDataCart();
        float alpha = 0.45f;
        AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
        alphaUp.setFillAfter(true);
        binding.btApply.startAnimation(alphaUp);
        binding.txtAddress.setText("Address : "+SharePref.getetLoginaddress("c_address", getActivity())+",\nCity : "+SharePref.getetLogincity("c_city", getActivity())+",\nPin : "+SharePref.getetLoginzip("c_zip", getActivity())+",\nCountry : India");
        binding.commanRecyclerviewCart.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.commanRecyclerviewCart.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewCart.recyclerView.setFocusable(false);
        binding.btApply.setEnabled(false);
        binding.edCoupen.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if ((charSequence.toString().length() <= 2)) {
                    //add_fund_button.setBackgroundColor(Color.TRANSPARENT);
                    float alpha = 0.45f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    binding.btApply.startAnimation(alphaUp);
                    binding.btApply.setEnabled(false);
                    binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);

                }
                else {

                    float alpha = 1f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    binding.btApply.startAnimation(alphaUp);
                    binding.btApply.setEnabled(true);
                    binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);

                   }

                if (charSequence.toString().length() == 2){
                    if (!(w_aa == 0.0)){
                        w_com = w_aa;
                        binding.txtPayableAmount.setText(""+w_com);
                        percedntage_coupen = "";
                    }
                    if (!(c_aa == 0.0)){
                        com = c_aa;
                        binding.txtPayableAmount.setText(""+com);
                        percedntage_coupen = "";
                    }
                }



            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.btApply.setOnClickListener(this);
    }

    private void getDataCart() {
        Call<list_cart_response> cart_responseCall = apiInterface.getCartList(SharePref.getetLoginId("c_id",getActivity()));
        cart_responseCall.enqueue(new Callback<list_cart_response>() {
            @Override
            public void onResponse(Call<list_cart_response> call, Response<list_cart_response> response) {
                list_cart_response cart_response = response.body();
                String status = cart_response.getStatus();
                if (status.equals("true")){
                    binding.relProgress.setVisibility(View.VISIBLE);
                    binding.btnNext.setVisibility(View.VISIBLE);
                    list = new ArrayList<>();
                    list.clear();
                    list = cart_response.getList_of_cart();
                    if (list.size() == 0){
                        Toast.makeText(getContext(),"Cart Data is not available",Toast.LENGTH_SHORT).show();
                    }else {
                        Log.d("TAG", "List Size = " + list.size());
                        cartProductAdapter = new CartProductAdapter(activity, listCartIcon, listCartLable, cart_response.getList_of_cart());
                        binding.commanRecyclerviewCart.recyclerView.setAdapter(cartProductAdapter);
                    }
                }
                if (status.equals("false")){
                    Toast.makeText(getContext(),"Cart Data is not available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<list_cart_response> call, Throwable t) {
                Log.d("TAG","Error = "+t.getMessage());
            }
        });
    }

    private void fillData() {
        listCartIcon = new ArrayList<>();
        listCartIcon.add(R.drawable.oneplus1);
//        listCartIcon.add(R.drawable.oneplus2);


        listCartLable = new ArrayList<>();
//        listCartLable.add(getResources().getString(R.string.dummy_oneplus1));
//        listCartLable.add(getResources().getString(R.string.dummy_oneplus2));

//        cartProductAdapter = new CartProductAdapter(activity, listCartIcon, listCartLable,list);
//        binding.commanRecyclerviewCart.recyclerView.setAdapter(cartProductAdapter);

        String[] labels = {getResources().getString(R.string.CartStep1), getResources().getString(R.string.CartStep2),
                getResources().getString(R.string.CartStep3), getResources().getString(R.string.CartStep4)};

        setStateProgressBarData(labels,
                StateProgressBar.StateNumber.FOUR, StateProgressBar.StateNumber.ONE);
    }

    private void setStateProgressBarData(String[] name,
                                         StateProgressBar.StateNumber maxNum,
                                         StateProgressBar.StateNumber curNum) {
        binding.stateProgressBar.setStateDescriptionData_Top(name);
//        binding.stateProgressBar.setStateDescriptionData_Bottom(name);
        binding.stateProgressBar.setCurrentStateNumber(curNum);
        binding.stateProgressBar.setMaxStateNumber(maxNum);
        binding.stateProgressBar.setDescriptionTopSpaceIncrementer(36f);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                if (step < 5) {
                    step++;
                    binding.btnPrevious.setVisibility(View.VISIBLE);
                    if (step == 2) {
                        sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        Log.d("TAG","List to string = "+list.toString());
                        HideAll();
                        getWalletAmout();
                        binding.cartShippingAddress.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Next 1 = ");

                    } else if (step == 3) {
                        getDataList();
                        sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        HideAll();
                        binding.cartPaymentType.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Next 2 = ");
                    } else if (step == 4) {
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        binding.btnNext.setText(getResources().getString(R.string.ConfirmPlaceOrder));
                        HideAll();
                        binding.recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(activity));
                        binding.recyclerViewMovieList.setHasFixedSize(true);
                        Balance_sheet_adapter adapter = new Balance_sheet_adapter(list,getActivity(),shipping_charges);
                        binding.recyclerViewMovieList.setAdapter(adapter);
                        binding.cartFinalAmount.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Next 3 = ");
                    }else if (step == 5){
//                        startActivity(new Intent(getActivity(),DesignActivity.class));
                        if (payment_mode.equals("Online"))
                        {
                            launchPayUMoneyFlow();
                        }else {
                            goToplaceOder();
                        }
                        Toast.makeText(getActivity(),""+payment_mode,Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btnPrevious:
                if (step > 1) {
                    step--;
                    binding.btnNext.setText(getResources().getString(R.string.Next));
                    if (step == 1) {
                        com = 0.0;
                        w_com = 0.0;

                        binding.btnPrevious.setVisibility(View.GONE);
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
                        HideAll();
                        binding.cartItem.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Previous 1 = ");
                    } else if (step == 2) {
                        com = 0.0;
                        w_com = 0.0;
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                        HideAll();
                        binding.cartShippingAddress.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Previous 2 = ");
                    } else if (step == 3) {
                        com = 0.0;
                        w_com = 0.0;
                        binding.checkWallet.setChecked(false);
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                        HideAll();
                        binding.cartPaymentType.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Previous 3 = ");
                    } else if (step == 4) {
                        com = 0.0;
                        w_com = 0.0;
                        binding.checkWallet.setChecked(false);
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                        HideAll();
                        binding.recyclerViewMovieList.setLayoutManager(new LinearLayoutManager(activity));
                        binding.recyclerViewMovieList.setHasFixedSize(true);
                        Balance_sheet_adapter adapter = new Balance_sheet_adapter(list,getActivity(),shipping_charges);
                        binding.recyclerViewMovieList.setAdapter(adapter);
                        binding.cartFinalAmount.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Previous 4 = ");

                    }
                }
                break;
            case R.id.rd1:
                binding.rd2.setChecked(false);
                break;
            case R.id.rd2:
                binding.rd1.setChecked(false);
                binding.rd2.setChecked(true);
                break;
            case R.id.rd3:
                payment_mode = "Online";
                sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                binding.rd4.setChecked(false);
                binding.rd5.setChecked(false);
                break;
            case R.id.rd4:
                payment_mode = "Cash On Delivery";
                sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                binding.rd3.setChecked(false);
                binding.rd5.setChecked(false);
                break;
            case R.id.rd5:
                binding.rd4.setChecked(false);
                binding.rd3.setChecked(false);
                break;
            case R.id.imgEditAddress:
                Intent intent = new Intent(activity, AddressListActivity.class);
                startActivity(intent);
                break;
            case R.id.checkWallet:
                if (binding.checkWallet.isChecked()){
                    wallet_status = "true";
                    wallet_Amount_use = ""+w_a;
//                    Toast.makeText(getActivity(), "Unity 3D", Toast.LENGTH_LONG).show();
                    binding.llWalletAmount.setVisibility(View.VISIBLE);
                    binding.txtwalletAount.setText("-"+w_a+" ₹");
                    w_com = com - w_a;
                    binding.txtPayableAmount.setText(""+(com-w_a)+" ₹");
                }else{
                    wallet_status = "false";
                    wallet_Amount_use = "";
                    binding.llWalletAmount.setVisibility(View.GONE);
                    binding.txtPayableAmount.setText(""+com+" ₹");
                }
                break;

            case R.id.btApply:
                if (binding.edCoupen.getText().toString().trim().isEmpty()){
                    binding.edCoupen.setError("Please enter coupen code");
                }else{
                    sendCoupen();
                }

        }
    }



    private void sendCoupen() {
        Call<cuopne_code_response> codeResponseCall = apiInterface.applyCoupone(binding.edCoupen.getText().toString().trim());
        codeResponseCall.enqueue(new Callback<cuopne_code_response>() {
            @Override
            public void onResponse(Call<cuopne_code_response> call, Response<cuopne_code_response> response) {
                cuopne_code_response code_response = response.body();
                String status = code_response.getStatus();
                Log.d("TAG"," Cou[pe = "+status);
                if (status.equals("true")){

                    String  type = code_response.getList().get(0).getType();
                    String price_pre = code_response.getList().get(0).getPrice();
                    Toast.makeText(getActivity(),"price = "+price_pre,Toast.LENGTH_SHORT).show();
                    percedntage_coupen = price_pre;
                    if (wallet_status.equals("true")){
                        binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);

                        if (type.equals("0")){
                            // Precentage
                            double precentage = (w_com * (Integer.parseInt(price_pre)))/100;
                            w_aa = w_com;
                            binding.txtPayableAmount.setText(""+(w_com-precentage)+" ₹");
                        }else{
                            w_aa = w_com;
                            binding.txtPayableAmount.setText(""+(w_com-(Integer.parseInt(price_pre)))+" ₹");
                            // Amount
                        }
                    }else{
                        binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);
                        if (type.equals("0")){
                            // Precentage
                            c_aa = com;
                            double precentage = (com * (Integer.parseInt(price_pre)))/100;
                            binding.txtPayableAmount.setText(""+(com-precentage)+" ₹");
                        }else{
                            c_aa = com;
                            binding.txtPayableAmount.setText(""+(com-(Integer.parseInt(price_pre)))+" ₹");
                            // Amount
                        }
                    }
                }
                if (status.equals("false")){


                    Toast.makeText(getActivity(),""+code_response.getMsg(),Toast.LENGTH_SHORT).show();
                    binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_highlight_off_black_24dp, 0);

                }
            }

            @Override
            public void onFailure(Call<cuopne_code_response> call, Throwable t) {

            }
        });
    }

    private void getDataList() {
        List<list_of_cart> listOfCarts = new ArrayList<>();
        double p = 0.0;
        double q = 0.0;
        int g = 0;
        double pq = 0.0;
        double tp = 0.0;
        double  stp = 0.0;

        com = 0.0;
        for (int i = 0; i<list.size();i++){
            p = Double.parseDouble(list.get(i).getPrice());
            q = Double.parseDouble(list.get(i).getQty());
            g = Integer.parseInt(list.get(i).getGst());
            pq = p * q;
            tp = (pq * g) / 100;
            stp = pq + tp;

            com = com + stp + (q * Double.parseDouble(shipping_charges));
        }
        binding.txtPayableAmount.setText(""+com+" ₹");
    }

    private void HideAll() {
        binding.cartItem.setVisibility(View.GONE);
        binding.cartShippingAddress.setVisibility(View.GONE);
        binding.cartPaymentType.setVisibility(View.GONE);
        binding.cartFinalAmount.setVisibility(View.GONE);
    }


    private void sendPin(final String pin) {

        Call<PinCodeResponse> pinCodeResponseCall = apiInterface.checkPinCode(pin);
        pinCodeResponseCall.enqueue(new Callback<PinCodeResponse>() {
            @Override
            public void onResponse(Call<PinCodeResponse> call, retrofit2.Response<PinCodeResponse> response) {
                PinCodeResponse codeResponse = response.body();

                String status = codeResponse.getStatus();

                if (status.equals("true")) {
                    shipping_charges = codeResponse.getPincodes().get(0).getShipping_charges();
                    Log.d("TAG"," Chage = "+shipping_charges+" "+response.body().toString());
                    binding.txtShippingCharge.setText(""+shipping_charges+" ₹");
                }
                if (status.equals("false")) {
                    Toast.makeText(getContext(), "Pin code is not Available", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PinCodeResponse> call, Throwable t) {
                Log.d("TAG", "Pincode Error = " + t.getMessage());
            }
        });
    }

    private void getWalletAmout() {
        Call<Get_Wallet_Response> get_wallet_responseCall = apiInterface.getWalletAmount(SharePref.getetLoginId("c_id",getContext()));
        get_wallet_responseCall.enqueue(new Callback<Get_Wallet_Response>() {
            @Override
            public void onResponse(Call<Get_Wallet_Response> call, Response<Get_Wallet_Response> response) {
                Get_Wallet_Response get_wallet_response = response.body();
                String status = get_wallet_response.getStatus();
                if (status.equals("true")){
                    binding.txtwallet.setText(""+get_wallet_response.getAmount()+" ₹");
                    w_a = Integer.parseInt(get_wallet_response.getAmount());
                }
            }

            @Override
            public void onFailure(Call<Get_Wallet_Response> call, Throwable t) {

            }
        });
    }

    // Place Order
    private void goToplaceOder() {

        JSONObject object_main = new JSONObject();
        JSONObject ooj_product_id = new JSONObject();
        JSONObject object = new JSONObject();
        JSONObject object_item = null;
        JSONObject obj_sub = null;
        JSONObject object1 = null;
        JSONArray array = null;
        String p_id = "";
        for (int i = 0; i<list.size();i++) {
            Log.d("TAG","Loop product = "+i+"  "+list.size());
            p_id = list.get(i).getId();
            object_item = new JSONObject();
            try {
                object_item.put("id", list.get(i).getId());
                object_item.put("name", list.get(i).getName());
                object_item.put("photo", list.get(i).getPhoto());
                object_item.put("size", list.get(i).getSize());
                object_item.put("color", list.get(i).getColor());
                object_item.put("cprice", list.get(i).getCprice());
                object_item.put("stock", ""+(Integer.parseInt(list.get(i).getStock()) - Integer.parseInt(list.get(i).getStock())));
                object_item.put("type", "null");
                object_item.put("file", "null");
                object_item.put("link", "null");
                object_item.put("license", "null");
                object_item.put("license_qty", "null");
                object_item.put("measure", list.get(i).getMeasure());
                object_item.put("vendor_id", list.get(i).getUser_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            obj_sub = new JSONObject();
            try {
                obj_sub.put("qty", list.get(i).getQty());
                obj_sub.put("size", list.get(i).getSize());
                obj_sub.put("color", list.get(i).getColor());
                obj_sub.put("stock", ""+(Integer.parseInt(list.get(i).getStock()) - Integer.parseInt(list.get(i).getStock())));
                obj_sub.put("price", list.get(i).getCprice());
                obj_sub.put("item", object_item);
                obj_sub.put("license", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ooj_product_id.put("item", object_item);
                object.put("" + p_id, obj_sub);
                Log.d("TAG","Show "+object);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {

            Log.d("TAG","Show "+ooj_product_id);
             object1 = new JSONObject();
            array = new JSONArray();
            array.put(object);
            object1.put("items", array);
            Log.d("TAG","Show "+object1);
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            object_main.put("user_id", SharePref.getetLoginId("c_id",getActivity()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            object_main.put("cart", object1);
            String[] t_amount = binding.txtPayableAmount.getText().toString().trim().split(" ₹");
            object_main.put("pay_amount", t_amount[0]);
            object_main.put("method", payment_mode);
            object_main.put("shipping", "shipping");
            object_main.put("pickup_location", "pickup_location");
            object_main.put("customer_email", SharePref.getetLoginEmail("c_email",getActivity()));
            object_main.put("customer_name", SharePref.getetLoginName("c_name",getActivity()));
            object_main.put("shipping_cost", ""+shipping_charges);
            object_main.put("tax", "0");
            object_main.put("customer_phone", SharePref.getetLoginMob("c_mob",getActivity()));
            object_main.put("customer_address", SharePref.getetLoginaddress("c_address",getActivity()));
            object_main.put("customer_country", "India");
            object_main.put("customer_city", SharePref.getetLogincity("c_city",getActivity()));
            object_main.put("customer_zip", SharePref.getetLoginzip("c_zip",getActivity()));
            object_main.put("shipping_email", SharePref.getetLoginEmail("c_email",getActivity()));
            object_main.put("shipping_name", SharePref.getetLoginName("c_name",getActivity()));
            object_main.put("shipping_phone", SharePref.getetLoginMob("c_mob",getActivity()));
            object_main.put("shipping_address", SharePref.getetLoginaddress("c_address",getActivity()));
            object_main.put("shipping_country", "India");
            object_main.put("shipping_city", SharePref.getetLogincity("c_city",getActivity()));
            object_main.put("shipping_zip", SharePref.getetLoginzip("c_zip",getActivity()));
            object_main.put("order_note", "");
            object_main.put("dp", "0");
            object_main.put("vendor_id", "");
            object_main.put("totalQty", ""+list.size());
            object_main.put("totalPrice", ""+t_amount[0]);
            object_main.put("coupon_code",""+binding.edCoupen.getText().toString().trim());
            object_main.put("coupon_discount",""+percedntage_coupen);
            object_main.put("gst",""+t_amount[0]);
            object_main.put("txnid","");
            object_main.put("used_wallet",""+wallet_Amount_use);

            Log.i("TAG", "getList2 main: " + object_main);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constants.product_place_order,
                object_main,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "onResponse: " + response);

                        String status = response.optString("status");
                        if (status.equals("true")){
                            Intent intent = new Intent(activity, HomeActivity.class);
                            intent.putExtra(Constants.temp, "1");
                            startActivity(intent);
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "onErrorResponse: "+error.getMessage());
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);

    }



    private PayUmoneySdkInitializer.PaymentParam calculateServerSideHashAndInitiatePayment1(final PayUmoneySdkInitializer.PaymentParam paymentParam) {
        StringBuilder stringBuilder = new StringBuilder();
        HashMap<String, String> params = paymentParam.getParams();
        stringBuilder.append(params.get(PayUmoneyConstants.KEY) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.TXNID) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.AMOUNT) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.PRODUCT_INFO) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.FIRSTNAME) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.EMAIL) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF1) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF2) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF3) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF4) + "|");
        stringBuilder.append(params.get(PayUmoneyConstants.UDF5) + "||||||");

        AppEnvironment appEnvironment = AppEnvironment.PRODUCTION;
        stringBuilder.append(appEnvironment.salt());

        String hash = hashCal(stringBuilder.toString());
        paymentParam.setMerchantHash(hash);
        return paymentParam;
    }

    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            byte messageDigest[] = algorithm.digest();
            for (byte aMessageDigest : messageDigest) {
                String hex = Integer.toHexString(0xFF & aMessageDigest);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException ignored) {
        }
        return hexString.toString();
    }

    /**
     * This function prepares the data for payment and launches payumoney plug n play sdk
     */
    private void launchPayUMoneyFlow() {

        PayUmoneyConfig payUmoneyConfig = PayUmoneyConfig.getInstance();

        //Use this to set your custom text on result screen button
        payUmoneyConfig.setDoneButtonText("Go to Dashboard");

        //Use this to set your custom title for the activity
        payUmoneyConfig.setPayUmoneyActivityTitle("Younger Shopping Club");

        PayUmoneySdkInitializer.PaymentParam.Builder builder = new PayUmoneySdkInitializer.PaymentParam.Builder();

        double amount = 0;
        try {
            amount = Double.parseDouble("100");
        } catch (Exception e) {
            e.printStackTrace();
        }


        String txnId = System.currentTimeMillis() + "";
        String phone = "8983249969";
        String productName = "Younger Shopping Club";
        String firstName = "Milind";
        String email = "milindnarethe909@gmail.com";
        String[] t_amount = binding.txtPayableAmount.getText().toString().trim().split(" ₹");

        String udf1 = "";
        String udf2 = "";
        String udf3 = "";
        String udf4 = "";
        String udf5 = "";
        String udf6 = "";
        String udf7 = "";
        String udf8 = "";
        String udf9 = "";
        String udf10 = "";

        AppEnvironment appEnvironment = AppEnvironment.PRODUCTION;
        builder.setAmount(""+t_amount[0])
                .setTxnId(txnId)
                .setPhone(phone)
                .setProductName(productName)
                .setFirstName(firstName)
                .setEmail(email)
                .setsUrl(appEnvironment.surl())
                .setfUrl(appEnvironment.furl())
                .setUdf1(udf1)
                .setUdf2(udf2)
                .setUdf3(udf3)
                .setUdf4(udf4)
                .setUdf5(udf5)
                .setUdf6(udf6)
                .setUdf7(udf7)
                .setUdf8(udf8)
                .setUdf9(udf9)
                .setUdf10(udf10)
                .setIsDebug(appEnvironment.debug())
                .setKey(appEnvironment.merchant_Key())
                .setMerchantId(appEnvironment.merchant_ID());

        try {
            mPaymentParams = builder.build();

            /*
             * Hash should always be generated from your server side.
             * */
//            generateHashFromServer(mPaymentParams);


//         //**
//             * Do not use below code when going live
//             * Below code is provided to generate hash from sdk.
//             * It is recommended to generate hash from server side only.
//             * *//*
            mPaymentParams = calculateServerSideHashAndInitiatePayment1(mPaymentParams);

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, activity, R.style.AppTheme_default, false);

        } catch (Exception e) {
            // some exception occurred
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            bt_make_payment.setEnabled(true);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data !=
                null) {
            TransactionResponse transactionResponse = data.getParcelableExtra(PayUmoneyFlowManager
                    .INTENT_EXTRA_TRANSACTION_RESPONSE);

            ResultModel resultModel = data.getParcelableExtra(PayUmoneyFlowManager.ARG_RESULT);

            // Check which object is non-null
            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                String payuResponse = transactionResponse.getPayuResponse();
                Log.d("TAG" + "resp", payuResponse);
                try {
                    JSONObject jsonObject = new JSONObject(payuResponse.toString());
                    JSONObject jsonObject1 = jsonObject.getJSONObject("result");
                    String status = jsonObject1.getString("status");
                    String p_id = jsonObject1.getString("paymentId");
                    String t_id = jsonObject1.getString("txnid");
                    String date = jsonObject1.getString("addedon");
                    String amt = jsonObject1.getString("amount");
                    if (status.equalsIgnoreCase("success")) {
//                        ATMTSharedPreferenceManager.setPaymentStatus("p_status", true, getApplicationContext());
                        sendPaymetStatus(p_id, t_id, amt, date, status);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            } else if (resultModel != null && resultModel.getError() != null) {
                Log.d("TAG", "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d("TAG", "Both objects are null!");
            }
        }
    }





    private void sendPaymetStatus(final String p_id1, final String t_id, final String amt, final String ddate, final String status) {
        Log.d("TAG","Response Pay u money = "+t_id+" amount = "+amt+" status = "+status);

        JSONObject object_main = new JSONObject();
        JSONObject ooj_product_id = new JSONObject();
        JSONObject object = new JSONObject();
        JSONObject object_item = null;
        JSONObject obj_sub = null;
        JSONObject object1 = null;
        JSONArray array = null;
        String p_id = "";
        for (int i = 0; i<list.size();i++) {
            Log.d("TAG","Loop product = "+i+"  "+list.size());
            p_id = list.get(i).getId();
            object_item = new JSONObject();
            try {
                object_item.put("id", list.get(i).getId());
                object_item.put("name", list.get(i).getName());
                object_item.put("photo", list.get(i).getPhoto());
                object_item.put("size", list.get(i).getSize());
                object_item.put("color", list.get(i).getColor());
                object_item.put("cprice", list.get(i).getCprice());
                object_item.put("stock", ""+(Integer.parseInt(list.get(i).getStock()) - Integer.parseInt(list.get(i).getStock())));
                object_item.put("type", "null");
                object_item.put("file", "null");
                object_item.put("link", "null");
                object_item.put("license", "null");
                object_item.put("license_qty", "null");
                object_item.put("measure", list.get(i).getMeasure());
                object_item.put("vendor_id", list.get(i).getUser_id());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            obj_sub = new JSONObject();
            try {
                obj_sub.put("qty", list.get(i).getQty());
                obj_sub.put("size", list.get(i).getSize());
                obj_sub.put("color", list.get(i).getColor());
                obj_sub.put("stock", ""+(Integer.parseInt(list.get(i).getStock()) - Integer.parseInt(list.get(i).getStock())));
                obj_sub.put("price", list.get(i).getCprice());
                obj_sub.put("item", object_item);
                obj_sub.put("license", "");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                ooj_product_id.put("item", object_item);
                object.put("" + p_id, obj_sub);
                Log.d("TAG","Show "+object);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        try {

            Log.d("TAG","Show "+ooj_product_id);
            object1 = new JSONObject();
            array = new JSONArray();
            array.put(object);
            object1.put("items", array);
            Log.d("TAG","Show "+object1);
        }catch (JSONException e){
            e.printStackTrace();
        }

        try {
            object_main.put("user_id", SharePref.getetLoginId("c_id",getActivity()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            object_main.put("cart", object1);
            String[] t_amount = binding.txtPayableAmount.getText().toString().trim().split(" ₹");
            object_main.put("pay_amount", t_amount[0]);
            object_main.put("method", payment_mode);
            object_main.put("shipping", "shipping");
            object_main.put("pickup_location", "pickup_location");
            object_main.put("customer_email", SharePref.getetLoginEmail("c_email",getActivity()));
            object_main.put("customer_name", SharePref.getetLoginName("c_name",getActivity()));
            object_main.put("shipping_cost", ""+shipping_charges);
            object_main.put("tax", "0");
            object_main.put("customer_phone", SharePref.getetLoginMob("c_mob",getActivity()));
            object_main.put("customer_address", SharePref.getetLoginaddress("c_address",getActivity()));
            object_main.put("customer_country", "India");
            object_main.put("customer_city", SharePref.getetLogincity("c_city",getActivity()));
            object_main.put("customer_zip", SharePref.getetLoginzip("c_zip",getActivity()));
            object_main.put("shipping_email", SharePref.getetLoginEmail("c_email",getActivity()));
            object_main.put("shipping_name", SharePref.getetLoginName("c_name",getActivity()));
            object_main.put("shipping_phone", SharePref.getetLoginMob("c_mob",getActivity()));
            object_main.put("shipping_address", SharePref.getetLoginaddress("c_address",getActivity()));
            object_main.put("shipping_country", "India");
            object_main.put("shipping_city", SharePref.getetLogincity("c_city",getActivity()));
            object_main.put("shipping_zip", SharePref.getetLoginzip("c_zip",getActivity()));
            object_main.put("order_note", "");
            object_main.put("dp", "0");
            object_main.put("vendor_id", "");
            object_main.put("totalQty", ""+list.size());
            object_main.put("totalPrice", ""+t_amount[0]);
            object_main.put("coupon_code",""+binding.edCoupen.getText().toString().trim());
            object_main.put("coupon_discount",""+percedntage_coupen);
            object_main.put("gst",""+t_amount[0]);
            object_main.put("txnid",""+t_id);
            object_main.put("used_wallet",""+wallet_Amount_use);

            Log.i("TAG", "getList2 main: " + object_main);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                Constants.product_place_order,
                object_main,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "onResponse: " + response);

                        String status = response.optString("status");
                        if (status.equals("true")){
                            Intent intent = new Intent(activity, HomeActivity.class);
                            intent.putExtra(Constants.temp, "1");
                            startActivity(intent);
                        }

                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("TAG", "onErrorResponse: "+error.getMessage());
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 10,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        requestQueue.add(jsonObjectRequest);


//        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please wait", "Sending data...", false);
//        StringRequest request = new StringRequest(Request.Method.POST, Constants.SEND_PAYMENT_STATUS_URL,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        progressDialog.dismiss();
//                        JSONObject jsonObject = null;
//                        Log.d(TAG, response);
//
//                        try {
//                            jsonObject = new JSONObject(response);
//                            String status = null, message = null;
//                            if (jsonObject.has("status") && jsonObject.has("message")) {
//                                status = jsonObject.getString("status");
//                                message = jsonObject.getString("message");
//                                if (status.equals("200Ok") && message.equals("activation succeeded")) {
//                                    ATMTSharedPreferenceManager.setPaymentStatus("p_status", true, getApplicationContext());
//                                    ATMTSharedPreferenceManager.setExpDate("expDate", jsonObject.getString("subscription_expiration_date"), getApplicationContext());
//                                    startActivity(new Intent(MakePayment.this, MainActivity.class));
//                                    finish();
//                                } else {
//                                    startActivity(new Intent(MakePayment.this, MainActivity.class));
//                                    finish();
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                progressDialog.dismiss();
//
//                String message = null;
//                if (error instanceof NetworkError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ServerError) {
//                    message = "The server could not be found. Please try again after some time!!";
//                } else if (error instanceof AuthFailureError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof ParseError) {
//                    message = "Parsing error! Please try again after some time!!";
//                } else if (error instanceof NoConnectionError) {
//                    message = "Cannot connect to Internet...Please check your connection!";
//                } else if (error instanceof TimeoutError) {
//                    message = "Connection TimeOut! Please check your internet connection.";
//                }
//                Toast.makeText(MakePayment.this, message, Toast.LENGTH_SHORT).show();
//            }
//        }) {
//
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//
//                Map<String, String> params = new HashMap<>();
//                params.put(KEY_CLIENT_ID, ATMTSharedPreferenceManager.getClientID("cid", getApplicationContext()));
//                params.put(payment_id, p_id);
//                params.put(transaction_id, t_id);
//                params.put(amount, amt);
//                params.put(transaction_time, ddate);
//                params.put(Status, status);
//                return params;
//            }
//        };
//
//        request.setRetryPolicy(new DefaultRetryPolicy(
//                MY_SOCKET_TIMEOUT_MS,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//
//
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(request);
    }






}
