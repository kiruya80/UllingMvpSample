package com.example.architecture.view;

import static com.example.architecture.model.DatabaseModel.DB_TYPE_LOCAL_ROOM;
import static com.example.architecture.model.DatabaseModel.REMOTE_TYPE_RETROFIT;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.databinding.FragFireDatabaseBinding;
import com.example.architecture.enty.User;
import com.example.architecture.viewmodel.FireDatabaseViewModel;
import com.ulling.lib.core.base.BaseLazyQLifeFragement;
import com.ulling.lib.core.util.QcLog;

import java.util.Random;

/**
 * Created by P100651 on 2017-07-04.
 * <p>
 * <p>
 * https://firebase.google.com/docs/database/android/start/
 * <p>
 * <p>
 * <p>
 * http://anhana.tistory.com/4
 * <p>
 * https://github.com/firebase/quickstart-android/tree/master/database
 * <p>
 * http://yookn.tistory.com/244
 */
public class FireDatabaseFragment extends BaseLazyQLifeFragement {
    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private FireDatabaseViewModel viewModel;
    private String userId;
//    private TextView tvUsers;
//    private Button getButton;
//    private Button addButton;
//    private Button deleteButton;
    private int nThreads = 2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private ValueEventListener valueEventListener;
    FragFireDatabaseBinding viewBinding;

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
    public void needDestroyData() {
        if (databaseReference != null && childEventListener != null)
            databaseReference.removeEventListener(childEventListener);
        if (databaseReference != null && valueEventListener != null)
            databaseReference.removeEventListener(valueEventListener);
    }

    @Override
    protected int needGetLayoutId() {
        return R.layout.frag_fire_database;
    }

    @Override
    protected void needViewBinding() {
        viewBinding = (FragFireDatabaseBinding) getViewBinding();
//        tvUsers = (TextView) view.findViewById(R.id.tvUsers);
//        addButton = (Button) view.findViewById(R.id.addButton);
        viewBinding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QcLog.e("addUser == ");
                User user = new User();
                user.setId(new Random().nextInt(100) + "_id");
                user.setName(new Random().nextInt(100) + "_name");
                user.setLastName(new Random().nextInt(100) + "_lastname");
                user.setAge(new Random().nextInt(100));
//                databaseReference.child("usersData").push().setValue(user);
//                databaseReference.child("usersData").child(user.id).setValue(user);
                databaseReference.child(user.id).setValue(user);
            }
        });
    }

    @Override
    protected void needInitData() {
        QcLog.e("initData == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("usersData");
//        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usersData");
        initFirebaseDatabase();
    }

    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(FireDatabaseViewModel.class);
            viewModel.initViewModel(qCon, nThreads, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT);
        }
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
    }

    @Override
    protected void optGetArgument() {
        QcLog.e("optGetArgument == ");
        userId = getArguments().getString(UID_KEY);
    }

    @Override
    public void needPageVisiableToUser() {
        QcLog.e("needPageVisiableToUser == ");
        QcLog.e("databaseReference.toString() == " + databaseReference.toString());
//        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
//            String msg = messageData.getValue().toString();
//            dataAdapter.add(msg);
//        }
//        getSingleAllData();
    }


    private void initFirebaseDatabase() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QcLog.e("ValueEventListener onDataChange == ");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                viewBinding.tvUsers.setText("");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null) {
                        QcLog.e("onDataChange == " + user.toString());
                        viewBinding.tvUsers.setText(viewBinding.tvUsers.getText() + "\n" + user.toString());
                    }
//                            String name = ds.child("name").getValue(String.class);
//                            String lastName = ds.child("lastName").getValue(String.class);
//                            //and so on
//                            QcLog.e("name == " + name + " , lastName = " + lastName);
                }
//                String userId = dataSnapshot.getKey();
//                QcLog.e("userId == " + userId.toString());
//                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("usersData").child(userId);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                QcLog.e("onCancelled user == " + error.toException());
            }
        };
        databaseReference.addValueEventListener(valueEventListener);
    }

    private void getSingleAllData() {
        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("usersData");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QcLog.e("addListenerForSingleValueEvent ValueEventListener onDataChange == ");
                viewBinding.tvUsers.setText("");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null) {
                        QcLog.e("onDataChange == " + user.toString());
                        viewBinding.tvUsers.setText(viewBinding.tvUsers.getText() + "\n" + user.toString());
                    }
//                            String name = ds.child("name").getValue(String.class);
//                            String lastName = ds.child("lastName").getValue(String.class);
//                            //and so on
//                            QcLog.e("name == " + name + " , lastName = " + lastName);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        keyRef.addListenerForSingleValueEvent(valueEventListener);
    }

    private void initFirebaseDatabase2() {
        childEventListener = new ChildEventListener() {
            /**
             * 리스트의 아이템을 검색하거나 아이템의 추가가 있을때 수신합니다.
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                User user = dataSnapshot.getValue(User.class);
                QcLog.e("onChildAdded user == " + user.toString());
                viewBinding.tvUsers.setText(user.toString());
            }

            /**
             * 아이템의 변화가 있을때 수신합니다.
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                QcLog.e("onChildChanged == ");
            }

            /**
             * 아이템이 삭제되었을때 수신합니다.
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                QcLog.e("onChildRemoved == ");
                User removeUser = dataSnapshot.getValue(User.class);
                QcLog.e("onChildRemoved == " + removeUser.toString());
//                mAdapter.remove(message);
//                String firebaseKey = dataSnapshot.getKey();
//                int count = mAdapter.getCount();
//                for (int i = 0; i < count; i++) {
//                    if (mAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
//                        mAdapter.remove(mAdapter.getItem(i));
//                        break;
//                    }
//                }
            }

            /**
             *  순서가 있는 리스트에서 순서가 변경되었을때 수신합니다.
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                QcLog.e("onChildMoved == ");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                QcLog.e("onCancelled == ");
            }
        };
        databaseReference.addChildEventListener(childEventListener);
    }

}