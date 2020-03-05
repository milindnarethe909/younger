package com.youngershopping.ui.start;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.youngershopping.BaseAppFragmentActivity;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityTourBinding;
import com.youngershopping.ui.user.LoginActivity;
import com.youngershopping.view.tour.CommonFragment;
import com.youngershopping.view.tour.CustPagerTransformer;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TourActivity extends BaseAppFragmentActivity implements View.OnClickListener {

    private List<CommonFragment> fragments = new ArrayList<>(); // 供ViewPager使用
    private final String[] imageArray = {"assets://tour3.png", "assets://tour4.png", "assets://tour5.png"};
    private ActivityTourBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tour);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//                getWindow()
//                        .getDecorView()
//                        .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
//            } else {
//                getWindow()
//                        .setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            }
//        }
        dealStatusBar();

        initImageLoader();
        fillViewPager();
        binding.txtDONE.setOnClickListener(this);
    }


    private void fillViewPager() {
        binding.txtTitle.setText(getResources().getString(R.string.tourTitle1));
        binding.txtSubTitle.setText(getResources().getString(R.string.tourSubTitle1));
        binding.indicatorTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TourActivity.this, SplashActivity.class));
            }
        });

        binding.viewpager.setPageTransformer(false, new CustPagerTransformer(this));

        for (int i = 0; i < 10; i++) {
            fragments.add(new CommonFragment());
        }

        binding.viewpager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                CommonFragment fragment = fragments.get(position % 10);
                fragment.bindData(imageArray[position % imageArray.length]);
                return fragment;
            }

            @Override
            public int getCount() {
                return imageArray.length;
            }
        });

        binding.indicator.setViewPager(binding.viewpager);
        binding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicatorTv();
                if (position == 2) {
                    binding.txtTitle.setText(getResources().getString(R.string.tourTitle3));
                    binding.txtSubTitle.setText(getResources().getString(R.string.tourSubTitle3));
                    binding.txtDONE.setVisibility(View.VISIBLE);
                } else if (position == 1){
                    binding.txtTitle.setText(getResources().getString(R.string.tourTitle2));
                    binding.txtSubTitle.setText(getResources().getString(R.string.tourSubTitle2));
                    binding.txtDONE.setVisibility(View.GONE);
                } else if (position == 0){
                    binding.txtTitle.setText(getResources().getString(R.string.tourTitle1));
                    binding.txtSubTitle.setText(getResources().getString(R.string.tourSubTitle1));
                    binding.txtDONE.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        updateIndicatorTv();
    }

    private void updateIndicatorTv() {
        int totalNum = binding.viewpager.getAdapter().getCount();
        int currentItem = binding.viewpager.getCurrentItem() + 1;
        binding.indicatorTv.setText(Html.fromHtml("<font color='#12edf0'>" + currentItem + "</font>  /  " + totalNum));
    }

    private void dealStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int statusBarHeight = getStatusBarHeight();
            ViewGroup.LayoutParams lp = binding.positionView.getLayoutParams();
            lp.height = statusBarHeight;
            binding.positionView.setLayoutParams(lp);
        }
    }

    private int getStatusBarHeight() {
        Class<?> c = null;
        Object obj = null;
        Field field = null;
        int x = 0, statusBarHeight = 0;
        try {
            c = Class.forName("com.android.internal.R$dimen");
            obj = c.newInstance();
            field = c.getField("status_bar_height");
            x = Integer.parseInt(field.get(obj).toString());
            statusBarHeight = getResources().getDimensionPixelSize(x);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        return statusBarHeight;
    }

    @SuppressWarnings("deprecation")
    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                this)
                .memoryCacheExtraOptions(480, 800)
                // default = device screen dimensions
                .threadPoolSize(3)
                // default
                .threadPriority(Thread.NORM_PRIORITY - 1)
                // default
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                // default
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024).memoryCacheSizePercentage(13) // default
                .discCacheSize(50 * 1024 * 1024)
                .discCacheFileCount(100)
                .discCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs().build();

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, LoginActivity.class));
    }
}
