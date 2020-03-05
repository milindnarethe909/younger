package com.youngershopping.adapter.account;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawFeedbackRatingBinding;
import com.youngershopping.pojo.feedback_item_click;
import com.youngershopping.utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android on 23/4/2018.
 */

public class FeedbackRatingAdapter extends RecyclerView.Adapter<FeedbackRatingAdapter.MyViewHolder> {

    private static List<String> mListData;
    private static Activity con;
    public List<String> listTag = new ArrayList<String>();
    List<feedback_item_click> list;

    public FeedbackRatingAdapter(Activity con, List<String> mListData,List<feedback_item_click> list) {
        this.con = con;
        this.mListData = mListData;
        this.list = list;
        listTag.add("");
        listTag.add("");
        listTag.add("");
        listTag.add("");
        listTag.add("");
        listTag.add("");
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_feedback_rating,
                viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final String data = mListData.get(position);
        holder.binding.txtTitle.setText(data);
        final String tag = listTag.get(position);
        holder.binding.imgExcellent.setImageResource(tag.equalsIgnoreCase(Constants.excellent) ? R.drawable.smily_excellent_blue : R.drawable.smily_excellent);
        holder.binding.imgGood.setImageResource(tag.equalsIgnoreCase(Constants.good) ? R.drawable.smily_good_blue : R.drawable.smily_good);
        holder.binding.imgAverage.setImageResource(tag.equalsIgnoreCase(Constants.average) ? R.drawable.smily_average_blue : R.drawable.smily_average);
        holder.binding.imgBad.setImageResource(tag.equalsIgnoreCase(Constants.bad) ? R.drawable.smily_bad_blue : R.drawable.smily_bad);
        holder.binding.imgPoor.setImageResource(tag.equalsIgnoreCase(Constants.poor) ? R.drawable.smily_poor_blue : R.drawable.smily_poor);

        holder.binding.llExcellent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTag(position, Constants.excellent);
                list.get(position).setTag(Constants.excellent);
                list.get(position).setTitle(data);
            }
        });

        holder.binding.llGood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTag(position, Constants.good);
                list.get(position).setTag(Constants.good);
                list.get(position).setTitle(data);

            }
        });

        holder.binding.llAverage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTag(position, Constants.average);
                list.get(position).setTag(Constants.average);
                list.get(position).setTitle(data);

            }
        });

        holder.binding.llBad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTag(position, Constants.bad);
                list.get(position).setTag(Constants.bad);
                list.get(position).setTitle(data);

            }
        });

        holder.binding.llPoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTag(position, Constants.poor);
                list.get(position).setTag(Constants.poor);
                list.get(position).setTitle(data);
            }
        });
    }

    private void setTag(int pos, String s) {
        listTag.remove(pos);
        listTag.add(pos, s);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mListData == null ? 0 : mListData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RawFeedbackRatingBinding binding;

        public MyViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }
    }


}

