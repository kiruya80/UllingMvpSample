package com.example.architecture.remotedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.architecture.common.ApiUrl;
import com.example.architecture.enty.retrofit.Item;
import com.example.architecture.enty.retrofit.SOAnswersResponse;
import com.ulling.lib.core.util.QcLog;

import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 온라인 데이터 통신 모듈
 * ㄴ retrofit2
 *
 * Created by P100651 on 2017-07-05.
 */
public class RetrofitRemoteData {

//    public static final String BASE_URL = "http://www.zoftino.com/api/";
    private static MutableLiveData<SOAnswersResponse> data = new MutableLiveData<SOAnswersResponse>();

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    //execute call back in background thread
                    .callbackExecutor(Executors.newSingleThreadExecutor())
                    .build();
        }
        return retrofit;
    }

    public static LiveData<SOAnswersResponse> getIntData() {
        QcLog.e("getIntData == ");
        return data;
    }


    public static void getSOAnswersResponse() {
        QcLog.e("PROCESSING IN THREAD BEFORE RETROFIT CALL " + Thread.currentThread().getName());
//        Call<StoreInfo> call = getRetrofitClient().create(StoreApi.class).getStoreInfo();

        Call<SOAnswersResponse> call = RetrofitRemoteData
                .getRetrofitClient(ApiUrl.BASE_URL)
                .create(GetAnswersApi.class).getAnswers();

        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
                //use postValue since it is running on background thread.
                if (response.isSuccessful()) {
                    QcLog.e("onResponse isSuccessful == ");
                    QcLog.e("getItems().size = " + response.body().getItems().size());
                    SOAnswersResponse si = response.body();
                    data.postValue(si);
//                    for (Item item : response.body().getItems()) {
//                        QcLog.e("item == " + item.toString());
//                    }
                } else {
                    int statusCode = response.code();
                    QcLog.e("onResponse == " + statusCode);
                    // handle request errors depending on status code
                }
                QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API");
            }
        });
    }
//
//    public static void getUserInfo(String userId) {
//        QcLog.e("PROCESSING IN THREAD BEFORE RETROFIT CALL " + Thread.currentThread().getName());
//        Call<User> call = getRetrofitClient().create(GetUserInfoApi.class).getUser(userId);
//
//        //rest service call runs on background thread and Callback also runs on background thread
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                User si = response.body();
//                //use postValue since it is running on background thread.
//                data.postValue(si);
//                QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                QcLog.e( "Error RETROFIT");
//            }
//        });
//    }

    /**
     * 비동기 호출하기
     *
     * call.enqueue(new Callback<SOAnswersResponse>() {
     */
    public void getAnswers() {

        Call<SOAnswersResponse> call = RetrofitRemoteData
                .getRetrofitClient(ApiUrl.BASE_URL)
                .create(GetAnswersApi.class).getAnswers();

        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<SOAnswersResponse>() {
            @Override
            public void onResponse(Call<SOAnswersResponse> call, Response<SOAnswersResponse> response) {
//                User si = response.body();
                //use postValue since it is running on background thread.

                if (response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
                    QcLog.e("onResponse isSuccessful == ");
                    QcLog.e("getItems().size = " + response.body().getItems().size());
                    for (Item item : response.body().getItems()) {
                        QcLog.e("item == " + item.toString());

                    }
                } else {
                    int statusCode = response.code();
                    QcLog.e("onResponse == " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<SOAnswersResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API");
            }
        });
    }
}
