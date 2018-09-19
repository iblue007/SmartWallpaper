package com.xxm.smartwallpaper.inteface;

import java.util.Map;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年07月06日 21:13.</br>
 * @update: </br>
 */

public interface ProxyCallback {

    void onPrepared(String originUrl, String proxyUrl, Map<String, String> params);
}
