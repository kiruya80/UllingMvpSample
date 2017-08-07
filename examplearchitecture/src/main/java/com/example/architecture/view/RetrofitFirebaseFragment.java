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
import com.example.architecture.databinding.FragRetrofitFirebaseBinding;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.view.adapter.RetrofitFirebaseAdapter;
import com.example.architecture.viewmodel.RetrofitFirebaseViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
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
public class RetrofitFirebaseFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {

    private QUllingApplication qApp;
    private FragRetrofitFirebaseBinding viewBinding;
    private RetrofitFirebaseViewModel viewModel;
    private RetrofitFirebaseAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;
    // Sets the starting page index
    private int viewStartingPageIndex = 1;
    private boolean loading = false;
    private boolean hasNextPage = true;
    // The current offset index of data you have loaded
    private int viewCurrentPage = 1;
    private EndlessRecyclerScrollListener qcEndlessScroll;

//    private DatabaseReference databaseReference;
    private String childName = "answerList";
//    private ValueEventListener valueEventListener;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public RetrofitFirebaseAdapter getAdapter() {
        return adapter;
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static RetrofitFirebaseFragment newInstance(int sectionNumber) {
        RetrofitFirebaseFragment fragment = new RetrofitFirebaseFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_retrofit_firebase;
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

//        databaseReference = FirebaseDatabase.getInstance().getReference().child(childName);

        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(RetrofitFirebaseViewModel.class);
            viewModel.needInitViewModel(getActivity(), this);
            viewModel.needDatabaseModel(DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL, childName);
//            viewModel.initViewModel(this, getActivity(), DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new RetrofitFirebaseAdapter(this);
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
        viewBinding = (FragRetrofitFirebaseBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter, QcDefine.PAGE_SIZE, viewBinding.tvEmpty);

        qcEndlessScroll = viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener();
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {

            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore ===== " + hasNextPage + " ,  loading = " + loading);
//                if (hasNextPage && !loading) {
//                    QcToast.getInstance().show("onLoadMore !! " + page, false);
//                    page = page_;
//                    loading = true;
//                    adapter.addProgress();
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if (viewModel != null) {
//                                viewModel.getAnswersFromRemoteResponse(page);
//                            }
//                        }
//                    }, 1000);
//                }
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
                if (viewModel != null) {
                    viewModel.getAnswersFromRemoteResponse(page);
                    page++;
                }
            }
        });
        viewBinding.btnGetFirebase.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                QcLog.e("btnGetFirebase == ");
                refreshData();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        observerAnswersLiveData(viewModel.getAnswersList());
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
        });
        viewBinding.btnSaveFirebase.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                QcLog.e("btnSaveFirebase == ");

            }
        });
    }

    public void onSuccess(int statusCode, boolean hasNextPage, QcBaseItem data) {
//            // 성공한 경우 처리

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
        }
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
        viewModel.subscribeFirebaseDatabase();

//        valueEventListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                QcLog.e("ValueEventListener 11 onDataChange == ");
//                // This method is called once with the initial value and again
//                // whenever data at this location is updated.
////                List<User> userList = new ArrayList<>();
////                for (DataSnapshot ds : dataSnapshot.getChildren()) {
////                    User user = ds.getValue(User.class);
////                    if (user != null) {
////                        userList.add(user);
////                    }
////                }
////                adapter.addAll(userList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError error) {
//                QcLog.e("onCancelled user == " + error.toException());
//            }
//        };
//        if (databaseReference != null)
//            databaseReference.addValueEventListener(valueEventListener);
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
        observerAnswersLiveData(viewModel.getAnswersList());
    }

    // https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792

    /**
     * 네트워크에서 가져온 데이터 셋팅
     *
     * @param answersLive
     */
    private void observerAnswersLiveData(LiveData<List<ItemResponse>> answersLive) {
        //observer LiveData
        answersLive.observe(this, new Observer<List<ItemResponse>>() {
            @Override
            public void onChanged(@Nullable List<ItemResponse> answersList) {
                if (answersList == null) {
                    QcLog.e("answersList == null ");
                    return;
                }

                adapter.removeLoadFail();
                adapter.removeProgress();
                adapter.addAll(answersList);

//                hasNextPage = answers.getHasMore();
                loading = false;

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
        refreshData();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                observerAnswersLiveData(viewModel.getAnswersList());
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

    public void refreshData() {

        needResetData();
        isLoading = true;
        viewModel.getAnswersList().removeObservers(this);

        if (viewBinding.qcRecyclerView != null
                && viewBinding.qcRecyclerView.isReverseLayout()) {
            viewBinding.swipeRefreshLayout.setEnabled(false);
            viewBinding.swipeRefreshLayout.setRefreshing(false);

        } else {
            viewBinding.swipeRefreshLayout.setRefreshing(true);
        }
    }
}