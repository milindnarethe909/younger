package com.youngershopping.ui.home.ui1.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.youngershopping.BaseAppFragment;
import com.youngershopping.R;
import com.youngershopping.adapter.cart.CartProductAdapter;
import com.youngershopping.databinding.FragmentCartBinding;
import com.youngershopping.ui.home.ui1.HomeActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CartFragment . OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CartFragment# newInstance} factory method to
 * create an instance of this fragment.
 */
public class CartFragment extends BaseAppFragment implements View.OnClickListener {
    private FragmentCartBinding binding;
    private Activity activity;
    private CartProductAdapter cartProductAdapter;
    private List<Integer> listCartIcon;
    private List<String> listCartLable;

    public CartFragment() {
        // Required rating_empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_cart, container, false);
        init();
        setlistner();
        fillData();
        return binding.getRoot();
    }

    private void setlistner() {
        binding.rd1.setOnClickListener(this);
        binding.rd2.setOnClickListener(this);
        binding.rd3.setOnClickListener(this);
        binding.rd4.setOnClickListener(this);
        binding.rd5.setOnClickListener(this);
    }

    private void init() {
        activity = (HomeActivity) getActivity();
        binding.commanRecyclerviewCart.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.commanRecyclerviewCart.recyclerView.setNestedScrollingEnabled(false);
        binding.commanRecyclerviewCart.recyclerView.setFocusable(false);
    }

    private void fillData() {
        listCartIcon = new ArrayList<>();
        listCartIcon.add(R.drawable.oneplus1);
        listCartIcon.add(R.drawable.oneplus2);

        listCartLable = new ArrayList<>();
        listCartLable.add(getResources().getString(R.string.dummy_oneplus1));
        listCartLable.add(getResources().getString(R.string.dummy_oneplus2));

//        cartProductAdapter = new CartProductAdapter(activity, listCartIcon, listCartLable,);
        binding.commanRecyclerviewCart.recyclerView.setAdapter(cartProductAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rd1:
                binding.rd2.setChecked(false);
                break;
            case R.id.rd2:
                binding.rd1.setChecked(false);
                break;
            case R.id.rd3:
                binding.rd4.setChecked(false);
                binding.rd5.setChecked(false);
                break;
            case R.id.rd4:
                binding.rd3.setChecked(false);
                binding.rd5.setChecked(false);
                break;
            case R.id.rd5:
                binding.rd4.setChecked(false);
                binding.rd3.setChecked(false);
                break;
        }
    }
}
