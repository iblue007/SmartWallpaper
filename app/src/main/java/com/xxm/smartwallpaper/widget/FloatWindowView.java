package com.xxm.smartwallpaper.widget;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.LinearLayout;

import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.util.MediaHelper;
import com.xxm.smartwallpaper.util.MessageUtils;

import java.io.File;

/**
 * Created by xuqunxing on 2018/8/17.
 */
public class FloatWindowView extends LinearLayout {

    /**
     * 记录悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录悬浮窗的高度
     */
    public static int viewHeight;
    private Activity mContext;
    private TextureView videoView;
    public MediaPlayer mPlayer;
    private Surface mSurface;
    private String mVideoPath;
    public FloatWindowView(final Activity context, final String path) {
        super(context);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.float_window, this);
        View view = findViewById(R.id.big_window_layout);
        mVideoPath = path;
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        videoView = (TextureView) findViewById(R.id.video_view);
        File file = new File(path);
        if (!file.exists()) {
            Log.e("TAG", "播放路径不存在！");
            //可以加载项目中资源文件里面的默认视频
            return;
        }
        //进行TextureView控件创建的监听
        videoView.setAlpha(0.5f);
        videoView.setSurfaceTextureListener(surfaceTextureListener);
    }


    private TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {

        //创建完成  TextureView才可以进行视频画面的显示
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            // Log.i(TAG,"onSurfaceTextureAvailable");
            mSurface = new Surface(surface);//连接对象（MediaPlayer和TextureView）
            play(mVideoPath);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            // Log.i(TAG,"onSurfaceTextureSizeChanged");
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            // Log.i(TAG,"onSurfaceTextureDestroyed");
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {
            // Log.i(TAG,"onSurfaceTextureUpdated");
        }
    };

    private void play(String url){
        try {
            mPlayer = MediaHelper.getInstance();
            mPlayer.reset();
            mPlayer.setDataSource(url);
            //让MediaPlayer和TextureView进行视频画面的结合
            mPlayer.setSurface(mSurface);
            //设置监听
            mPlayer.setOnBufferingUpdateListener(onBufferingUpdateListener);
            mPlayer.setOnCompletionListener(onCompletionListener);
            mPlayer.setOnErrorListener(onErrorListener);
            mPlayer.setOnPreparedListener(onPreparedListener);
            mPlayer.setScreenOnWhilePlaying(true);//在视频播放的时候保持屏幕的高亮
            //异步准备
            mPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //准备完成监听
    private MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            MediaHelper.play();
            //   hasPlay = true;
        }

    };

    //错误监听
    private MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            return true;
        }
    };

    //完成监听
    private MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            //视频播放完成
            // mediaController.showPlayFinishView();
            Log.e("======","======onCompletionListener");
            play(mVideoPath);
        }
    };

    //缓冲的监听
    private MediaPlayer.OnBufferingUpdateListener onBufferingUpdateListener = new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            Log.i(TAG,"percent:"+percent);
            //mediaController.updateSeekBarSecondProgress(percent);
        }
    };

    public void setVideoPath(String path) {
        if(!TextUtils.isEmpty(path)){
            play(path);
        }else {
            MessageUtils.show(R.string.view_video_path_error);
        }
    }

    public void setVideoAlpha(float alpha) {
        if(videoView != null){
            Log.e("======","======progress:"+alpha);
            videoView.setAlpha(alpha);
        }
    }

    public void stopVideoPlay() {
        if(mPlayer != null){
            mPlayer.stop();
        }
    }
}