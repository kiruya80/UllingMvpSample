package com.example.architecture.view;

import android.arch.lifecycle.LifecycleFragment;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.databinding.ActivityMainBinding;
import com.ulling.lib.core.base.QcBaseLifecycleActivity;
import com.ulling.lib.core.util.QcLog;

import java.util.ArrayList;

public class MainActivity extends QcBaseLifecycleActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private QUllingApplication qApp;
    private ArrayList<LifecycleFragment> BaseQLifecycleFragmentList = new ArrayList<>();

    ActivityMainBinding viewBinding;

    @Override
    protected void needDestroyData() {

    }
    @Override
    protected int needGetLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void needLayoutIdBinding() {
        viewBinding = (ActivityMainBinding) getViewDataBinding();
    }
//    public ViewDataBinding getViewDataBinding() {
//        return DataBindingUtil.setContentView(this, needGetLayoutId());
//    }

    @Override
    protected void needSetupView() {
//        mViewPager = (ViewPager) findViewById(R.id.container);
//        mViewPager.setAdapter(mSectionsPagerAdapter);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);
//        tabLayout.setTabMode(TabLayout.MODE_FIXED);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        viewBinding.container.setAdapter(mSectionsPagerAdapter);
        viewBinding.tabs.setupWithViewPager(viewBinding.container);
        viewBinding.tabs.setTabMode(TabLayout.MODE_FIXED);
        viewBinding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void needInitData() {
        qApp = QUllingApplication.getInstance();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        setFragment();
    }

    @Override
    protected void needInitViewModel() {

    }

    @Override
    protected void needSubscribeUiFromViewModel() {

    }


    private void setFragment() {
        BaseQLifecycleFragmentList.add(FireLogInFragment.newInstance(0));
        BaseQLifecycleFragmentList.add(FireDatabaseFragment.newInstance(1));
        BaseQLifecycleFragmentList.add(RetrofitFragment.newInstance(2));
        BaseQLifecycleFragmentList.add(LiveDataFragment.newInstance(3));
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = new String[]{"LogIn", "Firebase", "Retrofit", "LiveData"};

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            QcLog.e("getItem == " + position);
            return BaseQLifecycleFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return BaseQLifecycleFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }
}
