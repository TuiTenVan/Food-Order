package com.example.foodorder.viewmodel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TextView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.R;
import com.example.foodorder.adapter.CartAdapter;
import com.example.foodorder.constant.Constant;
import com.example.foodorder.constant.GlobalFuntion;
import com.example.foodorder.database.FoodDatabase;
import com.example.foodorder.databinding.LayoutBottomSheetOrderBinding;
import com.example.foodorder.model.Food;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

public class CartViewModel {

    private Context mContext;
    public ObservableList<Food> listFoodInCart = new ObservableArrayList<>();
    public static String strTotalPrice;
    private static int mAmount;
    private static CartAdapter mCartAdapter;
    private DialogOrderViewModel mDialogOrderViewModel;

    public CartViewModel(Context mContext) {
        this.mContext = mContext;
        getListFoodInCart();
    }

    public void getListFoodInCart() {
        if (listFoodInCart != null) {
            listFoodInCart.clear();
        } else {
            listFoodInCart = new ObservableArrayList<>();
        }
        List<Food> list = FoodDatabase.getInstance(mContext).foodDAO().getListFoodCart();
        listFoodInCart.addAll(list);
    }

    @BindingAdapter({"list_cart", "calculate_price"})
    public static void loadListFoodInCart(RecyclerView recyclerView, ObservableList<Food> list, TextView textView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);


        mCartAdapter = new CartAdapter(list, (totalPrice, amount) -> {
            textView.setText(totalPrice);
            strTotalPrice = totalPrice;
            mAmount = amount;
        });
        strTotalPrice = getValueTotalPrice(recyclerView.getContext());
        textView.setText(strTotalPrice);
        recyclerView.setAdapter(mCartAdapter);
    }

    private static String getValueTotalPrice(Context context) {
        List<Food> listFoodCart = FoodDatabase.getInstance(context).foodDAO().getListFoodCart();
        if (listFoodCart == null || listFoodCart.isEmpty()) {
            mAmount = 0;
            return 0 + Constant.CURRENCY;
        }

        int totalPrice = 0;
        for (Food food : listFoodCart) {
            totalPrice = totalPrice + food.getTotalPrice();
        }

        mAmount = totalPrice;
        return totalPrice + Constant.CURRENCY;
    }

    public void onClickOrderCart() {
        if (mContext == null || listFoodInCart == null || listFoodInCart.isEmpty()) {
            return;
        }

        LayoutBottomSheetOrderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext),
                R.layout.layout_bottom_sheet_order, null, false);
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(binding.getRoot());
        bottomSheetDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        mDialogOrderViewModel = new DialogOrderViewModel(mContext, bottomSheetDialog, listFoodInCart,
                strTotalPrice, mAmount, () -> {
            GlobalFuntion.showToastMessage(mContext, mContext.getString(R.string.msg_order_success));
            GlobalFuntion.hideSoftKeyboard((Activity) mContext);
            bottomSheetDialog.dismiss();

            FoodDatabase.getInstance(mContext).foodDAO().deleteAllFood();
            clearCart();
        });
        binding.setDialogOrderViewModel(mDialogOrderViewModel);

        bottomSheetDialog.show();
    }

    @SuppressLint("NotifyDataSetChanged")
    private void clearCart() {
        if (listFoodInCart != null) {
            listFoodInCart.clear();
        }
        mCartAdapter.notifyDataSetChanged();
    }

    public void release() {
        mContext = null;
        if (mDialogOrderViewModel != null) {
            mDialogOrderViewModel.release();
        }
    }
}
