package com.xxm.smartwallpaper.bean;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月02日 18:41.</br>
 * @update: </br>
 */

public class VideoPaperBean extends VideoPapaerBase {

    public String md5;
    /**
     * 原始资源-宽
     */
    public int width;
    /**
     * 原始资源-高
     */
    public int height;
    /**
     * 预览视频地址
     */
    public String previewUri;
    /**
     * 文件大小
     */
    public long mp4Size;
    /**
     * 视频时长(毫秒为单位)
     */
    public long duration;
    /**
     * 是否有音频轨（是否有声音）
     */
    public boolean hasAudio;
    /**
     * 下载地址
     */
    public String downloadUri;
    /**
     * 壁纸下载地址
     */
    public String wallpaperUri;
    /**
     * 比特率
     */
    public long bitrate;
    /**
     *
     */
    public String mimeType;

    //------v1.1新增-----//
    /**
     * 点赞数
     */
    public long digNum;
    /**
     * 用户头像
     */
    public String authorIcon;
    /**
     * 用户昵称
     */
    public String authorNickName;
    /**
     * 用户ID
     */
    public long authorId;
    /**
     * 用户性别
     */
    public int authorSex;

    public long scanNum;

    //------本地视频信息-------//
    /**
     * 专辑ID
     */
    public long bucketId;
    /**
     * 专辑名称
     */
    public String bucketName;

    public String bucketThumb;

    public int bucketKidsCount;
    public int IsTopic ;//是否是话题
    public String HotNumber ;//热度
    public int isOriginal ;//是否原创，1=是，0=不是

    @Override
    public String toString() {
        return "VideoPaperBean{" +
                "authorIcon='" + authorIcon + '\'' +
                ", md5='" + md5 + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", previewUri='" + previewUri + '\'' +
                ", mp4Size=" + mp4Size +
                ", duration=" + duration +
                ", hasAudio=" + hasAudio +
                ", downloadUri='" + downloadUri + '\'' +
                ", wallpaperUri='" + wallpaperUri + '\'' +
                ", bitrate=" + bitrate +
                ", mimeType='" + mimeType + '\'' +
                ", digNum=" + digNum +
                ", authorNickName='" + authorNickName + '\'' +
                ", authorId=" + authorId +
                ", authorSex=" + authorSex +
                ", bucketId=" + bucketId +
                ", bucketName='" + bucketName + '\'' +
                ", bucketThumb='" + bucketThumb + '\'' +
                ", bucketKidsCount=" + bucketKidsCount +
                '}';
    }
}
