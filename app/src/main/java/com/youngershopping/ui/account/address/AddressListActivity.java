package com.youngershopping.ui.account.address;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.adapter.account.AddressAdapter;
import com.youngershopping.databinding.ActivityAddressBinding;
import com.youngershopping.databinding.ActivityNotificationBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class AddressListActivity extends BaseApp implements ListItemClickInterface {
    private ActivityAddressBinding binding;
    private Activity activity = AddressListActivity.this;
    private String TAG = AddressListActivity.class.getSimpleName();
    private AddressAdapter addressAdapter;
    private List<String> listAddress;
    private List<Integer> listAddressIcon;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_address);
        init();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        fillData();
    }

    private void fillData() {
        listAddressIcon = new ArrayList<>();
        listAddressIcon.add(R.mipmap.address_home);
        listAddressIcon.add(R.mipmap.address_office);
        listAddressIcon.add(R.mipmap.address_other);
        listAddressIcon.add(R.mipmap.address_other);
        listAddress = new ArrayList<>();
        listAddress.add(getResources().getString(R.string.txt_AddressHome1));
//        listAddress.add(getResources().getString(R.string.txt_AddressOffice1));
//        listAddress.add(getResources().getString(R.string.txt_AddressOther1));
//        listAddress.add(getResources().getString(R.string.txt_AddressOther1));

        addressAdapter = new AddressAdapter(activity, listAddress,listAddressIcon);
        binding.commanRecyclerview.recyclerView.setAdapter(addressAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_address_list, menu);
        return true;
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
        } else if (id == R.id.action_add) {
            Intent intent = new Intent(activity, AddAddressActivity.class);
            intent.putExtra(Constants.name,getResources().getString(R.string.txt_AddAddress));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemsSelectedClick(int position, String type) {

    }

}
