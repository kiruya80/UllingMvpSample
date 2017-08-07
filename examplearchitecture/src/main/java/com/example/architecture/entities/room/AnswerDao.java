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
    @Query("SELECT * FROM Answer ORDER BY answerId ASC ")
    public LiveData<List<Answer>> getAllAnswer();

//    @Query("select * from user")
//    List<User> loadAllUsers();

    @Query("SELECT * FROM Answer where answerId = :answerId")
    LiveData<Answer> getAnswerById(int answerId);

    //    @Insert(onConflict = IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertAnswer(Answer answer);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertMultipleAnswer(Answer... answers);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long[] insertMultipleListAnswer(List<Answer> answers);

    @Query("DELETE FROM Answer")
    public int deleteAll();
//    @Delete
//    public int deleteAnswer(Answer answer);

    @Delete
    public int deleteAnswer(Answer answer);

//    @Query("delete from Answer where id = :answerId")
//    public int deleteAnswer(int answerId);

}