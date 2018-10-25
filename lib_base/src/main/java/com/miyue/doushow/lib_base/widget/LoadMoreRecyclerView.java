package com.miyue.doushow.lib_base.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;


/**
 * 配合HMSwipeRefreshView 完成下拉刷新、滑到底部自动加载更多(暂未做横列兼容)
 *
 * @author zeda
 */
public class LoadMoreRecyclerView extends RecyclerView {

    private SwipeRefreshView mRefreshView;

    private OnLoadMoreListener mOnLoadMoreListener;


    public LoadMoreRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        android.support.v7.widget.LinearLayoutManager manager = new android.support.v7.widget.LinearLayoutManager(context);
        manager.setOrientation(android.support.v7.widget.LinearLayoutManager.VERTICAL);
        setLayoutManager(manager);
        addOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (mRefreshView != null) {
                    mRefreshView.setEnabled(((android.support.v7.widget.LinearLayoutManager) getLayoutManager()).findFirstCompletelyVisibleItemPosition() == 0);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                android.support.v7.widget.LinearLayoutManager manager = (android.support.v7.widget.LinearLayoutManager) getLayoutManager();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.Adapter adapter = getAdapter();
                    BaseLoadMoreViewAdapter loadMoreViewAdapter = null;

                    if (adapter == null || !(adapter instanceof BaseLoadMoreViewAdapter))//不是加载更多adapter不处理
                        return;
                    else
                        loadMoreViewAdapter = (BaseLoadMoreViewAdapter) adapter;

                    int last = manager.findLastCompletelyVisibleItemPosition();
                    int total = manager.getItemCount();

                    if (last == total - 1 && (mRefreshView == null || !mRefreshView
                            .isRefreshing()) && !loadMoreViewAdapter.isLoading()
                            && mOnLoadMoreListener != null && loadMoreViewAdapter.isHaveMoreData()) {
                        loadMoreViewAdapter.setIsLoading(true);
                        mOnLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }


    public void setRefreshView(SwipeRefreshView mRefreshMoreView) {
        this.mRefreshView = mRefreshMoreView;
    }

    /**
     * 加载更多时间监听
     *
     * @author zeda
     */
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }


}
