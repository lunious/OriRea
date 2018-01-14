package com.lunioussky.orirea.module;

import android.content.Context;

import com.lunioussky.orirea.OriApp;

import dagger.Module;
import dagger.Provides;

/**
 * desc:
 * author: Will .
 * date: 2017/9/2 .
 */
@Module
public class ApplicationModule {

    private Context mContext;

    public ApplicationModule(Context context) {
        this.mContext = context;
    }

    @Provides
    OriApp provideApplication() {
        return (OriApp) mContext.getApplicationContext();
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
