package com.sofrecom.sofid.ui.resetPassword;

import androidx.lifecycle.MutableLiveData;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 02/05/2019
 */
public class ResetPasswordViewModel extends BaseViewModel {
    public ResetPasswordViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void resetPassword(String currentPassword, String newPassword, String confirmPassword) {
        getResponseLiveData().setValue(ApiResponse.loading());
        getCompositeDisposable().add(getDataManager()
                .resetPasswordApi(currentPassword, newPassword, confirmPassword)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            if (response.getError()==null) {
                                getResponseLiveData().setValue(ApiResponse.success(response));
                            }else {
                                getResponseLiveData().setValue(ApiResponse.apiError(response.getError()));
                            }
                        }, throwable -> getResponseLiveData().setValue(ApiResponse.internalError(throwable))
                ));
    }
}
