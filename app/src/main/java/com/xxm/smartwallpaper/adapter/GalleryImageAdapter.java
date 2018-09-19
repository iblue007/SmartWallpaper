package com.xxm.smartwallpaper.adapter;

import android.content.Context;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import com.lling.photopicker.utils.Global;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.VideoPaperBean;
import com.xxm.smartwallpaper.inteface.ItemFilter;
import com.xxm.smartwallpaper.loader.NativeHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年06月20日 10:07.</br>
 * @update: </br>
 */

public class GalleryImageAdapter extends EnhanceRecyclerAdapter<VideoPaperBean> implements ItemFilter<VideoPaperBean> {

    private SimpleDateFormat mDateFormattor = new SimpleDateFormat("mm:ss");
    private VideoPaperBean mCamera;

    public GalleryImageAdapter(Context context, int layoutResId) {
        super(context, layoutResId, true);
        setItemFilter(this);
        mCamera = new VideoPaperBean();
        mCamera.resId = "camera";
        mCamera.thumbUri = BaseImageDownloader.Scheme.DRAWABLE.wrap("" + R.drawable.bg_shoot);
        mExistIds.clear();
    }

    @Override
    protected void convert(BaseRecyclerViewHolder holder, int position) {
        super.convert(holder, position);

        VideoPaperBean bean = getItem(position);
        if (bean != null) {
            if ("camera".equals(bean.resId)) {
                holder.setVisibility(R.id.tv_video_footnote, View.GONE);
                holder.setImageBitmap(R.id.iv_gallery_image_thumb, null);
                holder.setBackgroundDrawable(R.id.iv_gallery_image_thumb, R.drawable.bg_shoot);
            } else {
                holder.setVisibility(R.id.tv_video_footnote, View.VISIBLE);
                holder.setText(R.id.tv_video_footnote, mDateFormattor.format(new Date(bean.duration)));
                holder.displayImage(R.id.iv_gallery_image_thumb, bean.thumbUri);
            }
        }
    }

    @Override
    protected List<VideoPaperBean> executeAsync(Bundle params) {
        if (params == null) {
            return null;
        }

//        ServerResult<VideoPaperBean> res = new ServerResult<VideoPaperBean>();
//        res.itemList.add(mCamera);
        List<VideoPaperBean> list = null;

        long bucketId = params.getLong("bucketId", -1);
        if (bucketId == -1) {
            list = NativeHelper.queryLocal(Global.getContext(), mPageIndex, mPageSize, null, null, MediaStore.Video.Media.DATE_MODIFIED + " DESC");
        } else {
            list = NativeHelper.queryLocal(Global.getContext(), mPageIndex, mPageSize, MediaStore.Video.Media.BUCKET_ID + "=?", new String[]{"" + bucketId}, null);
        }

      //  res.getCsResult().setResultCode(0);
//        if (list != null) {
//            res.itemList.addAll(list);
//        }
        return list;
    }

    private Set<String> mExistIds = new HashSet<String>();
    @Override
    public boolean accept(List<VideoPaperBean> exists, VideoPaperBean item) {
        if (item != null) {
            if (!mExistIds.contains(item.previewUri)) {
                mExistIds.add(item.previewUri);
                return true;
            }
        }
        return false;
    }
}
