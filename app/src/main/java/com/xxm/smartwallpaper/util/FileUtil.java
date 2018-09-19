package com.xxm.smartwallpaper.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;


/**
 * 文件相关的工具类
 */
public class FileUtil {

    private static final String TAG = "FileUtil";

    /**
     * 根据字节内容生成新文件
     *
     * @param file
     * @param datas
     * @return boolean
     */
    public static boolean generateFile(File file, List<byte[]> datas) {
        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            for (byte[] data : datas) {
                bos.write(data);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 往现有文件中追加数据
     *
     * @param file
     * @param datas
     * @return boolean
     */
    public static boolean appendData(File file, byte[]... datas) {
        RandomAccessFile rfile = null;
        try {
            rfile = new RandomAccessFile(file, "rw");
            rfile.seek(file.length());
            for (byte[] data : datas) {
                rfile.write(data);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rfile != null) {
                try {
                    rfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * 创建文件夹
     *
     * @param dir
     */
    public static void createDir(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            f.mkdirs();
        }

    }

    /**
     * 往创建文件，并往文件中写内容
     *
     * @param path
     * @param content
     * @param append
     */
    public static boolean writeFile(String path, String content, boolean append) {
        boolean res = true;
        try {
            File f = new File(path);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            if (!f.exists()) {
                f.createNewFile();
                f = new File(path); // 重新实例化
            }
            FileWriter fw = new FileWriter(f, append);
            if ((content != null) && !"".equals(content)) {
                fw.write(content);
                fw.flush();
            }
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
            res = false;
        }

        return res;
    }

    /**
     * 删除某个文件
     *
     * @param path 文件路径
     */
    public static boolean delFile(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @desc 先重命名文件，再删除，避免部分机型出现的EBUSY异常
     * @author linliangbin
     * @time 2017/7/13 17:55
     */
    public static boolean delFileSafely(String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                String tempPath = f.getParent() + File.separator + System.currentTimeMillis();
                File tempFile = new File(tempPath);
                f.renameTo(tempFile);
                tempFile.delete();
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 删除文件夹
     *
     * @param folderPath
     */
    public static void delFolder(final String folderPath) {
        try {
            delAllFile(folderPath); // 删除完里面所有内容
            delFile(folderPath);// 删除空文件夹
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除path 下全部文件
     *
     * @param path
     * @return true删除成功
     */
    public static boolean delAllFile(String path) {
        return delAllFile(path, null);
    }


    /**
     * 删除文件夹内所有文件
     *
     * @param path
     * @param filenameFilter 过滤器 支持null
     * @return boolean
     */
    public static boolean delAllFile(String path, FilenameFilter filenameFilter) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (!file.isDirectory()) {
            return flag;
        }
        File[] tempList = file.listFiles(filenameFilter);

        if (tempList == null) return true;

        int length = tempList.length;
        for (int i = 0; i < length; i++) {

            if (tempList[i].isFile()) {
                tempList[i].delete();
            }
            if (tempList[i].isDirectory()) {
                /**
                 * 删除内部文件
                 */
                delAllFile(tempList[i].getAbsolutePath(), filenameFilter);
                /**
                 * 删除空文件夹
                 */
                String[] ifEmptyDir = tempList[i].list();
                if (null == ifEmptyDir || ifEmptyDir.length <= 0) {
                    tempList[i].delete();
                }
                flag = true;
            }
        }
        return flag;
    }


    /**
     * 文件复制类
     *
     * @param srcFile  源文件
     * @param destFile 目标文件
     * @return boolean
     */
    public static boolean copy(String srcFile, String destFile) {
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(srcFile);
            out = new FileOutputStream(destFile);
            byte[] bytes = new byte[1024];
            int c;
            while ((c = in.read(bytes)) != -1) {
                out.write(bytes, 0, c);
            }
            out.flush();
            return true;
        } catch (Exception e) {
            System.out.println("Error!" + e);
            return false;
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != out) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     */
    public static void copyFolder(String oldPath, String newPath) {
        (new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
        File a = new File(oldPath);
        String[] file = a.list();
        if (null == file)
            return;
        File temp = null;
        for (int i = 0; i < file.length; i++) {
            try {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }

                if (temp.isFile()) {
                    FileInputStream input = new FileInputStream(temp);
                    FileOutputStream output = new FileOutputStream(newPath + "/" + (temp.getName()).toString());
                    byte[] b = new byte[1024 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                }
                if (temp.isDirectory()) {// 如果是子文件夹
                    copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 移动文件到指定目录
     *
     * @param oldPath String 如：c:/fqf.txt
     * @param newPath String 如：d:/fqf.txt
     */
    public static void moveFile(String oldPath, String newPath) {
        copy(oldPath, newPath);
        delFile(oldPath);

    }

    /**
     * 重命名文件或文件夹
     *
     * @param resFilePath 源文件路径
     * @param newFilePath 重命名
     * @return 操作成功标识
     */
    public static boolean renameFile(String resFilePath, String newFilePath) {
        File resFile = new File(resFilePath);
        File newFile = new File(newFilePath);
        return resFile.renameTo(newFile);
    }

    /**
     * 移动文件夹到指定目录
     *
     * @param oldPath String 如：c:/fqf
     * @param newPath String 如：d:/fqf
     */
    public static void moveFolder(String oldPath, String newPath) {
        copyFolder(oldPath, newPath);
        delFolder(oldPath);
    }

    /**
     * 将输入流保存为文件
     *
     * @param in
     * @param fileName 文件名称
     */
    public static void saveStream2File(InputStream in, String fileName) {
        int size;
        byte[] buffer = new byte[1000];
        BufferedOutputStream bufferedOutputStream = null;
        try {
            bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(new File(fileName)));
            while ((size = in.read(buffer)) > -1) {
                bufferedOutputStream.write(buffer, 0, size);
            }
            bufferedOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 图片文件过滤器
     */
    public static FileFilter imagefileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            String tmp = pathname.getName().toLowerCase();
            if (tmp.endsWith(".png") || tmp.endsWith(".jpg") || tmp.endsWith(".bmp") || tmp.endsWith(".gif") || tmp.endsWith(".jpeg")) {
                return true;
            }
            return false;
        }
    };

    /**
     * 文件夹过滤器
     */
    public static FileFilter folderFilter = new FileFilter() {
        public boolean accept(File pathname) {
            return pathname.isDirectory();
        }
    };

    /**
     * 铃声文件过滤器
     */
    public static FileFilter mp3fileFilter = new FileFilter() {
        public boolean accept(File pathname) {
            String tmp = pathname.getName().toLowerCase();
            if (tmp.endsWith(".mp3")) {
                return true;
            }
            return false;
        }
    };

    public static File[] getFilesFromDir(String dirPath, FileFilter fileFilter) {
        File dir = new File(dirPath);
        if (dir.isDirectory()) {
            if (fileFilter != null)
                return dir.listFiles(fileFilter);
            else
                return dir.listFiles();
        }
        return null;
    }

    /**
     * 获取已存在的文件名列表
     *
     * @param dir        目录
     * @param fileFilter 过滤器
     * @param hasSuffix  是否包括后缀
     * @return List<String>
     */
    public static List<String> getExistsFileNames(String dir, FileFilter fileFilter, boolean hasSuffix) {
        String path = dir;
        File file = new File(path);
        File[] files = file.listFiles(fileFilter);
        List<String> fileNameList = new ArrayList<String>();
        if (null != files) {
            for (File tmpFile : files) {
                String tmppath = tmpFile.getAbsolutePath();
                String fileName = getFileName(tmppath, hasSuffix);
                fileNameList.add(fileName);
            }
        }
        return fileNameList;
    }

    /**
     * 获取已存在的文件名列表 (包括子目录)
     *
     * @param dir       目录
     * @param hasSuffix 是否包括后缀
     * @return List<String>
     */
    public static List<String> getAllExistsFileNames(String dir, boolean hasSuffix) {
        String path = dir;
        File file = new File(path);
        File[] files = file.listFiles();
        List<String> fileNameList = new ArrayList<String>();
        if (null != files) {
            for (File tmpFile : files) {
                if (tmpFile.isDirectory()) {
                    fileNameList.addAll(getAllExistsFileNames(tmpFile.getPath(), hasSuffix));
                } else {
                    String tmp = tmpFile.getName().toLowerCase();
                    if (tmp.endsWith(".png") || tmp.endsWith(".jpg") || tmp.endsWith(".bmp") || tmp.endsWith(".gif") || tmp.endsWith(".jpeg")) {
                        fileNameList.add(tmpFile.getAbsolutePath());
                    }
                }
            }
        }
        return fileNameList;
    }

    /**
     * 从路径中获取 文件名
     *
     * @param path
     * @param hasSuffix 是否包括后缀
     * @return String
     */
    public static String getFileName(String path, boolean hasSuffix) {
        if (null == path || -1 == path.lastIndexOf("/") || -1 == path.lastIndexOf("."))
            return null;
        if (!hasSuffix)
            return path.substring(path.lastIndexOf("/") + 1, path.lastIndexOf("."));
        else
            return path.substring(path.lastIndexOf("/") + 1);
    }

    /**
     * 获取目录
     *
     * @param path
     * @return String
     */
    public static String getPath(String path) {
        File file = new File(path);

        try {
            if (!file.exists() || !file.isDirectory())
                file.mkdirs();
            return file.getAbsolutePath();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 指定目录下是否存在指定名称的文件
     *
     * @param dir      目录
     * @param fileName 文件名称
     * @return boolean
     */
    public static boolean isFileExits(String dir, String fileName) {
        fileName = fileName == null ? "" : fileName;
        dir = dir == null ? "" : dir;
        int index = dir.lastIndexOf("/");
        String filePath;
        if (index == dir.length() - 1)
            filePath = dir + fileName;
        else
            filePath = dir + "/" + fileName;
        File file = new File(filePath);
        return file.exists();
    }

    /**
     * 指定路么下是否存在文件
     *
     * @param filePath 文件路径
     * @return boolean
     */
    public static boolean isFileExits(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists())
                return true;
        } catch (Exception e) {

        }
        return false;
    }

    /**
     * 保存图片
     *
     * @param dirPath
     * @param fileName
     * @param bmp
     * @return boolean
     */
    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp) {
        try {
            File dir = new File(dirPath);

            // 目录不存时创建目录
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (flag == false)
                    return false;
            }

            // 未指定文件名时取当前毫秒作为文件名
            if (fileName == null || fileName.trim().length() == 0)
                fileName = System.currentTimeMillis() + ".jpg";
            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 保存图片,带保存格式：jpg\png
     *
     * @param dirPath
     * @param fileName
     * @param bmp
     * @param format   保存的格式
     * @return boolean
     */
    public static boolean saveImageFile(String dirPath, String fileName, Bitmap bmp, Bitmap.CompressFormat format) {
        try {
            File dir = new File(dirPath);

            // 目录不存时创建目录
            if (!dir.exists()) {
                boolean flag = dir.mkdirs();
                if (flag == false)
                    return false;
            }

            format = format == null ? Bitmap.CompressFormat.JPEG : format;
            // 未指定文件名时取当前毫秒作为文件名
            if (fileName == null || fileName.trim().length() == 0) {
                fileName = System.currentTimeMillis() + "";
                if (format.equals(Bitmap.CompressFormat.PNG))
                    fileName += ".png";
                else
                    fileName += ".jpg";
            }
            File picPath = new File(dirPath, fileName);
            FileOutputStream fos = new FileOutputStream(picPath);
            bmp.compress(format, 100, fos);
            fos.flush();
            fos.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 获取文件夹总大小 B单位
     *
     * @param path
     * @return long
     */
    public static long getFileAllSize(String path) {
        try {
            File file = new File(path);
            if (file.exists()) {
                if (file.isDirectory()) {
                    File[] children = file.listFiles();
                    long size = 0;
                    for (File f : children)
                        size += getFileAllSize(f.getPath());
                    return size;
                } else {
                    long size = file.length();
                    return size;
                }
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return String
     */
    public static String readFileContent(String path) {
        StringBuffer sb = new StringBuffer();
        if (!isFileExits(path)) {
            return sb.toString();
        }
        InputStream ins = null;
        try {
            ins = new FileInputStream(new File(path));
            BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    /**
     * Description: 读取asset下文件内容
     * Author: guojianyun_91
     * Date: 2015年4月9日 下午2:05:48
     *
     * @param ctx
     * @param path assets下文件相对路径
     * @return
     */
    public static String readAssetsContent(Context ctx, String path) {
        StringBuffer sb = new StringBuffer();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(ctx.getAssets().open(path)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


    /**
     * 转换为文件大小，单位：B KB MB GB TB
     *
     * @param size
     * @return String
     */
    public static String getMemorySizeString(long size) {
        float result = size;
        if (result < 1024) {
            BigDecimal temp = new BigDecimal(result);
            temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
            return temp + "Bytes";
        } else {
            result = result / 1024;
            if (result < 1024) {
                BigDecimal temp = new BigDecimal(result);
                temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                return temp + "KB";
            } else {
                result = result / 1024;
                if (result < 1024) {
                    BigDecimal temp = new BigDecimal(result);
                    temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                    return temp + "MB";
                } else {
                    result = result / 1024;
                    if (result < 1024) {
                        BigDecimal temp = new BigDecimal(result);
                        temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                        return temp + "GB";
                    } else {
                        result = result / 1024;
                        BigDecimal temp = new BigDecimal(result);
                        temp = temp.setScale(2, BigDecimal.ROUND_HALF_UP);
                        return temp + "TB";
                    }
                }
            }
        }
    }

    /**
     * 转换成百分比格式%
     *
     * @param percent
     * @return String
     */
    public static String getMemoryPercentString(float percent) {
        BigDecimal result = new BigDecimal(percent * 100.0f);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP) + "%";
    }


    /**
     * 从assets目录复制文件到指定路径
     *
     * @param context
     * @param srcFileName    复制的文件名
     * @param targetDir      目标目录
     * @param targetFileName 目标文件名
     * @return boolean
     */
    public static boolean copyAssetsFile(Context context, String srcFileName, String targetDir, String targetFileName) {
        AssetManager asm = null;
        FileOutputStream fos = null;
        DataInputStream dis = null;
        try {
            asm = context.getAssets();
            dis = new DataInputStream(asm.open(srcFileName));
            createDir(targetDir);
            File targetFile = new File(targetDir, targetFileName);
            if (targetFile.exists()) {
                targetFile.delete();
            }

            fos = new FileOutputStream(targetFile);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = dis.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
            }
            fos.flush();
            return true;
        } catch (Exception e) {
            Log.w(TAG, "copy assets file failed:" + e.toString());
        } finally {
            try {
                if (fos != null)
                    fos.close();
                if (dis != null)
                    dis.close();
            } catch (Exception e2) {
            }
        }

        return false;
    }//end copyAssetsFile


    public static boolean downloadFileByURL(String downloadUrl, String localPath) {
        File f = null;
        InputStream is = null;
        OutputStream os = null;
        try {
            f = new File(localPath);
            if (!f.exists()) {
                URL url = new URL(downloadUrl);
                URLConnection con = url.openConnection();
                con.setConnectTimeout(8 * 1000);
                con.setRequestProperty(
                        "User-Agent",
                        "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2) Gecko/20100115 Firefox/3.6");
                is = con.getInputStream();
                if (con.getContentEncoding() != null
                        && con.getContentEncoding().equalsIgnoreCase("gzip")) {
                    is = new GZIPInputStream(con.getInputStream());
                }
                byte[] bs = new byte[2048];
                int len = -1;
                os = new FileOutputStream(f);
                while ((len = is.read(bs)) != -1) {
                    os.write(bs, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            if (f != null && f.exists()) {
                FileUtil.delFile(f.getAbsolutePath());
            }
            return false;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * <br>Description: 创建文件，并往文件中写入对象
     * <br>Author:caizp
     * <br>Date:2015年4月23日下午4:20:03
     *
     * @param filePath
     * @param object
     * @return
     */
    public static boolean writeObjectToFile(String filePath, Object object) {
        FileOutputStream output = null;
        ObjectOutputStream oos = null;
        try {
            File f = new File(filePath);
            if (!f.getParentFile().exists()) {
                f.getParentFile().mkdirs();
            }
            // 删除原文件
            if (f.exists()) f.delete();
            output = new FileOutputStream(f);
            oos = new ObjectOutputStream(output);
            oos.writeObject(object);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != output) {
                    output.close();
                }
                if (null != oos) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * <br>Description: 从文件中读取对象
     * <br>Author:caizp
     * <br>Date:2015年4月23日下午4:20:03
     *
     * @param filePath
     * @return
     */
    public static Object readObjectFromFile(String filePath) {
        FileInputStream input = null;
        ObjectInputStream ois = null;
        try {
            File f = new File(filePath);
            if (!f.exists()) {
                return null;
            }
            input = new FileInputStream(f);
            ois = new ObjectInputStream(input);
            return ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != input) {
                    input.close();
                }
                if (null != ois) {
                    ois.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Description: 保存图片到系统图库
     *
     * @param ctx
     * @return
     */
    public static Uri saveToSystemMediaStore(Context ctx, String dir, String fileName) {
        long mImageTime = System.currentTimeMillis();
        long dateSeconds = mImageTime / 1000;
        ContentValues values = new ContentValues();
        ContentResolver resolver = ctx.getContentResolver();
        values.put(MediaStore.Images.ImageColumns.DATA, dir + "/" + fileName);
        values.put(MediaStore.Images.ImageColumns.TITLE, fileName);
        values.put(MediaStore.Images.ImageColumns.DISPLAY_NAME, fileName);
        values.put(MediaStore.Images.ImageColumns.DATE_TAKEN, mImageTime);
        values.put(MediaStore.Images.ImageColumns.DATE_ADDED, dateSeconds);
        values.put(MediaStore.Images.ImageColumns.DATE_MODIFIED, dateSeconds);
        values.put(MediaStore.Images.ImageColumns.MIME_TYPE, "image/jpg");
        values.put(MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME, "FelinkPhoto");
        Uri uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        return uri;
    }


    /**
     * @desc 获取URI对应的真实路径
     * @author linliangbin
     * @time 2017/3/8 18:32
     */
    public static String getRealFilePath(final Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }


    /**
     * @desc 删除图片在图库中的记录
     * @author linliangbin
     * @time 2017/3/13 9:46
     */
    public static void deleteImageFile(Context context, String imgPath) {
        ContentResolver resolver = context.getContentResolver();
        Cursor cursor = MediaStore.Images.Media.query(resolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=?",
                new String[]{imgPath}, null);
        boolean result = false;
        if (cursor.moveToFirst()) {
            long id = cursor.getLong(0);
            Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Uri uri = ContentUris.withAppendedId(contentUri, id);
            int count = context.getContentResolver().delete(uri, null, null);
            result = count == 1;
        } else {
            File file = new File(imgPath);
            result = file.delete();
        }

    }


}
