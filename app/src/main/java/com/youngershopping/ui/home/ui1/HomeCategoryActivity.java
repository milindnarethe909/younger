package com.youngershopping.ui.home.ui1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.adapter.home.HomeDataBestSellingAdapter;
import com.youngershopping.adapter.home.category.expandable.CategoryExpandableListAdapter;
import com.youngershopping.adapter.home.category.HomeCategoryDataPopularAdapter;
import com.youngershopping.databinding.ActivityHomecategoryBinding;
import com.youngershopping.pojo.ChildCategory;
import com.youngershopping.pojo.ParentCategory;
import com.youngershopping.pojo.best_seller_pojo;
import com.youngershopping.ui.product.ProductListActivity;
import com.youngershopping.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeCategoryActivity extends BaseApp implements View.OnClickListener {
    private ActivityHomecategoryBinding binding;
    private Activity activity = HomeCategoryActivity.this;
    private String TAG = HomeCategoryActivity.class.getSimpleName();
    private CategoryExpandableListAdapter adapter;
    private HomeCategoryDataPopularAdapter homeCategoryDataPopularAdapter;
    private List<Integer> listhomeCategoryDataPopular;
    private List<best_seller_pojo> listHomeDataBestSelling;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_homecategory);
        init();
        setlistner();
    }

    private void setlistner() {
        binding.btnSeeAll.setOnClickListener(this);
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setTitle("RAM");

        binding.commanRecyclerviewPopular.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        fillData();
        expandable();
    }

    private void expandable() {
        binding.expandableListView.setNestedScrollingEnabled(false);
        binding.expandableListView.setGroupIndicator(null);
        setItems();
        setListener();
    }

    private void fillData() {
        binding.linearPopular.setVisibility(View.GONE);
        listHomeDataBestSelling = new ArrayList<>();
        getBestSeller();
//        listhomeCategoryDataPopular = new ArrayList<>();
//        listhomeCategoryDataPopular.add(R.mipmap.camera1);
//        listhomeCategoryDataPopular.add(R.mipmap.camera2);
//        listhomeCategoryDataPopular.add(R.mipmap.camera3);
//        listhomeCategoryDataPopular.add(R.mipmap.camera4);
//        listhomeCategoryDataPopular.add(R.mipmap.camera5);
//        listhomeCategoryDataPopular.add(R.mipmap.camera6);
//        listhomeCategoryDataPopular.add(R.mipmap.camera7);
//        homeCategoryDataPopularAdapter = new HomeCategoryDataPopularAdapter(activity, listhomeCategoryDataPopular);
//        binding.commanRecyclerviewPopular.recyclerView.setAdapter(homeCategoryDataPopularAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSeeAll:
//                SharePref.setProductId("product_id","72",getApplicationContext());
                intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.from, getResources().getString(R.string.PopularProduct));
                startActivity(intent);
                break;
        }
    }

    // Exapandable
    // Setting headers and raw_category_child to expandable listview
    void setItems() {

        // Array list for header
        ArrayList<ParentCategory> header = new ArrayList<ParentCategory>();

        // Array list for child items
        List<ChildCategory> child1 = new ArrayList<ChildCategory>();
        List<ChildCategory> child2 = new ArrayList<ChildCategory>();
        List<ChildCategory> child3 = new ArrayList<ChildCategory>();

        // Hash map for both header and child
        // Adding headers to list
        HashMap<Integer, List<ChildCategory>> hashMap = new HashMap<Integer, List<ChildCategory>>();
        ParentCategory parent = new ParentCategory();
        parent.icon = R.mipmap.cate_sub_clothing;
        parent.Title = getResources().getString(R.string.dummy_subcategory1);
        parent.subTitle = getResources().getString(R.string.dummy_subcategory1_1);
        header.add(parent);

        parent = new ParentCategory();
        parent.icon = R.mipmap.cate_sub_footwear;
        parent.Title = getResources().getString(R.string.dummy_subcategory2);
        parent.subTitle = getResources().getString(R.string.dummy_subcategory2_1);
        header.add(parent);

        parent = new ParentCategory();
        parent.icon = R.mipmap.cate_sub_watches;
        parent.Title = getResources().getString(R.string.dummy_subcategory3);
        parent.subTitle = getResources().getString(R.string.dummy_subcategory3_1);
        header.add(parent);


        // Adding child data
        ChildCategory child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_clothing_shop;
        child.Title = getResources().getString(R.string.dummy_subcategory10);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_tshirts;
        child.Title = getResources().getString(R.string.dummy_subcategory11);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_jeans;
        child.Title = getResources().getString(R.string.dummy_subcategory21);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_sports_wear;
        child.Title = getResources().getString(R.string.dummy_subcategory31);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_suit;
        child.Title = getResources().getString(R.string.dummy_subcategory41);
        child1.add(child);




        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_inner;
        child.Title = getResources().getString(R.string.dummy_subcategory51);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_winter;
        child.Title = getResources().getString(R.string.dummy_subcategory61);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_ethnic;
        child.Title = getResources().getString(R.string.dummy_subcategory151);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_shorts;
        child.Title = getResources().getString(R.string.dummy_subcategory71);
        child1.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_slippers;
        child.Title = getResources().getString(R.string.dummy_subcategory131);
        child2.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_loafer;
        child.Title = getResources().getString(R.string.dummy_subcategory141);
        child2.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_wallet;
        child.Title = getResources().getString(R.string.dummy_subcategory81);
        child3.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_luggage;
        child.Title = getResources().getString(R.string.dummy_subcategory91);
        child3.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_socks;
        child.Title = getResources().getString(R.string.dummy_subcategory101);
        child3.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_sunglasses;
        child.Title = getResources().getString(R.string.dummy_subcategory111);
        child3.add(child);

        child = new ChildCategory();
        child.icon = R.mipmap.sub_cate1_necklace;
        child.Title = getResources().getString(R.string.dummy_subcategory121);
        child3.add(child);

        // Adding header and raw_category_child to hash map
        hashMap.put(0, child1);
        hashMap.put(1, child2);
        hashMap.put(2, child3);

        adapter = new CategoryExpandableListAdapter(activity, header, hashMap);

        // Setting adpater over expandablelistview
        binding.expandableListView.setAdapter(adapter);
    }

    // Setting different listeners to expandablelistview
    void setListener() {

        // This listener will show toast on group click
//        binding.expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView listview, View view,
//                                        int group_pos, long id) {
//                Utils.setListViewHeight(listview, group_pos);
//                return false;
//            }
//        });

        // This listener will expand one group at one time
        // You can remove this listener for expanding all groups
        binding.expandableListView
                .setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

                    // Default position
                    int previousGroup = -1;

                    @Override
                    public void onGroupExpand(int groupPosition) {
                        if (groupPosition != previousGroup)

                            // Collapse the expanded group
                            binding.expandableListView.collapseGroup(previousGroup);
                        previousGroup = groupPosition;
                    }

                });

        // This listener will show toast on child click
