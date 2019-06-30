package com.sofrecom.sofid.ui.splash;

import androidx.lifecycle.MutableLiveData;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.model.db.User;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.AppLogger;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 12/04/2019
 */
public class SplashViewModel extends BaseViewModel {

    public SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
