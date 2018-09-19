package com.xxm.smartwallpaper.loader;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.xxm.smartwallpaper.bean.VideoPaperBean;
import com.xxm.smartwallpaper.util.FileUtil;
import com.xxm.smartwallpaper.util.MediaUtil;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 本地数据加载器</br>
 * @author: cxy </br>
 * @date: 2017年05月02日 18:40.</br>
 * @update: </br>
 */

public class NativeHelper {

//    private static NativeUnit sUnitTool = new NativeUnit();
//    private static NativeSeries sSeriesTool = new NativeSeries();

    /**
     * 保存到本地
     *
     * @param bean
     */
//    public static boolean save(VideoPaperBean bean) {
//        return sUnitTool.save(bean);
//    }

//    public static boolean save(VideoPaperSeriesBean seriesBean) {
//        return sSeriesTool.save(seriesBean);
//    }

    /**
     * 删除本地数据
     *
     * @param vid
     * @param identifier
     */
//    public static boolean delete(String vid, String identifier, boolean isSeries) {
//        if (isSeries) {
//            return sSeriesTool.delete(vid, identifier);
//        }
//        return sUnitTool.delete(vid, identifier);
//    }

    /**
     * 本地视频文件是否存在
     * <p>注：这里仅仅只是判断视频文件是否存在，并不对资源完整性、配置完整性进行判断</p>
     * <p>需要同时进行资源完整性、配置完整性判断的，参见：{@link #hasNativeVideoStrict(String, String)}</p>
     *
     * @param vid
     * @param identifier
     * @return
     */
//    public static boolean hasNativeVideo(String vid, String identifier) {
//        return sUnitTool.verifyResourceWeak(vid, identifier);
//    }
//
//    public static boolean hasNativeVideo(String seriesId, String vid, String identifier) {
//        return sSeriesTool.verifySubResourceWeak(seriesId, vid, identifier);
//    }
//
//    public static boolean hasNativeVideoStrict(String vid, String identifier) {
//        return sUnitTool.verifyResourceStrong(vid, identifier);
//    }
//
//    public static boolean hasNativeVideoStrict(String seriesId, String vid, String identifier) {
//        return sSeriesTool.verifySubResourceStrong(seriesId, vid, identifier);
//    }
//
//    public static boolean hasNativeSubInfoStrict(String seriesId, String vid, String identifier) {
//        return sSeriesTool.verifySubStrong(seriesId, vid, identifier);
//    }

    /**
     * 获取单元配置文件路径(MD5--vid_identifier)
     *
     * @param vid
     * @param identifier
     * @return
     */
//    public static String getConfigPath(String vid, String identifier, boolean isSeries) {
//        return isSeries ? sSeriesTool.getConfigPath(vid, identifier) : sUnitTool.getConfigPath(vid, identifier);
//    }
//
//    /**
//     * 获取资源文件路径(vid_identifier)
//     *
//     * @param vid
//     * @param identifier
//     * @return
//     */
//    public static String getResourcePath(String vid, String identifier) {
//        return sUnitTool.getResourcePath(vid, identifier);
//    }

//    public static String getResourcePath(String seriesId, String vid, String identifier) {
//        return sSeriesTool.getSubResourcePath(seriesId, vid, identifier);
//    }
//
//    public static String getResourceName(String seriesId, String vid, String identifier) {
//        return sSeriesTool.getSubResourceName(seriesId, vid, identifier);
//    }
//
//    public static String getResourceName(String vid, String identifier) {
//        return sUnitTool.getResourceName(vid, identifier);
//    }

