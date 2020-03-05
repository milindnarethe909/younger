package com.youngershopping.adapter.account;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawNotificationBinding;
import com.youngershopping.databinding.RawOrderBinding;
import com.youngershopping.pojo.get_data_notification;
import com.youngershopping.pojo.get_notification_response;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<get_data_notification> mListData;
    private Activity activity;

    public NotificationAdapter(Activity con, List<get_data_notification> mListData) {
        this.activity = con;
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_notification,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtDetail.setText(mListData.get(position).getNote());
        holder.binding.txtTitle.setText(mListData.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawNotificationBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            switch (view.getId()) {
            }
        }
    }


}

