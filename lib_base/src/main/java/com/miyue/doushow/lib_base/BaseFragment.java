package com.miyue.doushow.lib_base;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author:janecer
 * created on 2018/10/15
 */
public abstract class BaseFragment extends Fragment {

    protected BaseActivity mContext ;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (BaseActivity) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.mContext = null;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(this.setContentView(), container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        this.bindViews(view, savedInstanceState);
        this.initData();
    }

    protected void showToast(int msgRes) {
        if (this.mContext != null) {
            this.mContext.showToast(msgRes);
        }
    }

    protected void showToast(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (this.mContext != null) {
                this.mContext.showToast(msg);
            }
        }
    }

    protected abstract int setContentView();

    protected abstract void bindViews(View var1, Bundle var2);

    protected abstract void initData();
}
