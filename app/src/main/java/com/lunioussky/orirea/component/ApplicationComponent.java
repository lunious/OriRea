package com.lunioussky.orirea.component;

import android.content.Context;

import com.lunioussky.orirea.OriApp;
import com.lunioussky.orirea.module.ApplicationModule;
import com.lunioussky.orirea.module.HttpModule;
import com.lunioussky.orirea.net.JanDanApi;
import com.lunioussky.orirea.net.NewsApi;

import dagger.Component;

/**
 * desc: .
 * author: Will .
 * date: 2017/9/2 .
 */
@Component(modules = {ApplicationModule.class,HttpModule.class})
public interface ApplicationComponent {

    OriApp getApplication();

    NewsApi getNetEaseApi();

    JanDanApi getJanDanApi();

    Context getContext();

}
