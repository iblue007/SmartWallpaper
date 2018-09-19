package com.xxm.smartwallpaper.config;

import android.content.Context;
import android.content.SharedPreferences;

public class BaseConfigPreferences {

    public static final String NAME = "smartwallpaper";
    /**
     * 是否系统音量改变
     */
    public static final String IS_SYSTEM_VOLUME_CHANGE = "is_system_volume_change";
    /**
     * 首页推荐视频壁纸音乐是否开启
     */
    public static final String IS_VIDEO_WALLPAPER_VOLUMN_ENABLE = "is_video_wallpaper_volumn_enable";
    /**
     * 预览视频音乐是否开启
     */
    public static final String IS_VIDEO_PREVIEW_VOLUMN_ENABLE = "is_video_preview_volumn_enable";
    /**
     * 设置页声音是否开启，设置页声音控制设为桌面壁纸的声音
     */
    public static final String IS_SETTINGS_VOLUMN_ENABLE = "is_settings_volumn_enable";
    /**
     * 是否是第一次进入裁剪页面
     */
    public static final String IS_FIRST_VIDEO_CLIP = "is_first_video_clip";
    /**
     * 当前应用的天天视频壁纸切换时间间隔(0为开屏切换)
     */
    public static final String KEY_CURRENT_SERIES_INTERVAL = "key_current_series_interval";
    /**
     * 上一套视频壁纸应用时间
     */
    public static final String KEY_LAST_VIDEO_APPLY_TIME = "key_last_video_apply_time";
    /**
     * 当前应用的天天视频壁纸ID+identifier
     */
    public static final String KEY_CURRENT_SERIES_ID_AND_IDENTIFIER = "key_current_series_id_and_identifier";
    /**
     * 最新的媒体文件路径
     */
    public static final String KEY_LATEST_MEDIA_PATH = "latest_media_path";
    /**
     * 91桌面传过来的静态壁纸的路径
     */
    public static final String STATIC_WALLPAPER_PATH = "static_wallpaper_path";
    /**
     * 应用视频壁纸后是否要弹toast
     */
    public static final String STATIC_WALLPAPER_SHOW_TOAST = "static_wallpaper_show_toast";
    /**
     * 待替换的媒体文件路径
     */
    public static final String KEY_PENDING_MEDIA_PATH = "pending_media_path";

    /**
     * 第一次打开解锁后的多个广告
     */
    public static final String AD_SCREEN_ON_ADS_FIRST = "ad_screen_on_ads_first";
    /**
     * 打开壁纸的次数
     */
    public static final String ON_TIME_OPEN_ALBUM = "on_time_open_album";
    /**
     * 桌面动态壁纸（服务）启动时间（天）
     */
    public static final String KEY_RECORD_LIFECYCLE_START_TIME = "key_record_lifecycle_start_time";
    /**
     * 桌面动态壁纸（服务）终止时间（天）
     */
    public static final String KEY_RECORD_LIFECYCLE_END_TIME = "key_record_lifecycle_end_time";
    /**
     * 最新的视频ID
     */
    public static final String KEY_LATEST_MEDIA_VIDEO_ID = "latest_media_videoid";
    /**
     * 双击进入详情页面
     */
    public static final String IS_GIVE_ALBUM_COIN = "is_give_album_coin";
    /**
     * 循环播放的用户ID 或是 视频ID
     */
    public static final String KEY_PLAYLIST_LOOP_ID_AND_TYPE = "key_playlist_id_and_type";

    /**
     * 最新的预览图路径
     */
    public static final String KEY_LATEST_VIDEO_PREVIEW_URL = "latest_preview_url";
    /**
     * 订阅列表是否发生改变
     */
    public static final String IS_SUBSCRIBE_LIST_CHANGED = "is_subscribe_list_changed";
    /**
     * 订阅升级为下载高清视频 弹框提示
     */
    public static final String VIDEO_DOWN_HD_DIALOG = "video_down_hd_dialog";
    /**
     * 91桌面主题应用
     */
    public static final String VIDEO_THEME_APPLY = "video_theme_apply";
    /**
     * 循环播放
     */
    public static final String VIDEO_CIRCLE_PLAY = "video_circle_play";

