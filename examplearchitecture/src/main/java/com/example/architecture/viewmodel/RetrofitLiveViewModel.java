package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.entities.room.User;
import com.example.architecture.model.DatabaseModel;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 * <p>
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
public class RetrofitLiveViewModel extends AndroidViewModel {
    private DatabaseModel mDatabaseModel;
    //    private Application application;
    private Context qCon;
//    private int nThreads = 2;
//    private Executor executor = Executors.newFixedThreadPool(nThreads);

    public RetrofitLiveViewModel(@NonNull Application application) {
        super(application);
//        this.application = application;
    }

    public void initViewModel(Context qCon, int nThreads, int dbTypeLocal, int remoteType, String baseUrl) {
        QcLog.e("initViewModel == ");
        this.qCon = qCon;
//        this.nThreads = nThreads;
//        this.executor = Executors.newFixedThreadPool(nThreads);
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication(), nThreads, dbTypeLocal, remoteType, baseUrl);
//        mDatabaseModel = new DatabaseModel(getApplication());
//        mDatabaseModel.initLocalDb(DB_TYPE_LOCAL_ROOM);
//        mDatabaseModel.initRemoteDb(DB_TYPE_REMOTE_RETROFIT);
    }

    /**
     * userDao
     */
//    public void addUserDao(final User u) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                long resultIndex = mDatabaseModel.insertUser(u);
//                QcLog.e("addUser resultIndex == " + resultIndex);
//                QcToast.getInstance().show("add user seccess !! index = " + resultIndex, false);
//            }
//        });
//    }
//
//    public void deleteUserDao(final String userId) {
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                // result : 0 없는 경우  , 1: 성공
//                int result = mDatabaseModel.deleteUser(userId);
//                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
//                if (result == 1) {
//                    QcToast.getInstance().show("delete user seccess !!", false);
//                } else {
//                    QcToast.getInstance().show("delete user fail !!", false);
//                }
//            }
//        });
//    }
//
//    public void deleteUserDaoAsyncTask(final String userId) {
//        new AsyncTask<Void, String, Integer>() {
//            @Override
//            protected Integer doInBackground(Void... params) {
//                return mDatabaseModel.deleteUser(userId);
//            }
//
//            @Override
//            protected void onPostExecute(Integer result) {
//                super.onPostExecute(result);
//                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
//                if (result == 1) {
//                    QcToast.getInstance().show("delete user seccess !!", false);
//                } else {
//                    QcToast.getInstance().show("delete user fail !!", false);
//                }
//            }
//        }.execute();
//
//    }
//    public LiveData<User> getUserInfo(int userId) {
//        return mDatabaseModel.getUserInfo(userId);
//    }
    public LiveData<List<User>> getAllUsers() {
        return mDatabaseModel.getAllUsers();
    }

    /**
     * answerDao
     */
    public void addAnswer(final Answer answer) {
//        mDatabaseModel.addAnswer(answer);
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                long resultIndex = mDatabaseModel.insertAnswer(answer);
//                QcLog.e("addAnswer resultIndex == " + resultIndex);
//                QcToast.getInstance().show("addAnswer seccess !! index = " + resultIndex, false);
//            }
//        });
    }

    public LiveData<List<Answer>> getAllAnswers() {
        return mDatabaseModel.getAllAnswer();
    }

    public void getAnswers(boolean isRemote) {
        if (mDatabaseModel != null)
        mDatabaseModel.getAnswers(isRemote);
    }

    public void deleteAnswer() {
        if (mDatabaseModel != null)
        mDatabaseModel.deleteAnswer();
    }












    public LiveData<AnswersResponse> getAnswersLiveData() {
        return mDatabaseModel.answers();
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
}