    /**
     * 获取资源壁纸文件路径(wallpaper_vid_identifier)
     *
     * @param vid
     * @param identifier
     * @return
     */
//    public static String getWallpaperPath(String vid, String identifier) {
//        return sUnitTool.getWallpaperPath(vid, identifier);
//    }

//    public static String getWallpaperPath(String seriesId, String vid, String identifier) {
//        return sSeriesTool.getSubWallpaperPath(seriesId, vid, identifier);
//    }
//
//    public static String getWallpaperName(String vid, String identifier) {
//        return sUnitTool.getWallpaperName(vid, identifier);
//    }
//
//    public static String getWallpaperName(String seriesId, String vid, String identifier) {
//        return sSeriesTool.getSubWallpaperName(seriesId, vid, identifier);
//    }
//
//    /**
//     * 视频单元配置文件所在目录
//     *
//     * @return
//     */
//    public static String getUnitConfigDir(boolean isSeries) {
//        return isSeries ? sSeriesTool.getConfigDir() : sUnitTool.getConfigDir();
//    }
//
//    /**
//     * 视频资源文件所在目录
//     *
//     * @return
//     */
//    public static String getResourceDir(boolean isSeries) {
//        return isSeries ? sSeriesTool.getResourceDir() : sUnitTool.getResourceDir();
//    }
//
//    /**
//     * 壁纸资源文件所在目录
//     *
//     * @return
//     */
//    public static String getWallpaperDir(boolean isSeries) {
//        return isSeries ? sSeriesTool.getWallpaperDir() : sUnitTool.getWallpaperDir();
//    }
//
//    /**
//     * 加载已下载的视频单元本地列表
//     *
//     * @param context
//     * @return
//     */
//    public static List<VideoPaperBean> queryUnitDownloaded(Context context) {
//        return sUnitTool.query(context, 0, 100);
//    }

    /**
     * 获取合辑列表
     *
     * @param context
     * @return
     */
    public static List<VideoPaperBean> queryBuckets(Context context, boolean needThumb, boolean needCount) {
        List<VideoPaperBean> list = null;
        Map<Long, String> mapping = new HashMap<Long, String>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media.BUCKET_DISPLAY_NAME, MediaStore.Video.Media.BUCKET_ID}, null, null, MediaStore.Video.Media.BUCKET_ID + " DESC");
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                list = new ArrayList<VideoPaperBean>();
                do {
                    String bucketName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_DISPLAY_NAME));
                    long bucketId = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.BUCKET_ID));
                    if (mapping.containsKey(bucketId)) {
                        continue;
                    }
                    mapping.put(bucketId, bucketName);

                    VideoPaperBean bean = new VideoPaperBean();
                    bean.bucketId = bucketId;
                    bean.bucketName = bucketName;
                    list.add(bean);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

        //获取合辑缩略图（合辑下的第一个视频的缩略图）
        if (needThumb && list != null && !list.isEmpty()) {
            for (int i = 0, len = list.size(); i < len; i++) {
                try {
                    VideoPaperBean bean = list.get(i);
                    cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                            new String[]{MediaStore.Video.Media.DATA, MediaStore.Video.Media._ID}, MediaStore.Video.Media.BUCKET_ID + " = ?", new String[]{bean.bucketId + ""}, MediaStore.Video.Media._ID + " DESC limit 1");

                    if (cursor.moveToFirst()) {
                        String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                        long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                        bean.bucketThumb = genVideoThumbnailUri(context, id, path);
                        cursor.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        List<VideoPaperBean> dirty = new ArrayList<VideoPaperBean>();
        //获取合辑下的视频总数
//        if (needCount) {
        for (VideoPaperBean bean : list) {
            try {
                cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                        new String[]{"count(*) as count"}, MediaStore.Video.Media.BUCKET_ID + " = ?", new String[]{bean.bucketId + ""}, null);

                if (cursor.moveToFirst()) {
                    bean.bucketKidsCount = cursor.getInt(cursor.getColumnIndexOrThrow("count"));
                    if (bean.bucketKidsCount < 1) {
                        dirty.add(bean);
                    }
                } else {
                    dirty.add(bean);
                }
                cursor.close();
            } catch (Exception e) {
                e.printStackTrace();
                dirty.add(bean);
            }
        }
//        }

        for (int i = 0, len = dirty.size(); i < len; i++) {
            VideoPaperBean bean = dirty.get(i);
            list.remove(bean);
        }
        return list;
    }

    public static List<VideoPaperBean> queryLocal(Context context, int pageIndex, int pageSize) {
        return queryLocal(context, pageIndex, pageSize, null, null, null);
    }

    public static List<VideoPaperBean> queryLocal(Context context, int pageIndex, int pageSize, String selection, String[] selectionArgs, String sortOrder) {
        List<VideoPaperBean> list = null;

        if (pageSize <= 0 || pageIndex < 0) {
            return list;
        }

        String tSelect = null;
        String[] tSelectArgs = null;
        if (!TextUtils.isEmpty(selection)) {
            tSelect = selection;
            tSelectArgs = selectionArgs;
        }

        String order = MediaStore.Video.Media._ID + " DESC";
        if (!TextUtils.isEmpty(sortOrder)) {
            order = sortOrder;
        }

        int page = ((pageIndex - 1) < 0 ? 0 : (pageIndex - 1));
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, tSelect, tSelectArgs, order + " limit " + (page * pageSize) + "," + pageSize);

        if (cursor != null) {
            list = new ArrayList<VideoPaperBean>();

            while (cursor.moveToNext()) {
                try {
                    String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE));
                    String path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));
                    long id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));
                    if (!FileUtil.isFileExits(path)) {
                        continue;
                    }

                    VideoPaperBean bean = MediaUtil.video2Bean(path);
                    if (bean == null /*|| bean.duration < 1000*/) {
                        continue;
                    }

                    bean.title = title;
