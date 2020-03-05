package com.youngershopping.adapter.product.list;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawProductFilterResultBinding;
import com.youngershopping.databinding.RawProductFilterTypeBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.utils.Constants;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductFilterTypeAdapter extends RecyclerView.Adapter<ProductFilterTypeAdapter.MyViewHolder> {

    private List<String> mListData;
    private Activity activity;
    private int pos;

    public ProductFilterTypeAdapter(Activity con, List<String> mListData,int pos) {
        this.activity = con;
        this.mListData = mListData;
        this.pos = pos;
    }
    public void Refresh(int position) {
        this.pos = position;
        notifyDataSetChanged();
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_product_filter_type,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtFilterType.setText(mListData.get(position));
        if (pos == position){
            holder.binding.txtFilterType.setBackgroundColor(activity.getResources().getColor(R.color.linelight1));
        }else{
            holder.binding.txtFilterType.setBackgroundColor(activity.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductFilterTypeBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ListItemClickInterface callback = (ListItemClickInterface) activity;
            callback.onItemsSelectedClick(getPosition(), Constants.clickType);
        }
    }


}

