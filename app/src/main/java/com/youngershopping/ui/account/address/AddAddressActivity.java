package com.youngershopping.ui.account.address;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
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
import com.youngershopping.databinding.ActivityAddaddressBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import dmax.dialog.SpotsDialog;

public class AddAddressActivity extends BaseApp implements ListItemClickInterface, View.OnClickListener {
    private ActivityAddaddressBinding binding;
    private Activity activity = AddAddressActivity.this;
    private String TAG = AddAddressActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_addaddress);
        init();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(getIntent().getStringExtra(Constants.name));

        if(!(SharePref.getetLoginaddress("c_address",getApplicationContext()).equals(""))) {
            binding.edtStreetAddress.setText(SharePref.getetLoginaddress("c_address", getApplicationContext()));
        }

        if(!(SharePref.getetLogincity("c_city",getApplicationContext()).equals(""))) {
            binding.edtCity.setText(SharePref.getetLoginaddress("c_city", getApplicationContext()));
        }

        if(!(SharePref.getetLoginzip("c_zip",getApplicationContext()).equals(""))) {
            binding.edtPincode.setText(SharePref.getetLoginaddress("c_zip", getApplicationContext()));
        }

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
    public void onItemsSelectedClick(int position, String type) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSubmit:
                validation();
        }
    }

    private void validation() {
        String StreetAddress = binding.edtStreetAddress.getText().toString().trim();
        String City = binding.edtCity.getText().toString().trim();
        String pin = binding.edtPincode.getText().toString().trim();
        String country = binding.edtCountry.getText().toString().trim();

        if (StreetAddress.length() <= 0){
            binding.edtStreetAddress.requestFocus();
            binding.edtStreetAddress.setError("Address is reuired");
        }else if (City.length() <= 0){
            binding.edtCity.requestFocus();
            binding.edtCity.setError("City is reuired");
        }else if (pin.length() <= 0){
            binding.edtPincode.requestFocus();
            binding.edtPincode.setError("Pin Code is reuired");
        }else if ((pin.length() <6 || pin.length() >7)){
            binding.edtPincode.requestFocus();
            binding.edtPincode.setError("Pin Code minimum 6 digit");
        }else if (country.length() <=0){
            binding.edtCountry.requestFocus();
            binding.edtCountry.setError("Country is reuired");
        }else{
            sendAddress(StreetAddress,City,pin,country);
        }

    }

    private void sendAddress(final String streetAddress, final String city, final String pin, String country) {
        final AlertDialog alertDialog= new
                SpotsDialog.Builder().setContext(AddAddressActivity.this).build();
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
                alertDialog.dismiss();
                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");
                    if (status == true){
                        Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                        SharePref.setLoginaddress("c_address",streetAddress, getApplicationContext());
                        SharePref.setLoginaddress("c_city",city ,getApplicationContext());
                        SharePref.setLoginaddress("c_zip",pin ,getApplicationContext());

                        binding.edtStreetAddress.setText(SharePref.getetLoginaddress("c_address", getApplicationContext()));
                        binding.edtStreetAddress.setText(SharePref.getetLogincity("c_city", getApplicationContext()));
                        binding.edtStreetAddress.setText(SharePref.getetLoginzip("c_zip", getApplicationContext()));
                        startActivity(new Intent(getApplicationContext(), AddressListActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        finish();

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
                params.put("id",SharePref.getetLoginId("c_id",getApplicationContext()));
                params.put("name",SharePref.getetLoginName("c_name",getApplicationContext()));
                params.put("phone",SharePref.getetLoginphoto("c_mob",getApplicationContext()));
                params.put("city",city);
                params.put("address",streetAddress);
                params.put("pincode",pin);
                Log.d(TAG,"Address Params = "+params.toString());
                return params;

            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
