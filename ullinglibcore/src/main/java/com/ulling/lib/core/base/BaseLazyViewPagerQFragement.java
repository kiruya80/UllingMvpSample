package com.ulling.lib.core.base;

import android.os.Bundle;
import android.view.View;

import com.ulling.lib.core.util.QcLog;

/**
 * 뷰페이저용 프레그먼트
 * 페이져 이동시 현재 페이지인 경우만 데이터 및 뷰표시
 */
public abstract class BaseLazyViewPagerQFragement extends BaseLazyQFragment {
    private boolean isViewPrepared;
    private boolean hasFetchData;

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hasFetchData = false;
        isViewPrepared = false;
    }

    @Override
    protected void setup(View view) {
        super.setup(view);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        QcLog.i("setUserVisibleHint == " + isVisibleToUser);
        if (isVisibleToUser) {
            lazyFetchDataIfPrepared();
        }
    }

    protected void lazyFetchData() {
        QcLog.i("lazyFetchData == ");
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewPrepared = true;
        lazyFetchDataIfPrepared();
    }

    private void lazyFetchDataIfPrepared() {
        if (getUserVisibleHint() && !hasFetchData && isViewPrepared) {
            hasFetchData = true;
            lazyFetchData();
        }
    }
}
