package com.xxm.smartwallpaper.inteface;

import java.io.File;
import java.io.InputStream;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年06月23日 17:55.</br>
 * @update: </br>
 */

public interface IVideoDiskCache {

    File getDirectory();

    File get(String url);

    String getKey(String url);

    boolean save(String url, InputStream is);

    boolean remove(String url);

    void close();

    void clear();

    long size();

    void beginTransaction(String url);

    void write(byte[] bytes);

    void write(byte[] bytes, int offset);

    void endTransaction();

    boolean isAvailable(String url);
}
