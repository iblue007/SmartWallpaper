package com.xxm.smartwallpaper.bean;

import java.io.Serializable;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月17日 13:22.</br>
 * @update: </br>
 */

public class VideoPapaerBase implements Serializable {

    /**
     * 单个视频类型
     */
    public static final int RES_TYPE_UNIT = 3;
    /**
     * 天天视频类型
     */
    public static final int RES_TYPE_SERIES = 2;
    /**
     * 明星专辑视频类型
     */
    public static final int RES_TYPE_STAR = 4;
    /**
     * 广告视频类型
     */
    public static final int RES_TYPE_AD = 10;


    public String resId;

    public String title;

    public String desc;

    public String thumbUri;

    public String identifier;

    public int resType;
}
