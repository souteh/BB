package com.sofrecom.sofid.data.local.db;

import com.sofrecom.sofid.data.model.db.User;

import java.util.List;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by oibnchahdia on 10/04/2019
 */

@Singleton
public class AppDbHelper implements DbHelper {

    private final AppDatabase mAppDatabase;

    @Inject
    public AppDbHelper(AppDatabase appDatabase) {
        this.mAppDatabase = appDatabase;
    }

    @Override
    public Observable<List<User>> getAllUsers() {
        return Observable.fromCallable(new Callable<List<User>>() {
            @Override
            public List<User> call() throws Exception {
                return mAppDatabase.userDao().loadAll();
            }
        });
    }

    @Override
    public Long insertUser(final User user) {
        return mAppDatabase.userDao().insert(user);
    }

    @Override
    public Single<User> getUser(String username) {
        return mAppDatabase.userDao().findByName(username);
    }

    @Override
    public Single<User> loginByPasswordToken(String username, String passwordToken) {
        return mAppDatabase.userDao().findByPasswordToken(username,passwordToken);
    }
}
