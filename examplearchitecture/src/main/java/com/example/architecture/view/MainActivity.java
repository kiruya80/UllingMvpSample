package com.example.architecture.view;

import android.arch.lifecycle.LifecycleFragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.ulling.lib.core.base.BaseQLifecycleActivity;
import com.ulling.lib.core.util.QcLog;

import java.util.ArrayList;

public class MainActivity extends BaseQLifecycleActivity {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private QUllingApplication qApp;
    private ArrayList<LifecycleFragment> BaseQLifecycleFragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment();
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        qApp = QUllingApplication.getInstance();
    }

    private void setFragment() {
        BaseQLifecycleFragmentList.add(FireLogInFragment.newInstance(0));
        BaseQLifecycleFragmentList.add(FireDatabaseFragment.newInstance(1));
        BaseQLifecycleFragmentList.add(RetrofitFragment.newInstance(2));
        BaseQLifecycleFragmentList.add(UserProfileFragment.newInstance(3));
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
