package com.xxm.smartwallpaper.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.adapter.GalleryAlbumAdapter;
import com.xxm.smartwallpaper.adapter.GalleryImageAdapter;
import com.xxm.smartwallpaper.bean.VideoBean;
import com.xxm.smartwallpaper.bean.VideoPaperBean;
import com.xxm.smartwallpaper.inteface.OnItemClickListener;
import com.xxm.smartwallpaper.inteface.OnLoadStateListener;
import com.xxm.smartwallpaper.manager.BaseConfig;
import com.xxm.smartwallpaper.service.WindowService;
import com.xxm.smartwallpaper.util.EasyVideoTool;
import com.xxm.smartwallpaper.util.FileUtil;
import com.xxm.smartwallpaper.util.MessageUtils;
import com.xxm.smartwallpaper.util.ScreenUtil;
import com.xxm.smartwallpaper.util.StatusBarUtil;
import com.xxm.smartwallpaper.util.SystemUtil;
import com.xxm.smartwallpaper.widget.FunPopup;
import com.xxm.smartwallpaper.widget.GridItemDecoration;
import com.xxm.smartwallpaper.widget.HeaderBarCompat;
import com.xxm.smartwallpaper.widget.LoadStateView;

import java.io.File;


/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年06月20日 10:07.</br>
 * @update: </br>
 */

public class GalleryImageActivity extends BaseAcitivity implements LoadStateView.OnRetryListener, OnLoadStateListener, EasyVideoTool.OnMediaStateListener {

    Toolbar toolbar;
    AppBarLayout appbar;
    RecyclerView recyclerView;
    LoadStateView loadStateView;
    ImageView ivFlexible;
    TextureView videoSurface;
    LinearLayout containerContent;
    LinearLayout boxVideo;

    /**
     * 是否直接跳到编辑页
     */
    public static final String EXTRA_EDIT_DIRECTLY = "extra_edit_next";

    private static final int REQUEST_CODE_SYSTEM_VIDEO = 132;

    private GalleryImageAdapter mImageAdapter;

    private GalleryAlbumAdapter mAlbumAdapter;

    private FunPopup mAlbumPopup;

    private VideoPaperBean mSelectedVideo = null;
    private VideoPaperBean mSelectedAlbum = null;

    private Matrix matrix;

    private GridLayoutManager mImageLayoutManager;

    private ValueAnimator mShowAnimator;
    private ValueAnimator mHideAnimator;

    private boolean isEditNext = false;

