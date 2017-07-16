package com.ulling.lib.core.base;

import android.arch.lifecycle.LifecycleFragment;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.util.QcLog;

/**
 * 기본 프레그먼트
 * 필수적인 요소들만 개발자가
 * 오버라이드하여 사용할수 있게 만든 베이스 프레그먼트
 */
public abstract class QcBaseLifeFragment extends LifecycleFragment {
    public Context qCon;
    public String APP_NAME;
    //    public String TAG = getClass().getSimpleName();

    /**
     * 리줌에서 애니메이션을 시작할지 결정하는 플래그
     * pageview에서는 false 설정해야함
     */
    public boolean isResumeAnimation = true;
    /**
     * 레이아웃 내 설정한 아이디들을 바인딩하는 클래스
     *
     * 프레그먼트와 맞는 뷰데이터바인딩 클래스로 형변환 해야함
     */
    private ViewDataBinding rootViewBinding;

    /**
     * 바인딩된 뷰데이터바인딩 가져오기
     */
    public ViewDataBinding getViewBinding() {
        return rootViewBinding;
    }

    /**
     * 필수
     * need~ 시작
     */

    /**
     * 1.
     *
     * 설정한 레이아웃 아이디를 가지고
     * onCreateView 에서 자동으로 바인딩된다
     * rootViewBinding = DataBindingUtil.inflate(inflater, needGetLayoutId(), container, false);
     *
     * @return 레이아웃 아이디 클래스이름을 기준으로 생성
     *
     * ex) LiveDataFragment -> R.layout.frag_user_profile;
     */
    protected abstract int needGetLayoutId();

    /**
     * 2.
     *
     * 프레그먼트 UI 데이터 초기화
     */
    protected abstract void needResetData();

    /**
     * 3.
     *
     * UI에서 필요한 데이터 바인딩
     * 옵져버 달기?
     */
    protected abstract void needUIInflate();

    /**
     * 4.
     *
     * 버튼 및 기타 UI이벤트 설정
     */
    protected abstract void needUIEventListener();

    /**
     * 5.
     *
     * 뷰모델 초기화
     */
    protected abstract void needInitViewModel();

    /**
     * 6.
     *
     * 데이터모델로부터 변화되는 데이터를 구독하고
     * 데이터를 UI에 연결한다
     */
    protected abstract void needSubscribeUiFromViewModel();

    /**
     * 옵션
     * opt
     *
     * animationResume
     * animationPause
     */
    /**
     * 옵션
     * 프레그먼트 Destroy되는 경우 데이터 리셋
     */
    protected void needDestroyData() {
    }

    /**
     * 데이터 전달시 가져오기
     * LiveData로 활용하능한지는 체크해봐야함 !!
     * 또한 데이터가 필요한지도 확인 필요
     */
    protected void optGetArgument() {
    }

    /**
     * 애니메이션 시작
     */
    protected void optAnimationResume() {
    }


    /**
     * 애니메이션 정지
     */
    protected void optAnimationPause() {
    }


    /**
     * Lifecycle
     *
     * onAttach() > onCreateView() > onStart() > onStop() > onResume() > onPause() > onDestroyView() > onDetach()
     */
    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
        if (getArguments() != null) {
            optGetArgument();
        }
    }


    /**
     * Layout을 inflater을하여 View작업을 하는곳이다.
     * 사용자 UI를 처음 그리는 시점에서 호출
     * View 를 반환
     * 프래그먼트가 UI를 제공하지 않는 경우 null을 반환
     *
     * onStop / onDestroyView 에서 돌아오는 경우 호출됨
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        QcLog.i("onCreateView == ");
        if (rootViewBinding == null)
        rootViewBinding = DataBindingUtil.inflate(
                inflater, needGetLayoutId(), container, false);
//        View view = viewBinding.getRoot();
//        needUIDataBinding();
        needUIInflate();
        return rootViewBinding.getRoot();
    }
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        QcLog.i("onCreateView == ");
//        View view = inflater.inflate(needGetLayoutId(), container, false);
//        needInitSetupView(view);
//        return view;
//    }

    /**
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i("onViewCreated == ");
        qCon = getActivity().getApplicationContext();
        needResetData();
        needInitViewModel();
        needUIEventListener();
        needSubscribeUiFromViewModel();
    }

    /**
     * Activity에서 Fragment를 모두 생성하고 난다음 호출 된다.
     * Activity의 onCreate()에서 setContentView()한 다음이라고 생각 하면 쉽게 이해 될것 같다.
     * 여기서 부터는 ui변경작업이 가능하다.
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QcLog.i("onActivityCreated == ");
//        optGetArgument();
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
        if (isResumeAnimation)
            optAnimationResume();
    }

    /**
     * Fragment를 종료한다는 첫 번째 신호
     * 저장되어야 할 것들을 저장 시켜야함
     */
    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
        optAnimationPause();
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
        needDestroyData();
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
