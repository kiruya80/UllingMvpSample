package com.example.architecture.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.viewmodel.UserProfileViewModel;
import com.ulling.lib.core.base.BaseLazyQLifeFragement;
import com.ulling.lib.core.util.QcLog;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

public class RetrofitFragment extends BaseLazyQLifeFragement implements View.OnClickListener {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;
    private int nThreads = 2;

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
    protected int getFragmentLayoutId() {
        return R.layout.frag_retrofit;
    }

    @Override
    protected void initSetupView(View view) {
    }


    @Override
    protected void initData() {
        QcLog.e("initData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        initViewModel();
    }

    @Override
    public void initViewModel() {
        QcLog.e("initViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }

    @Override
    public void lazyFetchData() {
        QcLog.e("lazyFetchData == ");
    }

    @Override
    public void subscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
    }

    @Override
    protected void destroyData() {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.TwitterLoginBtn:
//                intent = new Intent(this, TwitterLoginActivity.class);
//                startActivity(intent);
                break;
        }
    }
}