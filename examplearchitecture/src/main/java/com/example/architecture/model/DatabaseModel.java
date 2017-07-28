package com.example.architecture.model;

import android.arch.lifecycle.LiveData;
import android.content.Context;

import com.example.architecture.common.QcDefine;
import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.entities.retrofit.OwnerResponse;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.entities.room.AnswerDao;
import com.example.architecture.entities.room.Owner;
import com.example.architecture.entities.room.User;
import com.example.architecture.entities.room.UserDao;
import com.example.architecture.localdb.DatabaseCreator;
import com.example.architecture.localdb.RoomLocalData;
import com.example.architecture.network.RemoteDataListener;
import com.example.architecture.network.RemoteDataObserver;
import com.example.architecture.remotedb.RetrofitRemoteData;
import com.ulling.lib.core.entities.QcBaseResponse;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit2.Retrofit;

/**
 * 뷰모델에서 데이터 요청시
 * 상태값 또는 로컬디비 체크후 없는 경우 온라인 데이터 요청
 * 가져온 데이터는 라이브데이터에 저장
 * <p>
 * <p>
 * 실데이터를 관리하는 모델클래스
 * 통신모듈 교체가능하게 만들기
 * <p>
 * 데이터 가공하여 Room입력
 * <p>
 * <p>
 * Created by P100651 on 2017-07-05.
 */
public class DatabaseModel {
    private Executor executor = Executors.newFixedThreadPool(QcDefine.THREAD_POOL_NUM);
    /**
     * local db type
     */
    public static final int DB_TYPE_LOCAL_ROOM = 0;
    /**
     * remote type
     */
    public static final int REMOTE_TYPE_RETROFIT = 0;
    private final Context qCtx;
    private int localDbType = DB_TYPE_LOCAL_ROOM;
    private int remoteType = REMOTE_TYPE_RETROFIT;
    private RoomLocalData localData;
    private RetrofitRemoteData retrofitRemoteData;
    //    private RetrofitRemoteData remoteData;
    //    private final PersonDAO personDAO;
    private UserDao userDao = null;
    private AnswerDao answerDao = null;
    private LiveData<AnswersResponse> answers = new AnswersResponse();

    public DatabaseModel(Context context, int localDbType, int remoteType, String baseUrl) {
        this.qCtx = context;
        this.localDbType = localDbType;
        this.remoteType = remoteType;
        initLocalDb();
        if (baseUrl != null && !"".equals(baseUrl))
            initRemoteDb(baseUrl);
        if (localData != null)
            userDao = localData.userDatabase();
        if (localData != null)
            answerDao = localData.answerDatabase();
    }

//    public DatabaseModel(Context context, int nThreads, int localDbType, int remoteType, String baseUrl) {
//        this.qCtx = context;
//        this.executor = Executors.newFixedThreadPool(QcDefine.THREAD_POOL_NUM);
//        this.localDbType = localDbType;
//        this.remoteType = remoteType;
//        initLocalDb();
//        if (baseUrl != null && !"".equals(baseUrl))
//            initRemoteDb(baseUrl);
//        if (localData != null)
//            userDao = localData.userDatabase();
//        if (localData != null)
//            answerDao = localData.answerDatabase();
//    }

    private void initLocalDb() {
        if (localDbType == DB_TYPE_LOCAL_ROOM) {
            localData = DatabaseCreator.getRoomLocalData(qCtx);
        }
    }

    private void initRemoteDb(String baseUrl) {
        if (remoteType == REMOTE_TYPE_RETROFIT) {
            retrofitRemoteData = RetrofitRemoteData.getInstance(baseUrl);
            retrofitRemoteData.registerAdapterDataObserver(mObservable);
            Retrofit retrofit = retrofitRemoteData.getRetrofitClient();
//            getAnswersApi = retrofit.create(GetAnswersApi.class);
//            RetrofitRemoteData.getRetrofitClient(baseUrl);
//            getAnswersApi = RetrofitRemoteData
//                    .getRetrofitClient(ApiUrl.BASE_URL)
//                    .create(GetAnswersApi.class);
        }
    }

