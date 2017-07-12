package com.ulling.lib.core.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.util.QcLog;

public abstract class BaseLazyQFragment extends BaseQLifecycleFragment {
    public Context qCon;
    public String APP_NAME;
    protected abstract int getFragmentLayoutId();

    protected abstract void initData();

    protected void setupView(View view) {
    }

    protected void setup(View view) {
        setupView(view);
    }

    protected void fetchData() {
        QcLog.i("fetchData == ");
    }

    /**
     * Fragment가 Activity에 붙을때 호출 된다.
     * @param activity
     */
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        QcLog.i("onAttach");
    }

    /**
     * Activity에서의 onCreate()와 비슷하나, ui관련 작업은 할 수 없다.
     * @param savedInstanceState
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
    }

    /**
     * Layout을 inflater을하여 View작업을 하는곳이다.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QcLog.i("onCreateView == ");
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        setup(view);
        return view;
    }

    /**
     * 다시 돌아올때
     * onDestroyView에서
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i("onViewCreated == ");
        qCon = getActivity().getApplicationContext();
        initData();
//        fetchData();
    }

    /**
     * Activity에서 Fragment를 모두 생성하고 난다음 호출 된다. Activity의 onCreate()에서 setContentView()한 다음이라고 생각 하면 쉽게 이해 될것 같다. 여기서 부터는 ui변경작업이 가능하다.
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        QcLog.i("onActivityCreated == ");
    }

    @Override
    public void onStart() {
        super.onStart();
        QcLog.i("onStart == ");
    }

    @Override
    public void onResume() {
        super.onResume();
        QcLog.i("onResume == ");
    }

    @Override
    public void onPause() {
        super.onPause();
        QcLog.i("onPause == ");
    }

    @Override
    public void onStop() {
        super.onStop();
        QcLog.i("onStop == ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        QcLog.i("onDestroyView == ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        QcLog.i("onDestroy == ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        QcLog.i("onDetach == ");
    }
}
