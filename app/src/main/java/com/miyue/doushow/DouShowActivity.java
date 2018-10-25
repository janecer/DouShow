package com.miyue.doushow;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.TextView;

import com.miyue.doushow.lib_base.BaseActivity;
import com.miyue.doushow.lib_base.Lmsg;
import com.miyue.doushow.lib_base.callback.NoDoubleClickListener;
import com.miyue.doushow.lib_base.widget.BaseLoadMoreViewAdapter;
import com.miyue.doushow.lib_base.widget.BaseRecViewHolder;
import com.miyue.doushow.lib_base.widget.LoadMoreRecyclerView;
import com.miyue.doushow.lib_base.widget.RefreshLoadMoreView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author:janecer
 * created on 2018/10/16
 */
public class DouShowActivity extends BaseActivity {
    private static final String TAG = "DouShowActivity";
    @BindView(R.id.sample_text)
    TextView sampleText;
    @BindView(R.id.refresh_view)
    RefreshLoadMoreView refreshView;

    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        sampleText.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void noDoubleClick(View view) {
                startActivity(new Intent(mContext,VideoPlayActivity.class));
            }
        });

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("");
        }
        refreshView.setAdapter(new BaseLoadMoreViewAdapter<String>(this, R.layout.item_refreshload_test, list) {


            @Override
            public void onBindViewHolder(BaseRecViewHolder holder, String data, int position) {

            }
        });

        refreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshView.setRefreshing(false);
                    }
                }, 3000);
            }
        });

        refreshView.setOnLoadMoreListener(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                Lmsg.i(TAG, " onLoadMore____");
            }
        });
    }

    @Override
    protected void initData() {

    }

}
