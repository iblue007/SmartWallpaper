package com.xxm.smartwallpaper.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.xxm.smartwallpaper.R;


/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月08日 13:07.</br>
 * @update: </br>
 */

public class LoadStateView extends FrameLayout {

    public static final int STATE_NONE = 0;
    public static final int STATE_LOADING = 1;
    public static final int STATE_ERROR = 2;
    public static final int STATE_NOTHING = 3;

    private View layerLoading;
    private View layerError;
    private View layerNothing;

    private ImageView mErrorImg;
    private TextView mErrorCode;
    private Button mBtnReload;
    private TextView mErrorCause;

    private ImageView mNothingImg;
    private TextView mNothingCode;
    private Button mBtnNothingReload;
    private TextView mNothingTip;

    private RotateImageView mLoadingView;
    private TextView mLoadingHint;

    private OnRetryListener mListener;
    private int mCurrentState = STATE_NONE;

    public LoadStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        inflate(getContext(), R.layout.view_load_state, this);
        layerLoading = findViewById(R.id.view_loading);
        layerError = findViewById(R.id.view_neterror_setting);
        layerNothing = findViewById(R.id.view_nothing_setting);

        mBtnReload = (Button) findViewById(R.id.btn_reload);
        mErrorImg = (ImageView) findViewById(R.id.iv_error_img);
        mErrorCode = (TextView) findViewById(R.id.tv_error_code);
        mErrorCause = (TextView) findViewById(R.id.tv_error_cause);

        mNothingImg = (ImageView) findViewById(R.id.iv_nothing_img);
        mNothingCode = (TextView) findViewById(R.id.tv_nothing_code);
        mNothingTip = (TextView) findViewById(R.id.tv_nothing_cause);
        mBtnNothingReload = (Button) findViewById(R.id.btn_nothing_reload);

        mLoadingView = (RotateImageView) findViewById(R.id.progress_small_title);
        mLoadingHint = (TextView) findViewById(R.id.tv_loading_hint);

        mBtnReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onReload();
                }
            }
        });

        mBtnNothingReload.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onNothingReload();
                }
            }
        });

        updateStateView();
    }

    public void setErrorImage(int resId) {
        mErrorImg.setImageResource(resId);
    }

    public void setErrorImage(Drawable image) {
        mErrorImg.setImageDrawable(image);
    }

    public void setErrorCode(int code) {
        mErrorCode.setText("出错码：" + code);
    }

    public void setErrorCause(CharSequence cause) {
        mErrorCause.setText(cause);
    }

    public void setNothingImage(int resId) {
        mNothingImg.setImageResource(resId);
    }

    public void setNotingImage(Drawable image) {
        mNothingImg.setImageDrawable(image);
    }

    public void setNothingCode(int code) {
        mNothingCode.setText("状态：" + code);
    }

    public void setNothingTip(CharSequence tip) {
        mNothingTip.setText(tip);
    }

    public void setNothingButtonDesc(CharSequence desc) {
        mBtnNothingReload.setText(desc);
    }

    public void setRetryButtonDesc(CharSequence desc) {
        mBtnReload.setText(desc);
    }

    public void setLoadingHint(CharSequence hint) {
        mLoadingHint.setText(hint);
    }

    /**
     * @param visibility {@link View#setVisibility(int)}
     */
    public void setRetryButtonVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            mBtnReload.setVisibility(visibility);
        }
    }

    /**
     * @param visibility {@link View#setVisibility(int)}
     */
    public void setNothingButtonVisibility(int visibility) {
        if (visibility == View.GONE || visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            mBtnNothingReload.setVisibility(visibility);
        }
    }

    public void setBackgroundTransparent() {
        layerLoading.setBackgroundColor(Color.TRANSPARENT);
        layerError.setBackgroundColor(Color.TRANSPARENT);
        layerNothing.setBackgroundColor(Color.TRANSPARENT);
//        layerError.setBackgroundColor(Color.TRANSPARENT);
        setBackgroundColor(Color.TRANSPARENT);
    }

    public void setClickable(boolean clickable) {
        layerLoading.setClickable(clickable);
    }

    public void setOnRetryListener(OnRetryListener listener) {
        this.mListener = listener;
    }

    public void updateState(int state) {
        if (mCurrentState == state) {
            return;
        }

        if (mCurrentState == STATE_LOADING) {
            try {
                mLoadingView.stopAni();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mCurrentState = state;
        updateStateView();
    }

    private void updateStateView() {
        if (mCurrentState == STATE_NONE) {
            layerLoading.setVisibility(GONE);
            layerError.setVisibility(GONE);
            layerNothing.setVisibility(GONE);
        } else if (mCurrentState == STATE_ERROR) {
            layerError.setVisibility(VISIBLE);
            layerLoading.setVisibility(GONE);
            layerNothing.setVisibility(GONE);
        } else if (mCurrentState == STATE_NOTHING) {
            layerLoading.setVisibility(GONE);
            layerError.setVisibility(GONE);
            layerNothing.setVisibility(VISIBLE);
        } else {
            try {
                mLoadingView.startAni();
            } catch (Exception e) {
                e.printStackTrace();
            }
            layerLoading.setVisibility(VISIBLE);
            layerError.setVisibility(GONE);
            layerNothing.setVisibility(GONE);
        }
    }

    @Override
    public boolean isInEditMode() {
        return true;
//        return super.isInEditMode();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mLoadingView != null) {
            mLoadingView.stopAni();
            mLoadingView = null;
        }
    }

    public interface OnRetryListener {
        void onReload();

        void onNothingReload();
    }
}
