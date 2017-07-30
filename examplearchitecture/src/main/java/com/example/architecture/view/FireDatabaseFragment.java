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
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.architecture.QUllingApplication;
import com.example.architecture.R;
import com.example.architecture.common.ApiUrl;
import com.example.architecture.databinding.FragFireDatabaseBinding;
import com.example.architecture.entities.room.User;
import com.example.architecture.view.adapter.FireDatabaseAdapter;
import com.example.architecture.viewmodel.FireDatabaseViewModel;
import com.ulling.lib.core.base.QcBaseShowLifeFragement;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.view.QcRecyclerView;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

import java.util.ArrayList;
import java.util.List;
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
 * <p>
 * <p>
 * http://www.zoftino.com/android-livedata-examples
 */
public class FireDatabaseFragment extends QcBaseShowLifeFragement implements SwipeRefreshLayout.OnRefreshListener {

    private QUllingApplication qApp;
    private static final String UID_KEY = "uid";
    private FragFireDatabaseBinding viewBinding;
    private FireDatabaseViewModel viewModel;
    private String userId;
    private int nThreads = 2;
    //    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;
    private ValueEventListener valueEventListener;
    private FireDatabaseAdapter adapter;
    private boolean isLoading = false;
    private int page = 1;
    // Sets the starting page index
    private int viewStartingPageIndex = 1;
    // The current offset index of data you have loaded
    private int viewCurrentPage = 1;
    private EndlessRecyclerScrollListener qcEndlessScroll;

    @Override
    public void needDestroyData() {
        if (databaseReference != null && childEventListener != null)
            databaseReference.removeEventListener(childEventListener);
        if (databaseReference != null && valueEventListener != null)
            databaseReference.removeEventListener(valueEventListener);
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
    protected int needGetLayoutId() {
        return R.layout.frag_fire_database;
    }

    @Override
    protected void optGetArgument(Bundle savedInstanceState) {
        super.optGetArgument(savedInstanceState);
        QcLog.e("optGetArgument == ");
        userId = getArguments().getString(UID_KEY);
    }

    @Override
    protected void needInitToOnCreate() {
        QcLog.e("needInitToOnCreate == ");
        qApp = QUllingApplication.getInstance();
        APP_NAME = QUllingApplication.getAppName();
//        firebaseDatabase = FirebaseDatabase.getInstance();
//        databaseReference = firebaseDatabase.getReference("usersData");
//        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usersData");
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(FireDatabaseViewModel.class);
            viewModel.initViewModel(qCon, DB_TYPE_LOCAL_ROOM, REMOTE_TYPE_RETROFIT, ApiUrl.BASE_URL);
        }
        adapter = new FireDatabaseAdapter(this);
        if (adapter != null && !adapter.isViewModel())
            adapter.setViewModel(viewModel);
    }

    @Override
    protected void needResetData() {
        QcLog.e("needResetData == ");
        isLoading = false;
        page = viewStartingPageIndex;
        setResetScrollStatus();
      if (adapter != null)
            adapter.needResetData();
    }

    private void setResetScrollStatus() {
        if (viewBinding != null && qcEndlessScroll != null)
            qcEndlessScroll.onResetStatus();
    }
    @Override
    public void needInitViewModel() {
        QcLog.e("needInitViewModel == ");
    }

