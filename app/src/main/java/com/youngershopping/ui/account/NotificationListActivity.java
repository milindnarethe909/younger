package com.youngershopping.ui.account;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.adapter.account.NotificationAdapter;
import com.youngershopping.databinding.ActivityNotificationBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.pojo.get_data_notification;
import com.youngershopping.pojo.get_notification_response;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationListActivity extends BaseApp implements ListItemClickInterface {
    private ActivityNotificationBinding binding;
    private Activity activity = NotificationListActivity.this;
    private String TAG = NotificationListActivity.class.getSimpleName();
    private NotificationAdapter notificationAdapter;
    private List<String> listNotification;
    private List<String> list_Notification_Title;

    private List<get_data_notification> list;

    ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_notification);
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        init();
    }

    private void init() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        fillData();
    }

    private void fillData() {
        listNotification = new ArrayList<>();
        list = new ArrayList<>();
        list.clear();
//        listNotification.add(getResources().getString(R.string.dummy_noti1));
//        listNotification.add(getResources().getString(R.string.dummy_noti2));
//        listNotification.add(getResources().getString(R.string.dummy_noti3));
        
        getNotification();

//        notificationAdapter = new NotificationAdapter(activity, listNotification);
//        binding.commanRecyclerview.recyclerView.setAdapter(notificationAdapter);

    }

    private void getNotification() {
        Call<get_notification_response> getNotificationResponseCall = apiInterface.getNotification();
        getNotificationResponseCall.enqueue(new Callback<get_notification_response>() {
            @Override
            public void onResponse(Call<get_notification_response> call, Response<get_notification_response> response) {
                get_notification_response get_notification_response = response.body();
                String status = get_notification_response.getStatus();

                if (status.equals("true")){
                    list = get_notification_response.getData();
                    notificationAdapter = new NotificationAdapter(activity, list);
                    binding.commanRecyclerview.recyclerView.setAdapter(notificationAdapter);
                }

                if (status.equals("false")){
                    Toast.makeText(getApplicationContext(),"Notification Not Avaiable",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<get_notification_response> call, Throwable t) {
                Log.d(TAG,"Error Notification = "+t.getMessage());
            }
        });
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
