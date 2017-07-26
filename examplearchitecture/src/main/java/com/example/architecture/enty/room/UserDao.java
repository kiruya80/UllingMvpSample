package com.example.architecture.enty.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Dao
 * 주석으로 표시된 인터페이스이며 데이터베이스 액세스와 관련된 메소드 정의를 포함
 * 수행하는 데이터베이스 작업 유형에 따라 삽입, 삭제, 업데이트 및 쿼리와 같은 주석이있는 메서드
 *
 * - 실행 : 백그라운드 스레드에서
 * 1. 스레드 풀 실행자를 사용하여 실 운영
 *    Executor executor = Executors.newFixedThreadPool(2);
 * 2. AsyncTask를 사용하여 실 운영하기
 *
 @Insert(onConflict = OnConflictStrategy.REPLACE)
 public long insertPerson(Person person);
 @Update
 public void updatePerson(Person person);
 @Delete
 public void deletePerson(Person person);
 @Query("SELECT * FROM person")
 public LiveData<List<Person>> getAllPersons();
 @Query("SELECT * FROM person where mobile = :mobileIn")
 public LiveData<Person> getPersonByMobile(String mobileIn);
 @Query("SELECT * FROM person where city In (:cityIn)")
 public List<Person> getPersonByCities(List<String> cityIn);
 }


 */
@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    public LiveData<List<User>> getAllUsers();
    //    @Insert(onConflict = IGNORE)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public long insertUser(User user);

    @Delete
     int deleteUser(User user);
    @Query("delete from user where id = :userId")
     int deleteUser(String userId);





    @Query("select * from user")
    List<User> loadAllUsers();

    @Query("select * from user where id = :id")
//    User loadUserById(int id);
    LiveData<User> loadUserById(int id);

    @Query("select * from user where name = :firstName and lastName = :lastName")
    List<User> findByNameAndLastName(String firstName, String lastName);


    @Query("delete from user where name like :badName OR lastName like :badName")
    int deleteUsersByName(String badName);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertOrReplaceUsers(User... users);

    @Delete
    void deleteUsers(User user1, User user2);

    @Query("SELECT * FROM User WHERE :age == :age") // TODO: Fix this!
    List<User> findYoungerThan(int age);

    @Query("SELECT * FROM User WHERE age < :age")
    List<User> findYoungerThanSolution(int age);

    @Query("DELETE FROM User")
    void deleteAll();
}