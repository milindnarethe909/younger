package com.youngershopping.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
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
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.connection_internet.InternetConnection;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityLoginBinding;
import com.youngershopping.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class LoginActivity extends BaseApp implements View.OnClickListener {

    private static final String TAG = LoginActivity.class.getName();
    private ActivityLoginBinding binding;
    private Activity activity = LoginActivity.this;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (new InternetConnection().checkConnection(getApplicationContext())){
            this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
            init();
            listner();
        }else{
            ShowDialog();
        }
        

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

    private void init() {
        String text = getResources().getString(R.string.dontHaveAccount) + " <font color='#FFD741'><u>" + getResources().getString(R.string.SignupNow) + "</u></font>"; //set Black color of name
        binding.txtRegister.setText(Html.fromHtml(text));

    }

    private void listner() {
        binding.txtRegister.setOnClickListener(this);
        binding.btnLogin.setOnClickListener(this);
        binding.txtForgotPassword.setOnClickListener(this);
        binding.imgSocialFB.setOnClickListener(this);
        binding.imgSocialGoogle.setOnClickListener(this);
        binding.imgSocialTwitter.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        intent = new Intent(activity, HomeActivity.class);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        switch (view.getId()) {
            case R.id.txtRegister:
                startActivity(new Intent(activity, RegisterActivity.class));
                break;
            case R.id.imgSocialFB:
                startActivity(intent);
                finish();
                break;
            case R.id.imgSocialGoogle:
                startActivity(intent);
                finish();
                break;
            case R.id.imgSocialTwitter:
                startActivity(intent);
                finish();
                break;
            case R.id.btnLogin:

                validation();
//                startActivity(intent);
//                finish();
                break;
            case R.id.txtForgotPassword:
                startActivity(new Intent(activity, ForgotPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                finish();
                break;
        }
    }

    private void validation() {
        String email = binding.edtEmail.getText().toString().trim();
        String pass = binding.edtPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()){
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Email id & Mobile No. is required");
        }else if (pass.isEmpty()){
            binding.edtPassword.requestFocus();
            binding.edtPassword.setError("Password is required");
        }else{
            Log.i(TAG, "validation Login :  OK");
            sendLogin(email,pass);
        }

//        else if (!(email.matches(emailPattern))){
//            binding.edtEmail.requestFocus();
//            binding.edtEmail.setError("Email id is Invalid");
//        }
    }

    private void sendLogin(final String email, final String pass) {
        final AlertDialog alertDialog= new
                SpotsDialog.Builder().setContext(LoginActivity.this).build();
        alertDialog.setTitle("");
        alertDialog.setMessage("Please wait.....");
        alertDialog.setCancelable(true);
        alertDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.Login_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Login Response = "+response);
                alertDialog.dismiss();
//                {"status":true,"message":"Register Successfully"}

                JSONObject object = null;

                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");

                    if (status == true){
                        if (object.getString("message").equals("Login succesfully")) {
                            Toast.makeText(getApplicationContext(),"Login succesfully",Toast.LENGTH_SHORT).show();

                            JSONArray array = object.getJSONArray("user_detail");
                            JSONObject object1 = array.getJSONObject(0);
                            SharePref.setLoginStatus("status_login","true",getApplicationContext());
                            SharePref.setLoginId("c_id",object1.getString("id"),getApplicationContext());
                            SharePref.setLoginName("c_name",object1.getString("name"),getApplicationContext());
                            SharePref.setLoginEmail("c_email",object1.getString("email"),getApplicationContext());
                            SharePref.setLoginMob("c_mob",object1.getString("phone"),getApplicationContext());
                            SharePref.setLoginphoto("c_photo",object1.getString("photo"),getApplicationContext());
//                            zip   residency   city    address district    wallet  gendor  status
                            SharePref.setLoginzip("c_zip",object1.getString("zip"),getApplicationContext());

                            SharePref.setLoginresidency("c_residency",object1.getString("residency"),getApplicationContext());

                            SharePref.setLogincity("c_city",object1.getString("city"),getApplicationContext());

                            SharePref.setLoginaddress("c_address",object1.getString("address"),getApplicationContext());

                            SharePref.setLogindistrict("c_district",object1.getString("district"),getApplicationContext());

                            SharePref.setLoginwallet("c_wallet",object1.getString("wallet"),getApplicationContext());

                            SharePref.setLogingendor("c_gendor",object1.getString("gendor"),getApplicationContext());

                            SharePref.setLoginStatus("c_status",object1.getString("status"),getApplicationContext());

                            SharePref.setPassword("c_password",pass,getApplicationContext());

                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class)
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
                Log.d(TAG,"Login Error = "+error.getMessage());
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
                params.put("user_email", email);
                params.put("password", pass);
                Log.d(TAG,"LoginActivity params = "+params);
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

}
