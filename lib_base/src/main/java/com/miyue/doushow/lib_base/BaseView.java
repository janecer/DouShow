package com.miyue.doushow.lib_base;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;


public interface BaseView {

    void showToastMessage(int msgResId);

    void showToastMessage(String msg);

    ProgressDialog showProgressDialog(String message);

    void startLoading(int loadingId) ;

    void startLoading();

    void stopLoading();

    void dismissDialog();

    void showSimpleDialog(String title, String message, String positiveString, DialogInterface.OnClickListener positiveListener, String negativeString, DialogInterface.OnClickListener negativeListener);

    void setResultAndFinish(int result, Intent data);

    void startActivityForResult(Intent intent, int requestCode, Bundle bundle);

    void startActivity(Intent intent);

    Context getApplicationContext();

    void sendBroadcast(Intent intent);

    void finish();

    BaseMvpActivity getActivityContext();

}
