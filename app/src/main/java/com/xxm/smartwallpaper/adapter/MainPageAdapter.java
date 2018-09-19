package com.xxm.smartwallpaper.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xxm.smartwallpaper.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主页page adapter</br>
 * @author: cxy </br>
 * @date: 2017年05月02日 15:29.</br>
 * @update: </br>
 */

public class MainPageAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> mFragmentList = new ArrayList<BaseFragment>();

    public MainPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        mFragmentList.clear();
        mFragmentList.addAll(fragments);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentList.get(position).getTitle();
    }

    public void refresh(List<BaseFragment> fragments) {
        mFragmentList.clear();
        mFragmentList.addAll(fragments);
        notifyDataSetChanged();
    }
}