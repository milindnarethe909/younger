package com.youngershopping.adapter.home;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawHomeCategoryBinding;
import com.youngershopping.ui.home.ui1.HomeCategoryActivity;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class HomeDataCategoryAdapter extends RecyclerView.Adapter<HomeDataCategoryAdapter.MyViewHolder> {

    private List<Integer> mListDataIcon;
    private List<String> mListDataLable;
    private Activity activity;

    public HomeDataCategoryAdapter(Activity con, List<Integer> mListDataIcon, List<String> mListDataLable) {
        this.activity = con;
        this.mListDataLable = mListDataLable;
        this.mListDataIcon = mListDataIcon;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_home_category,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.imgCategory.setImageResource(mListDataIcon.get(position));
        holder.binding.txtTitle.setText(mListDataLable.get(position));
        if (position == 0){
            holder.binding.txtTitle.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        }else{
            holder.binding.txtTitle.setTextColor(activity.getResources().getColor(R.color.textdark));
        }

    }

    @Override
    public int getItemCount() {
        return mListDataLable == null ? 0 : mListDataLable.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawHomeCategoryBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(activity, HomeCategoryActivity.class);
            activity.startActivity(intent);
        }
    }


}

