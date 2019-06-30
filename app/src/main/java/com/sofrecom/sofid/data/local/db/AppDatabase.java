package com.sofrecom.sofid.data.local.db;

import com.sofrecom.sofid.data.local.db.dao.UserDao;
import com.sofrecom.sofid.data.model.db.User;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/**
 * Created by oibnchahdia on 10/04/2019
 */
@Database(entities = {User.class}, version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();

}
