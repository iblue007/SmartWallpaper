package com.xxm.smartwallpaper.video;

/**
 * 播放状态回调
 * Created by linliangbin on 2018/5/4 22:32.
 */

public interface PlayerCallbackInterface {

    public void onBufferUpdate(int percent);

    public void onPlayError(int what, int extra);

    public void onPrepared();

    public void onBufferingStart();

    public void onRenderingStart();

    public void onPlayStart();

    public void onPlayComplete();


}
