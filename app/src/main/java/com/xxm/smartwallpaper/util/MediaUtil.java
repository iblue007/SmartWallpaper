package com.xxm.smartwallpaper.util;

import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;
import android.os.Build;
import android.view.TextureView;

import com.xxm.smartwallpaper.bean.VideoPaperBean;

import java.io.File;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年05月09日 13:49.</br>
 * @update: </br>
 */

public class MediaUtil {

    private static MediaMetadataRetriever mmr = new MediaMetadataRetriever();

    public static int getMediaWidth(String src) {
        String width = getValue(src, MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);

        try {
            return Integer.valueOf(width);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getMediaHeight(String src) {
        String height = getValue(src, MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);

        try {
            return Integer.valueOf(height);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static int getMediaDuration(String src) {
        String duration = getValue(src, MediaMetadataRetriever.METADATA_KEY_DURATION);

        try {
            return Integer.valueOf(duration);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static boolean isVideoAvailable(String src) {
        try {
            String hasVideo = getValue(src, MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);

            if ("yes".equalsIgnoreCase(hasVideo)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static String getValue(String src, int keyCode) {
        MediaMetadataRetriever mmr = getMetadataRetriever(src);
        return mmr.extractMetadata(keyCode);
    }

    private static MediaMetadataRetriever getMetadataRetriever(String src) {
        final String str = src;
        mmr.setDataSource(str);
        return mmr;
    }

    public static VideoPaperBean video2Bean(String src) {
        File file = new File(src);
        MediaMetadataRetriever retriever = getMetadataRetriever(src);

        String hasVideo = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_VIDEO);
        if (!"yes".equals(hasVideo)) {
            return null;
        }

        VideoPaperBean bean = new VideoPaperBean();
        bean.title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
        bean.resId = "-1";
        try {
            String width = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            bean.width = Integer.valueOf(width);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            String height = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            bean.height = Integer.valueOf(height);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bean.mp4Size = file.length();

        try {
            String duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            bean.duration = Long.valueOf(duration);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bean.previewUri = file.getAbsolutePath();

        String hasAudio = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_HAS_AUDIO);
        bean.hasAudio = "yes".equals(hasAudio);

        String bitrate = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE);
        try {
            bean.bitrate = Long.valueOf(bitrate);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT >= 17) {
            try {
                String rotation = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION);
                int degress = Integer.valueOf(rotation);
                if ((degress / 90) % 2 == 1) {//0、180、360
                    int temp = bean.width;
                    bean.width = bean.height;
                    bean.height = temp;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String mimeType = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_MIMETYPE);
        bean.mimeType = mimeType;
        return bean;
    }

    /**
     * scale type like centerinside
     *
     * @param videoWidth
     * @param videoHeight
     */
    public static void transformVideo(TextureView videoSurface, int videoWidth, int videoHeight) {
        if (videoSurface.getHeight() == 0 || videoSurface.getWidth() == 0) {
            return;
        }
        float sx = (float) videoSurface.getWidth() / (float) videoWidth;
        float sy = (float) videoSurface.getHeight() / (float) videoHeight;

        float minScale = Math.min(sx, sy);
        Matrix matrix = new Matrix();

        //1、先平移到最终缩放后的原点位置
        matrix.preTranslate((videoSurface.getWidth() - videoWidth * minScale) / 2, (videoSurface.getHeight() - videoHeight * minScale) / 2);
        //2、因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来
        matrix.preScale(videoWidth / (float) videoSurface.getWidth(), videoHeight / (float) videoSurface.getHeight());
        //3、以centerinside的方法缩放
        matrix.preScale(minScale, minScale);

        videoSurface.setTransform(matrix);
        videoSurface.postInvalidate();
    }
}
