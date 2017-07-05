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

public class SampleImageLocalDataSource implements SampleImageSource {

    @Override
    public void getImages(Context context, int size, LoadImageCallback loadImageCallback) {
        ArrayList<ImageItem> items = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            final int random = (int) (Math.random() * 15);
            final String name = String.format("sample_%02d", random);
            final int resource = context.getResources().getIdentifier(name, "drawable", context.getApplicationContext().getPackageName());
            items.add(new ImageItem(resource, name));
        }

        if (loadImageCallback != null) {
            loadImageCallback.onImageLoaded(items);
        }
    }
}