    private String mSaveVideoPath = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_image);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        appbar = (AppBarLayout) findViewById(R.id.appbar);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        loadStateView = (LoadStateView) findViewById(R.id.load_state_view);
        boxVideo = (LinearLayout) findViewById(R.id.box_video);
        ivFlexible = (ImageView) findViewById(R.id.iv_flexible);
        videoSurface = (TextureView) findViewById(R.id.video_surface);
        EasyVideoTool.get().bind(videoSurface);
        EasyVideoTool.get().setOnMediaStateListener(this);
        loadStateView.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT > 19) {
            StatusBarUtil.from(this)
                    .setActionbarView(toolbar)
                    .setActionbarPadding(true)
                    .setLightStatusBar(true)
                    .process();
        }
        initData();
        initView();
    }

    private void initData() {
        isEditNext = getIntent().getBooleanExtra(EXTRA_EDIT_DIRECTLY, false);
    }

    private void initView() {
        HeaderBarCompat.setTitle(toolbar, "视频选择");
        setSupportActionBar(toolbar);

        // 先隐藏
        boxVideo.setPadding(0, -getResources().getDimensionPixelSize(R.dimen.gallery_image_preview_height) - getResources().getDimensionPixelSize(R.dimen.gallery_image_preview_padding) * 2, 0, boxVideo.getPaddingBottom());

        handleScroll();

        toolbar.setNavigationIcon(R.mipmap.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopup();
            }
        });

        mImageLayoutManager = new GridLayoutManager(this, 3);
        GridItemDecoration itemDecoration = new GridItemDecoration();
        itemDecoration.setOutRect(1, 1, 1, 1);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(mImageLayoutManager);

        mImageAdapter = new GalleryImageAdapter(this, R.layout.item_gallery_image);

        mImageAdapter.setOnLoadStateListener(this);
        loadStateView.setOnRetryListener(this);

        recyclerView.setAdapter(mImageAdapter);

        mImageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View v, int position, int id) {
                VideoPaperBean bean = mImageAdapter.getItem(position);
                if (bean != null) {
                    if ("camera".equals(bean.resId)) {
                        Intent intent = new Intent();
                        intent.setAction("android.media.action.VIDEO_CAPTURE");
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                        mSaveVideoPath = BaseConfig.SOURCE_TEMP_VIDEO_DIR + System.currentTimeMillis() + ".mp4";
                        FileUtil.createDir(BaseConfig.SOURCE_TEMP_VIDEO_DIR);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mSaveVideoPath)));
                        SystemUtil.startActivityForResultSafely(GalleryImageActivity.this, intent, REQUEST_CODE_SYSTEM_VIDEO);
                    } else {
                        playVideo(bean);
                    }
                }
            }
        });

        Bundle bundle = new Bundle();
        mImageAdapter.refresh(bundle);
    }

    private void initPopup() {
        if (mAlbumPopup == null) {
            final View popupView = View.inflate(this, R.layout.view_gallery_album, null);
            RecyclerView popupRecycler = (RecyclerView) popupView.findViewById(R.id.recycler_view);

            int desiredHeight = (int) (ScreenUtil.getCurrentScreenHeight(this) * 0.4f);

            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            GridItemDecoration decoration = new GridItemDecoration();
            decoration.setOutRect(0, 1, 0, 1);
            mAlbumAdapter = new GalleryAlbumAdapter(this, R.layout.item_gallery_album);

            popupRecycler.setLayoutManager(layoutManager);
            popupRecycler.addItemDecoration(decoration);
            popupRecycler.setAdapter(mAlbumAdapter);

            mAlbumAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(ViewGroup parent, View v, int position, int id) {
                    performAlbumClick(position);
                }
            });

            mAlbumAdapter.setOnLoadStateListener(new OnLoadStateListener() {
                @Override
                public void onLoadStart(boolean loadMore) {

                }

                @Override
                public void onLoadError(boolean pending, boolean isNothing, int errorCode, String errorMsg) {
                    MessageUtils.show( "未找到本机视频~");
                }

                @Override
                public void onLoadCompleted(boolean end, int code) {
                    ivFlexible.setImageResource(R.mipmap.ic_arrow_expand);
                }
            });

            mAlbumAdapter.refresh(null);

            mAlbumPopup = new FunPopup.Builder(this)
                    .setContentView(popupView)
                    .setLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    .setOutsideTouchable(true)
                    .setAnimationStyle(R.style.Animation_AppCompat_DropDownUp)
                    .create();

            mAlbumPopup.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    ivFlexible.setImageResource(R.mipmap.ic_arrow_collapse);
                }
            });
        }

        if (mAlbumAdapter.getItemCount() > 0) {
            ivFlexible.setImageResource(R.mipmap.ic_arrow_expand);
        }
        mAlbumPopup.showAsDropDown(toolbar, 0, 0);
    }

    private void performAlbumClick(int position) {
        VideoPaperBean bean = mAlbumAdapter.getItem(position);
        if (bean == null) {
            return;
        }
        mSelectedAlbum = bean;
        if (mImageAdapter != null) {
            if (bean.bucketId == -1) {
                HeaderBarCompat.setTitle(toolbar, "相册选择");
            } else {
                HeaderBarCompat.setTitle(toolbar, bean.bucketName);
            }
            Bundle bundle = mImageAdapter.getParams();
            long bucketId = 0;
            if (bundle != null) {
                bucketId = bundle.getLong("bucketId");
                if (bucketId != bean.bucketId) {
                    bucketId = bean.bucketId;
                    bundle.putLong("bucketId", bucketId);
                    mImageAdapter.refresh(bundle);
                }
            } else {
                bundle = new Bundle();
                bucketId = bean.bucketId;
                bundle.putLong("bucketId", bucketId);
                mImageAdapter.refresh(bundle);
            }
        }
        if (mAlbumPopup != null) {
            mAlbumPopup.dismiss();
        }
    }

    private int mOrientation = -1;
    private int mAnimThresold = 8;

    private void handleScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > mAnimThresold) {//缩进
                    mOrientation = 1;
                } else if ((dy < -mAnimThresold || mImageLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) && mSelectedVideo != null) {//伸出
                    mOrientation = 0;
                } else {
                    mOrientation = -1;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mOrientation == 0) {
                        showVideoSurface();
                    } else if (mOrientation == 1) {
                        hideVideoSurface();
                    }
                }
            }
        });
    }

    private void showVideoSurface() {
        if (mShowAnimator == null) {
            mShowAnimator = ValueAnimator.ofInt(-videoSurface.getHeight() - boxVideo.getPaddingBottom(), boxVideo.getPaddingBottom());
            mShowAnimator.setInterpolator(new DecelerateInterpolator());
            mShowAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    boxVideo.setPadding(0, (Integer) animation.getAnimatedValue(), 0, boxVideo.getPaddingBottom());
                }
            });
            mShowAnimator.setDuration(300);
        }
        if ((mHideAnimator == null || !mHideAnimator.isRunning()) && boxVideo.getPaddingTop() < 0) {
            mShowAnimator.start();
        }
    }

    private void hideVideoSurface() {
        if (mHideAnimator == null) {
            mHideAnimator = ValueAnimator.ofInt(boxVideo.getPaddingBottom(), -videoSurface.getHeight() - boxVideo.getPaddingBottom());
            mHideAnimator.setInterpolator(new AccelerateInterpolator());
            mHideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    boxVideo.setPadding(0, (Integer) animation.getAnimatedValue(), 0, boxVideo.getPaddingBottom());
                }
            });
            mHideAnimator.setDuration(300);
        }

        if ((mShowAnimator == null || !mShowAnimator.isRunning()) && boxVideo.getPaddingTop() >= 0) {
            mHideAnimator.start();
        }
    }

    private void playVideo(VideoPaperBean target) {
        if (EasyVideoTool.get().isPlaying() && mSelectedVideo == target || TextUtils.isEmpty(target.previewUri)) {
            return;
        }
        mSelectedVideo = target;
        EasyVideoTool.get().setVolumnOpen(true);
        EasyVideoTool.get().setDataSource(target.previewUri);
        EasyVideoTool.get().setLooping(true);
        try {
            EasyVideoTool.get().play();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (boxVideo.getPaddingTop() < 0) {
            showVideoSurface();
        }
    }

    private void jumpToNext(String path) {
        if (!FileUtil.isFileExits(path)) {
            FileUtil.createDir(BaseConfig.SOURCE_TEMP_VIDEO_DIR);
            MessageUtils.show( "文件不存在！");
            return;
        }
//        Intent resIntent = new Intent();
//        resIntent.putExtra("extra_data_source", path);
//        setResult(Activity.RESULT_OK, resIntent);
        VideoBean videoBean = new VideoBean();
        videoBean.setVideoPath(path);
        Intent intent = new Intent(this, WindowService.class);
        intent.putExtra("type", 3);
        intent.putExtra("bean", videoBean);
        startService(intent);

        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.toolbar_next_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_next) {
            //下一步
            if (mSelectedVideo != null) {
                jumpToNext(mSelectedVideo.previewUri);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onReload() {
        if (mImageAdapter != null) {
            mImageAdapter.refresh(mImageAdapter.getParams());
        }
    }

    @Override
    public void onNothingReload() {

    }

    @Override
    public void onLoadStart(boolean loadMore) {
        loadStateView.updateState(LoadStateView.STATE_LOADING);
    }

    @Override
    public void onLoadError(boolean pending, boolean isNothing, int errorCode, String errorMsg) {
        if (pending) {
            loadStateView.setErrorCode(errorCode);
            loadStateView.updateState(LoadStateView.STATE_ERROR);
        } else {
            if (isNothing) {
                loadStateView.updateState(LoadStateView.STATE_ERROR);
            } else {
                loadStateView.updateState(LoadStateView.STATE_NONE);
            }
        }
    }

    @Override
    public void onLoadCompleted(boolean end, int code) {
        loadStateView.updateState(LoadStateView.STATE_NONE);
        if (mImageAdapter != null && mImageAdapter.getItemCount() > 0) {
            VideoPaperBean bean = mImageAdapter.getItem(0);
            if (!FileUtil.isFileExits(bean.previewUri)) {
                if (mImageAdapter.getItemCount() > 1) {
                    bean = mImageAdapter.getItem(1);
                }
            }
            playVideo(bean);
            if (mSelectedVideo == null && boxVideo.getPaddingTop() >= 0) {
                hideVideoSurface();
            }
        }
    }

    @Override
    protected void onDestroy() {
        EasyVideoTool.get().unbind();
        super.onDestroy();
    }

    @Override
    public void finish() {
        EasyVideoTool.get().unbind();
        super.finish();
    }


    /**
     * scale type like centerinside
     *
     * @param videoWidth
     * @param videoHeight
     */
    private void transformVideo(int videoWidth, int videoHeight) {
        if (videoSurface.getHeight() == 0 || videoSurface.getWidth() == 0) {
            return;
        }
        float sx = (float) videoSurface.getWidth() / (float) videoWidth;
        float sy = (float) videoSurface.getHeight() / (float) videoHeight;

        float minScale = Math.min(sx, sy);
        if (this.matrix == null) {
            matrix = new Matrix();
        } else {
            matrix.reset();
        }

        //1、先平移到最终缩放后的原点位置
        matrix.preTranslate((videoSurface.getWidth() - videoWidth * minScale) / 2, (videoSurface.getHeight() - videoHeight * minScale) / 2);
        //2、因为默认视频是fitXY的形式显示的,所以首先要缩放还原回来
        matrix.preScale(videoWidth / (float) videoSurface.getWidth(), videoHeight / (float) videoSurface.getHeight());
        //3、以centerinside的方法缩放
        matrix.preScale(minScale, minScale);

        videoSurface.setTransform(matrix);
        videoSurface.postInvalidate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_SYSTEM_VIDEO) {
            if (resultCode == Activity.RESULT_OK) {
                jumpToNext(mSaveVideoPath);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        EasyVideoTool.get().start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        EasyVideoTool.get().pause();
    }

    @Override
    public void onBackPressed() {
        if (mSelectedAlbum != null && mSelectedAlbum.bucketId != -1) {
            if (mAlbumAdapter != null) {
                performAlbumClick(0);
                return;
            }
        }
        EasyVideoTool.get().unbind();
        super.onBackPressed();
    }

    @Override
    public void onSurfaceAvaible(SurfaceTexture surface, int width, int height) {
        if (mSelectedVideo != null) {
            EasyVideoTool.get().setDataSource(mSelectedVideo.previewUri);
            EasyVideoTool.get().setVolumnOpen(true);
            try {
                EasyVideoTool.get().play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onRenderingStart(MediaPlayer mp) {

    }

    @Override
    public void onBufferingEnd(MediaPlayer mp) {

    }

    @Override
    public void onBufferingStart(MediaPlayer mp) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public void onError(MediaPlayer mp, int what, int extra) {

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public boolean onPendingPlay(MediaPlayer mp) {
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        transformVideo(width, height);
    }
}
