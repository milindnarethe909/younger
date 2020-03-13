package com.youngershopping.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.payumoney.core.PayUmoneyConfig;
import com.payumoney.core.PayUmoneyConstants;
import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;
import com.payumoney.sdkui.ui.utils.ResultModel;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.pojo.Wallet_Payment_Response;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.home.ui1.HomeActivity;

import com.youngershopping.utils.AppEnvironment;
import com.youngershopping.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class WalletActivity extends AppCompatActivity implements View.OnClickListener {
    Intent intent;

    private final int ADD_CARD_CODE = 435;

    private Button add_fund_button;
    private ProgressDialog loadingDialog;
    private CardView add_money_card;

    private Button add_money_button;
    private EditText money_et;
    private TextView balance_tv;
    private String session_token;
    private Button one, two, three;
    private double update_amount = 0;
    //    private ArrayList<CardInfo> cardInfoArrayList;
    private String currency = "";
    //    private CustomDialog customDialog;
    private Context context;
    private TextView currencySymbol;
    private ImageView backArrow;

    String walletBalance = "0";

    String TAG = WalletActivity.class.getSimpleName();

    ApiInterface apiInterface;

    private PayUmoneySdkInitializer.PaymentParam mPaymentParams;
    int MY_SOCKET_TIMEOUT_MS = 30000; // 30 seconds. You can change it

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
        getSupportActionBar().hide();

        Bundle bundle = getIntent().getExtras();
        walletBalance = bundle.getString("wallet_amount");
        apiInterface = APIClient.getClient().create(ApiInterface.class);
//        cardInfoArrayList = new ArrayList<>();
        add_fund_button = (Button) findViewById(R.id.add_fund_button);
        add_money_card = (CardView) findViewById(R.id.add_money_card);
        balance_tv = (TextView) findViewById(R.id.balance_tv);
        currencySymbol = (TextView) findViewById(R.id.currencySymbol);
        backArrow = (ImageView) findViewById(R.id.backArrow);
        context = this;

//        currencySymbol.setText(SharedHelper.getKey(context,"currency"));
        money_et = (EditText) findViewById(R.id.money_et);
        one = (Button) findViewById(R.id.one);
        two = (Button) findViewById(R.id.two);
        three = (Button) findViewById(R.id.three);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        backArrow.setOnClickListener(this);
        balance_tv.setText("₹ "+walletBalance);
        /*one.setText(SharedHelper.getKey(context,"currency")+"199");
        two.setText(SharedHelper.getKey(context,"currency")+"59balance_tv9");
        three.setText(SharedHelper.getKey(context,"currency")+"1099");*/

        one.setText("₹100");
        two.setText("₹200");
        three.setText("₹300");

        float alpha = 0.45f;
        AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
        alphaUp.setFillAfter(true);
        add_fund_button.startAnimation(alphaUp);


        money_et.addTextChangedListener(new TextWatcher() {
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
                    add_fund_button.startAnimation(alphaUp);

                }
                else {

                    float alpha = 1f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    add_fund_button.startAnimation(alphaUp);

                }
                if (count == 1 || count == 0) {
                    one.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                    two.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                    three.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        add_fund_button.setOnClickListener(this);
        loadingDialog = new ProgressDialog(this);
        loadingDialog.setIndeterminate(true);
        loadingDialog.setMessage("Please wait...");

        add_money_card.setVisibility(View.VISIBLE);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Constants.temp, "3");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.add_fund_button:
                if (money_et.getText().toString().isEmpty() || money_et.getText().toString().equalsIgnoreCase("0")) {
                    update_amount = 0;
                    Toast.makeText(this, "Enter an amount greater than 0", Toast.LENGTH_SHORT).show();
                    float alpha = 0.45f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    add_fund_button.startAnimation(alphaUp);
                } else {
                    update_amount = Double.parseDouble(money_et.getText().toString());
                    //  payByPayPal(update_amount);
//                    if(cardInfoArrayList.size() > 0){
//                        showChooser();
//                    }else{
//                        gotoAddCard();
//                    }

                    launchPayUMoneyFlow();
                }
                break;

            case R.id.backArrow:
                GoToMainActivity();
                break;

            case R.id.one:
                one.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                two.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                three.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                money_et.setText("100");
                break;
            case R.id.two:
                one.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                two.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                three.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                money_et.setText("200");
                break;
            case R.id.three:
                one.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                two.setBackground(getResources().getDrawable(R.drawable.review_bg_money));
                three.setBackground(getResources().getDrawable(R.drawable.border_stroke_new));
                money_et.setText("300");
                break;
        }

    }

    private void GoToMainActivity() {
        intent = new Intent(this, HomeActivity.class);
        intent.putExtra(Constants.temp, "3");
        startActivity(intent);
    }

    public void checkBalanceStatus(String wallet_balance){

        double bal = Double.parseDouble(wallet_balance);

        if(bal < 49.0){
            findViewById(R.id.lblMin).setVisibility(View.VISIBLE);

        } else {
            findViewById(R.id.lblMin).setVisibility(View.GONE);
        }

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
        builder.setAmount(""+update_amount)
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

            PayUmoneyFlowManager.startPayUMoneyFlow(mPaymentParams, WalletActivity.this, R.style.AppTheme_default, false);

        } catch (Exception e) {
            // some exception occurred
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//            bt_make_payment.setEnabled(true);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                Log.d(TAG + "resp", payuResponse);
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
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }
        }
    }

    public void showToast(String msg) {
        Toast.makeText(getApplicationContext(), "" + msg, Toast.LENGTH_SHORT).show();
    }





    private void sendPaymetStatus(final String p_id, final String t_id, final String amt, final String ddate, final String status) {
        Log.d("TAG","Response Pay u money = "+t_id+" amount = "+amt+" status = "+status);

        Call<Wallet_Payment_Response> walletPaymentResponseCall = apiInterface.walletAddMoney(SharePref.getetLoginId("c_id",getApplicationContext()),amt,t_id,status);
        walletPaymentResponseCall.enqueue(new Callback<Wallet_Payment_Response>() {
            @Override
            public void onResponse(Call<Wallet_Payment_Response> call, Response<Wallet_Payment_Response> response) {
                Wallet_Payment_Response paymentResponse = response.body();
                String status = paymentResponse.getStatus();
                if (status.equals("true")){
                    Toast.makeText(getApplicationContext(),"Payment Update Successfully in Wallet",Toast.LENGTH_SHORT).show();
                    balance_tv.setText("₹ "+paymentResponse.getData());
                }
            }

            @Override
            public void onFailure(Call<Wallet_Payment_Response> call, Throwable t) {

            }
        });


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


//implementation 'com.payumoney.core:payumoney-sdk:7.4.4'
//        implementation 'com.payumoney.sdkui:plug-n-play:1.6.0'