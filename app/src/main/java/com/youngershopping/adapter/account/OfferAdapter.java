package com.youngershopping.adapter.account;

import android.app.Activity;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawNotificationBinding;
import com.youngershopping.databinding.RawOfferBinding;
import com.youngershopping.pojo.offer_code_data;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Android on 8/6/2018.
 */
public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {

    private List<Integer> mListData;
    private Activity activity;
    private List<offer_code_data> dataList;

    public OfferAdapter(Activity con, List<Integer> mListData,List<offer_code_data> dataList) {
        this.activity = con;
        this.mListData = mListData;
        this.dataList = dataList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_offer,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.binding.imgOffer.setImageResource(mListData.get(position));
//        Get 20% off on macbook pro, 3 days validity.
        String dd = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.d("TAG","Current Date = "+dd);   //2020-02-19
        String[] startday = dd.split("-");
        String[] d = dataList.get(position).getEnd_date().split("-");
        String s = startday[2]+"/"+startday[1]+"/"+startday[0];
        String e = d[2]+"/"+d[1]+"/"+d[0];
        Calendar calendars = new GregorianCalendar();
        Calendar calendare = new GregorianCalendar();
        calendars.set(Integer.parseInt(d[0]),Integer.parseInt(d[1]),Integer.parseInt(d[2]));
        calendare.set(Integer.parseInt(startday[0]),Integer.parseInt(startday[1]),Integer.parseInt(startday[2]));
        int aa = daysBetween(calendars.getTime(),calendare.getTime());
        int a = dayofweek(Integer.parseInt(d[2]),Integer.parseInt(d[1]),Integer.parseInt(d[0]));
        if (dataList.get(position).getType().equals("0")) {
            holder.binding.txtOffer.setText("Get " +dataList.get(position).getPrice()+" % off on all product, Coupen Code : "+dataList.get(position).getCode()+", "+aa+" days validity.");
        }else{
            holder.binding.txtOffer.setText("Get " +dataList.get(position).getPrice()+" Rs. off on all product, Coupen Code : "+dataList.get(position).getCode()+", "+aa+" days validity.");
        }
    }


    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public int daysBetween(Date d1,Date d2){
        return (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawOfferBinding binding;

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

    static int dayofweek(int d, int m, int y)
    {
        int a = (14 - m)/12;
        int year = y +4800 -a;
        int month = m + 12 *a -3;
        int jbn = d + (153 * month + 2)/5 + 365*year + year/100 + year / 400 - 32045;
        return jbn;
//        Log.d("TAG", "dayofweek: "+d +" "+m+" "+y);
//        int t[] = { 0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4 };
//        y -= (m < 3) ? 1 : 0;
//        return ( y + y/4 - y/100 + y/400 + t[m-1] + d) % 7;
////        return 0;
    }

}

