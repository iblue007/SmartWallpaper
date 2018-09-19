package com.xxm.smartwallpaper.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.lling.photopicker.PhotoPickerActivity;
import com.lling.photopicker.utils.Global;
import com.umeng.analytics.MobclickAgent;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.PicBean;
import com.xxm.smartwallpaper.config.BaseConfigPreferences;
import com.xxm.smartwallpaper.config.UMEventAnalytic;
import com.xxm.smartwallpaper.inteface.OnDataLoadListener;
import com.xxm.smartwallpaper.inteface.OnViewClickListener;
import com.xxm.smartwallpaper.manager.BaseConfig;
import com.xxm.smartwallpaper.service.WindowService;
import com.xxm.smartwallpaper.ui.MainActivity;
import com.xxm.smartwallpaper.ui.SpecialActivity;
import com.xxm.smartwallpaper.util.CheckPermission;
import com.xxm.smartwallpaper.util.CommonUtil;
import com.xxm.smartwallpaper.util.FileUtil;
import com.xxm.smartwallpaper.util.MessageUtils;
import com.xxm.smartwallpaper.util.ThreadUtil;
import com.xxm.smartwallpaper.util.XPermissionUtils;
import com.xxm.smartwallpaper.widget.dialog.CommonTwoButtonDialog;

import java.util.ArrayList;

import xcv.sde.dfa.listener.Interface_ActivityListener;
import xcv.sde.dfa.os.OffersManager;
import xcv.sde.dfa.os.PointsManager;

/**
 * Created by xuqunxing on 2018/9/3.
 */
public class WallpaperPicFragment extends BaseFragment implements View.OnClickListener {


