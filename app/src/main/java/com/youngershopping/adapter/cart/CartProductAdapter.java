package com.youngershopping.adapter.cart;

import android.app.Activity;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youngershopping.R;
import com.youngershopping.SharPref.SharePref;
import com.youngershopping.databinding.RawCartproductBinding;
import com.youngershopping.pojo.list_cart_deteted_response;
import com.youngershopping.pojo.list_cart_response;
import com.youngershopping.pojo.list_of_cart;
import com.youngershopping.retrofit.APIClient;
import com.youngershopping.retrofit.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Android on 8/6/2018.
 */
public class CartProductAdapter extends RecyclerView.Adapter<CartProductAdapter.MyViewHolder> {

    private List<Integer> mListData;
    private List<String> mListDatatemp;
    private Activity activity;
    private int quantity = 1;
    List<list_of_cart> list;

    public CartProductAdapter(Activity con, List<Integer> mListData, List<String> mListDatatemp,List<list_of_cart> list) {
        this.activity = con;
        this.mListDatatemp = mListDatatemp;
        this.mListData = mListData;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_cartproduct,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        holder.binding.img.setImageResource(mListData.get(position));
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+list.get(position).getPhoto()).into(holder.binding.img);
        Log.i("TAG", "onBindViewHolder: Name = ."+list.get(position).getName());
        holder.binding.txtTitle.setText(list.get(position).getName());

        holder.binding.txtPriceMain.setText("₹"+list.get(position).getPrice());
//        holder.binding.txtPriceDiscount.setText(activity.getResources().getString(R.string.dummy_price_discount));
        holder.binding.txtPriceDiscount.setText("₹"+list.get(position).getCprice());
        quantity = Integer.parseInt(list.get(position).getQty());
        Log.d("TAG","Qu = "+quantity);
        Log.d("TAG","Qu 1 = "+list.get(position).getQty());
        holder.binding.imgCompare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletedCartData(list.get(position).getId(),position);


            }
        });

        holder.binding.txtPriceDiscount.setPaintFlags(holder.binding.txtPriceDiscount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.binding.txtQuantity.setText(String.valueOf(quantity));
        holder.binding.txtQuantity.setTag(String.valueOf(quantity));
        String str = (String) holder.binding.txtQuantity.getText().toString().trim();
        quantity = Integer.parseInt(str);
        Log.d("TAG","Qu 1 = "+quantity);
        final int size1 = Integer.parseInt(list.get(position).getProduct_stock());
        holder.binding.imgQuantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity > 1) {
                    quantity--;
                    holder.binding.txtQuantity.setText(String.valueOf(quantity));
                    holder.binding.txtQuantity.setTag(String.valueOf(quantity));
                    list.get(position).setQty(""+quantity);
                }
            }
        });

        holder.binding.imgQuantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (quantity < size1) {
                    quantity++;
                    holder.binding.txtQuantity.setText(String.valueOf(quantity));
                    holder.binding.txtQuantity.setTag(String.valueOf(quantity));
                    list.get(position).setQty(""+quantity);
                }

            }
        });


    }

    private void deletedCartData(String p_id, final int p) {
        ApiInterface apiInterface = APIClient.getClient().create(ApiInterface.class);
        Call<list_cart_deteted_response> cartResponseCall = apiInterface.deleted_cart(SharePref.getetLoginId("c_id",activity),p_id);
        cartResponseCall.enqueue(new Callback<list_cart_deteted_response>() {
            @Override
            public void onResponse(Call<list_cart_deteted_response> call, Response<list_cart_deteted_response> response) {
                list_cart_deteted_response deteted_response = response.body();
                String s = deteted_response.getStatus();
                if (s.equals("true")){
                    removeAt(p);
                    Toast.makeText(activity,"Remove Cart Item",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<list_cart_deteted_response> call, Throwable t) {
                    Log.d("TAG","Error = "+t.getMessage());
            }
        });

    }

    public void removeAt(int position) {
        list.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, list.size());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawCartproductBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            binding.imgQuantityMinus.setOnClickListener(this);
            binding.imgQuantityPlus.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            binding.txtQuantity.setText(String.valueOf(quantity));
            String str = (String) binding.txtQuantity.getText().toString().trim();
            quantity = Integer.parseInt(str);
            switch (view.getId()) {
//                case R.id.imgQuantityMinus:
////                    if (quantity > 1) {
////                        quantity--;
////                        binding.txtQuantity.setText(String.valueOf(quantity));
////                        binding.txtQuantity.setTag(String.valueOf(quantity));
////                    }
//                    break;
//                case R.id.imgQuantityPlus:
////                    if (quantity < 99) {
////                        quantity++;
////                        binding.txtQuantity.setText(String.valueOf(quantity));
////                        binding.txtQuantity.setTag(String.valueOf(quantity));
////                    }
//                    break;

            }
        }
    }

}