    /**
     * 每日桌面视频播放量
     */
    public static final String VIDEOPLAY_BF_LAUNCHER = "videoplay_bf_launcher";
    /**
     * 每日视频详情视频播放量
     */
    public static final String VIDEOPLAY_BF_DETAIL = "videoplay_bf_Detail";
    /**
     * 首页发现页的当前页码
     */
    public static final String KEY_LATEST_NOVEL_PAGE_INDEX = "key_latest_novel_page_index";
    /**
     * 首页热门页的当前页码
     */
    public static final String KEY_LATEST_HOT_PAGE_INDEX = "key_latest_hot_page_index";
    /**
     * 是否开启双击功能
     */
    public static final String KEY_DOUBLE_CLICK_ENABLE = "key_double_click_enable";
    /**
     * 是否已经上报设置成功数据
     */
    public static final String KEY_HAS_REPORT_SET_SUCCESS = "key_has_report_set_success";

    /**
     * 当前播放列表排序方式
     */
    public static final String KEY_PLAYLIST_PLAY_SEQUENCE_TYPE = "key_playlist_play_sequence_type";
    public static final int PLAY_SEQUENCE_TYPE_NEWEST = 1;
    public static final int PLAY_SEQUENCE_TYPE_RAMDOM = 2;
    public static final int PLAY_SEQUENCE_TYPE_LOOP_VIDEO = 3;
    public static final int PLAY_SEQUENCE_TYPE_LOOP_USER = 4;
    /**
     * wifi自动下载和播放
     */
    public static final String IS_WIFI_AUTO_ENABLE = "is_wifi_auto_enable";
    /**
     * 是否执行过上滑操作
     */
    public static final String IS_SHOW_DRAGGING_VIEW = "is_show_dragging_view";
    /**
     * 是否执行过双击操作
     */
    public static final String IS_SHOW_DOUBLE_VIEW = "is_show_double_view";
    /**
     * 设置壁纸弹框 10天后显示的当时的时间
     */
    public static final String VIDEO_WALLPAPER_DIALOG_SHOW_TIME = "video_wallpaper_dialog_show_time";
    /**
     * 当前已经提示设置微视频壁纸次数
     */
    public static final String KEY_SET_VIDEO_WALLPAPER_COUNT = "key_set_video_wallpaper_count";
    /**
     * 最大提示设置微视频壁纸次数
     */
    public static final String KEY_MAX_VIDEO_WALLPAPER_SET_COUNT = "key_max_video_wallpaper_set_count";
    /**
     * 设置静态壁纸时的来源
     */
    public static final String KEY_SET_STATIC_WALLPAPER_FROM_TYPE = "key_set_static_wallpaper_from_type";
    /**
     * @date 2018.07.30
     * @issue 修复v2.5版本，误将原来数据库名称viewpager.db改为videopaper.db，导致升级用户丢失旧版本已下载的内容且升级后产生SubscribeUser表找不到问题
     * @fixed 将数据库名称还原为viewpager.db，且数据库版本升级一个版本号2，由此产生的灰度期间下载数据丢失的，将以数据拷贝方式处理
     * @return
     */
    public static final String DB_FIX_DIRTY = "db_fix_dirty";
    /**
     * 第一次启动时间
     */
    private static final String KEY_FIRST_LAUNCH_TIME = "first_launch_time";
    /**
     * 插件第一次启动时间
     */
    private static final String KEY_PLUGIN_FIRST_LAUNCH_TIME = "plugin_first_launch_time";
    //用户第一次安装的版本
    private static final String KEY_VERSION_FROM = "is_resident";
    //是否已经弹过，拍摄按钮的引导
    private static final String KEY_HAVE_SHOW_GUIDE = "key_have_show_guide";
    /**
     * 动态插件升级配置版本号(视频壁纸3.1使用)
     */
    private static final String KEY_PLUGIN_UPGRADE_CONFIG_VERSION_2318 = "key_plugin_upgrade_version_2318";
    /**
     * 默认关闭插件推荐下载app功能
     */
    public static final int ENABLE_PLUGIN_RECOMMEND_VIDEOPAPER_DEFAULT = 0;
    public static final String KEY_ENABLE_PLUGIN_RECOMMEND_VIDEOPAPER = "key_enable_plugin_recommend_videopaper";

