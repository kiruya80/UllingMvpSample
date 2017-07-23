package com.ulling.lib.core.viewutil.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by P100651 on 2017-07-20.
 */
public abstract class QcRecyclerBaseAdapter extends RecyclerView.Adapter<QcBaseViewHolder> {
    public Context qCon;

    public interface QcRecyclerItemListener {

        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
        void onItemCheck(boolean checked, int position);
        void onDeleteItem(int itemPosition);

        void onRefresh();
        void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    }

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
//    protected abstract void needSetData();

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
    protected abstract void needUIEventListener(QcBaseViewHolder holder, int position, Object object);

    /**
     * 6.
     *
     * 리스너 달기
     * @param qcRecyclerItemListener
     */
    protected abstract void setEventListener(QcRecyclerItemListener qcRecyclerItemListener);

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
     * @param viewType
     * @return
     */
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewType, viewGroup, false);
        return new QcBaseViewHolder(binding);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        Object object = needItemFromPosition(position);
//        holder.bind(obj);
        needUIBinding(holder, position, object);
        needUIEventListener(holder, position, object);
    }

    /**
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
//        return getLayoutIdForPosition(position);
        return needLayoutIdFromItemViewType(position);
    }

}