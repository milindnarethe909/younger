package com.youngershopping.ui.home.ui1.fragment;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
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
import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.adapter.home.HomeDataBestSellingAdapter;
import com.youngershopping.adapter.home.HomeDataBrandsAdapter;
import com.youngershopping.adapter.home.HomeDataCategoryAdapter;
import com.youngershopping.adapter.home.HomeDataNewArrivalAdapter;
import com.youngershopping.adapter.home.HomeDataSaleAdapter;
import com.youngershopping.adapter.home.HomePagerAdapter;
import com.youngershopping.adapter.home.HomePagerAdapter1;
import com.youngershopping.connection_internet.InternetConnection;
import com.youngershopping.databinding.DialogSupportBinding;
import com.youngershopping.databinding.FragmentHomeBinding;
import com.youngershopping.pojo.band_name_list;
import com.youngershopping.pojo.best_seller_pojo;
import com.youngershopping.pojo.new_arrival_list_pojo;
import com.youngershopping.pojo.serach_details_gson;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.ui.product.ProductListActivity;
import com.youngershopping.utils.Constants;
import com.youngershopping.webapi.APIUrls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment . OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends BaseAppFragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = HomeFragment.class.getName();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private FragmentHomeBinding binding;
    private Activity activity;
    private HomePagerAdapter homePagerAdapter;
    private HomeDataNewArrivalAdapter homeDataNewArrivalAdapter;
    private HomeDataBestSellingAdapter homeDataBestSellingAdapter;
    private HomeDataSaleAdapter homeDataSaleAdapter;
    private HomeDataCategoryAdapter homeDataCategoryAdapter;
    private HomeDataBrandsAdapter homeDataBrandsAdapter;
    private List<band_name_list> listHomeDataBrands;
    private List<new_arrival_list_pojo> listHomeDataNewArrival;
    private List<best_seller_pojo> listHomeDataBestSelling;
    private List<Integer> listHomeDataSale;
    private List<Integer> listHomeDataCategoryIcon;
    private List<String> listHomeDataCategoryLable;

    private ArrayList<String> bannerlist;
    private ArrayList<String> bannerlist1;
    android.app.AlertDialog.Builder builder;

    public HomeFragment() {
        // Required rating_empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (new InternetConnection().checkConnection(getContext())) {
            binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
            init();
            listner();

        }else{
            ShowDialog();
        }
        return binding.getRoot();
    }

    private void ShowDialog() {
        builder = new android.app.AlertDialog.Builder(getActivity());
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage("") .setTitle("Internet Connection");

        //Setting message manually and performing action on button click
        builder.setMessage("Do you want to close this application ?")
                .setCancelable(false)
                .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(getActivity(), HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
        //Creating dialog box
        android.app.AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Internet Connection");
        alert.show();
    }

    private void listner() {
        binding.btnSeeAllBestSelling.setOnClickListener(this);
        binding.btnSeeAllNewArrival.setOnClickListener(this);
        binding.imgSearchVoice.setOnClickListener(this);
//        edtSearch
    }

    private void init() {
        activity = (HomeActivity) getActivity();

        binding.commanRecyclerviewNewarrival.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.commanRecyclerviewNewarrival.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewNewarrival.recyclerView.setFocusable(false);

        binding.commanRecyclerviewBestSelling.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.commanRecyclerviewBestSelling.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewBestSelling.recyclerView.setFocusable(false);

        binding.commanRecyclerviewSale.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.commanRecyclerviewSale.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewSale.recyclerView.setFocusable(false);

        binding.commanRecyclerviewCategory.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewCategory.recyclerView.setFocusable(false);
        binding.commanRecyclerviewCategory.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));

        binding.commanRecyclerviewBrands.recyclerView.setLayoutManager(new GridLayoutManager(activity, 4));
        binding.commanRecyclerviewBrands.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewBrands.recyclerView.setFocusable(false);

        fillData();
    }

    private void fillData() {

        bannerlist = new ArrayList<>();
        bannerlist1 = new ArrayList<>();
        listHomeDataBrands = new ArrayList<>();
        listHomeDataNewArrival = new ArrayList<>();
        listHomeDataBestSelling = new ArrayList<>();
        getBanner();
        getBandList();
        getNewArrival();
        getBestSeller();


        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12 * 2, getResources().getDisplayMetrics());
        binding.viewPager.setPadding(margin, 0, margin, 0);
        binding.viewPager.setClipToPadding(false);
        binding.viewPager.setCurrentItem(300);
        binding.viewPager1.setPadding(margin, 0, margin, 0);
        binding.viewPager1.setClipToPadding(false);
        binding.viewPager1.setCurrentItem(300);

        setupAutoPager();
        setupAutoPager1();

        binding.edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    serachData();
                    return true;
                }
                return false;
            }
        });