    public void onCleared() {
        QcLog.e("onCleared == ");
        if (localData != null && localDbType == DB_TYPE_LOCAL_ROOM)
            localData.close();
    }

    /**
     * userDao
     */
    public long insertUser(User u) {
        if (userDao != null) {
            long resultIndex = userDao.insertUser(u);
            QcLog.e("insertUser " + resultIndex);
            return resultIndex;
        } else {
            return -1;
        }
    }

    public int deleteUser(String userId) {
        if (userDao != null) {
            int rec = userDao.deleteUser(userId);
            QcLog.e("deleteUser = " + rec);
            return rec;
        } else {
            return -1;
        }
    }
//    public LiveData<User> getUserInfo(int userId) {
//        if (userDao != null) {
//            return userDao.loadUserById(userId);
//        } else {
//            return null;
//        }
//    }

    public LiveData<List<User>> getAllUsers() {
        if (userDao != null) {
            return userDao.getAllUsers();
        } else {
            return null;
        }
    }
    /**
     * answerDao
     */
    /**
     * @param answer
     * @return
     */
    public long insertAnswer(Answer answer) {
        if (answerDao != null) {
            long resultIndex = answerDao.insertAnswer(answer);
            QcLog.e("insertUser " + resultIndex);
            return resultIndex;
        } else {
            return -1;
        }
    }

    public long[] insertMultipleAnswer(Answer... answers) {
        if (answerDao != null) {
            long[] resultIndex = answerDao.insertMultipleAnswer(answers);
            QcLog.e("insertUser " + resultIndex);
            return resultIndex;
        } else {
            return null;
        }
    }

    public long[] insertMultipleListAnswer(List<Answer> answers) {
        if (answerDao != null) {
            long[] resultIndex = answerDao.insertMultipleListAnswer(answers);
            QcLog.e("insertUser " + resultIndex);
            return resultIndex;
        } else {
            return null;
        }
    }

    public LiveData<List<Answer>> getAllAnswerFromRoom() {
        QcLog.e("getAllAnswerFromRoom === ");
        if (answerDao != null) {
            return answerDao.getAllAnswer();
        } else {
            return null;
        }
    }

//    public int deleteAnswer(int answerId) {
//        if (answerDao != null) {
//            int rec = answerDao.deleteAnswer(answerId);
//            QcLog.e("deleteUser = " + rec);
//            return rec;
//        } else {
//            return -1;
//        }
//    }

    public int deleteAllAnswer() {
        if (answerDao != null) {
            int rec = answerDao.deleteAll();
            QcLog.e("deleteUser = " + rec);
            return rec;
        } else {
            return -1;
        }
    }


    public LiveData<AnswersResponse> getAnswersFromRemoteResponse() {
        return RetrofitRemoteData.getAnswersResponse();
    }


    public void getAnswersFromRemoteResponse(int page, RemoteDataListener remoteDataListener) {
        retrofitRemoteData.getAnswersResponse(page, remoteDataListener);
    }

    public void getAnswersFromRemote(final int page) {
        retrofitRemoteData.getAnswers(page, new RemoteDataListener() {
            @Override
            public void onSuccess(int statusCode, boolean hasNextPage, QcBaseResponse answers) {
                QcLog.e("success = " + statusCode);
                if (answers != null) {
                    AnswersResponse answersResponse = (AnswersResponse) answers;
                    if (answersResponse != null)
                    getAnswersResponse(page, answersResponse);
                }
            }

            @Override
            public void onError(int statusCode, String msg) {
                QcLog.e("onError = " + msg.toString());
            }

            @Override
            public void onFailure(Throwable t, String msg) {
                QcLog.e("onFailure = " + t.toString());
            }
        });
    }

