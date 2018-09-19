package com.xxm.smartwallpaper.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.xxm.smartwallpaper.util.ScreenUtil;


/**
 * Created by Administrator on 2016/10/27.
 */
public class RotateImageView extends ImageView {
    Bitmap bitmap = null;
    ValueAnimator ani = null;
    boolean postPause = false;

    public RotateImageView(Context context) {
        super(context);
        init();
    }

    public RotateImageView(Context context, AttributeSet attr) {
        super(context, attr);
        init();
    }

    private float scale = 1;
    private float angel = 0;
    private Paint paint = new Paint();

    private void init() {
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        findBitmap();
        if (bitmap == null) {
            return;
        }
        canvas.save();
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.scale(scale, scale);
        canvas.rotate(angel);
        canvas.translate(-getWidth() / 2, -getHeight() / 2);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        canvas.restore();
    }

    public void startAni() {
        stopAni();
        postPause = false;
        ani = ValueAnimator.ofFloat(0, 1);
        ani.setEvaluator(new TypeEvaluator() {
            @Override
            public Object evaluate(float f, Object s, Object e) {
                float rate = 1;
                float state1 = 0.5f;
                if (f < state1) {
                    rate = f / state1;
                    scale = 0.5f + 0.5f * (1 - rate);
                    angel = 90 * rate;
                } else {
                    rate = (f - state1) / (1 - state1);
                    scale = 0.5f + 0.5f * rate;
                    angel = 90 + 90 * rate;


                }
                RotateImageView.this.postInvalidate();
                return f;
            }
        });
        ani.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                try {
                    if (postPause) {
                        animation.end();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        ani.setInterpolator(new LinearInterpolator());
        ani.setDuration(1000);
        ani.setRepeatCount(-1);
        ani.setRepeatMode(Animation.RESTART);
        ani.start();

    }

    public void stopAni() {
        postPause = true;
        if (ani != null) {
            ani.end();
            ani = null;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = 0;
        int h = 0;
        findBitmap();
        if (bitmap != null) {
            w = bitmap.getWidth() + getPaddingBottom() + getPaddingRight();
            h = bitmap.getHeight() + getPaddingTop() + getPaddingBottom();
        } else {
            w = ScreenUtil.dip2px(getContext(), 50) + getPaddingBottom() + getPaddingRight();
            h = ScreenUtil.dip2px(getContext(), 50) + getPaddingTop() + getPaddingBottom();
        }
        setMeasuredDimension(w, h);
    }

    private void findBitmap() {
        if (bitmap == null) {
            Drawable drawable = getDrawable();
            if (drawable instanceof BitmapDrawable) {
                BitmapDrawable bd = (BitmapDrawable) drawable;
                Bitmap b = bd.getBitmap();
                if (b != null) {
                    bitmap = b;
                }
            }
        }
    }
}
