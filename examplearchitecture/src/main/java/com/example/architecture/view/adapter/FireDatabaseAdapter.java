package com.example.architecture.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.architecture.R;
import com.example.architecture.databinding.RowFireDatabaseBinding;
import com.example.architecture.entities.room.User;
import com.example.architecture.viewmodel.FireDatabaseViewModel;
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
public class FireDatabaseAdapter extends QcRecyclerBaseAdapter<User> {
//    private List<? extends User> userList;
    private FireDatabaseViewModel viewModel;


//    public void addAll(List<User> data) {
//        QcLog.e("addAll == ");
//        this.userList = data;
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


    public FireDatabaseAdapter(QcBaseLifeFragment qFragment) {
        super(qFragment);
    }


    @Override
    public void needInitToOnCreate() {
        itemList = new ArrayList<>();
//        this.viewModel = (LiveDataViewModel) viewModel;
    }

    @Override
    public void needResetData() {
        itemList = new ArrayList<>();
        notifyDataSetChanged();
    }

    @Override
    public void setViewModel(AndroidViewModel viewModel) {
        this.viewModel = (FireDatabaseViewModel) viewModel;
//        observerUserListResults(this.viewModel.getAllUsers());
    }

    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return R.layout.row_fire_database;
    }

//    @Override
//    protected Object needItemFromPosition(int position) {
//        if (userList != null && userList.size() >= position) {
//            return userList.get(position);
//        } else {
//            return null;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return userList == null ? 0 : userList.size();
//    }


    @Override
    protected void needUIEventListener(int viewTypeResId, ViewDataBinding binding) {
        RowFireDatabaseBinding hoderBinding = (RowFireDatabaseBinding) binding;
        hoderBinding.ivProfile.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserLastName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnClickListener(mOnSingleClickListener);
        hoderBinding.tvUserName.setOnLongClickListener(mOnLongClickListener);
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        User user = (User) object;
        QcLog.e("user == " + position + " " + user.toString());
        RowFireDatabaseBinding hoderBinding = (RowFireDatabaseBinding) holder.getBinding();
        hoderBinding.tvPosition.setTag(position);
        hoderBinding.tvPosition.setText("" + position + "\n" + user.getId());
        hoderBinding.ivProfile.setTag(position);
        hoderBinding.tvUserLastName.setTag(position);
        hoderBinding.tvUserLastName.setText(user.getLastName());
        hoderBinding.tvUserName.setTag(position);
        hoderBinding.tvUserName.setText(user.getName());
    }

    @Override
    protected void needUILoadFailBinding(QcBaseViewHolder holder, int position, Object object) {
//        RowLoadFailBinding hoderBinding = (RowLoadFailBinding) holder.getBinding();
//        hoderBinding.btnReload.setTag(position);
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
            User item = (User) needItemFromPosition(position);
            switch (v.getId()) {
                case R.id.ivProfile:
                    QcLog.e("ivProfile ==== " + position);
                    QcToast.getInstance().show("ivProfile = ", false);
                    break;
                case R.id.tvUserLastName:
                    QcLog.e("tvUserLastName ==== " + position);
                    QcToast.getInstance().show("tvUserLastName = " + item.getLastName(), false);
                    break;
                case R.id.tvUserName:
                    QcLog.e("tvUserName ==== " + position);
                    QcToast.getInstance().show("tvUserName = " + item.getName(), false);
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
                User item = (User) needItemFromPosition(position);
                QcToast.getInstance().show("onLongClick ivProfile = " + item.getLastName(), false);
                return false;
            } else if (v.getId() == R.id.tvUserLastName) {
                User item = (User) needItemFromPosition(position);
                QcToast.getInstance().show("onLongClick tvUserLastName = " + item.getLastName(), false);
                return false;
            } else if (v.getId() == R.id.tvUserName) {
                User item = (User) needItemFromPosition(position);
                QcToast.getInstance().show("onLongClick tvUserName = " + item.getName(), false);

                return false;

            }
            return true;
        }
    };
}
