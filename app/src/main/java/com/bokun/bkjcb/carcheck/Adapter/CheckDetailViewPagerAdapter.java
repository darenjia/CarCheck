package com.bokun.bkjcb.carcheck.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.bokun.bkjcb.carcheck.Fragment.CheckItemFragment;

import java.util.List;

/**
 * Created by DengShuai on 2018/11/7.
 * Description :
 */
public class CheckDetailViewPagerAdapter extends FragmentPagerAdapter {
    private List<CheckItemFragment> fragments;

    public CheckDetailViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public CheckDetailViewPagerAdapter(FragmentManager fm, List<CheckItemFragment> fragments) {
        this(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int i) {
        return fragments.get(i);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
