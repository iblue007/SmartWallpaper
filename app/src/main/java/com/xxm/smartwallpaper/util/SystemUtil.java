/**
 * Create Date:2011-7-14下午02:03:22
 */
package com.xxm.smartwallpaper.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.R;

import java.util.List;


/**
 * 系统工具类
 */
public class SystemUtil {
    private static final String TAG = "SystemUtil";

    /**
     * 在浏览器中打开指定地址
     *
     * @param ctx
     * @param url 网页url
     */
    public static void openPage(Context ctx, String url) {
        try {
            if (null != url && !(url.startsWith("http://") || url.startsWith("https://"))) {
                url = "http://" + url;
            }
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ctx.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 安全打开一个APP
     *
     * @param ctx
     * @param intent
     */
    public static void startActivitySafely(final Context ctx, Intent intent) {
        if (ctx == null)
            return;
        if (intent == null) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    makeShortToast(ctx, R.string.dockbar_null_intent);
                }
            });
            return;
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    makeShortToast(ctx, R.string.activity_not_found);
                }
            });
            Log.e(TAG, e.getMessage());
        }
    }

    /**
     * 打开一个APP
     *
     * @param ctx
     * @param intent
     */
    public static void startActivity(final Context ctx, Intent intent) {
        if (intent == null) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    makeShortToast(ctx, R.string.dockbar_null_intent);
                }
            });
            return;
        }

        try {
            ctx.startActivity(intent);
        } catch (Exception e) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    makeShortToast(ctx, R.string.activity_not_found);
                }
            });
            e.printStackTrace();
        }
    }

    /**
     * 接收Activity返回结果
     *
     * @param ctx
     * @param intent
     * @param requestCode
     */
    public static void startActivityForResultSafely(final Activity ctx, Intent intent, int requestCode) {
        try {
            ctx.startActivityForResult(intent, requestCode);
        } catch (Exception e) {
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(ctx, R.string.activity_not_found, Toast.LENGTH_SHORT).show();
                }
            });
            e.printStackTrace();
        }
    }

    /**
     * 显示软键盘
     *
     * @param view
     */
    public static void showKeyboard(View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(view, 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param view
     */
    public static void hideKeyboard(View view) {
        if (null == view)
            return;
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    /**
     * 隐藏软键盘
     *
     * @param ctx
     */
    public static void createHideInputMethod(Activity ctx) {
        final InputMethodManager manager = (InputMethodManager) ctx.getSystemService(Activity.INPUT_METHOD_SERVICE);
        ctx.getWindow().getDecorView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (manager.isActive()) {
                    manager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
                return true;
            }
        });
    }

    // /**
    // * 更新应用程序使用次数 <br>
    // * Author:ryan <br>
    // * Date:2012-6-30下午08:08:38
    // */
    // private static void maybeUpdateUsedTime(Context ctx, ComponentName
    // component) {
    // if (component == null)
    // return;
    // if (ctx.getPackageName().equals(component.getPackageName()))
    // return;
    //
    // BaseAppDataFactory.updateUsedTime(ctx, component);
    // }

    /**
     * 创建toast
     *
     * @param ctx
     * @param resId
     */
    public static void makeShortToast(Context ctx, int resId) {
        if (resId == 0)
            return;
        Toast.makeText(ctx, ctx.getText(resId), Toast.LENGTH_SHORT).show();
    }

    /**
     * 根据名称得到Drawable
     *
     * @param ctx
     * @param resName
     * @return Drawable
     */
    public static Drawable getDrawableByResourceName(Context ctx, String resName) {
        if (StringUtil.isEmpty(resName))
            return null;

        Resources res = ctx.getResources();
        int resId = res.getIdentifier(resName, "drawable", ctx.getPackageName());
        if (resId == 0)
            return null;

        return res.getDrawable(resId);
    }

    /**
     * 根据名称得到Bitmap
     *
     * @param ctx
     * @param resName
     * @return Bitmap
     */
    public static Bitmap getBitmapByResourceName(Context ctx, String resName) {
        if (StringUtil.isEmpty(resName))
            return null;

        Resources res = ctx.getResources();
        int resId = res.getIdentifier(resName, "drawable", ctx.getPackageName());
        if (resId == 0)
            return null;

        return ((BitmapDrawable) res.getDrawable(resId)).getBitmap();
    }

    /**
     * <br>Description:获取资源ID
     * <br>Author:caizp
     * <br>Date:2014-3-31上午11:43:10
     *
     * @param ctx
     * @param key
     * @param type
     * @return
     */
    public static int getResourceId(Context ctx, String key, String type) {
        return ctx.getResources().getIdentifier(key, type, ctx.getPackageName());
    }

    /**
     * 是不是系统应用
     *
     * @param appInfo
     * @return boolean
     */
    public static boolean isSystemApplication(ApplicationInfo appInfo) {
        if (appInfo == null)
            return false;

        return isSystemApplication(appInfo.flags);
    }

    public static boolean isSystemApplication(int flags) {
        if ((flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0)
            return true;
        else if ((flags & ApplicationInfo.FLAG_SYSTEM) != 0)
            return true;

        return false;
    }


    /**
     * 判断机型(或固件版本)是否支持google语音识别功能
     *
     * @return 支持返回true, 否则返回false
     */
    public static boolean isVoiceRecognitionEnable(Context context) {
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
        if (activities.size() != 0)
            return true;
        else
            return false;
    }

    /**
     * 获取当前进程名
     *
     * @param context
     * @return String
     */
    public static String getCurProcessName(Context context) {
        try {
            int pid = android.os.Process.myPid();
            ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
                if (appProcess.pid == pid) {
                    return appProcess.processName;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static ProgressDialog createProgressDialog(Context context) {
        ProgressDialog progressDialog = new ProgressDialog(context);
        return progressDialog;
    }


    /**
     * Get activity from context object
     *
     * @param context something
     * @return object of Activity or null if it is not Activity
     */
    public static Activity scanForActivity(Context context) {
        if (context == null) return null;

        if (context instanceof Activity) {
            return (Activity) context;
        } else if (context instanceof ContextWrapper) {
            return scanForActivity(((ContextWrapper) context).getBaseContext());
        }

        return null;
    }


    /**全名屏顶部适配*/
    public static void setAllScreenStrategyTop(Context context, RelativeLayout parentView){
        try {
            String machineName = android.os.Build.MANUFACTURER.toLowerCase();
            if(machineName.contains("oppo")){
                boolean hasSystemFeature = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
                if(hasSystemFeature){
                    RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) parentView.getLayoutParams();
                    layoutParams.topMargin = 85;
                    parentView.setLayoutParams(layoutParams);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**是否是全名屏*/
    public static boolean isAllScreenStrategy(Context context){
        try {
            boolean hasSystemFeature = false;
            String machineName = android.os.Build.MANUFACTURER.toLowerCase();
            if(machineName.contains("oppo")){
                hasSystemFeature = context.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
            }
            return hasSystemFeature;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
