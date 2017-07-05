package com.ulling.ullingmvpsample.view.presenter;


import android.content.Context;

import com.ulling.ullingmvpsample.adapter.contract.ImageAdapterContract;
import com.ulling.ullingmvpsample.data.ImageItem;
import com.ulling.ullingmvpsample.data.source.image.SampleImageRepository;
import com.ulling.ullingmvpsample.data.source.image.SampleImageSource;
import com.ulling.ullingmvpsample.listener.OnMyItemClickListener;

import java.util.ArrayList;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 6. 20.
 * @description :
 * @since :
 */

public class MainPresenter implements MainContract.Presenter, OnMyItemClickListener {

    private MainContract.View view;

    private ImageAdapterContract.Model adapterModel;
    private ImageAdapterContract.View adapterView;

    private SampleImageRepository sampleImageData;

    @Override
    public void attachView(MainContract.View view) {
        this.view = view;
    }

    @Override
    public void detachView() {
        view = null;
    }

    @Override
    public void setSampleImageData(SampleImageRepository sampleImageData) {
        this.sampleImageData = sampleImageData;
    }

    @Override
    public void loadItems(Context context, final boolean isClear) {
        sampleImageData.getImages(context, 10, new SampleImageSource.LoadImageCallback() {
            @Override
            public void onImageLoaded(ArrayList<ImageItem> list) {
                if (list != null) {
                    if (isClear) {
                        adapterModel.clearItem();
                    }
                    adapterModel.addItems(list);
                    adapterView.notifyAdapter();
                }
            }
        });


    }

    @Override
    public void setImageAdapterModel(ImageAdapterContract.Model adapterModel) {
        this.adapterModel = adapterModel;
    }

    @Override
    public void setImageAdapterView(ImageAdapterContract.View adapterView) {
        this.adapterView = adapterView;

        this.adapterView.setOnClickListener(this);
    }

    @Override
    public void onItemClick(int position) {
        ImageItem imageItem = adapterModel.getItem(position);
        view.showToast(imageItem.getTitle());
    }
}