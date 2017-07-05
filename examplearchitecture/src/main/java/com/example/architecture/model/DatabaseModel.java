package com.example.architecture.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.architecture.enty.Book;
import com.example.architecture.enty.Loan;
import com.example.architecture.enty.User;
import com.example.architecture.localdb.RoomLocalData;
import com.example.architecture.remotedb.RetrofitRemoteData;
import com.ulling.lib.core.util.QcLog;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 뷰모델에서 데이터 요청시
 * 상태값 또는 로컬디비 체크후 없는 경우 온라인 데이터 요청
 * 가져온 데이터는 라이브데이터에 저장
 *
 * Created by P100651 on 2017-07-05.
 */
public class DatabaseModel {
    public static final int DB_TYPE_LOCAL_ROOM = 0;
    public static final int DB_TYPE_REMOTE_RETROFIT = 1;
    private RoomLocalData localData;
    private RetrofitRemoteData remoteData;

    private LiveData<User> user;
    private LiveData<List<User>> userList;

    private RoomLocalData mDb;

    private Application mApplication;

    public void onCleared() {
        QcLog.e("onCleared == ");
        mDb.close();
    }
    public DatabaseModel(Application application) {
        mApplication = application;
    }

    public void initLocalDb(int localDbTypeRoom) {
        if (localDbTypeRoom == DB_TYPE_LOCAL_ROOM) {
            initLocalRoom();
        }
    }
    public void initRemoteDb(int localDbTypeRoom) {
        if (localDbTypeRoom == DB_TYPE_REMOTE_RETROFIT) {
            initRemoteRetrofit();
        }
    }

    public RoomLocalData getlocalData() {
        return localData;
    }

    public void initLocalRoom( ) {
        mDb = RoomLocalData.getInMemoryDatabase(mApplication);
    }
    public void initRemoteRetrofit() {
        RetrofitRemoteData.getRetrofitClient();
    }

    public LiveData<User> getUserInfo(boolean local, String userId) {
        LiveData<User> user = null;
        if (local) {
            user = mDb.userModel().load(userId);
        } else {
//            MutableLiveData<User> data = RetrofitRemoteData.getUserInfo(userId);
        }
//        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
//        // 옵져버에게 전달하기전 데이터 가공
//        LiveData<String> userResult = Transformations.map(user,
//                new Function<User, String>() {
//                    @Override
//                    public String apply(User loansWithUserAndBook) {
//                        QcLog.e("apply == ");
//                        StringBuilder sb = new StringBuilder();
////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
////                                Locale.US);
////                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
////                            sb.append(String.format("%s\n  (Returned: %s)\n",
////                                    loan.bookTitle,
////                                    simpleDateFormat.format(loan.endTime)));
////                        }
//                        return sb.toString();
//                    }
//                });
        return user;
    }


}
