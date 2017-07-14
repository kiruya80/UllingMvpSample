package com.ulling.lib.core.base;

import android.arch.lifecycle.LifecycleActivity;
import android.content.Context;
import android.os.Bundle;

import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 */
public abstract class BaseQLifecycleActivity extends LifecycleActivity {
    public Context qCon;
    public String APP_NAME;

    /**
     * 뷰모델 초기화
     */
    protected abstract void initViewModel();

    /**
     * 데이터 subscribe
     */
    protected abstract void subscribeUiFromViewModel();

    /**
     * 데이터 초기화
     */
    protected abstract void onCreateInitData();

    /**
     * 애니메이션 시작
     */
    protected abstract void animationResume();

    /**
     * 애니메이션 정지
     */
    protected abstract void animationPause();

    /**
     * 정리할 데이터
     */
    protected abstract void destroyData();

    protected abstract int getFragmentLayoutId();

    protected abstract void onCreateSetUpView();
    protected abstract void onCreateGetArgument();

    /**
     * Lifecycle
     *
     * onCreate() > ( onRestart() )  > onStart() > onResume() > onPause() > onStop() > onDestroy()
     */
    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
        qCon = getApplication().getApplicationContext();
        setContentView(getFragmentLayoutId());
        onCreateGetArgument();
        onCreateSetUpView();
        onCreateInitData();
//        fetchData();
    }

    /**
     */
    @Override
    public void onRestart() {
        super.onRestart();
        QcLog.i("onRestart == ");
    }

    /**
     * 액티비티가 사용자에게 표시되기 직전에 호출
     * 액티비티가 전경으로 나오면 onResume()
     * 액티비티가 숨겨지면 onStop()
     */
    @Override
    public void onStart() {
        super.onStart();
        QcLog.i("onStart == ");
    }

    /**
     * 액티비티가 시작되고 사용자와 상호 작용하기 직전에 호출
     */
    @Override
    public void onResume() {
        super.onResume();
        QcLog.i("onResume == ");
        animationResume();
    }

    /**
     * 시스템이 다른 액티비티를 재개하기 직전에 호출
     * CPU를 소모하는 기타 작업들을 중단하는 등 여러 가지 용도에 사용
     * 이 메서드는 무슨 일을 하든 매우 빨리 끝내야함
     */
    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
        animationPause();
    }

    /**
     * 액티비티가 더 이상 사용자에게 표시되지 않게 되면 호출
     * 액티비티가 다시 사용자와 상호 작용하면 onRestart()
     */
    @Override
    public void onStop() {
        super.onStop();
        QcLog.i("onStop == ");
    }

    /**
     * 액티비티가 사라지면 onDestroy()
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.i("onDestroy == ");
        destroyData();
    }
}
