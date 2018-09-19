//package com.xxm.smartwallpaper.util;
//
//
//import android.text.TextUtils;
//
//import com.lling.photopicker.utils.Global;
//import com.xxm.smartwallpaper.inteface.IVideoDiskCache;
//import com.xxm.smartwallpaper.inteface.ProxyCallback;
//
//import java.io.File;
//
///**
// * @Description: </br>
// * @author: cxy </br>
// * @date: 2017年06月23日 16:21.</br>
// * @update: </br>
// */
//
//public class VideoProxyLoader {
//
//    private boolean hasInitailized = false;
//    private VideoProxyConfiguration mConfiguration;
//    private HttpGetProxy mProxy;
//
//    private static class VideoProxyLoaderHolder {
//        public static final VideoProxyLoader sInstance = new VideoProxyLoader();
//    }
//
//    public static VideoProxyLoader getInstance() {
//        return VideoProxyLoaderHolder.sInstance;
//    }
//
//    public boolean hasInitialized() {
//        return hasInitailized;
//    }
//
//    public void init(VideoProxyConfiguration configuration) {
//        if (configuration == null) {
//            return;
//        }
//
//        this.mConfiguration = configuration;
//        mProxy = HttpGetProxy.get();
//        mProxy.monitor(configuration.proxyPort);
//        mProxy.setDiskCache(configuration.diskCache);
//
//        this.hasInitailized = true;
//    }
//
//    public void proxyAsync(final String originUrl, final ProxyCallback callback) {
//        if (TextUtils.isEmpty(originUrl) || (!originUrl.startsWith("http://") && !originUrl.startsWith("https://"))) {
//            if (callback != null) {
//                Global.runInMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onPrepared(originUrl, originUrl, null);
//                    }
//                });
//            }
//            return;
//        }
//
//        final String path = VideoProxyLoader.getInstance().isNativeAvailable(originUrl);
//        if (!TextUtils.isEmpty(path)) {
//            if (callback != null) {
//                Global.runInMainThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        callback.onPrepared(originUrl, path, null);
//                    }
//                });
//            }
//            return;
//        }
//        mProxy.enqueue(originUrl, callback);
//    }
//
//    public void postPause() {
//        mProxy.postPause();
//    }
//
//    public IVideoDiskCache getDiskCache() {
//        return this.mConfiguration.getDiskCache();
//    }
//
//    public String isNativeAvailable(String url) {
//        try {
//            File cache = this.mConfiguration.getDiskCache().get(url);
//            return this.mConfiguration.getDiskCache().isAvailable(url) ? cache.getAbsolutePath() : null;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
//    }
//}
