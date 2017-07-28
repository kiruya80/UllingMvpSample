package com.ulling.lib.core.viewutil.recyclerView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.ulling.lib.core.util.QcLog;

/**
 * Created by P100651 on 2017-04-27.
 */
public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    /**
     * mLayoutManager 에서 가져오는 값들정리
     */
    // The minimum amount of items to have below your current scroll position
    // before loading more.
//    private int visibleThreshold = 3;
    private int visibleItemCount;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    private int spanCount = 0;
    /**
     * 뷰의 네트워크데이터들에 의해 변화될수 있는 값들
     */

    // Sets the starting page index
    private int startingPageIndex = 1;
    // The current offset index of data you have loaded
    private int currentPage = 1;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = false;
    private boolean hasNextPage = true;
    private boolean networkError = false;
    /**
     * 고정영역이 있는 경우
     */
    private int visibleThreshold = 0;
    private int viewStartingPageIndex = 1;
    private int viewCurrentPage = 1;
    /**
     * 엣지 타입
     */
    public static final int EDGE_TYPE_NONE = 0;
    public static final int EDGE_TYPE_TOP = 1;
    public static final int EDGE_TYPE_BOTTOM = 2;
    private int isEdgetype = EDGE_TYPE_NONE;

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    public abstract void onLoadEnd();

    public interface QcScrollDataListener {
        void onStartingPageIndex(int viewStartingPageIndex_);

        void onCurrentPage(int viewCurrentPage_);

        void onResetStatus();

        void onNetworkLoading(boolean loading_);

        void onNextPage(boolean hasNextPage_);
        void onNetworkError(boolean networkError_);
    }

    // Call this method whenever performing new searches
    private void resetStatus() {
        startingPageIndex = viewStartingPageIndex;
        currentPage = this.viewCurrentPage;
        loading = false;
        hasNextPage = true;
    }

    /**
     * 리사이클뷰를 가진 프레그먼트(액티비티)에서
     * 다음 페이지를 가져올때 상태값을 리스너에 저장
     */
    public QcScrollDataListener qcScrollDataListener = new QcScrollDataListener() {
        @Override
        public void onStartingPageIndex(int viewStartingPageIndex_) {
            QcLog.e("onStartingPageIndex =====" + viewStartingPageIndex_);
            viewStartingPageIndex = viewStartingPageIndex_;
        }

        @Override
        public void onCurrentPage(int viewCurrentPage_) {
            QcLog.e("onCurrentPage =====" + viewCurrentPage_);
            viewCurrentPage = viewCurrentPage_;
        }

        @Override
        public void onNetworkLoading(boolean loading_) {
             QcLog.e("onNetworkLoading =====" + loading_);
            loading = loading_;
        }

        @Override
        public void onNextPage(boolean hasNextPage_) {
            QcLog.e("onNextPage =====" + hasNextPage_);
            hasNextPage = hasNextPage_;
        }

        @Override
        public void onResetStatus() {
            resetStatus();
        }
        @Override
        public void onNetworkError(boolean networkError_) {
            QcLog.e("onNetworkError =====" + networkError_);
            networkError = networkError_;
        }
    };

    public QcScrollDataListener getQcScrollDataListener() {
        return qcScrollDataListener;
    }
//    public void setStartingPageIndex(int startingPageIndex) {
//        this.startingPageIndex = startingPageIndex;
//    }

    /**
     * 생성자
     *
     * @param layoutManager
     */
    public EndlessRecyclerScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            spanCount = staggeredGridLayoutManager.getSpanCount();
//            visibleThreshold = visibleThreshold * spanCount;
            this.mLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            spanCount = gridLayoutManager.getSpanCount();
//            visibleThreshold = visibleThreshold * spanCount;
            this.mLayoutManager = (GridLayoutManager) layoutManager;
        } else if (layoutManager instanceof LinearLayoutManager) {
            this.mLayoutManager = (LinearLayoutManager) layoutManager;
        }
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            } else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        return maxSize;
    }

    // This happens many times a second during a scroll, so be wary of the code you place here.
    // We are given a few useful parameters to help us work out if we need to load some more data,
    // but first we check if we are waiting for the previous load to finish.
    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        if (mLayoutManager == null)
            return;
        /**
         * 화면에서 아래쪽에 보이는 포지션뷰
         * 0부터 시작
         * */
        int lastVisibleItemPosition = 0;
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
            // get maximum element within the list
            lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else {
            return;
        }
        /**
         * 화면에 보이는 뷰 갯수
         * spanCount가 있는 경우
         * */
        if (spanCount == 0) {
            visibleItemCount = mLayoutManager.getChildCount();
        } else {
            visibleItemCount = mLayoutManager.getChildCount() * spanCount;
        }
        /**
         * 아이템 총 갯수
         * 아이템이 없는 경우 return;
         */
        int totalItemCount = mLayoutManager.getItemCount();
        if (totalItemCount <= 0) {
            return;
        }
        QcLog.e("lastVisibleItemPosition == " + lastVisibleItemPosition
                + " , visibleItemCount = " + visibleItemCount
                + " , totalItemCount == " + totalItemCount
                + " , hasNextPage == " + hasNextPage+ " , loading == " + loading+ " , networkError == " + networkError);
        /**
         * 화면에 보이는 아이템이 마지막 아이템보다 큰 경우
         * 즉, 화면에 보이는 갯수가 적은 경우는 패스한다
         *
         */
        if (visibleItemCount == totalItemCount && (lastVisibleItemPosition + 1) <= visibleItemCount) {
            QcLog.e("총갯수와 화면에 보이는 갯수가 같고, 화면에 보이는 갯수가 화면 마지막 포지션보다 큰경우 Pass =====");
            isEdgetype = EDGE_TYPE_NONE;
            return;
        }
        /**
         * If the total item count is zero and the previous isn't, assume the
         * list is invalidated and should be reset back to initial state
         *
         * 이전 아이템 총갯수와 현재 아이템 총 갯수 비교
         *
         * 아이템이 줄어든 경우??
         */
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
//            if (totalItemCount == 0) {
//                this.loading = true;
//            }
        } else if (totalItemCount > previousTotalItemCount) {
            /**
             * If it’s still loading, we check to see if the dataset count has
             *  changed, if so we conclude it has finished loading and update the current page
             *  number and total item count.
             *
             * 로딩중인 상태
             * 아이템이 늘어난 경우 로딩이 완료되었다고 처리?
             */
//            if (this.loading) {
//                this.loading = false;
//                previousTotalItemCount = totalItemCount;
//            }
        }
        /**
         * If it isn’t currently loading, we check to see if we have breached
         * the visibleThreshold and need to reload more data.
         * If we do need to reload some more data, we execute onLoadMore to fetch the data.
         *  threshold should reflect how many total columns there are too
         *
         * (totalItemCount - visibleItemCount) 남은 보여줄 데이터 갯수
         * (lastVisibleItemPosition + visibleThreshold) 마지막 보여주고 있는 포지션
         *
         * ###### visibleItemCount 갯수만큼 데이터가 남아잇는 경우 미리 데이터를 로딩한다 ######
         *
         */
