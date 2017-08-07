package com.example.architecture.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.model.DatabaseModel;
import com.example.architecture.network.RemoteDataListener;
import com.example.architecture.view.DetailActivity;
import com.ulling.lib.core.base.QcBaseAndroidViewModel;
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
public class RetrofitLiveViewModel extends QcBaseAndroidViewModel {
    private DatabaseModel mDatabaseModel;

    public RetrofitLiveViewModel(@NonNull Application application) {
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

    /**
     * room 에서 가져오기
     *
     * @return
     */
    public LiveData<List<Answer>> getAllAnswersFromRoom() {
        return mDatabaseModel.getAllAnswerFromRoom();
    }

    /**
     * room에서 삭제
     */
    public void deleteAnswerFromRoom() {
        if (mDatabaseModel != null)
            mDatabaseModel.deleteAnswerFromRoom();
    }

    public void getAnswersFromRemote(final int page) {
        QcLog.e("getAnswersFromRemote == " + page);
        if (mDatabaseModel != null)
            mDatabaseModel.getAnswersFromRemote(page, new RemoteDataListener() {
            @Override
            public void onSuccess(int statusCode, boolean hasNextPage, Object data) {
                QcLog.e("success = " + statusCode);
                if (data != null) {
                    AnswersResponse answersResponse = (AnswersResponse) data;
                    if (answersResponse != null)
                        mDatabaseModel.setAnswersResponseToRoom(page, answersResponse);
                }
            }

            @Override
            public void onError(int statusCode, String msg) {
                QcLog.e("onError = " + msg.toString());
            }

            @Override
            public void onFailure(Throwable t, String msg) {
                QcLog.e("onFailure = " + t.toString());
            }
        });
    }

    /**
     * http://overcome26.tistory.com/71
     * http://www.androidauthority.com/using-shared-element-transitions-activities-fragments-631996/
     *
     * http://mikescamell.com/shared-element-transitions-part-4-recyclerview/
     *
     */
    public void moveFragment(View view, int answerId) {
//            public void onItemClick(View view, int position, Object object, String... transName) {
        Intent intent = new Intent(qActivity, DetailActivity.class);
//        intent.putExtra("item", (Serializable) item);
        intent.putExtra("answerId", answerId);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.putExtra("TransitionName_profile", view.getTransitionName());
            Pair<View, String> pair  = Pair.create(view, view.getTransitionName());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(qActivity, pair);
            qActivity.startActivity(intent, options.toBundle());
        }
        else {
            qActivity.startActivity(intent);
        }
    }

}