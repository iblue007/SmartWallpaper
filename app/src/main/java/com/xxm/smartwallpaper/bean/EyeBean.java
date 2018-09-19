package com.xxm.smartwallpaper.bean;

import java.io.Serializable;

public class EyeBean implements Serializable {
    private int alpha;
    private int color;

    public EyeBean(int alpha, int color) {
        this.color = color;
        this.alpha = alpha;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }
}
