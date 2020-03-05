package com.youngershopping.adapter.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.youngershopping.R;
import com.youngershopping.databinding.RawHomebannerPagerBinding;

import java.util.ArrayList;

public class HomePagerAdapter1 extends PagerAdapter {
    private Activity activity;
    private LayoutInflater mLayoutInflater;
    private int[] GalImages = new int[]{
            R.mipmap.baner1,
            R.mipmap.baner2,
            R.mipmap.baner3,
            R.mipmap.baner4,
            R.mipmap.baner5
    };

    ArrayList<String> bannerlist;


    public HomePagerAdapter1(ArrayList<String> bannerlist,Activity activity) {
        this.bannerlist = bannerlist;
        this.activity = activity;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return bannerlist.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayoutCompat) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
//        View itemView = mLayoutInflater.inflate(R.layout.raw_homebanner_pager, container, false);
        RawHomebannerPagerBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.raw_homebanner_pager, container, false);
        Log.d("TAG","Banner url = "+bannerlist);
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+bannerlist.get(position)).centerCrop().transform(new CircleCrop()).fitCenter().into(binding.img);
        binding.img.setScaleType(ImageView.ScaleType.FIT_XY);
//        binding.img.setImageResource(GalImages[position]);

        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayoutCompat) object);
    }
}