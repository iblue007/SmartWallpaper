package com.xxm.smartwallpaper.video;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;

import com.lling.photopicker.utils.Global;

/**
 * @Description: 简易视频播放工具类</br>
 * @author: cxy </br>
 * @date: 2017年05月09日 18:22.</br>
 * @update: </br>
 */

public class VideoPlayer implements TextureView.SurfaceTextureListener {
    private static final String TAG = "VideoPlayer";
    private static VideoPlayer instance;
    boolean isPause = false;
    protected VideoPlayerInterface mMediaPlayer;
    protected TextureView mTextureView;
    protected String mDataSource;
    private boolean isLooping = false;
    protected Context context;
    private onMediaProgressListener onMediaProgressListener;

    private VideoPlayer() {
        try {
            context = Global.getContext();
            mMediaPlayer = new CustomExoPlayer(context.getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    protected VideoPlayer(boolean isOri) {
//        try {
//            context = Global.getContext();
//            mMediaPlayer = new CustomMediaPlayer(context.getApplicationContext());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public synchronized static VideoPlayer getInstance() {
        if (instance == null) {
            instance = new VideoPlayer();
        }
        return instance;
    }

    public synchronized void release() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        context = null;
        mTextureView = null;
        mDataSource = null;
        instance = null;

      //  VideoProxyLoader.getInstance().postPause();
    }

    public synchronized void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    public VideoPlayerInterface getPlayer() {
        return mMediaPlayer;
    }

    public void restart() {
        if (mMediaPlayer != null) {
            mMediaPlayer.resumePlaying();
            setVolumnOpen(true);
        }
        isPause = false;
    }

    public void pause() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pausePlaying();
            setVolumnOpen(false);
            isPause = true;
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public boolean isPause() {
        return isPause;
    }


    public void play(String dataSource, TextureView textureView) {
        play(dataSource, textureView, this.isLooping, false, AudioManager.STREAM_MUSIC);
    }

    public void play(String dataSource, TextureView textureView, boolean isLooping, boolean volumnOpen) {
        play(dataSource, textureView, isLooping, volumnOpen, AudioManager.STREAM_MUSIC);
    }

    /**
     * @desc 当前播放资源是否为本地资源
     * @author linliangbin
     * @time 2018/6/4 18:19
     */
    private boolean isLocalVideoResouce() {
        if (TextUtils.isEmpty(mDataSource)) {
            return false;
        }
        if (mDataSource.startsWith("http") || mDataSource.startsWith("https")) {
            return false;
        }
        return true;
    }

    private void gotoPlay() {
//        if (mMediaPlayer == null && context != null) {
//            mMediaPlayer = new CustomMediaPlayer(context.getApplicationContext());
//        }
        try {
            if (isLocalVideoResouce()) {
                mMediaPlayer.playLocalFile(mTextureView, mDataSource, isLooping);
            } else {
                mMediaPlayer.playServerUrl(mTextureView, mDataSource, isLooping);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setVolumnOpen(boolean open) {
        if (mMediaPlayer != null) {
            float volumn = open ? 1.0f : 0.0f;
            mMediaPlayer.setVolume(volumn);
        }
    }

    public void play(String dataSource, TextureView textureView, boolean isLooping, boolean volumnOpen, int streamType) {
        Log.e(TAG, "开始新的播放");
        isPause = false;
        try {
            if (mMediaPlayer != null) mMediaPlayer.resetPlayer();
            if (dataSource == null || textureView == null) {
                return;
            }
            mDataSource = dataSource;
            this.isLooping = isLooping;
            mTextureView = textureView;
            gotoPlay();
            setVolumnOpen(volumnOpen);
            mTextureView.setSurfaceTextureListener(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, "onSurfaceTextureAvailable2");
        if (mMediaPlayer != null) {
            mMediaPlayer.resetPlayer();
        }
        gotoPlay();
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        Log.e(TAG, "onSurfaceTextureSizeChanged");
    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
        Log.e(TAG, "onSurfaceTextureDestroyed2");
        releaseMediaPlayer();
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surface) {
        if (mMediaPlayer != null) {
            int currentPosition = mMediaPlayer.getCurrentPosition();
            int duration = mMediaPlayer.getDuration();
            if (onMediaProgressListener != null) {
                onMediaProgressListener.onMediaProgress(currentPosition, duration);
            }
        }
    }

    public void setOnMediaStateListener(OnMediaStateListener listener) {
        if (mMediaPlayer != null) {
            mMediaPlayer.setOnMediaStateListener(listener);
        }
    }

    public void setOnMediaProgressListener(VideoPlayer.onMediaProgressListener onMediaProgressListener) {
        this.onMediaProgressListener = onMediaProgressListener;
    }

    public interface onMediaProgressListener {
        void onMediaProgress(int currentDegree, int TotalDegree);
    }


    public interface OnMediaStateListener {

        void onError(MediaPlayer mp);

        void onRenderingStart(MediaPlayer mp);

        void onBufferingEnd(MediaPlayer mp);

        void onBufferingStart(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);

        void onRepeat();

    }
}
