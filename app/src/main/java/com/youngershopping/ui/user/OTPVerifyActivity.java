package com.youngershopping.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youngershopping.BaseApp;
import com.youngershopping.connection_internet.InternetConnection;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityOtpverifyBinding;
import com.youngershopping.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class OTPVerifyActivity extends BaseApp implements View.OnClickListener {

    private static final String TAG = OTPVerifyActivity.class.getName();
    private ActivityOtpverifyBinding binding;
    private Activity activity = OTPVerifyActivity.this;
    AlertDialog.Builder builder;
    String st_otp = "",st_name = "",st_email = "",st_mob = "",st_password = "",st_user_id = "",st_key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (new InternetConnection().checkConnection(getApplicationContext())) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_otpverify);

            init();
            Bundle bundle = getIntent().getExtras();
            if (bundle.getString("key").equals("otp")){
                st_key = bundle.getString("key");
                st_otp = bundle.getString("otp");
                st_user_id = bundle.getString("id");

            }else {

                st_otp = bundle.getString("otp");
                st_name = bundle.getString("name");
                st_email = bundle.getString("email");
                st_mob = bundle.getString("mob");
                st_password = bundle.getString("pass");
            }




            listner();
        }else{
            ShowDialog();
        }
    }

    private void init() {
    }

    private void listner() {
        binding.btnSubmit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSubmit:
                validationOTP();
//                if (getIntent().getStringExtra(Constants.from).equalsIgnoreCase(Constants.from_Register)) {
//                    intent = new Intent(activity, HomeActivity.class);
//                    intent.setAction(Intent.ACTION_MAIN);
//                    intent.addCategory(Intent.CATEGORY_HOME);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    startActivity(new Intent(activity, ResetPasswordActivity.class));
//                }
                break;
        }
    }

    private void validationOTP() {

        String otp = binding.otpView.getText().toString().trim();


        if (otp.isEmpty()){
            binding.otpView.requestFocus();
            binding.otpView.setError("OTP is required");
        }else if (!(st_otp.length() == 6)){
            binding.otpView.requestFocus();
            binding.otpView.setError("OTP minimum 6 digit");
        }else if (!(otp.equals(st_otp))){
            Toast.makeText(getApplicationContext(),"OTP is not match",Toast.LENGTH_SHORT).show();
        }else{

            if (st_key.equals("otp")){
                Intent intent = new Intent(getApplicationContext(),ResetPasswordActivity.class);
                intent.putExtra("id",st_user_id);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }else{
                sendOTP();
            }

        }
    }

    private void sendOTP() {
        final AlertDialog alertDialog= new
                SpotsDialog.Builder().setContext(OTPVerifyActivity.this).build();
        alertDialog.setTitle("");
        alertDialog.setMessage("Please wait.....");
        alertDialog.setCancelable(true);
        alertDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.registration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Registarion Response = "+response);
                alertDialog.dismiss();
//                {"status":true,"message":"Register Successfully"}

                JSONObject object = null;

                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");

                    if (status == true){
                        if (object.getString("message").equals("Register Successfully")) {
                            Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                        }


                    }
                    if (status == false){
                        Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Registion Error = "+error.getMessage());
                alertDialog.dismiss();
                if (error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_name", st_name);
                params.put("user_mobile", st_mob);
                params.put("user_email", st_email);
                params.put("password", st_password);
                params.put("otp","1");
                Log.d(TAG,"Registarion params = "+params);
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(
                6000*20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void ShowDialog() {
        builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage("") .setTitle("Internet Connection");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getApplicationContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Internet Connection");
        alert.show();
    }

}
