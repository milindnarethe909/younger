package com.youngershopping.adapter.home.category;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawHomedataBinding;
import com.youngershopping.ui.product.ProductDetailActivity;
import com.youngershopping.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class HomeCategoryDataPopularAdapter extends RecyclerView.Adapter<HomeCategoryDataPopularAdapter.MyViewHolder> {

    private List<Integer> mListData;
    private List<String> mListDatatemp;
    private Activity activity;

    public HomeCategoryDataPopularAdapter(Activity con, List<Integer> mListData) {
        this.activity = con;
        this.mListData = mListData;
        mListDatatemp = new ArrayList<>();
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem1));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem2));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem3));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem4));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem5));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem6));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem7));

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_homedata,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText(mListDatatemp.get(position));

        holder.binding.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
        holder.binding.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
        holder.binding.txtPriceDiscount.setPaintFlags(holder.binding.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (position % 3 == 0) {
            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
        } else {
            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
        }
        if (position % 2 == 0) {
            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_border);
        } else {
            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_fill);
        }

        Utils.TransitionalImageView(mListData.get(position),holder.binding.img,activity);
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawHomedataBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            SharePref.setwishlist("wish","false",activity);
            Intent intent = new Intent(activity, ProductDetailActivity.class);
            activity.startActivity(intent);
        }
    }


}

