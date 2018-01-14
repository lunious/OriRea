package com.lunioussky.orirea.ui.welfare;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.lunioussky.orirea.R;
import com.lunioussky.orirea.component.ApplicationComponent;
import com.lunioussky.orirea.ui.base.BaseFragment;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * Created by 11645 on 2018/1/13.
 */

public class WelfareFragment extends BaseFragment {


    @BindView(R.id.fake_status_bar)
    View fakeStatusBar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    Unbinder unbinder;

    public static WelfareFragment newInstance() {
        Bundle args = new Bundle();
        WelfareFragment fragment = new WelfareFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public int getContentLayout() {
        return R.layout.fragment_welfare;
    }

    @Override
    public void initInjector(ApplicationComponent appComponent) {

    }

    @Override
    public void bindView(View view, Bundle savedInstanceState) {

    }

    @Override
    public void initData() {

    }


}
