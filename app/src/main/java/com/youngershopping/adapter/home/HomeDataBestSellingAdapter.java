package com.youngershopping.adapter.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawHomedataBinding;
import com.youngershopping.pojo.best_seller_pojo;
import com.youngershopping.ui.product.ProductDetailActivity;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class HomeDataBestSellingAdapter extends RecyclerView.Adapter<HomeDataBestSellingAdapter.MyViewHolder> {

    private List<best_seller_pojo> mListData;
    private List<String> mListDatatemp;
    private Activity activity;

    public HomeDataBestSellingAdapter(Activity con, List<best_seller_pojo> mListData) {
        this.activity = con;
        this.mListData = mListData;
//        mListDatatemp = new ArrayList<>();
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item4));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_book1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item5));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem2));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item6));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem3));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_homedata,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.binding.txtTitle.setText(mListData.get(position).getProduct_name());
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+mListData.get(position).getProduct_image()).into(holder.binding.img);

        holder.binding.txtPriceMain.setText("Rs. "+mListData.get(position).getCprice());
        holder.binding.txtPriceDiscount.setText("Rs. "+mListData.get(position).getPrice());
        holder.binding.txtPriceDiscount.setPaintFlags(holder.binding.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        if (mListData.get(position).getCprice().equals("")){
            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
        }else{
            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
        }

        double a = mListData.get(position).getRating();

        if(a>=5){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar5.setImageResource(R.drawable.star_fill);

        }else if(a>=4 && a<5){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=3 && a<4){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=2 && a<3){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=1 && a<2){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_border);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else{
            holder.binding.imgStar1.setImageResource(R.drawable.star_border);
            holder.binding.imgStar2.setImageResource(R.drawable.star_border);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }



        if (position % 3 == 0) {
//            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
        } else {
//            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
        }
        if (position % 2 == 0) {
            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_border);
        } else {
            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_fill);
        }

        holder.binding.productClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePref.setwishlist("wish","false",activity);
                Intent intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("product_id",mListData.get(position).getProduct_id());
                activity.startActivity(intent);
            }
        });

//        holder.binding.txtTitle.setText(mListDatatemp.get(position));
//
//        holder.binding.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
//        holder.binding.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
//        holder.binding.txtPriceDiscount.setPaintFlags(holder.binding.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//        if (position % 3 == 0) {
//            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
////            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
//        } else {
//            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
////            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
//        }
//        if (position % 2 == 0) {
//            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_fill);
//        } else {
//            holder.binding.imgFavourite.setImageResource(R.drawable.favorite_border);
//        }

//        Utils.TransitionalImageView(mListData.get(position),holder.binding.img,activity);
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
//            Intent intent = new Intent(activity, ProductDetailActivity.class);
//            activity.startActivity(intent);
        }
    }


}

