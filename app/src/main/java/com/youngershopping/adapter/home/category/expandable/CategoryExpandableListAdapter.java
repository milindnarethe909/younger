package com.youngershopping.adapter.home.category.expandable;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import com.youngershopping.R;
import com.youngershopping.databinding.RawCategoryChildBinding;
import com.youngershopping.databinding.RawCategoryParentBinding;
import com.youngershopping.pojo.ChildCategory;
import com.youngershopping.pojo.ParentCategory;

import java.util.HashMap;
import java.util.List;

public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<ParentCategory> header; // header titles
    // Child data in format of header title, child title
    private HashMap<Integer, List<ChildCategory>> child;

    public CategoryExpandableListAdapter(Context context, List<ParentCategory> listDataHeader,
                                         HashMap<Integer, List<ChildCategory>> listChildData) {
        this._context = context;
        this.header = listDataHeader;
        this.child = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {

        // This will return the child
        return this.child.get(this.header.get(groupPosition)).get(
                childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        RawCategoryChildBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.raw_category_child, parent, false);

        String groupname = header.get(groupPosition).Title;
        Log.d("TAG","Header Title = "+groupname);
        SubCategoryExpandableListAdapter sbc = new SubCategoryExpandableListAdapter(_context,
                child, groupPosition, groupname);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(_context, 4));
        binding.recyclerView.setAdapter(sbc);

        return binding.getRoot();
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        // return children count
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {

        // Get header position
        return this.header.get(groupPosition);
    }

    @Override
    public int getGroupCount() {

        // Get header size
        return this.header.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        // Getting header title
        ParentCategory headerTitle = (ParentCategory) getGroup(groupPosition);

        LayoutInflater infalInflater = (LayoutInflater) this._context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        RawCategoryParentBinding binding = DataBindingUtil.inflate(infalInflater, R.layout.raw_category_parent, parent, false);

        binding.txtTitle.setText(headerTitle.Title);
        binding.txtSubTitle.setText(headerTitle.subTitle);
        binding.imgCategory.setImageResource(headerTitle.icon);

        // If group is expanded then change the text into bold and change the
        // icon

//        float start, target;
//        if (isExpanded) {
//            start = 0f;
//            target = 90f;
//        } else {
//            start = 90f;
//            target = 0f;
//        }
//        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(imgArrow, View.ROTATION, start, target);
//        objectAnimator.setDuration(300);
//        objectAnimator.start();

        if (isExpanded) {
            binding.view.setVisibility(View.GONE);
            binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_arrow_drop_up, 0);
        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon

            binding.view.setVisibility(View.VISIBLE);
            binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_arrow_drop_down, 0);
        }

        return binding.getRoot();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
