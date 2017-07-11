package com.example.architecture.view;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.viewmodel.UserProfileViewModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.ulling.lib.core.base.BaseLazyViewPagerQFragement;
import com.ulling.lib.core.util.QcLog;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

/**
 * Created by P100651 on 2017-07-04.
 *
 * http://anhana.tistory.com/4
 *
 * https://github.com/firebase/quickstart-android/tree/master/database
 */
public class FireDatabaseFragment extends BaseLazyViewPagerQFragement {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private UserProfileViewModel viewModel;
    private String userId;
    private TextView tvUsers;
    private Button getButton;
    private Button addButton;
    private Button deleteButton;
    private int nThreads = 2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    public FireDatabaseFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static FireDatabaseFragment newInstance(int sectionNumber) {
        FireDatabaseFragment fragment = new FireDatabaseFragment();
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

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("appVersion");

        QcLog.e("databaseReference.toString() == " + databaseReference.toString());
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


    private void initFirebaseDatabase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("message");
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                ChatData chatData = dataSnapshot.getValue(ChatData.class);
//                chatData.firebaseKey = dataSnapshot.getKey();
//                mAdapter.add(chatData);
//                mListView.smoothScrollToPosition(mAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
//                int count = mAdapter.getCount();
//                for (int i = 0; i < count; i++) {
//                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
//                        mAdapter.remove(mAdapter.getItem(i));
//                        break;
//                    }
//                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        databaseReference.removeEventListener(childEventListener);
    }
    private EditText mEdtMessage;
    public void onClick(View v) {
//        String message = mEdtMessage.getText().toString();
//        if (!TextUtils.isEmpty(message)) {
//            mEdtMessage.setText("");
//            databaseReference.push().setValue(message);
//        }
//
//
//        String message = mEdtMessage.getText().toString();
//        if (!TextUtils.isEmpty(message)) {
//            mEdtMessage.setText("");
//            ChatData chatData = new ChatData();
//            chatData.userName = userName;
//            chatData.message = message;
//            chatData.time = System.currentTimeMillis();
//            databaseReference.push().setValue(chatData);
//        }

    }
}