    private static BaseConfigPreferences baseConfig;
    private static SharedPreferences baseSP;

    protected BaseConfigPreferences(Context context) {
        baseSP = context.getSharedPreferences(NAME, Context.MODE_PRIVATE | 4);
    }

    public static BaseConfigPreferences getInstance(Context context) {
        if (null == baseConfig) {
            baseConfig = new BaseConfigPreferences(context);
        }
        return baseConfig;
    }

    public static Long getVideoWallpaperDialogShowTime() {
        return baseSP.getLong(VIDEO_WALLPAPER_DIALOG_SHOW_TIME, 0);
    }

    public static void setVideoWallpaperDialogShowTime(long strCount) {
        baseSP.edit().putLong(VIDEO_WALLPAPER_DIALOG_SHOW_TIME, strCount).apply();
    }

    public SharedPreferences getBaseSP() {
        return baseSP;
    }

    /**
     * 获取第一次使用的时间
     *
     * @return 时间
     */
    public long getFirstLaunchTime() {
        long current = System.currentTimeMillis();
        long time = baseSP.getLong(KEY_FIRST_LAUNCH_TIME, current);
        if (current == time) {
            setFirstLaunchTime(current);
        }
        return time;
    }

    /**
     * 设置第一次使用的时间
     *
     * @param time 时间
     */
    public void setFirstLaunchTime(long time) {
        baseSP.edit().putLong(KEY_FIRST_LAUNCH_TIME, time).commit();
    }

    /**
     * 获取插件第一次使用的时间
     *
     * @return 时间
     */
    public long getFirstPluginLaunchTime() {
        long time = baseSP.getLong(KEY_PLUGIN_FIRST_LAUNCH_TIME, 0);
        return time;
    }

    /**
     * 设置插件第一次使用的时间
     *
     * @param time 时间
     */
    public void setFirstPluginLaunchTime(long time) {
        baseSP.edit().putLong(KEY_PLUGIN_FIRST_LAUNCH_TIME, time).commit();
    }

    /**
     * 某个版本是否已被启动过
     *
     * @param currentVer 版本
     * @return true表示已被启动过
     */
    public boolean isVersionShowed(String currentVer) {
        return baseSP.getBoolean(currentVer, false);
    }

    /**
     * 设置某个版本是否被启动过
     *
     * @param version 版本
     * @param showed  true已启动过/false还未启动过
     */
    public void setVersionShowed(String version, boolean showed) {
        baseSP.edit().putBoolean(version, showed).commit();
    }

    /**
     * 获取某个版本是否被启动过
     *
     * @param versionCode 版本号
     */
    public boolean isVersionCodeShowed(int versionCode) {
        return baseSP.getBoolean("#" + String.valueOf(versionCode), false);
    }

    /**
     * 设置某个版本是否被启动过
     *
     * @param versionCode 版本号
     * @param showed      true已启动过/false还未启动过
     */
    public void setVersionCodeShowed(int versionCode, boolean showed) {
        baseSP.edit().putBoolean("#" + String.valueOf(versionCode), showed).commit();
    }

    /**
     * 获取新增用户时记录的版本号
     *
     * @return
     * @Title: getVersionCodeForResident
     * @author lytjackson@gmail.com
     * @date 2014-3-31
     */
    public int getVersionCodeFrom() {
        return baseSP.getInt(KEY_VERSION_FROM, -1);
    }

    /**
     * 新增时记录版本号
     *
     * @param versionCode
     * @Title: setVersionCodeForResident
     * @author lytjackson@gmail.com
     * @date 2014-3-31
     */
    public void setVersionCodeFrom(int versionCode) {
        int vcFrom = baseSP.getInt(KEY_VERSION_FROM, 0);
        if (vcFrom <= 0) {
            baseSP.edit().putInt(KEY_VERSION_FROM, versionCode).commit();
        }
    }

    /**
     * 是否已经显示过引导
     */
    public boolean getHaveShowGuide() {
        return baseSP.getBoolean(KEY_HAVE_SHOW_GUIDE, false);
    }

    public void setHaveShowGuide(boolean have) {
        baseSP.edit().putBoolean(KEY_HAVE_SHOW_GUIDE, have).commit();
    }

