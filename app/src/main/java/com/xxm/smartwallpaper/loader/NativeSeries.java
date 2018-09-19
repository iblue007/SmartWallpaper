//package com.xxm.smartwallpaper.loader;
//
//import android.content.Context;
//import android.text.TextUtils;
//
//import com.xxm.smartwallpaper.bean.VideoPaperBean;
//import com.xxm.smartwallpaper.manager.BaseConfig;
//import com.xxm.smartwallpaper.util.FileUtil;
//
//import org.json.JSONArray;
//import org.json.JSONException;
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
//public class NativeSeries extends AbstractNative<VideoPaperSeriesBean> {
//
//    @Override
//    public boolean save(VideoPaperSeriesBean bean) {
//
//        if (bean == null || bean.units == null || bean.units.isEmpty()) {
//            return false;
//        }
//
//        if (verifyBase(bean, true)) {
//            String configPath = getConfigPath(bean.resId, bean.identifier);
//            try {
//                String content = format(bean).toString();
//                return FileUtil.writeFile(configPath, NativeAssistant.encryptConfig(content), false);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        return false;
//    }
//
//    @Override
//    public boolean delete(String vid, String identifier) {
//        boolean res = true;
//        String configPath = getConfigPath(vid, identifier);
//        String configStr = FileUtil.readFileContent(configPath);
//        try {
//            VideoPaperSeriesBean bean = parse(new JSONObject(NativeAssistant.decryptConfig(configStr)));
//            if (bean != null) {
//                if (bean.units != null && !bean.units.isEmpty()) {
//                    for (int i = 0, len = bean.units.size(); i < len; i++) {
//                        VideoPaperBean item = bean.units.get(i);
//                        String resFile = getSubResourcePath(bean.resId, item.resId, item.identifier);
//                        String wallpaperFile = getSubWallpaperPath(bean.resId, item.resId, item.identifier);
//                        FileUtil.delFile(resFile);
//                        FileUtil.delFile(wallpaperFile);
//                    }
//                }
//            }
//            String configFile = getConfigPath(vid, identifier);
//            FileUtil.delFile(configFile);
//        } catch (Exception e) {
//            e.printStackTrace();
//            res = false;
//        }
//        return res;
//    }
//
//    @Override
//    public List<VideoPaperSeriesBean> query(Context context, int pageIndex, int pageSize) {
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
//        List<VideoPaperSeriesBean> list = null;
//        if (files != null && files.length > 0) {
//            for (int i = 0, len = files.length; i < len; i++) {
//                File file = files[i];
//                VideoPaperSeriesBean bean = verifyConfig(FileUtil.readFileContent(file.getAbsolutePath()), true);
//                if (bean != null) {
//                    if (list == null) {
//                        list = new ArrayList<VideoPaperSeriesBean>();
//                    }
//                    list.add(bean);
//                }
//            }
//        }
//        return list;
//    }
//
//    /**
//     * 天天视频已分视频保存
//     *
//     * @param vid
//     * @param identifier
//     * @return
//     */
//    @Override
//    public String getResourceName(String vid, String identifier) {
//        return null;
//    }
//
//    /**
//     * 获取子无
//     *
//     * @param seriesId
//     * @param vid
//     * @param subIdentifier
//     * @return
//     */
//    public String getSubResourceName(String seriesId, String vid, String subIdentifier) {
//        return seriesId + "_" + vid + "_" + subIdentifier;
//    }
//
//    public String getSubResourcePath(String seriesId, String vid, String subIdentifier) {
//        return getResourceDir() + getSubResourceName(seriesId, vid, subIdentifier);
//    }
//
//    /**
//     * 天天视频已分壁纸保存
//     *
//     * @param vid
//     * @param identifier
//     * @return
//     */
//    @Override
//    public String getWallpaperName(String vid, String identifier) {
//        return null;
//    }
//
//    public String getSubWallpaperName(String seriesId, String vid, String subIdentifier) {
//        return "wallpaper_" + seriesId + "_" + vid + "_" + subIdentifier;
//    }
//
//    public String getSubWallpaperPath(String seriesId, String vid, String subIdentifier) {
//        return getWallpaperDir() + getSubWallpaperName(seriesId, vid, subIdentifier);
//    }
//
//    @Override
//    public String getConfigName(String vid, String identifier) {
//        try {
//            return (DigestUtil.md5Encode((vid).getBytes("utf-8")));
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
//        return BaseConfig.SERIES_CONFIG_DIR;
//    }
//
//    @Override
//    public boolean verifyResourceWeak(String vid, String identifier) {
//        String configFile = getConfigPath(vid, null);
//        VideoPaperSeriesBean bean = verifyConfig(FileUtil.readFileContent(configFile), false);
//        return bean != null;
//    }
//
//    public boolean verifySubResourceWeak(String seriesId, String vid, String subIdentifier) {
//        String configFile = getConfigPath(seriesId, null);
//        boolean exists = false;
//        try {
//            String content = FileUtil.readFileContent(configFile);
//            content = NativeAssistant.decryptConfig(content);
//            JSONObject json = new JSONObject(content);
//            VideoPaperSeriesBean bean = parse(json);
//            if (bean != null && bean.units != null) {
//                for (VideoPaperBean item : bean.units) {
//                    //1、天天配置文件有存在记录
//                    if (item.resId.equals(vid) && item.identifier.equals(subIdentifier)) {
//                        //2、对应的资源存在，且校验通过
//                        exists = FileUtil.isFileExits(getSubResourcePath(seriesId, vid, subIdentifier));
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return exists;
//    }
//
//    @Override
//    public boolean verifyResourceStrong(String vid, String identifier) {
//        String configFile = getConfigPath(vid, null);
//        VideoPaperSeriesBean bean = verifyConfig(FileUtil.readFileContent(configFile), true);
//        return bean != null;
//    }
//
//    /**
//     * 验证下属视频资源（强）
//     * @param seriesId
//     * @param vid
//     * @param subIdentifier
//     * @return
//     */
//    public boolean verifySubResourceStrong(String seriesId, String vid, String subIdentifier) {
//        String configFile = getConfigPath(seriesId, null);
//        boolean exists = false;
//        try {
//            String content = FileUtil.readFileContent(configFile);
//            content = NativeAssistant.decryptConfig(content);
//            JSONObject json = new JSONObject(content);
//            VideoPaperSeriesBean bean = parse(json);
//            if (bean != null && bean.units != null) {
//                for (VideoPaperBean item : bean.units) {
//                    //1、天天配置文件有存在记录
//                    if (item.resId.equals(vid) && item.identifier.equals(subIdentifier)) {
//                        //2、对应的资源存在，且校验通过
//                        exists = NativeAssistant.validateFileChecksum(item.md5, new File(getSubResourcePath(seriesId, vid, subIdentifier)));
//                        break;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return exists;
//    }
//
//    /**
//     * 验证下属信息（强）
//     * @param seriesId
//     * @param vid
//     * @param subIdentifier
//     * @return
//     */
//    public boolean verifySubStrong(String seriesId, String vid, String subIdentifier) {
//        String configFile = getConfigPath(seriesId, null);
//        VideoPaperSeriesBean bean = null;
//        try {
//            String content = FileUtil.readFileContent(configFile);
//            content = NativeAssistant.decryptConfig(content);
//            bean = parse(new JSONObject(content));
//            if (bean != null && bean.units != null && !bean.units.isEmpty()) {
//                int len = bean.units.size();
//                for (int i = 0; i < len; i++) {
//                    VideoPaperBean item = bean.units.get(i);
//                    if (vid.equals(item.resId) && subIdentifier.equals(item.identifier)) {
//                        return NativeAssistant.verifyBean(item, getSubWallpaperPath(seriesId, vid, subIdentifier), getSubResourcePath(seriesId, vid, subIdentifier));
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
//
//    public boolean verifySubWallpaperWeak(String seriesId, String vid, String subIndentifier) {
//        String wpPath = getSubWallpaperPath(seriesId, vid, subIndentifier);
//        return FileUtil.isFileExits(wpPath);
//    }
//
//    @Override
//    public VideoPaperSeriesBean verifyConfig(String content, boolean isStrict) {
//        if (TextUtils.isEmpty(content)) {
//            return null;
//        }
//        try {
//            content = NativeAssistant.decryptConfig(content);
//            JSONObject json = new JSONObject(content);
//            VideoPaperSeriesBean bean = parse(json);
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
//    public boolean verifyBase(VideoPaperSeriesBean bean, boolean isStrict) {
//        if (bean == null || bean.units == null || bean.units.isEmpty()) {
//            return false;
//        }
//
//        if (TextUtils.isEmpty(bean.thumbUri) || TextUtils.isEmpty(bean.resId) || TextUtils.isEmpty(bean.identifier)) {
//            return false;
//        }
//
//        int len = bean.units.size();
//        for (int i = 0; i < len; i++) {
//            VideoPaperBean item = bean.units.get(i);
//            boolean verifier;
//            if (isStrict) {
//                verifier = NativeAssistant.verifyBean(item, getSubWallpaperPath(bean.resId, item.resId, item.identifier), getSubResourcePath(bean.resId, item.resId, item.identifier));
//            } else {
//                verifier = FileUtil.isFileExits(getSubWallpaperPath(bean.resId, item.resId, item.identifier))
//                        && FileUtil.isFileExits(getSubResourcePath(bean.resId, item.resId, item.identifier));
//            }
//            if (!verifier) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public JSONObject format(VideoPaperSeriesBean bean) {
//        if (bean == null) {
//            return null;
//        }
//
//        if (bean.units == null || bean.units.isEmpty()) {
//            return null;
//        }
//
//        JSONObject json = new JSONObject();
//        try {
//            json.put("seriesId", bean.resId);
//            json.put("identifier", bean.identifier);
//            json.put("title", bean.title);
//            json.put("desc", bean.desc);
//            json.put("thumbUri", bean.thumbUri);
//            json.put("interval", bean.interval);
//            json.put("resType", VideoPaperSeriesBean.RES_TYPE_SERIES);
//
//            int unitCount = bean.units.size();
//            JSONArray jsonArray = new JSONArray();
//            for (int i = 0; i < unitCount; i++) {
//                VideoPaperBean item = bean.units.get(i);
//                JSONObject jsonItem = NativeAssistant.beanToJson(item);
//                if (jsonItem != null) {
//                    jsonArray.put(jsonItem);
//                }
//            }
//            json.put("data", jsonArray);
//        } catch (JSONException e) {
//            e.printStackTrace();
//            json = null;
//        }
//
//        return json;
//    }
//
//    @Override
//    public VideoPaperSeriesBean parse(JSONObject json) {
//        if (json == null) {
//            return null;
//        }
//        VideoPaperSeriesBean bean = new VideoPaperSeriesBean();
//        try {
//            bean.desc = json.optString("desc");
//            bean.interval = json.optInt("interval");
//            bean.resId = json.optString("seriesId");
//            bean.title = json.optString("title");
//            bean.thumbUri = json.optString("thumbUri");
//            bean.identifier = json.optString("identifier");
//            bean.resType = json.optInt("resType", VideoPaperSeriesBean.RES_TYPE_SERIES);
//            bean.units = new ArrayList<VideoPaperBean>();
//
//            JSONArray jsonArray = json.optJSONArray("data");
//            if (jsonArray != null && jsonArray.length() > 0) {
//                for (int i = 0, len = jsonArray.length(); i < len; i++) {
//                    JSONObject jsonBean = jsonArray.optJSONObject(i);
//                    VideoPaperBean item = NativeAssistant.jsonToBean(jsonBean);
//                    if (item != null) {
//                        bean.units.add(item);
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return bean;
//    }
//
//
//}
