package com.youngershopping.view.tour;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.databinding.FragmentCommonBinding;
import com.nostra13.universalimageloader.core.ImageLoader;


public class CommonFragment extends BaseAppFragment implements DragLayout.GotoDetailListener {
    private String imageUrl;
    private FragmentCommonBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_common, container, false);
        ImageLoader.getInstance().displayImage(imageUrl, binding.image);
//        binding.dragLayout.setGotoDetailListener(this);
        return binding.getRoot();
    }

    @Override
    public void gotoDetail() {
        Activity activity = (Activity) getContext();
//        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
//                new Pair(binding.image, DetailActivity.IMAGE_TRANSITION_NAME),
//                new Pair(binding.address1, DetailActivity.ADDRESS1_TRANSITION_NAME),
//                new Pair(binding.address2, DetailActivity.ADDRESS2_TRANSITION_NAME),
//                new Pair(binding.address3, DetailActivity.ADDRESS3_TRANSITION_NAME),
//                new Pair(binding.address4, DetailActivity.ADDRESS4_TRANSITION_NAME),
//                new Pair(binding.address5, DetailActivity.ADDRESS5_TRANSITION_NAME),
//                new Pair(binding.rating, DetailActivity.RATINGBAR_TRANSITION_NAME),
//                new Pair(binding.head1, DetailActivity.HEAD1_TRANSITION_NAME),
//                new Pair(binding.head2, DetailActivity.HEAD2_TRANSITION_NAME),
//                new Pair(binding.head3, DetailActivity.HEAD3_TRANSITION_NAME),
//                new Pair(binding.head4, DetailActivity.HEAD4_TRANSITION_NAME)
//        );
        Intent intent = new Intent(activity, DetailActivity.class);
        intent.putExtra(DetailActivity.EXTRA_IMAGE_URL, imageUrl);
//        ActivityCompat.startActivity(activity, intent, options.toBundle());
        startActivity(intent);
    }

    public void bindData(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
