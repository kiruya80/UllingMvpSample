package com.ulling.lib.core.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

/**
 * Created by P100651 on 2017-07-21.
 * <p>
 * http://thdev.tech/androiddev/2016/11/01/Android-RecyclerView-intro.html
 * <p>
 * LinearLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="LinearLayoutManager" />
 * GridLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="GridLayoutManager"
 * app:spanCount="2" />
 * LinearLayoutManager
 * <p>
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="StaggeredGridLayoutManager"
 * app:spanCount="3" />
 * <p>
 * <p>
 * LinearLayoutManager
 * // use a linear layout manager
 * mLayoutManager = new LinearLayoutManager(this);
 * mRecyclerView.setLayoutManager(mLayoutManager);
 * <p>
 * GridLayoutManager
 * // use a staggered grid layout manager
 * mGridLayoutManager = new new GridLayoutManager(this, 3);
 * mRecyclerView.setLayoutManager(mGridLayoutManager);
 * <p>
 * StaggeredGridLayoutManager
 * // use a staggered grid layout manager
 * mStgaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
 * mRecyclerView.setLayoutManager(mStgaggeredGridLayoutManager);
 * <p>
 * <p>
 * Header/Footer을 포함?
 * Fail loading ?
 */
public class QcRecyclerView extends RecyclerView {
    //    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    private Context context;
    //    private LinearLayoutManager layoutManager;
//    private GridLayoutManager gridLayoutManager;
//    private StaggeredGridLayoutManager stgaggeredGridLayoutManager;
//    private int transform = linear;
//    private int orientation = VERTICAL;
//    private int spanCount = 1;
//    public static final int linear = 0;
//    public static final int Grid = 1;
//    public static final int StaggeredGrid = 2;
//    public static final int HORIZONTAL = 0;
//    public static final int VERTICAL = 1;
//    private boolean reverseLayout = false;
    private View emptyView;
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {
        @Override
        public void onChanged() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    setEmptyView(true);
                } else {
                    setEmptyView(false);
                }
            }
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            super.onItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
        }
    };

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
        if (adapter != null && emptyObserver != null && emptyView != null) {
           if (!adapter.hasObservers())
            adapter.registerAdapterDataObserver(emptyObserver);
//            emptyObserver.onChanged();
        }
    }

    public void setEmptyView(boolean isEmpty) {
        if (isEmpty) {
            emptyView.setVisibility(View.VISIBLE);
            QcRecyclerView.this.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            QcRecyclerView.this.setVisibility(View.VISIBLE);
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }

    public interface QcRecyclerListener {
        void onLoadMore(int page, int totalItemsCount, RecyclerView view);

        void onLoadEnd();
    }

    private QcRecyclerListener qcRecyclerListener;

    public QcRecyclerView(Context context) {
        // 자신의 생성자를 호출합니다.
        this(context, null);
    }

    public QcRecyclerView(Context context, @Nullable AttributeSet attrs) {
        // 자신의 생성자를 호출합니다.
        this(context, attrs, 0);
    }

    public QcRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
//        initView();
//        getAttrs(attrs);
//        setLinearLayoutManager();
//        setEndlessRecyclerScrollListener();
        if (endlessRecyclerScrollListener != null)
            addOnScrollListener(endlessRecyclerScrollListener);
    }

    public void resetEndlessScrollState() {
        endlessRecyclerScrollListener.resetState();
    }

    EndlessRecyclerScrollListener endlessRecyclerScrollListener = new EndlessRecyclerScrollListener((RecyclerView.LayoutManager) getLayoutManager()) {
        @Override
        public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
            QcLog.i("onLoadMore =====");
            if (qcRecyclerListener != null)
                qcRecyclerListener.onLoadMore(page, totalItemsCount, view);
        }

        @Override
        public void onLoadEnd() {
            QcLog.i("onLoadEnd =====");
            if (qcRecyclerListener != null)
                qcRecyclerListener.onLoadEnd();
        }
    };

    public void setQcRecyclerListener(QcRecyclerListener qcRecyclerListener) {
        this.qcRecyclerListener = qcRecyclerListener;
    }

    public EndlessRecyclerScrollListener getEndlessRecyclerScrollListener() {
        return endlessRecyclerScrollListener;
    }

    private void initView() {
//        String infService = Context.LAYOUT_INFLATER_SERVICE;
//        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
//        View v = li.inflate(R.layout.welcome_login_button, this, false);
//        addView(v);
//        bg = (LinearLayout) findViewById(R.id.bg);
//        symbol = (ImageView) findViewById(R.id.symbol);
//        text = (TextView) findViewById(R.id.text);
    }

    private void getAttrs(AttributeSet attrs) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView);
//        setTypeArray(typedArray);
    }
//    private void getAttrs(AttributeSet attrs, int defStyle) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView, defStyle, 0);
//        setTypeArray(typedArray);
//    }

    private void setTypeArray(TypedArray typedArray) {
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcOrientation)) {
//            orientation = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcOrientation, 0);
//        }
//
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcReverseLayout)) {
//            reverseLayout = typedArray.getBoolean(R.styleable.QcAttrsRecyclerView_qcReverseLayout, false);
//        }
//
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcSpanCount)) {
//            spanCount = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcSpanCount, 1);
//        }
//        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_qcLayoutManager)) {
//            transform = typedArray.getInt(R.styleable.QcAttrsRecyclerView_qcLayoutManager, 0);
//        }
//        try {
//            distanceExample = typedArray.getDimension(R.styleable.MyCustomElement_distanceExample, 100.0f);
//        } finally {
//            ta.recycle();
//        }
//        try {
//            mShowText = typedArray.get(R.styleable.transform, false);
//            mTextPos = typedArray.getInteger(R.styleable.PieChart_labelPosition, 0);
//        } finally {
//            typedArray.recycle();
//        }
//        int bg_resID = typedArray.getResourceId(R.styleable.LoginButton_bg, R.drawable.login_naver_bg);
//        bg.setBackgroundResource(bg_resID);
//
//        int symbol_resID = typedArray.getResourceId(R.styleable.LoginButton_symbol, R.drawable.login_naver_symbol);
//        symbol.setImageResource(symbol_resID);
//
//        int textColor = typedArray.getColor(R.styleable.LoginButton_textColor, 0);
//        text.setTextColor(textColor);
//
//        String text_string = typedArray.getString(R.styleable.LoginButton_text);
//        text.setText(text_string);
        typedArray.recycle();
    }
//    private void setLinearLayoutManager() {
//        if (linear == orientation) {
//            layoutManager = new LinearLayoutManager(getContext());
//            layoutManager.setOrientation(orientation);
//            layoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(layoutManager);
//        } else if (Grid == orientation) {
//            gridLayoutManager = new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout);
//            gridLayoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(gridLayoutManager);
//        } else if (StaggeredGrid == orientation) {
//            stgaggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount,
//                    StaggeredGridLayoutManager.VERTICAL);
//            stgaggeredGridLayoutManager.setItemPrefetchEnabled(true);
//            setLayoutManager(stgaggeredGridLayoutManager);
//        }
//    }
    //    @Override
//    public RecyclerView.LayoutManager getLayoutManager() {
//        if (linear == orientation) {
//            return layoutManager;
//        } else if (Grid == orientation) {
//            return gridLayoutManager;
//        } else if (StaggeredGrid == orientation) {
//            return stgaggeredGridLayoutManager;
//        }
//        return layoutManager;
//    }
}