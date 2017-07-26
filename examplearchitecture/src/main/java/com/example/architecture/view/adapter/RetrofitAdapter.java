package com.example.architecture.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.architecture.R;
import com.example.architecture.databinding.RowRetrofitBinding;
import com.example.architecture.entities.retrofit.ItemResponse;
import com.example.architecture.viewmodel.RetrofitViewModel;
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
 * @since :
 * <p>
 * https://medium.com/google-developers/android-data-binding-recyclerview-db7c40d9f0e4
 *
 *  http://realignist.me/code/2016/05/25/data-binding-guide.html
 */
public class RetrofitAdapter extends QcRecyclerBaseAdapter {
    private List<ItemResponse> itemList;

    RetrofitViewModel viewModel;

    public void addAll(List<ItemResponse> data) {
        QcLog.e("addAll == ");
        this.itemList = data;
        notifyDataSetChanged();
    }
    public RetrofitAdapter(QcBaseLifeFragment qFragment) {
        super(qFragment);
    }

    @Override
    public void needInitToOnCreate() {
        itemList = new ArrayList<>();
    }

    @Override
    public void needResetData() {
        itemList = new ArrayList<>();
    }

    @Override
    public void setViewModel(AndroidViewModel viewModel) {
        this.viewModel = (RetrofitViewModel) viewModel;
//        observerUserListResults(this.viewModel.getAllUsers());
    }


    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return R.layout.row_retrofit;
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
        RowRetrofitBinding hoderBinding = (RowRetrofitBinding) binding;

        hoderBinding.tvPosition.setOnClickListener(mOnSingleClickListener);
        hoderBinding.rlProfile.setOnClickListener(mOnSingleClickListener);
        hoderBinding.rlProfile.setOnLongClickListener(mOnLongClickListener);

        hoderBinding.tvUserId.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnLongClickListener(mOnLongClickListener);
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        ItemResponse item = (ItemResponse) object;
        // BindingHolder#getBinding()がViewDataBindingを返すのでsetVariable()を呼んでいる
        // 専用のBinding（この場合だとListItemSampleBinding）を返すことが出来るなら普通にsetUser()でOK
//        holder.getBinding().setVariable(BR.user, getItem(position));
        RowRetrofitBinding hoderBinding = (RowRetrofitBinding) holder.getBinding();

        hoderBinding.tvPosition.setTag(position);
        hoderBinding.tvPosition.setText("" + position);

        hoderBinding.tvUserId.setTag(position);
        hoderBinding.tvUserId.setText("" + item.getOwnerResponse().getUserId());
        hoderBinding.tvUserName.setTag(position);
        hoderBinding.tvUserName.setText(item.getOwnerResponse().getDisplayName());


        hoderBinding.rlProfile.setTag(position);
        if (item.getOwnerResponse().getProfileImage() != null)
            Glide.with(qCon)
                    .load(item.getOwnerResponse().getProfileImage())
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
                    QcToast.getInstance().show("tvUserId = " + itemList.get(position).getOwnerResponse().getUserId(), false);
                    break;
                case R.id.tvUserName:
                    QcLog.e("tvUserName ==== " + position);
                    QcToast.getInstance().show("tvUserName = " + itemList.get(position).getOwnerResponse().getDisplayName(), false);
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
                QcToast.getInstance().show("onLongClick rlProfile = " +  itemList.get(position).getOwnerResponse().getProfileImage(), false);
                return false;
            } else if (v.getId() == R.id.tvUserId) {
                QcToast.getInstance().show("onLongClick tvUserId = " + itemList.get(position).getOwnerResponse().getUserId(), false);
                return false;
            } else if (v.getId() == R.id.tvUserName) {
                QcToast.getInstance().show("onLongClick tvUserName = " + itemList.get(position).getOwnerResponse().getDisplayName(), false);

                return false;

            }
            return true;
        }
    };

}