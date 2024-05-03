package com.example.foodorder.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.foodorder.R;
import com.example.foodorder.databinding.FragmentFeedbackBinding;
import com.example.foodorder.model.Feedback;
import com.example.foodorder.view.BaseFragment;
import com.example.foodorder.view.activity.MainActivity;

public class FeedbackFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        FragmentFeedbackBinding fragmentFeedbackBinding = FragmentFeedbackBinding.inflate(inflater, container, false);
        Feedback feedback = new Feedback();
        fragmentFeedbackBinding.setFeedbackModel(feedback);

        return fragmentFeedbackBinding.getRoot();
    }

    @Override
    protected void initToolbar() {
        MainActivity mainActivity = (MainActivity) getActivity();
        if (mainActivity != null)
            mainActivity.setToolBar(true, getString(R.string.feedback));
    }
}
