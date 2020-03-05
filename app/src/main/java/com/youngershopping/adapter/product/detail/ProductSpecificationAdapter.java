package com.youngershopping.adapter.product.detail;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawHomedataBinding;
import com.youngershopping.databinding.RawProductSpecificationBinding;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductSpecificationAdapter extends RecyclerView.Adapter<ProductSpecificationAdapter.MyViewHolder> {

    private List<String> mListData;
    private List<String> mListDatatemp;
    private Activity activity;

    public ProductSpecificationAdapter(Activity con, List<String> mListData,List<String> mListDatatemp) {
        this.activity = con;
        this.mListData = mListData;
        this.mListDatatemp = mListDatatemp;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_product_specification,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText(mListData.get(position));
        holder.binding.txtSubTitle.setText(mListDatatemp.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductSpecificationBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);

            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
        }
    }


}

