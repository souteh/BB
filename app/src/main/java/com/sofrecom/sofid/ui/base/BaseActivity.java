package com.sofrecom.sofid.ui.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sofrecom.sofid.R;
import com.sofrecom.sofid.utils.CommonUtils;
import com.sofrecom.sofid.utils.CustomAlertDialog;
import com.sofrecom.sofid.utils.NetworkUtils;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Optional;
import cn.pedant.SweetAlert.SweetAlertDialog;
import dagger.android.AndroidInjection;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by oibnchahdia on 11/04/2019
 */
public abstract class BaseActivity<T extends ViewDataBinding, V extends BaseViewModel> extends AppCompatActivity{

    @Nullable
    @BindView(R.id.left_btn_toolbar)
    ImageView leftButtonToolbar;

    @Nullable
    @BindView(R.id.right_btn_toolbar)
    ImageView rightButtonToolbar;

    @Nullable
    @BindView(R.id.title_toolbar)
    TextView titleToolbar;

    @Nullable
    @BindView(R.id.toolbar)
    LinearLayout toolbarLayout;

    private CustomAlertDialog mProgressDialog;
    private CustomAlertDialog mDialog;
    private T mViewDataBinding;
    private V mViewModel;

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    public abstract int getBindingVariable();

    /**
     * @return layout resource id
     */
    public abstract
    @LayoutRes
    int getLayoutId();

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    public abstract V getViewModel();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
    }

    public T getViewDataBinding() {
        return mViewDataBinding;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public boolean hasPermission(String permission) {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }

    public void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }

    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissionsSafely(String[] permissions, int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }

    public void showLoadingDialog() {
        hideLoading();
        mProgressDialog = CommonUtils.getLoadingDialog(this);
        mProgressDialog.show();
    }

    private void performDataBinding() {
        // Data binding
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        this.mViewModel = mViewModel == null ? getViewModel() : mViewModel;
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();

        // ButterKnife binding
        ButterKnife.bind(this);
    }

    // --------- STATUSBAR ---------

    protected void changeColorTextStatusBarToWhite(){
        setColorStatusBar(R.color.light_gray);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

    protected void setColorStatusBar(int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(this,color));
        }
    }

    // --------- DIALOG ---------

    protected void showErrorDialog(String message){
        hideErrorDialog();
        mDialog = new CustomAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        mDialog.setTitleText(message);
        mDialog.setCancelable(true);
        mDialog.show();
    }

    protected void hideErrorDialog(){
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.cancel();
        }
    }

    // --------- TOOLBAR ---------

    protected void hideToolbar(){
        if (toolbarLayout!=null)
            toolbarLayout.setVisibility(View.GONE);
    }

    protected void showToolbar(){
        if (toolbarLayout!=null)
            toolbarLayout.setVisibility(View.VISIBLE);
    }

    protected void setLeftButtonToolbarImg(int res){
        if (leftButtonToolbar!=null)
            leftButtonToolbar.setImageResource(res);
    }

    protected void setRightButtonToolbarImg(int res){
        if (rightButtonToolbar!=null)
            rightButtonToolbar.setImageResource(res);
    }

    protected void hideLeftButtonToolbar(){
        if (leftButtonToolbar!=null)
            leftButtonToolbar.setVisibility(View.GONE);
    }

    protected void showLeftButtonToolbar(){
        if (leftButtonToolbar!=null)
            leftButtonToolbar.setVisibility(View.VISIBLE);
    }

    protected void hideRightButtonToolbar(){
        if (rightButtonToolbar!=null)
            rightButtonToolbar.setVisibility(View.GONE);
    }

    protected void showRightButtonToolbar(){
        if (rightButtonToolbar!=null)
            rightButtonToolbar.setVisibility(View.VISIBLE);
    }

    protected void setTitleToolbar(String title){
        if (titleToolbar!=null)
            titleToolbar.setText(title);
    }

    @Nullable
    public ImageView getLeftButtonToolbar() {
        return leftButtonToolbar;
    }

    @Nullable
    public ImageView getRightButtonToolbar() {
        return rightButtonToolbar;
    }
}
