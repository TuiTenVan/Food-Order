package com.example.foodorder.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorder.R;
import com.example.foodorder.databinding.FragmentHomeBinding;
import com.example.foodorder.view.BaseFragment;
import com.example.foodorder.view.activity.MainActivity;
import com.example.foodorder.viewmodel.HomeViewModel;

public class HomeFragment extends BaseFragment {

    private HomeViewModel mHomeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentHomeBinding fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        mHomeViewModel = new HomeViewModel(getActivity());
        fragmentHomeBinding.setHomeViewModel(mHomeViewModel);

        return fragmentHomeBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(false, getString(R.string.home));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHomeViewModel != null) {
            mHomeViewModel.release();
        }
    }
}
