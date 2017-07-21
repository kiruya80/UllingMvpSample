package com.example.architecture.view.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.example.architecture.R;
import com.example.architecture.databinding.RowRetrofitBinding;
import com.example.architecture.enty.retrofit.Item;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;

import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

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
public class AnswersAdapter extends RecyclerView.Adapter<QcBaseViewHolder> {
    private List<Item> mItems;
    private Context qCon;
    private PostItemListener mItemListener;
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        final View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_retrofit, viewGroup, false);
//        return new QcBaseViewHolder(v);
//        ListItemBinding binding = ListItemBinding.inflate(layoutInflater, viewGroup, false);
        //or
        RowRetrofitBinding viewBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.row_retrofit, viewGroup, false);
        return new QcBaseViewHolder(viewBinding);
//        if (layoutInflater == null) {
//            layoutInflater = LayoutInflater.from(viewGroup.getContext());
//        }
//        final ListItemBinding binding =
//                DataBindingUtil.inflate(layoutInflater, R.layout.list_item, viewGroup, false);
//      return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        // BindingHolder#getBinding()がViewDataBindingを返すのでsetVariable()を呼んでいる
        // 専用のBinding（この場合だとListItemSampleBinding）を返すことが出来るなら普通にsetUser()でOK
//        holder.getBinding().setVariable(BR.user, getItem(position));
        Item item = mItems.get(position);
        RowRetrofitBinding hoderBinding = (RowRetrofitBinding) holder.getBinding();
        hoderBinding.tvUserId.setText("" + item.getOwner().getUserId());
        hoderBinding.tvUserName.setText(item.getOwner().getDisplayName());
        if (item.getOwner().getProfileImage() != null)
            Glide.with(qCon)
                    .load(item.getOwner().getProfileImage())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade(R.anim.fade_in, 300)
                    .bitmapTransform(new BlurTransformation(qCon, 3), new CropCircleTransformation(qCon))
//                    .bitmapTransform(new BlurTransformation(qCon))
                    .into(hoderBinding.ivProfile);
        hoderBinding.tvPosition.setText("" + position);
    }
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
//
//        public TextView titleTv;
//        PostItemListener mItemListener;
//
//        public ViewHolder(View itemView, PostItemListener postItemListener) {
//            super(itemView);
//            titleTv = (TextView) itemView.findViewById(android.R.id.text1);
//
//            this.mItemListener = postItemListener;
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            Item item = getItem(getAdapterPosition());
//            this.mItemListener.onPostClick(item.getAnswerId());
//
//            notifyDataSetChanged();
//        }
//    }

    public AnswersAdapter(Context qCon, List<Item> posts, PostItemListener itemListener) {
        this.qCon = qCon;
        this.mItems = posts;
        this.mItemListener = itemListener;
    }
//    @Override
//    public AnswersAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//
//        Context context = parent.getContext();
//        LayoutInflater inflater = LayoutInflater.from(context);
//
//        View postView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
//
//        ViewHolder viewHolder = new ViewHolder(postView, this.mItemListener);
//        return viewHolder;
//    }
//
//    @Override
//    public void onBindViewHolder(AnswersAdapter.ViewHolder holder, int position) {
//
//        Item item = mItems.get(position);
//        TextView textView = holder.titleTv;
//        textView.setText(item.getOwner().getDisplayName());
//    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void updateAnswers(List<Item> items) {
        mItems.addAll(items);
        mItems.addAll(items);
        mItems.addAll(items);
        mItems.addAll(items);
        mItems.addAll(items);
//        mItems = items;
        notifyDataSetChanged();
    }

    private Item getItem(int adapterPosition) {
        return mItems.get(adapterPosition);
    }

    public interface PostItemListener {
        void onPostClick(long id);
    }
}