package com.youngershopping;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.databinding.DialogTwoButtonsBinding;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;
import com.google.gson.Gson;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ANDROID on 6/17/2017.
 */

public class BaseApp extends AppCompatActivity {
    private static final int RESTART_DELAY = 200;
    private String TAG = "BaseApp";
    private Activity activity = BaseApp.this;
    private static SharedPreferences mediaPrefs = null;
    private Gson gson = null;
    protected ActionBar actionBar;


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
    }


    public SharedPreferences getSharedPreferences() {
        if (mediaPrefs == null) {
            mediaPrefs = getSharedPreferences(Constants.MediaPrefs, Context.MODE_PRIVATE);
        }
        return mediaPrefs;
    }

    private AlertDialog progressDialog = null;

    public void initProgressDialog() {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_loading, null);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(activity);
        builder1.setView(alertLayout);
        builder1.setCancelable(true);

        progressDialog = builder1.create();
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    public void hideKeyboard() {
        // Check if no view has focus:
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    View view = getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputManager = (InputMethodManager)
                                getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void CameraStrictMode() {
        //Todo solve camera error
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    public void showProgressDialog(final @StringRes Integer messageId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    } else {
                        initProgressDialog();
                        progressDialog.show();
                    }
//                    progressDialog.setMessage(getString(messageId));
                } catch (Exception ex) {
                    progressDialog = null;
                    showProgressDialog();
                }
            }
        });
    }

    public void showProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    } else {
                        initProgressDialog();
                        progressDialog.show();
                    }
                } catch (Exception ex) {
                    progressDialog = null;
                    showProgressDialog();
                }
            }
        });
    }

    public void hideProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public void showToast(String string) {
        Utils.showToast(activity, string);
    }

    public void showToast_onThread(String string) {
        hideProgressDialog();
        Utils.showToast_onThread(activity, string);
    }

    public AlertDialog showTwoButtonsDialog(String title, String message, String txtPositive,
                                            String txtNagative) {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final DialogTwoButtonsBinding alertLayout = DataBindingUtil.inflate(flater, R.layout.dialog_two_buttons, null, false);

        dialogBuilder.setView(alertLayout.getRoot());
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        if (title == null || title.trim().isEmpty()) {
            alertLayout.view.setVisibility(View.GONE);
            alertLayout.txtTitle.setVisibility(View.GONE);
        } else {
            alertLayout.txtTitle.setText(title);
        }

        alertLayout.txtSubTitle.setText(message);
        alertLayout.btnPositive.setText(txtPositive);
        if (txtNagative.isEmpty()) {
            alertLayout.btnNagative.setVisibility(View.GONE);
        } else {
            alertLayout.btnNagative.setText(txtNagative);
        }

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }


    // Quickblox
    public void restartApp(Context context) {
        // Application needs to restart when user declined some permissions at runtime
        Intent restartIntent = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
        PendingIntent intent = PendingIntent.getActivity(context, 0, restartIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC, System.currentTimeMillis() + RESTART_DELAY, intent);
        System.exit(0);
    }
}
