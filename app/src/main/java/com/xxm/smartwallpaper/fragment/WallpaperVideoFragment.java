package com.xxm.smartwallpaper.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.umeng.analytics.MobclickAgent;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.VideoBean;
import com.xxm.smartwallpaper.config.UMEventAnalytic;
import com.xxm.smartwallpaper.inteface.OnDataLoadListener;
import com.xxm.smartwallpaper.inteface.OnViewClickListener;
import com.xxm.smartwallpaper.manager.BaseConfig;
import com.xxm.smartwallpaper.service.WindowService;
import com.xxm.smartwallpaper.ui.GalleryImageActivity;
import com.xxm.smartwallpaper.ui.MainActivity;
import com.xxm.smartwallpaper.ui.SpecialActivity;
import com.xxm.smartwallpaper.util.CommonUtil;
import com.xxm.smartwallpaper.util.CreateWindows;
import com.xxm.smartwallpaper.util.FileUtil;
import com.xxm.smartwallpaper.util.PermissionHelper;
import com.xxm.smartwallpaper.util.ThreadUtil;

/**
 * Created by xuqunxing on 2018/9/3.
 */
public class WallpaperVideoFragment extends BaseFragment implements View.OnClickListener {


    private CreateWindows createWindows;
    private PermissionHelper mPermissionHelper;
    private String currentVideoPath = "";
    private RelativeLayout picLL1;
    private RelativeLayout picLL2;
    private RelativeLayout picLL3;
    private RelativeLayout picLL4;
    private RelativeLayout picLL5;
    private RelativeLayout picLL6;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.activity_main_video, null);
        init(inflate);
        return inflate;
    }

    private void init(View view) {
        SeekBar seekBar = (SeekBar)view. findViewById(R.id.activity_seekbar_alpah);
        picLL1 = (RelativeLayout) view.findViewById(R.id.ll_pic_1);
        picLL2 = (RelativeLayout) view.findViewById(R.id.ll_pic_2);
        picLL3 = (RelativeLayout) view.findViewById(R.id.ll_pic_3);
        picLL4 = (RelativeLayout) view.findViewById(R.id.ll_pic_4);
        picLL5 = (RelativeLayout) view.findViewById(R.id.ll_pic_5);
        picLL6 = (RelativeLayout)view. findViewById(R.id.ll_pic_6);
        RelativeLayout picLL7 = (RelativeLayout) view.findViewById(R.id.ll_pic_7);
        RelativeLayout picLL8 = (RelativeLayout) view.findViewById(R.id.ll_pic_8);
        RelativeLayout picLL9 = (RelativeLayout) view.findViewById(R.id.ll_pic_9);
        RelativeLayout addQQ = (RelativeLayout)view.findViewById(R.id.add_qq_group);
        picLL1.setOnClickListener(this);
        picLL2.setOnClickListener(this);
        picLL3.setOnClickListener(this);
        picLL4.setOnClickListener(this);
        picLL5.setOnClickListener(this);
        picLL6.setOnClickListener(this);
        picLL7.setOnClickListener(this);
        picLL8.setOnClickListener(this);
        picLL9.setOnClickListener(this);
        addQQ.setOnClickListener(this);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress >= 85){
                    progress = 85;
                }
                VideoBean videoBean = new VideoBean();
                videoBean.setVideoPath(currentVideoPath);
                videoBean.setAlpha(progress * 1.0f / 100);
                Intent intent = new Intent(getContext(), WindowService.class);
                intent.putExtra("type", 3);
                intent.putExtra("bean", videoBean);
                getContext().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        initVideoData(null);
    }

    private void initVideoData(final OnDataLoadListener onDataLoadListener) {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                FileUtil.copyAssetsFile(getContext(), "0001.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0001.mp4");
                FileUtil.copyAssetsFile(getContext(), "0002.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0002.mp4");
                FileUtil.copyAssetsFile(getContext(), "0003.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0003.mp4");
                FileUtil.copyAssetsFile(getContext(), "0004.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0004.mp4");
                FileUtil.copyAssetsFile(getContext(), "0005.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0005.mp4");
                boolean copyAssetsFile = FileUtil.copyAssetsFile(getContext(), "0006.mp4", BaseConfig.WIFI_DOWNLOAD_PATH, "0006.mp4");
                if(copyAssetsFile && onDataLoadListener != null){
                    onDataLoadListener.onDataLoadCompleteListener();
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.ll_pic_1){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO1);
            startVideoPlay(1);

        }else if(id == R.id.ll_pic_2){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO2);
            startVideoPlay(2);
        }else if(id == R.id.ll_pic_3){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO3);
            startVideoPlay(3);
        }else if(id == R.id.ll_pic_4){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO4);
            startVideoPlay(4);
        }else if(id == R.id.ll_pic_5){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO5);
            startVideoPlay(5);
        }else if(id == R.id.ll_pic_6){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_VIDEO6);
            startVideoPlay(6);
        }else if(id == R.id.ll_pic_7){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_ClOSE);
            stopVideoPlay();
        }else if(id == R.id.ll_pic_8){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_MORE);
            startActivity(new Intent(getContext(), SpecialActivity.class));
        }else if(id == R.id.add_qq_group){
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.VIDEO_CLICK_QQ_GROUP);
            CommonUtil.joinQQGroup(getContext());
        }else if(id == R.id.ll_pic_9){
            getContext().startActivity(new Intent(getContext(),GalleryImageActivity.class));
        }
        //startAd();
    }

    private void startVideoPlay(int pos){
       // CommonUtil.startVideoPlay(getContext(),pos);
        currentVideoPath = BaseConfig.WIFI_DOWNLOAD_PATH +"000"+pos+".mp4";
        if(!FileUtil.isFileExits(currentVideoPath)){
            initVideoData(new OnDataLoadListener() {
                @Override
                public void onDataLoadCompleteListener() {
                    setVideoWallPaper();
                }
            });
        }else {
            setVideoWallPaper();
        }
    }

    private void setVideoWallPaper(){
        VideoBean videoBean = new VideoBean();
        videoBean.setVideoPath(currentVideoPath);
        Intent intent = new Intent(getContext(), WindowService.class);
        intent.putExtra("type", 3);
        intent.putExtra("bean", videoBean);
        getContext().startService(intent);

        if(onViewCLickListener != null){
            onViewCLickListener.onWindViewChangeClickListener(MainActivity.TYPE_VIDEO);
        }
    }
    public void stopVideoPlay(){
        //CommonUtil.stopVideoPlay(getContext());
        Intent intent = new Intent(getContext(), WindowService.class);
        getContext().stopService(intent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void setOnViewClicListener(OnViewClickListener onViewCLickListener1) {
        onViewCLickListener = onViewCLickListener1;
    }
}
