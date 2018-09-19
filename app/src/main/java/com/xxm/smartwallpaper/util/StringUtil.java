/**
 * Version 1.0
 *
 * =============================================================
 * Revision History
 * 
 * Modification                    Tracking
 * Date           Author           Number      Description of changes
 * ----------     --------------   ---------   -------------------------
 * 2009-9-23         yangbin            代码重构
 */
package com.xxm.smartwallpaper.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;

import org.w3c.dom.Node;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 */
public final class StringUtil {

	/**
	 * IFLAG
	 */
	private static final String IFLAG = "i";

	/**
	 * ICONST
	 */
	private static final int ICONST = 971496;
	
	private static final String[] MD5_KEY_ARRAY = new String[] {
		"0F224B212E3A404098EBDB61CAA79804",
		"1B49D2C733BA49D195F21BF69B0F354F",
		"1C2D85268E8A4EAD8D45C7014EF62F75",
		"2FACACD15F224A36B140C263E62C1A31",
		"3C19D53124714CD3BEA580580909CC0B",
		"8B2EF1ACA7244D8E8FD93606808E1898",
		"9AEB4E5D3B36400EB0CD1A52EA5D1812",
		"23F9ED791394425BA316D6B35072ECA7",
		"34ED6DF271934286BB689DE8F94DEDB6" };