    private void getAnswersResponse(final int page, AnswersResponse answersResponse) {
        /**
         * get data -> insert
         */
        List<ItemResponse> itemResponses = answersResponse.getItemResponses();
        List<Answer> answers = new ArrayList<Answer>();
        for (ItemResponse item : itemResponses) {
            Answer answer = new Answer();
            answer.setAnswerId(item.getAnswerId());
            answer.setLastPage(page);
            answer.setQuestionId(item.getQuestionId());
            OwnerResponse ownerResponse = item.getOwnerResponse();
            Owner owner = new Owner();
            owner.setReputation(ownerResponse.getReputation());
            owner.setUserId(ownerResponse.getUserId());
            owner.setUserType(ownerResponse.getUserType());
            if (ownerResponse.getAcceptRate() != null)
                owner.setAcceptRate(ownerResponse.getAcceptRate());
            owner.setProfileImage(ownerResponse.getProfileImage());
            owner.setDisplayName(ownerResponse.getDisplayName());
            owner.setLink(ownerResponse.getLink());
            answer.setOwner(owner);
            answer.setAccepted(item.getIsAccepted());
            answer.setScore(item.getScore());
            answer.setLastActivityDate(item.getLastActivityDate());
            if (item.getLastEditDate() != null)
                answer.setLastEditDate(item.getLastEditDate());
            answer.setCreationDate(item.getCreationDate());
            answer.setHasMore(answersResponse.getHasMore());
            answers.add(answer);
        }
        if (answers != null && answers.size() > 0)
            insertAnswersToRoom(answers);
    }
















//    public void getAnswers(boolean isRemote) {
//        if (isRemote) {
//            Call<AnswersResponse> call = RetrofitRemoteData
//                    .getRetrofitClient(ApiUrl.BASE_URL)
//                    .create(GetAnswersApi.class).getAnswers();
//            //rest service call runs on background thread and Callback also runs on background thread
//            call.enqueue(new Callback<AnswersResponse>() {
//                @Override
//                public void onResponse(Call<AnswersResponse> call, Response<AnswersResponse> response) {
//                    //use postValue since it is running on background thread.
//                    if (response.isSuccessful()) {
//                        QcLog.e("onResponse isSuccessful == ");
//                        QcLog.e("getItems().size = " + response.body().getItemResponses().size());
//                        AnswersResponse answersResponse = response.body();
////                        data.postValue(si);
//                        /**
//                         * get data -> insert
//                         */
//                        List<ItemResponse> itemResponses = answersResponse.getItemResponses();
//                        List<Answer> answers = new ArrayList<Answer>();
//                        for (ItemResponse item : itemResponses) {
//                            Answer answer = new Answer();
//                            answer.setAnswerId(item.getAnswerId());
//                            answer.setQuestionId(item.getQuestionId());
//                            OwnerResponse ownerResponse = item.getOwnerResponse();
//                            Owner owner = new Owner();
//                            owner.setReputation(ownerResponse.getReputation());
//                            owner.setUserId(ownerResponse.getUserId());
//                            owner.setUserType(ownerResponse.getUserType());
//                            if (ownerResponse.getAcceptRate() != null)
//                                owner.setAcceptRate(ownerResponse.getAcceptRate());
//                            owner.setProfileImage(ownerResponse.getProfileImage());
//                            owner.setDisplayName(ownerResponse.getDisplayName());
//                            owner.setLink(ownerResponse.getLink());
//                            answer.setOwner(owner);
//                            answer.setAccepted(item.getIsAccepted());
//                            answer.setScore(item.getScore());
//                            answer.setLastActivityDate(item.getLastActivityDate());
//                            if (item.getLastEditDate() != null)
//                                answer.setLastEditDate(item.getLastEditDate());
//                            answer.setCreationDate(item.getCreationDate());
//                            answers.add(answer);
//                        }
//                        if (answers != null && answers.size() > 0)
//                            insertAnswersToRoom(answers);
//                    } else {
//                        int statusCode = response.code();
//                        QcLog.e("onResponse == " + statusCode);
//                        // handle request errors depending on status code
//                    }
//                    QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
//                }
//
//                @Override
//                public void onFailure(Call<AnswersResponse> call, Throwable t) {
//                    QcLog.e("onFailure error loading from API");
//                }
//            });
//        } else {
//        }
//    }
    private RemoteDataObserver mObservable = new RemoteDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            QcLog.e("onChanged = ");
        }
    };

    public void insertAnswersToRoom(final List<Answer> answers) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (answerDao != null) {
                    long[] resultIndex = answerDao.insertMultipleListAnswer(answers);
                    QcLog.e("addAnswer resultIndex == " + resultIndex);
                    QcToast.getInstance().show("addAnswer seccess !! index = " + resultIndex, false);
                }
            }
        });
    }

    public void deleteAnswerFromRoom() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (answerDao != null) {
                    int resultIndex = answerDao.deleteAll();
                    QcLog.e("deleteAnswer resultIndex == " + resultIndex);
                    QcToast.getInstance().show("deleteAnswer seccess !! index = " + resultIndex, false);
                }
            }
        });
    }

    public void getAnswers() {
//        RetrofitRemoteData.getAnswersResponse();
//        if (getAnswersApi != null)
//            getAnswersApi.getAnswers().enqueue(new Callback<SOAnswersResponse>() {
//                @Override
//                public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//
//                    if (response.isSuccessful()) {
////                    mAdapter.updateAnswers(response.body().getItems());
//                        QcLog.e("onResponse isSuccessful == ");
//                        QcLog.e("getItems().size = " + response.body().getItems().size());
//                        for (Item item : response.body().getItems()) {
//                            QcLog.e("item == " + item.toString());
//                        }
//                        if (response.body() != null)
//                        answers = response.body();
//                    } else {
//                        int statusCode = response.code();
//                        QcLog.e("onResponse == " + statusCode);
//                        // handle request errors depending on status code
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
////                showErrorMessage();
//                    QcLog.e("onFailure error loading from API");
//
//                }
//            });
    }

