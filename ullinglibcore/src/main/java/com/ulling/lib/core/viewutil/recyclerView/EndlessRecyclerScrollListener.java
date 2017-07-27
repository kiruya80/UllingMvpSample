package com.ulling.lib.core.viewutil.recyclerView;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * Created by P100651 on 2017-04-27.
 */
public abstract class EndlessRecyclerScrollListener extends RecyclerView.OnScrollListener {
    private RecyclerView.LayoutManager mLayoutManager;
    // The minimum amount of items to have below your current scroll position
    // before loading more.
    private int visibleThreshold = 5;
    // The current offset index of data you have loaded
    private int currentPage = 0;
    // The total number of items in the dataset after the last load
    private int previousTotalItemCount = 0;
    // True if we are still waiting for the last set of data to load.
    private boolean loading = true;
    // Sets the starting page index
    private int startingPageIndex = 0;
    private boolean isLastItem = false;

    // Defines the process for actually loading more data based on page
    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

    public abstract void onLoadEnd();

    // Call this method whenever performing new searches
    public void resetState() {
//        this.currentPage = this.startingPageIndex;
        this.currentPage = 0;
        this.previousTotalItemCount = 0;
        this.loading = true;
        this.isLastItem = false;
    }

    public void setStartingPageIndex(int startingPageIndex) {
//        this.currentPage = startingPageIndex;
        this.startingPageIndex = startingPageIndex;
    }

    public EndlessRecyclerScrollListener(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int spanCount = staggeredGridLayoutManager.getSpanCount();
            visibleThreshold = visibleThreshold * spanCount;
            this.mLayoutManager = (StaggeredGridLayoutManager) layoutManager;
        } else if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            int spanCount = gridLayoutManager.getSpanCount();
            visibleThreshold = visibleThreshold * spanCount;
            this.mLayoutManager = (GridLayoutManager) layoutManager;
        } else if (layoutManager instanceof LinearLayoutManager) {
            this.mLayoutManager = (LinearLayoutManager) layoutManager;
        }
    }
//    public EndlessRecyclerScrollListener(GridLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
//    }
//
//    public EndlessRecyclerScrollListener(StaggeredGridLayoutManager layoutManager) {
//        this.mLayoutManager = layoutManager;
//        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
//    }
//
//    public EndlessRecyclerScrollListener(RecyclerView.LayoutManager layoutManager, int orientation) {
//        this.mLayoutManager = layoutManager;
//        if (Grid == orientation) {
//            GridLayoutManager gridLayoutManager = (GridLayoutManager) mLayoutManager;
//            int spanCount = gridLayoutManager.getSpanCount();
//            visibleThreshold = visibleThreshold * spanCount;
//        } else if (StaggeredGrid == orientation) {
//            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) mLayoutManager;
//            int spanCount = staggeredGridLayoutManager.getSpanCount();
//            visibleThreshold = visibleThreshold * spanCount;
//        }
//    }

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
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
//        if (totalItemCount == 0)
//            return;
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
        // If the total item count is zero and the previous isn't, assume the
        // list is invalidated and should be reset back to initial state
        if (totalItemCount < previousTotalItemCount) {
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }
        // If it’s still loading, we check to see if the dataset count has
        // changed, if so we conclude it has finished loading and update the current page
        // number and total item count.
        if (loading && (totalItemCount > previousTotalItemCount)) {
            loading = false;
            previousTotalItemCount = totalItemCount;
        }
        isLastItem = false;
        // If it isn’t currently loading, we check to see if we have breached
        // the visibleThreshold and need to reload more data.
        // If we do need to reload some more data, we execute onLoadMore to fetch the data.
        // threshold should reflect how many total columns there are too
        if (totalItemCount != 0 && !loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
//            QcLog.e("lastVisibleItemPosition == " + lastVisibleItemPosition + " , visibleThreshold == " + visibleThreshold + " , totalItemCount == " + totalItemCount);
            /**
             * more
             *
             * lastVisibleItemPosition =====26 , visibleThreshold= 5 , totalItemCount= 30
             */
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
        /**
         * find end
         *
         * lastVisibleItemPosition =====29 , visibleThreshold= 5 , totalItemCount= 30
         */
        if (totalItemCount != 0 && lastVisibleItemPosition >= (totalItemCount - 1)) {
//            QcLog.e("lastVisibleItemPosition == " + lastVisibleItemPosition + " , visibleThreshold == " + visibleThreshold + " , totalItemCount == " + totalItemCount + " , loading == " + loading);
            isLastItem = true;
        }
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (isLastItem && newState == RecyclerView.SCROLL_STATE_IDLE) {
            onLoadEnd();
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