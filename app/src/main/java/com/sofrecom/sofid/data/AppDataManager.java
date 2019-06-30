package com.sofrecom.sofid.data;

import android.content.Context;

import com.sofrecom.sofid.data.local.db.DbHelper;
import com.sofrecom.sofid.data.local.prefs.DataPreferences;
import com.sofrecom.sofid.data.model.api.SecurityQuestion;
import com.sofrecom.sofid.data.model.api.UserResponse;
import com.sofrecom.sofid.data.model.db.User;
import com.sofrecom.sofid.data.remote.ApiHelper;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by oibnchahdia on 10/04/2019
 */

@Singleton
public class AppDataManager implements DataManager {

    private final ApiHelper mApiHelper;
    private final Context mContext;
    private final DbHelper mDbHelper;
    private final DataPreferences mDataPreferences;

    @Inject
    public AppDataManager(Context context, DbHelper dbHelper, ApiHelper apiHelper, DataPreferences dataPreferences) {
        mContext = context;
        mDbHelper = dbHelper;
        mApiHelper = apiHelper;
        mDataPreferences = dataPreferences;
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return mDbHelper.getAllUsers();
    }

    @Override
    public Long insertUser(User user) {
        return mDbHelper.insertUser(user);
    }

    @Override
    public Single<User> getUser(String username) {
        return mDbHelper.getUser(username);
    }

    @Override
    public Single<User> loginByPasswordToken(String username, String password) {
        String passwordToken = mDataPreferences.getPasswordToken(username,password);
        return mDbHelper.loginByPasswordToken(username, passwordToken);
    }

    @Override
    public Single<UserResponse> loginByApi(String username, String password) {
        return mApiHelper.loginByApi(username, password);
    }

    @Override
    public Single<UserResponse> resetPasswordApi(String currentPassword, String newPassword, String confirmPassword) {
        return mApiHelper.resetPasswordApi(currentPassword, newPassword, confirmPassword);
    }

    @Override
    public Single<UserResponse> sendSecurityQuestion(String question, String answer) {
        return mApiHelper.sendSecurityQuestion(question,answer);
    }

    @Override
    public Single<SecurityQuestion> getSecurityQuestion() {
        return mApiHelper.getSecurityQuestion();
    }

    @Override
    public void saveUser(String username, String password, UserResponse mUserResponse) {
        mDataPreferences.setAccessToken(mUserResponse.getAccessToken());
        mDataPreferences.setRefreshToken(mUserResponse.getRefreshToken());
        mDataPreferences.setUsername(username);

        User mUser = new User();
        String passwordToken = mDataPreferences.getPasswordToken(username,password);

        mUser.setAccessToken(mUserResponse.getAccessToken());
        mUser.setRefreshToken(mUserResponse.getRefreshToken());
        mUser.setUsername(username);
        mUser.setPasswordToken(passwordToken);

        mDbHelper.insertUser(mUser);
    }
}
