package com.ulling.lib.core.base;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.util.QcLog;

public abstract class BaseQLifeFragment extends LifecycleFragment {
    public Context qCon;
    public String APP_NAME;
//    public String TAG = getClass().getSimpleName();

    /**
     * 필수
     *
     * @return
     */
    protected abstract int getFragmentLayoutId();
    protected abstract void initSetupView(View view);

    /**
     * 데이터 초기화
     */
    protected abstract void initData();

    /**
     * 뷰모델 초기화
     */
    protected abstract void initViewModel();

    /**
     * 데이터 subscribe
     */
    protected abstract void subscribeUiFromViewModel();


    /**
     * 정리할 데이터
     */
    protected abstract void destroyData();
    /**
     * 옵션
     * animationResume
     * animationPause
     */

    protected void initArgument() {
    }
    /**
     * 애니메이션 시작
     */
    protected void animationResume() {
    };

    /**
     * 애니메이션 정지
     */
    protected void animationPause() {
    };

    /**
     * Lifecycle
     *
     * onAttach() > onCreateView() > onStart() > onStop() > onResume() > onPause() > onDestroyView() > onDetach()
     */
    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     * @param savedInstanceState
     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        QcLog.i("onCreate");
//    }

    /**
     * Layout을 inflater을하여 View작업을 하는곳이다.
     * 사용자 UI를 처음 그리는 시점에서 호출
     * View 를 반환
     * 프래그먼트가 UI를 제공하지 않는 경우 null을 반환
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QcLog.i("onCreateView == ");
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        initSetupView(view);
        return view;
    }

    /**
     * 다시 돌아올때
     * onDestroyView에서
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i("onViewCreated == ");
        qCon = getActivity().getApplicationContext();
        initData();
        initViewModel();
    }

    /**
     * Activity에서 Fragment를 모두 생성하고 난다음 호출 된다.
     * Activity의 onCreate()에서 setContentView()한 다음이라고 생각 하면 쉽게 이해 될것 같다.
     * 여기서 부터는 ui변경작업이 가능하다.
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QcLog.i("onActivityCreated == ");
        initArgument();
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
    }

    /**
     * Fragment를 종료한다는 첫 번째 신호
     * 저장되어야 할 것들을 저장 시켜야함
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
     * 사용자 UI를 제거하는 시점에서 호출
     * View 리소스를 해제 해주는 역할 구현
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        QcLog.i("onDestroyView == ");
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

    /**
     * Fragment가 Activity에 제거 될 때 호출
     */
    @Override
    public void onDetach() {
        super.onDetach();
        QcLog.i("onDetach == ");
    }
}