//        listHomeDataBrands.add(R.mipmap.brand_gionee);
//        listHomeDataBrands.add(R.mipmap.brand_hero);
//        listHomeDataBrands.add(R.mipmap.brand_hanes);
//        listHomeDataBrands.add(R.mipmap.brand_phoenix);
//        listHomeDataBrands.add(R.mipmap.brand_rolex);
//        listHomeDataBrands.add(R.mipmap.brand_levis);
//        listHomeDataBrands.add(R.mipmap.brand_ebi);
//        listHomeDataBrands.add(R.mipmap.brand_vivo);

        listHomeDataSale = new ArrayList<>();
        listHomeDataSale.add(R.mipmap.camera4);
        listHomeDataSale.add(R.mipmap.camera4);
        listHomeDataSale.add(R.mipmap.book1);
        listHomeDataSale.add(R.mipmap.camera5);
        listHomeDataSale.add(R.mipmap.mobile1);

//        listHomeDataBestSelling = new ArrayList<>();
//        listHomeDataBestSelling.add(R.mipmap.camera1);
//        listHomeDataBestSelling.add(R.mipmap.man4);
//        listHomeDataBestSelling.add(R.mipmap.book1);
//        listHomeDataBestSelling.add(R.mipmap.man5);
//        listHomeDataBestSelling.add(R.mipmap.camera2);
//        listHomeDataBestSelling.add(R.mipmap.man6);
//        listHomeDataBestSelling.add(R.mipmap.camera3);

//        listHomeDataNewArrival = new ArrayList<>();
//        listHomeDataNewArrival.add(R.mipmap.mackbook1);
//        listHomeDataNewArrival.add(R.mipmap.man1);
//        listHomeDataNewArrival.add(R.mipmap.mobile1);
//        listHomeDataNewArrival.add(R.mipmap.man2);
//        listHomeDataNewArrival.add(R.mipmap.man3);
//        listHomeDataNewArrival.add(R.mipmap.dress1);

        listHomeDataCategoryIcon = new ArrayList<>();
        listHomeDataCategoryLable = new ArrayList<>();
        listHomeDataCategoryIcon.add(R.mipmap.cate_all);
        listHomeDataCategoryIcon.add(R.mipmap.cate_mobile);
        listHomeDataCategoryIcon.add(R.mipmap.cate_fashion_men);
        listHomeDataCategoryIcon.add(R.mipmap.cate_fashion_women);
        listHomeDataCategoryIcon.add(R.mipmap.cate_tv);
        listHomeDataCategoryIcon.add(R.mipmap.cate_laptop);
        listHomeDataCategoryIcon.add(R.mipmap.cate_kitchen);
        listHomeDataCategoryIcon.add(R.mipmap.cate_toy_shop);
        listHomeDataCategoryIcon.add(R.mipmap.cate_motorcycle);

        listHomeDataCategoryLable.add(getResources().getString(R.string.allCategory));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category1));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category2));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category3));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category4));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category5));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category6));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category7));
        listHomeDataCategoryLable.add(getResources().getString(R.string.dummy_category9));

        homeDataCategoryAdapter = new HomeDataCategoryAdapter(activity, listHomeDataCategoryIcon, listHomeDataCategoryLable);
        binding.commanRecyclerviewCategory.recyclerView.setAdapter(homeDataCategoryAdapter);



        homeDataSaleAdapter = new HomeDataSaleAdapter(activity, listHomeDataSale);
        binding.commanRecyclerviewSale.recyclerView.setAdapter(homeDataSaleAdapter);

