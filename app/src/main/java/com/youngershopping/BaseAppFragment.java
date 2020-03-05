package com.youngershopping;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.youngershopping.databinding.DialogTwoButtonsBinding;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;
import com.google.gson.Gson;

/**
 * Created by ANDROID on 6/17/2017.
 */

public class BaseAppFragment extends Fragment {

    private String TAG = "BaseApp";
    private static SharedPreferences mediaPrefs = null;
    private Gson gson = null;


    public SharedPreferences getSharedPreferences(Activity context) {
        if (mediaPrefs == null) {
            mediaPrefs = context.getSharedPreferences(Constants.MediaPrefs, Context.MODE_PRIVATE);
        }
        return mediaPrefs;
    }

    private AlertDialog progressDialog = null;

    public void initProgressDialog(Activity context) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_loading, null);
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setView(alertLayout);
        builder1.setCancelable(true);

        progressDialog = builder1.create();
        progressDialog.setCancelable(true);
        progressDialog.getWindow().setBackgroundDrawableResource(R.color.transparent);
    }

    public void hideKeyboard(final Activity context) {
        // Check if no view has focus:
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    context.getWindow().setSoftInputMode(
                            WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                    View view = context.getCurrentFocus();
                    if (view != null) {
                        InputMethodManager inputManager = (InputMethodManager)
                                context.getSystemService(Context.INPUT_METHOD_SERVICE);
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

    public void showProgressDialog(final Activity context) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progressDialog != null && !progressDialog.isShowing()) {
                        progressDialog.show();
                    } else {
                        initProgressDialog(context);
                        progressDialog.show();
                    }
                } catch (Exception ex) {
                    progressDialog = null;
                    showProgressDialog(context);
                }
            }
        });
    }

    public void dismissProgressDialog(Activity context) {
        context.runOnUiThread(new Runnable() {
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


    public void requestFocus(View view, Activity context) {
        if (view.requestFocus()) {
            context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public Gson getGson() {
        if (gson == null) {
            gson = new Gson();
        }
        return gson;
    }

    public void showToast(String string, Activity context) {
        Utils.showToast(context, string);
    }

    public void showToast_onThread(String string, Activity context) {
        dismissProgressDialog(context);
        Utils.showToast_onThread(context, string);
    }

    public AlertDialog showTwoButtonsDialog(String title, String message, String txtPositive,
                                            String txtNagative, Activity context) {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
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


}
