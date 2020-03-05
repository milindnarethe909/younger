package com.youngershopping.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityHomebaseBinding;

public abstract class HomeBaseActivity extends BaseApp {

    private String TAG = HomeBaseActivity.class.getSimpleName();
    private Activity con = HomeBaseActivity.this;
    private ActivityHomebaseBinding binding;
    private int page = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_homebase);
    }


//    protected abstract void ClickonHomeActivity();


//    protected void setHomeActivity() {
//        Intent intent = new Intent(con, HomeCategoryActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(intent);
//        finish();
//    }

    protected LinearLayoutCompat getMiddleContent() {
        return binding.middlelayout;
    }


}