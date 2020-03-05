package com.youngershopping.adapter.home.category.expandable;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.youngershopping.R;
import com.youngershopping.databinding.RawCategoryChildItemBinding;
import com.youngershopping.pojo.ChildCategory;
import com.youngershopping.ui.product.ProductListActivity;
import com.youngershopping.utils.Constants;

import java.util.HashMap;
import java.util.List;

public class SubCategoryExpandableListAdapter extends RecyclerView.Adapter<SubCategoryExpandableListAdapter.ViewHolder> {
    private final String TAG = SubCategoryExpandableListAdapter.class.getSimpleName();
    private HashMap<Integer, List<ChildCategory>> child;
    private Context activity;
    int groupPosition;
    String groupname;

    public SubCategoryExpandableListAdapter(Context activity, HashMap<Integer, List<ChildCategory>> child,
                                            int groupPosition, String groupname) {
        this.child = child;
        this.activity = activity;
        this.groupPosition = groupPosition;
        this.groupname = groupname;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private RawCategoryChildItemBinding binding;
        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.raw_category_child_item, parent, false);

        return new SubCategoryExpandableListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChildCategory child = (ChildCategory) getChild(groupPosition, position);
        holder.binding.txtTitle.setText(child.Title);
        holder.binding.imgCategory.setImageResource(child.icon);
        holder.binding.imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra(Constants.from, child.Title);
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
//        return child.size();
        return this.child.get(groupPosition).size();
    }

    public Object getChild(int groupPosition, int childPosititon) {

        // This will return the child
        return this.child.get(groupPosition).get(
                childPosititon);
    }
}
