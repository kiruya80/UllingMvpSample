package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.databinding.FragRetrofitBinding;
import com.example.architecture.viewmodel.RetrofitViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.util.QcLog;

public class RetrofitFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragRetrofitBinding viewBinding;
    private static final String UID_KEY = "uid";
    private RetrofitViewModel viewModel;
    private int nThreads = 2;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RetrofitFragment newInstance(int sectionNumber) {
        RetrofitFragment fragment = new RetrofitFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_retrofit;
    }

    @Override
    protected void optGetArgument() {
        super.optGetArgument();
        QcLog.e("optGetArgument == ");
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
    }

    @Override
    protected void needUIInflate() {
        QcLog.e("needUIInflate == ");
        viewBinding = (FragRetrofitBinding) getViewBinding();
    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(RetrofitViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }

}