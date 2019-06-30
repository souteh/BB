package com.sofrecom.sofid.data.local.db.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Single;

import com.sofrecom.sofid.data.model.db.User;

/**
 * Created by oibnchahdia on 10/04/2019
 */

@Dao
public interface UserDao {

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE username LIKE :username LIMIT 1")
    Single<User> findByName(String username);

    @Query("SELECT * FROM users WHERE username LIKE :username and passwordToken LIKE :passwordToken LIMIT 1")
    Single<User> findByPasswordToken(String username, String passwordToken);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insert(User user);

    @Query("SELECT * FROM users")
    List<User> loadAll();

    @Query("SELECT * FROM users WHERE id IN (:userIds)")
    List<User> loadAllByIds(List<Integer> userIds);
}
