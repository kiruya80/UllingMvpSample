package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.FragRetrofitLiveBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.view.adapter.RetrofitLiveAdapter;
import com.example.architecture.viewmodel.RetrofitLiveViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

/**
 * https://news.realm.io/kr/news/retrofit2-for-http-requests/
 *
 * http://devuryu.tistory.com/44
 *
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 *
 * http://kang6264.tistory.com/m/entry/Retrofit-기본-기능에-대해서-알아보자날씨를-조회하는-RestAPI
 *
 *
 *
 * https://github.com/square/retrofit
 *
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github
 *
 *
 * http://www.zoftino.com/android-livedata-examples
 *
 * http://www.zoftino.com/android-persistence-library-room
 */
public class RetrofitLiveFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragRetrofitLiveBinding viewBinding;
    private static final String UID_KEY = "uid";
    private RetrofitLiveViewModel viewModel;
    private int nThreads = 2;

    private RetrofitLiveAdapter adapter;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RetrofitLiveFragment newInstance(int sectionNumber) {
        RetrofitLiveFragment fragment = new RetrofitLiveFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_retrofit_live;
    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        QcLog.e("optGetArgument == ");
    }

    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(RetrofitLiveViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }

    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragRetrofitLiveBinding) getViewBinding();

        adapter = new RetrofitLiveAdapter(this);
        viewBinding.recyclerView.setAdapter(adapter);


    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
        viewBinding.btnGet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null)
                    viewModel.getAnswers(true);
            }
        });
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");

    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
        observerAllAnswer(viewModel.getAllAnswers());
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }


    private void observerAllAnswer(LiveData<List<Answer>> answers) {
        //observer LiveData
        answers.observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(@Nullable List<Answer> allanswers) {
                QcLog.e("allanswers observe == ");
                if (allanswers == null) {
                    return;
                }
                adapter.addAll(allanswers);
            }
        });
    }

}