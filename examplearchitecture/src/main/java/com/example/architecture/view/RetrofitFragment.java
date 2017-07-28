package com.example.architecture.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.FragRetrofitBinding;
import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.view.adapter.RetrofitAdapter;
import com.example.architecture.viewmodel.RetrofitViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

/**
 * https://news.realm.io/kr/news/retrofit2-for-http-requests/
 * <p>
 * http://devuryu.tistory.com/44
 * <p>
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 * <p>
 * http://kang6264.tistory.com/m/entry/Retrofit-기본-기능에-대해서-알아보자날씨를-조회하는-RestAPI
 * <p>
 * <p>
 * <p>
 * https://github.com/square/retrofit
 * <p>
 * https://github.com/googlesamples/android-architecture-components/tree/master/GithubBrowserSample/app/src/main/java/com/android/example/github
 * <p>
 * <p>
 * http://www.zoftino.com/android-livedata-examples
 * <p>
 * http://www.zoftino.com/android-persistence-library-room
 */
public class RetrofitFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {

    private QUllingApplication qApp;
    private FragRetrofitBinding viewBinding;
    private RetrofitViewModel viewModel;
    private RetrofitAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;
    // Sets the starting page index
    private int viewStartingPageIndex = 1;
    // The current offset index of data you have loaded
    private int viewCurrentPage = 1;
    private EndlessRecyclerScrollListener.QcScrollDataListener qcScrollListener;

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
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(RetrofitViewModel.class);
            viewModel.initViewModel(qCon, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new RetrofitAdapter(this);
        if (adapter != null && !adapter.isViewModel())
            adapter.setViewModel(viewModel);
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        isLoading = false;
        page = viewStartingPageIndex;
        setResetScrollStatus();
       if (adapter != null)
            adapter.needResetData();
    }


    private void setResetScrollStatus() {
        if (viewBinding != null && viewBinding.qcRecyclerView != null)
            qcScrollListener.onResetStatus();
    }
    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragRetrofitBinding) getViewBinding();
//        viewBinding.recyclerView.setAdapter(adapter);
        viewBinding.qcRecyclerView.setEmptyView(viewBinding.tvEmpty);
        viewBinding.qcRecyclerView.setAdapter(adapter);
        qcScrollListener = viewBinding.qcRecyclerView.getQcScrollDataListener();
        qcScrollListener.onStartingPageIndex(viewStartingPageIndex);
        qcScrollListener.onCurrentPage(viewCurrentPage);
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {

            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore =====");
                page = page_;
                QcToast.getInstance().show("onLoadMore !! " + page, false);
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (viewModel != null) {
//                            viewModel.getAnswersFromRemoteResponse(page);
//                            qcScrollListener.onNetworkLoading(true);
//                        }
//                    }
//                }, 1000);

                if (viewModel != null) {
                    viewModel.getAnswersFromRemoteResponse(page);
                    qcScrollListener.onNetworkLoading(true);
                }
            }

            @Override
            public void onLoadEnd() {
                QcLog.e("onLoadEnd =====");
                QcToast.getInstance().show("onLoadEnd !! ", false);
            }
        });
        viewBinding.progressBar.setVisibility(View.GONE);
        viewBinding.swipeRefreshLayout.setOnRefreshListener(this);
        viewBinding.swipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);

    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
        viewBinding.btnGet.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null)
                    viewModel.getAnswersFromRemoteResponse(page);
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
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
        observerAnswersLiveData(viewModel.getAnswersFromRemoteResponse());
    }

    // https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
    private void observerAnswersLiveData(LiveData<AnswersResponse> answersLive) {
        //observer LiveData
        answersLive.observe(this, new Observer<AnswersResponse>() {
            @Override
            public void onChanged(@Nullable AnswersResponse answers) {
                if (answers == null) {
                    QcLog.e("answersLive observe answersLive == null ");
                    if (qcScrollListener != null) {
                        qcScrollListener.onNextPage(false);
                        qcScrollListener.onNetworkLoading(false);
                    }
                    return;
                }
                QcLog.e("Success page = " + page + " , hasNextPage ="+ answers.getHasMore());
                QcToast.getInstance().show("observe page = " + page + " , hasNextPage ="+  answers.getHasMore(), false);
                Snackbar.make(viewBinding.qcRecyclerView, "Success page = " + page + " , hasNextPage ="+ answers.getHasMore(), Snackbar.LENGTH_LONG)
                        .setAction("Action", new OnSingleClickListener() {
                            @Override
                            public void onSingleClick(View v) {
                                QcLog.e("Snackbar onSingleClick ");
                            }
                        }).show();
                if (qcScrollListener != null) {
                    qcScrollListener.onNextPage(answers.getHasMore());
                    qcScrollListener.onNetworkLoading(false);
                }

                adapter.add(answers.getItemResponses());
//                adapter.addAll(answers.getItemResponses());
            }
        });
    }

    @Override
    public void onRefresh() {
        if (isLoading) {
            QcToast.getInstance().show("isRefreshing !! " + isLoading, false);
            return;
        }
        needResetData();
        isLoading = true;
        viewModel.getAnswersFromRemoteResponse().removeObservers(this);
        viewBinding.swipeRefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                observerAnswersLiveData(viewModel.getAnswersFromRemoteResponse());
                viewBinding.swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
            }
        }, 2000);
    }

    private void setProgress(boolean isProgress) {
        if (isProgress) {
            viewBinding.progressBar.setVisibility(View.VISIBLE);
        } else {
            viewBinding.progressBar.setVisibility(View.GONE);
        }
    }
}