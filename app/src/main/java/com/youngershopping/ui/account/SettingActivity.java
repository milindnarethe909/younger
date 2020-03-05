package com.youngershopping.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivitySettingBinding;

public class SettingActivity extends BaseApp {
    private ActivitySettingBinding binding;
    private Activity activity = SettingActivity.this;
    private String TAG = SettingActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_setting);
        init();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
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

}
