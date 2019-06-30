package com.sofrecom.sofid.data.remote;

import com.sofrecom.sofid.data.model.api.SecurityQuestion;
import com.sofrecom.sofid.data.model.api.UserResponse;

import io.reactivex.Single;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public interface ApiHelper {
    Single<UserResponse> loginByApi(String username, String password);
    Single<UserResponse> resetPasswordApi(String currentPassword, String newPassword, String confirmPassword);
    Single<UserResponse> sendSecurityQuestion(String question, String answer);
    Single<SecurityQuestion> getSecurityQuestion();
}
