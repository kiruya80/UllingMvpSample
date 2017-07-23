package com.ulling.lib.core.viewutil;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;

import com.ulling.lib.core.R;
import com.ulling.lib.core.util.QcLog;
import com.ulling.lib.core.viewutil.recyclerView.EndlessRecyclerScrollListener;

/**
 * Created by P100651 on 2017-07-21.
 *
 * http://thdev.tech/androiddev/2016/11/01/Android-RecyclerView-intro.html
 *
 * LinearLayoutManager
 *
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="LinearLayoutManager" />
 * GridLayoutManager
 *
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="GridLayoutManager"
 * app:spanCount="2" />
 * LinearLayoutManager
 *
 * <android.support.v7.widget.RecyclerView
 * app:layoutManager="StaggeredGridLayoutManager"
 * app:spanCount="3" />
 *
 *
 * LinearLayoutManager
 * // use a linear layout manager
 * mLayoutManager = new LinearLayoutManager(this);
 * mRecyclerView.setLayoutManager(mLayoutManager);
 *
 * GridLayoutManager
 * // use a staggered grid layout manager
 * mGridLayoutManager = new new GridLayoutManager(this, 3);
 * mRecyclerView.setLayoutManager(mGridLayoutManager);
 *
 * StaggeredGridLayoutManager
 * // use a staggered grid layout manager
 * mStgaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
 * mRecyclerView.setLayoutManager(mStgaggeredGridLayoutManager);
 *
 *
 * Header/Footer을 포함?
 * Fail loading ?
 */
public class QcRecyclerView extends RecyclerView {
//    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    private Context context;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private StaggeredGridLayoutManager stgaggeredGridLayoutManager;


    EndlessRecyclerScrollListener endlessRecyclerScrollListener;
    private int transform = linear;
    private int orientation = VERTICAL;

    private int spanCount = 1;

    public static final int linear = 0;
    public static final int Grid = 1;
    public static final int StaggeredGrid = 2;


    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    private boolean reverseLayout = false;

    public interface QcRecyclerScrollListener {
        void onLoadMore(int page, int totalItemsCount, RecyclerView view);
    }

    private QcRecyclerScrollListener qcRecyclerScrollListener;

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
        initView();
        getAttrs(attrs);
        setLinearLayoutManager();
        setEndlessRecyclerScrollListener();
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
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView);
        setTypeArray(typedArray);
    }


//    private void getAttrs(AttributeSet attrs, int defStyle) {
//        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.QcAttrsRecyclerView, defStyle, 0);
//        setTypeArray(typedArray);
//    }

    private void setTypeArray(TypedArray typedArray) {

        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_orientation)) {
            orientation = typedArray.getInt(R.styleable.QcAttrsRecyclerView_orientation, 0);
        }

        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_spanCount)) {
            reverseLayout = typedArray.getBoolean(R.styleable.QcAttrsRecyclerView_spanCount, false);
        }

        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_spanCount)) {
            spanCount = typedArray.getInt(R.styleable.QcAttrsRecyclerView_spanCount, 1);
        }
        if (typedArray.hasValue(R.styleable.QcAttrsRecyclerView_layoutManager)) {
            transform = typedArray.getInt(R.styleable.QcAttrsRecyclerView_layoutManager, 0);
        }

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


    private void setLinearLayoutManager() {

        if (linear == orientation) {
            layoutManager = new LinearLayoutManager(getContext());
            layoutManager.setOrientation(orientation);
            setLayoutManager(layoutManager);

        } else if (Grid == orientation) {
            gridLayoutManager = new GridLayoutManager(getContext(), spanCount, orientation, reverseLayout);
            setLayoutManager(gridLayoutManager);

        } else if (StaggeredGrid == orientation) {
            stgaggeredGridLayoutManager = new StaggeredGridLayoutManager(spanCount,
                    StaggeredGridLayoutManager.VERTICAL);

            setLayoutManager(stgaggeredGridLayoutManager);

        }

    }

    private void setEndlessRecyclerScrollListener() {
        if (getLayoutManager() != null)
        endlessRecyclerScrollListener = new EndlessRecyclerScrollListener(getLayoutManager(), orientation) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                QcLog.i("onLoadMore =====");
                if (qcRecyclerScrollListener != null)
                    qcRecyclerScrollListener.onLoadMore(page, totalItemsCount, view);
            }
        };
    }


    public void setQcRecyclerScrollListener(QcRecyclerScrollListener qcRecyclerScrollListener) {
        this.qcRecyclerScrollListener = qcRecyclerScrollListener;
    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        if (linear == orientation) {
            return layoutManager;

        } else if (Grid == orientation) {
            return gridLayoutManager;
        } else if (StaggeredGrid == orientation) {
            return stgaggeredGridLayoutManager;

        }
        return layoutManager;
    }

    public int getOrientation() {
        return orientation;
    }

    public EndlessRecyclerScrollListener getEndlessRecyclerScrollListener() {
        return endlessRecyclerScrollListener;
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);
    }

    @Override
    public void setRecyclerListener(RecyclerListener listener) {
        super.setRecyclerListener(listener);
    }

    @Override
    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener listener) {
        super.addOnChildAttachStateChangeListener(listener);
    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
    }

    @Override
    public void addOnScrollListener(OnScrollListener listener) {
        super.addOnScrollListener(listener);
    }

    @Override
    public void removeOnScrollListener(OnScrollListener listener) {
        super.removeOnScrollListener(listener);
    }

    @Override
    public void addOnItemTouchListener(OnItemTouchListener listener) {
        super.addOnItemTouchListener(listener);
    }

    @Override
    public void removeOnItemTouchListener(OnItemTouchListener listener) {
        super.removeOnItemTouchListener(listener);
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        super.setNestedScrollingEnabled(enabled);
    }

}