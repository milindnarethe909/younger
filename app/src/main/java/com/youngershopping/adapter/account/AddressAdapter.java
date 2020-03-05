package com.youngershopping.adapter.account;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawAddressBinding;
import com.youngershopping.databinding.RawNotificationBinding;
import com.youngershopping.ui.account.address.AddAddressActivity;
import com.youngershopping.utils.Constants;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private List<String> mListData;
    private List<Integer> mListDataIcon;
    private Activity activity;

    public AddressAdapter(Activity con, List<String> mListData, List<Integer> mListDataIcon) {
        this.activity = con;
        this.mListDataIcon = mListDataIcon;
        this.mListData = mListData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_address,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText(mListData.get(position));
        holder.binding.txtAddress.setText("Address : "+SharePref.getetLoginaddress("c_address",activity)+", City : "+SharePref.getetLogincity("c_city",activity));
        holder.binding.txtDefault.setVisibility(View.VISIBLE);
        holder.binding.imgAddressType.setImageResource(mListDataIcon.get(position));
//        if (position == 1){
//            holder.binding.txtDefault.setVisibility(View.VISIBLE);
//        }else{
//            holder.binding.txtDefault.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawAddressBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.imgEdit.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Intent intent;
            intent = new Intent(activity, AddAddressActivity.class);
            intent.putExtra(Constants.name,activity.getResources().getString(R.string.txt_EditAddress));
            activity.startActivity(intent);
        }
    }


}

