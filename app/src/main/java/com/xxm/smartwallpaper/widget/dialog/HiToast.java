package com.xxm.smartwallpaper.widget.dialog;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.xxm.smartwallpaper.R;
import com.lling.photopicker.utils.Global;

public class HiToast {

    Toast mToast;
    TextView mMessage;

    private HiToast(Context context, CharSequence msg, int duration) {
        mToast = Toast.makeText(context, msg, duration);
        View view = LayoutInflater.from(context).inflate(R.layout.view_hi_toast, null);
        mToast.setView(view);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mMessage = (TextView) view.findViewById(R.id.message_hi_toast);
        mMessage.setText(msg);
    }

    public static HiToast makeText(Context context, CharSequence msg, int duration) {
        return new HiToast(context, msg, duration);
    }

    public static HiToast makeText(Context context, int strId, int duration) {
        return new HiToast(context, context.getString(strId), duration);
    }

    public void setText(CharSequence msg) {
        mMessage.setText(msg);
    }

    public void setText(int strId) {
        mMessage.setText(Global.getContext().getString(strId));
    }

    public void setDuration(int duration) {
        mToast.setDuration(duration);
    }

    public void show() {
        if (mToast != null) {
            mToast.show();
        }
    }
}
