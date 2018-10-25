package com.miyue.doushow;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.miyue.doushow.lib_base.lib.VLApplication;


/**
 * Author:janecer
 * created on 2018/10/17
 */
public class DSApplication extends VLApplication {


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();
//        Jzvd.setMediaInterface(new JZMediaIjkplayer());
    }
}
