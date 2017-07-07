package com.example.architecture.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.architecture.R;
import com.example.architecture.viewmodel.UserProfileViewModel;
import com.ulling.lib.core.base.BaseQLifecycleFragment;
import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-04.
 */
public class UserProfileFragment extends BaseQLifecycleFragment {
    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;
    private String userId;
    private int section_number;
    private TextView books_tv;
    private Button button;

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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userId = getArguments().getString(UID_KEY);
        section_number = getArguments().getInt(ARG_SECTION_NUMBER);
        initViewModel();
        subscribeUiFromViewModel();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profile, container, false);
        books_tv = (TextView) view.findViewById(R.id.books_tv);
        button = (Button) view.findViewById(R.id.button);
        return view;
    }


    @Override
    public  void initViewModel() {
        QcLog.e("initViewModel == ");
        // 안드로이드가 ViewModel을 생성합니다.
        // ViewModel 최고의 장점은 configurationChanges에서도 살아남는 점입니다!
        // 내장된 ViewModelProviders.of(...)를 이용해서 onCreate가 ViewModel의 인스턴스를 얻는다는 점을 주의하세요. 이전에 이 액티비티 생애주기를 위한 CustomResultViewModel이 없었다면 새롭게 생성합니다.
        viewModel = ViewModelProviders.of(this).get(UserProfileViewModel.class);
        viewModel.createDb();

    }

    @Override
    public void subscribeUiFromViewModel() {
        QcLog.e("subscribeUiLoans == ");
        viewModel.getLoansResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String result) {
                QcLog.e("getLoansResult observe == ");
                books_tv.setText(result);
            }
        });
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