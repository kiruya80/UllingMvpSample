package com.ulling.lib.core.base;

import android.arch.lifecycle.LifecycleFragment;

/**
 * Created by P100651 on 2017-07-04.
 */
public abstract class BaseQLifecycleFragment extends LifecycleFragment {
    /**
     * 데이터 초기화
     */
    public abstract void resetData();

    public abstract void startAnimation();
    public abstract void stopAnimation();

    /**
     * 뷰모델 초기화
     */
    public abstract void initViewModel();

    /**
     * 데이터 subscribe
     */
    public abstract void subscribeUiFromViewModel();
}
