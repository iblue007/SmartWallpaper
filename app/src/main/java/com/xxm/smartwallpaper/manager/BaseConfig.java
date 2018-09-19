package com.xxm.smartwallpaper.manager;

import android.os.Environment;

/**
 * Created by xuqunxing on 2018/8/20.
 */

public class BaseConfig {

    private static final String BASE_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ScreenVideopaper/";

    public static final String WIFI_DOWNLOAD_PATH = BASE_DIR + "WifiDownload/";

    /**
     * 临时资源目录
     */
    public static final String SOURCE_TEMP_DIR = BASE_DIR + "tmp/";


    public static final String SOURCE_TEMP_ONLINE_CACHE = SOURCE_TEMP_DIR + "onlinecache/";

    public static final String SOURCE_TEMP_VIDEO_DIR = SOURCE_TEMP_DIR + "video/";

}
