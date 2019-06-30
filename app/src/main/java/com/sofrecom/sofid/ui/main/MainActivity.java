package com.sofrecom.sofid.ui.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.sofrecom.sofid.BR;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.databinding.ActivityMainBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.ui.settings.SettingsActivity;

import javax.inject.Inject;

/**
 * Created by oibnchahdia on 07/05/2019
 */
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {
    @Inject
    ViewModelProviderFactory factory;
    MainViewModel mMainViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, factory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
    }

    private void setupToolbar() {
        setTitleToolbar(getString(R.string.home));
        setRightButtonToolbarImg(R.drawable.ic_settings);

        getRightButtonToolbar().setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        });
    }
}
