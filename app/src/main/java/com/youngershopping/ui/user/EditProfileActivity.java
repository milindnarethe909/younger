package com.youngershopping.ui.user;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
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
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.ActivityEditprofileBinding;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;

public class EditProfileActivity extends BaseApp implements View.OnClickListener {
    private ActivityEditprofileBinding binding;
    private Activity activity = EditProfileActivity.this;
    private String TAG = EditProfileActivity.class.getSimpleName();
    String status1 = "true";
    AlertDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_editprofile);
        init();
        setlistner();
    }

    private void setlistner() {
        binding.cbChangePassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    status1 = "true";
                    binding.linearChangePassword.setVisibility(View.VISIBLE);
                } else {
                    status1 = "false";
                    binding.linearChangePassword.setVisibility(View.GONE);
                }
            }
        });
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        binding.edtName.setText(SharePref.getetLoginName("c_name",getApplicationContext()));
        binding.edtEmail.setText(SharePref.getetLoginEmail("c_email",getApplicationContext()));
        binding.edtMobile.setText(SharePref.getetLoginMob("c_mob",getApplicationContext()));
        binding.txtDeactive.setText(SharePref.getetLoginStatus("c_status",getApplicationContext()));

        binding.btnSubmit.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                validation();
        }
    }

    private void validation() {
        String name = binding.edtName.getText().toString().trim();
        String mob = binding.edtMobile.getText().toString().trim();

        if (status1.equals("false")) {
            if ((name.length() <= 0)) {
                binding.edtName.requestFocus();
                binding.edtName.setError("Name is required");
            } else if ((mob.length() <= 0)) {
                binding.edtMobile.requestFocus();
                binding.edtMobile.setError("Mobile No. is required");
            } else if (!(isValidMobile(mob))) {
                binding.edtMobile.requestFocus();
                binding.edtMobile.setError("Mobile No is minimum 10 digit");
            } else {
                sendEdittextData(name, mob);
            }
        }else{
            if ((name.length() <= 0)) {
                binding.edtName.requestFocus();
                binding.edtName.setError("Name is required");
            } else if ((mob.length() <= 0)) {
                binding.edtMobile.requestFocus();
                binding.edtMobile.setError("Mobile No. is required");
            } else if (!(isValidMobile(mob))) {
                binding.edtMobile.requestFocus();
                binding.edtMobile.setError("Mobile No is minimum 10 digit");
            } else if (binding.edtOldPassword.getText().toString().trim().isEmpty()){
                binding.edtOldPassword.requestFocus();
                binding.edtOldPassword.setError("Old Password is required");
            }else if (binding.edtNewPassword.getText().toString().trim().isEmpty()){
                binding.edtNewPassword.requestFocus();
                binding.edtNewPassword.setError("New Password is required");
            }else if (binding.edtCPassword.getText().toString().trim().isEmpty()){
                binding.edtCPassword.requestFocus();
                binding.edtCPassword.setError("Confirm Password is required");
            }else if (binding.edtNewPassword.getText().toString().trim().length() < 6){
                binding.edtNewPassword.requestFocus();
                binding.edtNewPassword.setError("Password must be at least six characters longPassword must be at least six characters long");
            }else if (!(binding.edtNewPassword.getText().toString().trim().equals(binding.edtCPassword.getText().toString().trim()))){
                binding.edtNewPassword.requestFocus();
                binding.edtNewPassword.setError("New Password & Confirm Password doesn't match");
                binding.edtCPassword.requestFocus();
                binding.edtCPassword.setError("New Password & Confirm Password doesn't match");
            }else if (!(binding.edtOldPassword.getText().toString().trim().equals(SharePref.getPassword("c_password",getApplicationContext())))){
                binding.edtOldPassword.requestFocus();
                binding.edtOldPassword.setError(" Old Password & New Password doesn't match");

            }else{
                sendEdittextData(name, mob);
            }
        }
    }

    private void sendEdittextData(final String name, final String mob) {
        alertDialog= new
                SpotsDialog.Builder().setContext(EditProfileActivity.this).build();
        alertDialog.setTitle("");
        alertDialog.setMessage("Please wait.....");
        alertDialog.setCancelable(true);
        alertDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.Editprofile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG,"Response Editprofile = "+response);

                JSONObject object = null;
//                {"status":true,"message":"Profile Update successfully"}

                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");
                    if (status == true){
                        SharePref.setLoginName("c_name",name,getApplicationContext());
                        SharePref.setLoginMob("c_mob",mob,getApplicationContext());
                        binding.edtName.setText(SharePref.getetLoginName("c_name",getApplicationContext()));
                        binding.edtEmail.setText(SharePref.getetLoginEmail("c_email",getApplicationContext()));
                        binding.edtMobile.setText(SharePref.getetLoginMob("c_mob",getApplicationContext()));
                        binding.txtDeactive.setText(SharePref.getetLoginStatus("c_status",getApplicationContext()));
                        if (status1.equals("true")){
                            sendPassword();
                        }else{
                            alertDialog.dismiss();
                            Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, HomeActivity.class);
                            intent.putExtra(Constants.temp, "3");
                            startActivity(intent);
                            finish();
                        }
//                        startActivity(new Intent(getApplicationContext(), HomeActivity.class).putExtra(Constants.temp, "3").setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//                        finish();

                    }

                    if (status == false){
                        alertDialog.dismiss();
                        Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Edit Profile Error = "+error.getMessage());
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
                params.put("user_id",SharePref.getetLoginId("c_id",getApplicationContext()));
                params.put("name",name);
                params.put("phone",mob);
                params.put("city",SharePref.getetLogincity("c_city",getApplicationContext()));
                params.put("address",SharePref.getetLoginaddress("c_address",getApplicationContext()));
                params.put("pincode",SharePref.getetLoginzip("c_zip",getApplicationContext()));
                Log.d(TAG,"Edit Params = "+params.toString());
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void sendPassword() {
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

                        SharePref.setPassword("c_password",binding.edtNewPassword.getText().toString().trim(),getApplicationContext());

                        Intent intent = new Intent(activity, HomeActivity.class);
                        intent.putExtra(Constants.temp, "3");
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
                params.put("user_id", SharePref.getetLoginId("c_id",getApplicationContext()));
                params.put("password", binding.edtNewPassword.getText().toString().trim());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 9 && phone.length() <= 10;
        }
        return false;
    }
}
