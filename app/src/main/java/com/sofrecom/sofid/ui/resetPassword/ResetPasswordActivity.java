package com.sofrecom.sofid.ui.resetPassword;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.data.model.api.UserResponse;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.databinding.ActivityResetPasswordBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.ui.main.MainActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionActivity;
import com.sofrecom.sofid.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by oibnchahdia on 02/05/2019
 */
public class ResetPasswordActivity extends BaseActivity<ActivityResetPasswordBinding, ResetPasswordViewModel> {

    @Inject
    ViewModelProviderFactory factory;
    ResetPasswordViewModel mResetPasswordViewModel;

    @BindView(R.id.current_pwd_edittext)
    TextInputEditText mCurrentPassword;

    @BindView(R.id.new_pwd_edittext)
    TextInputEditText mNewPassword;

    @BindView(R.id.confirm_pwd_edittext)
    TextInputEditText mConfirmPassword;

    @BindView(R.id.update_btn)
    Button mUpdateButton;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_reset_password;
    }

    @Override
    public ResetPasswordViewModel getViewModel() {
        mResetPasswordViewModel = ViewModelProviders.of(this, factory).get(ResetPasswordViewModel.class);
        return mResetPasswordViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColorTextStatusBarToWhite();

        mResetPasswordViewModel.getResponseLiveData().observe(this,this::consumeResponse);

        mUpdateButton.setOnClickListener(v -> {

            if (!NetworkUtils.isNetworkConnected(ResetPasswordActivity.this)){
                showErrorDialog(getString(R.string.no_internet));
                return;
            }

            String currentPassword = mCurrentPassword.getText()!=null ? mCurrentPassword.getText().toString() : "";
            String newPassword     = mNewPassword.getText()!=null ? mNewPassword.getText().toString() : "";
            String confirmPassword = mConfirmPassword.getText()!=null ? mConfirmPassword.getText().toString() : "";

            if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()){
                showErrorDialog(getString(R.string.please_complete_all_fields));
                return;
            }

            if (currentPassword.equals(newPassword)) {
                showErrorDialog(getString(R.string.new_password_must_be_different_from_the_old));
                return;
            }

            if (!confirmPassword.equals(newPassword)) {
                showErrorDialog(getString(R.string.new_password_is_different_from_the_confirmation));
                return;
            }

            mResetPasswordViewModel.resetPassword(currentPassword,newPassword,confirmPassword);
        });
    }

    private void consumeResponse(ApiResponse apiResponse) {
        hideLoading();
        switch (apiResponse.status) {
            case LOADING:
                showLoadingDialog();
                break;

            case SUCCESS:
                UserResponse mUserResponse = null;
                if (apiResponse.data instanceof UserResponse)
                    mUserResponse = (UserResponse)apiResponse.data;

                Intent intent;
                if (mUserResponse!=null && mUserResponse.isHasDefaultPassword()!=null && !mUserResponse.isHasSecurityQuestion()) {
                    intent = new Intent(ResetPasswordActivity.this, SecurityQuestionActivity.class);
                }else {
                    intent = new Intent(ResetPasswordActivity.this, MainActivity.class);
                }
                startActivity(intent);
                break;

            case INTERNAL_ERROR:
                showErrorDialog(getString(R.string.internal_error));
                break;

            case API_ERROR:
                showErrorDialog(apiResponse.apiError.getMessage()!=null ? apiResponse.apiError.getMessage() : getString(R.string.api_error_default));
                break;

            default:
                break;
        }
    }
}
