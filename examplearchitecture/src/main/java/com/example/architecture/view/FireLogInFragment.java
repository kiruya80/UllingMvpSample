package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import com.google.firebase.database.FirebaseDatabase;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.databinding.FragFireLoginBinding;
import com.example.architecture.viewmodel.FireLogInViewModel;
import com.ulling.lib.core.base.BaseLazyQLifeFragement;
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
public class FireLogInFragment extends BaseLazyQLifeFragement implements View.OnClickListener {
    private QUllingApplication qApp;
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
    private FragFireLoginBinding viewBinding;

    public FireLogInFragment() {
    }

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
    public void needDestroyData() {
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_fire_login;
    }

    @Override
    protected void needViewBinding() {
        viewBinding = (FragFireLoginBinding) getViewBinding();
//        AnonymousAuthBtn = (Button) view.findViewById(R.id.AnonymousAuthBtn);
//        CustomAuthBtn = (Button) view.findViewById(R.id.CustomAuthBtn);
//        EmailPasswordBtn = (Button) view.findViewById(R.id.EmailPasswordBtn);
//        // Assign fields
//        googleLogInBtn = (Button) view.findViewById(R.id.googleLogInBtn);
//        facebookLogInBtn = (Button) view.findViewById(R.id.facebookLogInBtn);
//        TwitterLoginBtn = (Button) view.findViewById(R.id.TwitterLoginBtn);

        // Set click listeners
        viewBinding.AnonymousAuthBtn.setOnClickListener(this);
//        viewBinding.CustomAuthBtn.setOnClickListener(this);
//        viewBinding.EmailPasswordBtn.setOnClickListener(this);
//        viewBinding.googleLogInBtn.setOnClickListener(this);
//        viewBinding.facebookLogInBtn.setOnClickListener(this);
//        viewBinding.TwitterLoginBtn.setOnClickListener(this);
    }

    @Override
    protected void needInitData() {
        QcLog.e("needInitData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(FireLogInViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }


    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
    }


    @Override
    public void needPageVisiableToUser() {
        QcLog.e("needPageVisiableToUser == ");
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.AnonymousAuthBtn:
//                intent = new Intent(this, AnonymousAuthActivity.class);
//                startActivity(intent);
                break;
            case R.id.CustomAuthBtn:
//                intent = new Intent(this, CustomAuthActivity.class);
//                startActivity(intent);
                break;
            case R.id.EmailPasswordBtn:
//                intent = new Intent(this, EmailPasswordActivity.class);
//                startActivity(intent);
                break;

            case R.id.googleLogInBtn:
//                intent = new Intent(this, GoogleSignInActivity.class);
//                startActivity(intent);
                break;
            case R.id.facebookLogInBtn:
//                intent = new Intent(this, FacebookLoginActivity.class);
//                startActivity(intent);
                break;
            case R.id.TwitterLoginBtn:
//                intent = new Intent(this, TwitterLoginActivity.class);
//                startActivity(intent);
                break;
        }
    }

}