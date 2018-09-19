package com.xxm.smartwallpaper.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.lling.photopicker.utils.Global;
import com.xxm.smartwallpaper.R;
import com.xxm.smartwallpaper.bean.VideoPaperBean;
import com.xxm.smartwallpaper.loader.NativeHelper;
import com.xxm.smartwallpaper.util.imageLoader.DisplayOptionUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: </br>
 * @author: cxy </br>
 * @date: 2017年06月19日 16:24.</br>
 * @update: </br>
 */

public class GalleryAlbumAdapter extends EnhanceRecyclerAdapter<VideoPaperBean> {

    private VideoPaperBean mLastModifiyBean;

    public GalleryAlbumAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
        mLastModifiyBean = new VideoPaperBean();
        mLastModifiyBean.bucketId = -1;
        mLastModifiyBean.bucketName = "所有";
    }

    @Override
    protected void convert(BaseRecyclerViewHolder holder, int position) {
        super.convert(holder, position);

        VideoPaperBean bean = getItem(position);
        if (bean != null) {
            if (bean.bucketId == -1) {
                holder.setVisibility(R.id.iv_gallery_album_thumb, View.INVISIBLE);
            } else {
                holder.setVisibility(R.id.iv_gallery_album_thumb, View.VISIBLE);
                holder.displayImage(R.id.iv_gallery_album_thumb, bean.bucketThumb, DisplayOptionUtil.VIDEO_ROUNDED_OPTIONS);
            }
            holder.setText(R.id.tv_gallery_album_title, bean.bucketName);
        }
    }

    @Override
    protected List<VideoPaperBean> executeAsync(Bundle params) {
        List<VideoPaperBean> data = new ArrayList<VideoPaperBean>();
        data.add(mLastModifiyBean);

        List<VideoPaperBean> list = NativeHelper.queryBuckets(Global.getContext(), true, false);
//        res.getCsResult().setResultCode(0);
        if (list != null) {
            data.addAll(list);
        }

//        res.itemList.addAll(data);
        return data;
    }

    @Override
    public boolean addToRoot() {
        return true;
    }
}
