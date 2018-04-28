package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.common.QcDefine;
import com.example.architecture.databinding.FragRetrofitBinding;
import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.view.adapter.RetrofitAdapter;
import com.example.architecture.viewmodel.RetrofitViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

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

    public static String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private QUllingApplication qApp;
    private FragRetrofitBinding viewBinding;
    private RetrofitViewModel viewModel;
    private RetrofitAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;
    // Sets the starting page index
    private int viewStartingPageIndex = 1;
    private boolean loading = false;
    private boolean hasNextPage = true;
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
            viewModel = ViewModelProviders.of(this).get(RetrofitViewModel.class);
            viewModel.needInitViewModel(getActivity(), this);
            viewModel.needDatabaseModel(DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
//            viewModel.initViewModel(this, getActivity(), DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new RetrofitAdapter(this, qcRecyclerItemListener);
        if (adapter != null && !adapter.isViewModel())
            adapter.setViewModel(viewModel);
    }


    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        isLoading = false;
        page = viewStartingPageIndex;
        hasNextPage = true;
        loading = false;
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
        viewBinding = (FragRetrofitBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter, QcDefine.PAGE_SIZE, viewBinding.tvEmpty);

        qcEndlessScroll = viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener();
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {

            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore ===== " + hasNextPage + " ,  loading = " + loading);
                if (hasNextPage && !loading) {
                    QcToast.getInstance().show("onLoadMore !! " + page, false);
                    page = page_;
                    loading = true;
                    adapter.addProgress();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (viewModel != null) {
                                viewModel.getAnswersFromRemoteResponse(page);
                            }
                        }
                    }, 1000);
                }

            }

            @Override
            public void onPositionTop() {
                QcLog.e("onPositionTop =====");
                QcToast.getInstance().show("onPositionTop !! ", false);
            }

            @Override
            public void onPositionBottom() {
                if (!loading) {
                    QcLog.e("onPositionBottom =====");
                    QcToast.getInstance().show("onPositionBottom !! ", false);
                }
            }
        });
        viewBinding.progressBar.setVisibility(View.GONE);

        if (viewBinding.qcRecyclerView != null
                && viewBinding.qcRecyclerView.isReverseLayout()) {
            viewBinding.swipeRefreshLayout.setEnabled(false);
            viewBinding.swipeRefreshLayout.setRefreshing(false);

        } else {
            viewBinding.swipeRefreshLayout.setOnRefreshListener(this);
            viewBinding.swipeRefreshLayout.setColorSchemeResources(
                    R.color.colorAccent,
                    R.color.colorPrimary,
                    R.color.colorPrimaryDark);
        }

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

    public void onSuccess(int statusCode, boolean hasNextPage, QcBaseItem data) {
//            // 성공한 경우 처리
//            if (qcScrollListener != null) {
//                qcScrollListener.onNextPage(hasNextPage);
//                qcScrollListener.onNetworkLoading(false);
//                qcScrollListener.onNetworkError(false);
//            }
    }

    public void onError(int statusCode, final String msg) {
        /**
         * 에러인 경우는 리스트 및 뷰에 표현하고
         * 다시시도 버튼등으로 표현한다
         *
         * 아답터에 푸터등에 추가하는방식
         */
        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadFail();
                        adapter.removeProgress();
                        adapter.addLoadFail();
                        QcToast.getInstance().show("RemoteDataListener onError !!!! " + msg, false);
                    }
                });
            }
        }).start();

        setScrollLoadFail();
    }

    public void onFailure(Throwable t, final String msg) {
//        QcBaseItem failQcBaseItem = new QcBaseItem();
//        failQcBaseItem.setType(QcRecyclerBaseAdapter.TYPE_LOAD_FAIL);
//        adapter.add((ItemResponse)failQcBaseItem);

        new Thread(new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.removeLoadFail();
                        adapter.removeProgress();
                        adapter.addLoadFail();
                        QcToast.getInstance().show("RemoteDataListener onFailure !!!! " + msg, false);
                    }
                });
            }
        }).start();
        /**
         * 에러인 경우는 리스트 및 뷰에 표현하고
         * 다시시도 버튼등으로 표현한다
         *
         * 아답터에 푸터등에 추가하는방식
         */

        setScrollLoadFail();
    }

    private void setScrollLoadFail() {
        page = page--;
        hasNextPage = true;
        loading = false;
        if (qcEndlessScroll != null) {
            qcEndlessScroll.onCurrentPage(page);
//            qcEndlessScroll.onNextPage(true);
//            // 에러인경우 네트워킹상태는 true로해서 계속 데이터를 불러오지 않기 위해서
//            qcEndlessScroll.onNetworkLoading(false);
//            qcEndlessScroll.onNetworkError(true);
        }
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
        observerAnswersLiveData(viewModel.getAnswersFromRemoteResponse());
    }

    @Override
    protected void needOnHiddenToUser() {

    }
    // https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792

    /**
     * 네트워크에서 가져온 데이터 셋팅
     *
     * @param answersLive
     */
    private void observerAnswersLiveData(LiveData<AnswersResponse> answersLive) {
        //observer LiveData
        answersLive.observe(this, new Observer<AnswersResponse>() {
            @Override
            public void onChanged(@Nullable AnswersResponse answers) {
                if (answers == null) {
                    QcLog.e("answersLive observe answersLive == null ");
//                    if (qcEndlessScroll != null) {
//                        qcEndlessScroll.onNextPage(false);
//                        qcEndlessScroll.onNetworkLoading(false);
//                    }
                    return;
                }
                QcLog.e("Success page = " + page + " , hasNextPage =" + answers.getHasMore());
                QcToast.getInstance().show("observe page = " + page + " , hasNextPage =" + answers.getHasMore(), false);
//                Snackbar.make(viewBinding.qcRecyclerView, "Success page = " + page + " , hasNextPage =" + answers.getHasMore(), Snackbar.LENGTH_LONG)
//                        .setAction("Action", new OnSingleClickListener() {
//                            @Override
//                            public void onSingleClick(View v) {
//                                QcLog.e("Snackbar onSingleClick ");
//                            }
//                        }).show();

                QcLog.e("answers = " + answers.getItemResponses().toString());
                adapter.removeLoadFail();
                adapter.removeProgress();
                adapter.addList(answers.getItemResponses());

                hasNextPage = answers.getHasMore();
                loading = false;
//                if (qcEndlessScroll != null) {
//                    qcEndlessScroll.onNextPage(answers.getHasMore());
//                    qcEndlessScroll.onNetworkLoading(false);
//                }

            }
        });
    }

    /**
     * 스크롤 리스너에서 페이지 리셋이 안되는듯 체크해야함
     */
    @Override
    public void onRefresh() {
        if (isLoading) {
            QcToast.getInstance().show("isRefreshing !! " + isLoading, false);
            return;
        }
        needResetData();
        isLoading = true;
        viewModel.getAnswersFromRemoteResponse().removeObservers(this);

        if (viewBinding.qcRecyclerView != null
                && viewBinding.qcRecyclerView.isReverseLayout()) {
            viewBinding.swipeRefreshLayout.setEnabled(false);
            viewBinding.swipeRefreshLayout.setRefreshing(false);

        } else {
            viewBinding.swipeRefreshLayout.setRefreshing(true);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                observerAnswersLiveData(viewModel.getAnswersFromRemoteResponse());
                viewBinding.swipeRefreshLayout.setRefreshing(false);

                if (viewBinding.qcRecyclerView != null
                        && viewBinding.qcRecyclerView.isReverseLayout()) {
                } else {
                    viewBinding.swipeRefreshLayout.setRefreshing(false);
                }
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

    private QcRecyclerBaseAdapter.QcRecyclerItemListener qcRecyclerItemListener = new QcRecyclerBaseAdapter.QcRecyclerItemListener() {


        @Override
        public void onItemClick(View view, int position, Object o) {
            ItemResponse item = (ItemResponse) o;
            QcLog.e("onItemClick == " + item.toString());
        }

        @Override
        public void onItemLongClick(View view, int position, Object o) {
            ItemResponse item = (ItemResponse) o;
            QcLog.e("onItemClick == " + item.toString());
        }

        @Override
        public void onItemCheck(boolean checked, int position, Object o) {
            ItemResponse item = (ItemResponse) o;
            QcLog.e("onItemClick == " + item.toString());
        }

        @Override
        public void onDeleteItem(int itemPosition, Object o) {
            ItemResponse item = (ItemResponse) o;
            QcLog.e("onItemClick == " + item.toString());
        }

        @Override
        public void onReload() {
//            if (viewModel != null)
//                viewModel.getAnswersFromRemoteResponse(page, remoteDataListener);
        }
    };
}