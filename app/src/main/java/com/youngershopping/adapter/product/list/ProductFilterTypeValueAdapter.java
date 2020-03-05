package com.youngershopping.adapter.product.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawProductFilterTypeBinding;
import com.youngershopping.databinding.RawProductFilterTypeValueBinding;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductFilterTypeValueAdapter extends RecyclerView.Adapter<ProductFilterTypeValueAdapter.MyViewHolder> {

    private List<String> mListData;
    private Activity activity;

    public ProductFilterTypeValueAdapter(Activity con, List<String> mListData) {
        this.activity = con;
        this.mListData = mListData;
    }
    public void Refresh(int position) {
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_product_filter_type_value,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.chFilterTypeValue.setText(mListData.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductFilterTypeValueBinding binding;

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

