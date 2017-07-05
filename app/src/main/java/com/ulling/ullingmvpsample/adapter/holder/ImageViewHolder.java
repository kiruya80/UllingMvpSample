package com.ulling.ullingmvpsample.adapter.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ulling.ullingmvpsample.R;
import com.ulling.ullingmvpsample.data.ImageItem;
import com.ulling.ullingmvpsample.listener.OnMyItemClickListener;
import com.ulling.ullingmvpsample.util.ImageAsync;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 6. 20.
 * @description :
 * @since :
 */

public class ImageViewHolder extends RecyclerView.ViewHolder {

    private Context context;

    private OnMyItemClickListener onItemClickListener;

    @BindView(R.id.img_view)
    ImageView imageView;

    public ImageViewHolder(Context context, ViewGroup parent, OnMyItemClickListener onItemClickListener) {
        super(LayoutInflater.from(context).inflate(R.layout.item_image, parent, false));

        this.context = context;
        this.onItemClickListener = onItemClickListener;

        ButterKnife.bind(this, itemView);
    }

    public void onBind(ImageItem item, final int position) {
        itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        new ImageAsync(context, imageView).execute(item.getImageRes());
    }
}