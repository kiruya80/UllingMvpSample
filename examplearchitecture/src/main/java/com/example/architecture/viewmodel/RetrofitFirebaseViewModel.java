package com.example.architecture.viewmodel;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.model.DatabaseModel;
import com.example.architecture.network.RemoteDataListener;
import com.example.architecture.view.RetrofitFirebaseFragment;
import com.ulling.lib.core.base.QcBaseAndroidViewModel;
import com.ulling.lib.core.entities.QcBaseItem;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P100651 on 2017-07-04.
 *
 * UI의 데이터를 준비하는 클래스
 * livedata 1,2,3,....
 *
 * https://code.tutsplus.com/tutorials/getting-started-with-retrofit-2--cms-27792
 */
public class RetrofitFirebaseViewModel extends QcBaseAndroidViewModel {
    private DatabaseModel mDatabaseModel;
    private DatabaseReference databaseReference;
    private ValueEventListener valueEventListener;

    public RetrofitFirebaseViewModel(@NonNull Application application) {
        super(application);
    }

    @Override
    public void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl) {

    }

    //    @Override
    public void needDatabaseModel(int dbTypeLocal, int remoteType, String baseUrl, String firebaseChildName) {
        // db model 초기화
        mDatabaseModel = new DatabaseModel(getApplication(), dbTypeLocal, remoteType, baseUrl);

        if (firebaseChildName != null && !firebaseChildName.isEmpty())
            databaseReference = FirebaseDatabase.getInstance().getReference().child(firebaseChildName);

    }

    /**
     * 이 메서드는 ViewModel이 더이상 사용되지 않고 파괴될 때 호출됩니다.
     * Realm 인스턴스같이 ViewModel이 어떤 데이터를 관찰하고 ViewModel이 새는 것을 막기 위해
     * 구독을 취소할 필요가 있는 경우 유용합니다.
     */
    @Override
    protected void onCleared() {
        QcLog.e("onCleared == ");
        if (mDatabaseModel != null)
            mDatabaseModel.onCleared();
        super.onCleared();
    }


    public void getAnswersFromRemoteResponse(int page) {
        if (mDatabaseModel != null)
            mDatabaseModel.getAnswersFromRemoteResponse(page, remoteDataListener);
    }

    /**
     * 리모트 데이터에서 가져온 결과 상태값 리스너
     *
     * 아답터 처리를 뷰모델에서 처리할지 고민중..
     */
    private RemoteDataListener<QcBaseItem> remoteDataListener = new RemoteDataListener<QcBaseItem>() {

        @Override
        public void onSuccess(int statusCode, boolean hasNextPage, QcBaseItem data) {
            QcLog.e("onSuccess == ");
            /**
             * 온라인 데이터를 가져왔다
             * 파이어베이스에 저장한다
             * 파이어베이스데이터는 연동되어 자동 업데이트된다
             *
             */
//            RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
//            if (mRetrofitFragment != null)
//                mRetrofitFragment.onSuccess(statusCode, hasNextPage, data);
            AnswersResponse answersResponse_ = (AnswersResponse)data;
            if (answersResponse_ != null && answersResponse_.getItemResponses() != null) {
                for (ItemResponse answer : answersResponse_.getItemResponses()) {
                    addFirebaseAnswer(answer);
                }
            }
        }

        @Override
        public void onError(int statusCode, String msg) {
            RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
            if (mRetrofitFragment != null)
                mRetrofitFragment.onError(statusCode, msg);
        }

        @Override
        public void onFailure(Throwable t, String msg) {
            RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
            if (mRetrofitFragment != null)
                mRetrofitFragment.onFailure(t, msg);
        }
    };


    public void addFirebaseAnswer(ItemResponse answer) {
        QcLog.e("addFirebaseAnswer == ");
//                User user = new User();
//                user.setId(new Random().nextInt(20) + "_id");
//                user.setName(new Random().nextInt(20) + "_name");
//                user.setLastName(new Random().nextInt(20) + "_lastname");
//                user.setAge(new Random().nextInt(20));
//                databaseReference.child("usersData").push().setValue(user);
//                databaseReference.child("usersData").child(user.id).setValue(user);
        if (databaseReference != null)
            databaseReference.child(answer.getAnswerId()+"").setValue(answer);

    }



    private static MutableLiveData<List<ItemResponse>> answersList = new MutableLiveData<List<ItemResponse>>();

    public static MutableLiveData<List<ItemResponse>> getAnswersList() {
        return answersList;
    }


    /**
     * 전체 데이터를 받아오는 경우
     * 최초에 한번만 호출?
     */
    public void subscribeFirebaseDatabase2() {
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<ItemResponse> answerList = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    ItemResponse answer = ds.getValue(ItemResponse.class);
                    if (answer != null) {
                        answerList.add(answer);
                    }
                }

                answersList.setValue(answerList);
