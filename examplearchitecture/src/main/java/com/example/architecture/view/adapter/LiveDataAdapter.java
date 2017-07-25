package com.example.architecture.view.adapter;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;

import com.example.architecture.R;
import com.example.architecture.databinding.RowLiveDataBinding;
import com.example.architecture.enty.User;
import com.ulling.lib.core.listener.OnSingleClickListener;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

import java.util.ArrayList;

/**
 * Created by P100651 on 2017-07-20.
 */
public class LiveDataAdapter extends QcRecyclerBaseAdapter {
//    QcRecyclerItemListener qcRecyclerItemListener;
    ArrayList<User> userList = new ArrayList<>();

    public LiveDataAdapter(Context qCon) {
        super(qCon);
//        needInitData();
    }

//    public void setListener(OnSingleClickListener listener) {
//        this.listener = listener;
//    }

    public void addAll(ArrayList<User> data) {
        QcLog.e("addAll == ");
        this.userList = data;
        notifyDataSetChanged();
    }

    @Override
    public void needInitData() {
    }

    @Override
    public void needResetData() {
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
    protected void needUIEventListener(ViewDataBinding binding) {
        RowLiveDataBinding rowLiveDataBinding = (RowLiveDataBinding) binding;
        rowLiveDataBinding.ivProfile.setOnClickListener(mOnSingleClickListener);
        rowLiveDataBinding.tvUserId.setOnClickListener(mOnSingleClickListener);
        rowLiveDataBinding.tvUserName.setOnClickListener(mOnSingleClickListener);

        rowLiveDataBinding.tvUserName.setOnLongClickListener(new View.OnLongClickListener() {
            public boolean onLongClick(View v) {
                int position = (int) v.getTag();
                QcLog.e("onLongClick ==== " + position);
                return false;
            }
        });
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        RowLiveDataBinding rowLiveDataBinding = (RowLiveDataBinding) holder.getBinding();
        rowLiveDataBinding.ivProfile.setTag(position);
        rowLiveDataBinding.tvUserId.setTag(position);
        User user = (User) object;
        QcLog.e("user == " + position + " " + user.toString());
        rowLiveDataBinding.tvUserId.setText(user.getId());
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
                    break;
                case R.id.tvUserId:
                    QcLog.e("tvUserId ==== " + position);
                    break;
                case R.id.tvUserName:
                    QcLog.e("tvUserName ==== " + position);
                    break;
                default:
                    QcLog.e("default ==== " + position);
                    break;
            }
        }
    };

//    @Override
//    protected void setEventListener(QcRecyclerItemListener qcRecyclerItemListener) {
//        this.qcRecyclerItemListener = qcRecyclerItemListener;
//    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        } else {
            return 0;
        }
    }
}
