package com.xxm.smartwallpaper.video;

import android.view.SurfaceHolder;
import android.view.TextureView;

/**
 * Created by linliangbin on 2018/5/4 22:20.
 */

public interface VideoPlayerInterface {

    public void resetPlayer();

    /**
     * 恢复播放
     */
    public void resumePlaying();


    /**
     * 暂停播放
     */
    public void pausePlaying();


    /**
     * 获取当前播放进度
     *
     * @return
     */
    public int getCurrentPosition();


    /**
     * 获取视频长度
     *
     * @return
     */
    public int getDuration();

    /**
     * 当前是否在播放
     *
     * @return
     */
    public boolean isPlaying();

    /**
     * 释放资源
     */
    public void release();


    /**
     * @desc 播放本地文件
     * @author linliangbin
     * @time 2018/5/22 17:59
     */
    public void playLocalFile(TextureView textureView, String path, boolean isLooping);

    public void playLocalFile(SurfaceHolder surfaceHolder, String path, boolean isLooping);

    /**
     * @desc 播放服务端视频文件
     * @author linliangbin
     * @time 2018/5/22 17:59
     */
    public void playServerUrl(TextureView textureView, String path, boolean isLooping);

    public void setOnMediaStateListener(VideoPlayer.OnMediaStateListener listener);


    public void setVolume(float volume);

    /**
     * @desc 当前是否处理暂停播放
     * @author linliangbin
     * @time 2018/5/25 16:16
     */
    public boolean isOnPausePlay();


    public void setVideoSizeListener(VideoSizeListener videoSizeListener);


}