//    public void addPerson(Person p) {
//        long rec =  personDAO.insertPerson(p);
//        Log.d("db insert ","added "+rec);
//    }
//
//    public void updatePerson(Person p) {
//        personDAO.updatePerson(p);
//    }
//    private LiveData<User> user;
//    private LiveData<List<User>> userList;
//    private Application mApplication;
//
////    public DatabaseModel(Application application) {
////        mApplication = application;
////    }
//
//    public RoomLocalData getlocalData() {
//        return localData;
//    }
//
//    public void initLocalRoom( ) {
//        localData = RoomLocalData.getInMemoryDatabase(mApplication);
//    }
//    public void initRemoteRetrofit() {
//        RetrofitRemoteData.getRetrofitClient();
//    }
//
//    public LiveData<User> getUserInfo(boolean local, String userId) {
//        LiveData<User> user = null;
//        if (local) {
//            user = localData.userModel().load(userId);
//        } else {
////            MutableLiveData<User> data = RetrofitRemoteData.getUserInfo(userId);
//        }
////        // Instead of exposing the list of Loans, we can apply a transformation and expose Strings.
////        // 옵져버에게 전달하기전 데이터 가공
////        LiveData<String> userResult = Transformations.map(user,
////                new Function<User, String>() {
////                    @Override
////                    public String apply(User loansWithUserAndBook) {
////                        QcLog.e("apply == ");
////                        StringBuilder sb = new StringBuilder();
//////                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm",
//////                                Locale.US);
//////                        for (LoanWithUserAndBook loan : loansWithUserAndBook) {
//////                            sb.append(String.format("%s\n  (Returned: %s)\n",
//////                                    loan.bookTitle,
//////                                    simpleDateFormat.format(loan.endTime)));
//////                        }
////                        return sb.toString();
////                    }
////                });
//        return user;
//    }
}
