package com.ulling.ullingmvpsample.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.ulling.ullingmvpsample.adapter.contract.ImageAdapterContract;
import com.ulling.ullingmvpsample.adapter.holder.ImageViewHolder;
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

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> implements ImageAdapterContract.Model, ImageAdapterContract.View {

    private Context context;
    private OnMyItemClickListener onItemClickListener;

    private ArrayList<ImageItem> imageItems;

    public ImageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public void addItems(ArrayList<ImageItem> imageItems) {
        this.imageItems = imageItems;
    }

    @Override
    public void clearItem() {
        if (imageItems != null) {
            imageItems.clear();
        }
    }

    @Override
    public void notifyAdapter() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return imageItems != null ? imageItems.size() : 0;
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(context, parent, onItemClickListener);
    }

    @Override
    public void setOnClickListener(OnMyItemClickListener clickListener) {
        this.onItemClickListener = clickListener;
    }

    @Override
    public ImageItem getItem(int position) {
        return imageItems.get(position);
    }

    @Override
    public void onBindViewHolder(final ImageViewHolder holder, int position) {
        if (holder == null) return;
        holder.onBind(getItem(position), position);
    }
}