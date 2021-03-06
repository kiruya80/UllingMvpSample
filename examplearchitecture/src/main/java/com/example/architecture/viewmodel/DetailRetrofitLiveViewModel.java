package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.architecture.entities.room.Answer;
import com.example.architecture.model.DatabaseModel;
import com.ulling.lib.core.base.QcBaseAndroidViewModel;
import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 * <p>
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
public class DetailRetrofitLiveViewModel extends QcBaseAndroidViewModel {
    private DatabaseModel mDatabaseModel;

    public DetailRetrofitLiveViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl) {
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

    /**
     * room 에서 가져오기
     */
    public LiveData<Answer> getAnswerFromRoom(int answerId) {
        return mDatabaseModel.getAnswerFromRoom(answerId);
    }


}