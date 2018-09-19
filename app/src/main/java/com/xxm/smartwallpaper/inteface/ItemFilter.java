package com.xxm.smartwallpaper.inteface;

import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年07月07日 20:34.</br>
 * @update: </br>
 */

public interface ItemFilter<T> {

    boolean accept(List<T> exists, T item);
}
