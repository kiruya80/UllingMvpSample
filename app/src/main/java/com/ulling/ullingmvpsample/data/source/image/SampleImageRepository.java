package com.ulling.ullingmvpsample.data.source.image;

import android.content.Context;

import com.ulling.ullingmvpsample.data.ImageItem;

import java.util.ArrayList;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 6. 20.
 * @description :
 * @since :
 */


public class SampleImageRepository implements SampleImageSource {

    private static SampleImageRepository sampleImageRepository;

    public static SampleImageRepository getInstance() {
        if (sampleImageRepository == null) {
            sampleImageRepository = new SampleImageRepository();
        }
        return sampleImageRepository;
    }

    private SampleImageLocalDataSource sampleImageLocalDataSource;

    private SampleImageRepository() {
        sampleImageLocalDataSource = new SampleImageLocalDataSource();
    }

    @Override
    public void getImages(Context context, int size, final LoadImageCallback loadImageCallback) {
        sampleImageLocalDataSource.getImages(context, size, new LoadImageCallback() {
            @Override
            public void onImageLoaded(ArrayList<ImageItem> list) {
                if (loadImageCallback != null) {
                    loadImageCallback.onImageLoaded(list);
                }
            }
        });
    }
}