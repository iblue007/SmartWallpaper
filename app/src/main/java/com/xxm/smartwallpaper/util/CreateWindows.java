package com.xxm.smartwallpaper.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.xxm.smartwallpaper.BuildConfig;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.PicBean;
import com.xxm.smartwallpaper.bean.TextBean;
import com.xxm.smartwallpaper.bean.VideoBean;
import com.xxm.smartwallpaper.video.VideoPlayer;
import com.xxm.smartwallpaper.widget.lowversion.ExoVideoView;

public class CreateWindows {
    private int flag = 0;
    private ImageView imageView = null;
    private Context mContext;
    private ExoVideoView videoView = null;
    private TextView textView = null;
    private LayoutParams viewLayout = null;
    private WindowManager windows;

    public CreateWindows(Context context, WindowManager windows) {
        this.mContext = context;
        this.windows = windows;
        this.viewLayout = new LayoutParams();
        this.viewLayout.format = PixelFormat.RGBA_8888;
        if (Build.VERSION.SDK_INT >= 26) {
            this.viewLayout.type = LayoutParams.TYPE_APPLICATION_OVERLAY;//WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;;//
        } else {
            this.viewLayout.type = LayoutParams.TYPE_PHONE;
        }
        this.viewLayout.flags = 792;//WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE| LayoutParams.FLAG_NOT_FOCUSABLE;
        this.viewLayout.gravity = 51;
    }

    public void stopPicWallpaper() {
        if (imageView != null) {
            this.windows.removeView(imageView);
            imageView = null;
        }
    }

    public void stopTextWallpaper() {
        if (textView != null) {
            this.windows.removeView(textView);
            textView = null;
        }
    }

    public void stopVideoWallpaper() {
        if (videoView != null) {
            VideoPlayer.getInstance().setVolumnOpen(false);
            this.windows.removeView(videoView);
            videoView = null;
        }
    }

    public void updatePicView(PicBean picBean) {
        try {
            stopTextWallpaper();
            stopVideoWallpaper();
            if (this.imageView == null) {
                this.imageView = new ImageView(this.mContext);
                this.imageView.setScaleType(ScaleType.FIT_XY);
                this.windows.addView(this.imageView, this.viewLayout);
            }
            this.viewLayout.x = picBean.getLeft();
            this.viewLayout.y = picBean.getTop();
            this.viewLayout.width = picBean.getWidth() == 0 ? ScreenUtil.getCurrentScreenWidth(mContext) : picBean.getWidth();
            this.viewLayout.height = picBean.getHeight() == 0 ? ScreenUtil.getCurrentScreenHeight(mContext) : picBean.getWidth();
            this.viewLayout.alpha = ((float) picBean.getAlpha()) / 100.0f;
            if (picBean.getFile().equals(BuildConfig.VERSION_NAME)) {
                this.imageView.setImageResource(R.mipmap.logosmart);
            } else {
                Bitmap bitmap = BitmapFactory.decodeFile(picBean.getFile());
                if (bitmap != null) {
                    // TODO:xuqunxing 图片太大的情况 要压缩下  Log.e("======","======bitmapSIze:"+bitmap.getByteCount());
                    this.imageView.setImageBitmap(bitmap);
                } else {
                    Toast.makeText(this.mContext, "\u6587\u4ef6\u6709\u53ef\u80fd\u4e0d\u5b58\u5728\uff0c\u8bf7\u91cd\u8bd5", 0).show();
                }
            }
            this.windows.updateViewLayout(this.imageView, this.viewLayout);
            this.flag = 1;
        } catch (Exception e) {
            Log.e("======", "======error:" + e.getMessage());
            e.printStackTrace();
        }
    }

    public void updateTextView(TextBean textBean) {
       try{
           stopPicWallpaper();
           stopVideoWallpaper();
           if (this.textView == null) {
               this.textView = new TextView(this.mContext);
               this.textView.setTypeface(null, 1);
               this.windows.addView(this.textView, this.viewLayout);
           }
           this.viewLayout.alpha = ((float) textBean.getAlpha()) / 100.0f;
           this.viewLayout.x = textBean.getLeft();
           this.viewLayout.y = textBean.getTop();
           this.textView.setTextColor(textBean.getColor());
           this.textView.setText(textBean.getText());
           this.textView.setTextSize((float) textBean.getSize());
           this.windows.updateViewLayout(this.textView, this.viewLayout);
           Log.e("======", "======left：" + viewLayout.x + "--top:" + viewLayout.y);
           this.flag = 2;
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    public void updateVideoView(VideoBean videoBean) {
        try{
            stopPicWallpaper();
            stopTextWallpaper();
            String videoPath = videoBean.getVideoPath();
            this.viewLayout.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
                    | WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED| LayoutParams.FLAG_NOT_TOUCHABLE;
            if (this.videoView == null ) {
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                videoView = new ExoVideoView(mContext);
                videoView.setLayoutParams(params);
                videoView.setBackgroundColor(Color.BLACK);
                videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
                windows.addView(videoView, this.viewLayout);
            }
            if(!videoBean.isNoUpdateUri()){
                VideoPlayer.getInstance().release();
                videoView.setVideoPath(videoPath);
            }
            videoView.setAlpha(videoBean.getAlpha());
            //设置TYPE_PHONE，需要申请android.permission.SYSTEM_ALERT_WINDOW权限
            //TYPE_TOAST同样可以实现悬浮窗效果，不需要申请其他权限
//        this.viewLayout.type = WindowManager.LayoutParams.TYPE_TOAST;
//        this.viewLayout.format = PixelFormat.RGBA_8888;
//        this.viewLayout.gravity = Gravity.LEFT | Gravity.TOP;
            this.viewLayout.x = 0;
            this.viewLayout.y = 0;
            // this.viewLayout.alpha = 0.5f;// 1.0f;
            this.viewLayout.token = videoView.getApplicationWindowToken();
            this.windows.updateViewLayout(this.videoView, this.viewLayout);
            this.flag = 3;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public View getView() {
        if (this.flag == 2) {
            return this.textView;
        }else if(this.flag == 3){
            return this.videoView;
        }
        return this.imageView;
    }

}
