package com.youngershopping.ui.account.order;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.adapter.account.order.OrderAdapter;
import com.youngershopping.databinding.ActivityOrderBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.pojo.order_data_list_response;
import com.youngershopping.pojo.order_list_respones;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderListActivity extends BaseApp implements ListItemClickInterface {
    private ActivityOrderBinding binding;
    private Activity activity = OrderListActivity.this;
    private String TAG = OrderListActivity.class.getSimpleName();
    private OrderAdapter orderAdapter;
    private List<String> listOrder;
    private List<order_data_list_response> dataListResponses;
    ApiInterface apiInterface;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_order);
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
        listOrder = new ArrayList<>();
        dataListResponses = new ArrayList<>();
        getOrderList();
//        listOrder.add(Constants.orderStatus_Pending);
//        listOrder.add(Constants.orderStatus_Completed);
//        listOrder.add(Constants.orderStatus_Cancelled);
//        listOrder.add(Constants.orderStatus_Pending);
//        listOrder.add(Constants.orderStatus_Denied);
//        listOrder.add(Constants.orderStatus_Prescription_required);
//        listOrder.add(Constants.orderStatus_Shipped);
//        listOrder.add(Constants.orderStatus_Processing);

//        orderAdapter = new OrderAdapter(activity, listOrder);
//        binding.commanRecyclerview.recyclerView.setAdapter(orderAdapter);

    }

    private void getOrderList() {
        String user_id = "5";
        Call<order_list_respones> orderListResponesCall = apiInterface.getOderList(user_id);
        orderListResponesCall.enqueue(new Callback<order_list_respones>() {
            @Override
            public void onResponse(Call<order_list_respones> call, Response<order_list_respones> response) {
                order_list_respones orderListRespones = response.body();
                if (orderListRespones.getStatus().equals("true")){
                    dataListResponses = orderListRespones.getList();
                    orderAdapter = new OrderAdapter(activity, listOrder,dataListResponses);
                    binding.commanRecyclerview.recyclerView.setAdapter(orderAdapter);
                }
                if (orderListRespones.getStatus().equals("false")){
                    Toast.makeText(getApplicationContext(),"Order is not available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<order_list_respones> call, Throwable t) {

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
