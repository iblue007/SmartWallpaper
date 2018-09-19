package com.xxm.smartwallpaper.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SeekBar;

import com.umeng.analytics.MobclickAgent;
import com.xxm.smartwallpaper.config.UMEventAnalytic;
import com.xxm.smartwallpaper.service.WindowService;
import com.xxm.smartwallpaper.ui.MainActivity;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.TextBean;
import com.xxm.smartwallpaper.inteface.OnViewClickListener;
import com.xxm.smartwallpaper.util.CreateWindows;
import com.xxm.smartwallpaper.util.ScreenUtil;

import zhou.colorpalette.ColorSelectActivity;
import zhou.colorpalette.ColorSelectDialog;

/**
 * Created by xuqunxing on 2018/9/3.
 */
public class WallpaperTextFragment extends BaseFragment implements View.OnClickListener, SeekBar.OnSeekBarChangeListener {

    private CreateWindows createWindows;
    private int MaxAlbumTime = 25;
    private ColorSelectDialog colorSelectDialog;
    private int lastColor = Color.BLACK;
    private View currentColorView;
    private int currentAlpha = 50;
    private int marginLeft = 0;
    private int marginTop = 0;
    private int marginSize = 30;
    private EditText contentEt;
    private String contentStr;
    private TextBean textBean;
    private SeekBar seekBarTop;
    private SeekBar seekBarSize;
    private SeekBar seekBarAlpha;
    private SeekBar seekBarLeft;
    private Intent intent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.activity_main_text, null);
        init(inflate);
        return inflate;
    }

    private void init(View view) {
        this.intent = new Intent(getContext(), WindowService.class);
        this.intent.putExtra("type", 2);
        View currentColor = view.findViewById(R.id.fragment_text_current_color);
        View selectcolor = view.findViewById(R.id.fragment_text_select_color);
        contentEt = view.findViewById(R.id.fragment_edit_content);
        currentColorView = view.findViewById(R.id.view_current_color);
        seekBarAlpha = (SeekBar) view.findViewById(R.id.activity_seekbar_alpah);
        seekBarLeft = (SeekBar) view.findViewById(R.id.activity_seekbar_margin_left);
        seekBarTop = (SeekBar) view.findViewById(R.id.activity_seekbar_margin_right);
        seekBarSize = (SeekBar) view.findViewById(R.id.activity_seekbar_size);
        textBean = new TextBean(currentAlpha, marginLeft, marginTop, marginSize, contentStr, lastColor);
        currentColorView.setBackgroundColor(lastColor);
        currentColor.setOnClickListener(this);
        selectcolor.setOnClickListener(this);
        seekBarSize.setMax(400);
        seekBarLeft.setMax(ScreenUtil.getCurrentScreenWidth(getContext()));
        seekBarTop.setMax(ScreenUtil.getCurrentScreenHeight(getContext()));
        seekBarAlpha.setProgress(currentAlpha);
        initListener();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.fragment_text_select_color) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.TEXT_CLICK_SELECT_COLOR);
            Intent intent = new Intent(getContext(), ColorSelectActivity.class);
            intent.putExtra(ColorSelectActivity.LAST_COLOR, lastColor);
            startActivityForResult(intent, MainActivity.ACTIVITY_RESULT_COLOR);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            lastColor = data.getIntExtra(ColorSelectActivity.RESULT, 0);
            currentColorView.setBackgroundColor(lastColor);
            textBean.setColor(lastColor);
            UpdateTextView(textBean);
        }
    }

    private void UpdateTextView(TextBean textBean) {
        intent.putExtra("bean", textBean);
        getContext().startService(intent);
        if (onViewCLickListener != null) {
            onViewCLickListener.onWindViewChangeClickListener(MainActivity.TYPE_TEXT);
        }
    }

    public void stopTextWallpaper() {
        getContext().stopService(new Intent(getContext(), WindowService.class));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    private void initListener() {
        seekBarAlpha.setOnSeekBarChangeListener(this);
        seekBarLeft.setOnSeekBarChangeListener(this);
        seekBarTop.setOnSeekBarChangeListener(this);
        seekBarSize.setOnSeekBarChangeListener(this);
        contentEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                contentStr = s.toString();
                textBean.setText(contentStr);
                UpdateTextView(textBean);
            }
        });
    }

    public void setOnViewClicListener(OnViewClickListener onViewCLickListener1) {
        onViewCLickListener = onViewCLickListener1;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.activity_seekbar_alpah:
                currentAlpha = progress;
                textBean.setAlpha(progress);
                UpdateTextView(textBean);
                return;
            case R.id.activity_seekbar_margin_left:
                marginLeft = progress;
                textBean.setLeft(progress);
                UpdateTextView(textBean);
                return;
            case R.id.activity_seekbar_margin_right:
                marginTop = progress;
                textBean.setTop(progress);
                UpdateTextView(textBean);
                return ;
            case R.id.activity_seekbar_size:
                marginSize = progress;
                textBean.setSize(progress);
                UpdateTextView(textBean);
                return;
            default:
                return;
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
