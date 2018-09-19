package com.xxm.smartwallpaper.ui;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Surface;
import android.view.TextureView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.manager.BaseConfig;
import com.xxm.smartwallpaper.widget.lowversion.ExoVideoView;

import java.io.IOException;

/**
 * Created by xuqunxing on 2018/9/12.
 */

public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        FrameLayout frameLayout = findViewById(R.id.layout_video_player_container);
        ExoVideoView videoView = new ExoVideoView(this);
        frameLayout.addView(videoView,new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        String videoPath = BaseConfig.WIFI_DOWNLOAD_PATH +"000"+1+".mp4";
        videoView.setVideoPath(videoPath);
    }


}
