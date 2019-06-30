package com.sofrecom.sofid.data.remote;

import com.sofrecom.sofid.data.model.api.SecurityQuestion;
import com.sofrecom.sofid.data.model.api.UserResponse;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public interface ApiService {
//    @FormUrlEncoded
//    @POST("auth.json")
//    Single<UserResponse> login(@Field("username") String username,
//                               @Field("password") String password);

//    @FormUrlEncoded
//    @POST("auth.json")
//    Single<UserResponse> resetPassword(@Field("currentPassword") String currentPassword,
//                                       @Field("newPassword") String newPassword,
//                                       @Field("confirmPassword") String confirmPassword);

//    @FormUrlEncoded
//    @POST("auth.json")
//    Single<JsonElement> sendSecurityQuestion(@Field("question") String question,
//                                             @Field("answer") String answer);

    @GET("auth.json")
    Single<UserResponse> login();

    @GET("auth.json")
    Single<UserResponse> resetPassword();

    @GET("auth.json")
    Single<UserResponse> sendSecurityQuestion();

    @GET("question.json")
    Single<SecurityQuestion> getSecurityQuestion();
}
