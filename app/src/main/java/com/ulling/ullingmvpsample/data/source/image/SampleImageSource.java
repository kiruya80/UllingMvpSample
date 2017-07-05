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

public interface SampleImageSource {

    interface LoadImageCallback {

        void onImageLoaded(ArrayList<ImageItem> list);
    }

    void getImages(Context context, int size, LoadImageCallback loadImageCallback);
}