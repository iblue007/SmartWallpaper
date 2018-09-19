package com.xxm.smartwallpaper.bean;

import java.io.Serializable;

public class PicBean implements Serializable {
    private int alpha;
    private String file;
    private int height;
    private int left;
    private int top;
    private int width;

    public PicBean(String file, int alpha, int left, int top, int width, int height) {
        this.file = file;
        this.alpha = alpha;
        this.height = height;
        this.width = width;
        this.top = top;
        this.left = left;
    }

    public String getFile() {
        return this.file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getAlpha() {
        return this.alpha;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTop() {
        return this.top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getLeft() {
        return this.left;
    }

    public void setLeft(int left) {
        this.left = left;
    }
}