    /**
     * 系统音量是否改变
     *
     * @return
     */
    public boolean isSystemVolumeChanged() {
        return baseSP.getBoolean(IS_SYSTEM_VOLUME_CHANGE, false);
    }

    /**
     * 设置系统音量已经改变
     *
     * @param value
     */
    public void setSystemVolumeChanged(boolean value) {
        baseSP.edit().putBoolean(IS_SYSTEM_VOLUME_CHANGE, value).commit();
    }

    /**
     * 视频壁纸音乐是否开启
     *
     * @return
     */
    public boolean isVideoWallpaperVolumnEnable() {
        return baseSP.getBoolean(IS_VIDEO_WALLPAPER_VOLUMN_ENABLE, true);
    }

    /**
     * 设置视频壁纸音乐是否开启
     *
     * @param value
     */
    public void setVideoWallpaperVolumnEnable(boolean value) {
        baseSP.edit().putBoolean(IS_VIDEO_WALLPAPER_VOLUMN_ENABLE, value).commit();
    }

    /**
     * 视频壁纸预览音乐是否开启
     *
     * @return
     */
    public boolean isVideoPreviewVolumnEnable() {
        return baseSP.getBoolean(IS_VIDEO_WALLPAPER_VOLUMN_ENABLE, false);
    }

    /**
     * 设置视频壁纸预览音乐是否开启
     *
     * @param value
     */
    public void setVideoPreviewVolumnEnable(boolean value) {
        baseSP.edit().putBoolean(IS_VIDEO_WALLPAPER_VOLUMN_ENABLE, value).commit();
    }

    public boolean isSettingsVolumnEnable() {
        return baseSP.getBoolean(IS_SETTINGS_VOLUMN_ENABLE, false);
    }

    /**
     * 设置页面声音是否开启
     */
    public void setSettingsVolumnEnable(boolean value) {
        baseSP.edit().putBoolean(IS_SETTINGS_VOLUMN_ENABLE, value).apply();
    }

    /**
     * 是否是第一次进入裁剪页面
     *
     * @return
     */
    public boolean isFirstVideoClip() {
        return baseSP.getBoolean(IS_FIRST_VIDEO_CLIP, true);
    }

    public void setIsFirstVideoClip(boolean value) {
        baseSP.edit().putBoolean(IS_FIRST_VIDEO_CLIP, value).commit();
    }

    /**
     * 获取动态插件升级配置版本号
     *
     * @return
     */
    public int getPluginUpgradeConfigVersion() {
        return baseSP.getInt(KEY_PLUGIN_UPGRADE_CONFIG_VERSION_2318, 1);
    }

    /**
     * 设置动态插件升级配置版本号
     *
     * @param version
     */
    public void setPluginUpgradeConfigVersion(int version) {
        baseSP.edit().putInt(KEY_PLUGIN_UPGRADE_CONFIG_VERSION_2318, version).commit();
    }

    /**
     * 获取91桌面传过来的静态壁纸的路径
     */
    public boolean getStaticWallpaperShowToast() {
        return baseSP.getBoolean(STATIC_WALLPAPER_SHOW_TOAST, true);
    }

    /**
     * 设置91桌面传过来的静态壁纸的路径
     */
    public void setStaticWallpaperShowToast(boolean show) {
        baseSP.edit().putBoolean(STATIC_WALLPAPER_SHOW_TOAST, show).commit();
    }

    /**
     * 获取91桌面传过来的静态壁纸的路径
     */
    public String getStaticWallpaperPath() {
        return baseSP.getString(STATIC_WALLPAPER_PATH, "");
    }

    /**
     * 设置91桌面传过来的静态壁纸的路径
     */
    public void setStaticWallpaperPath(String path) {
        baseSP.edit().putString(STATIC_WALLPAPER_PATH, path).commit();
    }

    /**
     * 获取待替换视频文件地址
     *
     * @return
     */
    public String getPendingMediaPath() {
        return baseSP.getString(KEY_PENDING_MEDIA_PATH, null);
    }

    /**
     * 设置待替换视频文件本地地址
     *
     * @param path
     */
    public void setPendingMediaPath(String path) {
        baseSP.edit().putString(KEY_PENDING_MEDIA_PATH, path).commit();
    }

