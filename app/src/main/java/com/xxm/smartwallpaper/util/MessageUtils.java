package com.xxm.smartwallpaper.util;

import android.content.Context;
import android.widget.Toast;

import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.widget.dialog.HiToast;


/**
 * 消息处理工具类
 */
public class MessageUtils {

    private static HiToast mHiToast;
    private static Context mCtx = Global.getContext();
    private static String mPkg = Global.getPkgName();

    public static void show(final String text) {
        show(text, Toast.LENGTH_SHORT);
    }

    public static void show(final int resId) {
        show(mCtx.getResources().getString(resId), Toast.LENGTH_SHORT);
    }

    public static void showLong(final String text) {
        show(text, Toast.LENGTH_LONG);
    }

    public static void showLong(final int resId) {
        show(mCtx.getResources().getString(resId), Toast.LENGTH_LONG);
    }

    public static void show(CharSequence text, int duration) {
        //非app部分，仍以系统Toast样式弹出
        if (!"com.felink.videopaper".equals(mPkg) && !"com.felink.videopaper.overseas".equals(mPkg)) {
            Framework.show(text, duration);
            return;
        }
        if (mHiToast == null) {
            mHiToast = HiToast.makeText(mCtx, text, duration);
        } else {
            mHiToast.setText(text);
            mHiToast.setDuration(duration);
        }
        mHiToast.show();
    }

    public static void show(int resId, int duration) {
        CharSequence msg = mCtx.getResources().getString(resId);
        show(msg, duration);
    }

    /**
     * 系统原生的Toast
     */
    public static final class Framework {
        private static Toast mToast;

        public static void show(final String text) {
            show(text, Toast.LENGTH_SHORT);
        }

        public static void show(final int resId) {
            show(mCtx.getResources().getString(resId), Toast.LENGTH_SHORT);
        }

        public static void show(CharSequence text, int duration) {
            if (mToast == null) {
                mToast = Toast.makeText(mCtx, text, duration);
            } else {
                mToast.setText(text);
                mToast.setDuration(duration);
            }
            mToast.show();
        }
    }
}
