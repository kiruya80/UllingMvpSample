package com.example.architecture.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.architecture.R;
import com.example.architecture.databinding.RowLiveDataBinding;
import com.example.architecture.entities.room.User;
import com.example.architecture.viewmodel.LiveDataViewModel;
import com.ulling.lib.core.base.QcBaseLifeFragment;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.util.QcToast;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by P100651 on 2017-07-20.
 */
public class LiveDataAdapter extends QcRecyclerBaseAdapter {
    //    QcRecyclerItemListener qcRecyclerItemListener;
//    ArrayList<User> userList = new ArrayList<>();
    private List<? extends User> userList;
    private LiveDataViewModel viewModel;


    public void addAll(List<User> data) {
        QcLog.e("addAll == ");
        this.userList = data;
        notifyDataSetChanged();
    }

//    public void add(User data) {
//        QcLog.e("add == ");
//        if (this.userList == null)
//            this.userList = new ArrayList<>();
//        this.userList.add(data);
//        notifyDataSetChanged();
//    }


    /**
     * 중복 방지
     *
     * 깜빡이는 현상이 생김
     */
//    public void addUser(final List<? extends User> userList_) {
//        if (userList == null) {
//            userList = userList_;
//            notifyItemRangeInserted(0, userList_.size());
//        } else {
//            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
//                @Override
//                public int getOldListSize() {
//                    return userList.size();
//                }
//
//                @Override
//                public int getNewListSize() {
//                    return userList_.size();
//                }
//
//                @Override
//                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
//                    return userList.get(oldItemPosition).getId() ==
//                            userList_.get(newItemPosition).getId();
//                }
//
//                @Override
//                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
//                    User newUser = userList.get(newItemPosition);
//                    User old = userList.get(oldItemPosition);
//                    return newUser.getId() == old.getId()
//                            && Objects.equals(newUser.getLastName(), old.getLastName())
//                            && Objects.equals(newUser.getName(), old.getName())
//                            && newUser.getAge() == old.getAge();
//                }
//            });
//            userList = userList_;
//            result.dispatchUpdatesTo(this);
//        }
//    }


    private void observerUserListResults(LiveData<List<User>> userLive) {
        userLive.observe(qFragment, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> allUsers) {
                QcLog.e("allUsers observe == ");
                if (allUsers == null) {
                    return;
                }
//                addUser((ArrayList<User>) allUsers);
                addAll(allUsers);
//                for (User user: allUsers)
//                add(user);
            }
        });
    }



    public LiveDataAdapter(QcBaseLifeFragment qFragment) {
        super(qFragment);
    }


    @Override
    public void needInitToOnCreate() {
        userList = new ArrayList<>();
    }

    @Override
    public void needResetData() {
        userList = new ArrayList<>();
    }

    @Override
    public void setViewModel(AndroidViewModel viewModel) {
        this.viewModel = (LiveDataViewModel) viewModel;
//        observerUserListResults(this.viewModel.getAllUsers());
    }

    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return R.layout.row_live_data;
    }

    @Override
    protected Object needItemFromPosition(int position) {
        if (userList != null && userList.size() >= position) {
            return userList.get(position);
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return userList == null ? 0 : userList.size();
    }


    @Override
    protected void needUIEventListener(ViewDataBinding binding) {
        RowLiveDataBinding hoderBinding = (RowLiveDataBinding) binding;
        hoderBinding.rlProfile.setOnClickListener(mOnSingleClickListener);
        hoderBinding.rlProfile.setOnLongClickListener(mOnLongClickListener);
        hoderBinding.tvUserLastName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnLongClickListener(mOnLongClickListener);
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        User user = (User) object;
        QcLog.e("user == " + position + " " + user.toString());
        RowLiveDataBinding hoderBinding = (RowLiveDataBinding) holder.getBinding();
        hoderBinding.tvPosition.setTag(position);
        hoderBinding.tvPosition.setText("" + position + "\n" + user.getId());
        hoderBinding.rlProfile.setTag(position);
        hoderBinding.tvUserLastName.setTag(position);
        hoderBinding.tvUserLastName.setText(user.getLastName());
        hoderBinding.tvUserName.setTag(position);
        hoderBinding.tvUserName.setText(user.getName());
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
                case R.id.tvUserLastName:
                    QcLog.e("tvUserLastName ==== " + position);
                    QcToast.getInstance().show("tvUserLastName = " + userList.get(position).getLastName(), false);
                    break;
                case R.id.tvUserName:
                    QcLog.e("tvUserName ==== " + position);
                    QcToast.getInstance().show("tvUserName = " + userList.get(position).getName(), false);
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
                QcToast.getInstance().show("onLongClick rlProfile = " +  userList.get(position).getLastName(), false);
                return false;
            } else if (v.getId() == R.id.tvUserLastName) {
                QcToast.getInstance().show("onLongClick tvUserLastName = " + userList.get(position).getLastName(), false);
                return false;
            } else if (v.getId() == R.id.tvUserName) {
                QcToast.getInstance().show("onLongClick tvUserName = " + userList.get(position).getName(), false);

                return false;

            }
            return true;
        }
    };
}
