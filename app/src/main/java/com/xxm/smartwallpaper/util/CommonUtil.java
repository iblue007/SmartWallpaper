package com.xxm.smartwallpaper.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.View;

import com.xxm.smartwallpaper.ui.MainActivity;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.manager.BaseConfig;
import com.xxm.smartwallpaper.manager.MyWindowManager;
import com.xxm.smartwallpaper.widget.dialog.CommonTipDialog;

/**
 * Created by xuqunxing on 2018/8/24.
 */

public class CommonUtil {

    public static boolean joinQQGroup(Context context) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + "R9zgtSSucSXaHfefWrGKOhFYJPfOI4lY"));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            context.startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            e.printStackTrace();
            return false;
        }
    }

    public static void startVideoPlay(final Context context, int pos){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(!Settings.canDrawOverlays(context)){
                CommonTipDialog commonTipDialog = new CommonTipDialog(context, context.getString(R.string.dialog_require_power),
                        context.getString(R.string.dialog_require_power_tip),context.getString(R.string.goto_set));
                commonTipDialog.show();
                commonTipDialog.setOnGnClickListener(new CommonTipDialog.OnGnClickListener() {
                    @Override
                    public void onSubmitClick(View v) {
                        //没有悬浮窗权限,跳转申请
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                        context.startActivity(intent);
                    }

                    @Override
                    public void onCancleClick(View v) {

                    }
                });
            }else {
                String path = BaseConfig.WIFI_DOWNLOAD_PATH +"000"+pos+".mp4";
                MyWindowManager.createWindow(MainActivity.getInstance(),path);
            }
        }else {
            String path = BaseConfig.WIFI_DOWNLOAD_PATH +"000"+pos+".mp4";
            MyWindowManager.createWindow(MainActivity.getInstance(),path);
        }
    }

    public static void stopVideoPlay(Context context) {
        MyWindowManager.removeWindow(context);
    }
}
