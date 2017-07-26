package com.example.architecture.entities.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 *
 */
@Dao
public interface AnswerDao {
    @Query("SELECT * FROM Answer")
    public LiveData<List<Answer>> getAllAnswer();

//    @Query("select * from user")
//    List<User> loadAllUsers();


    //    @Insert(onConflict = IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertAnswer(Answer answer);


    @Insert
    public long[] insertMultipleAnswer(Answer... answers);

    @Insert
    public long[] insertMultipleListAnswer(List<Answer> answers);

    @Delete
    public int deleteAnswer(Answer answer);


    @Query("DELETE FROM Answer")
    public int deleteAll();
}