    @Override
    protected void needUIBinding() {
        QcLog.e("needUIBinding == ");
        viewBinding = (FragFireDatabaseBinding) getViewBinding();
        viewBinding.qcRecyclerView.setAdapter(adapter, viewBinding.tvEmpty);
        qcEndlessScroll = viewBinding.qcRecyclerView.getEndlessRecyclerScrollListener();
        qcEndlessScroll.onStartingPageIndex(viewStartingPageIndex);
        qcEndlessScroll.onResetStatus();
        viewBinding.qcRecyclerView.setQcRecyclerListener(new QcRecyclerView.QcRecyclerListener() {
            @Override
            public void onLoadMore(int page_, int totalItemsCount, RecyclerView view) {
                QcLog.e("onLoadMore =====");
                page = page_;
                QcToast.getInstance().show("onLoadMore !! " + page, false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        if (viewModel != null)
//                            viewModel.getAnswersFromRemote(page);
                        qcEndlessScroll.onNetworkLoading(true);
                        qcEndlessScroll.onNextPage(false);
                    }
                }, 1000);
            }

            @Override
            public void onPositionTop() {
                QcLog.e("onPositionTop =====");
                QcToast.getInstance().show("onPositionTop !! ", false);
            }
            @Override
            public void onPositionBottom() {
                QcLog.e("onPositionBottom =====");
                QcToast.getInstance().show("onPositionBottom !! ", false);
            }
        });
        viewBinding.progressBar.setVisibility(View.GONE);
        viewBinding.swipeRefreshLayout.setOnRefreshListener(this);
        viewBinding.swipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimaryDark);
    }

    @Override
    protected void needUIEventListener() {
        QcLog.e("needUIEventListener == ");
        viewBinding.addButton.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View view) {
                QcLog.e("addButton == ");
                User user = new User();
                user.setId(new Random().nextInt(20) + "_id");
                user.setName(new Random().nextInt(20) + "_name");
                user.setLastName(new Random().nextInt(20) + "_lastname");
                user.setAge(new Random().nextInt(20));
//                databaseReference.child("usersData").push().setValue(user);
//                databaseReference.child("usersData").child(user.id).setValue(user);
                if (databaseReference != null)
                    databaseReference.child(user.id).setValue(user);
            }
        });
    }

    @Override
    public void needSubscribeUiFromViewModel() {
        QcLog.e("needSubscribeUiFromViewModel == ");
    }

    @Override
    public void needShowToUser() {
        QcLog.e("needShowToUser == ");
//        for (DataSnapshot messageData : dataSnapshot.getChildren()) {
//            String msg = messageData.getValue().toString();
//            dataAdapter.add(msg);
//        }
//        getSingleAllData();
        subscribeFirebaseDatabase();
    }

    private void subscribeFirebaseDatabase() {
//        databaseReference.addValueEventListener(new ValueEventListener() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QcLog.e("ValueEventListener 11 onDataChange == ");
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                List<User> userList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null) {
//                        viewBinding.tvUsers.setText(viewBinding.tvUsers.getText() + "\n" + user.toString());
                        userList.add(user);
                    }
//                            String name = ds.child("name").getValue(String.class);
//                            String lastName = ds.child("lastName").getValue(String.class);
//                            //and so on
//                            QcLog.e("name == " + name + " , lastName = " + lastName);
                }
                adapter.addAll(userList);
//                String userId = dataSnapshot.getKey();
//                QcLog.e("userId == " + userId.toString());
//                DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("usersData").child(userId);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                QcLog.e("onCancelled user == " + error.toException());
            }
        };
        if (databaseReference != null)
            databaseReference.addValueEventListener(valueEventListener);
    }






    @Override
    public void onRefresh() {
        if (isLoading) {
            QcToast.getInstance().show("isRefreshing !! " + isLoading, false);
            return;
        }
        needResetData();
        isLoading = true;
        viewBinding.swipeRefreshLayout.setRefreshing(true);
//        viewModel.getAllAnswersFromRoom().removeObservers(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                subscribeFirebaseDatabase();
                viewBinding.swipeRefreshLayout.setRefreshing(false);
                isLoading = false;
            }
        }, 2000);
    }

    private void setProgress(boolean isProgress) {
        if (isProgress) {
            viewBinding.progressBar.setVisibility(View.VISIBLE);
        } else {
            viewBinding.progressBar.setVisibility(View.GONE);
        }
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
//                viewBinding.tvUsers.setText(user.toString());
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
        if (databaseReference != null)
            databaseReference.addChildEventListener(childEventListener);
    }

    private void getSingleAllData() {
//        DatabaseReference keyRef = FirebaseDatabase.getInstance().getReference().child("usersData");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                QcLog.e("ValueEventListener 22 onDataChange == ");
//                viewBinding.tvUsers.setText("");
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    if (user != null) {
                        QcLog.e("onDataChange == " + user.toString());
//                        viewBinding.tvUsers.setText(viewBinding.tvUsers.getText() + "\n" + user.toString());
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
        if (databaseReference != null)
            databaseReference.addListenerForSingleValueEvent(valueEventListener);
    }
}