//                    Log.d("cxydebug", "queryLocal: " + title);
//                    Log.d("cxydebug", "queryLocal: " + album);
//                    Log.d("cxydebug", "queryLocal: " + artist);
//                    Log.d("cxydebug", "queryLocal: " + displayName);
//                    Log.d("cxydebug", "queryLocal: " + mimeType);
//                    Log.d("cxydebug", "queryLocal: " + path);
//                    Log.d("cxydebug", "queryLocal: " + duration);
//                    Log.d("cxydebug", "queryLocal: " + size);
//                    Log.d("cxydebug", "queryLocal: " + width);
//                    Log.d("cxydebug", "queryLocal: " + height);
//                    Log.d("cxydebug", "queryLocal: -----\n\n");

                    bean.thumbUri = genVideoThumbnailUri(context, id, bean.previewUri);
                    list.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return list;
    }

    private static String genVideoThumbnailUri(Context context, long id, String videoPath) {
        String uri = null;
        try {
            String thumbName = FileUtil.getFileName(videoPath, false);
            //TODO:找个时间替换imageloader
            File thumbFile = new File(ImageLoader.getInstance().getDiskCache().getDirectory(), thumbName);
            uri = Uri.fromFile(thumbFile).toString();
//            BitmapFactory.Options options = new BitmapFactory.Options();
//            options.inSampleSize = 1;
            Bitmap thumb = ImageLoader.getInstance().loadImageSync(uri);
            if (thumb == null) {
//                thumb = MediaStore.Video.Thumbnails.getThumbnail(context.getContentResolver(), id, MediaStore.Video.Thumbnails.MICRO_KIND, options);
                thumb = ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND);
                ImageLoader.getInstance().getDiskCache().save(uri, thumb);
                ImageLoader.getInstance().getMemoryCache().put(uri, thumb);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return uri;
    }

    /**
     * 基于文件系统搜索本机视频
     *
     * @param context
     * @return
     */
    @Deprecated
    public static List<VideoPaperBean> queryLocalByFS(Context context) {
        List<VideoPaperBean> list = retrievedVideoFile(Environment.getExternalStorageDirectory().getAbsolutePath());
        return list;
    }

    private static List<VideoPaperBean> retrievedVideoFile(String src) {

        File base = new File(src);
        List<VideoPaperBean> list = new ArrayList<VideoPaperBean>();

        if (base.isFile()) {
            String name = base.getName().toLowerCase();
            if (name.endsWith(".mp4")) {
                VideoPaperBean bean = MediaUtil.video2Bean(src);
                if (base != null) {
                    list.add(bean);
                }
            }
        } else {
            File[] files = new File[0];
            try {
                files = base.listFiles(new FilenameFilter() {
                    @Override
                    public boolean accept(File dir, String name) {
                        try {
                            if (name.endsWith(".mp4")) {
                                return true;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return false;
                    }
                });
            } catch (Throwable e) {
                e.printStackTrace();
            }

            for (int i = 0, len = files.length; i < len; i++) {
                File file = files[i];
                List<VideoPaperBean> subList = retrievedVideoFile(file.getAbsolutePath());
                if (subList != null && !subList.isEmpty()) {
                    list.addAll(subList);
                }
            }
        }

        return list;
    }

}
