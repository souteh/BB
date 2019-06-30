package com.sofrecom.sofid.data.local.prefs;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oibnchahdia on 18/04/2019
 */
@Singleton
public class DataPreferences {

    KeyStoreHelper mKeyStoreHelper;
    SharedPreferencesHelper mSharedPreferencesHelper;

    public static final String DB_PASSWORD           = "db_password";
    private static final String ACCESS_TOKEN_PREF    = "access_token_pref";
    private static final String REFRESH_TOKEN_PREF   = "refresh_token_pref";
    private static final String USERNAME_PREF        = "username_pref";
    private static final String REMEMBER_ME_PREF     = "remember_me_pref";
    private static final String TOKEN_SEPARATOR      = "[ ]";

    @Inject
    public DataPreferences(KeyStoreHelper mKeyStoreHelper, SharedPreferencesHelper mSharedPreferencesHelper) {
        this.mKeyStoreHelper = mKeyStoreHelper;
        this.mSharedPreferencesHelper = mSharedPreferencesHelper;
    }

    public String getString(String key) {
        String keyField = mKeyStoreHelper.encrypt(key);
        return mKeyStoreHelper.decrypt(mSharedPreferencesHelper.getString(keyField));
    }

    public void putString(String key, String value) {
        String keyField = mKeyStoreHelper.encrypt(key);
        String valueField = mKeyStoreHelper.encrypt(value);
        mSharedPreferencesHelper.putString(keyField,valueField);
    }

    public Boolean getBoolean(String key) {
        String keyField = mKeyStoreHelper.encrypt(key);
        return mSharedPreferencesHelper.getBoolean(keyField);
    }

    public void putBoolean(String key, Boolean value) {
        String keyField = mKeyStoreHelper.encrypt(key);
        mSharedPreferencesHelper.putBoolean(keyField,value);
    }

    public String encrypt(String plainText) {
        return mKeyStoreHelper.encrypt(plainText);
    }

    public String getPasswordToken(String username, String password){
        return encrypt(username+TOKEN_SEPARATOR+password);
    }

    public String decrypt(String encryptedText) {
        return mKeyStoreHelper.decrypt(encryptedText);
    }

    public void setUsername(String username){
        putString(USERNAME_PREF,username);
    }

    public String getUsername(){
        return getString(USERNAME_PREF);
    }

    public void setAccessToken(String accessToken){
        putString(ACCESS_TOKEN_PREF,accessToken);
    }

    public String getAccessToken(){
        return getString(ACCESS_TOKEN_PREF);
    }

    public void setRefreshToken(String refreshToken){
        putString(REFRESH_TOKEN_PREF,refreshToken);
    }

    public String getRefreshToken(){
        return getString(REFRESH_TOKEN_PREF);
    }

    public void setRemembreMe(Boolean value){
        putBoolean(REMEMBER_ME_PREF,value);
    }

    public Boolean getRememberMe(){
        return getBoolean(REMEMBER_ME_PREF);
    }
}
