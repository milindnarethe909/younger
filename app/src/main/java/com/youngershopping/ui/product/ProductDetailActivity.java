package com.youngershopping.ui.product;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.bumptech.glide.Glide;
import com.youngershopping.BaseApp;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.adapter.home.HomeDataNewArrivalAdapter;
import com.youngershopping.adapter.product.detail.ProductImagePagerAdapter;
import com.youngershopping.adapter.product.detail.ProductOptionAdapter;
import com.youngershopping.adapter.product.detail.ProductSpecificationAdapter;
import com.youngershopping.adapter.product.detail.RelatedProductAdapter;
import com.youngershopping.databinding.ActivityProductdetailBinding;
import com.youngershopping.databinding.RawProductColorBinding;
import com.youngershopping.databinding.RawProductOptionBinding;
import com.youngershopping.interfaces.OnItemClickListener;
import com.youngershopping.pojo.Add_To_Cart_Response;
import com.youngershopping.pojo.ListofColor_pojo;
import com.youngershopping.pojo.PinCodeResponse;
import com.youngershopping.pojo.Wishaddresponse;
import com.youngershopping.pojo.list_cart_response;
import com.youngershopping.pojo.new_arrival_list_pojo;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;
import com.youngershopping.ui.home.ui1.HomeActivity;
import com.youngershopping.utils.Constants;
import com.youngershopping.utils.MonthPick_Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class ProductDetailActivity extends BaseApp implements View.OnClickListener {
    private ActivityProductdetailBinding binding;
    private Activity activity = ProductDetailActivity.this;
    private String TAG = ProductDetailActivity.class.getSimpleName();
    private List<Integer> listRelatedProduct;
    private List<String> listProductSpec;
    private List<String> listProductSpecValue;
    private List<String> listProductOptionValue;
    private ProductOptionAdapter productOptionAdapter;
    private ProductSpecificationAdapter productSpecificationAdapter;
    private RelatedProductAdapter relatedProductAdapter;
    private ProductImagePagerAdapter productImagePagerAdapter;
    private int quantity = 1;

    private String produt_name = "",product_mrp = "",product_price= "";
    private int product_stock = 0;

    private ArrayList<String> listslider ;

    private List<String> colorList;

    ArrayList<ListofColor_pojo> color_pojos;

    OnItemClickListener listener;
    ColorViewAdapter colorViewAdapter;

    private List<new_arrival_list_pojo> listHomeDataNewArrival;
    private HomeDataNewArrivalAdapter homeDataNewArrivalAdapter;

    ApiInterface apiInterface;
    String vendor_id = "";

    String p_size = "",p_color = "",p_price = "",p_quantity = "",p_name = "",p_stock = "",p_c_price = "",p_id = "",p_photo = "",p_gst = "";

    int a = 0;

    String count_cart = "0";

    TextView view_alert_count_textview;
    ImageView imageview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_productdetail);
        Bundle bundle = getIntent().getExtras();
        p_id = bundle.getString("product_id");

        init();
        apiInterface = APIClient.getClient().create(ApiInterface.class);
        int sdk = android.os.Build.VERSION.SDK_INT;

        if (SharePref.getwishlist("wish",ProductDetailActivity.this).equals("true")){
            Log.d("TAG","Wishlist Ok"+SharePref.getwishlist("wish",getApplicationContext()));
            a = 1;
            if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
            } else {
                binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
            }
        }else{
            a = 0;
            Log.d("TAG","Wishlist Okk"+SharePref.getwishlist("wish",getApplicationContext()));

        }
        getCart();
        listner();
        setQuantity();

    }


    private void listner() {
        binding.btnSeeAll.setOnClickListener(this);
        binding.btnSeeAllReview.setOnClickListener(this);
        binding.imgQuantityMinus.setOnClickListener(this);
        binding.imgQuantityPlus.setOnClickListener(this);
        binding.btnCheck.setOnClickListener(this);
        binding.imgFavourite.setOnClickListener(this);
        binding.imgAddToCart.setOnClickListener(this);
        binding.imgAddToCart1.setOnClickListener(this);
        binding.imgFavourite1.setOnClickListener(this);
    }

    private void init() {
        getData();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setTitle(Html.fromHtml("<font color='#000000'>" + getResources().getString(R.string.ProductDetail) + "</font>"));
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow_white);
        upArrow.setColorFilter(Color.parseColor("#000000"), PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(upArrow);




        binding.commanRecyclerviewRelated.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.commanRecyclerviewRelated.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewRelated.recyclerView.setFocusable(false);

        binding.commanRecyclerviewSpecification.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.commanRecyclerviewSpecification.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewSpecification.recyclerView.setFocusable(false);

        binding.commanRecyclerviewOption.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.commanRecyclerviewOption.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewOption.recyclerView.setFocusable(false);


        binding.colorRecyclerviewOption.recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
        binding.colorRecyclerviewOption.recyclerView.setNestedScrollingEnabled(false);
        binding.colorRecyclerviewOption.recyclerView.setFocusable(false);

        fillData();
    }

    private void getData() {
        Log.d(TAG,"Product is = "+p_id);
        StringRequest request = new StringRequest(Request.Method.POST, "https://youngersshoppingclub.com/young_apie/index.php/post/proddd", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                JSONObject object = null;
                Log.d("TAG","Response Details = "+response);

                try {
                    object = new JSONObject(response);

                    boolean status = object.getBoolean("responce");

                    if (status == true){

                        listProductOptionValue = new ArrayList<>();
                        colorList = new ArrayList<>();
                        JSONArray array = object.getJSONArray("data");
                        JSONObject object1 = array.getJSONObject(0);

                        product_stock = object1.getInt("stock");
                        product_mrp = object1.getString("mrp");
                        product_price = object1.getString("price");
                        produt_name = object1.getString("product_name");

                        p_gst = object1.getString("gst");
                        vendor_id = object1.getString("vendor_id");

                        JSONArray jsonArray = object1.getJSONArray("sub_cat");
                        listslider = new ArrayList<>();
                        color_pojos = new ArrayList<>();
                        listProductOptionValue = new ArrayList<>();
                        for (int i = 0;i<jsonArray.length();i++){
                            JSONObject object2 = jsonArray.getJSONObject(i);
                            Log.d(TAG,"REsponse Sub  = "+i);
                            ListofColor_pojo listofColor_pojo = new ListofColor_pojo();
                            listofColor_pojo.setColor_image(object2.getString("image"));
                            listofColor_pojo.setColorCode(object2.getString("color"));
                            listofColor_pojo.setSize(object2.getString("sizes"));
                            listofColor_pojo.setPrice(object2.getString("price"));
                            listofColor_pojo.setQuantity(object2.getString("quantity"));
                            listslider.add(object2.getString("image"));
                            listProductOptionValue.add(object2.getString("sizes"));
                            colorList.add(object2.getString("color"));
                            color_pojos.add(listofColor_pojo);

                        }

                        JSONArray review = object1.getJSONArray("reviess");

                        Log.d("TAG","Review size = "+review.length());

                        if (!(review.length() == 0)) {
                            binding.llReView.setVisibility(View.VISIBLE);
                            JSONObject jsonObjectreview = review.getJSONObject((review.length() - 1));
                            JSONArray r = object1.getJSONArray("reviess");

                            int a11 = 0;

                            for (int i = 0; i < r.length(); i++) {
                                JSONObject jsonObject = r.getJSONObject(i);
                                a11 = a11 + jsonObject.getInt("rating");
                            }

                            binding.tvReview.setText(a11 + " Ratings & " + review.length() + " Reviews");
                            binding.txtReviewUsername.setText(jsonObjectreview.getString("name"));
                            binding.txtUserReview.setText(jsonObjectreview.getString("review"));
                            String[] a1 = jsonObjectreview.getString("review_date").split("\\s+");

                            String d[] = a1[0].split("-");
                            MonthPick_Class monthPickClass = new MonthPick_Class();
                            binding.txtUserReviewDate.setText("" + d[2] + "-" + monthPickClass.Months(d[1]) + "-" + d[0]);
//                        if (!(jsonObjectreview.getString("photo").equals(""))){
//                            Glide.with(activity).load(jsonObjectreview.getString("photo")).into(binding.imgReview);
//                        }
                            double a = jsonObjectreview.getDouble("rating");
                            if (a >= 5) {
                                binding.imgStar1.setImageResource(R.drawable.star_fill);
                                binding.imgStar2.setImageResource(R.drawable.star_fill);
                                binding.imgStar3.setImageResource(R.drawable.star_fill);
                                binding.imgStar4.setImageResource(R.drawable.star_fill);
                                binding.imgStar5.setImageResource(R.drawable.star_fill);

                            } else if (a >= 4 && a < 5) {
                                binding.imgStar1.setImageResource(R.drawable.star_fill);
                                binding.imgStar2.setImageResource(R.drawable.star_fill);
                                binding.imgStar3.setImageResource(R.drawable.star_fill);
                                binding.imgStar4.setImageResource(R.drawable.star_fill);
                                binding.imgStar5.setImageResource(R.drawable.star_border);
                            } else if (a >= 3 && a < 4) {
                                binding.imgStar1.setImageResource(R.drawable.star_fill);
                                binding.imgStar2.setImageResource(R.drawable.star_fill);
                                binding.imgStar3.setImageResource(R.drawable.star_fill);
                                binding.imgStar4.setImageResource(R.drawable.star_border);
                                binding.imgStar5.setImageResource(R.drawable.star_border);
                            } else if (a >= 2 && a < 3) {
                                binding.imgStar1.setImageResource(R.drawable.star_fill);
                                binding.imgStar2.setImageResource(R.drawable.star_fill);
                                binding.imgStar3.setImageResource(R.drawable.star_border);
                                binding.imgStar4.setImageResource(R.drawable.star_border);
                                binding.imgStar5.setImageResource(R.drawable.star_border);
                            } else if (a >= 1 && a < 2) {
                                binding.imgStar1.setImageResource(R.drawable.star_fill);
                                binding.imgStar2.setImageResource(R.drawable.star_border);
                                binding.imgStar3.setImageResource(R.drawable.star_border);
                                binding.imgStar4.setImageResource(R.drawable.star_border);
                                binding.imgStar5.setImageResource(R.drawable.star_border);
                            } else {
                                binding.imgStar1.setImageResource(R.drawable.star_border);
                                binding.imgStar2.setImageResource(R.drawable.star_border);
                                binding.imgStar3.setImageResource(R.drawable.star_border);
                                binding.imgStar4.setImageResource(R.drawable.star_border);
                                binding.imgStar5.setImageResource(R.drawable.star_border);
                            }
                        }else{
                            binding.llReView.setVisibility(View.GONE);
                        }


                        binding.txtTitle.setText(produt_name);
                        p_name = produt_name;
                        binding.txtPriceMain.setText(product_mrp);
                        binding.txtPriceDiscount.setText(product_price);
                        p_c_price = product_price;
                        binding.txtAvgRating.setText(object1.getString("rating"));
                        binding.txtAvgRating1.setText(object1.getString("rating"));


                        String description = object1.getString("description");

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            binding.txtDescription.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            binding.txtDescription.setText(Html.fromHtml(description));
                        }



                        JSONArray  jsonArrayrelated = object1.getJSONArray("related");

                        listHomeDataNewArrival = new ArrayList<>();
                        listHomeDataNewArrival.clear();
                        int leg = jsonArrayrelated.length();
                        if (leg<6){
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
                        }else {
                            for (int i = 0; i < 6; i++) {
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
                        }
//                        for (int i = 0;i<jsonArrayrelated.length();i++){
//                            JSONObject jsonObject = jsonArrayrelated.getJSONObject(i);
//                            String category_id = "0";
//                            String subcategory_id = "";
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
//
//                        }

                        homeDataNewArrivalAdapter = new HomeDataNewArrivalAdapter(activity, listHomeDataNewArrival);
                        binding.commanRecyclerviewRelated.recyclerView.setAdapter(homeDataNewArrivalAdapter);

                        productImagePagerAdapter = new ProductImagePagerAdapter(activity,listslider);
                        binding.viewPager.setAdapter(productImagePagerAdapter);
                        binding.indicator.setViewPager(binding.viewPager);

                        productOptionAdapter = new ProductOptionAdapter(activity, listProductOptionValue, 0);
                        binding.commanRecyclerviewOption.recyclerView.setAdapter(productOptionAdapter);

                        Log.d(TAG,"Color Sise = "+colorList.toString()+" Size = "+colorList.size());
                        colorViewAdapter = new ColorViewAdapter(colorList,activity,0,listslider,color_pojos);
                        binding.colorRecyclerviewOption.recyclerView.setAdapter(colorViewAdapter);



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
                params.put("product_id",p_id);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void fillData() {
        binding.txtPriceDiscount.setPaintFlags(binding.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        binding.colorRecyclerviewOption.recyclerView.addOnItemTouchListener(new ColorViewAdapter.RecyclerTouchListener(getApplicationContext(), binding.colorRecyclerviewOption.recyclerView, new ColorViewAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("TAG","Color Position = "+colorList.get(position).toString());
                Log.d("TAG","Color Position = "+ color_pojos.get(position).toString());

                listslider.clear();
                listslider = new ArrayList<>();
                listslider.add(color_pojos.get(position).getColor_image());

                p_photo = color_pojos.get(position).getColor_image();

                p_color = colorList.get(position);
                productImagePagerAdapter = new ProductImagePagerAdapter(activity,listslider);
                binding.viewPager.setAdapter(productImagePagerAdapter);
                binding.indicator.setViewPager(binding.viewPager);


            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));


        binding.commanRecyclerviewOption.recyclerView.addOnItemTouchListener(new ProductOptionAdapter.RecyclerTouchListener(getApplicationContext(), binding.commanRecyclerviewOption.recyclerView, new ProductOptionAdapter.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Log.d("TAG","Product Size = "+listProductOptionValue.get(position).toString());
                binding.txtPriceMain.setText(color_pojos.get(position).getPrice());
                p_price = color_pojos.get(position).getPrice();
                product_stock = Integer.parseInt(color_pojos.get(position).getQuantity());
                p_stock =  color_pojos.get(position).getQuantity();
                p_size = listProductOptionValue.get(position).toString();

            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));





//Recycler View Shop By Catogary
//        binding.colorRecyclerviewOption.re.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), rv_item, new RecyclerTouchListener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//code for touch listner
//
//            }
//
//            @Override
//            public void onLongItemClick(View view, int position) {
//
//            }
//        }));

        // TODO Product Specification

//        listProductOptionValue.add(getResources().getString(R.string.dummy_storage_op1));
//        listProductOptionValue.add(getResources().getString(R.string.dummy_storage_op2));
//        listProductOptionValue.add(getResources().getString(R.string.dummy_storage_op3));
//        listProductOptionValue.add(getResources().getString(R.string.dummy_storage_op4));
//        productOptionAdapter = new ProductOptionAdapter(activity, listProductOptionValue, 0);
//        binding.commanRecyclerviewOption.recyclerView.setAdapter(productOptionAdapter);

        // TODO Related Product
        listRelatedProduct = new ArrayList<>();
        listRelatedProduct.add(R.mipmap.man1);
        listRelatedProduct.add(R.mipmap.man2);
        listRelatedProduct.add(R.mipmap.man3);
        listRelatedProduct.add(R.mipmap.man4);
        listRelatedProduct.add(R.mipmap.man5);
        listRelatedProduct.add(R.mipmap.man6);
        relatedProductAdapter = new RelatedProductAdapter(activity, listRelatedProduct);
        binding.commanRecyclerviewRelated.recyclerView.setAdapter(relatedProductAdapter);

        // TODO Product Specification
        listProductSpec = new ArrayList<>();
        listProductSpec.add(getResources().getString(R.string.dummy_spec1));
        listProductSpec.add(getResources().getString(R.string.dummy_spec2));
        listProductSpec.add(getResources().getString(R.string.dummy_spec3));
        listProductSpec.add(getResources().getString(R.string.dummy_spec4));
        listProductSpec.add(getResources().getString(R.string.dummy_spec5));
        listProductSpec.add(getResources().getString(R.string.dummy_spec6));
        listProductSpec.add(getResources().getString(R.string.dummy_spec7));

        listProductSpecValue = new ArrayList<>();
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec11));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec22));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec33));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec44));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec55));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec66));
        listProductSpecValue.add(getResources().getString(R.string.dummy_spec77));

        if (listProductSpecValue.size() == listProductSpec.size()) {
            productSpecificationAdapter = new ProductSpecificationAdapter(activity, listProductSpec, listProductSpecValue);
            binding.commanRecyclerviewSpecification.recyclerView.setAdapter(productSpecificationAdapter);
        }

