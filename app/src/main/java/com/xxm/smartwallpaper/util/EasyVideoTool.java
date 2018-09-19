package com.xxm.smartwallpaper.util;

import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.text.TextUtils;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.inteface.ProxyCallback;

import java.util.Map;

/**
 * @Description: 简易视频播放工具类</br>
 * @author: cxy </br>
 * @date: 2017年05月09日 18:22.</br>
 * @update: </br>
 */

public class EasyVideoTool {

    private static final String TAG = "EasyVideoTool";

    private Surface mSurface;
    private MediaPlayer mMediaPlayer;
    private TextureView mTextureView;
    private String mDataSource;
    private boolean isLooping = true;
    private boolean mVolumnOpen = false;
    private boolean isPreparing = false;
    private boolean isPrepared = false;

    private OnMediaStateListener mMediaStateListener;
    private TextureView.SurfaceTextureListener mSurfaceTextureLinstener;

    private AudioManager mAudioManager;

    private boolean isAudioOccupied = false;

    private long mBufferingStartTime = 0;
    private String mProxyUrl;

    private EasyVideoTool() {
        mAudioManager = (AudioManager) Global.getContext().getSystemService(Context.AUDIO_SERVICE);
    }

    static final class EasyVideoToolHolder {
        static final EasyVideoTool sInstance = new EasyVideoTool();
    }

    public static EasyVideoTool get() {
        return EasyVideoToolHolder.sInstance;
    }

