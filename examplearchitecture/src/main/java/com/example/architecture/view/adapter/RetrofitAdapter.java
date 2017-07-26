package com.example.architecture.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.architecture.R;
import com.example.architecture.databinding.RowRetrofitBinding;
import com.example.architecture.enty.retrofit.ItemResponse;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import java.util.List;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 18.
 * @description :
 * @since :
 * <p>
 * https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 *
 *  http://realignist.me/code/2016/05/25/data-binding-guide.html
 */
public class RetrofitAdapter extends RecyclerView.Adapter<QcBaseViewHolder> {
    private List<ItemResponse> mItemResponses;
    private Context qCon;
    private PostItemListener mItemListener;
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_retrofit, viewGroup, false);
//        return new QcBaseViewHolder(v);
//        ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
        //or
        RowRetrofitBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_retrofit, viewGroup, false);
        return new QcBaseViewHolder(viewBinding) {
        };
    }

    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        // BindingHolder#getBinding()がViewDataBindingを返すのでsetVariable()を呼んでいる
        // 専用のBinding（この場合だとListItemSampleBinding）を返すことが出来るなら普通にsetUser()でOK
//        holder.getBinding().setVariable(BR.user, getItem(position));
        ItemResponse itemResponse = mItemResponses.get(position);
        RowRetrofitBinding hoderBinding = (RowRetrofitBinding) holder.getBinding();
        hoderBinding.tvUserId.setText("" + itemResponse.getOwnerResponse().getUserId());
        hoderBinding.tvUserName.setText(itemResponse.getOwnerResponse().getDisplayName());
        if (itemResponse.getOwnerResponse().getProfileImage() != null)
            Glide.with(qCon)
                    .load(itemResponse.getOwnerResponse().getProfileImage())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade(R.anim.fade_in, 300)
                    .bitmapTransform(new BlurTransformation(qCon, 3), new CropCircleTransformation(qCon))
//                    .bitmapTransform(new BlurTransformation(qCon))
                    .into(hoderBinding.ivProfile);
        hoderBinding.tvPosition.setText("" + position);
    }

    public RetrofitAdapter(Context qCon, List<ItemResponse> posts, PostItemListener itemListener) {
        this.qCon = qCon;
        this.mItemResponses = posts;
        this.mItemListener = itemListener;
    }

    @Override
    public int getItemCount() {
        return mItemResponses == null ? 0 : mItemResponses.size();
    }

    public void updateAnswers(List<ItemResponse> itemResponses) {
        mItemResponses.addAll(itemResponses);
        mItemResponses.addAll(itemResponses);
        mItemResponses.addAll(itemResponses);
        mItemResponses.addAll(itemResponses);
        mItemResponses.addAll(itemResponses);
//        mItems = items;
        notifyDataSetChanged();
    }

    private ItemResponse getItem(int adapterPosition) {
        return mItemResponses.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}