    private String showImagePaht;
    private int MaxAlbumTime = 50;
    private float albumPoint = 210f;
    private Intent intent;
    private View selectPic2;
    private View selectPic;
    private View selectPic3;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = View.inflate(getContext(), R.layout.activity_main_pic, null);
        init(inflate);
        return inflate;
    }

    private void init(View view) {
        this.intent = new Intent(getContext(), WindowService.class);
        this.intent.putExtra("type", 1);
        selectPic = view.findViewById(R.id.ll_pic_1);
        selectPic2 = view.findViewById(R.id.ll_pic_2);
        selectPic3 = view.findViewById(R.id.ll_pic_3);
        View selectPic4 = view.findViewById(R.id.ll_pic_4);
        View selectPic5 = view.findViewById(R.id.ll_pic_5);
        RelativeLayout qqGroupRl = view.findViewById(R.id.add_qq_group);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.activity_seekbar_alpah);
        selectPic.setOnClickListener(this);
        qqGroupRl.setOnClickListener(this);
        selectPic2.setOnClickListener(this);
        selectPic3.setOnClickListener(this);
        selectPic4.setOnClickListener(this);
        selectPic5.setOnClickListener(this);
        seekBar.setProgress(50);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= 85) {
                    progress = 85;
                }
                PicBean picBean = new PicBean(showImagePaht, progress, 0, 0, 0, 0);
                intent.putExtra("bean", picBean);
                getContext().startService(intent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        new CheckPermission(getContext());
        camare();
        initPicData(null);


//        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//        Uri uri = Uri.fromParts("package", getContext().getPackageName(), null);
//        intent.setData(uri);
//        startActivityForResult(intent, 6000);

    }

    public void initPicData(final OnDataLoadListener onDataLoadListener) {
        ThreadUtil.executeMore(new Runnable() {
            @Override
            public void run() {
                FileUtil.copyAssetsFile(getContext(), "pic_001.jpg@f_webp", BaseConfig.WIFI_DOWNLOAD_PATH, "pic_001.jpg@f_webp");
                boolean copyAssetsFile = FileUtil.copyAssetsFile(getContext(), "pic_002.jpg@f_webp", BaseConfig.WIFI_DOWNLOAD_PATH, "pic_002.jpg@f_webp");
                if(copyAssetsFile && onDataLoadListener != null){
                    onDataLoadListener.onDataLoadCompleteListener();
                }
            }
        });
    }

    public void camare() {
        XPermissionUtils.requestPermissions(getContext(), XPermissionUtils.CAMERA_REQUEST_CODE, XPermissionUtils.permissionArr, new XPermissionUtils.OnPermissionListener() {
            @Override
            public void onPermissionGranted() {
                initPicData(null);
            }

            @Override
            public void onPermissionDenied(String[] deniedPermissions, boolean alwaysDenied) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.ll_pic_1) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_ALBUM);
            int openAlbumTime = BaseConfigPreferences.getInstance(getContext()).getOpenAlbumTime();
            boolean giveAlbumCoin = BaseConfigPreferences.getInstance(getContext()).isGiveAlbumCoin();
            if (openAlbumTime >= MaxAlbumTime && !giveAlbumCoin) {
                CommonTwoButtonDialog commonTipDialog = new CommonTwoButtonDialog(getContext(), getContext().getResources().getString(R.string.dialog_pic_title, albumPoint + ""),
                        getContext().getResources().getString(R.string.dialog_pic_des),
                        getContext().getResources().getString(R.string.dialog_pic_delete_coin, albumPoint + ""),
                        getContext().getResources().getString(R.string.dialog_pic_earn_coin));
                commonTipDialog.show();
                commonTipDialog.setOnGnClickListener(new CommonTwoButtonDialog.OnGnClickListener() {
                    @Override
                    public void onSubmit1Click(View v) {
//                        MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_ALBUM_QQ);
//                        CommonUtil.joinQQGroup(getContext());
                        float pointsBalance = PointsManager.getInstance(getContext()).queryPoints();
                        if (pointsBalance >= albumPoint) {
                            PointsManager.getInstance(getContext()).spendPoints(albumPoint);
                            BaseConfigPreferences.getInstance(getContext()).setGiveAlbumCoin(true);
                            gotoAlbum();
                        } else {
                            MessageUtils.show(getString(R.string.dialog_pic_no_coin));
                        }
                    }

                    @Override
                    public void onSubmit2Click(View v) {
                        OffersManager.getInstance(getContext()).showOffersWall(new Interface_ActivityListener() {

                            /**
                             * 当积分墙销毁的时候，即积分墙的Activity调用了onDestory的时候回调
                             */
                            @Override
                            public void onActivityDestroy(Context context) {
                                //  Toast.makeText(context, "全屏积分墙退出了", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            } else {
                BaseConfigPreferences.getInstance(getContext()).setOpenAlbumTime(openAlbumTime + 1);
                gotoAlbum();
            }
        } else if (id == R.id.ll_pic_2) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_PIC1);
            showImagePaht = BaseConfig.WIFI_DOWNLOAD_PATH + "pic_001.jpg@f_webp";
            if(FileUtil.isFileExits(showImagePaht)){
                setWallpaper();
            }else {
                initPicData(new OnDataLoadListener(){
                    @Override
                    public void onDataLoadCompleteListener() {
                        Global.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                setWallpaper();
                            }
                        });
                    }
                });
            }
        } else if (id == R.id.ll_pic_3) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_PIC2);
            showImagePaht = BaseConfig.WIFI_DOWNLOAD_PATH + "pic_002.jpg@f_webp";
            if(FileUtil.isFileExits(showImagePaht)){
                setWallpaper();
            }else {
                initPicData(new OnDataLoadListener(){
                    @Override
                    public void onDataLoadCompleteListener() {
                        Global.runInMainThread(new Runnable() {
                            @Override
                            public void run() {
                                setWallpaper();
                            }
                        });
                    }
                });
            }
        } else if (id == R.id.ll_pic_4) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_MORE);
            startActivity(new Intent(getContext(), SpecialActivity.class));
        } else if (id == R.id.ll_pic_5) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_CLOSE);
            getContext().stopService(new Intent(getContext(), WindowService.class));
        } else if (id == R.id.add_qq_group) {
            MobclickAgent.onEvent(getContext(), UMEventAnalytic.PIC_CLICK_QQ_GROUP);
            CommonUtil.joinQQGroup(getContext());
        }
    }

    private void setWallpaper(){
        PicBean picBean = new PicBean(showImagePaht, 50, 0, 0, 0, 0);
        this.intent.putExtra("bean", picBean);
        getContext().startService(this.intent);
        if (onViewCLickListener != null) {
            onViewCLickListener.onWindViewChangeClickListener(MainActivity.TYPE_PIC);
        }
    }
    private void gotoAlbum() {
        Intent intent = new Intent(getContext(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, 1);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, 1);
        startActivityForResult(intent, XPermissionUtils.CAMERA_REQUEST_CODE);
    }

    public void stopPicWallpaper() {
        getContext().stopService(new Intent(getContext(), WindowService.class));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
            if (result != null && result.size() > 0) {

                showImagePaht = result.get(0);
                PicBean picBean = new PicBean(showImagePaht, 50, 0, 0, 0, 0);
                this.intent.putExtra("bean", picBean);
                getContext().startService(this.intent);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    public void setOnViewClicListener(OnViewClickListener onViewCLickListener1) {
        onViewCLickListener = onViewCLickListener1;
    }
}
