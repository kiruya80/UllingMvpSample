package com.example.architecture.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.architecture.R;
import com.example.architecture.databinding.RowRetrofitLiveBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.viewmodel.RetrofitLiveViewModel;
import com.ulling.lib.core.base.QcBaseLifeFragment;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : KILHO
 * @project : UllingMvpSample
 * @date : 2017. 7. 18.
 * @description :
 * @since : <p> https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 *
 * http://realignist.me/code/2016/05/25/data-binding-guide.html
 *
 *
 * https://github.com/AnkitSinhal/DiffUtilExample/blob/master/app/src/main/java/com/sample/diffutil/EmployeeDiffCallback.java
 */
public class RetrofitLiveAdapter extends QcRecyclerBaseAdapter {
    private List<Answer> itemList = new ArrayList<>();

    private RetrofitLiveViewModel viewModel;

    public void addAll(List<Answer> data) {
        this.itemList = data;
        QcLog.e("addAll == " + itemList.size());
        notifyDataSetChanged();
    }

        public void addAnswer(final List<Answer> itemList_) {
        if (itemList == null) {
            this.itemList = itemList_;
            notifyItemRangeInserted(0, itemList.size());
        } else {
            DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return itemList.size();
                }

                @Override
                public int getNewListSize() {
                    return itemList_.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return itemList.get(oldItemPosition).getAnswerId() ==
                            itemList_.get(newItemPosition).getAnswerId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Answer newAnswer = itemList_.get(newItemPosition);
                    Answer oldAnswer = itemList.get(oldItemPosition);

                    return oldAnswer.equals(newAnswer);
                }


                @Nullable
                @Override
                public Object getChangePayload(int oldItemPosition, int newItemPosition) {
                    // Implement method if you're going to use ItemAnimator
                    return super.getChangePayload(oldItemPosition, newItemPosition);
                }
            });

            this.itemList.clear();
            this.itemList.addAll(itemList_);
            diffResult.dispatchUpdatesTo(this);

//            itemList = itemList_;
//            result.dispatchUpdatesTo(this);
        }
    }


    public RetrofitLiveAdapter(QcBaseLifeFragment qFragment) {
        super(qFragment);
    }

    @Override
    public void needInitToOnCreate() {
        itemList = new ArrayList<>();
    }

    @Override
    public void needResetData() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void setViewModel(AndroidViewModel viewModel) {
        this.viewModel = (RetrofitLiveViewModel) viewModel;
//        observerUserListResults(this.viewModel.getAllUsers());
    }


    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return R.layout.row_retrofit_live;
    }


    @Override
    protected Object needItemFromPosition(int position) {
        if (itemList != null && itemList.size() >= position) {
            return itemList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    @Override
    protected void needUIEventListener(ViewDataBinding binding) {
        RowRetrofitLiveBinding hoderBinding = (RowRetrofitLiveBinding) binding;

        hoderBinding.tvPosition.setOnClickListener(mOnSingleClickListener);
        hoderBinding.rlProfile.setOnClickListener(mOnSingleClickListener);
        hoderBinding.rlProfile.setOnLongClickListener(mOnLongClickListener);

        hoderBinding.tvUserId.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnLongClickListener(mOnLongClickListener);
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        Answer item = (Answer) object;
        RowRetrofitLiveBinding hoderBinding = (RowRetrofitLiveBinding) holder.getBinding();

        hoderBinding.tvPosition.setTag(position);
        hoderBinding.tvPosition.setText("" + position);

        hoderBinding.tvUserId.setTag(position);
        hoderBinding.tvUserId.setText("" + item.getOwner().getUserId());
        hoderBinding.tvUserName.setTag(position);
        hoderBinding.tvUserName.setText(item.getOwner().getDisplayName());


        hoderBinding.rlProfile.setTag(position);
        if (item.getOwner().getProfileImage() != null)
            Glide.with(qCon)
                    .load(item.getOwner().getProfileImage())
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .crossFade(R.anim.fade_in, 300)
                    .bitmapTransform(new BlurTransformation(qCon, 3), new CropCircleTransformation(qCon))
//                    .bitmapTransform(new BlurTransformation(qCon))
                    .into(hoderBinding.ivProfile);
    }


    private OnSingleClickListener mOnSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            int position = (int) v.getTag();
            QcLog.e("onItemClick ==== " + position);
            switch (v.getId()) {
                case R.id.rlProfile:
                    QcLog.e("rlProfile ==== " + position);
                    QcToast.getInstance().show("rlProfile = ", false);
                    break;
                case R.id.tvUserId:
                    QcLog.e("tvUserId ==== " + position);
                    QcToast.getInstance().show("tvUserId = " + itemList.get(position).getOwner().getUserId(), false);
                    break;
                case R.id.tvUserName:
                    QcLog.e("tvUserName ==== " + position);
                    QcToast.getInstance().show("tvUserName = " + itemList.get(position).getOwner().getDisplayName(), false);
                    break;
                default:
                    QcLog.e("default ==== " + position);
                    QcToast.getInstance().show("default", false);
                    break;
            }
        }
    };

    private View.OnLongClickListener mOnLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            QcLog.e("OnLongClickListener ==== " + position);
            if (v.getId() == R.id.rlProfile) {
                QcToast.getInstance().show("onLongClick rlProfile = " + itemList.get(position).getOwner().getProfileImage(), false);
                return false;
            } else if (v.getId() == R.id.tvUserId) {
                QcToast.getInstance().show("onLongClick tvUserId = " + itemList.get(position).getOwner().getUserId(), false);
                return false;
            } else if (v.getId() == R.id.tvUserName) {
                QcToast.getInstance().show("onLongClick tvUserName = " + itemList.get(position).getOwner().getDisplayName(), false);

                return false;

            }
            return true;
        }
    };

}