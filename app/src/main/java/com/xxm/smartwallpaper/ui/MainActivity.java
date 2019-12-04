package com.xxm.smartwallpaper.ui;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.xxm.smartwallpaper.BuildConfig;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.adapter.MainPageAdapter;
import com.xxm.smartwallpaper.fragment.BaseFragment;
import com.xxm.smartwallpaper.fragment.WallpaperPicFragment;
import com.xxm.smartwallpaper.fragment.WallpaperTextFragment;
import com.xxm.smartwallpaper.fragment.WallpaperVideoFragment;
import com.xxm.smartwallpaper.inteface.OnViewClickListener;
import com.xxm.smartwallpaper.util.XPermissionUtils;

import java.util.ArrayList;
import java.util.List;

import xcv.sde.dfa.AdManager;
import xcv.sde.dfa.listener.Interface_ActivityListener;
import xcv.sde.dfa.os.EarnPointsOrderList;
import xcv.sde.dfa.os.OffersManager;
import xcv.sde.dfa.os.PointsChangeNotify;
import xcv.sde.dfa.os.PointsEarnNotify;
import xcv.sde.dfa.os.PointsManager;

public class MainActivity extends BaseAcitivity implements View.OnClickListener,OnViewClickListener, PointsChangeNotify, PointsEarnNotify {

