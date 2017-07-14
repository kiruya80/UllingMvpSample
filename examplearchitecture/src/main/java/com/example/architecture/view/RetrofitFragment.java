package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.databinding.FragRetrofitBinding;
import com.example.architecture.viewmodel.RetrofitViewModel;
import com.ulling.lib.core.base.BaseLazyQLifeFragement;
import com.ulling.lib.core.util.QcLog;

public class RetrofitFragment extends BaseLazyQLifeFragement implements View.OnClickListener {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private RetrofitViewModel viewModel;
    private int nThreads = 2;

    FragRetrofitBinding viewBinding;

    public RetrofitFragment() {
    }

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
    protected void needDestroyData() {

    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_retrofit;
    }

    @Override
    protected void needViewBinding() {
        viewBinding = (FragRetrofitBinding) getViewBinding();
    }


    @Override
    protected void needInitData() {
        QcLog.e("initData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
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
    public void needPageVisiableToUser() {
        QcLog.e("lazyFetchData == ");
    }


    @Override
    public void onClick(View v) {
//        Intent intent;
//        switch (v.getId()) {
//            case R.id.TwitterLoginBtn:
////                intent = new Intent(this, TwitterLoginActivity.class);
////                startActivity(intent);
//                break;
//        }
    }
}