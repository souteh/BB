package com.sofrecom.sofid.ui.main;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 07/05/2019
 */
public class MainViewModel extends BaseViewModel {

    public MainViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
