package com.example.foodorder.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.constant.Constant;
import com.example.foodorder.database.FoodDatabase;
import com.example.foodorder.databinding.ItemCartBinding;
import com.example.foodorder.listener.ICalculatePriceListener;
import com.example.foodorder.listener.IClickItemCartListener;
import com.example.foodorder.model.Food;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> implements IClickItemCartListener {

    private final List<Food> mListFoods;
    private final ICalculatePriceListener iCalculatePriceListener;

    public CartAdapter(List<Food> mListFoods, ICalculatePriceListener listener) {
        this.mListFoods = mListFoods;
        this.iCalculatePriceListener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemCartBinding itemCartBinding = ItemCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(itemCartBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Food food = mListFoods.get(position);
        if (food == null) {
            return;
        }
        food.setAdapterPosition(holder.getAdapterPosition());
        food.setClickItemCartListener(this);
        holder.mItemCartBinding.setFoodModel(food);
    }

    @Override
    public int getItemCount() {
        return null == mListFoods ? 0 : mListFoods.size();
    }

    @Override
    public void clickDeteteFood(Context context, Food food, int position) {
        showConfirmDialogDeleteFood(context, food, position);
    }

    @Override
    public void updateItemFood(Context context, Food food, int position) {
        FoodDatabase.getInstance(context).foodDAO().updateFood(food);
        notifyItemChanged(position);
        calculateTotalPrice(context);
    }

    private void showConfirmDialogDeleteFood(Context context, Food food, int position) {
        new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.confirm_delete_food))
                .setMessage(context.getString(R.string.message_delete_food))
                .setPositiveButton(context.getString(R.string.delete), (dialog, which) -> {
                    FoodDatabase.getInstance(context).foodDAO().deleteFood(food);

                    mListFoods.remove(position);
                    notifyItemRemoved(position);
                    calculateTotalPrice(context);
                })
                .setNegativeButton(context.getString(R.string.dialog_cancel), (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void calculateTotalPrice(Context context) {
        List<Food> listFoodCart = FoodDatabase.getInstance(context).foodDAO().getListFoodCart();
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            String strZero = 0 + Constant.CURRENCY;
            iCalculatePriceListener.calculatePrice(strZero, 0);
            return;
        }

        int totalPrice = 0;
        for (Food food : listFoodCart) {
            totalPrice = totalPrice + food.getTotalPrice();
        }

        String totalString = totalPrice + Constant.CURRENCY;
        iCalculatePriceListener.calculatePrice(totalString, totalPrice);
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        private final ItemCartBinding mItemCartBinding;

        public CartViewHolder(ItemCartBinding itemCartBinding) {
            super(itemCartBinding.getRoot());
            this.mItemCartBinding = itemCartBinding;
        }
    }
}
