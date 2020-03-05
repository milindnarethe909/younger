package com.youngershopping.adapter.product.list;

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
import com.youngershopping.databinding.RawProductdataBinding;
import com.youngershopping.pojo.new_arrival_list_pojo;
import com.youngershopping.ui.product.ProductDetailActivity;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.MyViewHolder> {

    private List<new_arrival_list_pojo> mListData;
    private List<String> mListDatatemp;
    private Activity activity;
    private boolean layoutType = true; // true = grid, false = list

    public ProductListAdapter(Activity con, List<new_arrival_list_pojo> mListData, boolean layoutType) {
        this.activity = con;
        this.mListData = mListData;
        this.layoutType = layoutType;
//        mListDatatemp = new ArrayList<>();
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_macbook1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item2));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem2));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_mobile1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_item3));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem3));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_dress1));
//        mListDatatemp.add(activity.getResources().getString(R.string.dummy_book1));
    }

    public void updateView(boolean layoutType) {
        this.layoutType = layoutType;
        notifyDataSetChanged();
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_productdata,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (layoutType) {

            Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+mListData.get(position).getProduct_image()).into(holder.binding.layoutGrid.img);
//        holder.binding.img.setImageResource(mListData.get(position));
            holder.binding.layoutGrid.txtTitle.setText(mListData.get(position).getProduct_name());

            holder.binding.layoutGrid.txtPriceMain.setText("Rs. "+mListData.get(position).getCprice());
            holder.binding.layoutGrid.txtPriceDiscount.setText("Rs. "+mListData.get(position).getPrice());
            holder.binding.layoutGrid.txtPriceDiscount.setPaintFlags(holder.binding.layoutGrid.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//            if (mListData.get(position).getCprice().equals("")){
//                holder.binding.layoutGrid.txtPriceDiscount.setVisibility(View.GONE);
//            }else{
//                holder.binding.layoutGrid.txtPriceDiscount.setVisibility(View.VISIBLE);
//            }

            double a = mListData.get(position).getRating();

            if(a>=5){
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_fill);

            }else if(a>=4 && a<5){
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=3 && a<4){
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=2 && a<3){
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=1 && a<2){
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_border);
            }else{
                holder.binding.layoutGrid.imgStar1.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar2.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutGrid.imgStar5.setImageResource(R.drawable.star_border);
            }



            if (position % 3 == 0) {
//            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            } else {
//            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            }
            if (position % 2 == 0) {
                holder.binding.layoutGrid.imgFavourite.setImageResource(R.drawable.favorite_border);
            } else {
                holder.binding.layoutGrid.imgFavourite.setImageResource(R.drawable.favorite_fill);
            }

            holder.binding.layoutGrid.productClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra("product_id",mListData.get(position).getProduct_id());
                    activity.startActivity(intent);
                }
            });

            holder.binding.layoutGrid.linearGrid.setVisibility(View.VISIBLE);
            holder.binding.layoutList.linearList.setVisibility(View.GONE);
//            holder.binding.layoutGrid.txtTitle.setText(mListDatatemp.get(position));
//
//            holder.binding.layoutGrid.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
//            holder.binding.layoutGrid.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
//            holder.binding.layoutGrid.txtPriceDiscount.setPaintFlags(holder.binding.layoutGrid.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            if (position % 3 == 0) {
//                holder.binding.layoutGrid.txtPriceDiscount.setVisibility(View.GONE);
//                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_border);
//
//                holder.binding.layoutGrid.imgFavourite.setImageResource(R.drawable.favorite_fill);
//            } else {
//                holder.binding.layoutGrid.txtPriceDiscount.setVisibility(View.VISIBLE);
//                holder.binding.layoutGrid.imgStar4.setImageResource(R.drawable.star_fill);
//                holder.binding.layoutGrid.imgFavourite.setImageResource(R.drawable.favorite_border);
//            }
//
//            Utils.TransitionalImageView(mListData.get(position),holder.binding.layoutGrid.img,activity);
        } else {

            Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+mListData.get(position).getProduct_image()).into(holder.binding.layoutList.img);
//        holder.binding.img.setImageResource(mListData.get(position));
            holder.binding.layoutList.txtTitle.setText(mListData.get(position).getProduct_name());

            holder.binding.layoutList.txtPriceMain.setText("Rs. "+mListData.get(position).getCprice());
            holder.binding.layoutList.txtPriceDiscount.setText("Rs. "+mListData.get(position).getPrice());
            holder.binding.layoutList.txtPriceDiscount.setPaintFlags(holder.binding.layoutList.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

//            if (mListData.get(position).getCprice().equals("")){
//                holder.binding.layoutList.txtPriceDiscount.setVisibility(View.GONE);
//            }else{
//                holder.binding.layoutList.txtPriceDiscount.setVisibility(View.VISIBLE);
//            }

            double a = mListData.get(position).getRating();

            if(a>=5){
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_fill);

            }else if(a>=4 && a<5){
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=3 && a<4){
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=2 && a<3){
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_border);
            }else if(a>=1 && a<2){
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_fill);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_border);
            }else{
                holder.binding.layoutList.imgStar1.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar2.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar3.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_border);
                holder.binding.layoutList.imgStar5.setImageResource(R.drawable.star_border);
            }



            if (position % 3 == 0) {
//            holder.binding.txtPriceDiscount.setVisibility(View.VISIBLE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            } else {
//            holder.binding.txtPriceDiscount.setVisibility(View.GONE);
//            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            }
            if (position % 2 == 0) {
                holder.binding.layoutList.imgFavourite.setImageResource(R.drawable.favorite_border);
            } else {
                holder.binding.layoutList.imgFavourite.setImageResource(R.drawable.favorite_fill);
            }

            holder.binding.layoutList.productClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra("product_id",mListData.get(position).getProduct_id());

                    activity.startActivity(intent);
                }
            });


            holder.binding.layoutGrid.linearGrid.setVisibility(View.GONE);
            holder.binding.layoutList.linearList.setVisibility(View.VISIBLE);


//
//            holder.binding.layoutList.txtTitle.setText(mListDatatemp.get(position));
//
//            holder.binding.layoutList.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
//            holder.binding.layoutList.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
//            holder.binding.layoutList.txtPriceDiscount.setPaintFlags(holder.binding.layoutList.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//            if (position % 3 == 0) {
//                holder.binding.layoutList.txtPriceDiscount.setVisibility(View.GONE);
//                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_border);
//
//                holder.binding.layoutList.imgFavourite.setImageResource(R.drawable.favorite_fill);
//            } else {
//                holder.binding.layoutList.txtPriceDiscount.setVisibility(View.VISIBLE);
//                holder.binding.layoutList.imgStar4.setImageResource(R.drawable.star_fill);
//                holder.binding.layoutList.imgFavourite.setImageResource(R.drawable.favorite_border);
//            }
//
//            Utils.TransitionalImageView(mListData.get(position),holder.binding.layoutList.img,activity);
        }


    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductdataBinding binding;

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

