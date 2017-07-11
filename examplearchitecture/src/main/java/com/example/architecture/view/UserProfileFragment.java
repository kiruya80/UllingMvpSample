package com.example.architecture.view;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.enty.User;
import com.example.architecture.viewmodel.UserProfileViewModel;
import com.ulling.lib.core.base.BaseLazyViewPagerQFragement;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcPreferences;
import com.ulling.lib.core.util.QcToast;

import java.util.List;
import java.util.Random;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

/**
 * Created by P100651 on 2017-07-04.
 */
public class UserProfileFragment extends BaseLazyViewPagerQFragement {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;
    private String userId;
    private TextView tvUsers;
    private Button getButton;
    private Button addButton;
    private Button deleteButton;
    private int nThreads = 2;

    public UserProfileFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static UserProfileFragment newInstance(int sectionNumber) {
        UserProfileFragment fragment = new UserProfileFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.user_profile;
    }

    @Override
    protected void setup(View view) {
        tvUsers = (TextView) view.findViewById(R.id.tvUsers);
        getButton = (Button) view.findViewById(R.id.getButton);
        getButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QcLog.e("getUser == ");
                viewModel.getAllUsers();
            }
        });
        addButton = (Button) view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QcLog.e("addUser == ");
                viewModel.addUserDao(randomUser());
            }
        });
        deleteButton = (Button) view.findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Random random = new Random();
                int ranIndex = random.nextInt(100);
                QcLog.e("deleteUser == " + ranIndex);
//                viewModel.deleteUserDao(Integer.toString(ranIndex));
                viewModel.deleteUserDaoAsyncTask(Integer.toString(ranIndex));
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = getArguments().getString(UID_KEY);
    }

    @Override
    protected void initData() {
        QcLog.e("initData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        id_ = QcPreferences.getInstance().get("index", 1);
        initViewModel();
        QcToast.getInstance().show("initData !!", false);
    }

    @Override
    public void initViewModel() {
        QcLog.e("initViewModel == ");
        // 안드로이드가 ViewModel을 생성합니다.
        // ViewModel 최고의 장점은 configurationChanges에서도 살아남는 점입니다!
        // 내장된 ViewModelProviders.of(...)를 이용해서 onCreate가 ViewModel의 인스턴스를 얻는다는 점을 주의하세요. 이전에 이 액티비티 생애주기를 위한 CustomResultViewModel이 없었다면 새롭게 생성합니다.
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }

    @Override
    public void lazyFetchData() {
        QcLog.e("lazyFetchData == ");
        subscribeUiFromViewModel();
    }

    @Override
    public void subscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
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

    private void observerUserResults(LiveData<User> userLive) {
        //observer LiveData
        userLive.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                QcLog.e("User observe == ");
                if (user == null) {
                    return;
                }
                tvUsers.setText(user.toString());
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
                tvUsers.setText(allUsers.toString());
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

    @Override
    public void resetData() {
    }

    @Override
    public void startAnimation() {
    }

    @Override
    public void stopAnimation() {
    }
}