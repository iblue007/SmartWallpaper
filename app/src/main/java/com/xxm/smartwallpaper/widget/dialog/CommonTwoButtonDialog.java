package com.xxm.smartwallpaper.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.util.ScreenUtil;

/**开启循环视频壁纸的dialog
 * Created by xuqunxing on 2017/8/18.
 */
public class CommonTwoButtonDialog extends Dialog {

    private Context mContext;
    private TextView titleTv;
    private TextView tipTv;
    private TextView submit1Tv;
    private TextView submit2Tv;
    private String tipStr,title,ok1Str,ok2Str;
    private LinearLayout titleLl;

    public CommonTwoButtonDialog(Context context) {
        super(context, R.style.Dialog_No_Anim);
        this.mContext =context;
        init();
    }

    public CommonTwoButtonDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext =context;
        init();
    }

    public CommonTwoButtonDialog(Context context, String title, String tip, String ok1Str,String ok2Str) {
        super(context, R.style.Dialog_No_Anim);
        this.mContext =context;
        this.title = title;
        this.tipStr = tip;
        this.ok1Str = ok1Str;
        this.ok2Str = ok2Str;
        init();
    }


    private void init() {
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd_circle_down_tip_dialog2);

        initViews();
    }

    private void initViews() {
        titleTv = (TextView) findViewById(R.id.dialog_title);
        tipTv = (TextView) findViewById(R.id.dialog_tip);
        submit1Tv = (TextView) findViewById(R.id.dialog_submit1);
        submit2Tv = (TextView) findViewById(R.id.dialog_submit2);
        titleLl = (LinearLayout) findViewById(R.id.title_ll);
        submit2Tv.setOnClickListener(clickListener);
        submit1Tv.setOnClickListener(clickListener);

        if(!TextUtils.isEmpty(title)){
            titleTv.setText(title);
        }
        if(!TextUtils.isEmpty(tipStr)){
            tipTv.setVisibility(View.VISIBLE);
            tipTv.setText(tipStr);
        }else {
            tipTv.setVisibility(View.GONE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titleLl.getLayoutParams();
            layoutParams.bottomMargin = ScreenUtil.dip2px(getContext(),40);
            titleLl.setLayoutParams(layoutParams);
        }
        if(!TextUtils.isEmpty(ok2Str)){
            submit2Tv.setText(ok2Str);
        }else {

        }
        if(!TextUtils.isEmpty(ok1Str)){
            submit1Tv.setText(ok1Str);
        }else {

        }

    }


    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.dialog_submit1){
                if (onGnClickListener != null) {
                    onGnClickListener.onSubmit1Click(v);
                }
                dismiss();
            }else  if(id == R.id.dialog_submit2){
                dismiss();
                if (onGnClickListener != null) {
                    onGnClickListener.onSubmit2Click(v);
                }
            }
        }
    };

    private OnGnClickListener onGnClickListener;

    public void setOnGnClickListener(OnGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface OnGnClickListener {
        public void onSubmit1Click(View v);
        public void onSubmit2Click(View v);
    }

}
