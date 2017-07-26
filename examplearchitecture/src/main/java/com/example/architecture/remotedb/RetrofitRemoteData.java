package com.example.architecture.remotedb;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.example.architecture.common.ApiUrl;
import com.example.architecture.enty.retrofit.ItemResponse;
import com.example.architecture.enty.retrofit.AnswersResponse;
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
 *
 * Created by P100651 on 2017-07-05.
 */
public class RetrofitRemoteData {

//    public static final String BASE_URL = "http://www.zoftino.com/api/";
    private static MutableLiveData<AnswersResponse> data = new MutableLiveData<AnswersResponse>();

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
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
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
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
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


    public static LiveData<AnswersResponse> getIntData() {
        QcLog.e("getIntData == ");
        return data;
    }


    public static void getSOAnswersResponse() {
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
                    data.postValue(si);
//                    data.setValue(si);
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
     *
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
