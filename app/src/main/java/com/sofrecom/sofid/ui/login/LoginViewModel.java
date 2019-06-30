package com.sofrecom.sofid.ui.login;

import android.content.Context;

import com.sofrecom.sofid.R;
import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.model.api.ApiError;
import com.sofrecom.sofid.data.model.others.ApiResponse;
import com.sofrecom.sofid.ui.base.BaseViewModel;
import com.sofrecom.sofid.utils.NetworkUtils;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

/**
 * Created by oibnchahdia on 12/04/2019
 */
public class LoginViewModel extends BaseViewModel {

    public LoginViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void login(Context mContext, String username, String password) {
        if (NetworkUtils.isNetworkConnected(mContext)) {
            loginByApi(username,password);
        }else {
            loginByLocalDB(mContext,username,password);
        }
    }

    private void loginByApi(String username, String password){
        getResponseLiveData().setValue(ApiResponse.loading());
        getCompositeDisposable().add(getDataManager()
                .loginByApi(username, password)
                .doOnSuccess(mUserResponse -> getDataManager().saveUser(username,password,mUserResponse))
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

    private void loginByLocalDB(Context mContext,String username, String password){
        getResponseLiveData().setValue(ApiResponse.loading());
        getCompositeDisposable().add(getDataManager()
                .loginByPasswordToken(username, password)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                            if (response!=null) {
                                getResponseLiveData().setValue(ApiResponse.success(response));
                            }else {
                                getResponseLiveData().setValue(ApiResponse.apiError(ApiError.createApiError(mContext.getString(R.string.incorrect_username_or_password))));
                            }
                        }, throwable -> getResponseLiveData().setValue(ApiResponse.apiError(ApiError.createApiError(mContext.getString(R.string.incorrect_username_or_password))))
                ));
    }
}
