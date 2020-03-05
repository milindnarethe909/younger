package com.youngershopping.ui.product;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
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
import com.youngershopping.adapter.product.ProductReviewAdapter;
import com.youngershopping.adapter.product.detail.ProductImagePagerAdapter;
import com.youngershopping.adapter.product.detail.ProductOptionAdapter;
import com.youngershopping.databinding.ActivityProductreviewBinding;
import com.youngershopping.pojo.ListofColor_pojo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductReviewActivity extends BaseApp {
    private ActivityProductreviewBinding binding;
    private Activity activity = ProductReviewActivity.this;
    private String TAG = ProductReviewActivity.class.getSimpleName();
    private ProductReviewAdapter productReviewAdapter;
    private List<String> listProductReview;
    private List<String> listDiscreiption;
    private List<Double> doublesRating;
    private List<String> listImage;
    private List<String> listDate;
    String product_id = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_productreview);
        Bundle bundle = getIntent().getExtras();
        product_id = bundle.getString("product_id");
        init();
    }

    private void init() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.commanRecyclerview.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        fillData();
    }

    private void fillData() {
        getData();
        listProductReview = new ArrayList<>();
        listProductReview.add(getResources().getString(R.string.dummy_user2));
        listProductReview.add(getResources().getString(R.string.dummy_user1));
//        productReviewAdapter = new ProductReviewAdapter(activity, listProductReview);
//        binding.commanRecyclerview.recyclerView.setAdapter(productReviewAdapter);
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

                        binding.ll.setVisibility(View.VISIBLE);
                        JSONArray array = object.getJSONArray("data");
                        JSONObject object1 = array.getJSONObject(0);

                       binding.txtAvgRating1.setText(object1.getString("rating"));

                        JSONArray review = object1.getJSONArray("reviess");
                        listProductReview = new ArrayList<>();
                        listDiscreiption = new ArrayList<>();
                        listImage = new ArrayList<>();
                        listDate = new ArrayList<>();
                        int a1 = 0;
                        doublesRating = new ArrayList<>();
                        for (int i = 0;i<review.length();i++){
                            JSONObject jsonObjectreview = review.getJSONObject(i);
                            listProductReview.add(jsonObjectreview.getString("name"));
                            listDiscreiption.add(jsonObjectreview.getString("review"));
                            doublesRating.add(jsonObjectreview.getDouble("rating"));
                            listImage.add(jsonObjectreview.getString("photo"));
                            listDate.add(jsonObjectreview.getString("review_date"));
                            a1 = a1 + jsonObjectreview.getInt("rating");

                        }
                        binding.tvRatingReview.setText(a1+" Ratings & "+review.length()+" Reviews");
                        productReviewAdapter = new ProductReviewAdapter(activity, listProductReview,listDiscreiption,doublesRating,listImage,listDate);
                        binding.commanRecyclerview.recyclerView.setAdapter(productReviewAdapter);



                        double a = object1.getDouble("rating");
                        if(a>=5){
                            binding.imgStar1.setImageResource(R.drawable.star_fill);
                            binding.imgStar2.setImageResource(R.drawable.star_fill);
                            binding.imgStar3.setImageResource(R.drawable.star_fill);
                            binding.imgStar4.setImageResource(R.drawable.star_fill);
                            binding.imgStar5.setImageResource(R.drawable.star_fill);

                        }else if(a>=4 && a<5){
                            binding.imgStar1.setImageResource(R.drawable.star_fill);
                            binding.imgStar2.setImageResource(R.drawable.star_fill);
                            binding.imgStar3.setImageResource(R.drawable.star_fill);
                            binding.imgStar4.setImageResource(R.drawable.star_fill);
                            binding.imgStar5.setImageResource(R.drawable.star_border);
                        }else if(a>=3 && a<4){
                            binding.imgStar1.setImageResource(R.drawable.star_fill);
                            binding.imgStar2.setImageResource(R.drawable.star_fill);
                            binding.imgStar3.setImageResource(R.drawable.star_fill);
                            binding.imgStar4.setImageResource(R.drawable.star_border);
                            binding.imgStar5.setImageResource(R.drawable.star_border);
                        }else if(a>=2 && a<3){
                            binding.imgStar1.setImageResource(R.drawable.star_fill);
                            binding.imgStar2.setImageResource(R.drawable.star_fill);
                            binding.imgStar3.setImageResource(R.drawable.star_border);
                            binding.imgStar4.setImageResource(R.drawable.star_border);
                            binding.imgStar5.setImageResource(R.drawable.star_border);
                        }else if(a>=1 && a<2){
                            binding.imgStar1.setImageResource(R.drawable.star_fill);
                            binding.imgStar2.setImageResource(R.drawable.star_border);
                            binding.imgStar3.setImageResource(R.drawable.star_border);
                            binding.imgStar4.setImageResource(R.drawable.star_border);
                            binding.imgStar5.setImageResource(R.drawable.star_border);
                        }else{
                            binding.imgStar1.setImageResource(R.drawable.star_border);
                            binding.imgStar2.setImageResource(R.drawable.star_border);
                            binding.imgStar3.setImageResource(R.drawable.star_border);
                            binding.imgStar4.setImageResource(R.drawable.star_border);
                            binding.imgStar5.setImageResource(R.drawable.star_border);
                        }


//                        binding.txtTitle.setText(produt_name);
//                        binding.txtPriceMain.setText(product_mrp);
//                        binding.txtPriceDiscount.setText(product_price);
//                        binding.txtAvgRating.setText(object1.getString("rating"));
//                        binding.txtAvgRating1.setText(object1.getString("rating"));








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
                params.put("product_id","72");
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }



}
