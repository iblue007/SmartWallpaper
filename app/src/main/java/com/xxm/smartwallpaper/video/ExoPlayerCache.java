package com.xxm.smartwallpaper.video;

import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.CacheSpan;
import com.google.android.exoplayer2.upstream.cache.LeastRecentlyUsedCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.xxm.smartwallpaper.manager.BaseConfig;
import java.io.File;
import java.util.NavigableSet;
import java.util.Set;

/**
 * Exo 播放器文件缓存
 * Created by linliangbin on 2018/5/30 9:26.
 */

public class ExoPlayerCache {

    private static ExoPlayerCache instance;
    private SimpleCache cache;


    private ExoPlayerCache() {
        cache = new SimpleCache(new File(BaseConfig.SOURCE_TEMP_ONLINE_CACHE), new LeastRecentlyUsedCacheEvictor(100 * 1024 * 1024));
    }

    public synchronized static ExoPlayerCache getInstance() {
        if (instance == null) {
            instance = new ExoPlayerCache();
        }
        return instance;
    }

    public Cache getCacheStrategy() {
        return cache;
    }

    public long getCacheSize() {
        return cache.getCacheSpace();
    }

    public void clearCache() {
        Set<String> cacheString = cache.getKeys();
        for (String key : cacheString) {
            NavigableSet<CacheSpan> cacheSpans = cache.getCachedSpans(key);
            for (CacheSpan cacheSpan : cacheSpans) {
                try {
                    cache.removeSpan(cacheSpan);
                } catch (Cache.CacheException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
