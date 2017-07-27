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
import com.example.architecture.databinding.FragRetrofitLiveBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.view.adapter.RetrofitLiveAdapter;
import com.example.architecture.viewmodel.RetrofitLiveViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;

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
    private QUllingApplication qApp;
    private FragRetrofitLiveBinding viewBinding;
    private RetrofitLiveViewModel viewModel;
    private RetrofitLiveAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;

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
            viewModel.initViewModel(qCon, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new RetrofitLiveAdapter(this);
        if (adapter != null && !adapter.isViewModel())
            adapter.setViewModel(viewModel);
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        isLoading = false;
        page = 1;
        if (viewBinding != null && viewBinding.qcRecyclerView != null && viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener() != null)
            viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener().setStartingPageIndex(page);
        if (adapter != null)
        adapter.needResetData();
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragRetrofitLiveBinding) getViewBinding();
        viewBinding.qcRecyclerView.setEmptyView(viewBinding.tvEmpty);
        viewBinding.qcRecyclerView.setAdapter(adapter);
        viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener().setStartingPageIndex(page);
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {
            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore =====");
                page = page_;
                if (viewModel != null)
                    viewModel.getAnswersFromRemote(page);
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
                    viewModel.getAnswersFromRemote(page);
            }
        });
        viewBinding.btnDeleteRoom.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                if (viewModel != null)
                    viewModel.deleteAnswerFromRoom();
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
        observerAllAnswer(viewModel.getAllAnswersFromRoom());
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }

    private void observerAllAnswer(LiveData<List<Answer>> answers) {
        QcLog.e("observerAllAnswer == ");
        //observer LiveData
        answers.observe(this, new Observer<List<Answer>>() {
            @Override
            public void onChanged(@Nullable List<Answer> allanswers) {
                QcLog.e("allanswers observe == ");
                if (allanswers == null) {
                    return;
                }
//                adapter.addAll(allanswers);
                adapter.addAnswer(allanswers);

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
//        adapter.needResetData();
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