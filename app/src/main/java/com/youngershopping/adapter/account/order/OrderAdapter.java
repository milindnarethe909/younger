package com.youngershopping.adapter.account.order;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawOrderBinding;
import com.youngershopping.pojo.order_data_list_response;
import com.youngershopping.ui.account.order.OrderDetailsActivity;
import com.youngershopping.ui.account.order.OrderTrackActivity;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.MonthPick_Class;
import com.youngershopping.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {

    private List<String> mListData;
    private List<String> mListDatatemp;
    private Activity activity;
    private List<order_data_list_response> dataListResponses;

    public OrderAdapter(Activity con, List<String> mListData,List<order_data_list_response> dataListResponses) {
        this.activity = con;
        this.mListData = mListData;
        mListDatatemp = new ArrayList<>();
        this.dataListResponses = dataListResponses;
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_macbook1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item1) + ", " + activity.getResources().getString(R.string.dummy_cameraitem5)
//                + ", " + activity.getResources().getString(R.string.dummy_oneplus3));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_mobile1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem3));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item2));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item3));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_dress1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem6));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_order,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText(dataListResponses.get(position).getCustomer_name());
        String status = dataListResponses.get(position).getStatus();
        holder.binding.txtOrderStatus.setText(status);
        holder.binding.txtOrderId.setText(dataListResponses.get(position).getOrder_number());
        holder.binding.txtOrderType.setText("("+dataListResponses.get(position).getMethod()+")");
        holder.binding.txtOrderPrice.setText(dataListResponses.get(position).getPay_amount()+" ₹");
        String date_c = dataListResponses.get(position).getCreated_at();
        String[] d_s = date_c.split(" ");
        String d = d_s[0];
        String[] dd = d.split("-");
        MonthPick_Class monthPickClass = new MonthPick_Class();

        holder.binding.txtOrderDate.setText(""+dd[2]+"-"+monthPickClass.Months(dd[1])+"-"+dd[0]);
//        if (position % 2 == 0) {
//            holder.binding.txtOrderType.setText(activity.getResources().getString(R.string.txt_Order_cod));
//        } else if (position % 3 == 0) {
//            holder.binding.txtOrderType.setText(activity.getResources().getString(R.string.txt_Order_card));
//        } else {
//            holder.binding.txtOrderType.setText(activity.getResources().getString(R.string.txt_Order_paytm));
//        }



        holder.binding.linearDeliveryTime.setVisibility(View.GONE);
        holder.binding.txtCancelOrder.setVisibility(View.INVISIBLE);
        if (Utils.isStatusTrue(status, Constants.orderStatus_Pending)) {
            holder.binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            holder.binding.txtCancelOrder.setVisibility(View.VISIBLE);
            setStatus(holder, R.drawable.status_order_pending, R.color.order_pending);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Processing)) {
            holder.binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            setStatus(holder, R.drawable.status_order_dispatch, R.color.order_orange);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Shipped)) {
            holder.binding.linearDeliveryTime.setVisibility(View.VISIBLE);
            setStatus(holder, R.drawable.status_order_dispatch, R.color.order_orange);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Completed)) {
            setStatus(holder, R.drawable.status_order_delivered, R.color.order_green);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Denied)) {
            setStatus(holder, R.drawable.status_order_cancel, R.color.order_red);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Cancelled)) {
            setStatus(holder, R.drawable.status_order_cancel, R.color.order_red);
        } else if (Utils.isStatusTrue(status, Constants.orderStatus_Prescription_required)) {
            setStatus(holder, R.drawable.status_order_cancel, R.color.order_red);
        } else {
            setStatus(holder, R.drawable.status_order_cancel, R.color.order_red);
        }
    }

    @Override
    public int getItemCount() {
        return dataListResponses == null ? 0 : dataListResponses.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawOrderBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
            binding.txtCancelOrder.setOnClickListener(this);
            binding.txtRepeatOrder.setOnClickListener(this);
            binding.txtTrackOrder.setOnClickListener(this);
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
                    activity.startActivity(intent);
                    break;
                default:
                    intent = new Intent(activity, OrderDetailsActivity.class);
                    intent.putExtra(Constants.status, dataListResponses.get(getPosition()).getStatus());
                    activity.startActivity(intent);
                    break;
            }
        }
    }

    private void setStatus(MyViewHolder holder, int status_image, int color) {
        holder.binding.imgOrderStatus.setImageResource(status_image);
        holder.binding.txtOrderStatus.setTextColor(activity.getResources().getColor(color));
    }


}

