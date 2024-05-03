package com.example.foodorder.viewmodel;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableBoolean;
import androidx.databinding.ObservableField;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodorder.R;
import com.example.foodorder.adapter.MainViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainViewModel {

    public ObservableBoolean isShowToolbar = new ObservableBoolean();
    public ObservableField<String> title = new ObservableField<>();

    @BindingAdapter({"item_selected"})
    public static void setOnNavigationItemSelectedListener(BottomNavigationView bottomNavigation, ViewPager2 viewPager2) {
        viewPager2.setUserInputEnabled(false);
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter((FragmentActivity) viewPager2.getContext());
        viewPager2.setAdapter(mainViewPagerAdapter);

        bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                viewPager2.setCurrentItem(0);
            } else if (id == R.id.nav_cart) {
                viewPager2.setCurrentItem(1);
            } else if (id == R.id.nav_feedback) {
                viewPager2.setCurrentItem(2);
            } else if (id == R.id.nav_contact) {
                viewPager2.setCurrentItem(3);
            } else if (id == R.id.nav_order) {
                viewPager2.setCurrentItem(4);
            }
            return true;
        });
    }
}
