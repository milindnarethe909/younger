package com.youngershopping.ui.home.ui2.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;

import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.databinding.FragmentHome2Binding;

public class HomeFragment2 extends BaseAppFragment {

    private FragmentHome2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home2, container, false);
        return binding.getRoot();
    }
}