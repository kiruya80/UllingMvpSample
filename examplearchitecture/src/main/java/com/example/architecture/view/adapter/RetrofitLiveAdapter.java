package com.example.architecture.view.adapter;

import static com.example.architecture.R.layout.row_retrofit_live;

import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ViewDataBinding;
import android.os.Build;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.architecture.R;
import com.example.architecture.databinding.RowLoadFailBinding;
import com.example.architecture.databinding.RowRetrofitLiveBinding;
import com.example.architecture.entities.room.Answer;
import com.example.architecture.view.RetrofitLiveFragment;
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
public class RetrofitLiveAdapter extends QcRecyclerBaseAdapter<Answer> {
    private RetrofitLiveViewModel viewModel;


    public void addProgress() {
        addProgress(new Answer(QcRecyclerBaseAdapter.TYPE_LOAD_PROGRESS));
    }


    public void addLoadFail() {
        addLoadFail(new Answer(QcRecyclerBaseAdapter.TYPE_LOAD_FAIL));
    }


    public RetrofitLiveAdapter(QcBaseLifeFragment qFragment, QcRecyclerBaseAdapter.QcRecyclerItemListener qcRecyclerItemListener) {
        super(qFragment, qcRecyclerItemListener);

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
        if (itemList != null && itemList.size() > 0) {
            if (itemList.get(position).getType() == TYPE_DEFAULT) {
                return R.layout.row_retrofit_live;

            } else if (itemList.get(position).getType() == TYPE_LOAD_FAIL) {
                return R.layout.row_load_fail;

            } else if (itemList.get(position).getType() == TYPE_LOAD_PROGRESS) {
                return R.layout.row_load_progress;
            }

        }
        return row_retrofit_live;
    }

    @Override
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {
        if (viewTypeResId == R.layout.row_retrofit_live) {
            RowRetrofitLiveBinding hoderBinding = (RowRetrofitLiveBinding) binding;

            hoderBinding.tvPosition.setOnClickListener(mOnSingleClickListener);
            hoderBinding.rlProfile.setOnClickListener(mOnSingleClickListener);

            hoderBinding.tvUserId.setOnClickListener(mOnSingleClickListener);
            hoderBinding.tvUserName.setOnClickListener(mOnSingleClickListener);
            hoderBinding.tvUserName.setOnLongClickListener(mOnLongClickListener);
        } else if (viewTypeResId == R.layout.row_load_fail) {
            RowLoadFailBinding hoderBinding = (RowLoadFailBinding) binding;
            hoderBinding.btnReload.setOnClickListener(mOnSingleClickListener);
        } else if (viewTypeResId == R.layout.row_load_progress) {
//            RowLoadProgressBinding hoderBinding = (RowLoadProgressBinding) binding;
        }
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        if (object instanceof Answer) {

            Answer item = (Answer) object;
            if (item.getType() == TYPE_DEFAULT) {
                QcLog.e("needUIBinding  item == " + item.toString());
                RowRetrofitLiveBinding hoderBinding = (RowRetrofitLiveBinding) holder.getBinding();

                hoderBinding.tvPosition.setTag(position);
                hoderBinding.tvPosition.setText("" + position);

                hoderBinding.tvUserId.setTag(position);
                hoderBinding.tvUserId.setText("" + item.getOwner().getUserId());
                hoderBinding.tvUserName.setTag(position);
                hoderBinding.tvUserName.setText(item.getOwner().getDisplayName());

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    hoderBinding.tvUserId.setTransitionName(qCon.getString(R.string.trans_text_id) + position);
                    hoderBinding.tvUserName.setTransitionName(qCon.getString(R.string.trans_text_name) + position);
                    hoderBinding.rlProfile.setTransitionName(qCon.getString(R.string.trans_image_profile) + position);
                }

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
        }
    }

    @Override
    protected void needUILoadFailBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUILoadFailBinding == ");
        RowLoadFailBinding hoderBinding = (RowLoadFailBinding) holder.getBinding();
        hoderBinding.btnReload.setTag(position);
    }

    @Override
    protected void needUILoadProgressBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUILoadProgressBinding == ");
    }

    @Override
    protected void needUIOtherBinding(QcBaseViewHolder holder, int position, Object object) {
        QcLog.i("needUIOtherBinding == ");
    }

    private OnSingleClickListener mOnSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            int position = (int) v.getTag();
            QcLog.e("onItemClick ==== " + position);
            switch (v.getId()) {
                case R.id.btnReload:
                    QcLog.e("btnReload ==== " + position);
                    QcToast.getInstance().show("btnReload = ", false);
                    removeLoadFail();
                    addProgress();
                    RetrofitLiveFragment mRetrofitLiveFragment = (RetrofitLiveFragment) qFragment;
                    int page = mRetrofitLiveFragment.getPage();
                    if (viewModel != null)
                        viewModel.getAnswersFromRemote(page);
                    break;
                case R.id.rlProfile:
                    QcLog.e("rlProfile ==== " + position);
                    QcToast.getInstance().show("ivProfile = ", false);
                    Answer item = (Answer) needItemFromPosition(position);
                    if (item.getAnswerId() > 0 && viewModel != null)
                        viewModel.moveFragment(v, item.getAnswerId());
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
            if (v.getId() == R.id.ivProfile) {
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