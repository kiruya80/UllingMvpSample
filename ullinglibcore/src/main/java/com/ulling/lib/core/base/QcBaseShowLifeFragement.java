package com.ulling.lib.core.base;

import android.os.Bundle;
import android.view.View;

import com.ulling.lib.core.util.QcLog;

import static com.ulling.lib.core.util.QcLog.i;

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
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        i("setUserVisibleHint == " + section_number + " , " + isVisibleToUser);
        /**
         * QcBaseLifeFragment 의 resume에서 애니메이션 플레그 막기
         */
        isResumeAnimation = false;

        if (isVisibleToUser) {
            // 현재 페이지
            showToUserIfPrepared();
        } else {
            // 감춰진다
            hasShowData = false;
            optAnimationPause();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i("onViewCreated == ");
        isViewPrepared = true;
        showToUserIfPrepared();
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

    private void showToUserIfPrepared() {
        if (getUserVisibleHint() && !hasShowData && isViewPrepared) {
            hasShowData = true;
            needShowToUser();
            optAnimationResume();
        }
    }

}
