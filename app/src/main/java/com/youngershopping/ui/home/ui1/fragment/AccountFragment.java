package com.youngershopping.ui.home.ui1.fragment;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.FragmentAccountBinding;
import com.youngershopping.pojo.Get_Wallet_Response;
import com.youngershopping.pojo.WishlistProduct_Show;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.WalletActivity;
import com.youngershopping.ui.account.FeedbackActivity;
import com.youngershopping.ui.account.NotificationListActivity;
import com.youngershopping.ui.account.OfferListActivity;
import com.youngershopping.ui.account.SettingActivity;
import com.youngershopping.ui.account.address.AddressListActivity;
import com.youngershopping.ui.account.order.OrderListActivity;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.product.ProductListActivity;
import com.youngershopping.ui.user.EditProfileActivity;
import com.youngershopping.ui.user.LoginActivity;
import com.youngershopping.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountFragment extends BaseAppFragment implements View.OnClickListener {
    private FragmentAccountBinding binding;
    private Activity activity;

    ApiInterface apiInterface;

    public AccountFragment() {
        // Required rating_empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        init();
        listner();
        return binding.getRoot();
    }

    private void listner() {
        binding.txtLogout.setOnClickListener(this);
        binding.txtOrder.setOnClickListener(this);
        binding.txtAddress.setOnClickListener(this);
        binding.txtOffer.setOnClickListener(this);
        binding.txtNotification.setOnClickListener(this);
        binding.txtWishlist.setOnClickListener(this);
        binding.txtSettings.setOnClickListener(this);
        binding.txtFeedback.setOnClickListener(this);
        binding.imgEdit.setOnClickListener(this);
        binding.txtWallet.setOnClickListener(this);
        binding.txtRate.setOnClickListener(this);
        binding.txtTermsConditions.setOnClickListener(this);
    }

    private void init() {
        activity = (HomeActivity) getActivity();

        binding.txtUserName.setText(SharePref.getetLoginName("c_name",getContext()));
        binding.txtEmail.setText(SharePref.getetLoginEmail("c_email",getContext()));
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txtLogout:
                Logout();
                break;
            case R.id.imgEdit:
                intent = new Intent(activity, EditProfileActivity.class);
                startActivity(intent);
                break;
            case R.id.txtOrder:
                intent = new Intent(activity, OrderListActivity.class);
                startActivity(intent);
                break;
            case R.id.txtFeedback:
                intent = new Intent(activity, FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.txtNotification:
                intent = new Intent(activity, NotificationListActivity.class);
                startActivity(intent);
                break;
            case R.id.txtAddress:
                intent = new Intent(activity, AddressListActivity.class);
                startActivity(intent);
                break;
            case R.id.txtOffer:
                intent = new Intent(activity, OfferListActivity.class);
                startActivity(intent);
                break;
            case R.id.txtSettings:
                intent = new Intent(activity, SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.txtWishlist:
                ReciveWishList();
//                intent = new Intent(activity, ProductListActivity.class);
//                intent.putExtra(Constants.from, getResources().getString(R.string.account_Wishlist));
//                startActivity(intent);
                break;
            case R.id.txtWallet:
                getWalletAmout();


//                intent = new Intent(activity, WalletActivity.class);
//                startActivity(intent);
                break;

            case R.id.txtRate:
                getRateUs();
                break;
            case R.id.txtTermsConditions:
                startActivity(new Intent(getActivity(),Team_conditionActivity.class));
                break;
        }
    }

    private void getRateUs() {

        Uri uri = Uri.parse("market://details?id=" + getActivity().getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
        try {
            startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("shorturl.at/aghw6")));
        }
    }

    private void getWalletAmout() {
        Call<Get_Wallet_Response> get_wallet_responseCall = apiInterface.getWalletAmount(SharePref.getetLoginId("c_id",getContext()));
        get_wallet_responseCall.enqueue(new Callback<Get_Wallet_Response>() {
            @Override
            public void onResponse(Call<Get_Wallet_Response> call, Response<Get_Wallet_Response> response) {
                Get_Wallet_Response get_wallet_response = response.body();
                String status = get_wallet_response.getStatus();
                if (status.equals("true")){
                    Intent intent = new Intent(activity, WalletActivity.class);
                    intent.putExtra("wallet_amount",""+get_wallet_response.getAmount());
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Get_Wallet_Response> call, Throwable t) {

            }
        });
    }

    private void ReciveWishList() {

        Call<WishlistProduct_Show> showCall = apiInterface.wishlist(SharePref.getetLoginId("c_id",getContext()));
        showCall.enqueue(new Callback<WishlistProduct_Show>() {
            @Override
            public void onResponse(Call<WishlistProduct_Show> call, Response<WishlistProduct_Show> response) {
                WishlistProduct_Show show = response.body();

                Log.d("TAG","Wishlist Response = "+response.body().toString());
                String status = show.getStatus();

                if (status.equals("true")){

                    Intent intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.from, getResources().getString(R.string.account_Wishlist));
                    startActivity(intent);
                }

                if (status.equals("false")){
                    Toast.makeText(getContext(),"My Wish list is not available",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<WishlistProduct_Show> call, Throwable t) {
                Log.d("TAG","Response Error = "+t.getMessage());
            }
        });

    }

    private void Logout() {
        final AlertDialog alertDialog = showTwoButtonsDialog(getResources().getString(R.string.account_Logout),
                getResources().getString(R.string.account_Logout_msg),
                getResources().getString(R.string.Yes), getResources().getString(R.string.No), activity);
        if (alertDialog != null) {
            final AppCompatButton btnPositive = (AppCompatButton) alertDialog.findViewById(R.id.btnNagative);
            final AppCompatButton btnNagative = (AppCompatButton) alertDialog.findViewById(R.id.btnPositive);

            btnPositive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    alertDialog.dismiss();

                }
            });

            btnNagative.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharePref.setLoginStatus("status_login","false",getContext());
                    SharePref.setLoginId("c_id","",getContext());
                    SharePref.setLoginName("c_name","",getContext());
                    SharePref.setLoginEmail("c_email","",getContext());
                    SharePref.setLoginMob("c_mob","",getContext());
                    SharePref.setLoginphoto("c_photo","",getContext());
//                            zip   residency   city    address district    wallet  gendor  status
                    SharePref.setLoginzip("c_zip","",getContext());

                    SharePref.setLoginresidency("c_residency","",getContext());

                    SharePref.setLogincity("c_city","",getContext());

                    SharePref.setLoginaddress("c_address","",getContext());

                    SharePref.setLogindistrict("c_district","",getContext());

                    SharePref.setLoginwallet("c_wallet","",getContext());

                    SharePref.setLogingendor("c_gendor","",getContext());

                    SharePref.setLoginStatus("c_status","",getContext());

                    getContext().startActivity(new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP));


                    alertDialog.dismiss();
                }
            });

            alertDialog.setCancelable(false);
        }
    }
}