//        binding.viewPager.setAdapter(productImagePagerAdapter);
//        binding.indicator.setViewPager(binding.viewPager);

        // TODO Product Multiplication
//        productImagePagerAdapter = new ProductImagePagerAdapter(activity,listslider);
//        binding.viewPager.setAdapter(productImagePagerAdapter);
//        binding.indicator.setViewPager(binding.viewPager);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.d("TAG","Cart Click");
        if (id == android.R.id.home) {
            onBackPressed();
        }
        if (id == R.id.action_cart){
            Log.d("TAG","Radd to Cart Selected ");
        }



        return super.onOptionsItemSelected(item);
    }

    private MenuItem item_action_add;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_product_detail, menu);
//        getMenuInflater().inflate(R.menu.menu_product_detail, menu);
        item_action_add = (MenuItem) menu.findItem(R.id.action_cart);

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.action_cart);
        RelativeLayout relativeLayout = (RelativeLayout) alertMenuItem.getActionView();
        view_alert_count_textview = (TextView) relativeLayout.findViewById(R.id.view_alert_count_textview);
        imageview = (ImageView) relativeLayout.findViewById(R.id.imageview);
        view_alert_count_textview.setText(count_cart);
        view_alert_count_textview.setOnClickListener(this);
        imageview.setOnClickListener(this);
        float alpha = 0.45f;
        AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
        alphaUp.setFillAfter(true);
        binding.btnCheck.startAnimation(alphaUp);
        binding.btnCheck.setEnabled(false);
        binding.EdtPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                if (charSequence.toString().length() == 0) {
                    //add_fund_button.setBackgroundColor(Color.TRANSPARENT);
                    float alpha = 0.45f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    binding.btnCheck.startAnimation(alphaUp);
                    binding.btnCheck.setEnabled(false);
                    binding.EdtPincode.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);

                }
                else {

                    float alpha = 1f;
                    AlphaAnimation alphaUp = new AlphaAnimation(alpha, alpha);
                    alphaUp.setFillAfter(true);
                    binding.btnCheck.startAnimation(alphaUp);
                    binding.btnCheck.setEnabled(true);
                    binding.EdtPincode.setCompoundDrawablesWithIntrinsicBounds(0, 0,0, 0);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        binding.btnCheck.setOnClickListener(this);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSeeAll:
                SharePref.setProductId("product_id",p_id,getApplicationContext());
                intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.from, getResources().getString(R.string.RelatedProduct));
                startActivity(intent);
                break;
            case R.id.btnSeeAllReview:
                intent = new Intent(activity, ProductReviewActivity.class);
                intent.putExtra("product_id",p_id);
                startActivity(intent);
                break;
            case R.id.imgQuantityMinus:
                if (quantity > 1) {
                    quantity--;
                    setQuantity();
                }
                break;
            case R.id.imgQuantityPlus:
                if (quantity < product_stock) {
                    quantity++;
                    setQuantity();
                }
                break;

            case R.id.btnCheck:
                checkValidation();
                break;
            case R.id.imgFavourite:
            case R.id.imgFavourite1:
                checkFavourite();
                break;
            case R.id.imgAddToCart1:
            case R.id.imgAddToCart:
