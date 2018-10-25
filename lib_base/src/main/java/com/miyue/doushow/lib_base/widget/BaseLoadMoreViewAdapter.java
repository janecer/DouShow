package com.miyue.doushow.lib_base.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.miyue.doushow.lib_base.R;
import com.miyue.doushow.lib_base.callback.NoDoubleClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础的加载更多adapter
 * Created by zeda on 15/12/5.
 */
public abstract class BaseLoadMoreViewAdapter<T> extends RecyclerView.Adapter<BaseRecViewHolder> {
    public static final int PAGE_COUNT = 20 ;

    private View footerView;

    public static final int FOOTER_VIEW_TYPE = -2000;

    private boolean isHaveMoreData = true;// 是否有更多数据(默认为有);

    private View lineView;
    private ProgressBar progressBar;
    private TextView tipContent;

    private boolean isLoading = false;// 是否正在加载

    private int onePageNum = PAGE_COUNT;//一页显示的数量

    protected List<T> list;
    protected Context mContext;
    protected OnItemClickListener<T> onItemClickListener;
    private OnItemLongClickListener<T> onItemLongClickListener;
    private int mItemLayoutRes;

    private String tipByNotHaveMore = "只有这么多啦";
    private String tipByLoading = "正在加载...";
    private String tipByNotData = "暂无数据";
    private String tipByLoadFailure = "加载失败";

    public BaseLoadMoreViewAdapter(Context context, int itemLayout, List<T> list) {
        mContext = context;
        this.list = list;
        mItemLayoutRes = itemLayout;
        if (this.list == null) {
            this.list = new ArrayList<>();
        }
    }

    @Override
    public BaseRecViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isFooter(viewType)) {
            createFooterView(parent);
            return new BaseRecViewHolder(footerView);
        } else {
            View view = LayoutInflater.from(mContext).inflate(mItemLayoutRes, parent, false);
            changeViewSize(view);
            final BaseRecViewHolder holder = new BaseRecViewHolder(view);

            view.setOnClickListener(new NoDoubleClickListener() {
                @Override
                public void noDoubleClick(View view) {
                    final int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= getListSize()) return;
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClickDeal(holder, get(adapterPosition), adapterPosition);
                    }
                    onClickHook(holder, adapterPosition);
                }
            });
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    final int adapterPosition = holder.getAdapterPosition();
                    if (adapterPosition < 0 || adapterPosition >= getListSize()) return true;
                    if (onItemLongClickListener != null) {
                        onItemLongClickListener.onItemLongClick(holder, get(adapterPosition), adapterPosition);
                    }
                    return true;
                }
            });

            return holder;
        }
    }

    protected void changeViewSize(View rootView) {

    }

    protected void onClickHook(BaseRecViewHolder holder, int adapterPosition) {

    }

    @Override
    public void onBindViewHolder(BaseRecViewHolder holder, int position) {
        if (position < getListSize()){
            onBindViewHolder(holder, get(position), position);
        }
    }

    public abstract void onBindViewHolder(BaseRecViewHolder holder, T data, int position);

    @Override
    public int getItemCount() {
        return getListSize() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < getListSize()) {
            return super.getItemViewType(position);
        } else {
            return FOOTER_VIEW_TYPE;
        }
    }

    private void createFooterView(ViewGroup viewGroup) {
        footerView = LayoutInflater.from(mContext).inflate(R.layout.pull_to_load_footer, viewGroup, false);
        lineView = footerView.findViewById(R.id.line_layout);
        progressBar = (ProgressBar) footerView
                .findViewById(R.id.pull_to_load_footer_progressbar);
        tipContent = (TextView) footerView
                .findViewById(R.id.pull_to_load_footer_hint_textview);
    }


    public void setFooterVisibility(boolean shouldShow) {
        footerView.setVisibility(shouldShow ? View.VISIBLE : View.GONE);
    }

    public View getFooter() {
        return footerView;
    }


    private boolean isFooter(int viewType) {
        return viewType == FOOTER_VIEW_TYPE;
    }

    /**
     * 是否有更多数据(默认为有)
     */
    public boolean isHaveMoreData() {
        return isHaveMoreData;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public boolean isLoading() {
        return isLoading;
    }

    public void setOnePageNum(int onePageNum) {
        this.onePageNum = onePageNum;
    }

    public String getTipByNotHaveMore() {
        return tipByNotHaveMore;
    }

    public void setTipByNotHaveMore(String tipByNotHaveMore) {
        this.tipByNotHaveMore = tipByNotHaveMore;
    }

    public String getTipByLoading() {
        return tipByLoading;
    }

    public void setTipByLoading(String tipByLoading) {
        this.tipByLoading = tipByLoading;
    }

    public String getTipByNotData() {
        return tipByNotData;
    }

    public void setTipByNotData(String tipByNotData) {
        this.tipByNotData = tipByNotData;
    }

    public String getTipByLoadFailure() {
        return tipByLoadFailure;
    }

    public void setTipByLoadFailure(String tipByLoadFailure) {
        this.tipByLoadFailure = tipByLoadFailure;
    }

    /**
     * 显示底部加载View和listView分割线
     */
    public void showLineView() {
        if (lineView != null)
            lineView.setVisibility(View.VISIBLE);
    }

    /**
     * 隐藏底部加载View和listView分割线
     */
    public void hiddenLineView() {
        if (lineView != null)
            lineView.setVisibility(View.GONE);
    }

    public T get(int position) {
        if (position >= getListSize())
            return null;
        return list.get(position);
    }

    public List<T> getList() {
        return list;
    }

    public int getListSize() {
        return list.size();
    }

    public void add(T data) {
        int position = getListSize();
        list.add(data);
        if (position == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(getListSize() - 1);
        }
    }

    public void addAll(List<T> addDatas) {
        if (addDatas == null) {
            return;
        }
        int position = getListSize();
        list.addAll(addDatas);

        if (position == 0) {
            notifyDataSetChanged();
        } else {
            notifyItemRangeInserted(position, addDatas.size());
        }
    }

    public void addAll(int position, List<T> addList) {
        if (addList == null) {
            return;
        }
        list.addAll(position, addList);
        notifyDataSetChanged();
    }

    public void remove(T t) {
        int position = list.indexOf(t);
        if (position != -1) {
            list.remove(t);
            notifyItemRemoved(position);
        }
    }

    public void reset() {
        list.clear();
        notifyDataSetChanged();
    }

    public void replace(List<T> newDatas) {
        if (newDatas == null) {
            newDatas = new ArrayList<>();
        }
        list.clear();
        list.addAll(newDatas);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<T> listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    /**
     * 防止快速多次点击（如果不需要，请实现onItemClickDeal方法）
     */
    public static abstract class OnItemClickListener<T> {
        private long lastClickTime = 0;

        public void onItemClickDeal(BaseRecViewHolder holder, T data, int position) {
            long time = System.currentTimeMillis();
            if (time - lastClickTime < 500)
                return;
            lastClickTime = time;
            onItemClick(holder, data, position);
        }

        public abstract void onItemClick(BaseRecViewHolder holder, T data, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(BaseRecViewHolder holder, T data, int position);
    }
}
