package com.xxm.smartwallpaper.ui;

import android.app.Application;
import android.os.Handler;
import com.lling.photopicker.utils.Global;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.xxm.smartwallpaper.util.imageLoader.DisplayOptionUtil;


/**
 * Created by xuqunxing on 2018/9/3.
 */

public class SmartApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        initConfig();
        initAd();
        initImageLoader();
       // initUM();
    }

    private void initUM() {
//        UMConfigure.init(this,"5b8e684a8f4a9d191a000334", "UMENG", UMConfigure.DEVICE_TYPE_PHONE,"f389e7f0372f9dc892ac2d64397f39f7");
//        PushAgent mPushAgent = PushAgent.getInstance(this);
//        //注册推送服务，每次调用register方法都会回调该接口
//        mPushAgent.register(new IUmengRegisterCallback() {
//
//            @Override
//            public void onSuccess(String deviceToken) {
//                //注册成功会返回device token
//                Log.e("======","======deviceToken:"+deviceToken);
//            }
//
//            @Override
//            public void onFailure(String s, String s1) {
//
//            }
//        });
    }

    private void initAd() {
    }

    private void initConfig() {
        try {
            Global.setContext(getApplicationContext());
            Global.setHandler(new Handler());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initImageLoader() {
        try {
            ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                    .defaultDisplayImageOptions(DisplayOptionUtil.DEFAULT_OPTIONS)
                    .build();
            ImageLoader.getInstance().init(configuration);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
