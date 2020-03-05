package com.youngershopping.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.volley.AuthFailureError;
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
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityResetpasswordBinding;
import com.youngershopping.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;


public class ResetPasswordActivity extends BaseApp implements View.OnClickListener {

    private static final String TAG = ResetPasswordActivity.class.getName();
    private ActivityResetpasswordBinding binding;
    private Activity activity = ResetPasswordActivity.this;
    private String st_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_resetpassword);

        Bundle bundle = getIntent().getExtras();
        st_id = bundle.getString("id");
        init();
        listner();
    }

    private void init() {
    }

    private void listner() {
        binding.btnResetPassword.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnResetPassword:
                validation();
//                intent = new Intent(activity, HomeActivity.class);
//                intent.setAction(Intent.ACTION_MAIN);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
//                        | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                startActivity(intent);
//                finish();
                break;
        }
    }

    private void validation() {

        final String p = binding.edtPassword.getText().toString().trim();
        String cp = binding.edtCPassword.getText().toString().trim();

        if (p.isEmpty()){
            binding.edtPassword.requestFocus();
            binding.edtPassword.setError("Password is required");
        }else if (cp.isEmpty()){
            binding.edtCPassword.requestFocus();
            binding.edtCPassword.setError("Password is required");
        }else if (p.length() < 6){
            binding.edtPassword.requestFocus();
            binding.edtCPassword.requestFocus();
            binding.edtPassword.setError("Password must be at least six characters longPassword must be at least six characters long");
//            binding.edtCPassword.setError("Password is required");
        }else if (!(p.equals(cp))){
            binding.edtPassword.requestFocus();
            binding.edtCPassword.requestFocus();
            binding.edtPassword.setError("Password doesn't match");
            binding.edtCPassword.setError("Password doesn't match");
        }else {

            final AlertDialog alertDialog = new
                    SpotsDialog.Builder().setContext(ResetPasswordActivity.this).build();
            alertDialog.setTitle("");
            alertDialog.setMessage("Please wait.....");
            alertDialog.setCancelable(true);
            alertDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, Constants.Reset_Password_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    alertDialog.dismiss();
                    Log.d(TAG,"Reset Password Response = "+response);
                    try {
                        JSONObject object = new JSONObject(response);
                        boolean status = object.getBoolean("status");
                        if (status == true) {
                            Toast.makeText(getApplicationContext(), object.getString("message"), Toast.LENGTH_SHORT).show();
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



                            Intent intent = new Intent(activity, HomeActivity.class);
                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        if (status == false) {
                            Toast.makeText(getApplicationContext(), object.getString(",message"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d(TAG, "Reset Password Error = " + error.getMessage());
                    alertDialog.dismiss();
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", st_id);
                    params.put("password", p);
                    return params;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);
        }
    }


}
