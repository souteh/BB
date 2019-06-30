package com.sofrecom.sofid.data.local.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by oibnchahdia on 18/04/2019
 */
@Singleton
public class SharedPreferencesHelper {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesHelper(Context context){
        sharedPreferences = context.getSharedPreferences(context.getApplicationContext().getApplicationInfo().packageName, Context.MODE_PRIVATE);
    }

    public String getStringPlainText(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putStringPlainText(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public String getString(String key) {
        return sharedPreferences.getString(key, "");
    }

    public void putString(String key, String value) {
        sharedPreferences.edit()
                .putString(key, value)
                .apply();
    }

    public boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public void putBoolean(String key, boolean value) {
        sharedPreferences.edit()
                .putBoolean(key, value)
                .apply();
    }
}