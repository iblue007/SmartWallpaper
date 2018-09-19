package com.xxm.smartwallpaper.ui;

import android.app.WallpaperManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.xxm.smartwallpaper.BuildConfig;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.config.UMEventAnalytic;
import com.xxm.smartwallpaper.util.CommonUtil;
import com.xxm.smartwallpaper.util.MessageUtils;

import java.io.IOException;

/**
 * Created by xuqunxing on 2018/8/28.
 */

public class SpecialActivity extends BaseAcitivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        TextView versionCodeTv = (TextView) findViewById(R.id.activit_version_code);
        String versionName = BuildConfig.VERSION_NAME;
        versionCodeTv.setText("当前版本号：v" + versionName);
        findViewById(R.id.add_qq_group).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonUtil.joinQQGroup(SpecialActivity.this);
            }
        });
        findViewById(R.id.set_white_wallpaper).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    MobclickAgent.onEvent(getApplicationContext(), UMEventAnalytic.SET_WALLPAPER);
                    WallpaperManager manager = WallpaperManager.getInstance(SpecialActivity.this);
                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.white);
                    manager.setBitmap(bitmap);
                    MessageUtils.show("设置成功0.0");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
