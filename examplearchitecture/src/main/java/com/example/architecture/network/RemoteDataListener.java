package com.example.architecture.network;

import com.ulling.lib.core.entities.QcCommonResponse;

/**
 * Created by P100651 on 2017-07-27.
 */
public interface RemoteDataListener {
    void onSuccess(int statusCode, QcCommonResponse answers);
    void onError(int statusCode,String msg);
    void onFailure(Throwable t, String msg);

}

