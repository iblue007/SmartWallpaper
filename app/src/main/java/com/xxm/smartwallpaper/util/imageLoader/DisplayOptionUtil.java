package com.xxm.smartwallpaper.util.imageLoader;

import android.graphics.Bitmap;

import com.lling.photopicker.utils.Global;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.util.ScreenUtil;


/**
 * Created by dingdongjin_dian91 on 2016/2/19.
 */
public class DisplayOptionUtil {

    public static final DisplayImageOptions DEFAULT_OPTIONS = new DisplayImageOptions.Builder()
            .cacheInMemory(true)
            .cacheOnDisk(true)
            .build();

    public static final DisplayImageOptions VIDEO_UNIT_ITEM_OPTIONS = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnLoading(R.drawable.ic_loading_placehold_small)
            .showImageForEmptyUri(R.drawable.ic_loading_placehold_small)
            .showImageOnFail(R.drawable.ic_loading_placehold_small)
            .considerExifParams(true)
            .build();

    public static final DisplayImageOptions VIDEO_UNIT_ITEM_SMALL_OPTIONS = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnLoading(R.drawable.ic_loading_placehold_small)
            .showImageForEmptyUri(R.drawable.ic_loading_placehold_small)
            .showImageOnFail(R.drawable.ic_loading_placehold_small)
            .considerExifParams(true)
            .build();

    public static final DisplayImageOptions VIDEO_ROUNDED_OPTIONS = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnLoading(R.drawable.ic_loading_placehold_small)
            .showImageForEmptyUri(R.drawable.ic_loading_placehold_small)
            .showImageOnFail(R.drawable.ic_loading_placehold_small)
            .displayer(new RoundedBitmapDisplayer(ScreenUtil.dip2px(Global.getContext(), 3), 0))
            .considerExifParams(true)
            .build();


    public static final DisplayImageOptions VIDEO_ROUND_ICON_OPTIONS = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.ARGB_8888)
            .cacheOnDisk(true)
            .cacheInMemory(true)
            .showImageOnLoading(R.mipmap.ic_default_user_icon)
            .showImageForEmptyUri(R.mipmap.ic_default_user_icon)
            .showImageOnFail(R.mipmap.ic_default_user_icon)
            .displayer(new RoundedBitmapDisplayer(ScreenUtil.dip2px(Global.getContext(), 1000), 0))
            .considerExifParams(true)
            .build();


}
