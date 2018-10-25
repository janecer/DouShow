package com.miyue.doushow.lib_base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;

import com.miyue.doushow.lib_base.lib.VLActivity;

import java.util.List;

/**
 * Author:janecer
 * created on 2018/10/15
 */
public abstract class BaseActivity extends VLActivity {

    protected BaseActivity mContext;
    protected FragmentManager mFragmentMgr;
    protected FragmentTransaction mFragmentTransaction;
    private Fragment mCurrFragment;
    boolean isFirstEntry = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseSetContentView();
        mContext = this ;
        bindViews(savedInstanceState);
    }

    /**
     * 设置contentView方法
     */
    protected void baseSetContentView() {
        setContentView(setContentView());
    }

    protected abstract int setContentView();

    protected abstract void bindViews(Bundle savedInstanceState);

    protected abstract void initData();

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && isFirstEntry) {
            initData();
            isFirstEntry = false;
        }
    }

    public void showToast(String msg) {
        ToastUtils.show(this.getApplicationContext(),msg);
    }

    public void showToast(int msgResId) {
        ToastUtils.show(this.getApplicationContext(),msgResId);
    }

    /**
     * 展示fragment，允许其他fragment同时存在。用于一个Activity多个Fragment模块的情况
     *
     * @param fragment    fragment
     * @param containerId fragment的容器ID
     */
    public void showFragmentAllowOtherExist(Fragment fragment, int containerId) {
        showFragment(fragment, false, false, containerId);
    }

    /**
     * 展示Fragment，调用此方法，请确保Fragment的容器id为fl_container
     *
     * @param fragment       fragment
     * @param addToBackStack 是否需要加入到回退栈
     * @param hide           是否会隐藏其他Fragment
     * @param containerId    容器ID
     */
    public void showFragment(Fragment fragment, boolean addToBackStack, boolean hide, int containerId) {

        //容器检查
        View containerView = findViewById(containerId);
        if (containerView == null || !(containerView instanceof ViewGroup)) {
            throw new IllegalArgumentException("containerId必须是一个有效的容器ID");
        }

        if (fragment == null) {
            return;
        }

        if (fragment.isAdded() && mCurrFragment == fragment) {
            Lmsg.w("showFragment", "fragment has show");
            return;
        }

        if (mFragmentMgr == null) {
            mFragmentMgr = getSupportFragmentManager();
        }

        // 如果activity已经销毁，不往下处理
        if (mFragmentMgr.isDestroyed()) {
            return;
        }

        mFragmentTransaction = mFragmentMgr.beginTransaction();

        // 如果当前页面需要隐藏
        if (null != mCurrFragment && hide) {
            mFragmentTransaction.hide(mCurrFragment);
        }

        // 如果页面未添加，则添加；如果已经添加则显示
        if (fragment.isAdded()) {
            mFragmentTransaction.show(fragment);
        } else {
            mFragmentTransaction.add(containerId, fragment);
        }

        // 是否需要加入任务栈
        if (addToBackStack) {
            mFragmentTransaction.addToBackStack(null);
        }

        mFragmentTransaction.commitAllowingStateLoss();//commitAllowingStateLoss代替commit
        mCurrFragment = fragment;
    }

    /**
     * 管理fragment栈，后退动作
     */
    public void popBackStack() {
        if (mFragmentMgr == null) {
            finish();
            return;
        }
        List<Fragment> fragments = mFragmentMgr.getFragments();
        if (fragments == null || fragments.size() == 0) {
            finish();
            return;
        }
        int position = fragments.indexOf(mCurrFragment);
        if (position == 0) {
            finish();
        } else {
            mCurrFragment = fragments.get(position - 1);
            mFragmentMgr.popBackStack();
        }
    }

    /**
     * 获取当前展示的fragment
     */
    public Fragment getCurrentShowFragment() {
        return mCurrFragment;
    }
}
