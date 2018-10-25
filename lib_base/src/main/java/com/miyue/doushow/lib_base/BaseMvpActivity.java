package com.miyue.doushow.lib_base;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

/**
 * Author:janecer
 * created on 2018/10/15
 */
public abstract class BaseMvpActivity<T extends BasePresent> extends BaseActivity implements BaseView {

    protected T mPresenter ;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mPresenter = newPresent() ;
    }

    protected abstract T newPresent();

    @Override
    protected void initData() {
        if(this.mPresenter != null){
            mPresenter.onStart();
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
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setMessage("加载中");
        }
        if (!isFinishing() && !mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
        return mProgressDialog;
    }

    @Override
    public void startLoading(int loadingId) {
        // TODO: 2018/10/15
    }

    @Override
    public void startLoading() {
        // TODO: 2018/10/15
    }

    @Override
    public void stopLoading() {
        // TODO: 2018/10/15
    }

    @Override
    public void dismissDialog() {
        if (!isFinishing() && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void showSimpleDialog(String title, String message, String positiveString, DialogInterface.OnClickListener positiveListener, String negativeString, DialogInterface.OnClickListener negativeListener) {
        AlertDialog dialog = (new AlertDialog.Builder(this.mContext)).setTitle(title).setMessage(message).setPositiveButton(positiveString, positiveListener).setNegativeButton(negativeString, negativeListener).create();
        dialog.setCancelable(true);
        dialog.show();
    }

    @Override
    public void setResultAndFinish(int result, Intent data) {
        setResult(result,data);
        this.finish();
    }

    @Override
    public BaseMvpActivity getActivityContext() {
        return this;
    }
}
