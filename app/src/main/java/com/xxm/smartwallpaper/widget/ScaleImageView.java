package com.xxm.smartwallpaper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.xxm.smartwallpaper.R;


/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月03日 21:20.</br>
 * @update: </br>
 */

public class ScaleImageView extends ImageView {

    private int mDirection = 0;//基于宽来计算高 1：基于高来计算宽
    private float mRatio = 0.5627f;

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.ScaleImageView);
        mDirection = array.getInt(R.styleable.ScaleImageView_direction, 0);

        mRatio = array.getFloat(R.styleable.ScaleImageView_ratio, 0.5627f);

        array.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int w = getMeasuredWidth();
        int h = getMeasuredHeight();
        if (mDirection == 0) {
            h = (int) (w / mRatio);
            setMeasuredDimension(w, h);
        } else {
            w = (int) (h * mRatio);
            setMeasuredDimension(w, h);
        }
    }
}
