package com.ulling.ullingmvpsample.view.presenter;

import android.content.Context;

import com.ulling.ullingmvpsample.adapter.contract.ImageAdapterContract;
import com.ulling.ullingmvpsample.data.source.image.SampleImageRepository;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 6. 20.
 * @description :
 * @since :
 */

public interface MainContract {

    interface View {

        void showToast(String title);
    }

    interface Presenter {

        void attachView(View view);

        void setImageAdapterModel(ImageAdapterContract.Model adapterModel);

        void setImageAdapterView(ImageAdapterContract.View adapterView);

        void detachView();

        void setSampleImageData(SampleImageRepository sampleImageData);

        void loadItems(Context context, boolean isClear);
    }
}