//                addCardData();
                validationCart();
                break;
            case R.id.view_alert_count_textview:
            case R.id.imageview:
                if (count_cart.equals("0")){
                    Toast.makeText(getApplicationContext(),"Cart Data is not Available",Toast.LENGTH_SHORT).show();
                }else {
                    intent = new Intent(activity, HomeActivity.class);
                    intent.putExtra(Constants.temp, "2");
                    startActivity(intent);
                }
                Log.d("TAG","Radd to Cart Selected ");
                break;
        }
    }

    private void validationCart() {
        if (p_size.equals("")){
            Toast.makeText(getApplicationContext(),"Product Size Required",Toast.LENGTH_SHORT).show();
        }else if (p_color.equals("")){
            Toast.makeText(getApplicationContext(),"Product Color Required",Toast.LENGTH_SHORT).show();
        }else{
            addCardData();
        }
    }

    private void addCardData() {
        Log.d("TAG","GST = "+p_gst);
        Call<Add_To_Cart_Response> add = apiInterface.AddCart(SharePref.getetLoginId("c_id",getApplicationContext()),
                p_quantity,p_size,p_color,p_stock,p_price,p_id,p_name,p_c_price,p_stock,"0","0","0","0","0","Units",p_photo,p_gst,vendor_id);
        add.enqueue(new Callback<Add_To_Cart_Response>() {
            @Override
            public void onResponse(Call<Add_To_Cart_Response> call, retrofit2.Response<Add_To_Cart_Response> response) {

                Add_To_Cart_Response cart_response = response.body();
                Log.d("TAG","Response Cart = "+response.body().toString());

                String status = cart_response.getStatus();
                if (status.equals("true")){
                    Toast.makeText(getApplicationContext(),"Add to Cart Successfully",Toast.LENGTH_SHORT).show();
                    getCart();
//                    Intent intent = new Intent(activity, HomeActivity.class);
//                    intent.putExtra(Constants.temp, "2");
//                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<Add_To_Cart_Response> call, Throwable t) {
                Log.d("TAG","Error for Cart = "+t.getMessage());
            }
        });
    }


    private void checkFavourite() {
        String flag = "";
        if (a == 0){
            flag = "1";
        }else{
            flag = "0";
        }

        Call<Wishaddresponse> wishaddresponseCall = apiInterface.wishlistAdd(SharePref.getetLoginId("c_id",getApplicationContext()),p_id,flag);
        wishaddresponseCall.enqueue(new Callback<Wishaddresponse>() {
            @Override
            public void onResponse(Call<Wishaddresponse> call, retrofit2.Response<Wishaddresponse> response) {
                Log.d(TAG,"Wishlist Respone = "+response.body());
                Wishaddresponse wish = response.body();
                String status = wish.getStatus();
                String msg = wish.getMsg();
                String add = wish.getAdd();
                Log.d(TAG,"Wishlist Respone = "+status+" "+msg+" "+add);

                if (status.equals("true")){
                    if (msg.equals("success")){
//                        @mipmap/account_wishlist
                        int sdk = android.os.Build.VERSION.SDK_INT;

                        a = 1;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
                        } else {
                            binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
                        }
//                        binding.imgFavourite.setBackgroundResource(@mipmap/account_wishlist);
                    }

                    if (msg.equals("available")){
                        a = 1;
                        int sdk = android.os.Build.VERSION.SDK_INT;
                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
                        } else {
                            binding.imgFavourite.setImageResource(R.mipmap.account_wishlist);
                        }
                    }
                }

                if (status.equals("false")){
                    a = 0;
                    int sdk = android.os.Build.VERSION.SDK_INT;

                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        binding.imgFavourite.setImageResource(R.drawable.favorite_fill);
                    } else {
                        binding.imgFavourite.setImageResource(R.drawable.favorite_fill);
                    }


                }

            }

            @Override
            public void onFailure(Call<Wishaddresponse> call, Throwable t) {
                Log.d(TAG,"Wishlist Respone Error = "+t.getMessage());
            }
        });

