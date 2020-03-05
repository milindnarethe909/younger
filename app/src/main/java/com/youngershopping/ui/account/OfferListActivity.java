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
import com.youngershopping.adapter.account.OfferAdapter;
import com.youngershopping.databinding.ActivityNotificationBinding;
import com.youngershopping.databinding.ActivityOfferBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.pojo.offer_code_data;
import com.youngershopping.pojo.offer_response;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OfferListActivity extends BaseApp implements ListItemClickInterface {
    private ActivityOfferBinding binding;
    private Activity activity = OfferListActivity.this;
    private String TAG = OfferListActivity.class.getSimpleName();
    private OfferAdapter offerAdapter;
    private List<Integer> listOffer;

    ApiInterface apiInterface;

    private List<offer_code_data> code_data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_offer);
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
        listOffer = new ArrayList<>();
//        listOffer.add(R.mipmap.banner6);
//        listOffer.add(R.mipmap.banner7);
//        listOffer.add(R.mipmap.banner8);
//        listOffer.add(R.mipmap.banner9);
//        listOffer.add(R.mipmap.banner10);
//        listOffer.add(R.mipmap.banner11);
//
        getOfferData();

//        offerAdapter = new OfferAdapter(activity, listOffer);
//        binding.commanRecyclerview.recyclerView.setAdapter(offerAdapter);

    }

    public void getOfferData(){
        Call<offer_response> responseCall = apiInterface.getOffercode();
        responseCall.enqueue(new Callback<offer_response>() {
            @Override
            public void onResponse(Call<offer_response> call, Response<offer_response> response) {
                offer_response offerResponse = response.body();
                if (offerResponse.getStatus().equals("true")){
                    code_data = new ArrayList<>();
                    code_data = offerResponse.getOfferCodeData();
                    offerAdapter = new OfferAdapter(activity, listOffer,code_data);
                    binding.commanRecyclerview.recyclerView.setAdapter(offerAdapter);
                }else{
                    Toast.makeText(getApplicationContext(),"Offer is Not Available",Toast.LENGTH_SHORT).show();
                }
                Log.d("TAG","Offer Status = "+offerResponse.getStatus());
                if (offerResponse.getStatus().equals("false")){
                    Toast.makeText(getApplicationContext(),"Offer is Not Available",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<offer_response> call, Throwable t) {
                        Log.d("TAG"," Error for offre = "+t.getMessage());
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
