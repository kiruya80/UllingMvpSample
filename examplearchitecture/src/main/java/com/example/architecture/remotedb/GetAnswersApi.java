package com.example.architecture.remotedb;

import com.example.architecture.entities.retrofit.AnswersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by P100651 on 2017-07-04.
 */
public interface GetAnswersApi {

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<AnswersResponse> getAnswers();

    @GET("/answers?order=desc&sort=activity&site=stackoverflow")
    Call<AnswersResponse> getAnswers(@Query("tagged") String tags);
}