package com.sofrecom.sofid.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.Nullable;
import androidx.databinding.library.baseAdapters.BR;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.data.local.prefs.DataPreferences;
import com.sofrecom.sofid.data.model.api.UserResponse;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.databinding.ActivityLoginBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.ui.main.MainActivity;
import com.sofrecom.sofid.ui.resetPassword.ResetPasswordActivity;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by oibnchahdia on 12/04/2019
 */
public class LoginActivity extends BaseActivity<ActivityLoginBinding, LoginViewModel> {

    @Inject
    DataPreferences mDataPreferences;
    @Inject
    ViewModelProviderFactory factory;
    LoginViewModel mLoginViewModel;

    @BindView(R.id.connexion)
    Button mConnexionButton;

    @BindView(R.id.username_edittext)
    TextInputEditText mUsernameEditText;

    @BindView(R.id.password_edittext)
    TextInputEditText mPasswordEditText;

    @BindView(R.id.remember_me)
    CheckBox mRememberMeCheckbox;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public LoginViewModel getViewModel() {
        mLoginViewModel = ViewModelProviders.of(this, factory).get(LoginViewModel.class);
        return mLoginViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColorTextStatusBarToWhite();

        mLoginViewModel.getResponseLiveData().observe(this,this::consumeResponse);

        mConnexionButton.setOnClickListener(v -> {
            if (mUsernameEditText.getText()== null || mUsernameEditText.getText().toString().isEmpty() ||
                    mPasswordEditText.getText()== null || mPasswordEditText.getText().toString().isEmpty()){
                showErrorDialog(getString(R.string.username_and_password_required));
            }else {
                mLoginViewModel.login(LoginActivity.this,mUsernameEditText.getText().toString(),mPasswordEditText.getText().toString());
            }
        });

        Boolean isRememberMe = mDataPreferences.getRememberMe();
        String mUsername = mDataPreferences.getUsername();

        mRememberMeCheckbox.setChecked(isRememberMe);
        mRememberMeCheckbox.setOnCheckedChangeListener((buttonView, isChecked) -> mDataPreferences.setRemembreMe(isChecked));
        if (isRememberMe && mUsername!=null) {
            mUsernameEditText.setText(mUsername);
            mPasswordEditText.requestFocus();
        }
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
                if (mUserResponse!=null && mUserResponse.isHasDefaultPassword()!=null && mUserResponse.isHasDefaultPassword()) {
                    intent = new Intent(LoginActivity.this, ResetPasswordActivity.class);
                }else {
                    intent = new Intent(LoginActivity.this, MainActivity.class);
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
