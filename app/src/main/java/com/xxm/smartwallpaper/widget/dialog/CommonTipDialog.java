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
public class CommonTipDialog extends Dialog {

    private Context mContext;
    private TextView titleTv;
    private TextView tipTv;
    private TextView submitTv;
    private String tipStr,title,  cancleStr,  okStr;
    private LinearLayout titleLl;

    public CommonTipDialog(Context context) {
        super(context, R.style.Dialog_No_Anim);
        this.mContext =context;
        init();
    }

    public CommonTipDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext =context;
        init();
    }

    public CommonTipDialog(Context context, String title, String tip, String okStr) {
        super(context, R.style.Dialog_No_Anim);
        this.mContext =context;
        this.title = title;
        this.tipStr = tip;
        this.okStr = okStr;
        init();
    }


    private void init() {
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hd_circle_down_tip_dialog);

        initViews();
    }

    private void initViews() {
        titleTv = (TextView) findViewById(R.id.dialog_title);
        tipTv = (TextView) findViewById(R.id.dialog_tip);
        submitTv = (TextView) findViewById(R.id.dialog_submit);
        titleLl = (LinearLayout) findViewById(R.id.title_ll);
        //cancleTv.setOnClickListener(clickListener);
        submitTv.setOnClickListener(clickListener);

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
//        if(!TextUtils.isEmpty(cancleStr)){
//            cancleTv.setText(cancleStr);
//        }else {
//
//        }
        if(!TextUtils.isEmpty(okStr)){
            submitTv.setText(okStr);
        }else {

        }

    }


    View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            int id = v.getId();
            if(id == R.id.dialog_submit){
                if (onGnClickListener != null) {
                    onGnClickListener.onSubmitClick(v);
                }
                dismiss();
            }
//            else  if(id == R.id.dialog_cancle){
//                dismiss();
//                if (onGnClickListener != null) {
//                    onGnClickListener.onCancleClick(v);
//                }
//            }
        }
    };

    private OnGnClickListener onGnClickListener;

    public void setOnGnClickListener(OnGnClickListener onGnClickListener) {
        this.onGnClickListener = onGnClickListener;
    }

    public interface OnGnClickListener {
        public void onSubmitClick(View v);
        public void onCancleClick(View v);
    }

}
