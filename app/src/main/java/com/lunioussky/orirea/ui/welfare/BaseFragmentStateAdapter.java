package com.lunioussky.orirea.ui.welfare;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 11645 on 2018/1/14.
 */

public class BaseFragmentStateAdapter extends FragmentStatePagerAdapter {

    List<Fragment> fragmentList = new ArrayList<Fragment>();
    private List<String> mTitles;

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    public BaseFragmentStateAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> mTitles) {
        super(fm);
        this.mTitles = mTitles;
        this.fragmentList = fragmentList;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null && !mTitles.isEmpty()  ? mTitles.get(position) : "";
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}