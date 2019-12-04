package com.xxm.smartwallpaper.service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.PicBean;
import com.xxm.smartwallpaper.bean.TextBean;
import com.xxm.smartwallpaper.bean.VideoBean;
import com.xxm.smartwallpaper.ui.MainActivity;
import com.xxm.smartwallpaper.util.CreateWindows;
import com.xxm.smartwallpaper.video.VideoPlayer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by xuqunxing on 2018/9/7.
 */

@SuppressLint({"NewApi"})
@TargetApi(11)
public class WindowService extends Service {
    private static final int NOTIFICATION_ID = 1;
    private static final Class<?>[] mSetForegroundSignature;
    private static final Class<?>[] mStartForegroundSignature = new Class[]{Integer.TYPE, Notification.class};
    private static final Class<?>[] mStopForegroundSignature;
    CreateWindows createWindows = null;
    // private DataStore dataStore;
    private NotificationManager mNM;
    private boolean mReflectFlg = false;
    private Method mSetForeground;
    private Object[] mSetForegroundArgs = new Object[NOTIFICATION_ID];
    private Method mStartForeground;
    private Object[] mStartForegroundArgs = new Object[2];
    private Method mStopForeground;
    private Object[] mStopForegroundArgs = new Object[NOTIFICATION_ID];
    WindowManager windows;
    public static int TYPE_PIC = 1;
    public static int TYPE_TEXT = 2;
    public static int TYPE_VIDEO = 3;
    String CHANNEL_ONE_ID = "CHANNEL_ONE_ID";
    String CHANNEL_ONE_NAME = "CHANNEL_ONE_ID";

    static {
        Class[] clsArr = new Class[NOTIFICATION_ID];
        clsArr[0] = Boolean.TYPE;
        mSetForegroundSignature = clsArr;
        clsArr = new Class[NOTIFICATION_ID];
        clsArr[0] = Boolean.TYPE;
        mStopForegroundSignature = clsArr;
    }

    public void onCreate() {
        this.mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        try {
            this.mStartForeground = WindowService.class.getMethod("startForeground", mStartForegroundSignature);
            this.mStopForeground = WindowService.class.getMethod("stopForeground", mStopForegroundSignature);
        } catch (NoSuchMethodException e) {
            this.mStopForeground = null;
            this.mStartForeground = null;
        }
        try {
            //进行8.0的判断
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ONE_ID,
                        CHANNEL_ONE_NAME, NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.enableLights(true);
                notificationChannel.setLightColor(Color.RED);
                notificationChannel.setShowBadge(true);
                notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.createNotificationChannel(notificationChannel);
            }

            this.mSetForeground = getClass().getMethod("setForeground", mSetForegroundSignature);
            Notification.Builder builder = new Notification.Builder(this).setChannelId(CHANNEL_ONE_ID);
            builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
            builder.setSmallIcon(R.mipmap.logoscreen);
            builder.setTicker("\u5168\u5c40\u900f\u660e\u58c1\u7eb8\u670d\u52a1\u5f00\u542f");
            builder.setContentTitle("\u5168\u5c40\u900f\u660e\u58c1\u7eb8");
            builder.setContentText("\u4fdd\u8bc1\u670d\u52a1\u5728\u901a\u77e5\u680f\u9632\u6b62\u670d\u52a1\u88ab\u6740");
            startForegroundCompat(NOTIFICATION_ID, builder.build());
            this.windows = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            this.createWindows = new CreateWindows(this, this.windows);
            //this.dataStore = new DataStore(this);

            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
            intentFilter.addAction(Intent.ACTION_SCREEN_ON);
            intentFilter.addAction(Intent.ACTION_USER_PRESENT);
            registerReceiver(screenBroadcast, intentFilter);

        } catch (NoSuchMethodException e2) {
            throw new IllegalStateException("OS doesn't have Service.startForeground OR Service.setForeground!");
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        switch (intent.getIntExtra("type", 0)) {
            case 0 /*0*/:
                Toast.makeText(getApplicationContext(), "\u6587\u4ef6\u6709\u53ef\u80fd\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u8bd5", 0).show();
                break;
            case NOTIFICATION_ID /*1*/:
                this.createWindows.updatePicView((PicBean) intent.getSerializableExtra("bean"));
                break;
            case 2 /*2*/:
                this.createWindows.updateTextView((TextBean) intent.getSerializableExtra("bean"));
                break;
//            case 3 /*3*/:
//                this.createWindows.updateEyeView((EyeBean) intent.getSerializableExtra("bean"));
            case 3 /*3*/:
                this.createWindows.updateVideoView((VideoBean) intent.getSerializableExtra("bean"));
                break;
        }
        return 3;
    }

    public void onDestroy() {
        super.onDestroy();
        this.windows.removeView(this.createWindows.getView());
        stopForegroundCompat(NOTIFICATION_ID);
        unregisterReceiver(screenBroadcast);
    }

    void invokeMethod(Method method, Object[] args) {
        try {
            method.invoke(this, args);
        } catch (InvocationTargetException e) {
            Log.w("ApiDemos", "Unable to invoke method", e);
        } catch (IllegalAccessException e2) {
            Log.w("ApiDemos", "Unable to invoke method", e2);
        }
    }

    void startForegroundCompat(int id, Notification notification) {
        if (this.mReflectFlg) {
            if (this.mStartForeground != null) {
                this.mStartForegroundArgs[0] = Integer.valueOf(id);
                this.mStartForegroundArgs[NOTIFICATION_ID] = notification;
                invokeMethod(this.mStartForeground, this.mStartForegroundArgs);
                return;
            }
            this.mSetForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
            this.mNM.notify(id, notification);
        } else if (Build.VERSION.SDK_INT >= 5) {
            startForeground(id, notification);
        } else {
            this.mSetForegroundArgs[0] = Boolean.TRUE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
            this.mNM.notify(id, notification);
        }
    }

    void stopForegroundCompat(int id) {
        if (this.mReflectFlg) {
            if (this.mStopForeground != null) {
                this.mStopForegroundArgs[0] = Boolean.TRUE;
                invokeMethod(this.mStopForeground, this.mStopForegroundArgs);
                return;
            }
            this.mNM.cancel(id);
            this.mSetForegroundArgs[0] = Boolean.FALSE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
        } else if (Build.VERSION.SDK_INT >= 5) {
            stopForeground(true);
        } else {
            this.mNM.cancel(id);
            this.mSetForegroundArgs[0] = Boolean.FALSE;
            invokeMethod(this.mSetForeground, this.mSetForegroundArgs);
        }
    }

    public IBinder onBind(Intent arg0) {
        return null;
    }

    BroadcastReceiver screenBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                VideoPlayer.getInstance().pause();
                Log.e("======", "======screenoff");
            } else {
                Log.e("======", "======screenOn");
                VideoPlayer.getInstance().restart();
            }


        }
    };

}