package com.youngershopping.ui.home.ui1;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityHomeBinding;
import com.youngershopping.ui.home.ui1.fragment.AccountFragment;
import com.youngershopping.ui.home.ui1.fragment.CartFragment;
import com.youngershopping.ui.home.ui1.fragment.HomeFragment;
import com.youngershopping.ui.home.ui2.ui.cart.CartFragment2;
import com.youngershopping.utils.Constants;

public class HomeActivity extends BaseApp {
    private ActivityHomeBinding binding;
    private Activity activity = HomeActivity.this;
    private String TAG = HomeActivity.class.getSimpleName();
    private int page = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity,R.layout.activity_home);
        init();
    }

    private void init() {
        if (getIntent().getExtras() != null){
            if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("2")){
                setCartIcon();
            }else if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("3")){
                setAccountIcon();
            }else{
                setHomeIcon();
            }
        }else{
            setHomeIcon();
        }
    }


    public void ClickHome(View view) {
        if (page != 0) {
            setHomeIcon();
        }
    }

    public void ClickCart(View view) {
        if (page != 1) {
            setCartIcon();
        }
    }

    public void ClickAccount(View view) {
        if (page != 2) {
            setAccountIcon();
        }
    }

    public void setHomeIcon() {
        page = 0;
        HomeFragment fragment = new HomeFragment();
        resetAllIcon(fragment);
        binding.imgHome.setImageResource(R.drawable.menu_ic_home_selected);
        binding.txtHome.setTypeface(null, Typeface.BOLD);
        binding.txtHome.setTextColor(getResources().getColor(R.color.icon_selected));
    }

    public void setCartIcon() {
        page = 1;
        CartFragment2 fragment = new CartFragment2();
        resetAllIcon(fragment);
        binding.imgCart.setImageResource(R.drawable.menu_ic_shopping_cart_selected);
        binding.txtCart.setTypeface(null, Typeface.BOLD);
        binding.txtCart.setTextColor(getResources().getColor(R.color.icon_selected));
    }

    public void setAccountIcon() {
        page = 2;
        AccountFragment fragment = new AccountFragment();
        resetAllIcon(fragment);
        binding.imgAccount.setImageResource(R.drawable.menu_ic_account_selected);
        binding.txtAccount.setTypeface(null, Typeface.BOLD);
        binding.txtAccount.setTextColor(getResources().getColor(R.color.icon_selected));
    }

    public void resetAllIcon(Fragment fragment) {
        Log.e(TAG, "resetAllIcon: "+page );
        binding.imgHome.setImageResource(R.drawable.menu_ic_home_unselected);
        binding.imgCart.setImageResource(R.drawable.menu_ic_shopping_cart_unselected);
        binding.imgAccount.setImageResource(R.drawable.menu_ic_account_unselected);
        binding.txtHome.setTypeface(null, Typeface.NORMAL);
        binding.txtCart.setTypeface(null, Typeface.NORMAL);
        binding.txtAccount.setTypeface(null, Typeface.NORMAL);

        binding.txtHome.setTextColor(getResources().getColor(R.color.icon_unselected));
        binding.txtCart.setTextColor(getResources().getColor(R.color.icon_unselected));
        binding.txtAccount.setTextColor(getResources().getColor(R.color.icon_unselected));

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        // Begin Fragment transaction.
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Replace the layout holder with the required Fragment object.
        fragmentTransaction.replace(R.id.frameContainer, fragment);
        // Commit the Fragment replace action.
        fragmentTransaction.commit();
    }
}
