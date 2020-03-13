package com.youngershopping.adapter.home;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawHomesaledataBinding;
import com.youngershopping.ui.product.ProductDetailActivity;
import com.youngershopping.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class HomeDataSaleAdapter extends RecyclerView.Adapter<HomeDataSaleAdapter.MyViewHolder> {

    private List<Integer> mListData;
    private List<String> mListDatatemp;
    private Activity activity;

    public HomeDataSaleAdapter(Activity con, List<Integer> mListData) {
        this.activity = con;
        this.mListData = mListData;
        mListDatatemp = new ArrayList<>();
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem4));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem4));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_book1));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_cameraitem5));
        mListDatatemp.add(activity.getResources().getString(R.string.dummy_mobile1));
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_homesaledata,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        if (position == 0) {
            holder.binding.linearSale.setVisibility(View.VISIBLE);
            holder.binding.homedata.linearMain.setVisibility(View.GONE);
        } else {
            holder.binding.linearSale.setVisibility(View.GONE);
            holder.binding.homedata.linearMain.setVisibility(View.VISIBLE);

            holder.binding.homedata.txtTitle.setText(mListDatatemp.get(position));
            holder.binding.homedata.txtAvailable.setText((position + 2) + " " + activity.getResources().getString(R.string.Available));
            holder.binding.homedata.txtAvailable.setVisibility(View.VISIBLE);

            holder.binding.homedata.txtPriceMain.setText(activity.getResources().getString(R.string.dummy_price));
            holder.binding.homedata.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
            holder.binding.homedata.txtPriceDiscount.setPaintFlags(holder.binding.homedata.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

            if (position % 3 == 0) {
                holder.binding.homedata.txtPriceDiscount.setVisibility(View.VISIBLE);
//                holder.binding.homedata.imgStar4.setImageResource(R.drawable.star_fill);
            } else {
                holder.binding.homedata.txtPriceDiscount.setVisibility(View.GONE);
//                holder.binding.homedata.imgStar4.setImageResource(R.drawable.star_border);
            }
            if (position % 2 == 0) {
                holder.binding.homedata.imgFavourite.setImageResource(R.drawable.favorite_border);
            } else {
                holder.binding.homedata.imgFavourite.setImageResource(R.drawable.favorite_fill);
            }

            holder.binding.homedata.productClick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharePref.setwishlist("wish","false",activity);
                    Intent intent = new Intent(activity, ProductDetailActivity.class);
                    intent.putExtra("product_id","72");
                    activity.startActivity(intent);
                }
            });
            final String toyBornTime = "2020-03-12 12:00:00";
            final Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @SuppressLint("SetTextI18n")
                @Override
                public void run() {
                    handler.postDelayed(this, 1000);
                    try {
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                        // Please set date in  YYYY-MM-DD hh:mm:ss format
                        /*parse endDateTime in future date*/
                        Date futureDate = dateFormat.parse(toyBornTime);
                        Date currentDate = new Date();
                        Log.d("TAG","Date "+toyBornTime);
                        /*if current date is not comes after future date*/
                        if (!currentDate.after(futureDate)) {
                            long diff = futureDate.getTime()
                                    - currentDate.getTime();

                            long days = diff / (24 * 60 * 60 * 1000);
                            diff -= days *(24  *60 * 60  *1000);
                            long hours = diff / (60 * 60*  1000);
                            diff -= hours * (60*  60 * 1000);
                            long minutes = diff / (60 * 1000);
                            diff -= minutes * (60  *1000);
                            long seconds = diff / 1000;
                            @SuppressLint("DefaultLocale") String dayLeft = "" + String.format("%02d", days);
                            @SuppressLint("DefaultLocale") String hrLeft = "" + String.format("%02d", hours);
                            @SuppressLint("DefaultLocale") String minsLeft = "" + String.format("%02d", minutes);
                            @SuppressLint("DefaultLocale") String secondLeft = "" + String.format("%02d", seconds);
                            holder.binding.textSaleDate.setText(dayLeft + "D: "+hrLeft + "H: "+minsLeft + "M: "+secondLeft + "S");
//                        hrsLeft.setText(hrLeft + "H: ");
//                        minLeft.setText();
//                        secLeft.setText();

                        } else {
//                        textViewGone();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            handler.postDelayed(runnable, 1000);

            Utils.TransitionalImageView(mListData.get(position),holder.binding.homedata.img,activity);

        }

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawHomesaledataBinding binding;

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

    public void countDownStart() {

    }

}

