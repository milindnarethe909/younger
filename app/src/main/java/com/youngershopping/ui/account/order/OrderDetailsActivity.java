package com.youngershopping.ui.account.order;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.snackbar.Snackbar;
import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.adapter.account.order.OrderProductAdapter;
import com.youngershopping.databinding.ActivityOrderdetailBinding;
import com.youngershopping.databinding.DialogProductReturnBinding;
import com.youngershopping.databinding.DialogProductReviewBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;
import com.youngershopping.view.rating.BaseRatingBar;
import com.youngershopping.view.spinner.MaterialSpinner;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailsActivity extends BaseApp implements ListItemClickInterface, View.OnClickListener {
    private ActivityOrderdetailBinding binding;
    private Activity activity = OrderDetailsActivity.this;
    private String TAG = OrderDetailsActivity.class.getSimpleName();
    private OrderProductAdapter orderAdapter;
    private List<Integer> listIcon;
    private List<String> listLable;
    private List<String> listReturnReason;
    private String str_order_status = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_orderdetail);
        init();
        listner();
    }

    private void listner() {
        binding.txtTrackOrder.setOnClickListener(this);
        binding.txtCancelOrder.setOnClickListener(this);
        binding.txtRepeatOrder.setOnClickListener(this);
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setElevation(6);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>" + getResources().getString(R.string.txtOrderDetail) + "</font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow_white);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);

        str_order_status = getIntent().getStringExtra(Constants.status);
        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.commanRecyclerview.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerview.recyclerView.setFocusable(false);

        binding.txtOrderStatus.setText(getIntent().getStringExtra(Constants.status));
        binding.relativeOrderStatus.setBackgroundColor(getResources().getColor(getOrderStatusColor(str_order_status)));
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(getOrderStatusColor(str_order_status))));
        getWindow().setStatusBarColor(getResources().getColor(getOrderStatusColor(str_order_status)));
        fillData();


    }

    private void fillData() {
        listIcon = new ArrayList<>();
        listIcon.add(R.drawable.oneplus1);
//        listIcon.add(R.drawable.oneplus2);
        listIcon.add(R.mipmap.dress1);

        listLable = new ArrayList<>();
        listLable.add(getResources().getString(R.string.dummy_oneplus1));
//        listLable.add(getResources().getString(R.string.dummy_oneplus2));
        listLable.add(getResources().getString(R.string.dummy_dress1));

        orderAdapter = new OrderProductAdapter(activity, listIcon, listLable,str_order_status);
        binding.commanRecyclerview.recyclerView.setAdapter(orderAdapter);

        listReturnReason = new ArrayList<>();
        listReturnReason.add(getResources().getString(R.string.dummy_returnreason1));
        listReturnReason.add(getResources().getString(R.string.dummy_returnreason2));
        listReturnReason.add(getResources().getString(R.string.dummy_returnreason3));
        listReturnReason.add(getResources().getString(R.string.dummy_returnreason4));
        listReturnReason.add(getResources().getString(R.string.dummy_returnreason5));

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.temp) != null) {
                if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("1")) {
                    showReturnDialog(3);
                } else if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("2")) {
                    showReviewDialog();
                }
            }

        }
    }

    private int getOrderStatusColor(String orderStatus) {
        if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Pending)) {
            binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            binding.txtCancelOrder.setVisibility(View.VISIBLE);
            return R.color.yellowoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Processing)) {
            binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            return R.color.orangeoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Shipped)) {
            binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            return R.color.orangeoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Completed)) {
            return R.color.greenoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Denied)) {
            return R.color.redoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Cancelled)) {
            return R.color.redoff;
        } else if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Prescription_required)) {
            return R.color.redoff;
        } else {
            return R.color.redoff;
        }
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

    @Override
    public void onItemsSelectedClick(int position, String type) {

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.txtCancelOrder:
                break;
            case R.id.txtRepeatOrder:
                break;
            case R.id.txtTrackOrder:
                intent = new Intent(activity, OrderTrackActivity.class);
                intent.putExtra(Constants.status, str_order_status);
                activity.startActivity(intent);
                break;
        }
    }

    public AlertDialog showReturnDialog(final int productquantity) {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final DialogProductReturnBinding alertLayout = DataBindingUtil.inflate(flater, R.layout.dialog_product_return, null, false);

        dialogBuilder.setView(alertLayout.getRoot());
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertLayout.spinner.setItems(listReturnReason);
        alertLayout.spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                Snackbar.make(view, item, Snackbar.LENGTH_LONG).show();
            }
        });
        alertLayout.spinner.setOnNothingSelectedListener(new MaterialSpinner.OnNothingSelectedListener() {

            @Override public void onNothingSelected(MaterialSpinner spinner) {
//                Snackbar.make(spinner, "Nothing selected", Snackbar.LENGTH_LONG).show();
            }
        });

        alertLayout.txtQuantity.setText(String.valueOf(1));
        alertLayout.btnNagative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertLayout.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertLayout.imgQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = (String) alertLayout.txtQuantity.getTag();
                int quantity = Integer.parseInt(str);
                if (quantity > 1) {
                    quantity--;
                    alertLayout.txtQuantity.setText(String.valueOf(quantity));
                    alertLayout.txtQuantity.setTag(String.valueOf(quantity));
                }
            }
        });
        alertLayout.imgQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = (String) alertLayout.txtQuantity.getTag();
                int quantity = Integer.parseInt(str);
                if (quantity < productquantity) {
                    quantity++;
                    alertLayout.txtQuantity.setText(String.valueOf(quantity));
                    alertLayout.txtQuantity.setTag(String.valueOf(quantity));
                }
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

    public AlertDialog showReviewDialog() {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        final DialogProductReviewBinding alertLayout = DataBindingUtil.inflate(flater, R.layout.dialog_product_review, null, false);

        dialogBuilder.setView(alertLayout.getRoot());
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertLayout.rotationratingbar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating, boolean fromUser) {
                Log.d(TAG, "RotationRatingBar onRatingChange: " + rating);
            }
        });

        alertLayout.btnNagative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertLayout.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }

}
