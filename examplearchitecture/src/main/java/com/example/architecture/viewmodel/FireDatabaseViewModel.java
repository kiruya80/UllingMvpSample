package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.architecture.model.DatabaseModel;
import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 */
public class FireDatabaseViewModel extends AndroidViewModel {
    private Context qCon;
    private DatabaseModel mDatabaseModel;

    public FireDatabaseViewModel(@NonNull Application application) {
        super(application);
    }

    public void initViewModel(Context qCon, int dbTypeLocal, int remoteType, String baseUrl) {
        QcLog.e("initViewModel == ");
        this.qCon = qCon;
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication(), dbTypeLocal, remoteType, baseUrl);
//        mDatabaseModel = new DatabaseModel(getApplication());
//        mDatabaseModel.initLocalDb(DB_TYPE_LOCAL_ROOM);
//        mDatabaseModel.initRemoteDb(DB_TYPE_REMOTE_RETROFIT);
    }
}