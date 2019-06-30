package com.sofrecom.sofid;

import android.app.Activity;
import android.app.Application;

import com.crashlytics.android.Crashlytics;
import com.sofrecom.sofid.di.component.DaggerAppComponent;
import com.sofrecom.sofid.utils.AppLogger;

import io.fabric.sdk.android.Fabric;
import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public class SofidApp extends Application implements HasActivityInjector {
    @Inject
    DispatchingAndroidInjector<Activity> activityDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return activityDispatchingAndroidInjector;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Fabric.with(this, new Crashlytics());

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        AppLogger.init(this);

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }
}
