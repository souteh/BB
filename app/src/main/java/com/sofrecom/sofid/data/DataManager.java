package com.sofrecom.sofid.data;

import com.sofrecom.sofid.data.local.db.DbHelper;
import com.sofrecom.sofid.data.model.api.UserResponse;
import com.sofrecom.sofid.data.remote.ApiHelper;

/**
 * Created by oibnchahdia on 10/04/2019
 */
public interface DataManager extends DbHelper, ApiHelper {
    void saveUser(String username, String password, UserResponse mUserResponse);
}
