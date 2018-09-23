package com.alphasta.common.core.util;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 * String Utility Class This is used to encode passwords programmatically
 * 
 * <p>
 * <a h ref="StringUtil.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 */
public final class StringUtil {
	// ~ Static fields/initializers ==============================
	/**
	 * prevent from initializing.
	 */
	private StringUtil() {

	}

	/**
	 * EMPTY_STRING
	 */
	public final static String EMPTY_STRING = "";

	/**
	 * Encode a string using Base64 encoding. Used when storing passwords as
	 * cookies.
	 * 
	 * This is weak encoding in that anyone can use the decodeString routine to
	 * reverse the encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String encodeString(String str) {
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		return encoder.encodeBuffer(str.getBytes()).trim();
	}

	/**
	 * Decode a string using Base64 encoding.
	 * 
	 * @param str
	 * @return String
	 */
	public static String decodeString(String str) {
		sun.misc.BASE64Decoder dec = new sun.misc.BASE64Decoder();
		try {
			return new String(dec.decodeBuffer(str));
		} catch (IOException io) {
			throw new RuntimeException(io.getMessage(), io.getCause());
		}
	}

	/**
	 * 取得完整类名的最后一截，也就是类本身名称。例如，输入java.lang.Object,则返回Object
	 */
	public static String unqualify(String qualifiedName) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(".") + 1);
	}

	/**
	 * 例如，输入java.lang.Object,则返回java.lang
	 */
	public static String qualifier(String qualifiedName) {
		int loc = qualifiedName.lastIndexOf(".");
		return (loc < 0) ? "" : qualifiedName.substring(0, loc);
	}

	/**
	 * 组成完整类名
	 */
	public static String qualify(String prefix, String name) {
		if (name == null || prefix == null) {
			throw new NullPointerException();
		}
		return new StringBuffer(prefix.length() + name.length() + 1).append(
				prefix).append('.').append(name).toString();
	}

	/**
	 * 组成完整类名
	 */
	public static String[] qualify(String prefix, String[] names) {
		if (prefix == null) {
			return names;
		}
		int len = names.length;
		String[] qualified = new String[len];
		for (int i = 0; i < len; i++) {
			qualified[i] = qualify(prefix, names[i]);
		}
		return qualified;
	}

	/**
	 * 去除字符串中的&lt;和&gt;标记.
	 * @param src 给定源字符串
	 * @return 去除tags后的字符串。
	 */
	public static String stripTags(String src) {
		if (StringUtils.isBlank(src)) {
			return src;
		}
		return src.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
  /**
   * 解析以comma分隔的字符串，并去掉其中不必要的字符，最后以Set返回
   * 
   */
  @SuppressWarnings("unchecked")
  public static Set parseCommaSplitedString(String string) {
    final Set results = new HashSet();
    final String[] splited = org.springframework.util.StringUtils
        .commaDelimitedListToStringArray(string);

    for (int i = 0; i < splited.length; i++) {
      String sub = splited[i];

      // Remove the role's whitespace characters without depending on JDK 1.4+
      // Includes space, tab, new line, carriage return and form feed.
      String trimed = sub.trim(); // trim, don't use spaces, as per SEC-378
      trimed = StringUtils.replace(trimed, "\t", "");
      trimed = StringUtils.replace(trimed, "\r", "");
      trimed = StringUtils.replace(trimed, "\n", "");
      trimed = StringUtils.replace(trimed, "\f", "");

      results.add(trimed);
    }

    return results;
  }
  
  /**
   * 判断字符串是否为字母或数字组合
   * @param str 需要判断的字符串
   * @return 是否匹配 true,false
   */
  public static boolean isAlphanumeric(String str) {
    Pattern pattern = Pattern.compile("^[A-Za-z0-9]+$");
    Matcher isNum = pattern.matcher(str);
    if (!isNum.matches()) {
      return false;
    }
    return true;
  }
  
  /**
   * 将一个数字转化为字符串，并且在前面按照一定的长度补0，比如，serial=9, length=3
   * 则返回"009"
   * @param serial 给定的整数
   * @param length 返回字符串的长度
   */
  public static String zeroPadding(Integer serial, int length) {
    if (serial == null) {
      return StringUtils.leftPad("", length, '0');
    }

    String serialStr = serial.toString();
    return StringUtils.leftPad(serialStr, length, '0');
  }

  /**
   * 从给定的Serial字符串中取得数字，例如，给出"00089",则返回98
   * @param serial
   * @return
   */
  public static Integer getNumFromSerial(String serial) {
    if (StringUtils.isBlank(serial) || !StringUtils.isNumeric(serial)) {
      return null;
    }
    return Integer.valueOf(serial);
  }
}
