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

import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.adapter.Balance_sheet_adapter;
import com.youngershopping.adapter.cart.CartProductAdapter;
import com.youngershopping.databinding.FragmentCart2Binding;
import com.youngershopping.pojo.Get_Wallet_Response;
import com.youngershopping.pojo.PinCodeResponse;
import com.youngershopping.pojo.cuopne_code_response;
import com.youngershopping.pojo.list_cart_response;
import com.youngershopping.pojo.list_of_cart;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.WalletActivity;
import com.youngershopping.ui.account.address.AddressListActivity;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.home.ui2.HomeActivity2;
import com.youngershopping.view.stepview.StateProgressBar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


    String payment_mode = "online";


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
                if (charSequence.toString().length() == 0) {
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
                        binding.stateProgressBar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                        sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                        HideAll();
                        binding.cartPaymentType.setVisibility(View.VISIBLE);
                        Log.d("TAG"," Previous 3 = ");
                    } else if (step == 4) {
                        com = 0.0;
                        w_com = 0.0;
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
                payment_mode = "online";
                sendPin(SharePref.getetLoginzip("c_zip",getActivity()));
                binding.rd4.setChecked(false);
                binding.rd5.setChecked(false);
                break;
            case R.id.rd4:
                payment_mode = "Cash";
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

//                    Toast.makeText(getActivity(), "Unity 3D", Toast.LENGTH_LONG).show();
                    binding.llWalletAmount.setVisibility(View.VISIBLE);
                    binding.txtwalletAount.setText("-"+w_a+" ₹");
                    w_com = com - w_a;
                    binding.txtPayableAmount.setText(""+(com-w_a)+" ₹");
                }else{
                    wallet_status = "false";
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
                    if (wallet_status.equals("true")){
                        binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);

                        if (type.equals("0")){
                            // Precentage
                            double precentage = (w_com * (Integer.parseInt(price_pre)))/100;
                            binding.txtPayableAmount.setText(""+(w_com-precentage)+" ₹");
                        }else{
                            binding.txtPayableAmount.setText(""+(w_com-(Integer.parseInt(price_pre)))+" ₹");
                            // Amount
                        }
                    }else{
                        binding.edCoupen.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);
                        if (type.equals("0")){
                            // Precentage
                            double precentage = (com * (Integer.parseInt(price_pre)))/100;
                            binding.txtPayableAmount.setText(""+(com-precentage)+" ₹");
                        }else{
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


}
