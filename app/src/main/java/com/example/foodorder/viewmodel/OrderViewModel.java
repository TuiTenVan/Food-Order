package com.example.foodorder.viewmodel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodorder.ControllerApplication;
import com.example.foodorder.R;
import com.example.foodorder.adapter.OrderAdapter;
import com.example.foodorder.constant.GlobalFuntion;
import com.example.foodorder.model.Order;
import com.example.foodorder.utils.Utils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class OrderViewModel {

    private Context mContext;
    public ObservableList<Order> listOrder = new ObservableArrayList<>();

    public OrderViewModel(Context mContext) {
        this.mContext = mContext;
        getListOrders();
    }

    private void getListOrders() {
        if (mContext == null) {
            return;
        }
        ControllerApplication.get(mContext).getBookingDatabaseReference().child(Utils.getDeviceId(mContext))
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (listOrder != null) {
                            listOrder.clear();
                        } else {
                            listOrder = new ObservableArrayList<>();
                        }

                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Order order = dataSnapshot.getValue(Order.class);
                            listOrder.add(0, order);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        listOrder = null;
                    }
                });
    }

    @BindingAdapter({"list_data"})
    public static void loadListOrder(RecyclerView recyclerView, ObservableList<Order> list) {
        if (list == null) {
            GlobalFuntion.showToastMessage(recyclerView.getContext(),
                    recyclerView.getContext().getString(R.string.msg_get_date_error));
            return;
        }
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        OrderAdapter orderAdapter = new OrderAdapter(list);
        recyclerView.setAdapter(orderAdapter);
    }

    public void release() {
        mContext = null;
    }
}
