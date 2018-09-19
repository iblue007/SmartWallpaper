package com.xxm.smartwallpaper.video;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.TextureView;

import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.PlaybackPreparer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.drm.DrmSessionManager;
import com.google.android.exoplayer2.drm.FrameworkMediaCrypto;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.LoopingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.HttpDataSource;
import com.google.android.exoplayer2.upstream.TransferListener;
import com.google.android.exoplayer2.upstream.cache.CacheDataSourceFactory;
import com.google.android.exoplayer2.util.EventLogger;
import com.google.android.exoplayer2.util.Util;
import com.google.android.exoplayer2.video.VideoListener;
import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.config.BaseConfigPreferences;


/**
 * 基于EXOPlayer 播放器
 * Created by linliangbin on 2018/5/4 20:37.
 */

public class CustomExoPlayer implements PlaybackPreparer, VideoPlayerInterface {
    private static final DefaultBandwidthMeter BANDWIDTH_METER = new DefaultBandwidthMeter();
    private static final String TAG = "CustomExoPlayer";

    public VideoPlayer.OnMediaStateListener callbackInterface;
    Context context;
    Handler handler;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private EventLogger eventLogger;
    private DataSource.Factory mediaDataSourceFactory;
    private Handler mediaHandler;
    private TextureView textureView;
    private SurfaceHolder surfaceHolder;
    private VideoSizeListener videoSizeListener;

    private boolean isOnPausePlay = false;
    //缓冲开始时间（毫秒）
    private long mBufferingStartTime = 0;

    private float mVolumeValue = 0.0f;


    public CustomExoPlayer(Context context) {
        this.context = context;
        handler = new Handler();
        mediaDataSourceFactory = buildDataSourceFactory(true);
        TrackSelection.Factory adaptiveTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(BANDWIDTH_METER);
        trackSelector = new DefaultTrackSelector(adaptiveTrackSelectionFactory);
        eventLogger = new EventLogger(trackSelector);

        DrmSessionManager<FrameworkMediaCrypto> drmSessionManager = null;


        int extensionRendererMode = DefaultRenderersFactory.EXTENSION_RENDERER_MODE_OFF;
        DefaultRenderersFactory renderersFactory = new DefaultRenderersFactory(context,
                drmSessionManager, extensionRendererMode);

        player = ExoPlayerFactory.newSimpleInstance(renderersFactory, trackSelector);
        player.addListener(new PlayerEventListener());
        player.addListener(eventLogger);
        player.addMetadataOutput(eventLogger);
        player.addAudioDebugListener(eventLogger);
        player.addVideoDebugListener(eventLogger);
        player.setPlayWhenReady(true);
        player.addVideoListener(new PlayerVideoListener());


        HandlerThread mMediaHandlerThread = new HandlerThread("CustomExoPlayer");
        mMediaHandlerThread.start();
        mediaHandler = new Handler((mMediaHandlerThread.getLooper()));

        mVolumeValue = BaseConfigPreferences.getInstance(context).isVideoPreviewVolumnEnable() ? 1.0f : 0.0f;


    }

    private void updateVolumeSwitch() {

        if (BaseConfigPreferences.getInstance(context).isVideoWallpaperVolumnEnable()) {
            player.setVolume(1);
        } else {
            player.setVolume(0);
        }
    }

    /**
     * Returns a {@link DataSource.Factory}.
     */
    public DataSource.Factory buildDataSourceFactory(TransferListener<? super DataSource> listener) {
        return new DefaultDataSourceFactory(context, listener, buildHttpDataSourceFactory(listener));
    }

    /**
     * Returns a {@link HttpDataSource.Factory}.
     */
    public HttpDataSource.Factory buildHttpDataSourceFactory(
            TransferListener<? super DataSource> listener) {
        return new DefaultHttpDataSourceFactory(Util.getUserAgent(context, "videowallpaper"), listener);
    }

    /**
     * Returns a new DataSource factory.
     *
     * @param useBandwidthMeter Whether to set {@link #BANDWIDTH_METER} as a listener to the new
     *                          DataSource factory.
     * @return A new DataSource factory.
     */
    private DataSource.Factory buildDataSourceFactory(boolean useBandwidthMeter) {
        return buildDataSourceFactory(useBandwidthMeter ? BANDWIDTH_METER : null);
    }

    @Override
    public void preparePlayback() {

    }

    @Override
    public void resetPlayer() {

    }

    @Override
    public void resumePlaying() {
        isOnPausePlay = false;
        player.setPlayWhenReady(true);
    }

    @Override
    public void pausePlaying() {
        isOnPausePlay = true;
        player.setPlayWhenReady(false);
    }

