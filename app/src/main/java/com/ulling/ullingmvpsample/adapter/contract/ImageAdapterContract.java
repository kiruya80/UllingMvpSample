package com.ulling.ullingmvpsample.adapter.contract;

import com.ulling.ullingmvpsample.data.ImageItem;
import com.ulling.ullingmvpsample.listener.OnMyItemClickListener;

import java.util.ArrayList;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 6. 20.
 * @description :
 * @since :
 */

public interface ImageAdapterContract {


    interface View {

        void setOnClickListener(OnMyItemClickListener clickListener);

        void notifyAdapter();
    }

    interface Model {

        void addItems(ArrayList<ImageItem> imageItems);

        void clearItem();

        ImageItem getItem(int position);
    }
}
