package com.sofrecom.sofid;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.ui.main.MainViewModel;
import com.sofrecom.sofid.ui.login.LoginViewModel;
import com.sofrecom.sofid.ui.resetPassword.ResetPasswordViewModel;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionActivity;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionSettingViewModel;
import com.sofrecom.sofid.ui.securityQuestion.SecurityQuestionViewModel;
import com.sofrecom.sofid.ui.settings.SettingsViewModel;
import com.sofrecom.sofid.ui.splash.SplashViewModel;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/**
 * Created by oibnchahdia on 10/04/2019
 */
@Singleton
public class ViewModelProviderFactory extends ViewModelProvider.NewInstanceFactory {

    private final DataManager dataManager;
    private final SchedulerProvider schedulerProvider;

    @Inject
    public ViewModelProviderFactory(DataManager dataManager,
                                    SchedulerProvider schedulerProvider) {
        this.dataManager = dataManager;
        this.schedulerProvider = schedulerProvider;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            //noinspection unchecked
            return (T) new LoginViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(SplashViewModel.class)) {
            //noinspection unchecked
            return (T) new SplashViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(ResetPasswordViewModel.class)) {
            //noinspection unchecked
            return (T) new ResetPasswordViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(MainViewModel.class)) {
            //noinspection unchecked
            return (T) new MainViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(SecurityQuestionViewModel.class)) {
            //noinspection unchecked
            return (T) new SecurityQuestionViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(SettingsViewModel.class)) {
            //noinspection unchecked
            return (T) new SettingsViewModel(dataManager,schedulerProvider);
        } else if (modelClass.isAssignableFrom(SecurityQuestionSettingViewModel.class)) {
            //noinspection unchecked
            return (T) new SecurityQuestionSettingViewModel(dataManager,schedulerProvider);
        }

        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
