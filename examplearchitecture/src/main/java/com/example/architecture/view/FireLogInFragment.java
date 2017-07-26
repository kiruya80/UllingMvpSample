package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import com.google.firebase.database.FirebaseDatabase;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.FragFireLoginBinding;
import com.example.architecture.viewmodel.FireLogInViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 *
 *
 * https://firebase.google.com/docs/database/android/start/
 *
 *
 *
 * http://anhana.tistory.com/4
 *
 * https://github.com/firebase/quickstart-android/tree/master/database
 *
 * http://yookn.tistory.com/244
 */
public class FireLogInFragment extends QcBaseShowLifeFragement {
    private QUllingApplication qApp;
    private FragFireLoginBinding viewBinding;
    private static final String UID_KEY = "uid";
    private FireLogInViewModel viewModel;
    private int nThreads = 2;
    private FirebaseDatabase firebaseDatabase;

    //    private Button AnonymousAuthBtn;
//    private Button CustomAuthBtn;
//    private Button EmailPasswordBtn;
//    private Button googleLogInBtn;
//    private Button facebookLogInBtn;
//    private Button TwitterLoginBtn;

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FireLogInFragment newInstance(int sectionNumber) {
        FireLogInFragment fragment = new FireLogInFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_fire_login;
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
            viewModel = ViewModelProviders.of(this).get(FireLogInViewModel.class);
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
        viewBinding = (FragFireLoginBinding) getViewBinding();
//        AnonymousAuthBtn = (Button) view.findViewById(R.id.AnonymousAuthBtn);
//        CustomAuthBtn = (Button) view.findViewById(R.id.CustomAuthBtn);
//        EmailPasswordBtn = (Button) view.findViewById(R.id.EmailPasswordBtn);
//        // Assign fields
//        googleLogInBtn = (Button) view.findViewById(R.id.googleLogInBtn);
//        facebookLogInBtn = (Button) view.findViewById(R.id.facebookLogInBtn);
//        TwitterLoginBtn = (Button) view.findViewById(R.id.TwitterLoginBtn);

        // Set click listeners
//        viewBinding.AnonymousAuthBtn.setOnClickListener(this);
//        viewBinding.CustomAuthBtn.setOnClickListener(this);
//        viewBinding.EmailPasswordBtn.setOnClickListener(this);
//        viewBinding.googleLogInBtn.setOnClickListener(this);
//        viewBinding.facebookLogInBtn.setOnClickListener(this);
//        viewBinding.TwitterLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
        viewBinding.AnonymousAuthBtn.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                QcLog.e("AnonymousAuthBtn == ");
            }
        });
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");

    }


    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
    }


    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
    }

}