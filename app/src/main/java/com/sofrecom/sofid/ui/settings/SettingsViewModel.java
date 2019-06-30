package com.sofrecom.sofid.ui.settings;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 13/05/2019
 */
public class SettingsViewModel extends BaseViewModel {

    public SettingsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }
}
