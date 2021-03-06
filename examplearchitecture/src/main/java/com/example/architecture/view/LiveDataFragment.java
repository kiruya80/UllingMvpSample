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
import com.example.architecture.databinding.FragLiveDataBinding;
import com.example.architecture.entities.room.User;
import com.example.architecture.view.adapter.LiveDataAdapter;
import com.example.architecture.viewmodel.LiveDataViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by P100651 on 2017-07-04.
 */
public class LiveDataFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {

    public static String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private QUllingApplication qApp;
    private FragLiveDataBinding viewBinding;
    private static final String UID_KEY = "uid";
    private LiveDataViewModel viewModel;
    private LiveDataAdapter adapter;
    private int id_ = 1;
    private String userId;
    private boolean isLoading = false;
    private int page = 1;
    // Sets the starting page index
    private int viewStartingPageIndex = 1;
    // The current offset index of data you have loaded
    private int viewCurrentPage = 1;
    private EndlessRecyclerScrollListener qcEndlessScroll;

    public static LiveDataFragment newInstance(int sectionNumber) {
        LiveDataFragment fragment = new LiveDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_live_data;
    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        QcLog.e("optGetArgument == ");
        userId = getArguments().getString(UID_KEY);
    }

    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        id_ = QcPreferences.getInstance().get("index", 1);
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);
            viewModel.initViewModel(qCon, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new LiveDataAdapter(this);
        if (adapter != null && !adapter.isViewModel())
            adapter.setViewModel(viewModel);
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
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragLiveDataBinding) getViewBinding();
//        viewBinding.recyclerView.setAdapter(adapter);
        viewBinding.qcRecyclerView.setAdapter(adapter, QcDefine.PAGE_SIZE, viewBinding.tvEmpty);
        qcEndlessScroll = viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener();
        qcEndlessScroll.onStartingPageIndex(viewStartingPageIndex);
        qcEndlessScroll.onResetStatus();
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {
            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore =====");
                page = page_;
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
        viewBinding.getButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                QcLog.e("getUser == ");
                viewModel.getAllUsers();
            }
        });
        viewBinding.addButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                QcLog.e("addUser == ");
                viewModel.addUserDao(randomUser());
            }
        });
        viewBinding.deleteButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Random random = new Random();
                int ranIndex = random.nextInt(20);
                QcLog.e("deleteUser == " + ranIndex);
//                viewModel.deleteUserDao(Integer.toString(ranIndex));
                viewModel.deleteUserDaoAsyncTask(Integer.toString(ranIndex));
            }
        });
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
    }

    @Override
    protected void needOnShowToUser() {
        QcLog.e("needOnShowToUser == ");
        observerUserListResults(viewModel.getAllUsers());
    }

    @Override
    protected void needOnHiddenToUser() {
    }

    @Override
    protected void onDestroyToUser() {

    }

    private void observerUserListResults(LiveData<List<User>> userLive) {
        //observer LiveData
        userLive.observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> allUsers) {
                QcLog.e("allUsers observe == ");
                if (allUsers == null) {
                    return;
                }
                adapter.addList((ArrayList<User>) allUsers);
            }
        });
    }

    private User randomUser() {
        Random random = new Random();
        int ranIndex = random.nextInt(20);
        User user = new User();
        user.id = Integer.toString(id_);
        user.age = ranIndex;
        user.name = "Jason" + Integer.toString(ranIndex);
        user.lastName = "Seaver" + Integer.toString(ranIndex);
        id_++;
        QcPreferences.getInstance().put("index", id_);
        QcToast.getInstance().show("Add id = " + id_, false);
        return user;
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
        viewModel.getAllUsers().removeObservers(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                observerUserListResults(viewModel.getAllUsers());
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