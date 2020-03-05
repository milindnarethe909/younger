package com.youngershopping.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawMainBinding;
import com.youngershopping.ui.account.FeedbackActivity;
import com.youngershopping.ui.account.NotificationListActivity;
import com.youngershopping.ui.account.OfferListActivity;
import com.youngershopping.ui.account.SettingActivity;
import com.youngershopping.ui.account.address.AddAddressActivity;
import com.youngershopping.ui.account.address.AddressListActivity;
import com.youngershopping.ui.account.order.OrderDetailsActivity;
import com.youngershopping.ui.account.order.OrderListActivity;
import com.youngershopping.ui.account.order.OrderTrackActivity;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.home.ui1.HomeCategoryActivity;
import com.youngershopping.ui.home.ui2.HomeActivity2;
import com.youngershopping.ui.product.ProductDetailActivity;
import com.youngershopping.ui.product.ProductListActivity;
import com.youngershopping.ui.product.ProductReviewActivity;
import com.youngershopping.ui.user.EditProfileActivity;
import com.youngershopping.ui.user.ForgotPasswordActivity;
import com.youngershopping.ui.user.LoginActivity;
import com.youngershopping.ui.user.OTPVerifyActivity;
import com.youngershopping.ui.user.RegisterActivity;
import com.youngershopping.ui.user.ResetPasswordActivity;
import com.youngershopping.utils.Constants;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<String> mListData;
    private Activity activity;

    public MainAdapter(Activity con, List<String> mListData) {
        this.activity = con;
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_main,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText((position + 1) + ". " + mListData.get(position));

        if (position == 0 || position == 7 || position == 8 || position == 13) {
            holder.binding.txtTitle.setTextColor(activity.getResources().getColor(R.color.cartbudget));
        } else {
            holder.binding.txtTitle.setTextColor(activity.getResources().getColor(R.color.text));
        }
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawMainBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = null;
            switch (getPosition()) {
                case 0:
                    intent = new Intent(activity, HomeActivity.class);
                    break;
                case 1:
                    intent = new Intent(activity, HomeCategoryActivity.class);
                    break;
                case 2:
                    intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.temp, "1");
                    intent.putExtra(Constants.from, activity.getResources().getString(R.string.Productlist));
                    break;
                case 3:
                    intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.from, activity.getResources().getString(R.string.Productlist));
                    break;
                case 4:
                    intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.temp, "2");
                    intent.putExtra(Constants.from, activity.getResources().getString(R.string.Productlist));
                    break;
                case 5:
                    SharePref.setwishlist("wish","false",activity);
                    intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra("product_id","72");
                    break;
                case 6:
                    intent = new Intent(activity, ProductReviewActivity.class);
                    break;
                case 7:
                    intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra(Constants.temp, "2");
                    break;
                case 8:
                    intent = new Intent(activity, LoginActivity.class);
                    break;
                case 9:
                    intent = new Intent(activity, RegisterActivity.class);
                    break;
                case 10:
                    intent = new Intent(activity, OTPVerifyActivity.class);
                    intent.putExtra(Constants.from, Constants.from_Register);
                    break;
                case 11:
                    intent = new Intent(activity, ForgotPasswordActivity.class);
                    break;
                case 12:
                    intent = new Intent(activity, ResetPasswordActivity.class);
                    break;
                case 13:
                    intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra(Constants.temp, "3");
                    break;
                case 14:
                    intent = new Intent(activity, EditProfileActivity.class);
                    break;
                case 15:
                    intent = new Intent(activity, OfferListActivity.class);
                    break;
                case 16:
                    intent = new Intent(activity, NotificationListActivity.class);
                    break;
                case 17:
                    intent = new Intent(activity, OrderListActivity.class);
                    break;
                case 18:
                    intent = new Intent(activity, OrderDetailsActivity.class);
                    intent.putExtra(Constants.status, Constants.orderStatus_Shipped);
                    break;
                case 19:
                    intent = new Intent(activity, OrderTrackActivity.class);
                    break;
                case 20:
                    intent = new Intent(activity, OrderDetailsActivity.class);
                    intent.putExtra(Constants.status, Constants.orderStatus_Completed);
                    intent.putExtra(Constants.temp, "1");
                    break;
                case 21:
                    intent = new Intent(activity, OrderDetailsActivity.class);
                    intent.putExtra(Constants.status, Constants.orderStatus_Completed);
                    intent.putExtra(Constants.temp, "2");
                    break;
                case 22:
                    intent = new Intent(activity, AddressListActivity.class);
                    break;
                case 23:
                    intent = new Intent(activity, AddAddressActivity.class);
                    intent.putExtra(Constants.name, activity.getResources().getString(R.string.txt_AddAddress));
                    break;
                case 24:
                    intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.from, activity.getResources().getString(R.string.account_Wishlist));
                    break;
                case 25:
                    intent = new Intent(activity, SettingActivity.class);
                    break;
                case 26:
                    intent = new Intent(activity, FeedbackActivity.class);
                    break;
                case 27:
                    intent = new Intent(activity, HomeActivity2.class);
                    break;
                default:
                    intent = new Intent(activity, HomeActivity.class);
                    break;
            }
            activity.startActivity(intent);
        }
    }


}

