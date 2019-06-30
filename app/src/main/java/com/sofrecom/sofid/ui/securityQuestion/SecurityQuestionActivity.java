package com.sofrecom.sofid.ui.securityQuestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.sofrecom.sofid.BR;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.databinding.ActivitySecurityQuestionBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.ui.main.MainActivity;
import com.sofrecom.sofid.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by oibnchahdia on 08/05/2019
 */
public class SecurityQuestionActivity extends BaseActivity<ActivitySecurityQuestionBinding,SecurityQuestionViewModel> {

    @BindView(R.id.question_edittext)
    TextInputEditText mQuestionEditText;

    @BindView(R.id.answer_edittext)
    TextInputEditText mAnswerEditText;

    @BindView(R.id.send_btn)
    Button mSendButton;

    @BindView(R.id.skip_btn)
    LinearLayout mSkipButton;

    @Inject
    ViewModelProviderFactory factory;
    SecurityQuestionViewModel mSecurityQuestionViewModel;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_security_question;
    }

    @Override
    public SecurityQuestionViewModel getViewModel() {
        mSecurityQuestionViewModel = ViewModelProviders.of(this, factory).get(SecurityQuestionViewModel.class);
        return mSecurityQuestionViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        changeColorTextStatusBarToWhite();

        mSecurityQuestionViewModel.getResponseLiveData().observe(this,this::consumeResponse);

        mSendButton.setOnClickListener(v -> {
            if (!NetworkUtils.isNetworkConnected(SecurityQuestionActivity.this)){
                showErrorDialog(getString(R.string.no_internet));
                return;
            }

            String question = mQuestionEditText.getText()!=null ? mQuestionEditText.getText().toString() : "";
            String answer   = mAnswerEditText.getText()!=null ? mAnswerEditText.getText().toString() : "";

            if (question.isEmpty() || answer.isEmpty()){
                showErrorDialog(getString(R.string.please_complete_all_fields));
                return;
            }

            mSecurityQuestionViewModel.sendSecurityQuestion(question,answer);
        });

        mSkipButton.setOnClickListener(v -> {
            Intent intent = new Intent(SecurityQuestionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void consumeResponse(ApiResponse apiResponse) {
        hideLoading();
        switch (apiResponse.status) {
            case LOADING:
                showLoadingDialog();
                break;

            case SUCCESS:
                Intent intent = new Intent(SecurityQuestionActivity.this, MainActivity.class);
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
