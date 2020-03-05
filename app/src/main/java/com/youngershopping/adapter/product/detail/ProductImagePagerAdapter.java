package com.youngershopping.adapter.product.detail;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.youngershopping.R;
import com.youngershopping.databinding.RawProductimagePagerBinding;

import java.util.ArrayList;

public class ProductImagePagerAdapter extends PagerAdapter {
    private Activity activity;
    private LayoutInflater mLayoutInflater;
    private int[] GalImages = new int[]{
            R.drawable.oneplus1,
            R.drawable.oneplus2,
            R.drawable.oneplus3
    };

    ArrayList<String> list;


    public ProductImagePagerAdapter(Activity activity,ArrayList<String> list) {
        this.activity = activity;
        this.list = list;
        mLayoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayoutCompat) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RawProductimagePagerBinding binding = DataBindingUtil.inflate(mLayoutInflater, R.layout.raw_productimage_pager, container, false);
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+list.get(position)).into(binding.img);
//        binding.img.setImageResource(GalImages[position]);
        container.addView(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayoutCompat) object);
    }
}