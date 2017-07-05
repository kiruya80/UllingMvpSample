package com.example.architecture.remotedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.architecture.enty.User;
import com.ulling.lib.core.util.QcLog;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * 온라인 데이터 통신 모듈
 * ㄴ retrofit2
 *
 * Created by P100651 on 2017-07-05.
 */
public class RetrofitRemoteData {

    public static final String BASE_URL = "http://www.zoftino.com/api/";
    private static MutableLiveData<User> data = new MutableLiveData<User>();

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create())
                    //execute call back in background thread
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
        }
        return retrofit;
    }

    public static LiveData<User> getIntData() {
        return data;
    }

    public static void getUserInfo(String userId) {
        QcLog.e("PROCESSING IN THREAD BEFORE RETROFIT CALL " + Thread.currentThread().getName());
        Call<User> call = getRetrofitClient().create(GetUserInfoApi.class).getUser(userId);

        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User si = response.body();
                //use postValue since it is running on background thread.
                data.postValue(si);
                QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                QcLog.e( "Error RETROFIT");
            }
        });
    }
}
