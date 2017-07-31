package com.example.architecture.network;

/**
 * Created by P100651 on 2017-07-27.
 */
public interface RemoteDataListener<T>  {
    void onSuccess(int statusCode, boolean hasNextPage, T data);
    void onError(int statusCode, String msg);
    void onFailure(Throwable t, String msg);

}

