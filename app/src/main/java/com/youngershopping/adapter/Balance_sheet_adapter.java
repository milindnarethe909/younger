package com.youngershopping.adapter;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.youngershopping.R;
import com.youngershopping.pojo.list_of_cart;

import java.util.ArrayList;
import java.util.List;

public class Balance_sheet_adapter extends RecyclerView.Adapter<Balance_sheet_adapter.RowViewHolder> {

    List<list_of_cart> list;
    Activity activity;
    String shipping_charges;

    public Balance_sheet_adapter(List<list_of_cart> list, Activity activity,String shipping_charges) {
        this.list = list;
        this.activity = activity;
        this.shipping_charges = shipping_charges;
    }

    @NonNull
    @Override
    public RowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.table_list_item, parent, false);
        return new RowViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RowViewHolder holder, int position) {
        int rowcount = holder.getPosition();
//        if (rowcount == 0){
//            holder.txtcolorhead.setVisibility(View.VISIBLE);
//            holder.txtcolor.setVisibility(View.GONE);
//            holder.txtSrNo.setText("Sr. No");
//            holder.txtProductName.setText("Product Name");
//            holder.txtprice.setText("Price");
//            holder.txtqty.setText("Qty");
//            holder.txtsize.setText("Size");
////            holder.txtcolorhead.setText("Color");
//            holder.txtgst.setText("GST");
//            holder.txts_totalP.setText("Total Price");
//            holder.txtShipping.setText("Shpping Charge");
//
//            holder.txtSrNo.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtProductName.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtprice.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtqty.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtsize.setBackgroundResource(R.drawable.table_header_cell_bg);
////            holder.txtcolorhead.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtgst.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txts_totalP.setBackgroundResource(R.drawable.table_header_cell_bg);
//            holder.txtShipping.setBackgroundResource(R.drawable.table_header_cell_bg);
//
//            holder.txtSrNo.setTextColor(Color.parseColor("#000000"));
//            holder.txtProductName.setTextColor(Color.parseColor("#000000"));
//            holder.txtprice.setTextColor(Color.parseColor("#000000"));
//            holder.txtqty.setTextColor(Color.parseColor("#000000"));
//            holder.txtsize.setTextColor(Color.parseColor("#000000"));
////            holder.txtcolorhead.setTextColor(Color.parseColor("#000000"));
//            holder.txtgst.setTextColor(Color.parseColor("#000000"));
//            holder.txts_totalP.setTextColor(Color.parseColor("#000000"));
//            holder.txtShipping.setTextColor(Color.parseColor("#000000"));
//
//
//
//        }else{
        list_of_cart ofCart = list.get(rowcount);
//            holder.txtcolorhead.setVisibility(View.GONE);
//            holder.txtcolor.setVisibility(View.VISIBLE);
//            holder.txtSrNo.setText(""+rowcount);

        holder.txtProductName.setText(ofCart.getName());
        holder.txtprice.setText(ofCart.getPrice()+" ₹");
        holder.txtqty.setText(ofCart.getQty());
        holder.txtsize.setText(ofCart.getSize());
//            holder.txtcolor.setBackgroundColor(Color.parseColor(ofCart.getColor()));
        holder.txtgst.setText(ofCart.getGst()+" %");
        holder.txtShipping.setText(ofCart.getQty()+" * "+shipping_charges);
        double p = Double.parseDouble(ofCart.getPrice());    // Product Price
        int g = Integer.parseInt(ofCart.getGst());           // Product Gst
        int q = Integer.parseInt(ofCart.getQty());             // Qty
        Glide.with(activity).load("https://youngersshoppingclub.com/assets/images/"+list.get(position).getPhoto()).into(holder.img);
        double pq = p * q;                                  // product price * qty

        double tp = (pq * g) / 100;                                  // Precentage

        int s = Integer.parseInt(shipping_charges);
        double total = pq + tp + (q * s);

        holder.txts_totalP.setText(total+" ₹");
        holder.txtProductName.setBackgroundColor(Color.parseColor("#000000"));


//            holder.txtSrNo.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtProductName.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtprice.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtqty.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtsize.setBackgroundResource(R.drawable.table_content_cell_bg);
////            holder.ll_color.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtgst.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txtShipping.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txts_totalP.setBackgroundResource(R.drawable.table_content_cell_bg);
//            holder.txts_totalP.setTextColor(Color.parseColor("#000000"));
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RowViewHolder extends RecyclerView.ViewHolder {
        TextView txtSrNo;
        TextView txtProductName;
        TextView txtprice;
        TextView txtqty;
        TextView txtsize;
        TextView txtcolor,txtcolorhead;
        TextView txtgst;
        TextView txts_totalP,txtShipping;
        LinearLayout ll_color;
        ImageView img;

        public RowViewHolder(@NonNull View itemView) {
            super(itemView);
//            txtSrNo = (TextView) itemView.findViewById(R.id.txtSrNo);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtprice = (TextView) itemView.findViewById(R.id.txtprice);
            txtqty = (TextView) itemView.findViewById(R.id.txtqty);
            txtsize = (TextView) itemView.findViewById(R.id.txtsize);
//            txtcolor = (TextView) itemView.findViewById(R.id.txtcolor);
            txtgst = (TextView) itemView.findViewById(R.id.txtgst);
            txts_totalP = (TextView) itemView.findViewById(R.id.txts_totalP);
            ll_color = (LinearLayout) itemView.findViewById(R.id.ll_color);
//            txtcolorhead = (TextView) itemView.findViewById(R.id.txtcolorhead);
            txtShipping = (TextView) itemView.findViewById(R.id.txtShipping);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}
