package com.sofrecom.sofid.data.remote;

import com.sofrecom.sofid.data.model.api.SecurityQuestion;
import com.sofrecom.sofid.data.model.api.UserResponse;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

/**
 * Created by oibnchahdia on 10/04/2019
 */
@Singleton
public class AppApiHelper implements ApiHelper{

    private final ApiService mApiService;

    @Inject
    public AppApiHelper(ApiService apiService) {
        this.mApiService = apiService;
    }

    @Override
    public Single<UserResponse> loginByApi(String username, String password) {
        //return mApiService.login(username, password);
        return mApiService.login();
    }

    @Override
    public Single<UserResponse> resetPasswordApi(String currentPassword, String newPassword, String confirmPassword) {
        return mApiService.resetPassword();
    }

    @Override
    public Single<UserResponse> sendSecurityQuestion(String question, String answer) {
        return mApiService.sendSecurityQuestion();
    }

    @Override
    public Single<SecurityQuestion> getSecurityQuestion() {
        return mApiService.getSecurityQuestion();
    }
}
