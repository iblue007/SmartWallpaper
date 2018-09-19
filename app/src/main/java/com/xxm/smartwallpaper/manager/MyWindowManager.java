package com.xxm.smartwallpaper.manager;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.os.IBinder;
import android.view.WindowManager;

import com.xxm.smartwallpaper.util.ScreenUtil;
import com.xxm.smartwallpaper.widget.FloatWindowView;


/**
 * Created by xuqunxing on 2018/8/17.
 */
public class MyWindowManager {

    /**
     * 悬浮窗View的实例
     */
    private static FloatWindowView mWindow;


    /**
     * 悬浮窗View的参数
     */
    private static WindowManager.LayoutParams mWindowParams;

    /**
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createWindow(Activity context, String path) {

        WindowManager windowManager = (WindowManager)
                context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if (mWindow == null) {
            mWindow = new FloatWindowView(context,path);
            if (mWindowParams == null) {
                mWindowParams = new WindowManager.LayoutParams();
               // mWindowParams.alpha = 0.5f;
                mWindowParams.x = 0;
                mWindowParams.y = 0;
                if (Build.VERSION.SDK_INT >= 26) {
                    mWindowParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
                }else {
                    mWindowParams.type = WindowManager.LayoutParams.TYPE_PHONE;
                }
                mWindowParams.flags = 792;//WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |  WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
                mWindowParams.format = PixelFormat.TRANSLUCENT;
                mWindowParams.gravity = 51;//Gravity.LEFT | Gravity.TOP;

//                mWindowParams = new WindowManager.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                        ViewGroup.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY,
//                        792,
//                        PixelFormat.RGBA_8888);
//                mWindowParams.gravity = 51;//Gravity.TOP | Gravity.LEFT;
                try {
                    IBinder token = context.getWindow().getDecorView().getWindowToken();
                    mWindowParams.token = token;
                } catch (Exception e) {
                }
                windowManager.addView(mWindow, mWindowParams);
            }
//            mWindow.setFitsSystemWindows(true);
//            mWindow.getWindowToken();
            mWindowParams.x = 0;
            mWindowParams.y = 0;
            mWindowParams.width = ScreenUtil.getCurrentScreenWidth(context);
            mWindowParams.height = ScreenUtil.getCurrentScreenHeight(context);
          //  mWindowParams.alpha = 0.5f;
            windowManager.updateViewLayout(mWindow,mWindowParams);
        }else {
            mWindow.setVideoPath(path);
        }

    }


    public static void setAlpha(float alpha){
        if(mWindow != null){
            mWindow.setVideoAlpha(alpha);
        }
    }
    public static void removeWindow(Context context) {
        WindowManager windowManager = (WindowManager)
                context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        if(mWindow != null && windowManager != null){
            mWindow.stopVideoPlay();
            windowManager.removeView(mWindow);
            mWindow = null;
            mWindowParams = null;
        }

    }
}