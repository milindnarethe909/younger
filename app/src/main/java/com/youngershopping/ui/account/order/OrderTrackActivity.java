package com.youngershopping.ui.account.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.databinding.ActivityOrderTrackBinding;
import com.youngershopping.databinding.RawOrderTrackBinding;
import com.youngershopping.interfaces.ListItemClickInterface;

import java.util.ArrayList;
import java.util.List;

public class OrderTrackActivity extends BaseApp implements ListItemClickInterface {
    private ActivityOrderTrackBinding binding;
    private Activity activity = OrderTrackActivity.this;
    private String TAG = OrderTrackActivity.class.getSimpleName();
    private List<String> list0 = new ArrayList<>();
    private List<String> list1 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_order_track);
        init();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        fillData();
    }


    private void fillData() {
        list3.add("Tue, 27 Aug 18");
        list3.add("Wed, 28 Aug 18");
        list3.add("Thu, 29 Aug 18");
        list3.add("Sat, 31 Aug 18");
        list3.add("Sun, 01 Sep 18");

        list0.add(getResources().getString(R.string.txt_OrderStep_Placed));
        list0.add(getResources().getString(R.string.txt_OrderStep_Confirmed));
        list0.add(getResources().getString(R.string.txt_OrderStep_Processed));
        list0.add(getResources().getString(R.string.txt_OrderStep_Shipped));
        list0.add(getResources().getString(R.string.txt_OrderStep_Delivered));

        list1.add(getResources().getString(R.string.txt_OrderStep_Placed1));
        list1.add(getResources().getString(R.string.txt_OrderStep_Confirmed1));
        list1.add(getResources().getString(R.string.txt_OrderStep_Processed1));
        list1.add(getResources().getString(R.string.txt_OrderStep_Shipped1));
        list1.add(getResources().getString(R.string.txt_OrderStep_Delivered1));

        for (int i = 0; i < list0.size(); i++) {
            View view = View.inflate(this, R.layout.raw_order_track, null);
            RawOrderTrackBinding bindingRaw = DataBindingUtil.bind(view);
            if (i == 3){
                bindingRaw.sequenceStep.setActive(true);
            }
            bindingRaw.sequenceStep.setTitle(list0.get(i));
            bindingRaw.sequenceStep.setSubtitle(list1.get(i));
            bindingRaw.sequenceStep.setAnchor(list3.get(i));
            binding.sequenceLayout.addView(view);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemsSelectedClick(int position, String type) {

    }

}