    /**
     * 设置显示画布
     *
     * @param surface
     */
    public void bind(TextureView surface) {
        if (mSurface != null) {
            mSurface.release();
        }
        //清空历史画布
        mSurface = null;
        mTextureView = null;

        //初始化画布
        mTextureView = surface;
        initSurface();
        if (mTextureView != null && mTextureView.isAvailable()) {
            try {
                if (mSurfaceTextureLinstener != null) {
                    mSurfaceTextureLinstener.onSurfaceTextureAvailable(mTextureView.getSurfaceTexture(), mTextureView.getWidth(), mTextureView.getHeight());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void unbind() {
        try {
            release();
            mMediaStateListener = null;
        } catch (Exception e) {
            e.printStackTrace();
        }
       // VideoProxyLoader.getInstance().postPause();
    }

    public void play() throws Exception {
        if (mMediaPlayer == null) {
            initMediaPlayer();
        } else {
            if (mMediaPlayer.isPlaying()) {
                mMediaPlayer.stop();
            }
            mMediaPlayer.reset();
        }
        isPrepared = false;
        isPreparing = false;

        try {
            mProxyUrl = mDataSource;
            mMediaPlayer.setDataSource(mDataSource);
            mMediaPlayer.setSurface(mSurface);
            mMediaPlayer.setLooping(isLooping);
            prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        VideoProxyLoader.getInstance().proxyAsync(mDataSource, new ProxyCallback() {
//
//            @Override
//            public void onPrepared(String originUrl, String proxyUrl, Map<String, String> params) {
//                try {
//                    mProxyUrl = proxyUrl;
//                    mMediaPlayer.setDataSource(proxyUrl);
//                    mMediaPlayer.setSurface(mSurface);
//                    mMediaPlayer.setLooping(isLooping);
//                    prepare();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
    }

    /**
     * 全部释放
     */
    public void release() {
        mTextureView = null;
        mDataSource = null;
        mSurface = null;
        mVolumnOpen = false;

        isPrepared = false;
        isPreparing = false;
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.reset();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                mMediaPlayer.release();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mMediaPlayer = null;
        }
    }

    public void setLooping(boolean isLooping) {
        this.isLooping = isLooping;
    }

    public void setDataSource(String source) {
        restoreBufferingTime();
        this.mDataSource = source;
    }

    public void setVolumnOpen(boolean open) {
        mVolumnOpen = open;
        if (mMediaPlayer != null) {
            float volumn = mVolumnOpen ? 1.0f : 0.0f;
            if (mVolumnOpen) {
                int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
                if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    volumn = 0f;
                }
            } else {
                int result = mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
//                mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
            }
            mMediaPlayer.setVolume(volumn, volumn);
        }
    }

    public void start() {
        if (mMediaPlayer != null) {
            try {
                if (!isAudioOccupied) {
                    switchVolumn(true);
                }
                mMediaPlayer.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void pause() {
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.pause();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void seekTo(int msec) {
        if (mMediaPlayer != null) {
            try {
                mMediaPlayer.seekTo(msec);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isPlaying() {
        if (mMediaPlayer != null) {
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    public boolean isPreparing() {
        return isPreparing && mMediaPlayer != null;
    }

    public boolean isPrepared() {
        return isPrepared && !isPreparing && mMediaPlayer != null;
    }

    private void initSurface() {
        mSurfaceTextureLinstener = new TextureView.SurfaceTextureListener() {
            @Override
            public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
                mSurface = new Surface(surface);
                try {
                    if (mMediaPlayer == null) {
                        initMediaPlayer();
                    }
                    if (mMediaPlayer != null) {
                        mMediaPlayer.setSurface(mSurface);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (mMediaStateListener != null) {
                    mMediaStateListener.onSurfaceAvaible(surface, width, height);
                }
            }

            @Override
            public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

            }

            @Override
            public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
                release();
                return false;
            }

            @Override
            public void onSurfaceTextureUpdated(SurfaceTexture surface) {

            }
        };
        mTextureView.setSurfaceTextureListener(mSurfaceTextureLinstener);
    }

    public boolean prepare() throws Exception {
        if (mMediaPlayer != null) {
            mMediaPlayer.prepareAsync();
            isPreparing = true;
            logBufferingTime();
            return true;
        }
        return false;
    }

    private boolean initMediaPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                isPrepared = true;
                isPreparing = false;
                if (!mVolumnOpen) {
                    mp.setVolume(0, 0);
                }
                boolean consume = false;
                if (mMediaStateListener != null) {
                    consume = mMediaStateListener.onPendingPlay(mp);
                }
                if (!consume) {
                    start();
                }
            }
        });

        mMediaPlayer.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {
            @Override
            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                if (mMediaStateListener != null) {
                    mMediaStateListener.onBufferingUpdate(mp, percent);
                }
            }
        });

        mMediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                if (mMediaStateListener != null) {
                    mMediaStateListener.onError(mp, what, extra);
                }
                if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN || what == -38) {
//                    MessageUtils.showOnlyToast(Global.getContext(), "视频播放失败！");
                }
                return false;
            }
        });

        mMediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    logBufferingTime();
                    if (mMediaStateListener != null) {
                        mMediaStateListener.onRenderingStart(mp);
                    }
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START) {
                    if (mMediaStateListener != null) {
                        mMediaStateListener.onBufferingStart(mp);
                    }
                } else if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END) {
                    logBufferingTime();
                    if (mMediaStateListener != null) {
                        mMediaStateListener.onBufferingEnd(mp);
                    }
                }
                return false;
            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                if (mMediaStateListener != null) {
                    mMediaStateListener.onCompletion(mp);
                }
            }
        });

        mMediaPlayer.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
            @Override
            public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
                if (mMediaStateListener != null) {
                    mMediaStateListener.onVideoSizeChanged(mp, width, height);
                }
            }
        });

        return true;
    }

    private void switchVolumn(boolean on) {
        if (on) {
            //获取焦点，restart
            float volumn = mVolumnOpen ? 1f : 0f;
            if (mMediaPlayer != null) {
                mMediaPlayer.setVolume(volumn, volumn);
            }
        } else {
            if (mMediaPlayer != null) {
                mMediaPlayer.setVolume(0f, 0f);
            }
        }
    }

    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (mMediaPlayer == null) {
                return;
            }
            if (focusChange == AudioManager.AUDIOFOCUS_GAIN
                    || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT
                    || focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_MAY_DUCK) {
                //获取焦点，restart
                switchVolumn(true);
//                start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                //失去焦点，stop
                switchVolumn(false);
//                stop();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                //失去瞬时焦点，pause
                switchVolumn(false);
//                pause();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                //音量变小
                switchVolumn(false);
            }
        }
    };

    private void restoreBufferingTime() {
        mBufferingStartTime = 0;
    }

    private void logBufferingTime() {
        //本地视频不做缓冲时长统计
        if (TextUtils.isEmpty(mProxyUrl) || FileUtil.isFileExits(mProxyUrl)) {
            return;
        }
        if (mBufferingStartTime == 0) {
            //记录起始时间
            mBufferingStartTime = System.currentTimeMillis();
            Log.e(TAG, "start logging");
        } else if (mBufferingStartTime != -1) {
            //开始播放
            long delta = System.currentTimeMillis() - mBufferingStartTime;
            if (delta < 0) {
                return;
            }
            if (delta < 2000) {
                delta = (delta + 99) / 100 * 100;
                Log.e(TAG, "buffering = " + delta + "(ms)");
            } else {
                delta = (delta + 999) / 1000;
                Log.e(TAG, "buffering = " + delta + "(s)");
            }
            mBufferingStartTime = -1;
        }
    }

    public void setOnMediaStateListener(OnMediaStateListener listener) {
        mMediaStateListener = listener;
    }

    public interface OnMediaStateListener {

        void onSurfaceAvaible(SurfaceTexture surface, int width, int height);

        void onRenderingStart(MediaPlayer mp);

        void onBufferingEnd(MediaPlayer mp);

        void onBufferingStart(MediaPlayer mp);

        void onCompletion(MediaPlayer mp);

        void onError(MediaPlayer mp, int what, int extra);

        void onBufferingUpdate(MediaPlayer mp, int percent);

        boolean onPendingPlay(MediaPlayer mp);

        void onVideoSizeChanged(MediaPlayer mp, int width, int height);
    }

    public static class OnMediaStateListenerAdapter implements OnMediaStateListener {
        @Override
        public void onSurfaceAvaible(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public void onRenderingStart(MediaPlayer mp) {
        }

        @Override
        public void onBufferingEnd(MediaPlayer mp) {
        }

        @Override
        public void onBufferingStart(MediaPlayer mp) {
        }

        @Override
        public void onCompletion(MediaPlayer mp) {
        }

        @Override
        public void onError(MediaPlayer mp, int what, int extra) {
        }

        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
        }

        @Override
        public boolean onPendingPlay(MediaPlayer mp) {
            return false;
        }

        @Override
        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {

        }
    }
}
