package com.xxm.smartwallpaper.bean;

import java.io.Serializable;

public class VideoBean implements Serializable {
    private float alpha = 0;
    private String videoPath;
    private boolean noUpdateUri = false;

    public float getAlpha() {
        if(this.alpha == 0){
            return 0.5f;
        }
        return this.alpha;
    }

    public void setAlpha(float alpha) {
        this.alpha = alpha;
        noUpdateUri = true;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public boolean isNoUpdateUri() {
        return noUpdateUri;
    }

    public void setNoUpdateUri(boolean noUpdateUri) {
        this.noUpdateUri = noUpdateUri;
    }
}