    public static int TYPE_PIC = 1;
    public static int TYPE_TEXT = 2;
    public static int TYPE_VIDEO = 3;
    public static int ACTIVITY_RESULT_COLOR = 3000;
    TabLayout tabLayout;
    ViewPager viewPager;
    WallpaperPicFragment wallpaperPicFragment;
    WallpaperTextFragment wallpaperTextFragment;
    WallpaperVideoFragment wallpaperVideoFragment;
    private List<BaseFragment> mFragments = new ArrayList<>();
    private TextView offersCurrent;
    private TextView offersGetMore;
    private TextView phoneAdapterTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tab_home);
        viewPager = (ViewPager) findViewById(R.id.viewpaper_home);
        phoneAdapterTv = (TextView) findViewById(R.id.activity_phone_adapter);
        offersCurrent = (TextView) findViewById(R.id.activity_offers_current);
        offersGetMore = (TextView) findViewById(R.id.activity_offers_getmore);
        offersGetMore.setOnClickListener(this);
        String currentOffers = getString(R.string.activity_offers_now,0+"");
        offersCurrent.setText(currentOffers);
        instance = this;
        mFragments.clear();
        wallpaperPicFragment = new WallpaperPicFragment();
        wallpaperPicFragment.setTitle(getString(R.string.fragment_static_wallpaper));
        wallpaperVideoFragment = new WallpaperVideoFragment();
        wallpaperVideoFragment.setTitle(getString(R.string.fragment_dynamatic_wallpaper));
        wallpaperTextFragment = new WallpaperTextFragment();
        wallpaperTextFragment.setTitle(getString(R.string.fragment_text_wallpaper));
        mFragments.add(wallpaperVideoFragment);
        mFragments.add(wallpaperPicFragment);
        mFragments.add(wallpaperTextFragment);
        wallpaperPicFragment.setOnViewClicListener(this);
        wallpaperTextFragment.setOnViewClicListener(this);
        wallpaperVideoFragment.setOnViewClicListener(this);
        phoneAdapterTv.setOnClickListener(this);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.removeAllTabs();
        for (int i = 0, len = mFragments.size(); i < len; i++) {
            BaseFragment fragment = mFragments.get(i);
            tabLayout.addTab(tabLayout.newTab().setText(fragment.getTitle()));
        }

        MainPageAdapter mMainPageAdapter = new MainPageAdapter(getSupportFragmentManager(), mFragments);
        viewPager.setAdapter(mMainPageAdapter);
        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
                switch (pos) {
                    case 0://最新
                        //mFragments.get(0).onDispatchEvent(BaseFragment.ACTION_TYPE_DOUBLE_CLICK, null);
                        break;
                    case 1://最热
                        //mFragments.get(1).onDispatchEvent(BaseFragment.ACTION_TYPE_DOUBLE_CLICK, null);
                        break;
                }
            }
        });
        initAd();
    }

    private void initAd() {
        if(BuildConfig.FLAVOR.equals("screenwallpaper")){
            AdManager.getInstance(this).init("f8a96887c7151d9a", "ed24b00d7b5a1b71", false);
        }else {
            AdManager.getInstance(this).init("c16305426a979666", "dbe63a7784ab8625", false);
        }
     //   PointsManager.getInstance(this).awardPoints(30.0f);

        OffersManager.getInstance(this).onAppLaunch();
        PointsManager.getInstance(this).registerNotify(this);
        // (可选)注册积分订单赚取监听（sdk v4.10版本新增功能）
        PointsManager.getInstance(this).registerPointsEarnNotify(this);
        // 从5.3.0版本起，客户端积分托管将由 int 转换为 float
        float pointsBalance = PointsManager.getInstance(this).queryPoints();
        String currentOffers = getString(R.string.activity_offers_now,pointsBalance+"");
        offersCurrent.setText(currentOffers);
    }


    private static MainActivity instance;
    public static synchronized MainActivity getInstance() {
        return instance;
    }

    @Override
    public void onClick(View v) {
        if(v == offersGetMore){
            OffersManager.getInstance(this).showOffersWall(new Interface_ActivityListener() {

                /**
                 * 当积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
                 */
                @Override
                public void onActivityDestroy(Context context) {
                  //  Toast.makeText(context, "全屏积分墙退出了", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(v == phoneAdapterTv){
            startActivity(new Intent(getApplicationContext(),PhoneAdapterActivity.class));
        }
    }

    @Override
    protected void onResume() {
        // 从服务器端获取当前用户的虚拟货币.
        // 返回结果在回调函数getUpdatePoints(...)中处理
        //AppConnect.getInstance(this).getPoints(this);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        // 释放资源，原finalize()方法名修改为close()
        //AppConnect.getInstance(this).close();
        PointsManager.getInstance(this).unRegisterNotify(this);
        PointsManager.getInstance(this).unRegisterPointsEarnNotify(this);
        // 回收积分广告占用的资源
        OffersManager.getInstance(this).onAppExit();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == XPermissionUtils.CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if(wallpaperPicFragment != null){
                    wallpaperPicFragment.onActivityResult(requestCode,resultCode,data);
                }
            }
        }else if(requestCode == MainActivity.ACTIVITY_RESULT_COLOR){
            if(wallpaperTextFragment != null){
                wallpaperTextFragment.onActivityResult(requestCode,resultCode,data);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        XPermissionUtils.onRequestPermissionsResult(MainActivity.this, 1000, XPermissionUtils.permissionArr, grantResults);
    }

    @Override
    public void onWindViewChangeClickListener(int type) {
//        if(MainActivity.TYPE_PIC == type){
//             wallpaperTextFragment.stopTextWallpaper();
//             wallpaperVideoFragment.stopVideoPlay();
//        }else if(MainActivity.TYPE_TEXT == type){
//            wallpaperPicFragment.stopPicWallpaper();
//            wallpaperVideoFragment.stopVideoPlay();
//        }else if(MainActivity.TYPE_VIDEO == type){
//            wallpaperPicFragment.stopPicWallpaper();
//            wallpaperTextFragment.stopTextWallpaper();
//        }
    }

    @Override
    public void onPointBalanceChange(float v) {//积分改变回调
        Log.e("======","======vvvvvvvvv:"+v);
        String currentOffers = getString(R.string.activity_offers_now,v+"");
        offersCurrent.setText(currentOffers);
    }

    @Override
    public void onPointEarn(Context context, EarnPointsOrderList earnPointsOrderList) {//积分赚取回调

    }
}