//        homeDataNewArrivalAdapter = new HomeDataNewArrivalAdapter(activity, listHomeDataNewArrival);
//        binding.commanRecyclerviewNewarrival.recyclerView.setAdapter(homeDataNewArrivalAdapter);

        homeDataBestSellingAdapter = new HomeDataBestSellingAdapter(activity, listHomeDataBestSelling);
        binding.commanRecyclerviewBestSelling.recyclerView.setAdapter(homeDataBestSellingAdapter);

    }

    private int currentPage = 0;
    private void setupAutoPager()
    {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run()
            {

                binding.viewPager.setCurrentItem(currentPage, true);
                if(currentPage == Integer.MAX_VALUE)
                {
                    currentPage = 0;
                    Log.d("TAG","Count 1 = "+currentPage);
                }
                else
                {
                    if (currentPage<bannerlist.size()) {
                        ++currentPage;
                        Log.d("TAG","Count 11 = "+currentPage);
                    }else{
                        currentPage = 0;
                        Log.d("TAG","Count 12 = "+currentPage);
                    }

                }
            }
        };
        Log.d("TAG","Count 3 = "+currentPage);
        currentPage  = 0;
        Log.d("TAG","Count 4 = "+currentPage);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000);
    }
    private int currentPage1 = 0;
    private void setupAutoPager1()
    {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run()
            {

                binding.viewPager1.setCurrentItem(currentPage1, true);
                if(currentPage1 == Integer.MAX_VALUE)
                {
                    currentPage1 = 0;
                    Log.d("TAG","Count 111 = "+currentPage1);
                }
                else
                {
                    if (currentPage1<bannerlist1.size()) {
                        ++currentPage1;
                        Log.d("TAG","Count yes = "+currentPage1);
                    }else{
                        currentPage1 = 0;
                        Log.d("TAG","Count no = "+currentPage1);
                    }

                }
            }
        };
        Log.d("TAG","Count 3 = "+currentPage1);
        currentPage1  = 0;
        Log.d("TAG","Count 4 = "+currentPage1);


        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 3000);
    }

    private void getBestSeller() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.Best_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Best Seller List = "+response);

                    boolean status = object.getBoolean("status");

                    listHomeDataBestSelling.clear();

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("best");

                        int leg = jsonArray.length();
                        if (leg<6){
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String category_id = object1.getString("category_id");
                                String subcategory_id = "";
                                String product_name = object1.getString("product_name");
                                String product_image = object1.getString("product_image");
                                String product_id = object1.getString("product_id");
                                String price = object1.getString("price");
                                String cprice = object1.getString("mrp");
                                double rat = object1.getDouble("rating");
                                int stock = object1.getInt("stock");
                                String childcategory_id = "";

                                listHomeDataBestSelling.add(new best_seller_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                            }
                        }else {
                            for (int i = 0; i < 6; i++) {
                                JSONObject object1 = jsonArray.getJSONObject(i);

                                String category_id = object1.getString("category_id");
                                String subcategory_id = "";
                                String product_name = object1.getString("product_name");
                                String product_image = object1.getString("product_image");
                                String product_id = object1.getString("product_id");
                                String price = object1.getString("price");
                                String cprice = object1.getString("mrp");
                                double rat = object1.getDouble("rating");
                                int stock = object1.getInt("stock");
                                String childcategory_id = "";

                                listHomeDataBestSelling.add(new best_seller_pojo(category_id, childcategory_id, cprice, price, product_id, product_image, product_name, subcategory_id, rat, stock));
                            }
                        }

                        homeDataBestSellingAdapter = new HomeDataBestSellingAdapter(activity, listHomeDataBestSelling);
                        binding.commanRecyclerviewBestSelling.recyclerView.setAdapter(homeDataBestSellingAdapter);

                        Log.d("TAG","Best Seller List Size = "+listHomeDataNewArrival.size());

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
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void getNewArrival() {

        StringRequest request = new StringRequest(Request.Method.GET, Constants.New_Arrival_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;
                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Band List = "+response);

                    boolean status = object.getBoolean("status");

                    listHomeDataNewArrival.clear();

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("latest_product");


                        int leg = jsonArray.length();
                        if (leg<6){
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
                        }else {
                            for (int i = 0; i < 6; i++) {
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
                        }

                        homeDataNewArrivalAdapter = new HomeDataNewArrivalAdapter(activity, listHomeDataNewArrival);
                        binding.commanRecyclerviewNewarrival.recyclerView.setAdapter(homeDataNewArrivalAdapter);

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
                try {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof TimeoutError) {
                        Toast.makeText(getContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getContext(), "Parse Error", Toast.LENGTH_SHORT).show();
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void getBandList() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.Band_List_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;

                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Band List = "+response);

                    boolean status = object.getBoolean("status");


                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("brands_banners");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            band_name_list list = new band_name_list();

                            list.setId(object1.getString("id"));
                            list.setName(object1.getString("title"));
                            list.setImage(object1.getString("image"));

                            listHomeDataBrands.add(list);
                        }

                        homeDataBrandsAdapter = new HomeDataBrandsAdapter(activity, listHomeDataBrands);
                        binding.commanRecyclerviewBrands.recyclerView.setAdapter(homeDataBrandsAdapter);
                        Log.d("TAG","List Band List Size = "+listHomeDataBrands.size());

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
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }

    private void getBanner() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.BannerList_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;

                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Bannerb = "+response);

                    boolean status = object.getBoolean("status");

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("data");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            bannerlist.add(object1.getString("slider_image"));
                        }
                        Log.d("TAG","List Banner Size = "+bannerlist.size());
                        homePagerAdapter = new HomePagerAdapter(bannerlist,activity);
                        binding.viewPager.setAdapter(homePagerAdapter);
                        getBanner1();
//                        binding.viewPager1.setAdapter(homePagerAdapter);

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
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }


    private void getBanner1() {
        StringRequest request = new StringRequest(Request.Method.GET, Constants.banner_offer, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject object = null;

                try {
                    object = new JSONObject(response);

                    Log.d("TAG","Response Bannerb = "+response);

                    boolean status = object.getBoolean("status");

                    if (status == true){
                        JSONArray jsonArray = object.getJSONArray("data");

                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object1 = jsonArray.getJSONObject(i);
                            bannerlist1.add(object1.getString("image"));
                        }
                        Log.d("TAG","List Banner Size = "+bannerlist1.size());
                        HomePagerAdapter1 homePagerAdapter1 = new HomePagerAdapter1(bannerlist1,activity);
                        binding.viewPager1.setAdapter(homePagerAdapter1);

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
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof TimeoutError){
                    Toast.makeText(getContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
                }else if (error instanceof ParseError){
                    Toast.makeText(getContext(),"Parse Error",Toast.LENGTH_SHORT).show();
                }
            }
        });
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }



    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSeeAllNewArrival:
                intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.from, getResources().getString(R.string.NewArrival));

                startActivity(intent);
                break;
            case R.id.btnSeeAllBestSelling:
                intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.from, getResources().getString(R.string.BestSelling));
                startActivity(intent);
                break;
            case R.id.cardHelp:
                showDialog();
                break;
            case R.id.imgSearchVoice:
                searchValidation();
                break;
        }
    }

    private void searchValidation() {
        String search = binding.edtSearch.getText().toString().trim();
        if (search.isEmpty()){
            binding.edtSearch.setError("Please Search by Product, Brands, Category, etc..");
        }else{
           serachData();
        }
    }

    private void serachData() {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Log.d("TAG"," Search = "+binding.edtSearch.getText().toString().trim());
        Call<serach_details_gson> serachDetailsGsonCall =  apiInterface.searchData(binding.edtSearch.getText().toString().trim());
        serachDetailsGsonCall.enqueue(new Callback<serach_details_gson>() {
            @Override
            public void onResponse(Call<serach_details_gson> call, retrofit2.Response<serach_details_gson> response) {
                serach_details_gson serach_details_gson = response.body();
                String status = serach_details_gson.getStatus();
                if (status.equals("true")){
                    Intent intent = new Intent(activity, ProductListActivity.class);
                    intent.putExtra(Constants.from, ""+binding.edtSearch.getText().toString().trim());
                    startActivity(intent);
                }
                if (status.equals("false")){
                    Toast.makeText(getContext(),""+serach_details_gson.getMsg(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<serach_details_gson> call, Throwable t) {

            }
        });

    }

    public AlertDialog showDialog() {

        LayoutInflater flater = this.getLayoutInflater();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        final DialogSupportBinding alertLayout = DataBindingUtil.inflate(flater, R.layout.dialog_support, null, false);

        dialogBuilder.setView(alertLayout.getRoot());
        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        alertLayout.btnPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        alertLayout.txtNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    PackageManager pm = activity.getPackageManager();
                    pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(APIUrls.WhatsAPi));
                    startActivity(i);
                } catch (PackageManager.NameNotFoundException e) {
                    showToast(getResources().getString(R.string.whatsapp_not_install),activity);
                    e.printStackTrace();
                }
            }
        });
        alertLayout.txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.putExtra(Intent.EXTRA_EMAIL, new String[]{getResources().getText(R.string.email) + ""});
                email.putExtra(Intent.EXTRA_SUBJECT, getResources().getText(R.string.app_name) + "");
                email.putExtra(Intent.EXTRA_TEXT, "");
                email.setType("message/rfc822");
                startActivity(Intent.createChooser(email, getResources().getString(R.string.txt_support_help)));
            }
        });

        alertDialog.setCanceledOnTouchOutside(false);
        return alertDialog;
    }
}
