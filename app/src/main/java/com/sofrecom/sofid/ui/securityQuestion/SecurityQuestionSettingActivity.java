package com.sofrecom.sofid.ui.securityQuestion;

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
import com.sofrecom.sofid.data.model.api.SecurityQuestion;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.databinding.ActivitySecurityQuestionSettingBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.utils.CustomAlertDialog;
import com.sofrecom.sofid.utils.NetworkUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by oibnchahdia on 13/05/2019
 */
public class SecurityQuestionSettingActivity extends BaseActivity<ActivitySecurityQuestionSettingBinding,SecurityQuestionSettingViewModel>  {

    @BindView(R.id.question_edittext)
    TextInputEditText mQuestionEditText;

    @BindView(R.id.answer_edittext)
    TextInputEditText mAnswerEditText;

    @BindView(R.id.send_btn)
    Button mSendButton;

    @BindView(R.id.edit_btn)
    Button mEditButton;

    @BindView(R.id.delete_btn)
    Button mDeleteButton;

    @BindView(R.id.edit_layout)
    LinearLayout editLayout;

    @Inject
    ViewModelProviderFactory factory;
    SecurityQuestionSettingViewModel mSecurityQuestionSettingViewModel;

    CustomAlertDialog mDialog;
    boolean editMode = false;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_security_question_setting;
    }

    @Override
    public SecurityQuestionSettingViewModel getViewModel() {
        mSecurityQuestionSettingViewModel = ViewModelProviders.of(this, factory).get(SecurityQuestionSettingViewModel.class);
        return mSecurityQuestionSettingViewModel;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setupToolbar();

        mSecurityQuestionSettingViewModel.getResponseLiveData().observe(this,this::consumeResponse);

        mSecurityQuestionSettingViewModel.getSecurityQuestion();

        mEditButton.setOnClickListener(v -> switchEditMode());
        mDeleteButton.setOnClickListener(v -> deleteQuestionAnswer());
        mSendButton.setOnClickListener(v -> {
            if (!NetworkUtils.isNetworkConnected(SecurityQuestionSettingActivity.this)){
                showErrorDialog(getString(R.string.no_internet));
                return;
            }

            String question = mQuestionEditText.getText()!=null ? mQuestionEditText.getText().toString() : "";
            String answer   = mAnswerEditText.getText()!=null ? mAnswerEditText.getText().toString() : "";

            if (question.isEmpty() || answer.isEmpty()){
                showErrorDialog(getString(R.string.please_complete_all_fields));
                return;
            }

            mSecurityQuestionSettingViewModel.sendSecurityQuestion(question,answer);
        });
    }

    void switchEditMode(){
        if (!editMode) {
            editMode = true;
            mQuestionEditText.setEnabled(true);
            mAnswerEditText.setEnabled(true);
            mSendButton.setVisibility(View.VISIBLE);
            editLayout.setVisibility(View.GONE);
            mEditButton.setVisibility(View.GONE);
            mDeleteButton.setVisibility(View.GONE);
        }else {
            editMode = false;
            mQuestionEditText.setEnabled(false);
            mAnswerEditText.setEnabled(false);
            mSendButton.setVisibility(View.GONE);
            editLayout.setVisibility(View.VISIBLE);
            mEditButton.setVisibility(View.VISIBLE);
            mDeleteButton.setVisibility(View.VISIBLE);
        }
    }

    void deleteQuestionAnswer(){
        mDialog = new CustomAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        mDialog.setTitleText(getString(R.string.attention));
        mDialog.setContentText(getString(R.string.delete_question_msg));
        mDialog.setCancelable(true);
        mDialog.setConfirmText(getString(R.string.delete));
        mDialog.setCancelText(getString(R.string.cancel));
        mDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog.dismissWithAnimation();
            }
        });
        mDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                // TODO call delete
                finish();
            }
        });
        mDialog.show();

        Button mConfirmButton = mDialog.findViewById(cn.pedant.SweetAlert.R.id.confirm_button);
        Button mCancelButton  = mDialog.findViewById(cn.pedant.SweetAlert.R.id.cancel_button);

        mConfirmButton.setBackgroundColor(getResources().getColor(R.color.red));
        mConfirmButton.setTextColor(getResources().getColor(R.color.white));
        mCancelButton.setBackgroundResource(R.drawable.black_border_button_background);
        mCancelButton.setTextColor(getResources().getColor(R.color.black));
    }

    private void setupToolbar() {
        setTitleToolbar(getString(R.string.security_question));
        setLeftButtonToolbarImg(R.drawable.ic_back);
        getLeftButtonToolbar().setOnClickListener(v -> onBackPressed());
    }

    void setupValues(SecurityQuestion securityQuestion){
        mQuestionEditText.setText(securityQuestion.getQuestion());
        mAnswerEditText.setText(securityQuestion.getAnswer());
    }

    private void consumeResponse(ApiResponse apiResponse) {
        hideLoading();
        switch (apiResponse.status) {
            case LOADING:
                showLoadingDialog();
                break;

            case SUCCESS:
                if (apiResponse.data instanceof SecurityQuestion) {
                    setupValues((SecurityQuestion)apiResponse.data);
                }else {
                    finish();
                }
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