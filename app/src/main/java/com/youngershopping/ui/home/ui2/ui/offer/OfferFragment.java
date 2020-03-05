package com.youngershopping.ui.home.ui2.ui.offer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.youngershopping.R;
import com.youngershopping.databinding.FragmentOffer2Binding;

public class OfferFragment extends Fragment {
    private FragmentOffer2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_offer2, container, false);
        return binding.getRoot();
    }
}