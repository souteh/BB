package com.sofrecom.sofid.di.builder;

import com.sofrecom.sofid.ui.main.MainActivity;
import com.sofrecom.sofid.ui.login.LoginActivity;
import com.sofrecom.sofid.ui.resetPassword.ResetPasswordActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionSettingActivity;
import com.sofrecom.sofid.ui.settings.SettingsActivity;
import com.sofrecom.sofid.ui.splash.SplashActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by oibnchahdia on 10/04/2019
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector
    abstract ResetPasswordActivity bindResetPasswordActivity();

    @ContributesAndroidInjector
    abstract MainActivity bindHomeActivity();

    @ContributesAndroidInjector
    abstract SecurityQuestionActivity bindSecurityQuestionActivity();

    @ContributesAndroidInjector
    abstract SettingsActivity bindSettingsActivity();

    @ContributesAndroidInjector
    abstract SecurityQuestionSettingActivity bindSecurityQuestionSettingActivity();
}
