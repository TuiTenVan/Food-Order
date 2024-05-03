package com.example.foodorder.view.activity;

import android.os.Bundle;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.foodorder.R;
import com.example.foodorder.databinding.ActivityMainBinding;
import com.example.foodorder.view.BaseActivity;
import com.example.foodorder.viewmodel.MainViewModel;

public class MainActivity extends BaseActivity {

    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        mMainViewModel = new MainViewModel();
        activityMainBinding.setMainViewModel(mMainViewModel);
        setContentView(activityMainBinding.getRoot());
    }

    public void setToolBar(boolean isShow, String title) {
        mMainViewModel.isShowToolbar.set(isShow);
        if (isShow) {
            mMainViewModel.title.set(title);
        }
    }

    @Override
    public void onBackPressed() {
        showConfirmExitApp();
    }

    private void showConfirmExitApp() {
        new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive((dialog, which) -> finish())
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }
}