//                if (answerList != null && answerList.size() > 0)
//                    mDatabaseModel.setAnswersResponseToRoom(answerList);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                QcLog.e("onCancelled user == " + error.toException());
            }
        };
        if (databaseReference != null) {
//            databaseReference.addValueEventListener(valueEventListener);
            databaseReference.addListenerForSingleValueEvent(valueEventListener);

        }
    }

    /**
     * 데이터 하나가 변경되는 경우
     */
    public void subscribeFirebaseDatabase() {
        ChildEventListener childEventListener = new ChildEventListener() {
            /**
             * 리스트의 아이템을 검색하거나 아이템의 추가가 있을때 수신합니다.
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                QcLog.e("onChildAdded user == "  );
//                User user = dataSnapshot.getValue(User.class);
                ItemResponse answer = dataSnapshot.getValue(ItemResponse.class);

//                List<ItemResponse> answerList = new ArrayList<>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    ItemResponse answer = ds.getValue(ItemResponse.class);
//                    if (answer != null) {
//                        answerList.add(answer);
//                    }
//                }
                if (answer != null) {
                    int index = 0;
                    boolean replace = false;
                    List<ItemResponse> oriAnswerList = answersList.getValue();
                    if (oriAnswerList == null) {
                        oriAnswerList = new ArrayList<>();
                        oriAnswerList.add(answer);
                    } else {
                        QcLog.e("onChildAdded 1111== " + oriAnswerList.size());
                        for (ItemResponse item : oriAnswerList) {
                            if (item.getAnswerId().equals(answer.getAnswerId())) {
                                QcLog.e("equals == " + index);
                                oriAnswerList.set(index, item);
                                replace = true;
                                break;
                            }
                            index++;
                        }
                        if (!replace) {
                            oriAnswerList.add(answer);
                        }
                    }

                    QcLog.e("onChildAdded 2222== " + oriAnswerList.size());
                    answersList.setValue(oriAnswerList);
//                RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
//                if (mRetrofitFragment != null)
//                    mRetrofitFragment.getAdapter().add(answer);
                }
            }

            /**
             * 아이템의 변화가 있을때 수신합니다.
             * @param dataSnapshot
             * @param s
             */
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                QcLog.e("onChildChanged == ");
                ItemResponse answer = dataSnapshot.getValue(ItemResponse.class);

                if (answer != null) {
                    List<ItemResponse> oriAnswerList = answersList.getValue();
                    QcLog.e("onChildRemoved 1111== " + oriAnswerList.size());
//                    answerList_.remove(answer);

                    int index = 0;
                    for (ItemResponse item : oriAnswerList) {
                        if (item.getAnswerId().equals(answer.getAnswerId())) {
                            QcLog.e("  remove == " + index);
                            oriAnswerList.set(index, answer);
                            break;
                        }
                        index++;
                    }

                    QcLog.e("onChildRemoved remove == " + oriAnswerList.size());
                    QcLog.e("onChildRemoved 2222== " + answersList.getValue().size());
                    answersList.setValue(oriAnswerList);
                    QcLog.e("onChildRemoved setValue== " + answersList.getValue().size());

//                RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
//                if (mRetrofitFragment != null)
//                    mRetrofitFragment.getAdapter().remove(answer);

                }
            }

            /**
             * 아이템이 삭제되었을때 수신합니다.
             * @param dataSnapshot
             */
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                QcLog.e("onChildRemoved == ");
                ItemResponse answer = dataSnapshot.getValue(ItemResponse.class);

                if (answer != null) {
                    List<ItemResponse> oriAnswerList = answersList.getValue();
                    QcLog.e("onChildRemoved 1111== " + oriAnswerList.size());
//                    answerList_.remove(answer);

                    int index = 0;
                    for (ItemResponse item : oriAnswerList) {
                        if (item.getAnswerId().equals(answer.getAnswerId())) {
                            QcLog.e("  remove == " + index);
                            oriAnswerList.remove(index);
                            break;
                        }
                        index++;
                    }

                    QcLog.e("onChildRemoved remove == " + oriAnswerList.size());
                    QcLog.e("onChildRemoved 2222== " + answersList.getValue().size());
                    answersList.setValue(oriAnswerList);
                    QcLog.e("onChildRemoved setValue== " + answersList.getValue().size());

//                RetrofitFirebaseFragment mRetrofitFragment = (RetrofitFirebaseFragment) qFrag;
//                if (mRetrofitFragment != null)
//                    mRetrofitFragment.getAdapter().remove(answer);

                }
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


//    public void addListDiffResult(final List<T> itemList_) {
//        if (itemList == null) {
//            this.itemList = itemList_;
//            notifyItemRangeInserted(0, itemList.size());
//        } else {
//            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return itemList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return itemList_.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
////                    return itemList.get(oldItemPosition).getAnswerId() ==
////                            itemList_.get(newItemPosition).getAnswerId();
//                    return itemList.get(oldItemPosition).equals(
//                            itemList_.get(newItemPosition));
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    T newItem = itemList_.get(newItemPosition);
//                    T oldItem = itemList.get(oldItemPosition);
//
//                    return oldItem.equals(newItem);
//                }
//
//
//                @Nullable
//                @Override
//                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
//                    // Implement method if you're going to use ItemAnimator
//                    return super.getChangePayload(oldItemPosition, newItemPosition);
//                }
//            });
//
//            this.itemList.clear();
//            this.itemList.addAll(itemList_);
//            diffResult.dispatchUpdatesTo(this);
//        }
//    }



    public void deleteUserDaoAsyncTask(final String userId) {
        new AsyncTask<Void, String, Integer>() {
            @Override
            protected Integer doInBackground(Void... params) {
                return mDatabaseModel.deleteUser(userId);
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                QcLog.e("deleteUser userId = " + userId + " , result = " + result);
                if (result == 1) {
                    QcToast.getInstance().show("delete user seccess !!", false);
                } else {
                    QcToast.getInstance().show("delete user fail !!", false);
                }
            }
        }.execute();

    }


}