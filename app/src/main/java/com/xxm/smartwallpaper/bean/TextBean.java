package com.xxm.smartwallpaper.bean;

import java.io.Serializable;

public class TextBean implements Serializable {
    private int alpha;
    private int color;
    private int left;
    private int size;
    private String text;
    private int top;

    public TextBean(int alpha, int left, int top, int size, String text, int color) {
        this.alpha = alpha;
        this.color = color;
        this.text = text;
        this.size = size;
        this.top = top;
        this.left = left;
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

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
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
