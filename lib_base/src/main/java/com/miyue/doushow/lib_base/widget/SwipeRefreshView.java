package com.miyue.doushow.lib_base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.miyue.doushow.lib_base.Lmsg;
import com.miyue.doushow.lib_base.R;


/**
 * 配合LoadMoreListView 完成下拉刷新、滑到底部自动加载更多
 *
 * @author zeda
 */
public class SwipeRefreshView extends SwipeRefreshLayout {

    private LoadMoreRecyclerView mLoadMoreListView;

    private View mScrollableChild;
    private int mScrollableChildId;

    @SuppressWarnings("deprecation")
    public SwipeRefreshView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setColorSchemeColors(context.getResources().getColor(R.color.colorPrimary));

        TypedArray a = context.obtainStyledAttributes(
                attrs, R.styleable.SwipeRefreshView);
        mScrollableChildId = a.getResourceId(R.styleable.SwipeRefreshView_scrollableChildId, 0);
        if (mScrollableChildId != 0)
            mScrollableChild = findViewById(mScrollableChildId);
        a.recycle();
    }

    public void setLoadMoreListView(LoadMoreRecyclerView mLoadMoreListView) {
        this.mLoadMoreListView = mLoadMoreListView;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mLoadMoreListView != null) {
            RecyclerView.Adapter adapter = mLoadMoreListView.getAdapter();
            if (adapter != null && adapter instanceof BaseLoadMoreViewAdapter && ((BaseLoadMoreViewAdapter) adapter).isLoading()){//正在加载更多，不能下拉刷新
                return false;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean canChildScrollUp() {
        if (mScrollableChild == null) {
            if (mLoadMoreListView != null)
                mScrollableChild = mLoadMoreListView;
            else if (mScrollableChildId != 0)
                mScrollableChild = findViewById(mScrollableChildId);
        }

        if (mScrollableChild == null) {
            Lmsg.i("canscrollup: true, child is null");
            return super.canChildScrollUp();
        }

        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mScrollableChild instanceof RecyclerView) {
                RecyclerView.LayoutManager layoutManager = ((RecyclerView) mScrollableChild).getLayoutManager();
                if (layoutManager instanceof GridLayoutManager) {
                    return layoutManager.getItemCount() > 0 && ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
                } else if (layoutManager instanceof android.support.v7.widget.LinearLayoutManager) {
                    boolean canScrollUp = layoutManager.getItemCount() > 0 && ((android.support.v7.widget.LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
                    Lmsg.i("canScrollUp: " + canScrollUp);
                    return canScrollUp;
                } else {
                    return layoutManager.getItemCount() > 0 && layoutManager.getChildAt(0).getTop() < mScrollableChild.getPaddingTop();
                }
            } else {
                if (mScrollableChild instanceof ViewGroup && ((ViewGroup) mScrollableChild).getChildAt(0) instanceof RecyclerView) {
                    RecyclerView.LayoutManager layoutManager = ((RecyclerView) ((ViewGroup) mScrollableChild).getChildAt(0)).getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        return layoutManager.getItemCount() > 0 && ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
                    } else if (layoutManager instanceof android.support.v7.widget.LinearLayoutManager) {
                        boolean canScrollUp = layoutManager.getItemCount() > 0 && ((android.support.v7.widget.LinearLayoutManager) layoutManager).findFirstVisibleItemPosition() > 0;
                        Lmsg.i("canScrollUp: " + canScrollUp);
                        return canScrollUp;
                    } else {
                        return layoutManager.getItemCount() > 0 && layoutManager.getChildAt(0).getTop() < mScrollableChild.getPaddingTop();
                    }
                } else {
                    return super.canChildScrollUp();
                }
            }
        } else {
            boolean canScrollUp = ViewCompat.canScrollVertically(mScrollableChild, -1);
            Lmsg.i("canScrollUp: " + canScrollUp);
            return canScrollUp;
        }

    }
}
