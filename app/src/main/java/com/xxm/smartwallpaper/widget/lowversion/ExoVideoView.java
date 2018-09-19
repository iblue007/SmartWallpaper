package com.xxm.smartwallpaper.widget.lowversion;

import android.content.Context;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.FrameLayout;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.util.FileUtil;
import com.xxm.smartwallpaper.video.VideoPlayer;

/**
 * Created by xuqunxing on 2018/9/14.
 */
public class ExoVideoView extends FrameLayout implements VideoPlayer.OnMediaStateListener, VideoPlayer.onMediaProgressListener {
    public ExoVideoView(Context context) {
        this(context,null);
    }

    public ExoVideoView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ExoVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    TextureView videoSurface;
    private void initView() {
        View.inflate(getContext(), R.layout.view_exo_view,this);
        videoSurface = (TextureView) findViewById(R.id.iv_videoplayer_video_surface);
      //  videoSurface.setAlpha(0.5f);
       // startPlayVideo();
    }

    private void startPlayVideo(String path) {
        try {
            VideoPlayer.getInstance().setOnMediaStateListener(this);
            VideoPlayer.getInstance().setOnMediaProgressListener(this);
           // String path = BaseConfig.WIFI_DOWNLOAD_PATH + "0001.mp4";
            if (!TextUtils.isEmpty(path) && FileUtil.isFileExits(path)) {
                VideoPlayer.getInstance().play(path,videoSurface, true, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onError(MediaPlayer mp) {

    }

    @Override
    public void onRenderingStart(MediaPlayer mp) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mp) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onRepeat() {

    }

    @Override
    public void onMediaProgress(int currentDegree, int TotalDegree) {
        Log.e("======","======currentDegree:"+currentDegree+"--TotalDegree:"+TotalDegree);
    }

    public void setVideoPath(String videoPath) {
        startPlayVideo(videoPath);
    }
}
