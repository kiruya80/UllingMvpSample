package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.architecture.enty.User;
import com.example.architecture.model.DatabaseModel;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by P100651 on 2017-07-04.
 *
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 */
public class LiveDataViewModel extends AndroidViewModel {
    private DatabaseModel mDatabaseModel;
//    private Application application;
    private Context qCon;
    private int nThreads = 2;
    private Executor executor = Executors.newFixedThreadPool(nThreads);

    public LiveDataViewModel(@NonNull Application application) {
        super(application);
//        this.application = application;
    }

    public void initViewModel(Context qCon, int nThreads, int dbTypeLocal, int remoteType) {
        QcLog.e("initViewModel == ");
        this.qCon = qCon;
        this.nThreads = nThreads;
        this.executor = Executors.newFixedThreadPool(nThreads);
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication(), dbTypeLocal, remoteType);
//        mDatabaseModel = new DatabaseModel(getApplication());
//        mDatabaseModel.initLocalDb(DB_TYPE_LOCAL_ROOM);
//        mDatabaseModel.initRemoteDb(DB_TYPE_REMOTE_RETROFIT);

    }


    public void addUserDao(final User u) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                long resultIndex = mDatabaseModel.insertUser(u);
                QcLog.e("addUser resultIndex == " + resultIndex);
                QcToast.getInstance().show( "add user seccess !! index = " + resultIndex, false);
            }
        });
    }

    public void deleteUserDao(final String userId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                // result : 0 없는 경우  , 1: 성공
                int result = mDatabaseModel.deleteUser(userId);
                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
                if (result == 1) {
                  QcToast.getInstance().show("delete user seccess !!", false);
                } else {
                   QcToast.getInstance().show("delete user fail !!", false);
                }
            }
        });
    }

    public void deleteUserDaoAsyncTask(final String userId) {
        new AsyncTask<Void, String, Integer>() {
            @Override
            protected Integer doInBackground( Void... params) {
                return mDatabaseModel.deleteUser(userId);
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
                if (result == 1) {
                 QcToast.getInstance().show( "delete user seccess !!", false);
                } else {
             QcToast.getInstance().show( "delete user fail !!", false);
                }
            }
        }.execute();

    }

    public LiveData<User> getUserInfo(int userId) {
        return mDatabaseModel.getUserInfo(userId);
    }

    public LiveData<List<User>> getAllUsers() {
        return mDatabaseModel.getAllUsers();
    }

    /**
     * 이 메서드는 ViewModel이 더이상 사용되지 않고 파괴될 때 호출됩니다.
     * Realm 인스턴스같이 ViewModel이 어떤 데이터를 관찰하고 ViewModel이 새는 것을 막기 위해
     * 구독을 취소할 필요가 있는 경우 유용합니다.
     */
    @Override
    protected void onCleared() {
        QcLog.e("onCleared == ");
        if (mDatabaseModel != null)
            mDatabaseModel.onCleared();
        super.onCleared();
    }


//    private MediatorLiveData<List<User>> userList = new MediatorLiveData<>();
//
//    public LiveData<List<User>> getUserLive(){
//        return userList;
//    }


//    private LiveData<User> user;
    //    private PersonDAO personDAO = DatabaseCreator.getPersonDatabase(context).PersonDatabase();

//    public LiveData<User> getUser() {
//        return this.user;
//    }
//
//
//    private UserDao userDAO;
//
//    private LiveData<String> mLoansResult;
//    public LiveData<String> getLoansResult() {
//        return mLoansResult;
//    }
//    public LiveData<String> getRemoteDBLoansResult() {
//        return mLoansResult;
//    }

    /**
     * DB 초기 생성
     * 데이터에 옵져버 연결
     */
//    public void createDb() {
////         userDAO = DatabaseCreator.getRoomLocalData(this.getApplication().getApplicationContext()).userModel();
////
////        DatabaseInitializer.populateAsync(mDatabaseModel.getlocalData());
////        subscribeToDbChanges();
//    }
////    public void getUserInfo(String userId) {
////        user = mDatabaseModel.getUserInfo(false, "");
//////        if (user != null) {
//////            // local
//////            userList = mDb.userDao().findLoansByNameAfter("Mike", getYesterdayDate());
//////        } else {
//////            // remote data
//////            user = userRepo.getUser(userId);
//////        }
////    }
//
//
////    private void subscribeToDbChanges() {
////        QcLog.e("subscribeToDbChanges == ");
////        LiveData<List<LoanWithUserAndBook>> loans = mDatabaseModel.loanModel().findLoansByNameAfter("Mike", getYesterdayDate());
////        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
////        mLoansResult = Transformations.map(loans,
////                new Function<List<LoanWithUserAndBook>, String>() {
////                    @Override
////                    public String apply(List<LoanWithUserAndBook> loansWithUserAndBook) {
////                        QcLog.e("apply == ");
////                        StringBuilder sb = new StringBuilder();
////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
////                                Locale.US);
////                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
////                            sb.append(String.format("%s\n  (Returned: %s)\n",
////                                    loan.bookTitle,
////                                    simpleDateFormat.format(loan.endTime)));
////                        }
////                        return sb.toString();
////                    }
////                });
////
////    }
//
//    private Date getYesterdayDate() {
//        QcLog.e("getYesterdayDate == ");
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.DATE, -1);
//        return calendar.getTime();
//    }
}