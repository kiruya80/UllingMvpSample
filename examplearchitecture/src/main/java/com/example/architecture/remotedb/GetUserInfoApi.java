package com.example.architecture.remotedb;

import com.example.architecture.enty.room.User;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by P100651 on 2017-07-04.
 */
public interface GetUserInfoApi {
    /**
     * @GET declares an HTTP GET request
     * @Path("user") annotation on the userId parameter marks it as a
     * replacement for the {user} placeholder in the @GET path
     */
    @GET("/users/{user}")
    Call<User> getUser(@Path("user") String userId);
}