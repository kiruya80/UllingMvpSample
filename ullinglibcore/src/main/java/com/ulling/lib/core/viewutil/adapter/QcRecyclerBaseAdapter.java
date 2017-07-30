package com.ulling.lib.core.viewutil.adapter;

import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ulling.lib.core.base.QcBaseLifeFragment;
import com.ulling.lib.core.util.QcLog;

import java.util.List;

/**
 * Created by P100651 on 2017-07-20.
 *
 * https://github.com/googlesamples/android-architecture-components/blob/master/BasicSample/app/src/main/java/com/example/android/persistence/ui/ProductAdapter.java
 *
 *
 * 해결 및 최적화가 가능한지 체크 리스트
 *
 * 1. 공통으로 사용할 수 있는 데이터 리스트?
 * 2. 데이터 모델이 필요할까
 *
 */
public abstract class QcRecyclerBaseAdapter<T> extends RecyclerView.Adapter<QcBaseViewHolder> {
    public static final int TYPE_LOAD_FAIL = 0;
    public static final int TYPE_LOAD_PROGRESS = 999;
    public static final int TYPE_DEFAULT = 1;
    public Context qCon;
    public QcBaseLifeFragment qFragment;
    private AndroidViewModel viewModel;
    public QcRecyclerItemListener qcRecyclerItemListener;
    /**
     * data set
     */
    public List<T> itemList;

    public interface QcRecyclerItemListener<T> {
        void onItemClick(View view, int position, T t);
        void onItemLongClick(View view, int position, T t);
        void onItemCheck(boolean checked, int position, T t);
        void onDeleteItem(int itemPosition, T t);
        void onReload();
    }
    /**
     * 필수
     * need~ 시작
     */
    /**
     * 1-1.
     * <p>
     * adapter data 초기화
     */
    protected abstract void needInitToOnCreate();

    /**
     * 1-2.
     * 리셋할 데이터 정의
     */
    protected abstract void needResetData();

    /**
     * 2.
     * 뷰모델 설정
     *
     * @param viewModel
     */
    public abstract void setViewModel(AndroidViewModel viewModel);

    public boolean isViewModel() {
        if (viewModel == null) {
            return false;
        } else {
            return true;
        }
    }

    public AndroidViewModel getViewModel() {
        return viewModel;
    }

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
     */
    protected abstract Object needItemFromPosition(int position);
//    protected abstract void needAddData(LiveData<List<Class>> data);

    /**
     * 5.
     * <p>
     * 접근한 View에 이벤트에 따른 동작 설정
     * 버튼 및 기타 UI이벤트 설정
     */
    protected abstract void needUIEventListener(int viewTypeResId, ViewDataBinding binding);
    /**
     * 4.
     * <p>
     * UI에서 필요한 데이터 바인딩
     * View객체에 접근하여 데이터 연결한다.
     */
    protected abstract void needUIBinding(QcBaseViewHolder holder, int position, Object object);

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
    public QcRecyclerBaseAdapter(QcBaseLifeFragment qFragment) {
        super();
        this.qFragment = qFragment;
        this.qCon = qFragment.getContext();
        needInitToOnCreate();
        needResetData();
    }
    public QcRecyclerBaseAdapter(QcBaseLifeFragment qFragment, QcRecyclerItemListener qcRecyclerItemListener) {
        super();
        this.qFragment = qFragment;
        this.qCon = qFragment.getContext();
        this.qcRecyclerItemListener = qcRecyclerItemListener;
        needInitToOnCreate();
        needResetData();
    }

    /**
     * 1. 뷰모델 가져오기
     * 2. 이벤트 리스너 달기
     *
     * @param viewGroup
     * @param viewTypeResId
     * @return
     */
    @Override
    public QcBaseViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewTypeResId) {
        QcLog.i("onCreateViewHolder == ");
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater, viewTypeResId, viewGroup, false);
        needUIEventListener(viewTypeResId, binding);
        return new QcBaseViewHolder(binding);
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(QcBaseViewHolder holder, int position) {
        QcLog.i("onBindViewHolder == ");
        if (holder == null) {
            return;
        }
        if (position < 0) {
            return;
        }
        Object object = needItemFromPosition(position);
        if (object == null) {
            return;
        }
        needUIBinding(holder, position, object);
    }

    /**
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
//        return getLayoutIdForPosition(position);
        return needLayoutIdFromItemViewType(position);
    }
}