//        binding.expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//
//            @Override
//            public boolean onChildClick(ExpandableListView listview, View view,
//                                        int groupPos, int childPos, long id) {
//                Toast.makeText(
//                        activity,
//                        "You clicked : " + adapter.getChild(groupPos, childPos),
//                        Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
    }

    private void getBestSeller() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.propulare_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Best Seller List = "+response);

                    boolean status = object.getBoolean("status");

                    listHomeDataBestSelling.clear();

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("data");
                        binding.linearPopular.setVisibility(View.VISIBLE);
                        int leg = jsonArray.length();
                        if (leg<6){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String category_id = "";
                                String subcategory_id = "";
                                String product_name = object1.getString("name");
                                String product_image = object1.getString("photo");
                                String product_id = object1.getString("id");
                                String price = object1.getString("price");
                                String cprice = object1.getString("cprice");
                                double rat = object1.getDouble("rating");
                                int stock = 0;
                                String childcategory_id = "";

                                listHomeDataBestSelling.add(new best_seller_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                            }
                        }else {
                            for (int i = 0; i < 6; i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String category_id = "";
                                String subcategory_id = "";
                                String product_name = object1.getString("name");
                                String product_image = object1.getString("photo");
                                String product_id = object1.getString("id");
                                String price = object1.getString("price");
                                String cprice = object1.getString("cprice");
                                double rat = object1.getDouble("rating");
                                int stock = 0;
                                String childcategory_id = "";

                                listHomeDataBestSelling.add(new best_seller_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                            }
                        }

                        HomeDataBestSellingAdapter homeDataBestSellingAdapter = new HomeDataBestSellingAdapter(activity, listHomeDataBestSelling);
                        binding.commanRecyclerviewPopular.recyclerView.setAdapter(homeDataBestSellingAdapter);



                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Edit Profile Error = "+error.getMessage());
                if (error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}
