package com.ulling.lib.core.base;

import android.os.Bundle;
import android.view.View;

import com.ulling.lib.core.util.QcLog;

/**
 * 현재 보이는 프레그먼트
 * 보이는 경우
 * needPageVisiableToUser가 호출된다
 */
public abstract class QcBaseShowLifeFragement extends QcBaseLifeFragment {
    public static final String ARG_SECTION_NUMBER = "section_number";
    public int section_number = -1;
    private boolean isViewPrepared;
    private boolean hasShowData;

    /**
     * 사용자에게 보여지는 경우 호출된다
     */
    protected abstract void needShowToUser();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasShowData = false;
        isViewPrepared = false;
    }

    @Override
    public void onPause() {
        super.onPause();
        hasShowData = false;
        isViewPrepared = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        if (getArguments() != null && section_number < 0) {
//            section_number = getArguments().getInt(ARG_SECTION_NUMBER);
//            QcLog.e( "setUserVisibleHint getArguments()  != null = " + section_number);
//        }
        /**
         * QcBaseLifeFragment 의 resume에서 애니메이션 플레그 막기
         */
        isResumeAnimation = false;
        QcLog.i( "setUserVisibleHint == " + section_number + " , " + isVisibleToUser + " ," + isViewPrepared);
        if (isVisibleToUser && isViewPrepared) {
            hasShowData = true;
            needShowToUser();
        } else {
            hasShowData = false;
            optAnimationPause();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i( "onViewCreated == " + section_number + " ," + getUserVisibleHint());
        isViewPrepared = true;
        if (!hasShowData && getUserVisibleHint()) {
            hasShowData = true;
            needShowToUser();
        }
    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        if (getArguments() != null) {
            section_number = getArguments().getInt(ARG_SECTION_NUMBER);
        } else {
            section_number = -1;
        }
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        i("setUserVisibleHint == " + section_number + " , " + isVisibleToUser);
//        /**
//         * QcBaseLifeFragment 의 resume에서 애니메이션 플레그 막기
//         */
//        isResumeAnimation = false;
//
//        if (isVisibleToUser) {
//            // 현재 페이지
//            showToUserIfPrepared();
//        } else {
//            // 감춰진다
//            hasShowData = false;
//            optAnimationPause();
//        }
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        QcLog.i("onViewCreated == ");
//        isViewPrepared = true;
//        showToUserIfPrepared();
//    }
//
//
//
//    private void showToUserIfPrepared() {
//        if (getUserVisibleHint() && !hasShowData && isViewPrepared) {
//            hasShowData = true;
//            needShowToUser();
//            optAnimationResume();
//        }
//    }

}