	/**
	 * 判断字符串是否为空
	 * @param s
	 * @return boolean
	 */
	public static boolean isEmpty(CharSequence s) {
		if ((s == null) || (s.length() <= 0)) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为空（null）
	 *
	 * @param s
	 * @return
	 */
	public static boolean isNull(CharSequence s) {
		return isEmpty(s) ? true : "null".equalsIgnoreCase(s.toString()) ? true : false;
	}
	
	/**
	 * 判断字符串是否为空
	 * @param strs
	 * @return boolean
	 */
	public static boolean isAnyEmpty(String... strs) {
		if (strs == null)
			return true;
		
		final int N = strs.length;
		for (int i = 0; i < N; i++) {
			if (isEmpty(strs[i]))
				return true;
		}
		
		return false;
	}

	/**
	 * 从通过column和cursor获取值
	 * @param cursor
	 * @param columnName
	 * @return String
	 */
	public static String getString(Cursor cursor, String columnName) {
		int index = cursor.getColumnIndex(columnName);
		if (index != -1) {
			return cursor.getString(index);
		}
		return null;
	}

	/**
	 * 判断两字符串是否相等
	 * @param s1
	 * @param s2
	 * @return boolean
	 */
	public static boolean equal(String s1, String s2) {
		if (s1 == s2) {
			return true;
		}
		if ((s1 != null) && (s2 != null)) {
			return s1.equals(s2);
		}
		return false;
	}

	/**
	 * 获取文件扩展名
	 * @param fileName
	 * @return String
	 */
	public static String getFileSuffix(String fileName) {
		int end = fileName.lastIndexOf('.');
		if (end >= 0) {
			return fileName.substring(end + 1);
		}
		return null;
	}

	/**
	 * 重命名文件，规则：.png => .a; .jpg => .b
	 * @param path
	 * @return String
	 */
	public static String renameRes(String path) {
		if (path == null) {
			return null;
		}
		return path.replace(".png", ".a").replace(".jpg", ".b");
	}

	/**
	 * 转null为空
	 * @param s
	 * @return String
	 */
	public static String getNotNullString(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}

	/**
	 * 解析int 
	 * @param str
	 * @param defaultValue 默认值
	 * @return int
	 */
	public static int parseInt(String str, int defaultValue) {
		try {
			return Integer.parseInt(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	/**
	 * 解析long
	 * @param str
	 * @param defaultValue 默认值
	 * @return long
	 */
	public static long parseLong(String str, Long defaultValue) {
		try {
			return Long.parseLong(str);
		} catch (Exception e) {
			return defaultValue;
		}
	}
	
	/**
	 * 文件大小转换，单位：B KB MB GB
	 * @param num
	 * @param scale 小数位精确值
	 * @return String
	 */
	public static String parseLongToKbOrMb(long num, int scale) {
		try {
			float scaleNum;
			switch (scale) {
			case 0:
				scaleNum = 1;
				break;
			case 1:
				scaleNum = 10f;
				break;
			case 2:
				scaleNum = 100f;
				break;
			case 3:
				scaleNum = 1000f;
				break;
			case 4:
				scaleNum = 10000f;
				break;
			default:
				scaleNum = 1;
			}
			float n = num;
			if (n < 1024) {
				return Math.round(n * scaleNum) / scaleNum + "B";
			}
			n = n / 1024;
			if (n < 1024) {
				return Math.round(n * scaleNum) / scaleNum + "KB";
			}
			n = n / 1024;
			if (n < 1024) {
				return Math.round(n * scaleNum) / scaleNum + "MB";
			}
			n = n / 1024;
			return Math.round(n * scaleNum) / scaleNum + "GB";
		} catch (Exception e) {
			e.printStackTrace();
			return -1 + "B";
		}
	}
    
	/**
	 * 将毫秒级的时间转换为天数
	 * @param l
	 * @return String
	 */
	public static String parseLongToTime(long l) {
		StringBuffer sb = new StringBuffer();
		try {
			int d = (int) l / (24 * 60 * 60 * 1000);
			long rest = l % (24 * 60 * 60 * 1000);
			int h = (int) rest / (60 * 60 * 1000);
			rest = rest % (60 * 60 * 1000);
			int m = (int) rest / (60 * 1000);
			rest = rest % (60 * 1000);
			int s = (int) rest / 1000;
			rest = rest * 1000;
			sb.append(d + "d" + h + "h" + m + "m" + s + "s");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String parseShortNum(long num){
		if (num / 10000 == 0) {
			return "" + num;
		} else if (num / 10000000 == 0) {
			return new DecimalFormat("#.#").format(num / (10000 * 1.0d)) + "万";
		} else if (num / 100000000L == 0) {
			return new DecimalFormat("#.#").format(num / (10000000L * 1.0d)) + "千万";
		} else {
			return new DecimalFormat("#.#").format(num / (100000000L * 1.0d)) + "亿";
		}
	}

	/**
	 * 获得节点text
	 * @param node
	 * @return String
	 */
	public static String getNodeText(Node node) {
		String text = null;
		Node tNode = node.getFirstChild();
		if ((tNode != null) && "#text".equals(tNode.getNodeName())) {
			text = tNode.getNodeValue();
			if (text != null) {
				text = text.trim();
			}
		}
		return text;
	}

	/**
	 * 判断传入的字符串是否是数字类型
	 * @param sNum
	 * @return boolean
	 */
	public static boolean isNumberic(String sNum) {
		try {
			Float.parseFloat(sNum);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	 * 验证注册名称是否正确
	 * @param str
	 * @return boolean
	 */
	public static boolean checkOnlyContainCharaterAndNumbers(String str) {
		Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
		Matcher m = p.matcher(str);
		return m.matches();
	}

	/**
	 * 验证邮件地址格式是否正确
	 * @param str
	 * @return boolean
	 */
	public static boolean checkValidMailAddress(String str) {
		Pattern p1 = Pattern.compile("\\w+@(\\w+\\.)+[a-z]{2,3}");
		Matcher m = p1.matcher(str);
		return m.matches();
	}
	
	/**
	 * 验证是否正常
	 * @param str
	 * @return boolean
	 */
	public static boolean checkValidMobilePhoneNumber(String str) {
		Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$");
		Matcher m = p.matcher(str);
		return m.matches(); 
	}

	/**
	 * 获取非null的字符串
	 * @param str
	 * @return 为null则返回空字符串，否则返回字符串本身
	 */
	public static String checkString(String str) {
		if (null == str)
			return "";
		return str;
	}

	/**
	 * OpenHome key
	 * @param packname
	 * @param skey
	 * @return String
	 */
	public static String parseKey(String packname, String skey) {
		return IFLAG + (Math.abs(packname.hashCode() + skey.hashCode() + ICONST));
	}

	/**
	 * OpenHome 2.6.2版本的key
	 * @param actName
	 * @param packName
	 * @param id
	 * @return String
	 */
	public static String parseKeyNew(String actName, String packName, String id) {
		actName = actName.toLowerCase();
		packName = packName.toLowerCase();
		id = id.toLowerCase();
		int ret = id.hashCode() + (-25);
		try {
			char[] buf = new char[actName.length()]; // v0
			for (int i = 0; i < buf.length; i++) {
				buf[i] = (char) (actName.toCharArray()[i] << 2);
			}

			for (int i = 0; i < buf.length; i++) {
				if (i % buf[i] == 0) {
					ret += buf[i];
				} else {
					ret -= buf[i];
				}
			}
			ret = ret << 2;
			ret = ret >> 3;
			char[] buf2 = new char[packName.length()];
			for (int i = 0; i < buf2.length - 2; i++) {
				buf2[i] = (char) ((packName.toCharArray()[i] + buf[i % buf.length]) << 3);
				if ((buf2[i] * 2 - buf[i] * 2) % 2 == 0) {
					ret += buf2[i];
				} else {
					ret -= buf2[i];
				}
			}
			ret = Math.abs(ret);
		} catch (Exception e) {
			return "";
		}
		return "i" + ret;
	}

	/**
	 * 根据值获取map中的key
	 * @param map
	 * @param value
	 * @return List<String>
	 */
	public static List<String> getKeysByValue(HashMap<String, String> map, String value) {
		ArrayList<String> list = new ArrayList<String>();
		Iterator<String> it = map.keySet().iterator();
		while (it.hasNext()) {
			String k = it.next();
			String v = map.get(k);
			if (equal(v, value)) {
				list.add(k);
			}
		}
		return list;
	}

	/**
	 * 根据ResolveInfo获取AppKey
	 * @param info
	 * @return String
	 */
	public static String getAppKey(ResolveInfo info) {
		String appKey = (info.activityInfo.packageName + "|" + info.activityInfo.name).toLowerCase();
		return appKey;
	}

	/**
	 * 根据ComponentName获取AppKey
	 * @param info
	 * @return String
	 */
	public static String getAppKey(ComponentName info) {
		String appKey = (info.getPackageName() + "|" + info.getClassName()).toLowerCase();
		return appKey;
	}
	
	/**
	 * 根据pck,class获取AppKey
	 * @param pck
	 * @param clazz
	 * @return String
	 */
	public static String getAppKey(String pck, String clazz) {
		String appKey = (pck.trim() + "|" + clazz.trim()).toLowerCase();
		return appKey;
	}

	/**
	 * 根据ActivityInfo获取AppKey
	 * @param info
	 * @return String
	 */
	public static String getAppKey(ActivityInfo info) {
		String appKey = (info.packageName + "|" + info.name).toLowerCase();
		return appKey;
	}

	/**
	 * 文件后缀名过滤
	 * @param context
	 * @param name
	 * @param arrayId
	 * @return boolean
	 */
	public static boolean checkSuffixsWithInStringArray(Context context, String name, int arrayId) {
		String[] fileEndings = context.getResources().getStringArray(arrayId);
		for (String aEnd : fileEndings) {
			if (name.toLowerCase().endsWith(aEnd))
				return true;
		}
		return false;
	}

	/**
	 * 过滤掉插入字符串中有特殊字符的字符换，返回合法的字符串
	 * @param srcParam
	 * @return String
	 */
	public static String filtrateInsertParam(CharSequence srcParam) {
		if (StringUtil.isEmpty(srcParam)) {
			return "";
		}
		String result = srcParam.toString().replace("'", "''");
		result = result.replace("?", "");
		return result;
	}
	
	/**
	 * 获取随即的加密串
	 * @param index
	 * @return String
	 */
	public static String getMD5Key(int index){
		return MD5_KEY_ARRAY[index] ;
	}

	/**
	 * 获取随机数，0 和 max之间 ，不包含max
	 * @param max
	 * @return int
	 */
	public static int getRandom(int max){
		return Integer.parseInt(String.valueOf(System.currentTimeMillis() % max)) ;
	}
	
	/**
	 * 获取包名
	 * @param component
	 * @return String
	 */
	public static String getPkgName(String component){
		return component.substring(component.indexOf("{")+1, component.lastIndexOf("/"));
	}
	
	/**
	 * 获取画笔文字大小
	 * @param paint
	 * @return float
	 */
	public static float getFontHeight(Paint paint) {
		Paint.FontMetrics localFontMetrics = paint.getFontMetrics();
		return (float) Math.ceil(localFontMetrics.descent - localFontMetrics.ascent);
	}
	
	/**
	 * 获取一个汉字的Rect
	 * @param paint
	 * @param outRect
	 */
	public static void getChineseFontSize(Paint paint , Rect outRect) {
		paint.getTextBounds("桌", 0, 1, outRect);
	}
	
	/**
	 * 绘制文本，自动换行
	 * @param canvas
	 * @param paint
	 * @param text
	 * @param width
	 * @param paddingTop
	 * @param paddingLeft
	 */
	public static void drawText(Canvas canvas, Paint paint, String text, int width, int paddingTop , int paddingLeft) {
		int height = paint.getFontMetricsInt(null);
		int verticalSpace = 5 ;
		int lenght = text.length() ;
		int validateLength = paint.breakText(text, true, width, null);
		int count = (lenght - 1) / validateLength + 1;
		int start = 0 ;
		int top = paddingTop ;
		for (int i = 0 ; i < count ; i++) {
			int end = start + validateLength ;
			if (end >= lenght) {
				end = lenght ;
			}
			canvas.drawText(text.substring(start, end), paddingLeft, top + i * (verticalSpace + height), paint);
			start = end ;
		}
	}
	
	/**
	 * 转换ARGB色值，格式如#FFFFFF
	 * @param color
	 * @return String
	 */
	public static String Color2String(int color) {
		String A = "";
		String R = "";
		String G = "";
		String B = "";
		try{
			A = Integer.toHexString(Color.alpha(color));
			A = A.length()<2?('0'+A):A;
			R = Integer.toHexString(Color.red(color));
			R = R.length()<2?('0'+R):R;
			G = Integer.toHexString(Color.green(color));
			G = G.length()<2?('0'+G):G;
			B = Integer.toHexString(Color.blue(color));
			B = B.length()<2?('0'+B):B;
		}catch(Exception e){
			return "#FFFFFF";
		}
		return '#'+A+R+G+B;  
	}  
	
	/**
	 * 去除特殊字符：$^*()-+{}|.?[]&\_
	 * @param input
	 * @return String
	 */
	public static String regularSymbolFilter(String input) {
		if (TextUtils.isEmpty(input))
			return "";
		
		return input.replaceAll("\\$|\\^|\\*|\\(|\\)|\\-|\\+|\\{|\\}|\\||\\.|\\?|\\[|\\]|\\&|\\\\|\\_", "");
	}
	
	/**
	 * 为URL参数做UTF-8编码
	 * @param value
	 * @return String
	 */
	public static String encodeUrlParams(String value){
		String returnValue = "";
		try {
			value = URLEncoder.encode(value, "UTF-8");
			returnValue = value.replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return returnValue;
	}
	
	/**
	 * 主题数据特殊字符转换：. => _ ;  | => _
	 * @param appKey
	 * @return String
	 */
	public static String renameThemeData(String appKey) {
		if (appKey == null) {
			return null;
		}
		return appKey.replace(".", "_").replace("|", "_");
	}
	
	/**
	 * 获取包名的最后一个单词
	 * @param packageName
	 * @return String
	 */
	public static String getLastPackageName(String packageName)
	{
		String name="";
		if(packageName!=null)
		{			
			String[] ss=packageName.split("\\.");
			if(ss.length>0)
				name=ss[ss.length-1];
		}
		return name;
	}
	
	/**
     * 去除UTF-8格式BOM头
     * 
     * @return
     */
    public static String removeBomHeader(String s) {
    	if (s != null && s.startsWith("\ufeff")) {
			return s.substring(1);
		}
    	return s;
    }
    
    /**
     * <br>Description: 使用java正则表达式去掉多余的.与0 
     * <br>Author:caizp
     * <br>Date:2015年5月6日下午4:53:57
     * @param str
     * @return
     */
    public static String subZeroAndDot(String str){
    	if(TextUtils.isEmpty(str))return str;
        if(str.indexOf(".") > 0){  
        	str = str.replaceAll("0+?$", "");//去掉多余的0  
        	str = str.replaceAll("[.]$", "");//如最后一位是.则去掉  
        }  
        return str;  
    }
	public static final String EMPTY = "";
	public static String trim(String str) {
		return str == null ? EMPTY : str.trim();
	}
	
}
