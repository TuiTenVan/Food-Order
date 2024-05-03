package com.example.foodorder.view.activity;

import android.os.Bundle;

import com.example.foodorder.constant.Constant;
import com.example.foodorder.databinding.ActivityFoodDetailBinding;
import com.example.foodorder.model.Food;
import com.example.foodorder.view.BaseActivity;
import com.example.foodorder.viewmodel.FoodDetailViewModel;

public class FoodDetailActivity extends BaseActivity {

    private FoodDetailViewModel mFoodDetailViewModel;
    private Food mFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityFoodDetailBinding activityFoodDetailBinding = ActivityFoodDetailBinding.inflate(getLayoutInflater());
        setContentView(activityFoodDetailBinding.getRoot());

        getDataIntent();
        mFoodDetailViewModel = new FoodDetailViewModel(this, mFood);
        activityFoodDetailBinding.setFoodDetailViewModel(mFoodDetailViewModel);
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mFood = (Food) bundle.get(Constant.KEY_INTENT_FOOD_OBJECT);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFoodDetailViewModel != null) {
            mFoodDetailViewModel.release();
        }
    }
}