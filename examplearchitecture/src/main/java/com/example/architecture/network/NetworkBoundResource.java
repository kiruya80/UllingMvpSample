package com.example.architecture.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;

public abstract class NetworkBoundResource<ResultType, RequestType> {
    private final MediatorLiveData<Resource<ResultType>> result = new MediatorLiveData<>();

//    @MainThread
//    NetworkBoundResource() {
//        result.setValue(Resource.loading(null));
//        LiveData<ResultType> dbSource = loadFromDb();
//        result.addSource(dbSource, data -> {
//            result.removeSource(dbSource);
//            if (shouldFetch(data)) {
//                fetchFromNetwork(dbSource);
//            } else {
//                result.addSource(dbSource, new Observer<ResultType>() {
//                            @Override
//                            public void onChanged(@Nullable ResultType newData) {
//                                result.setValue(Resource.success(newData));
//                            }
//                        });
//
//            }
//        });
//    }
//
//    private void fetchFromNetwork(final LiveData<ResultType> dbSource) {
//        LiveData<ApiResponse<RequestType>> apiResponse = createCall();
//        // we re-attach dbSource as a new source,
//        // it will dispatch its latest value quickly
//        result.addSource(dbSource,
//                newData -> result.setValue(Resource.loading(newData)));
//        result.addSource(apiResponse, response -> {
//            result.removeSource(apiResponse);
//            result.removeSource(dbSource);
//            //noinspection ConstantConditions
//            if (response.isSuccessful()) {
//                saveResultAndReInit(response);
//            } else {
//                onFetchFailed();
//                result.addSource(dbSource, new Observer<ResultType>() {
//                            @Override
//                            public void onChanged(@Nullable ResultType resultType) {
//                                newData -> result.setValue(
//                                        Resource.error(response.errorMessage, newData));
//                            }
//                        });
//
//            }
//        });
//    }
//
//    @MainThread
//    private void saveResultAndReInit(ApiResponse<RequestType> response) {
//        new AsyncTask<Void, Void, Void>() {
//
//            @Override
//            protected Void doInBackground(Void... voids) {
//                saveCallResult(response.body);
//                return null;
//            }
//
//            @Override
//            protected void onPostExecute(Void aVoid) {
//                // we specially request a new live data,
//                // otherwise we will get immediately last cached value,
//                // which may not be updated with latest results received from network.
//                result.addSource(loadFromDb(),
//                        newData -> result.setValue(Resource.success(newData)));
//            }
//        }.execute();
//    }
}