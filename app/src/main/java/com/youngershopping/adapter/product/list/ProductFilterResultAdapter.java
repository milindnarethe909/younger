package com.youngershopping.adapter.product.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawProductFilterResultBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.utils.Constants;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductFilterResultAdapter extends RecyclerView.Adapter<ProductFilterResultAdapter.MyViewHolder> {

    private List<String> mListData;
    private List<String> mListData2;
    private Activity activity;

    public ProductFilterResultAdapter(Activity con, List<String> mListData, List<String> mListData2) {
        this.activity = con;
        this.mListData = mListData;
        this.mListData2 = mListData2;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_product_filter_result,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtFilterType.setText(mListData.get(position)+" :");
        holder.binding.txtFilterResult.setText(mListData2.get(position));
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductFilterResultBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ListItemClickInterface callback = (ListItemClickInterface) activity;
            callback.onItemsSelectedClick(getPosition(), Constants.clickResult);
        }
    }


}

