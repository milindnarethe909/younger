package com.youngershopping.adapter.product;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawProductreviewBinding;
import com.youngershopping.utils.MonthPick_Class;

import java.util.List;

/**
 * Created by Android on 8/6/2018.
 */
public class ProductReviewAdapter extends RecyclerView.Adapter<ProductReviewAdapter.MyViewHolder> {

    private List<String> mListData;
    private Activity activity;
    private List<String> listDiscription;
    private List<Double> doublesRating;
    private List<String> listImage;
    private List<String> listDate;

    public ProductReviewAdapter(Activity con, List<String> mListData,List<String> listDiscription,List<Double> doublesRating,List<String> listImage,List<String> listDate) {
        this.activity = con;
        this.mListData = mListData;
        this.listDiscription = listDiscription;
        this.doublesRating = doublesRating;
        this.listImage = listImage;
        this.listDate = listDate;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_productreview,
                viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
//        if (position % 2 == 0){
//            holder.binding.imgReview.setImageResource(R.mipmap.placeholder_user);
//        }else{
//            holder.binding.imgReview.setImageResource(R.mipmap.avatar2);
//        }

        String[] a1 = listDate.get(position).split("\\s+");

        String d[] = a1[0].split("-");
        MonthPick_Class monthPickClass = new MonthPick_Class();
        holder.binding.txtUserReviewDate.setText(""+d[2]+"-"+monthPickClass.Months(d[1])+"-"+d[0]);


        Log.d("TAG","Photo = "+position+" "+listImage.get(position));
//        if (listImage.get(position).equals("")){
//            holder.binding.imgReview.setImageResource(R.mipmap.avatar2);
//        }else{
            holder.binding.imgReview.setImageResource(R.mipmap.placeholder_user);
//        }
        holder.binding.txtReviewUsername.setText(mListData.get(position));
        holder.binding.txtUserReview.setText(listDiscription.get(position));

        double a = doublesRating.get(position);
        if(a>=5){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar5.setImageResource(R.drawable.star_fill);

        }else if(a>=4 && a<5){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=3 && a<4){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=2 && a<3){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else if(a>=1 && a<2){
            holder.binding.imgStar1.setImageResource(R.drawable.star_fill);
            holder.binding.imgStar2.setImageResource(R.drawable.star_border);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }else{
            holder.binding.imgStar1.setImageResource(R.drawable.star_border);
            holder.binding.imgStar2.setImageResource(R.drawable.star_border);
            holder.binding.imgStar3.setImageResource(R.drawable.star_border);
            holder.binding.imgStar4.setImageResource(R.drawable.star_border);
            holder.binding.imgStar5.setImageResource(R.drawable.star_border);
        }

    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawProductreviewBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }

        @Override
        public void onClick(View view) {
        }
    }


}

