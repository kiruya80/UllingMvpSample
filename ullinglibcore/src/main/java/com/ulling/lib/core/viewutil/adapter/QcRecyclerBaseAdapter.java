package com.ulling.lib.core.viewutil.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-07-20.
 */
public abstract class QcRecyclerBaseAdapter extends RecyclerView.Adapter<QcBaseViewHolder>    {
    public Context qCon;
//    public OnSingleClickListener listener;

//    public interface QcRecyclerItemListener {
//
//        void onItemClick(View view, int position);
//        void onItemLongClick(View view, int position);
//        void onItemCheck(boolean checked, int position);
//        void onDeleteItem(int itemPosition);
//
//        void onRefresh();
//    }

    /**
     * 필수
     * need~ 시작
     */
    /**
     * 1.
     * <p>
     * adapter data 초기화
     */
    protected abstract void needInitData();

    /**
     * 리셋할 데이터 정의
     */
    protected abstract void needResetData();

    /**
     * 2.
     * <p>
     * View Type 결정
     */
    protected abstract int needLayoutIdFromItemViewType(int position);

    /**
     * 3.
     * <p>
     * 포기션에 맞는 아이템 가져오기
     *
     * @param position
     * @return
     */
    protected abstract Object needItemFromPosition(int position);
//    protected abstract void needAddData(LiveData<List<Class>> data);

    /**
     * 4.
     * <p>
     * UI에서 필요한 데이터 바인딩
     * View객체에 접근하여 데이터 연결한다.
     */
    protected abstract void needUIBinding(QcBaseViewHolder holder, int position, Object object);

    /**
     * 5.
     * <p>
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
    protected abstract void needUIEventListener(ViewDataBinding binding);

    /**
     * 6.
     *
     * 리스너 달기
     * @param qcRecyclerItemListener
     */
//    protected abstract void setEventListener(QcRecyclerItemListener qcRecyclerItemListener);

    /**
     * 옵션
     * opt
     *
     * optAnimationResume
     * optAnimationPause
     */
    /**
     * 애니메이션 시작
     */
    protected void optAnimationResume() {
    }

    /**
     * 애니메이션 정지
     */
    protected void optAnimationPause() {
    }

    /**
     * 아답터 시작
     */

    public QcRecyclerBaseAdapter(Context qCon) {
        super();
        this.qCon = qCon;
        needInitData();
    }
    /**
     * @param viewGroup
     * @param viewTypeResId
     * @return
     */
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewTypeResId) {
        QcLog.e("onCreateViewHolder == ");
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewTypeResId, viewGroup, false);

        needUIEventListener(binding);

//        QcBaseViewHolder qcBaseViewHolder = new QcBaseViewHolder(binding);
//        qcBaseViewHolder.setOnItemClick(listener);
//        return qcBaseViewHolder;
        return new QcBaseViewHolder(binding);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        QcLog.e("onBindViewHolder == ");
        if (holder == null) {
            return;
        }
        if (position < 0) {
            return;
        }
        Object object = needItemFromPosition(position);
//        holder.bind(obj);
        if (object == null) {
            return;
        }

        needUIBinding(holder, position, object);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        QcLog.e("getItemViewType == ");
//        return getLayoutIdForPosition(position);
        return needLayoutIdFromItemViewType(position);
    }

}