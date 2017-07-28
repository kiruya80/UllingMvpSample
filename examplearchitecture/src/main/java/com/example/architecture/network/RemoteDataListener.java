package com.example.architecture.network;

import com.ulling.lib.core.entities.QcBaseResponse;

/**
 * Created by P100651 on 2017-07-27.
 */
public interface RemoteDataListener {
    void onSuccess(int statusCode, boolean hasNextPage, QcBaseResponse answers);
    void onError(int statusCode,String msg);
    void onFailure(Throwable t, String msg);

}

