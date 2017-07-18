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
import com.example.architecture.databinding.FragUserProfileBinding;
import com.example.architecture.enty.User;
import com.example.architecture.viewmodel.LiveDataViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;

import java.util.List;
import java.util.Random;

/**
 * Created by P100651 on 2017-07-04.
 */
public class LiveDataFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragUserProfileBinding viewBinding;
    private static final String UID_KEY = "uid";
    private LiveDataViewModel viewModel;
    private String userId;
    private int nThreads = 2;

    public static LiveDataFragment newInstance(int sectionNumber) {
        LiveDataFragment fragment = new LiveDataFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }


//    public static BaseQLifeFragment newInstance(LiveData<User> data) {
//        LiveDataFragment fragment = new LiveDataFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_SECTION_NUMBER, (Serializable) data);
//        fragment.setArguments(args);
//        return fragment;
//    }
//    public static BaseQLifeFragment newInstance(Bundle args) {
//        LiveDataFragment fragment = new LiveDataFragment();
//        fragment.setArguments(args);
//        return fragment;
//    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_user_profile;
    }


    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        QcLog.e("optGetArgument == ");
        userId = getArguments().getString(UID_KEY);
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        id_ = QcPreferences.getInstance().get("index", 1);
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragUserProfileBinding) getViewBinding();
//        tvUsers = (TextView) view.findViewById(R.id.tvUsers);
//        getButton = (Button) view.findViewById(R.id.getButton);
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
//        addButton = (Button) view.findViewById(R.id.addButton);
        viewBinding.addButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                QcLog.e("addUser == ");
                viewModel.addUserDao(randomUser());
            }
        });
//        deleteButton = (Button) view.findViewById(deleteButton);
        viewBinding.deleteButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                Random random = new Random();
                int ranIndex = random.nextInt(100);
                QcLog.e("deleteUser == " + ranIndex);
//                viewModel.deleteUserDao(Integer.toString(ranIndex));
                viewModel.deleteUserDaoAsyncTask(Integer.toString(ranIndex));
            }
        });

    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
        // 안드로이드가 ViewModel을 생성합니다.
        // ViewModel 최고의 장점은 configurationChanges에서도 살아남는 점입니다!
        // 내장된 ViewModelProviders.of(...)를 이용해서 onCreate가 ViewModel의 인스턴스를 얻는다는 점을 주의하세요. 이전에 이 액티비티 생애주기를 위한 CustomResultViewModel이 없었다면 새롭게 생성합니다.
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
//        if (viewModel != null && viewModel.getLoansResult() != null)
//        viewModel.getLoansResult().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable final String result) {
//                QcLog.e("getLoansResult observe == ");
//                books_tv.setText(result);
//            }
//        });
//        observerUserResults(viewModel.getUserInfo("1"));
        observerUserListResults(viewModel.getAllUsers());
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }

    private void observerUserResults(LiveData<User> userLive) {
        //observer LiveData
        userLive.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                QcLog.e("User observe == ");
                if (user == null) {
                    return;
                }
                viewBinding.tvUsers.setText(user.toString());
            }
        });
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
                viewBinding.tvUsers.setText(allUsers.toString());
            }
        });
    }

    int id_ = 1;

    private User randomUser() {
        Random random = new Random();
        int ranIndex = random.nextInt(100);
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
}