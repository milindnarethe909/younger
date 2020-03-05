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
import com.youngershopping.connection_internet.InternetConnection;
import com.youngershopping.databinding.ActivityForgotpasswordBinding;
import com.youngershopping.databinding.ActivityRegisterBinding;
import com.youngershopping.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;


public class ForgotPasswordActivity extends BaseApp implements View.OnClickListener {

    private static final String TAG = ForgotPasswordActivity.class.getName();
    private ActivityForgotpasswordBinding binding;
    private Activity activity = ForgotPasswordActivity.this;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

//        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword);
//        init();
//        listner();

        if (new InternetConnection().checkConnection(getApplicationContext())) {
            binding = DataBindingUtil.setContentView(this, R.layout.activity_forgotpassword);
            init();
            listner();
        }else{
            ShowDialog();
        }
    }

    private void init() {
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
                        Intent intent = new Intent(getApplicationContext(),ForgotPasswordActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
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

    private void listner() {
        binding.btnVerifyAccunt.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnVerifyAccunt:
//                intent = new Intent(activity, OTPVerifyActivity.class);
//                intent.putExtra(Constants.from,Constants.from_ForgotPassword);
//                startActivity(intent);
                validation();
                break;
        }
    }

    private void validation() {
        String mob = binding.edtEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (mob.isEmpty()){
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Email id is required");
        }else if (!(mob.matches(emailPattern))){
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Email id is Invalid");
//            Toast.makeText(getApplicationContext(),"Mobile No is minimum 10 digit",Toast.LENGTH_SHORT).show();

        }else{
            sendNo(mob);
        }
    }

    private void sendNo(final String mob) {
        final AlertDialog alertDialog= new
                SpotsDialog.Builder().setContext(ForgotPasswordActivity.this).build();
        alertDialog.setTitle("");
        alertDialog.setMessage("Please wait.....");
        alertDialog.setCancelable(true);
        alertDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, Constants.Forget_Password_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;

                alertDialog.dismiss();
                try {
                    object = new JSONObject(response);
                    Log.d(TAG,"Forget Password Response = "+response);

                    boolean status= object.getBoolean("status");

                    if (status == true){
                        Toast.makeText(getApplicationContext(),object.getString("message"),Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(),OTPVerifyActivity.class);
                        intent.putExtra("id",object.getString("User_id"));
                        intent.putExtra("otp",object.getString("otp"));
                        intent.putExtra("key","otp");
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
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
                Log.d(TAG,"Forget Error = "+error.getMessage());
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
                params.put("email",mob);
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));
        finish();
    }
}
