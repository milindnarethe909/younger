package com.youngershopping.ui.start;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.ActivityPermissionBinding;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.user.LoginActivity;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;


public class SplashActivity extends BaseApp {

    private static final String TAG = SplashActivity.class.getName();
    private String[] PERMISSIONS = {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private static final int CAMERA_MIC_PERMISSION_REQUEST_CODE = 1;
    private Activity activity = SplashActivity.this;
    private ActivityPermissionBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_permission);
        String android_id = Settings.Secure.getString(getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Utils.storeStateOfString(getSharedPreferences(), Constants.param_device_id, android_id);

        Handler han = new Handler();
        han.postDelayed(new Runnable() {
            @Override
            public void run() {
                permission();
            }
        }, 3000);

//        getFCM_ID();
    }


//    private void getFCM_ID() {
//        FirebaseInstanceId.getInstance().getInstanceId()
//                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
//                        if (!task.isSuccessful()) {
//                            Log.w(TAG, "getInstanceId failed", task.getException());
//                            return;
//                        }
//
//                        // Get new Instance ID token
//                        String token = task.getResult().getToken();
//
////                        GcmPubSub fa = GcmPubSub.getInstance(activity);
////                        for (String str : TOPICS) {
////                            try {
////                                fa.subscribe(token, "/topics/" + str, null);
////                            } catch (IOException e) {
////                                e.printStackTrace();
////                            }
////                        }
//                        // Log and toast
//                        Log.e(TAG, token);
//                        Utils.storeStateOfString(getSharedPreferences(), Constants.FCM_id, token);
//                    }
//                });
//    }


    private void permission() {
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, CAMERA_MIC_PERMISSION_REQUEST_CODE);
        } else {
            next();
        }
    }

    public boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    void next() {
//        startActivity(new Intent(SplashActivity.this, TourActivity.class));

        if (SharePref.getetLoginStatus("status_login",getApplicationContext()).equals("false")){
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        }else {
            startActivity(new Intent(SplashActivity.this, HomeActivity.class));
            finish();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CAMERA_MIC_PERMISSION_REQUEST_CODE) {
            boolean PermissionGranted = true;

            for (int grantResult : grantResults) {
                PermissionGranted &= grantResult == PackageManager.PERMISSION_GRANTED;
            }

            if (PermissionGranted) {
                next();
            } else {
                showToast(getResources().getString(R.string.permission_requried));
                finish();
            }
        }
    }


}
