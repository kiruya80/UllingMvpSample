package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

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
import com.example.architecture.common.QcDefine;
import com.example.architecture.databinding.FragRetrofitLiveBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.view.adapter.RetrofitLiveAdapter;
import com.example.architecture.viewmodel.RetrofitLiveViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

import java.util.List;

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
public class RetrofitLiveFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {
    public static String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private QUllingApplication qApp;
    private FragRetrofitLiveBinding viewBinding;
    private RetrofitLiveViewModel viewModel;
    private RetrofitLiveAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;

    // Sets the starting page index
    private final int viewStartingPageIndex = 1;
    // The current offset index of data you have loaded
    private int viewCurrentPage = 1;
    private EndlessRecyclerScrollListener qcEndlessScroll;
    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

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
    protected void onDestroyToUser() {

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
            viewModel.needInitViewModel(getActivity(), this);
            viewModel.needDatabaseModel(DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        if (adapter == null) {
            adapter = new RetrofitLiveAdapter(this, qcRecyclerItemListener);
            adapter.setViewModel(viewModel);
        }
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        isLoading = false;
        page = viewStartingPageIndex;
        if (adapter != null)
            adapter.needResetData();
        if (viewBinding != null && qcEndlessScroll != null) {
            qcEndlessScroll.onStartingPageIndex(viewStartingPageIndex);
            qcEndlessScroll.onResetStatus();
        }
    }


    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragRetrofitLiveBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter, QcDefine.PAGE_SIZE, viewBinding.tvEmpty);
        qcEndlessScroll = viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener();
        qcEndlessScroll.onStartingPageIndex(viewStartingPageIndex);
        qcEndlessScroll.onResetStatus();
        /**
         * 라이브데이터 사용시 문제점
         *
         * 1. 페이지 로딩 실패시 재로딩문제
         *
         * 2. 로컬에 저장된 데이터를 가져오고 이후 다음페이지 로딩 문제
         * ->로컬 데이터에 마지막 페이지값을 같이 저장한다
         *
         */
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {
            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore =====");
                page = page_;
                QcToast.getInstance().show("onLoadMore !! " + page, false);
                adapter.addProgress();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (viewBinding != null && viewBinding.qcRecyclerView != null) {
                            viewModel.getAnswersFromRemote(page);
                            qcEndlessScroll.onNetworkLoading(true);
                        }
                    }
                }, 1000);

            }

            @Override
            public void onPositionTop() {
                QcLog.e("onPositionTop =====");
                QcToast.getInstance().show("onPositionTop !! ", false);
            }

            @Override
            public void onPositionBottom() {
                QcLog.e("onPositionBottom =====");
                QcToast.getInstance().show("onPositionBottom !! ", false);
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
                if (viewModel != null) {
                    viewModel.getAnswersFromRemote(page);
                    QcToast.getInstance().show("onLoadMore !! " + page, false);
                }
            }
        });
        viewBinding.btnDeleteRoom.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null) {
                    // 생성이 안되었다고 오류가 나오는 경우 체크
                    viewModel.deleteAnswerFromRoom();
                    needResetData();
                }
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
    protected void needOnShowToUser() {
        QcLog.e("needOnShowToUser == ");
        observerAllAnswer(viewModel.getAllAnswersFromRoom());
    }

    @Override
    protected void needOnHiddenToUser() {

    }

    private void observerAllAnswer(LiveData<List<Answer>> answers) {
        QcLog.e("observerAllAnswer == ");
        //observer LiveData
        answers.observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(@Nullable List<Answer> allanswers) {
                QcLog.e("allanswers observe == ");
                if (allanswers != null && allanswers.size() > 0) {
                    if (qcEndlessScroll != null && allanswers.get(allanswers.size() - 1).getHasMore() != null) {
                        boolean hasNextPage = allanswers.get(allanswers.size() - 1).getHasMore();
                        QcLog.e("Success page = " + page + " , hasNextPage =" + hasNextPage);
                        page = allanswers.get(allanswers.size() - 1).getLastPage();
                        qcEndlessScroll.onNextPage(hasNextPage);
                        qcEndlessScroll.onCurrentPage(page);
                        qcEndlessScroll.onNetworkLoading(false);

                        QcToast.getInstance().show("observe page = " + page + " , hasNextPage =" + hasNextPage, false);
                        Snackbar.make(viewBinding.qcRecyclerView, "Success page = " + page + " , hasNextPage =" + hasNextPage, Snackbar.LENGTH_LONG)
                                .setAction("Action", new OnSingleClickListener() {
                                    @Override
                                    public void onSingleClick(View v) {
                                        QcLog.e("Snackbar onSingleClick ");
                                    }
                                }).show();
                    }
                    adapter.removeLoadFail();
                    adapter.removeProgress();
                    adapter.addAll(allanswers);
                } else {
                    if (qcEndlessScroll != null) {
                        qcEndlessScroll.onNextPage(false);
                        qcEndlessScroll.onNetworkLoading(false);
                    }
                }
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
        viewBinding.swipeRefreshLayout.setRefreshing(true);
        viewModel.getAllAnswersFromRoom().removeObservers(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                QcLog.e("allanswers observe == ");
                observerAllAnswer(viewModel.getAllAnswersFromRoom());
                viewBinding.swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
            }
        }, 2000);
    }


    QcRecyclerBaseAdapter.QcRecyclerItemListener qcRecyclerItemListener = new QcRecyclerBaseAdapter.QcRecyclerItemListener() {
        @Override
        public void onItemClick(View view, int position, Object object) {
//            public void onItemClick(View view, int position, Object object, String... transName) {
        }

        @Override
        public void onItemLongClick(View view, int position, Object o) {

        }

        @Override
        public void onItemCheck(boolean checked, int position, Object o) {

        }

        @Override
        public void onDeleteItem(int itemPosition, Object o) {

        }

        @Override
        public void onReload() {

        }
    };


    private void setProgress(boolean isProgress) {
        if (isProgress) {
            viewBinding.progressBar.setVisibility(View.VISIBLE);
        } else {
            viewBinding.progressBar.setVisibility(View.GONE);
        }
    }


//    public static List<Employee> getEmployeeListSortedByName() {
//        final List<Employee> employeeList = getEmployeeList();
//
//        Collections.sort(employeeList, new Comparator<Employee>() {
//            @Override
//            public int compare(Employee a1, Employee a2) {
//                return a1.getName().compareTo(a2.getName());
//            }
//        });
//
//        return employeeList;
//    }
}