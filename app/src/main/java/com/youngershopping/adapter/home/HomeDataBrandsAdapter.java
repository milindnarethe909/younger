package com.youngershopping.adapter.home;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youngershopping.R;
import com.youngershopping.databinding.RawHomeBrandsBinding;
import com.youngershopping.databinding.RawHomeCategoryBinding;
import com.youngershopping.pojo.band_name_list;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class HomeDataBrandsAdapter extends RecyclerView.Adapter<HomeDataBrandsAdapter.MyViewHolder> {

    private List<band_name_list> mListDataIcon;
    private Activity activity;

    public HomeDataBrandsAdapter(Activity con, List<band_name_list> mListDataIcon) {
        this.activity = con;
        this.mListDataIcon = mListDataIcon;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_home_brands,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+mListDataIcon.get(position).getImage()).into(holder.binding.imgBrands);
//        holder.binding.imgBrands.setImageResource(mListDataIcon.get(position));
    }

    @Override
    public int getItemCount() {
        return mListDataIcon == null ? 0 : mListDataIcon.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawHomeBrandsBinding binding;

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

