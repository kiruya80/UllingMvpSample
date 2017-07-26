package com.example.architecture.remotedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.architecture.entities.room.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by P100651 on 2017-07-04.
 *
 * 서로 다른 데이터 소스 사이의 중재자 (지속적인 모델, 웹 서비스, 캐시 등)을 고려할 수 있습니다.
 * 데이터 가져오기
 * 개발은 정보만 가져오기
 *
 * 데이터에 따른 분기처리?
 *
 * 모델에서 호출함
 * 기져온 데이터는 livedata에 담아 옵져버로 전달 애기티비에서 업데이트뷰를 한다
 *
 */
public class UserModel {
    private GetUserInfoApi getUserInfo;

    public LiveData<User> getUser(String userId) {
        // This is not an optimal implementation, we'll fix it below
        final MutableLiveData<User> data = new MutableLiveData<>();
        getUserInfo.getUser(userId).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // error case is left out for brevity
                data.setValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
        return data;
    }
/**
 * 2
 */
//    private UserCache userCache;
//    public LiveData<User> getUser(String userId) {
//        LiveData<User> cached = userCache.get(userId);
//        if (cached != null) {
//            return cached;
//        }
//
//        final MutableLiveData<User> data = new MutableLiveData<>();
//        userCache.put(userId, data);
//        // this is still suboptimal but better than before.
//        // a complete implementation must also handle the error cases.
//        webservice.getUser(userId).enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                data.setValue(response.body());
//            }
//        });
//        return data;
//    }
/**
 * 3
 */
//    private final UserDao userDao;
//    private final Executor executor;
//
//    @Inject
//    public UserRepository(Webservice webservice, UserDao userDao, Executor executor) {
//        this.webservice = webservice;
//        this.userDao = userDao;
//        this.executor = executor;
//    }
//
//    public LiveData<User> getUser(String userId) {
//        refreshUser(userId);
//        // return a LiveData directly from the database.
//        return userDao.load(userId);
//    }
//
//    private void refreshUser(final String userId) {
//        executor.execute(() -> {
//            // running in a background thread
//            // check if user was fetched recently
//            boolean userExists = userDao.hasUser(FRESH_TIMEOUT);
//            if (!userExists) {
//                // refresh the data
//                Response response = webservice.getUser(userId).execute();
//                // TODO check for error etc.
//                // Update the database.The LiveData will automatically refresh so
//                // we don't need to do anything else here besides updating the database
//                userDao.save(response.body());
//            }
//        });
//    }

}