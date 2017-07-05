/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ulling.googlemodule.view;

import android.arch.lifecycle.LifecycleActivity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ulling.googlemodule.R;
import com.ulling.lib.core.util.QcLog;

public class CustomResultUserActivity extends LifecycleActivity {

    private CustomResultViewModel mShowUserViewModel;

    private TextView mBooksTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QcLog.e("onCreate == ");

        setContentView(R.layout.db_activity);
        mBooksTextView = (TextView) findViewById(R.id.books_tv);

//        IntentFilter filter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
//        registerReceiver(mBroadcastReceiver,filter);

        mShowUserViewModel = ViewModelProviders.of(this).get(CustomResultViewModel.class);

        populateDb();

        subscribeUiLoans();
    }

    private void populateDb() {
        QcLog.e("populateDb == ");
        mShowUserViewModel.createDb();
    }

    private void subscribeUiLoans() {
        mShowUserViewModel.getLoansResult().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable final String result) {
                QcLog.e("getLoansResult observe == ");
                mBooksTextView.setText(result);
            }
        });
    }

    public void onRefreshBtClicked(View view) {
        QcLog.e("onRefreshBtClicked == ");
        populateDb();
        subscribeUiLoans();
    }


    static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    static final String SYSTEM_DIALOG_REASON_GLOBAL_ACTIONS = "globalactions";
    static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";

//    @Override
//    protected void onPause() {
//        super.onPause();
//        unregisterReceiver(mBroadcastReceiver);
//    }
//
//    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            QcLog.e("BroadcastReceiver Event ========= ");
//
//            String action = intent.getAction();
//            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
//                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
//                if (reason != null) {
//                    if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
//                        QcLog.e("Home Clcik Event ========= ");
//                    } else if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
//                        QcLog.e("Home Long Press Event ========= ");
//                    }
//                }
//            }
//
//        }
//    };
//
//    @Override
//    public void onAttachedToWindow() {
//        super.onAttachedToWindow();
//        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
//    }
//
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_HOME)
//        {
//            Log.i("Home Button","Clicked");
//        }
//        if(keyCode==KeyEvent.KEYCODE_BACK)
//        {
//            QcLog.e("KEYCODE_BACK Event ========= ");
//            finish();
//        }
//        return false;
//    }
}
