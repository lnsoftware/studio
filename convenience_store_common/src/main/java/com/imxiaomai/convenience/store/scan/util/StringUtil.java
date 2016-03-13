package com.imxiaomai.convenience.store.scan.util;

import com.imxiaomai.convenience.store.scan.constants.HttpConstant;
import com.imxiaomai.convenience.store.scan.manager.AccountManager;

import java.util.regex.Pattern;

public class StringUtil {
	public static final String DECIMAL_EQUALS_ZERO  = "^[0-9]+(.[0]{2})$";
	public static final String NUM_REG  = "^[0-9]+$";
	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String doEmpty(String str) {
		return doEmpty(str, "");
	}

	/**
	 * 处理空字符串
	 * 
	 * @param str
	 * @param defaultValue
	 * @return String
	 */
	public static String doEmpty(String str, String defaultValue) {
		if (str == null || str.equalsIgnoreCase("null")
				|| str.trim().equals("") || str.trim().equals("－请选择－")) {
			str = defaultValue;
		} else if (str.startsWith("null")) {
			str = str.substring(4, str.length());
		}
		return str.trim();
	}

	/**
	 * 请选择
	 */
	final static String PLEASE_SELECT = "请选择...";

	public static boolean notEmpty(Object o) {
		return o != null && !"".equals(o.toString().trim())
				&& !"null".equalsIgnoreCase(o.toString().trim())
				&& !"undefined".equalsIgnoreCase(o.toString().trim())
				&& !PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean empty(Object o) {
		return o == null || "".equals(o.toString().trim())
				|| "null".equalsIgnoreCase(o.toString().trim())
				|| "undefined".equalsIgnoreCase(o.toString().trim())
				|| PLEASE_SELECT.equals(o.toString().trim());
	}

	public static boolean num(Object o) {
		int n = 0;
		try {
			n = Integer.parseInt(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean decimal(Object o) {
		double n = 0;
		try {
			n = Double.parseDouble(o.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		if (n > 0.0) {
			return true;
		} else {
			return false;
		}
	}


	public static String nullConvert(String source) {
		if(source == null || "null".equals(source)) {
			return "";
		} else {
			return source;
		}
	}
	
	public static boolean decimalEqualsZero(String str) {
		Pattern pattern = Pattern.compile(DECIMAL_EQUALS_ZERO);
		return pattern.matcher(str).matches();
	}
	
	public static String urlParse(String url,String ...args) {
		long ct = System.currentTimeMillis();
		String cbToken = MD5Util.md5AsLowerHex(ct + "cb");
		String warehouseCode = AccountManager.getInstance().getShopCode();
		
		String urlStart = String.format(url, new Object[]{args});
		String newUrl = urlStart + HttpConstant.END_SUFFIX;
		
		return String.format(newUrl, String.valueOf(ct), cbToken, warehouseCode);
	}
	
	public static int toInt(String str) {
		if(empty(str)) {
			return 0;
		}
		Pattern pattern = Pattern.compile(NUM_REG);
		if(pattern.matcher(str).find()) {
			return new Integer(str).intValue();
		} 
		return 0;
	}
}
