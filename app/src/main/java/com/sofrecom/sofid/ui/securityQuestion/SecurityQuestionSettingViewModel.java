package com.sofrecom.sofid.ui.securityQuestion;

import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 13/05/2019
 */
public class SecurityQuestionSettingViewModel extends BaseViewModel {
    public SecurityQuestionSettingViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void sendSecurityQuestion(String question, String answer) {
        getResponseLiveData().setValue(ApiResponse.loading());
        getCompositeDisposable().add(getDataManager()
                .sendSecurityQuestion(question, answer)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            //if (response==null) {
                            getResponseLiveData().setValue(ApiResponse.success(response));
//                            }else {
//                                getResponseLiveData().setValue(ApiResponse.apiError(response.error));
//                            }
                        }, throwable -> getResponseLiveData().setValue(ApiResponse.internalError(throwable))
                ));
    }

    public void getSecurityQuestion() {
        getResponseLiveData().setValue(ApiResponse.loading());
        getCompositeDisposable().add(getDataManager()
                .getSecurityQuestion()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            //if (response==null) {
                            getResponseLiveData().setValue(ApiResponse.success(response));
//                            }else {
//                                getResponseLiveData().setValue(ApiResponse.apiError(response.error));
//                            }
                        }, throwable -> getResponseLiveData().setValue(ApiResponse.internalError(throwable))
                ));
    }
}
