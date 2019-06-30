package com.sofrecom.sofid.data.local.db;

import com.sofrecom.sofid.data.model.db.User;
import java.util.List;
import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public interface DbHelper {

    Observable<List<User>> getAllUsers();

    Long insertUser(final User user);

    Single<User> getUser(final String username);

    Single<User> loginByPasswordToken(final String username, final String password);
}