//        StringRequest request = new StringRequest(Request.Method.POST, Constants.wishlist_Add, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("user_id", SharePref.getetLoginId("id",getApplicationContext()));
//                params.put("product_id","72");
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(this);
//        queue.add(request);
    }

    private void checkValidation() {

        String pin = binding.EdtPincode.getText().toString().trim();
        if (pin.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please Enter your pin code",Toast.LENGTH_SHORT).show();
        }else if (!(pin.length() == 6)){
            Toast.makeText(getApplicationContext(),"Please Enter your six digit pin code",Toast.LENGTH_SHORT).show();
        }else{
            sendPin(pin);
        }
    }

    private void sendPin(final String pin) {

        Call<PinCodeResponse> pinCodeResponseCall = apiInterface.checkPinCode(pin);
        pinCodeResponseCall.enqueue(new Callback<PinCodeResponse>() {
            @Override
            public void onResponse(Call<PinCodeResponse> call, retrofit2.Response<PinCodeResponse> response) {
                PinCodeResponse codeResponse = response.body();

                String status = codeResponse.getStatus();

                if (status.equals("true")){
                    Toast.makeText(getApplicationContext(),"Pin code is Available",Toast.LENGTH_SHORT).show();
                    binding.EdtPincode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);
                }
                if (status.equals("false")){
                    binding.EdtPincode.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_highlight_off_black_24dp, 0);
                    Toast.makeText(getApplicationContext(),"Pin code is not Available",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PinCodeResponse> call, Throwable t) {
                Log.d("TAG","Pincode Error = "+t.getMessage());
            }
        });

//        StringRequest request = new StringRequest(Request.Method.POST, Constants.Check_pin_code, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Log.d(TAG,"Respone = "+response);
//
//                JSONObject object = null;
//
//                try {
//                    object = new JSONObject(response);
//                    boolean status = object.getBoolean("status");
//                    if(status == true){
//                        Toast.makeText(getApplicationContext(),"Pin code is Available",Toast.LENGTH_SHORT).show();
//                    }
//                    if (status == false){
//                        Toast.makeText(getApplicationContext(),"Pin code is not Available",Toast.LENGTH_SHORT).show();
//
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d(TAG,"Error = "+error.getMessage());
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String,String> params = new HashMap<>();
//                params.put("pincode",pin);
//                return params;
//            }
//        };
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//        queue.add(request);
    }

    private void setQuantity() {
        binding.txtQuantity.setText(String.valueOf(quantity));
        p_quantity = String.valueOf(quantity);
    }

    public static class ColorViewAdapter extends RecyclerView.Adapter<ColorViewAdapter.ColorViewHolder>{
        private List<String> mListData;
        private List<String> listSlider;
        private Activity activity;
        private int pos = 0;
        private String TAG = ColorViewAdapter.class.getSimpleName();
        private ArrayList<ListofColor_pojo> list;


        public ColorViewAdapter(List<String> mListData, Activity activity, int pos,List<String> listSlider,ArrayList<ListofColor_pojo> list) {
            Log.d(TAG,"Size 112 = "+mListData.size());
            this.mListData = mListData;
            this.activity = activity;
            this.pos = pos;
            this.listSlider = listSlider;
            this.list = list;
        }

        @NonNull
        @Override
        public ColorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.raw_product_color,
                    parent, false);
            return new ColorViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ColorViewHolder holder, int position) {
            Log.d(TAG,"Size = 11 "+Color.parseColor(mListData.get(position)));
            holder.binding.btnOption.setBackgroundColor(Color.parseColor(mListData.get(position)));
//            if (pos == position){
//                holder.binding.btnOption.setBackgroundResource(R.drawable.btn_selected_option);
//                holder.binding.btnOption.setTextColor(activity.getResources().getColor(R.color.colorPrimaryDark));
//            }else{
//                holder.binding.btnOption.setBackgroundResource(R.drawable.btn_unselected_option);
//                holder.binding.btnOption.setTextColor(activity.getResources().getColor(R.color.textlight1));
//            }
        }

        @Override
        public int getItemCount() {
            Log.d(TAG,"Size = "+mListData.size());
            return mListData.size();
        }

        public class ColorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private RawProductColorBinding binding;
            public ColorViewHolder(@NonNull View itemView) {
                super(itemView);
                binding = DataBindingUtil.bind(itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                pos = getPosition();
                listSlider.add(list.get(pos).getColor_image());
                Log.d(TAG,"Click Color = "+listSlider.toString());
//                productImagePagerAdapter = new ProductImagePagerAdapter(activity,listslider);

                notifyDataSetChanged();
            }
        }

        public interface ClickListener {
            void onClick(View view, int position);

            void onLongClick(View view, int position);
        }


        public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

            private GestureDetector gestureDetector;
            private ClickListener clickListener;

            public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ClickListener clickListener) {
                this.clickListener = clickListener;
                gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (child != null && clickListener != null) {
                            clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                        }
                    }
                });
            }

            @Override
            public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

                View child = rv.findChildViewUnder(e.getX(), e.getY());
                if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                    clickListener.onClick(child, rv.getChildPosition(child));
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        }


    }


    private void getCart() {
        Call<list_cart_response> cart_responseCall = apiInterface.getCartList(SharePref.getetLoginId("c_id",getApplicationContext()));
        cart_responseCall.enqueue(new Callback<list_cart_response>() {
            @Override
            public void onResponse(Call<list_cart_response> call, retrofit2.Response<list_cart_response> response) {
                list_cart_response cart_response = response.body();
                String status = cart_response.getStatus();
                Log.d("TAG","Cart  status = "+status);

                if (status.equals("true")){
                    Log.d("TAG","Cart Response = "+count_cart);
                    count_cart = ""+cart_response.getList_of_cart().size();
                    view_alert_count_textview.setText(count_cart);
                    Log.d("TAG","Cart Response = "+count_cart);
                }
            }

            @Override
            public void onFailure(Call<list_cart_response> call, Throwable t) {

            }
        });
    }



}
