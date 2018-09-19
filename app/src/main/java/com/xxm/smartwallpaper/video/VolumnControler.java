package com.xxm.smartwallpaper.video;

import android.content.Context;
import android.media.AudioManager;

import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.config.BaseConfigPreferences;

/**
 * 音量控制工具类
 * Created by linliangbin on 2017/7/6 11:02.
 */

public class VolumnControler {

    /**
     * 首次打开声音时的系统播放音量上限
     */
    public static final float VOLUME_UP_LIMIT = 0.5f;


    /**
     * 当前播放使用的音量 默认为当前系统音量，第一次播放时根据系统音量进行调节
     */
    public static final float VOLUME_NOT_INIT = -1.0F;


    private static VolumnControler instace;
    public float volumeOnValue = VOLUME_NOT_INIT;
    /**
     * 计算当前播放音量时的系统音量比例
     */
    public float systemVolume;
    private AudioManager audioManager;

    public VolumnControler() {
        audioManager = (AudioManager) Global.getContext().getSystemService(Context.AUDIO_SERVICE);
        systemVolume = getCurrentSystemVolumeRate();
    }

    public synchronized static VolumnControler getInstance() {
        if (instace == null) {
            instace = new VolumnControler();
        }
        return instace;
    }

    /**
     * @desc 获取当前视频播放音量
     * @author linliangbin
     * @time 2017/7/6 13:47
     */
    public float getVolumeValue() {

        float volume = 1.0f;
        if (hasSystemVolumeChange()) {
            volume = 1.0f;
        } else if (volumeOnValue == VOLUME_NOT_INIT) {
            volumeOnValue = computeComfortableVolume();
            volume = volumeOnValue;
        } else {
            volume = volumeOnValue;
        }
        return volume;
    }


    /**
     * @desc 上次音量开关切换后，系统音量是否已经变动过
     * @author linliangbin
     * @time 2017/7/6 13:39
     */
    public boolean hasSystemVolumeChange() {

        if (BaseConfigPreferences.getInstance(Global.getContext()).isSystemVolumeChanged()) {
            return true;
        }

        float current = getCurrentSystemVolumeRate();
        if (Math.abs(current - systemVolume) <= 0.01) {
            return false;
        } else {
            BaseConfigPreferences.getInstance(Global.getContext()).setSystemVolumeChanged(true);
            return true;
        }
    }

    /**
     * @desc 获取当前系统音量比例
     * @author linliangbin
     * @time 2017/7/6 13:48
     */
    private float getCurrentSystemVolumeRate() {

        int currentSystenVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxSystenVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        float currentSystemVolumeRate = currentSystenVolume / (float) maxSystenVolume;
        return currentSystemVolumeRate;
    }

    /**
     * @desc 计算当前合适的音量
     * 音量比例非线性
     * @author linliangbin
     * @time 2017/7/6 14:06
     */
    private float computeComfortableVolume() {
        try {
            float currentSystemVolumeRate = getCurrentSystemVolumeRate();
            int MAX_VOLUME = 1000;
            float soundVolume = MAX_VOLUME;
            if (currentSystemVolumeRate >= VOLUME_UP_LIMIT) {
                soundVolume = MAX_VOLUME * VOLUME_UP_LIMIT;
                return (float) (1 - (Math.log(MAX_VOLUME - soundVolume) / Math.log(MAX_VOLUME)));
            }else{
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1.0f;
    }


}
