package com.sofrecom.sofid.di.component;

import android.app.Application;

import com.sofrecom.sofid.SofidApp;
import com.sofrecom.sofid.di.builder.ActivityBuilder;
import com.sofrecom.sofid.di.module.AppModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by oibnchahdia on 10/04/2019
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(SofidApp app);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