    /**
     * 获取最新视频文件本地址
     *
     * @return
     */
    public String getLatestMediaPath() {
        return baseSP.getString(KEY_LATEST_MEDIA_PATH, null);
    }

    /**
     * 设置最新的视频文件本地地址
     *
     * @param path
     */
    public void setLatestMediaPath(String path) {
        baseSP.edit().putString(KEY_LATEST_MEDIA_PATH, path).commit();
        setStaticWallpaperPath("");
    }

    /**
     * 获取当前应用的天天视频地址ID
     *
     * @return
     */
    public String getCurrentSeriesIdAndIdentifier() {
        return baseSP.getString(KEY_CURRENT_SERIES_ID_AND_IDENTIFIER, null);
    }

    /**
     * 设置当前应用的天天视频地址ID
     *
     * @param seriesIdAndIdentifier
     */
    public void setCurrentSeriesIdAndIdentifier(String seriesIdAndIdentifier) {
        baseSP.edit().putString(KEY_CURRENT_SERIES_ID_AND_IDENTIFIER, seriesIdAndIdentifier).commit();
    }

    /**
     * 获取当前应用的天天视频壁纸时间间隔(0表示开屏切换)
     *
     * @return
     */
    public long getCurrentSeriesInterval() {
        return baseSP.getLong(KEY_CURRENT_SERIES_INTERVAL, 0);
    }

    /**
     * 获取上一套视频壁纸应用时间
     *
     * @return
     */
    public long getLastVideoApplyTime() {
        return baseSP.getLong(KEY_LAST_VIDEO_APPLY_TIME, 0);
    }

    /**
     * 设置上一套视频壁纸应用时间
     *
     * @param time
     */
    public void setLastVideoApplyTime(long time) {
        baseSP.edit().putLong(KEY_LAST_VIDEO_APPLY_TIME, time).commit();
    }

    /**
     * 第一次双击打开广告
     *
     * @return
     */
    public boolean isFirstScreenOnAds() {
        return baseSP.getBoolean(AD_SCREEN_ON_ADS_FIRST, true);
    }

    /**
     * 打开app多个广告的播放位置
     *
     * @return
     */
    public int getOpenAlbumTime() {
        return baseSP.getInt(ON_TIME_OPEN_ALBUM, 0);
    }

    public void setOpenAlbumTime(int time) {
        baseSP.edit().putInt(ON_TIME_OPEN_ALBUM, time).commit();
    }

    public void getFirstScreenOnAds(boolean isFirst) {
        baseSP.edit().putBoolean(AD_SCREEN_ON_ADS_FIRST, isFirst).commit();
    }

    public long getRecordLifecycleStartTime() {
        return baseSP.getLong(KEY_RECORD_LIFECYCLE_START_TIME, 0);
    }

    public void setRecordLifecycleStartTime(long time) {
        baseSP.edit().putLong(KEY_RECORD_LIFECYCLE_START_TIME, time).commit();
    }

    public void setRecordLifecycleEndTime(long time) {
        baseSP.edit().putLong(KEY_RECORD_LIFECYCLE_END_TIME, time).commit();
    }

    public String getLatestMediaVideoId() {
        return baseSP.getString(KEY_LATEST_MEDIA_VIDEO_ID, null);
    }

    /**
     * 获取和设置最后播放的视频ID
     *
     * @return
     */
    public void setLatestMediaVideoId(String videoId) {
        baseSP.edit().putString(KEY_LATEST_MEDIA_VIDEO_ID, videoId).commit();
    }

    /**
     * 相册是否已经贡献积分
     * @return
     */
    public boolean isGiveAlbumCoin() {
        return baseSP.getBoolean(IS_GIVE_ALBUM_COIN, false);
    }

    /**
     * 相册是否已经贡献积分
     *
     * @param value
     */
    public void setGiveAlbumCoin(boolean value) {
        baseSP.edit().putBoolean(IS_GIVE_ALBUM_COIN, value).commit();
    }


    public String getLoopPlayId() {
        return baseSP.getString(KEY_PLAYLIST_LOOP_ID_AND_TYPE, "");
    }

    /**
     * @desc 订阅视频播放-循环播放ID
     * @author linliangbin
     * @time 2017/6/27 16:30
     */

