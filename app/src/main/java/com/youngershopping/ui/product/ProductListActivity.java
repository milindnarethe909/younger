package com.youngershopping.ui.product;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.AuthFailureError;
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
import com.youngershopping.adapter.home.HomeDataNewArrivalAdapter;
import com.youngershopping.adapter.product.detail.ProductImagePagerAdapter;
import com.youngershopping.adapter.product.detail.ProductOptionAdapter;
import com.youngershopping.adapter.product.list.ProductFilterResultAdapter;
import com.youngershopping.adapter.product.list.ProductFilterTypeAdapter;
import com.youngershopping.adapter.product.list.ProductFilterTypeValueAdapter;
import com.youngershopping.adapter.product.list.ProductListAdapter;
import com.youngershopping.databinding.ActivityProductlistBinding;
import com.youngershopping.interfaces.ListItemClickInterface;
import com.youngershopping.pojo.ListofColor_pojo;
import com.youngershopping.pojo.new_arrival_list_pojo;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.MonthPick_Class;
import com.youngershopping.view.rangbar.RangeBar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductListActivity extends BaseApp implements ListItemClickInterface {
    private ActivityProductlistBinding binding;
    private Activity activity = ProductListActivity.this;
    private String TAG = ProductListActivity.class.getSimpleName();
    private ProductListAdapter productListAdapter;
    private ProductFilterResultAdapter productFilterResultAdapter;
    private ProductFilterTypeAdapter productFilterTypeAdapter;
    private ProductFilterTypeValueAdapter productFilterTypeValueAdapter;
    private List<new_arrival_list_pojo> listHomeDataNewArrival;
    private List<String> listFilterResult1;
    private List<String> listFilterResult2;
    private List<String> listFilterTypeValue;
    private boolean layoutType = true; // true = grid, false = list
    private int filterResultPos = 0;
    private String title = "";
    ArrayList<new_arrival_list_pojo> list;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_productlist);
        init();
    }

    private void init() {
        title = getIntent().getStringExtra(Constants.from);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(title);
        list = new ArrayList<>();
        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.temp) != null) {
                if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("1")) {
                    layoutType = false;
                } else if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("2")) {
                    filterResultPos = 3;

                    binding.relativeMain.setVisibility(View.GONE);
                    binding.linearFilter.setVisibility(View.VISIBLE);
                    actionBar.setTitle(getResources().getString(R.string.Filter));

                    binding.linearFilterValue.setVisibility(View.VISIBLE);
                    binding.linearPriceRange.setVisibility(View.GONE);
                }
            }

        }

        binding.floatbuttonPriceHL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Collections.sort(listHomeDataNewArrival, new_arrival_list_pojo.StuRollno);
                list = new ArrayList<>();
                list.clear();
                for(new_arrival_list_pojo str: listHomeDataNewArrival){
                    Log.d(TAG,"GET Price h t l = "+str.getCprice());
                    list.add(new new_arrival_list_pojo(str.getCategory_id(), str.getChildcategory_id(),str.getCprice(), str.getPrice(), str.getProduct_id(), str.getProduct_image(), str.getProduct_name(), str.getSubcategory_id(), str.getRating(), str.getStock()));
                }
                productListAdapter = new ProductListAdapter(activity, list, layoutType);
                binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
                productListAdapter.notifyDataSetChanged();
            }
        });

        binding.floatbuttonPriceLH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(listHomeDataNewArrival);
                list = new ArrayList<>();
                list.clear();
                for(new_arrival_list_pojo str: listHomeDataNewArrival){
                    Log.d(TAG,"GET Price l t h = "+str.getCprice());
                    list.add(new new_arrival_list_pojo(str.getCategory_id(), str.getChildcategory_id(),str.getCprice(), str.getPrice(), str.getProduct_id(), str.getProduct_image(), str.getProduct_name(), str.getSubcategory_id(), str.getRating(), str.getStock()));
                }
                productListAdapter = new ProductListAdapter(activity, list, layoutType);
                binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
                productListAdapter.notifyDataSetChanged();

            }
        });

        fillData();
        setLayout();
        priceRange();
    }

    private void priceRange() {
        binding.txtPriceMin.setText(Constants.currency + "50");
        binding.txtPriceMax.setText(Constants.currency + "1000");
        // Sets the display values of the indices
        binding.rangebar1.setOnRangeBarChangeListener(new RangeBar.OnRangeBarChangeListener() {
            @Override
            public void onRangeChangeListener(RangeBar rangeBar, int leftPinIndex,
                                              int rightPinIndex, String leftPinValue, String rightPinValue) {

                binding.txtPriceMin.setText(Constants.currency + "" + leftPinValue);
                binding.txtPriceMax.setText(Constants.currency + "" + rightPinValue);

            }

            @Override
            public void onTouchEnded(RangeBar rangeBar) {
                Log.d("RangeBar", "Touch ended");
            }

            @Override
            public void onTouchStarted(RangeBar rangeBar) {
                Log.d("RangeBar", "Touch started");
            }
        });
    }

    private void fillData() {

        if (title.equals("New Arrival")){
            SharePref.setwishlist("wish","false",activity);
            Log.d("TAG","New arrival");
            getNewArrivalData();
        }else if (title.equals("Related Products")){
            Log.d("TAG","Related Products");
            SharePref.setwishlist("wish","false",activity);
            getData();
        }else if (title.equals("My Wishlist")){
            Log.d("TAG","My Wishlist");

            getWishListData();

        }else if (title.equals("Best Selling")){
            SharePref.setwishlist("wish","false",activity);
            Log.d("TAG","Best Seller");
            getBestSeller();
        }else if (title.equals("Popular Products")){
            SharePref.setwishlist("wish","false",activity);
            Log.d("TAG","Best Seller");
            getBestSeller();
        }else{
            SharePref.setwishlist("wish","false",activity);
            Log.d("TAG","Search = "+SharePref.getwishlist("wish",activity));
            getSearchData();
        }
//        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
//        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
        binding.commanRecyclerview.recyclerView.showShimmerAdapter();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                productListAdapter = new ProductListAdapter(activity, list, layoutType);
                productListAdapter.notifyDataSetChanged();
                binding.commanRecyclerview.recyclerView.hideShimmerAdapter();
            }
        }, 1500);

        if (!title.equalsIgnoreCase(getResources().getString(R.string.account_Wishlist))) {
            listFilterResult1 = new ArrayList<>();
            listFilterResult1.add("Category");
            listFilterResult1.add("Brand");
            listFilterResult1.add("Price");
            listFilterResult1.add("Color");
            listFilterResult1.add("Storage");
            listFilterResult1.add("Size");
            listFilterResult1.add("Ram");
            listFilterResult1.add("Primary Camera");
            listFilterResult1.add("Secondary Camera");
            listFilterResult1.add("Rating");
            listFilterResult1.add("Discount");

            listFilterResult2 = new ArrayList<>();
            listFilterResult2.add(getResources().getString(R.string.dummy_category1));
            listFilterResult2.add("All");
            listFilterResult2.add("$50 - $500");
            listFilterResult2.add("Mirror Grey");
            listFilterResult2.add("128 GB");
            listFilterResult2.add("5.7 - 5.9 inch");
            listFilterResult2.add("6GM");
            listFilterResult2.add("12 - 15 MP");
            listFilterResult2.add("5 - 8 MP");
            listFilterResult2.add("4 & above");
            listFilterResult2.add("50% off");

            productFilterResultAdapter = new ProductFilterResultAdapter(activity, listFilterResult1, listFilterResult2);
            binding.commanRecyclerviewFilterResult.recyclerView.setAdapter(productFilterResultAdapter);

            productFilterTypeAdapter = new ProductFilterTypeAdapter(activity, listFilterResult1, filterResultPos);
            binding.commanRecyclerviewFilterType.recyclerView.setAdapter(productFilterTypeAdapter);

            listFilterTypeValue = new ArrayList<>();
            listFilterTypeValue.add("Beige");
            listFilterTypeValue.add("Black");
            listFilterTypeValue.add("Blue");
            listFilterTypeValue.add("Brown");
            listFilterTypeValue.add("Copper");
            listFilterTypeValue.add("Gold");
            listFilterTypeValue.add("Green");
            listFilterTypeValue.add("Grey");
            listFilterTypeValue.add("Khaki");
            listFilterTypeValue.add("Maroon");
            listFilterTypeValue.add("Multi Color");
            listFilterTypeValue.add("Natural");
            listFilterTypeValue.add("Navy");
            listFilterTypeValue.add("Olive");
            listFilterTypeValue.add("Orange");
            listFilterTypeValue.add("Pink");
            listFilterTypeValue.add("Purple");
            listFilterTypeValue.add("Red");
            listFilterTypeValue.add("Silver");
            listFilterTypeValue.add("Tan");
            listFilterTypeValue.add("White");
            listFilterTypeValue.add("Yellow");

            productFilterTypeValueAdapter = new ProductFilterTypeValueAdapter(activity, listFilterTypeValue);
            binding.commanRecyclerviewFilterValue.recyclerView.setAdapter(productFilterTypeValueAdapter);
        } else {
            binding.commanRecyclerviewFilterResult.recyclerView.setVisibility(View.GONE);
            binding.floatMenu.setVisibility(View.GONE);
        }

    }

    private void getSearchData() {
        StringRequest request = new StringRequest(Request.Method.POST, Constants.search_api, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                Log.d("TAG","Response Details = "+response);

                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");
                    if (status == true){
                        listHomeDataNewArrival = new ArrayList<>();
                        listHomeDataNewArrival.clear();
                        Log.d("TAG","Response Details = "+response);
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String category_id = jsonObject.getString("category_id");
                            String subcategory_id = jsonObject.getString("subcategory_id");
                            String product_name = jsonObject.getString("name");
                            String product_image = jsonObject.getString("photo");
                            String product_id = jsonObject.getString("id");
//                            String product_id = "0";
                            int price = jsonObject.getInt("price");

                            int cprice = jsonObject.getInt("cprice");
                            Log.d("TAG"," Search rating = "+jsonObject.getString("rating"));
                            double rat = jsonObject.getDouble("rating");
                            int stock = 0;
                            String childcategory_id = jsonObject.getString("childcategory_id");

                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                        }
                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
                    }
                    if (status == false){
                        Toast.makeText(getApplicationContext(),"Not Data Found",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Registion Error = "+error.getMessage());
//                alertDialog.dismiss();
                if (error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("product_name", title);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getWishListData() {
//        StringRequest request = new StringRequest(Request.Method.POST, "https://youngersshoppingclub.com/young_apie/index.php/post/proddd", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                JSONObject object = null;
//                Log.d("TAG","Response Details = "+response);
//
//                try {
//                    object = new JSONObject(response);
//
//                    boolean status = object.getBoolean("responce");
//
//                    if (status == true){
//
//                        JSONArray array = object.getJSONArray("data");
//                        JSONObject object1 = array.getJSONObject(0);
//
//                        JSONArray  jsonArrayrelated = object1.getJSONArray("related");
//
//                        listHomeDataNewArrival = new ArrayList<>();
//
//
//                        for (int i = 0; i < jsonArrayrelated.length(); i++) {
//                            JSONObject jsonObject = jsonArrayrelated.getJSONObject(i);
//                            String category_id = "0";
//                            String subcategory_id = "0";
//                            String product_name = jsonObject.getString("name");
//                            String product_image = jsonObject.getString("photo");
//                            String product_id = jsonObject.getString("id");
//                            int price = jsonObject.getInt("pprice");
//                            int cprice = jsonObject.getInt("cprice");
//                            double rat = 0.0;
//                            int stock = 0;
//                            String childcategory_id = "";
//
//                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
//                        }
//                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
//                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
//
//                    }
//
//                    if (status == false){
//                        Toast.makeText(getApplicationContext(),"Not Data Found",Toast.LENGTH_SHORT).show();
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG,"Registion Error = "+error.getMessage());
////                alertDialog.dismiss();
//                if (error instanceof NoConnectionError){
//                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
//                }else if (error instanceof TimeoutError){
//                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
//                }else if (error instanceof ParseError){
//                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
//                }
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("product_id","72");
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);

        StringRequest request = new StringRequest(Request.Method.POST, Constants.wishList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                Log.d("TAG","Response Details = "+response);

                try {
                    object = new JSONObject(response);
                    boolean status = object.getBoolean("status");
                    if (status == true){
                        SharePref.setwishlist("wish","true",activity);
                        listHomeDataNewArrival = new ArrayList<>();
                        listHomeDataNewArrival.clear();
                        Log.d("TAG","Response Details = "+response);
                        JSONArray array = object.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject jsonObject = array.getJSONObject(i);
                            String category_id = jsonObject.getString("category_id");
                            String subcategory_id = jsonObject.getString("subcategory_id");
                            String product_name = jsonObject.getString("name");
                            String product_image = jsonObject.getString("photo");
                            String product_id = jsonObject.getString("product_id");
                            int price = jsonObject.getInt("mrp");
                            int cprice = jsonObject.getInt("mrp");
                            double rat = 0.0;
                            int stock = 0;
                            String childcategory_id = jsonObject.getString("childcategory_id");

                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                        }
                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);
                    }
                    if (status == false){
                        Toast.makeText(getApplicationContext(),"Not Data Found",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Registion Error = "+error.getMessage());
//                alertDialog.dismiss();
                if (error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("user_id", SharePref.getetLoginId("c_id",getApplicationContext()));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void getBestSeller() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.Best_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);
                    Log.d("TAG","Response Best List = "+response);
                    boolean status = object.getBoolean("status");
                    listHomeDataNewArrival = new ArrayList<>();

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("best");


                        int leg = jsonArray.length();

                        listHomeDataNewArrival.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String category_id = object1.getString("category_id");
                            String subcategory_id = "";
                            String product_name = object1.getString("product_name");
                            String product_image = object1.getString("product_image");
                            String product_id = object1.getString("product_id");
                            int price = object1.getInt("price");
                            int cprice = object1.getInt("mrp");
                            double rat = object1.getDouble("rating");
                            int stock = object1.getInt("stock");
                            String childcategory_id = "";

                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                        }


                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);

                        Log.d("TAG","New arrival List Size = "+listHomeDataNewArrival.size());

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

    private void getNewArrivalData() {
        binding.progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(Request.Method.GET, Constants.New_Arrival_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                binding.progressBar.setVisibility(View.GONE);
                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Band List = "+response);

                    boolean status = object.getBoolean("status");
                    listHomeDataNewArrival = new ArrayList<>();

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("latest_product");
                        int leg = jsonArray.length();

                        listHomeDataNewArrival.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            String category_id = object1.getString("category_id");
                            String subcategory_id = "";
                            String product_name = object1.getString("product_name");
                            String product_image = object1.getString("product_image");
                            String product_id = object1.getString("product_id");
                            int price = object1.getInt("price");
                            int cprice = object1.getInt("mrp");
                            double rat = object1.getDouble("rating");
                            int stock = object1.getInt("stock");
                            String childcategory_id = "";

                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                        }


                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);

                        Log.d("TAG","New arrival List Size = "+listHomeDataNewArrival.size());

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Edit Profile Error = "+error.getMessage());
                binding.progressBar.setVisibility(View.GONE);

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

    private void setLayout() {
        if (layoutType) {
            binding.commanRecyclerview.recyclerView.setLayoutManager(new GridLayoutManager(activity, 2));
            binding.commanRecyclerview.recyclerView.setDemoLayoutReference(R.layout.raw_productdata_grid);
        } else {
            binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }
        if (!title.equalsIgnoreCase(getResources().getString(R.string.account_Wishlist))) {
            binding.commanRecyclerviewFilterResult.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
            binding.commanRecyclerviewFilterType.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
            binding.commanRecyclerviewFilterValue.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {

            layoutType = !layoutType;
            if (layoutType) {
                item_action_add.setIcon(getResources().getDrawable(R.drawable.ic_list));
            } else {
                item_action_add.setIcon(getResources().getDrawable(R.drawable.ic_grid));
            }
            setLayout();
            productListAdapter.updateView(layoutType);
        } else if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    private MenuItem item_action_add;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_list, menu);
        item_action_add = (MenuItem) menu.findItem(R.id.action_add);
        item_action_add.setIcon(getResources().getDrawable(R.drawable.ic_list));
        if (title.equalsIgnoreCase(getResources().getString(R.string.account_Wishlist))) {
            item_action_add.setVisible(false);
        }

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra(Constants.temp) != null) {
                if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("1")) {
                    layoutType = false;
                    item_action_add.setIcon(getResources().getDrawable(R.drawable.ic_grid));
                } else if (getIntent().getStringExtra(Constants.temp).equalsIgnoreCase("2")) {
                    item_action_add.setVisible(false);
                }
            }

        }
        return true;
    }

    @Override
    public void onItemsSelectedClick(int position, String type) {
        if (type.equalsIgnoreCase(Constants.clickResult)) {
            productFilterTypeAdapter.Refresh(position);
            if (position == 2) {
                binding.linearFilterValue.setVisibility(View.GONE);
                binding.linearPriceRange.setVisibility(View.VISIBLE);
            } else {
                binding.linearFilterValue.setVisibility(View.VISIBLE);
                binding.linearPriceRange.setVisibility(View.GONE);
            }

            binding.relativeMain.setVisibility(View.GONE);
            binding.linearFilter.setVisibility(View.VISIBLE);
            item_action_add.setVisible(false);
            actionBar.setTitle(getResources().getString(R.string.Filter));
        } else if (type.equalsIgnoreCase(Constants.clickType)) {
            productFilterTypeAdapter.Refresh(position);
            if (position == 2) {
                binding.linearFilterValue.setVisibility(View.GONE);
                binding.linearPriceRange.setVisibility(View.VISIBLE);
            } else {
                binding.linearFilterValue.setVisibility(View.VISIBLE);
                binding.linearPriceRange.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (binding.relativeMain.getVisibility() == View.VISIBLE) {
            super.onBackPressed();
        } else {
            binding.relativeMain.setVisibility(View.VISIBLE);
            binding.linearFilter.setVisibility(View.GONE);
            item_action_add.setVisible(true);
            actionBar.setTitle(title);
        }
    }


    private void getData() {
        StringRequest request = new StringRequest(Request.Method.POST, "https://youngersshoppingclub.com/young_apie/index.php/post/proddd", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                Log.d("TAG","Response Details = "+response);

                try {
                    object = new JSONObject(response);

                    boolean status = object.getBoolean("responce");

                    if (status == true){

                        JSONArray array = object.getJSONArray("data");
                        JSONObject object1 = array.getJSONObject(0);




                        JSONArray  jsonArrayrelated = object1.getJSONArray("related");

                        listHomeDataNewArrival = new ArrayList<>();


                        for (int i = 0; i < jsonArrayrelated.length(); i++) {
                            JSONObject jsonObject = jsonArrayrelated.getJSONObject(i);
                            String category_id = "0";
                            String subcategory_id = "";
                            String product_name = jsonObject.getString("name");
                            String product_image = jsonObject.getString("photo");
                            String product_id = jsonObject.getString("id");
                            int price = jsonObject.getInt("pprice");
                            int cprice = jsonObject.getInt("cprice");
                            double rat = 0.0;
                            int stock = 0;
                            String childcategory_id = "";

                            listHomeDataNewArrival.add(new new_arrival_list_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                        }



                        productListAdapter = new ProductListAdapter(activity, listHomeDataNewArrival, layoutType);
                        binding.commanRecyclerview.recyclerView.setAdapter(productListAdapter);





                    }

                    if (status == false){
                        Toast.makeText(getApplicationContext(),"Not Data Found",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG,"Registion Error = "+error.getMessage());
//                alertDialog.dismiss();
                if (error instanceof NoConnectionError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getApplicationContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("product_id",""+SharePref.getProductId("product_id",getApplicationContext()));
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }
}
