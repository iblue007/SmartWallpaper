package com.xxm.smartwallpaper.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog.Builder;
import android.widget.Toast;

public class CheckPermission {
    private static final String TAG = "CheckPermission";
    private static final int version = VERSION.SDK_INT;
    private Context context;

    class C03202 implements OnClickListener {
        C03202() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
            Toast.makeText(CheckPermission.this.context, "\u5f00\u542f\u60ac\u6d6e\u7a97\u5931\u8d25\uff0c\u8bf7\u624b\u52a8\u5f00\u542f(\u53ef\u767e\u5ea6)", 0).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public CheckPermission(Context context) {
        this.context = context;
        if (version >= 23) {
            if(version >= Build.VERSION_CODES.O){
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    dialog(2);
                }
            }else {
                if (!commonROMPermissionCheck(context)) {
                    dialog(2);
                }
            }
        } else if (version >= 19 && !checkOp(context, 24)) {
            dialog(3);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private boolean checkPermissionAbove26(){
        AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        if (appOpsMgr == null)
            return false;
        int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
                .getPackageName());
        return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
    }

    @TargetApi(19)
    private static boolean checkOp(Context context, int op) {
        AppOpsManager manager = (AppOpsManager) context.getSystemService("appops");
        try {
            if (((Integer) AppOpsManager.class.getDeclaredMethod("checkOp", new Class[]{Integer.TYPE, Integer.TYPE, String.class}).invoke(manager, new Object[]{Integer.valueOf(op), Integer.valueOf(Binder.getCallingUid()), context.getPackageName()})).intValue() == 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    private boolean commonROMPermissionCheck(Context context) {
        Boolean result = Boolean.valueOf(true);
        try {
            result = (Boolean) Settings.class.getDeclaredMethod("canDrawOverlays", new Class[]{Context.class}).invoke(null, new Object[]{context});
        } catch (Exception e) {
        }
        return result.booleanValue();
    }

    private void requestAlertWindowPermission(Context context) {
        Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
        intent.setData(Uri.parse("package:" + context.getPackageName()));
        ((Activity) context).startActivityForResult(intent, 1);
    }

    public void dialog(final int type) {
        Builder builder = new Builder(this.context);
        builder.setMessage((CharSequence) "\u5c0f\u7c73\uff0c\u9b45\u65cf\uff0c\u534e\u4e3a\u7b49\u90e8\u5206\u624b\u673a\u65e0\u6cd5\u663e\u793a\u60ac\u6d6e\u7a97\uff0c\u8bf7\u81ea\u5df1\u53bb\u8bbe\u7f6e\uff1a\n\u624b\u673a\u8bbe\u7f6e->\u5e94\u7528\u7a0b\u5e8f\u7ba1\u7406->\u5168\u5c40\u900f\u660e\u58c1\u7eb8->\u6743\u9650\u7ba1\u7406->\u6253\u5f00\u60ac\u6d6e\u7a97\u5373\u53ef");
        builder.setPositiveButton((CharSequence) "去设置", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                switch (type) {
                    case 2 /*2*/:
                        CheckPermission.this.requestAlertWindowPermission(CheckPermission.this.context);
                        return;
                    case 3 /*3*/:
                        ((Activity) CheckPermission.this.context).startActivity(new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + CheckPermission.this.context.getPackageName())));
                        return;
                    default:
                        return;
                }
            }
        });
        builder.setNegativeButton((CharSequence) "取消", new C03202());
        builder.setCancelable(false);
        builder.create().show();
    }
}
