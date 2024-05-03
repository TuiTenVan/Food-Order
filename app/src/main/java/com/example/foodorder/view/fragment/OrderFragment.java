package com.example.foodorder.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorder.R;
import com.example.foodorder.databinding.FragmentOrderBinding;
import com.example.foodorder.view.BaseFragment;
import com.example.foodorder.view.activity.MainActivity;
import com.example.foodorder.viewmodel.OrderViewModel;

public class OrderFragment extends BaseFragment {

    private OrderViewModel mOrderViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentOrderBinding fragmentOrderBinding = FragmentOrderBinding.inflate(inflater, container, false);
        mOrderViewModel = new OrderViewModel(getActivity());
        fragmentOrderBinding.setOrderViewModel(mOrderViewModel);

        return fragmentOrderBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, getString(R.string.order));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mOrderViewModel != null) {
            mOrderViewModel.release();
        }
    }
}
