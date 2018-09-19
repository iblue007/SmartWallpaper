//package com.xxm.smartwallpaper.util;
//
//import android.content.Context;
//import android.os.Environment;
//
//import com.xxm.smartwallpaper.inteface.IVideoDiskCache;
//import com.xxm.smartwallpaper.manager.BaseConfig;
//
//import java.io.File;
//
///**
// * @Description: </br>
// * @author: cxy </br>
// * @date: 2017年06月23日 16:41.</br>
// * @update: </br>
// */
//
//public class VideoProxyConfiguration {
//
//    Context context;
//    long diskCacheSize;
//    int diskCacheFileCount;
//    String disckCacheDir;
//    IVideoDiskCache diskCache;
//    int proxyPort;
//
//    public VideoProxyConfiguration(Builder builder) {
//        this.context = builder.context;
//        this.disckCacheDir = builder.disckCacheDir;
//        this.diskCacheFileCount = builder.diskCacheFileCount;
//        this.diskCacheSize = builder.diskCacheSize;
//        this.diskCache = builder.diskCache;
//        this.proxyPort = builder.proxyPort;
//    }
//
//    public String getDisckCacheDir() {
//        return disckCacheDir;
//    }
//
//    public IVideoDiskCache getDiskCache() {
//        return diskCache;
//    }
//
//    public int getDiskCacheFileCount() {
//        return diskCacheFileCount;
//    }
//
//    public long getDiskCacheSize() {
//        return diskCacheSize;
//    }
//
//    public int getProxyPort() {
//        return proxyPort;
//    }
//
//    public static VideoProxyConfiguration createDefault(Context context) {
//        return new VideoProxyConfiguration(new Builder(context));
//    }
//
//    public static class Builder {
//
//        Context context;
//        long diskCacheSize;
//        int diskCacheFileCount = 1;
//        String disckCacheDir;
//        IVideoDiskCache diskCache;
//        int proxyPort = 8899;
//
//        public Builder(Context context) {
//            this.context = context.getApplicationContext();
//            this.disckCacheDir = getDiskCacheDir();
//            this.diskCacheSize = 100l * 1024 * 1024;
//            this.diskCacheFileCount = 1;
//            this.diskCache = new VideoDiskCache(new File(this.disckCacheDir),
//                    TelephoneUtil.getVersionCode(this.context),
//                    this.diskCacheFileCount,
//                    this.diskCacheSize);
//        }
//
//        private String getDiskCacheDir() {
//            String cacheDir;
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                    || !Environment.isExternalStorageRemovable()) {
//                cacheDir = BaseConfig.VIDEO_DISK_CACHE_DIR;
//            } else {
//                cacheDir = context.getCacheDir().getPath();
//            }
//            return cacheDir;
//        }
//
//        public Builder setDiskCacheSize(long size) {
//            this.diskCacheSize = size;
//            return this;
//        }
//
//        public Builder setDiskCacheFileCount(int size) {
//            this.diskCacheFileCount = size;
//            return this;
//        }
//
//        public Builder setDiskCacheDir(String path) {
//            this.disckCacheDir = path;
//            return this;
//        }
//
//        public Builder setDiskCache(IVideoDiskCache diskCache) {
//            this.diskCache = diskCache;
//            return this;
//        }
//
//        public Builder setProxyPort(int port) {
//            this.proxyPort = port;
//            return this;
//        }
//
//        public VideoProxyConfiguration build() {
//            return new VideoProxyConfiguration(this);
//        }
//    }
//}
