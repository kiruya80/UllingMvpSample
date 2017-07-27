package com.example.architecture.remotedb;

import android.arch.lifecycle.MutableLiveData;
import android.database.Observable;

import com.example.architecture.common.ApiUrl;
import com.example.architecture.entities.retrofit.AnswersResponse;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.network.RemoteDataListener;
import com.example.architecture.network.RemoteDataObserver;
import com.ulling.lib.core.util.QcLog;

import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 온라인 데이터 통신 모듈
 * ㄴ retrofit2
 * <p>
 * Created by P100651 on 2017-07-05.
 */
public class RetrofitRemoteData {
    private static RetrofitRemoteData SINGLE_U;
    private static Retrofit retrofit = null;
    private final RemoteDataObservable mObservable = new RemoteDataObservable();


    private GetAnswersApi getAnswersApi;
    /**
     * observer 패턴
     */

    public void registerAdapterDataObserver(RemoteDataObserver observer) {
        mObservable.registerObserver(observer);
    }

    public void unregisterAdapterDataObserver(RemoteDataObserver observer) {
        mObservable.unregisterObserver(observer);
    }

    static class RemoteDataObservable extends Observable<RemoteDataObserver> {
        public boolean hasObservers() {
            return !mObservers.isEmpty();
        }

        public void notifyChanged() {
            for (int i = mObservers.size() - 1; i >= 0; i--) {
                mObservers.get(i).onChanged();
            }
        }
    }

    public static synchronized RetrofitRemoteData getInstance() {
        if (SINGLE_U == null) {
            SINGLE_U = new RetrofitRemoteData();
        }
        return SINGLE_U;
    }

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

    public void setGetAnswersApi() {
        getAnswersApi = retrofit.create(GetAnswersApi.class);
    }

    // http://chuumong.github.io/android/2017/02/22/MVP-Dagger-RxJava-Retrofit을-사용한-간단한-Android-App
    private Retrofit getClient(String baseUrl) {
        /**
         //        Cache cache = null;
         //        try {
         //            cache = new Cache(cacheFile, 10 * 1024 * 1024);
         //        } catch (Exception e) {
         //            e.printStackTrace();
         //        }

         OkHttpClient okHttpClient = new OkHttpClient.Builder()
         .addInterceptor(new Interceptor() {
        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        // Customize the request
        Request request = original.newBuilder()
        .header("Content-Type", "application/json")
        .removeHeader("Pragma")
        //                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
        .build();

        okhttp3.Response response = chain.proceed(request);
        response.cacheResponse();
        // Customize or return the response
        return response;
        }
        })
         //                .cache(cache)
         .build();

         **/
        /**
         HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
         logging.setLevel(HttpLoggingInterceptor.Level.BODY);

         OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
         httpClient.addInterceptor(logging);
         httpClient.networkInterceptors().add(new Interceptor() {
         //        httpClient.addInterceptor(new Interceptor() {
        @Override public okhttp3.Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder requestBuilder = original.newBuilder();
        requestBuilder.method(original.method(), original.body());
        //                Iterator<String> keys = HEADERS.keySet().iterator();
        //                while (keys.hasNext()) {
        //                    String key = keys.next();
        //                    requestBuilder.addHeader(key, HEADERS.get(key));
        //                     QcLog.e("  Key : " + key + " /// Value " + HEADERS.get(key));
        //                }

        return chain.proceed(requestBuilder.build());
        }
        });
         OkHttpClient client = httpClient.build();
         **/
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(getBaseURL())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
        return retrofit;
    }






    public void getAnswers(int page, final RemoteDataListener remoteDataListener) {
        Call<AnswersResponse> call = getRetrofitClient(ApiUrl.BASE_URL)
                .create(GetAnswersApi.class).getAnswers();
        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<AnswersResponse>() {
            @Override
            public void onResponse(Call<AnswersResponse> call, Response<AnswersResponse> response) {
                //use postValue since it is running on background thread.
                if (response.isSuccessful()) {
                    QcLog.e("onResponse isSuccessful == ");
                    QcLog.e("getItems().size = " + response.body().getItemResponses().size());
                    AnswersResponse answersResponse = response.body();
                    remoteDataListener.onSuccess(1, answersResponse);
                } else {
                    int statusCode = response.code();
                    QcLog.e("onResponse == " + statusCode);
                    remoteDataListener.onError(statusCode, "");
                    // handle request errors depending on status code
                }
                QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<AnswersResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API");
                remoteDataListener.onFailure(t, "");
            }
        });
    }












    private static MutableLiveData<AnswersResponse> answersResponse = new MutableLiveData<AnswersResponse>();

    public static MutableLiveData<AnswersResponse> getAnswersResponse() {
        return answersResponse;
    }

    public void getAnswersResponse(int page) {
        QcLog.e("PROCESSING IN THREAD BEFORE RETROFIT CALL " + Thread.currentThread().getName());
//        Call<StoreInfo> call = getRetrofitClient().create(StoreApi.class).getStoreInfo();
        Call<AnswersResponse> call = RetrofitRemoteData
                .getRetrofitClient(ApiUrl.BASE_URL)
                .create(GetAnswersApi.class).getAnswers();
        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<AnswersResponse>() {
            @Override
            public void onResponse(Call<AnswersResponse> call, Response<AnswersResponse> response) {
                //use postValue since it is running on background thread.
                if (response.isSuccessful()) {
                    QcLog.e("onResponse isSuccessful == ");
                    QcLog.e("getItems().size = " + response.body().getItemResponses().size());
                    AnswersResponse si = response.body();
                    answersResponse.postValue(si);
//                    data.setValue(si);
//                    for (Item item : response.body().getItems()) {
//                        QcLog.e("item == " + item.toString());
//                    }
                    mObservable.notifyChanged();
                } else {
                    int statusCode = response.code();
                    QcLog.e("onResponse == " + statusCode);
                    // handle request errors depending on status code
                }
                QcLog.e("PROCESSING IN THREAD IN RETROFIT RESPONSE HANDLER " + Thread.currentThread().getName());
            }

            @Override
            public void onFailure(Call<AnswersResponse> call, Throwable t) {
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
     * <p>
     * call.enqueue(new Callback<SOAnswersResponse>() {
     */
    public void getAnswers() {
        Call<AnswersResponse> call = RetrofitRemoteData
                .getRetrofitClient(ApiUrl.BASE_URL)
                .create(GetAnswersApi.class).getAnswers();
        //rest service call runs on background thread and Callback also runs on background thread
        call.enqueue(new Callback<AnswersResponse>() {
            @Override
            public void onResponse(Call<AnswersResponse> call, Response<AnswersResponse> response) {
//                User si = response.body();
                //use postValue since it is running on background thread.
                if (response.isSuccessful()) {
//                    mAdapter.updateAnswers(response.body().getItems());
                    QcLog.e("onResponse isSuccessful == ");
                    QcLog.e("getItems().size = " + response.body().getItemResponses().size());
                    for (ItemResponse itemResponse : response.body().getItemResponses()) {
                        QcLog.e("item == " + itemResponse.toString());
                    }
                } else {
                    int statusCode = response.code();
                    QcLog.e("onResponse == " + statusCode);
                    // handle request errors depending on status code
                }
            }

            @Override
            public void onFailure(Call<AnswersResponse> call, Throwable t) {
                QcLog.e("onFailure error loading from API");
            }
        });
    }
}
