package com.example.architecture.view.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.architecture.R;
import com.example.architecture.databinding.RowLiveDataBinding;
import com.example.architecture.enty.User;
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
    ArrayList<User> userList = new ArrayList<>();
    private LiveDataViewModel viewModel;

    public LiveDataAdapter(QcBaseLifeFragment qFragment) {
        super(qFragment);
    }

    public void addAll(ArrayList<User> data) {
        QcLog.e("addAll == ");
        this.userList = data;
        notifyDataSetChanged();
    }

    public void add(User data) {
        QcLog.e("add == ");
        if (this.userList == null)
            this.userList = new ArrayList<>();
        this.userList.add(data);
        notifyDataSetChanged();
    }

    public void setViewModel(AndroidViewModel viewModel) {
        this.viewModel = (LiveDataViewModel) viewModel;
        observerUserListResults(this.viewModel.getAllUsers());
    }

    public boolean isViewModel() {
        if (viewModel == null) {
            return false;
        } else {
            return true;
        }
    }

    private void observerUserListResults(LiveData<List<User>> userLive) {
        //observer LiveData
        userLive.observe(qFragment, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> allUsers) {
                QcLog.e("allUsers observe == ");
                if (allUsers == null) {
                    return;
                }
                addAll((ArrayList<User>) allUsers);
            }
        });
    }

    @Override
    public void needOneceInitData() {
        userList = new ArrayList<>();
//        this.viewModel = (LiveDataViewModel) viewModel;
    }

    @Override
    public void needResetData() {
        userList = new ArrayList<>();
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
        if (userList != null) {
            return userList.size();
        } else {
            return 0;
        }
    }

    @Override
    protected void needUIEventListener(ViewDataBinding binding) {
        RowLiveDataBinding rowLiveDataBinding = (RowLiveDataBinding) binding;
        rowLiveDataBinding.ivProfile.setOnClickListener(mOnSingleClickListener);
        rowLiveDataBinding.tvUserLastName.setOnClickListener(mOnSingleClickListener);
        rowLiveDataBinding.tvUserName.setOnClickListener(mOnSingleClickListener);

        rowLiveDataBinding.tvUserName.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                int position = (int) v.getTag();
                QcLog.e("onLongClick ==== " + position);
                QcToast.getInstance().show("onLongClick tvUserName", false);
                return false;
            }
        });
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        User user = (User) object;
        QcLog.e("user == " + position + " " + user.toString());

        RowLiveDataBinding rowLiveDataBinding = (RowLiveDataBinding) holder.getBinding();

        rowLiveDataBinding.tvPosition.setTag(position);
        rowLiveDataBinding.tvPosition.setText("" + position + "\n" + user.getId());
        rowLiveDataBinding.ivProfile.setTag(position);
        rowLiveDataBinding.tvUserLastName.setTag(position);
        rowLiveDataBinding.tvUserLastName.setText(user.getLastName());
        rowLiveDataBinding.tvUserName.setTag(position);
        rowLiveDataBinding.tvUserName.setText(user.getName());
    }

    private OnSingleClickListener mOnSingleClickListener = new OnSingleClickListener() {
        @Override
        public void onSingleClick(View v) {
            int position = (int) v.getTag();
            QcLog.e("onItemClick ==== " + position);
            switch (v.getId()) {
                case R.id.ivProfile:
                    QcLog.e("ivProfile ==== " + position);
                    QcToast.getInstance().show("ivProfile = ", false);
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

}
