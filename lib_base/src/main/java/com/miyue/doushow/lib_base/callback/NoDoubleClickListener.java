package com.miyue.doushow.lib_base.callback;

import android.view.View;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    private long lastClickTime = 0;

    @Override
    public synchronized void onClick(View v) {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500){
            return;
        }
        lastClickTime = time;
        noDoubleClick(v);
    }


    public abstract void noDoubleClick(View view) ;
}
