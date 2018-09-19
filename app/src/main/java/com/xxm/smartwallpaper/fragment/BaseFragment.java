package com.xxm.smartwallpaper.fragment;

import android.support.v4.app.Fragment;

import com.xxm.smartwallpaper.inteface.OnViewClickListener;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月02日 15:31.</br>
 * @update: </br>
 */

public class BaseFragment extends Fragment {

    public final static int ACTION_TYPE_DOUBLE_CLICK = 1;
    public final static int ACTION_TYPE_LOGIN_CHANGED = 2;
    /**
     * 是否隐藏
     */
    public boolean isHidden = false;
    protected CharSequence mTitle;
    protected OnViewClickListener onViewCLickListener;

    public void onDispatchEvent(int actionType, String action) {

    }

    public CharSequence getTitle() {
        return mTitle;
    }

    public BaseFragment setTitle(CharSequence title) {
        this.mTitle = title;
        return this;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;
    }

    public void onEnter() {
    }

    public void onLeave() {
    }

    /**
     * @desc 刷新数据
     * @author linliangbin
     * @time 2018/7/14 16:54
     */
    public void doRefresh() {

    }
}
