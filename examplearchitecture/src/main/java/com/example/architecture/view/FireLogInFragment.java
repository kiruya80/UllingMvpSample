package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import com.google.firebase.database.FirebaseDatabase;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.viewmodel.UserProfileViewModel;
import com.ulling.lib.core.base.BaseLazyViewPagerQFragement;
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
public class FireLogInFragment extends BaseLazyViewPagerQFragement implements View.OnClickListener {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;
    private int nThreads = 2;
    private FirebaseDatabase firebaseDatabase;

    private Button AnonymousAuthBtn;
    private Button CustomAuthBtn;
    private Button EmailPasswordBtn;
    private Button googleLogInBtn;
    private Button facebookLogInBtn;
    private Button TwitterLoginBtn;
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
    protected int getFragmentLayoutId() {
        return R.layout.frag_fire_login;
    }

    @Override
    protected void setup(View view) {
        AnonymousAuthBtn = (Button) view.findViewById(R.id.AnonymousAuthBtn);
        CustomAuthBtn = (Button) view.findViewById(R.id.CustomAuthBtn);
        EmailPasswordBtn = (Button) view.findViewById(R.id.EmailPasswordBtn);
        // Assign fields
        googleLogInBtn = (Button) view.findViewById(R.id.googleLogInBtn);
        facebookLogInBtn = (Button) view.findViewById(R.id.facebookLogInBtn);
        TwitterLoginBtn = (Button) view.findViewById(R.id.TwitterLoginBtn);

        // Set click listeners
        AnonymousAuthBtn.setOnClickListener(this);
        CustomAuthBtn.setOnClickListener(this);
        EmailPasswordBtn.setOnClickListener(this);
        googleLogInBtn.setOnClickListener(this);
        facebookLogInBtn.setOnClickListener(this);
        TwitterLoginBtn.setOnClickListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        userId = getArguments().getString(UID_KEY);
    }

    @Override
    protected void initData() {
        QcLog.e("initData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        initViewModel();
    }

    @Override
    public void initViewModel() {
        QcLog.e("initViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }

    @Override
    public void lazyFetchData() {
        QcLog.e("lazyFetchData == ");
    }

    @Override
    public void subscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
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


    @Override
    public void onDestroy() {
        super.onDestroy();
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