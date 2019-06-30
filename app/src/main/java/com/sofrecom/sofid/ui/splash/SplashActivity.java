package com.sofrecom.sofid.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.sofrecom.sofid.BuildConfig;
import com.sofrecom.sofid.ViewModelProviderFactory;
import com.sofrecom.sofid.data.local.prefs.DataPreferences;
import com.sofrecom.sofid.databinding.ActivitySplashBinding;
import com.sofrecom.sofid.ui.base.BaseActivity;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.BR;
import com.sofrecom.sofid.ui.login.LoginActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionActivity;
import com.sofrecom.sofid.utils.AppLogger;
import com.somesh.permissionmadeeasy.enums.Permission;
import com.somesh.permissionmadeeasy.helper.PermissionHelper;
import com.somesh.permissionmadeeasy.intefaces.PermissionListener;

import java.util.ArrayList;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by oibnchahdia on 12/04/2019
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> implements PermissionListener {
    private static final String TAG = "SplashActivity";
    private static final int REQUEST_CODE_MULTIPLE = 1011;
    PermissionHelper permissionHelper;

    @Inject
    ViewModelProviderFactory factory;
    SplashViewModel mSplashViewModel;

    @Inject
    DataPreferences mDataPreferences;

    @BindView(R.id.copyright_label)
    TextView copyrightLabel;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public SplashViewModel getViewModel() {
        mSplashViewModel = ViewModelProviders.of(this, factory).get(SplashViewModel.class);
        return mSplashViewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        AppLogger.i(TAG,"Splash activity");

        StringBuilder copyrightText = new StringBuilder();
        copyrightText.append(getString(R.string.app_name));
        copyrightText.append(" v");
        copyrightText.append(BuildConfig.VERSION_NAME);
        copyrightText.append(" ");
        copyrightText.append(getString(R.string.copyright));
        copyrightLabel.setText(copyrightText);

        progressBar.getIndeterminateDrawable().setColorFilter(
                getResources().getColor(R.color.white),
                android.graphics.PorterDuff.Mode.SRC_IN);

        permissionHelper = PermissionHelper.Builder()
                .with(this)
                .requestCode(REQUEST_CODE_MULTIPLE)
                .setPermissionResultCallback(this)
                .askFor(Permission.STORAGE)
                .rationalMessage("Des autorisations sont nécessaires pour que l'application fonctionne correctement") //Optional
                .build();

        permissionHelper.requestPermissions();
    }

    @Override
    public void onPermissionsGranted(int requestCode, ArrayList<String> acceptedPermissionList) {
        AppLogger.v(TAG,"onPermissionsGranted");
        timerSplash();
    }

    @Override
    public void onPermissionsDenied(int requestCode, ArrayList<String> deniedPermissionList) {
        AppLogger.v(TAG,"onPermissionsDenied");
        permissionHelper.requestPermissions();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    void timerSplash(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }

    void showErrorDialog(){
        SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE).setTitleText("Des autorisations sont nécessaires pour que l'application fonctionne correctement");
        pDialog.setCancelable(true);
        pDialog.setConfirmButton("Autoriser", new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                permissionHelper.requestPermissions();
            }
        });
        pDialog.show();
    }
}
