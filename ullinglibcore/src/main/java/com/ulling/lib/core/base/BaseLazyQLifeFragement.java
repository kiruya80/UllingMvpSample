package com.ulling.lib.core.base;

import android.os.Bundle;
import android.view.View;

import com.ulling.lib.core.util.QcLog;

/**
 * 뷰페이저용 프레그먼트
 * 페이져 이동시 현재 페이지인 경우만 데이터 및 뷰표시
 */
public abstract class BaseLazyQLifeFragement extends BaseQLifeFragment {
    public static final String ARG_SECTION_NUMBER = "section_number";
    public int section_number;
    public boolean isViewPrepared;
    public boolean hasFetchData;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasFetchData = false;
        isViewPrepared = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        QcLog.i("setUserVisibleHint == " + section_number + " , " + isVisibleToUser);
        if (isVisibleToUser) {
            // 현재 페이지
            lazyFetchDataIfPrepared();
        } else {
            // 감춰진다
            animationPause();
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    @Override
    protected void initArgument() {
        section_number = getArguments().getInt(ARG_SECTION_NUMBER);
    }

    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
            animationResume();
        }
    }

    protected abstract void lazyFetchData();
}