    public void logBufferingTime() {

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


    @Override
    public int getCurrentPosition() {
        return (int) player.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return (int) player.getDuration();
    }


    @Override
    public boolean isPlaying() {
        return player.getPlaybackState() == Player.STATE_READY;
    }

    @Override
    public void release() {
        player.release();
    }

    @Override
    public void playLocalFile(TextureView textureView, String path, boolean isLooping) {
        this.textureView = textureView;
        Uri uris = Uri.parse(path);
        player.setVideoTextureView(textureView);
        MediaSource mediaSource;
        if (isLooping) {
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
        }
        mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(uris, mediaHandler, eventLogger);
        player.prepare(mediaSource, false, false);
    }

    @Override
    public void playLocalFile(SurfaceHolder surfaceHolder, String path, boolean isLooping) {
        this.surfaceHolder = surfaceHolder;
        player.setVideoSurface(surfaceHolder.getSurface());
        Uri uris = Uri.parse(path);
        MediaSource mediaSource;
        if (isLooping) {
            player.setRepeatMode(Player.REPEAT_MODE_ONE);
        }
        mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                .createMediaSource(uris, mediaHandler, eventLogger);
        player.prepare(mediaSource, false, false);
    }


    @Override
    public void playServerUrl(TextureView textureView, String path, boolean isLooping) {
        this.textureView = textureView;
        Uri uris = Uri.parse(path);
        player.setVideoTextureView(textureView);
        MediaSource mediaSource;
        if (isLooping) {
            mediaSource = new LoopingMediaSource(new ExtractorMediaSource(uris,
                    new CacheDataSourceFactory(ExoPlayerCache.getInstance().getCacheStrategy(), mediaDataSourceFactory), new DefaultExtractorsFactory(), mediaHandler, null));
        } else {
            mediaSource = new ExtractorMediaSource.Factory(mediaDataSourceFactory)
                    .createMediaSource(uris, mediaHandler, eventLogger);
        }
        mBufferingStartTime = 0;
        logBufferingTime();
        player.prepare(mediaSource, false, false);
    }

    @Override
    public void setOnMediaStateListener(VideoPlayer.OnMediaStateListener listener) {
        this.callbackInterface = listener;
    }


    @Override
    public void setVolume(float left) {
        mVolumeValue = left;
        player.setVolume(left);
    }

    @Override
    public boolean isOnPausePlay() {
        return isOnPausePlay;
    }

    @Override
    public void setVideoSizeListener(VideoSizeListener videoSizeListener) {
        this.videoSizeListener = videoSizeListener;
    }



    private class PlayerVideoListener implements VideoListener {
        @Override
        public void onVideoSizeChanged(final int width, final int height, final int unappliedRotationDegrees, final float pixelWidthHeightRatio) {
            if (videoSizeListener != null) {
                videoSizeListener.onSizeChanged(width, height);
                return;
            }
            Global.runInMainThread(new Runnable() {
                @Override
                public void run() {
                    if (textureView != null && textureView instanceof AutosizeTexture) {
                        float videoAspectRatio = (height == 0 || width == 0) ? 1 : (width * pixelWidthHeightRatio) / height;
                        ((AutosizeTexture) textureView).setAspectRatio(videoAspectRatio);
                    }
                }
            });
        }

        @Override
        public void onRenderedFirstFrame() {

        }
    }

    private class PlayerEventListener implements Player.EventListener {

        @Override
        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
            if (callbackInterface != null) {
                switch (playbackState) {
                    case Player.STATE_ENDED:
                        break;
                    case Player.STATE_READY:
                        if (playWhenReady) {
                            player.setVolume(mVolumeValue);
                            logBufferingTime();
                            callbackInterface.onRenderingStart(null);
                        }
                        break;
                    case Player.STATE_BUFFERING:
                        callbackInterface.onBufferingStart(null);
                        break;
                }
            }
        }

        @Override
        public void onRepeatModeChanged(int repeatMode) {
            if (callbackInterface != null) {

            }
        }

        @Override
        public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {
            if (callbackInterface != null) {

            }
        }

        @Override
        public void onPositionDiscontinuity(@Player.DiscontinuityReason int reason) {
            if (callbackInterface != null) {
                callbackInterface.onRepeat();
            }
        }

        @Override
        public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {
            if (callbackInterface != null) {

            }
        }

        @Override
        public void onSeekProcessed() {
            if (callbackInterface != null) {

            }
        }

        @Override
        public void onPlayerError(ExoPlaybackException e) {
            if (callbackInterface != null) {
                callbackInterface.onError(null);
            }
        }

        @Override
        public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
            if (callbackInterface != null) {

            }
        }

        @Override
        @SuppressWarnings("ReferenceEquality")
        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
            if (callbackInterface != null) {

            }
        }

        @Override
        public void onLoadingChanged(boolean isLoading) {
            if (callbackInterface != null) {

            }

        }
    }
}
