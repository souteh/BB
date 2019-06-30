package com.sofrecom.sofid.di.module;

import android.app.Application;
import android.content.Context;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import com.commonsware.cwac.saferoom.SafeHelperFactory;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sofrecom.sofid.R;
import com.sofrecom.sofid.data.AppDataManager;
import com.sofrecom.sofid.data.DataManager;
import com.sofrecom.sofid.data.local.db.AppDatabase;
import com.sofrecom.sofid.data.local.db.AppDbHelper;
import com.sofrecom.sofid.data.local.db.DbHelper;
import com.sofrecom.sofid.data.local.prefs.DataPreferences;
import com.sofrecom.sofid.data.local.prefs.KeyStoreHelper;
import com.sofrecom.sofid.data.local.prefs.SharedPreferencesHelper;
import com.sofrecom.sofid.data.remote.ApiHelper;
import com.sofrecom.sofid.data.remote.ApiService;
import com.sofrecom.sofid.data.remote.AppApiHelper;
import com.sofrecom.sofid.utils.CommonUtils;
import com.sofrecom.sofid.utils.Constants;
import com.sofrecom.sofid.utils.rx.AppSchedulerProvider;
import com.sofrecom.sofid.utils.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by oibnchahdia on 10/04/2019
 */

@Module
public class AppModule {

    @Provides
    @Singleton
    AppDatabase provideAppDatabase(Context context, DataPreferences mDataPreferences) {

        String password = mDataPreferences.getString(DataPreferences.DB_PASSWORD);
        if (password == null || password.isEmpty()) {
            password = CommonUtils.getDBPassword();
            mDataPreferences.putString(DataPreferences.DB_PASSWORD,password);
        }

        Editable passwordEditable = new SpannableStringBuilder(password);
        SafeHelperFactory factory= SafeHelperFactory.fromUser(passwordEditable);

        return Room.databaseBuilder(context, AppDatabase.class, Constants.DB_NAME)
                .openHelperFactory(factory)
                .build();
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    SchedulerProvider provideSchedulerProvider() {
        return new AppSchedulerProvider();
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    Context provideContext(Application application) {
        return application;
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        return new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    ApiService provideApiService(Retrofit retrofit) {
        return retrofit.create(ApiService.class);
    }

    @Provides
    @Singleton
    AppApiHelper provideAppApiHelper(ApiService apiService) {
        return new AppApiHelper(apiService);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(logging);
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .build();
            return chain.proceed(request);
        })
                .connectTimeout(100, TimeUnit.SECONDS)
                .writeTimeout(100, TimeUnit.SECONDS)
                .readTimeout(300, TimeUnit.SECONDS);

        return httpClient.build();
    }

    @Provides
    @Singleton
    SharedPreferencesHelper provideSharedPreferencesHelper(Context context) {
        return new SharedPreferencesHelper(context);
    }

    @Provides
    @Singleton
    KeyStoreHelper provideKeyStoreHelper(Context context, SharedPreferencesHelper sharedPreferencesHelper) {
        return new KeyStoreHelper(context,sharedPreferencesHelper);
    }

    @Provides
    @Singleton
    DataPreferences provideAppSharedPreferences(KeyStoreHelper mKeyStoreHelper, SharedPreferencesHelper mSharedPreferencesHelper) {
        return new DataPreferences(mKeyStoreHelper,mSharedPreferencesHelper);
    }
}
