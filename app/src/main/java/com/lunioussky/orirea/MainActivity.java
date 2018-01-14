package com.lunioussky.orirea;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.lunioussky.orirea.component.ApplicationComponent;
import com.lunioussky.orirea.ui.base.BaseActivity;
import com.lunioussky.orirea.ui.base.SupportFragment;
import com.lunioussky.orirea.ui.jandan.JanDanFragment;
import com.lunioussky.orirea.ui.personal.PersonalFragment;
import com.lunioussky.orirea.ui.news.NewsFragment;
import com.lunioussky.orirea.ui.video.VideoFragment;
import com.lunioussky.orirea.ui.welfare.WelfareFragment;
import com.lunioussky.orirea.utils.StatusBarUtil;
import com.lunioussky.orirea.widget.BottomBar;
import com.lunioussky.orirea.widget.BottomBarTab;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    @BindView(R.id.contentContainer)
    FrameLayout mContentContainer;
    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;

    private SupportFragment[] mFragments = new SupportFragment[5];


    @Override
    public int getContentLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }


    @Override
    public void bindView(View view, Bundle savedInstanceState) {
        StatusBarUtil.setTranslucentForImageViewInFragment(MainActivity.this, 0, null);
        if (savedInstanceState == null) {
            mFragments[0] = NewsFragment.newInstance();
            mFragments[1] = VideoFragment.newInstance();
            mFragments[2] = JanDanFragment.newInstance();
            mFragments[3] = WelfareFragment.newInstance();
            mFragments[4] = PersonalFragment.newInstance();

            getSupportDelegate().loadMultipleRootFragment(R.id.contentContainer, 0,
                    mFragments[0],
                    mFragments[1],
                    mFragments[2],
                    mFragments[3],
                    mFragments[4]);
        } else {
            mFragments[0] = findFragment(NewsFragment.class);
            mFragments[1] = findFragment(VideoFragment.class);
            mFragments[2] = findFragment(JanDanFragment.class);
            mFragments[3] = findFragment(WelfareFragment.class);
            mFragments[4] = findFragment(PersonalFragment.class);
        }

        mBottomBar.addItem(new BottomBarTab(this, R.mipmap.ic_news, "首页"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_video, "视频"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_jiandan, "趣事"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_fuli, "福利"))
                .addItem(new BottomBarTab(this, R.mipmap.ic_my, "我的"));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                getSupportDelegate().showHideFragment(mFragments[position], mFragments[prePosition]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {

            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onRetry() {

    }

    @Override
    public void onBackPressedSupport() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressedSupport();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