    public void setLoopPlayId(String id) {
        baseSP.edit().putString(KEY_PLAYLIST_LOOP_ID_AND_TYPE, id).commit();
    }

    public void clearLoopPlayId() {
        setLoopPlayId("");
    }

    /**
     * 设置最新的视频预览图地址
     *
     * @return
     */
    public String getPendingPreviewImgPath() {
        return baseSP.getString(KEY_LATEST_VIDEO_PREVIEW_URL, null);
    }

    /**
     * 设置最新的视频预览图地址
     *
     * @param path
     */
    public void setPendingPreviewImgPath(String path) {
        baseSP.edit().putString(KEY_LATEST_VIDEO_PREVIEW_URL, path).commit();
    }

    /**
     * 订阅列表是否发生改变
     *
     * @return
     */
    public boolean isSubscribeListChanged() {
        return baseSP.getBoolean(IS_SUBSCRIBE_LIST_CHANGED, true);
    }

    public void setSubscribeListChanged(boolean value) {
        baseSP.edit().putBoolean(IS_SUBSCRIBE_LIST_CHANGED, value).commit();
    }

    /**
     * 订阅升级为下载高清视频 弹框提示
     */
    public boolean showHdDownTip() {
        return baseSP.getBoolean(VIDEO_DOWN_HD_DIALOG, false);
    }

    public void setShowHdDownTip(boolean value) {
        baseSP.edit().putBoolean(VIDEO_DOWN_HD_DIALOG, value).commit();
    }

    /**
     * 是否91桌面视频主题应用
     */
    public boolean getVideoThemeApply() {
        return baseSP.getBoolean(VIDEO_THEME_APPLY, false);
    }

    public void setVideoThemeApply(boolean value) {
        baseSP.edit().putBoolean(VIDEO_THEME_APPLY, value).commit();
    }

    /**
     * 是否开启循环播放
     */
    public boolean isOpenCirclePlay() {
        return baseSP.getBoolean(VIDEO_CIRCLE_PLAY, true);
    }

    public void setVideoCirclePlay(boolean value) {
        baseSP.edit().putBoolean(VIDEO_CIRCLE_PLAY, value).commit();
    }

    public int getPlaySquenceType() {
        return baseSP.getInt(KEY_PLAYLIST_PLAY_SEQUENCE_TYPE, PLAY_SEQUENCE_TYPE_NEWEST);
    }

    /**
     * @desc 订阅视频播放顺序
     * @author linliangbin
     * @time 2017/6/27 16:30
     */
    public void setPlaySquenceType(int type) {
        baseSP.edit().putInt(KEY_PLAYLIST_PLAY_SEQUENCE_TYPE, type).commit();
    }

    public boolean isLoopPlaySquenceType() {
        return getPlaySquenceType() == PLAY_SEQUENCE_TYPE_LOOP_USER || getPlaySquenceType() == PLAY_SEQUENCE_TYPE_LOOP_VIDEO;
    }

    /**
     * 获取每日桌面视频播放量
     */
    public int getDailyVideoPlay_Launcher() {
        return baseSP.getInt(VIDEOPLAY_BF_LAUNCHER, 0);
    }

    /**
     * 设置每日桌面视频播放量
     */
    public void setDailyVideoPlay_Launcher(int time) {
        baseSP.edit().putInt(VIDEOPLAY_BF_LAUNCHER, time).commit();
    }

    /**
     * 获取每日桌面视频播放量
     */
    public int getDailyVideoPlay_Detail() {
        return baseSP.getInt(VIDEOPLAY_BF_DETAIL, 0);
    }

    /**
     * 设置每日桌面视频播放量
     */
    public void setDailyVideoPlay_Detail(int time) {
        baseSP.edit().putInt(VIDEOPLAY_BF_DETAIL, time).commit();
    }

    /**
     * 获取首页发现页的请求页码
     *
     * @return
     */
    public int getLatestNovelPageIndex() {
        return baseSP.getInt(KEY_LATEST_NOVEL_PAGE_INDEX, 0);
    }

    /**
     * 首页发现面的请求页码
     *
     * @param pageIndex
     */
    public void setLatestNovelPageIndex(int pageIndex) {
        baseSP.edit().putInt(KEY_LATEST_NOVEL_PAGE_INDEX, pageIndex).commit();
    }

