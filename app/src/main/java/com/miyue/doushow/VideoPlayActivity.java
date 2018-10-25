package com.miyue.doushow;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.doushow.tiaotiao.lib_base.BaseActivity;

import java.util.LinkedHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.JZDataSource;
import cn.jzvd.JzvdStd;

/**
 * Author:janecer
 * created on 2018/10/17
 */
public class VideoPlayActivity extends BaseActivity {


    @BindView(R.id.jz_video)
    JzvdStd mJzvdStd;



    @Override
    protected int setContentView() {
        return R.layout.activity_videoplay;
    }

    @Override
    protected void bindViews(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        LinkedHashMap map = new LinkedHashMap();
        map.put("高清", "http://jzvd.nathen.cn/4f965ad507ef4194a60a943a34cfe147/32af151ea132471f92c9ced2cff785ea-5287d2089db37e62345123a1be272f8b.mp4");
        map.put("标清", "http://jzvd.nathen.cn/6340efd1962946ad80eeffd19b3be89c/65b499c0f16e4dd8900497e51ffa0949-5287d2089db37e62345123a1be272f8b.mp4");
        map.put("普清", "http://jzvd.nathen.cn/6ea7357bc3fa4658b29b7933ba575008/fbbba953374248eb913cb1408dc61d85-5287d2089db37e62345123a1be272f8b.mp4");
        JZDataSource jzDataSource = new JZDataSource(map, "测试视频列表");
        jzDataSource.looping = true;
        jzDataSource.currentUrlIndex = 2;
        jzDataSource.headerMap.put("key", "value");//header
        mJzvdStd.setUp(jzDataSource
                , JzvdStd.SCREEN_WINDOW_NORMAL);
        Glide.with(this).load("http://jzvd-pic.nathen.cn/jzvd-pic/00b026e7-b830-4994-bc87-38f4033806a6.jpg").into(mJzvdStd.thumbImageView);
    }

    @Override
    protected void initData() {

    }

}
