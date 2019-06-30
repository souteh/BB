package com.sofrecom.sofid.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.sofrecom.sofid.BR;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.databinding.ActivitySettingsBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionSettingActivity;
import com.sofrecom.sofid.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by oibnchahdia on 13/05/2019
 */
public class SettingsActivity extends BaseActivity<ActivitySettingsBinding, SettingsViewModel>{

    @Inject
    ViewModelProviderFactory factory;
    SettingsViewModel mSettingsViewModel;

    @BindView(R.id.language_btn)
    LinearLayout languageBtn;

    @BindView(R.id.security_question_btn)
    LinearLayout securityQuestionBtn;

    @BindView(R.id.update_password_btn)
    LinearLayout updatePasswordBtn;

    @BindView(R.id.note_app_btn)
    LinearLayout noteAppBtn;

    @BindView(R.id.share_logs_btn)
    LinearLayout shareLogsBtn;

    @BindView(R.id.about_btn)
    LinearLayout aboutBtn;

    @BindView(R.id.logout_btn)
    LinearLayout logoutBtn;

    @BindView(R.id.load_config_btn)
    LinearLayout loadConfigBtn;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_settings;
    }

    @Override
    public SettingsViewModel getViewModel() {
        mSettingsViewModel = ViewModelProviders.of(this, factory).get(SettingsViewModel.class);
        return mSettingsViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();
        securityQuestionBtn.setOnClickListener(v -> {
            if (!NetworkUtils.isNetworkConnected(SettingsActivity.this)){
                showErrorDialog(getString(R.string.no_internet));
                return;
            }

            Intent intent = new Intent(SettingsActivity.this, SecurityQuestionSettingActivity.class);
            startActivity(intent);
        });
    }

    private void setupToolbar() {
        setTitleToolbar(getString(R.string.settings));
        setLeftButtonToolbarImg(R.drawable.ic_back);

        getLeftButtonToolbar().setOnClickListener(v -> onBackPressed());
    }
}
