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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
import com.youngershopping.R;
import com.youngershopping.connection_internet.InternetConnection;
import com.youngershopping.databinding.ActivityRegisterBinding;
import com.youngershopping.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import dmax.dialog.SpotsDialog;


public class RegisterActivity extends BaseApp implements View.OnClickListener {

    private static final String TAG = RegisterActivity.class.getName();
    private ActivityRegisterBinding binding;
    private Activity activity = RegisterActivity.this;

    private EditText edtName,edtEmail,edtMobile,edtPassword,edtCPassword;
    private Button btnRegister;

    AlertDialog.Builder builder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        Window w = getWindow(); // in Activity's onCreate() for instance
//        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if (new InternetConnection().checkConnection(getApplicationContext())) {
            setContentView(R.layout.activity_register);
            init();
        }else{
            ShowDialog();
        }
//        listner();
    }

    private void init() {
        String text = getResources().getString(R.string.alreadyHaveAccount)
                + " <font color='#FFD741'><u>" + getResources().getString(R.string.LoginHere) + "</u></font>"; //set Black color of name
//        binding.txtLogin.setText(Html.fromHtml(text));
        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        edtCPassword = (EditText) findViewById(R.id.edtCPassword);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnRegister.setOnClickListener(this);
    }

    private void listner() {
        binding.txtLogin.setOnClickListener(this);
        binding.btnRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txtLogin:
                finish();
                break;
            case R.id.btnRegister:
//                intent = new Intent(activity, OTPVerifyActivity.class);
//                intent.putExtra(Constants.from, Constants.from_Register);
//                startActivity(intent);

                validation();
                break;
        }
    }

    private void validation() {
        String name = edtName.getText().toString().trim();
        String email = edtEmail.getText().toString().trim();
        String mob = edtMobile.getText().toString().trim();
        String pass = edtPassword.getText().toString().trim();
        String cpass = edtCPassword.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (name.isEmpty()){
            edtName.requestFocus();
            edtName.setError("Name is required");
        }else if (email.isEmpty()){
            edtEmail.requestFocus();
            edtEmail.setError("Email is required");
        }else if (!(email.matches(emailPattern))){
            edtEmail.requestFocus();
            edtEmail.setError("Email id is Invalid");
        }
        else if (mob.isEmpty()){
            edtMobile.requestFocus();
            edtMobile.setError("Mobile no is required");
        }else if (!(isValidMobile(mob))){
            edtMobile.requestFocus();
            edtMobile.setError("Mobile No is minimum 10 digit");
//            Toast.makeText(getApplicationContext(),"Mobile No is minimum 10 digit",Toast.LENGTH_SHORT).show();

        }else if (pass.isEmpty()){
            edtPassword.requestFocus();
            edtPassword.setError("Password is required");
        }else if (cpass.isEmpty()){
            edtCPassword.requestFocus();
            edtCPassword.setError("Confirm Password is required");
        }else if (pass.length() < 6 ){
            edtPassword.requestFocus();
            edtPassword.setError("Password must be at least six characters longPassword must be at least six characters long");
        }
        else if (!(pass.equals(cpass))){
            edtPassword.requestFocus();
            edtPassword.setError("Password doesn't match");
            edtCPassword.requestFocus();
            edtCPassword.setError("Password doesn't match");

        }else{
          SendData();

        }
    }

    private void SendData() {

        final AlertDialog alertDialog= new
                SpotsDialog.Builder().setContext(RegisterActivity.this).build();
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
                        if (object.getString("message").equals("Otp Send Successfully")) {
//                            Toast.makeText(getApplicationContext(),"Register Successfully",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), OTPVerifyActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("otp",object.getString("otp"));
                            intent.putExtra("name",edtName.getText().toString().trim());
                            intent.putExtra("email",edtEmail.getText().toString().trim());
                            intent.putExtra("mob",edtMobile.getText().toString().trim());
                            intent.putExtra("pass",edtPassword.getText().toString().trim());
                            intent.putExtra("key","user");
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
                params.put("user_name", edtName.getText().toString().trim());
                params.put("user_mobile", edtMobile.getText().toString().trim());
                params.put("user_email", edtEmail.getText().toString().trim());
                params.put("password", edtPassword.getText().toString().trim());
                params.put("otp","");
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


    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() > 9 && phone.length() <= 10;
        }
        return false;
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
                        Intent intent = new Intent(getApplicationContext(),RegisterActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