    /**
     * 获取首页热门页的请求页码
     *
     * @return
     */
    public int getLatestHotPageIndex() {
        return baseSP.getInt(KEY_LATEST_HOT_PAGE_INDEX, 0);
    }

    /**
     * 首页热门面的请求页码
     *
     * @param pageIndex
     */
    public void setLatestHotPageIndex(int pageIndex) {
        baseSP.edit().putInt(KEY_LATEST_HOT_PAGE_INDEX, pageIndex).commit();
    }

    /**
     * wifi下是否自动下载和播放
     *
     * @return
     */
    public boolean isWifiAutoEnable() {
        return baseSP.getBoolean(IS_WIFI_AUTO_ENABLE, true);
    }

    /**
     * wifi下是否自动下载和播放
     *
     * @param value
     */
    public void setWifiAutoEnable(boolean value) {
        baseSP.edit().putBoolean(IS_WIFI_AUTO_ENABLE, value).commit();
    }

    /**
     * 是否已经上报过设置成功数据
     *
     * @return
     */
    public boolean hasReportSetSuccess() {
        return baseSP.getBoolean(KEY_HAS_REPORT_SET_SUCCESS, false);
    }

    public void setHasReportSetSuccess(boolean value) {
        baseSP.edit().putBoolean(KEY_HAS_REPORT_SET_SUCCESS, value).commit();
    }

    public boolean isShowDraggingView() {
        return baseSP.getBoolean(IS_SHOW_DRAGGING_VIEW, false);
    }

    public void setIsShowDraggingView(boolean value) {
        baseSP.edit().putBoolean(IS_SHOW_DRAGGING_VIEW, value).commit();
    }

    public boolean isShowDoubleView() {
        return baseSP.getBoolean(IS_SHOW_DOUBLE_VIEW, false);
    }

    public void setIsShowDoubleView(boolean value) {
        baseSP.edit().putBoolean(IS_SHOW_DOUBLE_VIEW, value).commit();
    }

    public void commitFixedDirtyDB() {
        baseSP.edit().putBoolean(DB_FIX_DIRTY, true);
    }

    public boolean hasFixedDirtyDB() {
        return baseSP.getBoolean(DB_FIX_DIRTY, false);
    }


    /**
     * @desc 获取和设置提示设置视频壁纸服务次数
     * @author linliangbin
     * @time 2018/8/7 9:45
     */
    public int getVideoWallpaperSetCount() {
        return baseSP.getInt(KEY_SET_VIDEO_WALLPAPER_COUNT, 0);
    }

    public void setVideoWallpaperSetCount(int count) {
        baseSP.edit().putInt(KEY_SET_VIDEO_WALLPAPER_COUNT, count).commit();
    }

    public void increaseVideoWallpaperSetCount() {
        setVideoWallpaperSetCount(getVideoWallpaperSetCount() + 1);
    }

    public int getMaxVideoWallpaperSetCount() {
        return baseSP.getInt(KEY_MAX_VIDEO_WALLPAPER_SET_COUNT, 0);
    }

    public void setMaxVideoWallpaperSetCount(int count) {
        baseSP.edit().putInt(KEY_MAX_VIDEO_WALLPAPER_SET_COUNT, count).commit();
    }

    public int getSetStaticWallpaperFromType() {
        return baseSP.getInt(KEY_SET_STATIC_WALLPAPER_FROM_TYPE, 0);
    }

    public void setSetStaticWallpaperFromType(int fromType) {
        baseSP.edit().putInt(KEY_SET_STATIC_WALLPAPER_FROM_TYPE, fromType).commit();
    }

    /**
     * 是否开启插件推荐APP下载功能
     * @param enable
     */
    public void setEnablePluginRecommendVideopaper(int enable){
        baseSP.edit().putInt(KEY_ENABLE_PLUGIN_RECOMMEND_VIDEOPAPER, enable).commit();
    }

    public boolean getEnablePluginRecommendVideopaper(){
        return baseSP.getInt(KEY_ENABLE_PLUGIN_RECOMMEND_VIDEOPAPER,ENABLE_PLUGIN_RECOMMEND_VIDEOPAPER_DEFAULT) ==1;
    }

}