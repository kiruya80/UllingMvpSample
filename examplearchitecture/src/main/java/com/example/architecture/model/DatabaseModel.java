package com.example.architecture.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.architecture.enty.User;
import com.example.architecture.enty.UserDao;
import com.example.architecture.localdb.DatabaseCreator;
import com.example.architecture.localdb.RoomLocalData;
import com.example.architecture.remotedb.RetrofitRemoteData;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

/**
 * 뷰모델에서 데이터 요청시
 * 상태값 또는 로컬디비 체크후 없는 경우 온라인 데이터 요청
 * 가져온 데이터는 라이브데이터에 저장
 *
 * Created by P100651 on 2017-07-05.
 */
public class DatabaseModel {
    /**
     * local db type
     */
    public static final int DB_TYPE_LOCAL_ROOM = 0;
    /**
     * remote type
     */
    public static final int REMOTE_TYPE_RETROFIT = 0;
    private final Context qCtx;
    private int localDbType = DB_TYPE_LOCAL_ROOM;
    private int remoteType = REMOTE_TYPE_RETROFIT;

    private RoomLocalData localData;
    private RetrofitRemoteData remoteData;

    //    private final PersonDAO personDAO;
    private UserDao userDao = null;


    public DatabaseModel(Context context, int localDbType, int remoteType) {
        this.qCtx = context;
        this.localDbType = localDbType;
        this.remoteType = remoteType;
        initLocalDb();
        initRemoteDb();
        if (localData != null)
            userDao = localData.userDatabase();
    }

    private void initLocalDb() {
        if (localDbType == DB_TYPE_LOCAL_ROOM) {
            localData = DatabaseCreator.getRoomLocalData(qCtx);
        }
    }

    private void initRemoteDb() {
        if (remoteType == REMOTE_TYPE_RETROFIT) {
            RetrofitRemoteData.getRetrofitClient();
        }
    }

    public void onCleared() {
        QcLog.e("onCleared == ");
        if (localData != null && localDbType == DB_TYPE_LOCAL_ROOM)
            localData.close();
    }

    public long insertUser(User u) {
        if (userDao != null) {
            long resultIndex = userDao.insertUser(u);
            QcLog.e("insertUser " + resultIndex);
            return resultIndex;
        } else {
            return -1;
        }
    }

    public int deleteUser(String userId) {
        if (userDao != null) {
            int rec = userDao.deleteUser(userId);
            QcLog.e("deleteUser = " + rec);
            return rec;
        } else {
            return -1;
        }
    }

    public LiveData<User> getUserInfo(int userId) {
        if (userDao != null) {
            return userDao.loadUserById(userId);
        } else {
            return null;
        }
    }

    public LiveData<List<User>> getAllUsers() {
        if (userDao != null) {
            return userDao.getAllUsers();
        } else {
            return null;
        }
    }

//    public void addPerson(Person p) {
//        long rec =  personDAO.insertPerson(p);
//        Log.d("db insert ","added "+rec);
//    }
//
//    public void updatePerson(Person p) {
//        personDAO.updatePerson(p);
//    }


//    private LiveData<User> user;
//    private LiveData<List<User>> userList;


//    private Application mApplication;
//
////    public DatabaseModel(Application application) {
////        mApplication = application;
////    }
//
//    public RoomLocalData getlocalData() {
//        return localData;
//    }
//
//    public void initLocalRoom( ) {
//        localData = RoomLocalData.getInMemoryDatabase(mApplication);
//    }
//    public void initRemoteRetrofit() {
//        RetrofitRemoteData.getRetrofitClient();
//    }
//
//    public LiveData<User> getUserInfo(boolean local, String userId) {
//        LiveData<User> user = null;
//        if (local) {
//            user = localData.userModel().load(userId);
//        } else {
////            MutableLiveData<User> data = RetrofitRemoteData.getUserInfo(userId);
//        }
////        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
////        // 옵져버에게 전달하기전 데이터 가공
////        LiveData<String> userResult = Transformations.map(user,
////                new Function<User, String>() {
////                    @Override
////                    public String apply(User loansWithUserAndBook) {
////                        QcLog.e("apply == ");
////                        StringBuilder sb = new StringBuilder();
//////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
//////                                Locale.US);
//////                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
//////                            sb.append(String.format("%s\n  (Returned: %s)\n",
//////                                    loan.bookTitle,
//////                                    simpleDateFormat.format(loan.endTime)));
//////                        }
////                        return sb.toString();
////                    }
////                });
//        return user;
//    }

}
