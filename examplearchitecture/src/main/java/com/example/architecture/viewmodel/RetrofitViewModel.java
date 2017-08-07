package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.model.DatabaseModel;
import com.example.architecture.network.RemoteDataListener;
import com.example.architecture.view.RetrofitFragment;
import com.ulling.lib.core.base.QcBaseAndroidViewModel;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;

/**
 * Created by P100651 on 2017-07-04.
 *
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 *
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
public class RetrofitViewModel extends QcBaseAndroidViewModel {
    private DatabaseModel mDatabaseModel;

    public RetrofitViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl) {
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication(), dbTypeLocal, remoteType, baseUrl);

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


    public void getAnswersFromRemoteResponse(int page) {
        if (mDatabaseModel != null)
            mDatabaseModel.getAnswersFromRemoteResponse(page, remoteDataListener);
    }
    public LiveData<AnswersResponse> getAnswersFromRemoteResponse() {
        return mDatabaseModel.getAnswersFromRemoteResponse();
    }



    /**
     * 리모트 데이터에서 가져온 결과 상태값 리스너
     *
     * 아답터 처리를 뷰모델에서 처리할지 고민중..
     */
    private RemoteDataListener<QcBaseItem> remoteDataListener = new RemoteDataListener<QcBaseItem>() {

        @Override
        public void onSuccess(int statusCode, boolean hasNextPage, QcBaseItem data) {
            RetrofitFragment mRetrofitFragment = (RetrofitFragment) qFrag;
            if (mRetrofitFragment != null)
                mRetrofitFragment.onSuccess(statusCode, hasNextPage, data);
        }

        @Override
        public void onError(int statusCode, String msg) {
            RetrofitFragment mRetrofitFragment = (RetrofitFragment) qFrag;
            if (mRetrofitFragment != null)
                mRetrofitFragment.onError(statusCode, msg);
        }

        @Override
        public void onFailure(Throwable t, String msg) {
            RetrofitFragment mRetrofitFragment = (RetrofitFragment) qFrag;
            if (mRetrofitFragment != null)
                mRetrofitFragment.onFailure(t, msg);
        }
    };

























    public void deleteUserDaoAsyncTask(final String userId) {
        new AsyncTask<Void, String, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return mDatabaseModel.deleteUser(userId);
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
                if (result == 1) {
                    QcToast.getInstance().show("delete user seccess !!", false);
                } else {
                    QcToast.getInstance().show("delete user fail !!", false);
                }
            }
        }.execute();

    }


}