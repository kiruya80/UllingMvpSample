package com.example.architecture.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.FragRetrofitBinding;
import com.example.architecture.enty.retrofit.Item;
import com.example.architecture.enty.retrofit.SOAnswersResponse;
import com.example.architecture.view.adapter.AnswersAdapter;
import com.example.architecture.viewmodel.RetrofitViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

import java.util.ArrayList;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

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
 *https://github.com/square/retrofit
 *
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github
 *
 *
 * http://www.zoftino.com/android-livedata-examples
 *
 * http://www.zoftino.com/android-persistence-library-room
 *
 *
 */
public class RetrofitFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragRetrofitBinding viewBinding;
    private static final String UID_KEY = "uid";
    private RetrofitViewModel viewModel;
    private int nThreads = 2;

    private AnswersAdapter mAdapter;
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
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        QcLog.e("optGetArgument == ");
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragRetrofitBinding) getViewBinding();

         mAdapter = new AnswersAdapter(qCon, new ArrayList<Item>(0), new AnswersAdapter.PostItemListener() {

            @Override
            public void onPostClick(long id) {
                QcLog.e("Post id is" + id);
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(qCon);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        EndlessRecyclerScrollListener endlessRecyclerScrollListener = new EndlessRecyclerScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            }
        };
        viewBinding.recyclerView.setLayoutManager(layoutManager);
        viewBinding.recyclerView.setAdapter(mAdapter);
        viewBinding.recyclerView.addOnScrollListener(endlessRecyclerScrollListener);

//        viewBinding.recyclerView.setHasFixedSize(true);
        // 항목 구분선
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(qCon, DividerItemDecoration.VERTICAL);
        viewBinding.recyclerView.addItemDecoration(itemDecoration);


    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
        viewBinding.btnGet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null)
                    viewModel.getAnswers();
            }
        });
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(RetrofitViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
        observerUserListResults(viewModel.getAnswersLiveData());
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }

    // https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
    private void observerUserListResults(LiveData<SOAnswersResponse> answersLive) {
        //observer LiveData
        answersLive.observe(this, new Observer<SOAnswersResponse>() {
            @Override
            public void onChanged(@Nullable SOAnswersResponse answers) {
                if (answers == null) {
                    QcLog.e("answersLive observe answersLive == null ");
                    return;
                }
                QcToast.getInstance().show("observe answersLive", false);
                Snackbar.make(viewBinding.recyclerView, "Success get data", Snackbar.LENGTH_LONG)
                        .setAction("Action", new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                QcLog.e("Snackbar onSingleClick ");
                            }
                        }).show();
                QcLog.e("answersLive observe == ");
//                String result = "";
//                for (Item item : answers.getItems()) {
//                    result = result + item.toString() + "\n\n";
//                }
//                QcLog.e("result == " + result);
                mAdapter.updateAnswers(answers.getItems());
            }
        });
    }

}