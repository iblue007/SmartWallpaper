package com.xxm.smartwallpaper.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import com.xxm.smartwallpaper.adapter.BaseRecyclerViewHolder;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年06月20日 10:30.</br>
 * @update: </br>
 */

public class FunPopup extends PopupWindow {

    private Window mWindow;
    private Context mContext;
    private float mShowBgLevel = 1.0f;
    private float mDimAmount = 1.0f;
    private boolean mEnabledDimBehind = false;

    private BaseRecyclerViewHolder mHolder;

    public FunPopup(Builder builder) {
        super(builder.contentView, builder.layoutParams.width, builder.layoutParams.height, true);
        mHolder = BaseRecyclerViewHolder.get(builder.contentView);
        this.mDimAmount = builder.dimAmount;
        this.mEnabledDimBehind = builder.isDimBehind;
        this.mContext = builder.ctx;
        setAnimationStyle(builder.animResId);
        mShowBgLevel = builder.alpha;
        setOutsideTouchable(builder.outsideTouchable);
        setBackgroundDrawable(new ColorDrawable(Color.parseColor("#00000000")));
        setSoftInputMode(builder.softInputMode);
        setOnDismissListener(null);
    }

    @Override
    public void setOnDismissListener(final OnDismissListener onDismissListener) {
        super.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundLevel(0.0f);
                if (onDismissListener != null) {
                    onDismissListener.onDismiss();
                }
            }
        });
    }

    @Override
    public void showAsDropDown(View anchor) {
        super.showAsDropDown(anchor);
        setBackgroundLevel(mShowBgLevel);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        super.showAsDropDown(anchor, xoff, yoff);
        setBackgroundLevel(mShowBgLevel);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        super.showAsDropDown(anchor, xoff, yoff, gravity);
        setBackgroundLevel(mShowBgLevel);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        setBackgroundLevel(mShowBgLevel);
    }

    public BaseRecyclerViewHolder getHolder() {
        return mHolder;
    }

    private void setBackgroundLevel(float level) {
        try {
            // window.attr.alpha、dim behind 都是坑爹的
            Drawable bg = getBackground();
            if (bg instanceof ColorDrawable) {
                ColorDrawable cd = (ColorDrawable) bg;
                int alpha = (int) (level * 255);
                alpha = alpha < 0 ? 0 : alpha > 255 ? 255 : alpha;
                cd.setColor(Color.argb(alpha, 0, 0, 0));
                setBackgroundDrawable(cd);
            }
//            if (mEnabledDimBehind) {
//                View container = (View) getContentView().getParent().getParent();
//                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
//                WindowManager.LayoutParams lp = (WindowManager.LayoutParams) container.getLayoutParams();
//                lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
//                lp.dimAmount = level;
//                wm.updateViewLayout(container, lp);
//            } else {
//                mWindow = ((Activity) mContext).getWindow();
//                WindowManager.LayoutParams lp = mWindow.getAttributes();
//                lp.alpha = level;
//                mWindow.setAttributes(lp);
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class Builder {

        Context ctx;

        View contentView;
        ViewGroup.LayoutParams layoutParams;
        int animResId;
        float alpha = 0.0f;//默认不透
        float dimAmount = 0.0f;
        boolean isDimBehind = false;
        boolean outsideTouchable = true;
        int softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNCHANGED;

        public Builder(Context context) {
            this.ctx = context;
        }

        public Builder setContentView(int layoutId) {
            contentView = LayoutInflater.from(ctx).inflate(layoutId, null);
            return this;
        }

        public Builder setContentView(View view) {
            this.contentView = view;
            return this;
        }

        public Builder setLayoutParams(ViewGroup.LayoutParams layoutParams) {
            this.layoutParams = layoutParams;
            return this;
        }

        public Builder setLayoutParams(int w, int h) {
            this.layoutParams = new ViewGroup.LayoutParams(w, h);
            return this;
        }

        public Builder setAnimationStyle(int animId) {
            this.animResId = animId;
            return this;
        }

        public Builder setBackgroundAlpha(float alpha) {
            this.alpha = alpha;
            return this;
        }

        public Builder setDimAmount(float dimAmount) {
            this.isDimBehind = true;
            this.dimAmount = dimAmount;
            return this;
        }

        public Builder setOutsideTouchable(boolean touchable) {
            this.outsideTouchable = touchable;
            return this;
        }

        public Builder setSoftInputMode(int mode) {
            this.softInputMode = mode;
            return this;
        }

        public FunPopup create() {
            FunPopup popup = new FunPopup(this);
            return popup;
        }
    }
}
