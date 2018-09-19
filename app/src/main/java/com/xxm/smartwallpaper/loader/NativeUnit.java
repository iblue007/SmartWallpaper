//package com.xxm.smartwallpaper.loader;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.xxm.smartwallpaper.bean.VideoPaperBean;
//import com.xxm.smartwallpaper.manager.BaseConfig;
//import com.xxm.smartwallpaper.util.FileUtil;
//
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileFilter;
//import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @Description: </br>
// * @author: cxy </br>
// * @date: 2017年05月16日 12:57.</br>
// * @update: </br>
// */
//
//public class NativeUnit extends AbstractNative<VideoPaperBean> {
//
//
//    @Override
//    public boolean save(VideoPaperBean bean) {
//        if (verifyBase(bean, true)) {
//            String configPath = getConfigPath(bean.resId, bean.identifier);
//            try {
//                String content = format(bean).toString();
//                return FileUtil.writeFile(configPath, NativeAssistant.encryptConfig(content), false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean delete(String vid, String identifier) {
//        String resFile = getResourcePath(vid, identifier);
//        String configFile = getConfigPath(vid, identifier);
//        String wallpaperFile = getWallpaperPath(vid, identifier);
//
//        boolean resDelSuc = FileUtil.delFile(resFile);
//        boolean configDelSuc = FileUtil.delFile(configFile);
//        boolean wpDelSuc = FileUtil.delFile(wallpaperFile);
//        //有一个删除成功就算成功
//        return resDelSuc || configDelSuc || wpDelSuc;
//    }
//
//    @Override
//    public List<VideoPaperBean> query(Context context, int pageIndex, int pageSize) {
//        File configDir = new File(getConfigDir());
//        File[] files = configDir.listFiles(new FileFilter() {
//            @Override
//            public boolean accept(File pathname) {
//                if (pathname.isFile()) {
//                    return true;
//                }
//                return false;
//            }
//        });
//        List<VideoPaperBean> list = null;
//        if (files != null && files.length > 0) {
//            for (int i = 0, len = files.length; i < len; i++) {
//                File file = files[i];
//                VideoPaperBean bean = verifyConfig(FileUtil.readFileContent(file.getAbsolutePath()), true);
//                if (bean != null) {
//                    if (list == null) {
//                        list = new ArrayList<VideoPaperBean>();
//                    }
//                    list.add(bean);
//                }
//            }
//        }
//
//        return list;
//    }
//
//    @Override
//    public String getResourceName(String vid, String identifier) {
//        return (vid + "_" + identifier);
//    }
//
//    @Override
//    public String getWallpaperName(String vid, String identifier) {
//        return ("wallpaper_" + vid + "_" + identifier);
//    }
//
//    @Override
//    public String getConfigName(String vid, String identifier) {
//        try {
//            return (DigestUtil.md5Encode((vid + "_" + identifier).getBytes("utf-8")));
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//        return "";
//    }
//
//    @Override
//    public String getResourceDir() {
//        return BaseConfig.VIDEO_SOURCE_DIR;
//    }
//
//    @Override
//    public String getWallpaperDir() {
//        return BaseConfig.VIDEO_WALLPAPER_DIR;
//    }
//
//    @Override
//    public String getConfigDir() {
//        return BaseConfig.UNIT_CONFIG_DIR;
//    }
//
//    @Override
//    public boolean verifyResourceWeak(String vid, String identifier) {
//        String resFile = getResourcePath(vid, identifier);
//        return FileUtil.isFileExits(resFile);
//    }
//
//    @Override
//    public boolean verifyResourceStrong(String vid, String identifier) {
//        String configFile = getConfigPath(vid, identifier);
//        VideoPaperBean bean = verifyConfig(FileUtil.readFileContent(configFile), true);
//        return bean != null;
//    }
//
//    @Override
//    public VideoPaperBean verifyConfig(String content, boolean isStrict) {
//        if (TextUtils.isEmpty(content)) {
//            return null;
//        }
//        try {
//            content = NativeAssistant.decryptConfig(content);
//            JSONObject json = new JSONObject(content);
//            VideoPaperBean bean = parse(json);
//            //校验本地文件的合法性
//            if (verifyBase(bean, isStrict)) {
//                return bean;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//    @Override
//    public boolean verifyBase(VideoPaperBean bean, boolean isStrict) {
//        if (bean == null) {
//            return false;
//        }
//        return NativeAssistant.verifyBean(bean, getWallpaperPath(bean.resId, bean.identifier), getResourcePath(bean.resId, bean.identifier));
//    }
//
//    @Override
//    public JSONObject format(VideoPaperBean bean) {
//        return NativeAssistant.beanToJson(bean);
//    }
//
//    @Override
//    public VideoPaperBean parse(JSONObject json) {
//        return NativeAssistant.jsonToBean(json);
//    }
//}
