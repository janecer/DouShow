package com.miyue.doushow.lib_base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

/**
 * Author:janecer
 * created on 2018/10/15
 */
public abstract class BaseMvpFragment<T extends BasePresent> extends BaseFragment implements BaseView {

    protected T mPresenter ;
    private BaseView baseViewImpl;
    private boolean initSuccess = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof BaseView) {
            this.baseViewImpl = (BaseView)context;
        } else {
            throw new RuntimeException("宿主Activity必须使用AbstractMvpActivity");
        }
    }

    public void onDetach() {
        super.onDetach();
        this.baseViewImpl = null;
        if (this.mPresenter != null) {
            this.mPresenter.onDestroy();
        }
    }

    @Override
    protected void initData() {
        this.mPresenter = this.newPresenter();
        if (this.mPresenter != null && this.getUserVisibleHint() && !this.initSuccess) {
            this.mPresenter.onStart();
            this.initSuccess = true;
        }
    }

    protected abstract T newPresenter();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!this.initSuccess && isVisibleToUser && this.mPresenter != null) {
            this.mPresenter.onStart();
            this.initSuccess = true;
        }
    }

    @Override
    public void showToastMessage(int msgResId) {
        showToast(msgResId);
    }

    @Override
    public void showToastMessage(String msg) {
        showToast(msg);
    }

    @Override
    public ProgressDialog showProgressDialog(String message) {
        return this.baseViewImpl != null ? this.baseViewImpl.showProgressDialog(message) : null;
    }

    @Override
    public void startLoading(int loadingId) {

    }

    @Override
    public void startLoading() {

    }

    @Override
    public void stopLoading() {

    }

    @Override
    public void dismissDialog() {
        if (this.baseViewImpl != null) {
            this.baseViewImpl.dismissDialog();
        }
    }

    @Override
    public void showSimpleDialog(String title, String message, String positiveString, DialogInterface.OnClickListener positiveListener, String negativeString, DialogInterface.OnClickListener negativeListener) {
        if (this.baseViewImpl != null) {
            this.baseViewImpl.showSimpleDialog(title, message, positiveString, positiveListener, negativeString, negativeListener);
        }
    }

    @Override
    public void setResultAndFinish(int result, Intent data) {
        if (this.baseViewImpl != null) {
            this.baseViewImpl.setResultAndFinish(result,data);
        }
    }

    @Override
    public Context getApplicationContext() {
        return this.getContext().getApplicationContext();
    }

    @Override
    public void sendBroadcast(Intent intent) {
        if(getActivity() != null) {
            this.getActivity().sendBroadcast(intent);
        }
    }

    @Override
    public void finish() {
        if(getActivity() != null) {
            this.getActivity().finish();
        }
    }

    @Override
    public BaseMvpActivity getActivityContext() {
        return (BaseMvpActivity) this.mContext;
    }
}
