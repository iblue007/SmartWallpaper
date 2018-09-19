package com.xxm.smartwallpaper.widget;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.xxm.smartwallpaper.R;


/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年07月02日 14:08.</br>
 * @update: </br>
 */

public class HeaderBarCompat {

    public static void setTitle(Toolbar toolbar, CharSequence title) {
        View t = toolbar.findViewById(R.id.toolbar_title);
        if (t instanceof TextView) {
            TextView titleView = (TextView) t;
            titleView.setText(title);
            toolbar.setTitle("");
        } else {
            toolbar.setTitle(title);
        }
    }
}
