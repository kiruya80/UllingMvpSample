package com.ulling.lib.core.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.util.QcLog;

public abstract class BaseLazyQFragment extends BaseQLifecycleFragment {
    //    private String TAG = getClass().getSimpleName();
    protected abstract int getFragmentLayoutId();

    protected abstract int initData();

    protected void setupView(View view) {
    }

    protected void setup(View view) {
        setupView(view);
    }

    protected void fetchData() {
        QcLog.i("fetchData == ");
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        QcLog.i("onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.i("onCreate");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        QcLog.i("onCreateView == ");
        View view = inflater.inflate(getFragmentLayoutId(), container, false);
        setup(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        QcLog.i("onViewCreated == ");
        initData();
        fetchData();
    }

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
