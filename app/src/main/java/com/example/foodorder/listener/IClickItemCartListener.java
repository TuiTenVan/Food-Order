package com.example.foodorder.listener;

import android.content.Context;

import com.example.foodorder.model.Food;

public interface IClickItemCartListener {
    void clickDeteteFood(Context context, Food food, int position);
    void updateItemFood(Context context, Food food, int position);
}
