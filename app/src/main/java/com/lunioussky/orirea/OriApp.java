package com.lunioussky.orirea;


import com.lunioussky.orirea.component.ApplicationComponent;
import com.lunioussky.orirea.component.DaggerApplicationComponent;
import com.lunioussky.orirea.module.ApplicationModule;
import com.lunioussky.orirea.module.HttpModule;
import com.lunioussky.orirea.utils.ContextUtils;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackManager;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
public class OriApp extends LitePalApplication {

    private ApplicationComponent mApplicationComponent;

    private static OriApp sMyApp;

    public static int width = 0;

    public static int height = 0;


    @Override
    public void onCreate() {
        super.onCreate();
        sMyApp = this;
        BGASwipeBackManager.getInstance().init(this);
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .httpModule(new HttpModule())
                .build();
        LitePal.initialize(this);
        width = ContextUtils.getSreenWidth(OriApp.getContext());
        height = ContextUtils.getSreenHeight(OriApp.getContext());

    }

    public static OriApp getInstance() {
        return sMyApp;
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

}