//        if (hasNextPage && !loading && (lastVisibleItemPosition + visibleItemCount) > totalItemCount) {
        if (hasNextPage && !loading && !networkError
                && (totalItemCount - visibleItemCount) <= (lastVisibleItemPosition + visibleThreshold)) {
            /**
             * more
             *
             * lastVisibleItemPosition =====26 , visibleThreshold= 5 , totalItemCount= 30
             */
            QcLog.e("onLoadMore ======================== " + currentPage + " , loading = " + loading);
            currentPage++;
            loading = true;
            QcLog.e("onLoadMore  마지막, 더보기 호출  == " + currentPage + " , loading = " + loading);
            onLoadMore(currentPage, totalItemCount, view);
        }
        isEdgetype = EDGE_TYPE_NONE;
        /**
         * find end
         * 마지막 끝에 닿는 경우 체크
         * 단, 로딩하고 곂치는 경우 처리를 어떻게 할것인지
         *
         * lastVisibleItemPosition =====29 , visibleThreshold= 5 , totalItemCount= 30
         */
        if (!loading && lastVisibleItemPosition > visibleItemCount && lastVisibleItemPosition >= (totalItemCount - 1)) {
//            if (!loading &&  lastVisibleItemPosition >= (totalItemCount - 1)) {
            QcLog.e("로딩중이 아닌경우, 마지막일때 == ");
            isEdgetype = EDGE_TYPE_BOTTOM;
            return;
        }
//        if (lastVisibleItemPosition == visibleItemCount) {
//            QcLog.e("최상단인 경우  == " );
//            isEdgetype = EDGE_TYPE_TOP;
//        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            QcLog.e("isEdgetype == " + isEdgetype);
            if (isEdgetype == EDGE_TYPE_TOP) {
            } else if (isEdgetype == EDGE_TYPE_BOTTOM) {
                onLoadEnd();
            }
        }
    }
//    private LinearLayoutManager mLayoutManager;
//    //    RecyclerView.LayoutManager mLayoutManager;
//    private Boolean isLastItem = false;
//    private boolean isMoreLoading = false;
//    private boolean hasNextPage = false;
//    private int visibleThreshold = 1;
//    private int firstVisibleItem;
//    private int visibleItemCount;
//    private int totalItemCount;
//
//    public abstract void onLoadMore();
//
//    public abstract void onLastPage();
//    // Defines the process for actually loading more data based on page
////    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);
//
//    public EndlessScrollListener(LinearLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//    }
//
//    public EndlessScrollListener(GridLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//    }
////    public EndlessScrollListener(StaggeredGridLayoutManager layoutManager) {
////        this.mLayoutManager = layoutManager;
////    }
//
//    @Override
//    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//        super.onScrolled(recyclerView, dx, dy);
//        visibleItemCount = recyclerView.getChildCount();
//        totalItemCount = mLayoutManager.getItemCount();
//        firstVisibleItem = mLayoutManager.findFirstVisibleItemPosition();
//        if (!isMoreLoading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
//            if (hasNextPage) {
//                onLoadMore();
//                isMoreLoading = true;
//            }
//            isLastItem = true;
//        } else {
//            isLastItem = false;
//        }
//    }
//
//    @Override
//    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//        super.onScrollStateChanged(recyclerView, newState);
//        if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
//            onLastPage();
//        }
//    }
//
//    // Call this method whenever performing new searches
//    public void resetState() {
//        this.isLastItem = false;
//        this.isMoreLoading = false;
//        this.hasNextPage = false;
//        this.visibleThreshold = 1;
//    }
//
//    public void setMoreLoading(boolean isMoreLoading) {
//        this.isMoreLoading = isMoreLoading;
//    }
//
//    public void setHasNextPage(boolean hasNextPage) {
//        this.hasNextPage = hasNextPage;
//    }
}