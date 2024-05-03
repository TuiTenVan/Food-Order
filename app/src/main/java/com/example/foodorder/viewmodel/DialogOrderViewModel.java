package com.example.foodorder.viewmodel;

import android.content.Context;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableList;

import com.example.foodorder.ControllerApplication;
import com.example.foodorder.R;
import com.example.foodorder.constant.Constant;
import com.example.foodorder.constant.GlobalFuntion;
import com.example.foodorder.listener.ISendOrderSuccessListener;
import com.example.foodorder.model.Food;
import com.example.foodorder.model.Order;
import com.example.foodorder.utils.StringUtil;
import com.example.foodorder.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DialogOrderViewModel {

    private Context mContext;
    private BottomSheetDialog mBottomSheetDialog;
    private final ObservableList<Food> listFoodInCart;
    public String strTotalPrice;
    public ObservableField<String> strName = new ObservableField<>();
    public ObservableField<String> strAddress = new ObservableField<>();
    public ObservableField<String> strPhone = new ObservableField<>();
    private final int mAmount;
    private final ISendOrderSuccessListener iSendOrderSuccessListener;

    public DialogOrderViewModel(Context context, BottomSheetDialog mBottomSheetDialog,
                                ObservableList<Food> list, String total,
                                int amount, ISendOrderSuccessListener listener) {
        this.mContext = context;
        this.mBottomSheetDialog = mBottomSheetDialog;
        this.listFoodInCart = list;
        this.strTotalPrice = total;
        this.mAmount = amount;
        this.iSendOrderSuccessListener = listener;
    }

    public void release() {
        mContext = null;
        mBottomSheetDialog = null;
    }

    public String getStringListFoodsOrder() {
        if (listFoodInCart == null || listFoodInCart.isEmpty()) {
            return "";
        }
        String result = "";
        for (Food food : listFoodInCart) {
            if (StringUtil.isEmpty(result)) {
                result = "- " + food.getName() + " (" + food.getRealPrice() + Constant.CURRENCY + ") "
                        + "- " + mContext.getString(R.string.quantity) + " " + food.getCount();
            } else {
                result = result + "\n" + "- " + food.getName() + " (" + food.getRealPrice()
                        + Constant.CURRENCY + ") "
                        + "- " + mContext.getString(R.string.quantity) + " " + food.getCount();
            }
        }
        return result;
    }

    public void onClickCancel() {
        if (mBottomSheetDialog != null) {
            mBottomSheetDialog.dismiss();
        }
    }

    public void onClickSendOrder() {
        String name = strName.get();
        String phone = strPhone.get();
        String address = strAddress.get();

        if (StringUtil.isEmpty(name) || StringUtil.isEmpty(phone) || StringUtil.isEmpty(address)) {
            GlobalFuntion.showToastMessage(mContext, mContext.getString(R.string.message_enter_infor_order));
        } else {
            long id = System.currentTimeMillis();
            Order order = new Order(id, name, phone, address,
                    mAmount, getStringListFoodsOrder(), Constant.TYPE_PAYMENT_CASH);

            ControllerApplication.get(mContext).getBookingDatabaseReference()
                    .child(Utils.getDeviceId(mContext))
                    .child(String.valueOf(id))
                    .setValue(order, (error1, ref1) -> iSendOrderSuccessListener.sendOrderSuccess());
        }
    }
}
