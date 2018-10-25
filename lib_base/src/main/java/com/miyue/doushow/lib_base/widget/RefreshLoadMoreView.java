package com.miyue.doushow.lib_base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.miyue.doushow.lib_base.R;

/**
 * 下拉刷新上拉加载更多
 * Created by zeda on 16/1/15.
 */
public class RefreshLoadMoreView extends FrameLayout {
    private SwipeRefreshView mRefresh;
    private LoadMoreRecyclerView mLoadMore;

    public RefreshLoadMoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    public RefreshLoadMoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public RefreshLoadMoreView(Context context) {
        super(context);
        init(context, null);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RefreshLoadMoreView, 0, 0);
        boolean defaultDivider = a.getBoolean(R.styleable.RefreshLoadMoreView_defaultDivider, false);
        a.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.view_refresh_load_more, this, false);
        mRefresh = (SwipeRefreshView) view.findViewById(R.id.refresh_view);
        mLoadMore = (LoadMoreRecyclerView) view.findViewById(R.id.load_more);

        setDefaultDivider(defaultDivider);
        mRefresh.setLoadMoreListView(mLoadMore);
        mLoadMore.setRefreshView(mRefresh);
        addView(view);
    }

    public void setDefaultDivider(boolean enable) {
        if (enable) {
            mLoadMore.addItemDecoration(new DividerItemDecoration(getContext(), android.support.v7.widget.LinearLayoutManager.VERTICAL));
        }
    }

    public void setAdapter(BaseLoadMoreViewAdapter adapter) {
        mLoadMore.setAdapter(adapter);
    }

    public void setRefreshing(boolean isRefreshing) {
        mRefresh.setRefreshing(isRefreshing);
    }


    public void setOnRefreshListener(SwipeRefreshLayout.OnRefreshListener onRefreshListener) {
        mRefresh.setOnRefreshListener(onRefreshListener);
    }

    public void setOnLoadMoreListener(LoadMoreRecyclerView.OnLoadMoreListener onLoadMoreListener) {
        mLoadMore.setOnLoadMoreListener(onLoadMoreListener);
    }

    public LoadMoreRecyclerView getLoadMoreRecyclerView() {
        return mLoadMore;
    }

    public void setHasFixedSize(boolean hasFixedSize) {
        mLoadMore.setHasFixedSize(hasFixedSize);
    }
}
