package com.youngershopping.adapter.account.order;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawOrderdetailproductBinding;
import com.youngershopping.ui.account.order.OrderDetailsActivity;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.Utils;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class OrderProductAdapter extends RecyclerView.Adapter<OrderProductAdapter.MyViewHolder> {

    private List<Integer> mListData;
    private List<String> mListDatatemp;
    private Activity activity;
    private String orderStatus = "";

    public OrderProductAdapter(Activity con, List<Integer> mListData, List<String> mListDatatemp, String orderStatus) {
        this.activity = con;
        this.mListDatatemp = mListDatatemp;
        this.mListData = mListData;
        this.orderStatus = orderStatus;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_orderdetailproduct,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.img.setImageResource(mListData.get(position));
        holder.binding.txtTitle.setText(mListDatatemp.get(position));

        holder.binding.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
        holder.binding.txtQuantity.setText(activity.getResources().getString(R.string.txtQuantity) + " : " + (position + 1));
        if (Utils.isStatusTrue(orderStatus, Constants.orderStatus_Completed)) {
            holder.binding.linearReturnReview.setVisibility(View.VISIBLE);
        } else {
            holder.binding.linearReturnReview.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawOrderdetailproductBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            binding.txtReturn.setOnClickListener(this);
            binding.txtReview.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.txtReturn:
                    ((OrderDetailsActivity)activity).showReturnDialog((getPosition() + 1));
                    break;
                case R.id.txtReview:
                    ((OrderDetailsActivity)activity).showReviewDialog();
                    break;
            }
        }
    }

}

