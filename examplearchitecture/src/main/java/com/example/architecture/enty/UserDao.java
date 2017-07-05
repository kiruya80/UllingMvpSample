package com.example.architecture.enty;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.Date;
import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface UserDao {
//    @Query("SELECT * FROM user")
//    List getAll();
//    @Insert
//    void insertAll(User... users);

    //    @Insert(onConflict = REPLACE)
//    void save(User user);
    @Query("SELECT * FROM user WHERE id = :userId")
    public LiveData<User> load(String userId);

//    public LiveData<List<User>> findLoansByNameAfter(String userName, Date after);


    @Query("DELETE FROM User")
    void deleteAll();

    @Insert(onConflict = IGNORE)
    void insertUser(User user);
}