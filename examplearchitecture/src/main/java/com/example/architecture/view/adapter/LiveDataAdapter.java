package com.example.architecture.view.adapter;

import com.ulling.lib.core.viewutil.adapter.QcBaseViewHolder;
import com.ulling.lib.core.viewutil.adapter.QcRecyclerBaseAdapter;

/**
 * Created by P100651 on 2017-07-20.
 */
public class LiveDataAdapter extends QcRecyclerBaseAdapter {
    public LiveDataAdapter() {
        super();
        needInitData();
    }
    @Override
    protected void needInitData() {
    }

    @Override
    protected void needResetData() {
    }

    @Override
    protected int needLayoutIdFromItemViewType(int position) {
        return 0;
    }

    @Override
    protected Object needItemFromPosition(int position) {
        return null;
    }

    @Override
    protected void needUIBinding(QcBaseViewHolder holder, int position, Object object) {
        if (object == null) {
            return;
        }
    }

    @Override
    protected void needUIEventListener(QcBaseViewHolder holder, int position, Object object) {
        if (object == null) {
            return;
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
