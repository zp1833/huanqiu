package com.alphasta.cms;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import com.alphasta.common.core.util.ResourceBundleUtil;

/**
 * CMS常量
 * 
 * @author zw
 * 
 */
public final class CmsConstants {

	/**
	 * 私有构造器，防止常量类被实例化
	 */
	private CmsConstants() {

	}

	/**
	 * 资源文件.
	 */
	public static final String BUNDLE_KEY = "application";

	/**
	 * 资源绑定对象
	 */
	public static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_KEY);

	/**
	 * 部门为“省劳动厅”的id
	 */
	public static final int DEPT_ID = 0;

	/**
	 * 查询编号
	 */
	public static final int QUERY_CODE = 8;

	/**
	 * 性别常量，M-男性
	 */
	public static final String GENT = "M";

	/**
	 * 性别常量，F-女性
	 */
	public static final String LADY = "F";

	/**
	 * 婚姻常量，Y-已婚
	 */
	public static final String MARRIED_Y = "Y";

	/**
	 * 婚姻常量，N-未婚
	 */
	public static final String MARRIED_N = "N";

	/**
	 * 学历常量，0-初中以下
	 */
	public static final String ELEMENTARY = "0";

	/**
	 * 学历常量，1-初中
	 */
	public static final String JUNIOR = "1";

	/**
	 * 学历常量，2-高中
	 */
	public static final String SENIOR = "2";

	/**
	 * 学历常量，3-大专
	 */
	public static final String JUNIOR_COLLEGE = "3";

	/**
	 * 学历常量，4-本科
	 */
	public static final String UNDERGRADUATE = "4";

	/**
	 * 学历常量，5-本科以上
	 */
	public static final String DOCTOR = "5";

	/**
	 * 政治面貌，0-党员
	 */
	public static final String PARTY = "0";

	/**
	 * 政治面貌，1-团员
	 */
	public static final String MEMBER = "1";

	/**
	 * 政治面貌，2-群众
	 */
	public static final String MULTITUDE = "2";

	/**
	 * 政治面貌，3-其他
	 */
	public static final String OTHER = "3";

	/**
	 * 显示角色常量：ROLE_LEADERSHIP彩- 领导
	 */
	public static final String ROLE_LEADERSHIP = "ROLE_LEADERSHIP";

	/**
	 * 婚姻常量Map
	 */
	public static final Map<String, String> MARRIED_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		MARRIED_MAP.put(MARRIED_N, "未婚");
		MARRIED_MAP.put(MARRIED_Y, "已婚");
	}

	/**
	 * 学历常量Map
	 */
	public static final Map<String, String> DEGREE_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		DEGREE_MAP.put(ELEMENTARY, "初中以下");
		DEGREE_MAP.put(JUNIOR, "初中");
		DEGREE_MAP.put(SENIOR, "高中");
		DEGREE_MAP.put(JUNIOR_COLLEGE, "大专");
		DEGREE_MAP.put(UNDERGRADUATE, "本科");
		DEGREE_MAP.put(DOCTOR, "本科以上");
	}

	/**
	 * 政治面貌Map
	 */
	public static final Map<String, String> POLITICAL_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		POLITICAL_MAP.put(PARTY, "党员");
		POLITICAL_MAP.put(MEMBER, "团员");
		POLITICAL_MAP.put(MULTITUDE, "群众");
		POLITICAL_MAP.put(OTHER, "其他");
	}

	/**
	 * 性别常量Map
	 */
	public static final Map<String, String> SEX_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		SEX_MAP.put(GENT, "男");
		SEX_MAP.put(LADY, "女");
	}

	/**
	 * Y-是
	 */
	public static final String Y = "1";

	/**
	 * N-否
	 */
	public static final String N = "0";

	/**
	 * 是否状态Map
	 */
	public static final Map<String, String> YN_MAP = Collections
			.synchronizedMap(new LinkedHashMap<String, String>());
	static {
		YN_MAP.put(Y, "是");
		YN_MAP.put(N, "否");
	}

	/**
	 * 部门类别：1-直属单位
	 */
	public static final String OUTER = "1";

	/**
	 * 部门类别：0-内设处室
	 */
	public static final String INNER = "0";

	/**
	 * 部门类别：2-设区市劳动局
	 */
	public static final String SQWHJ = "2";

	/**
	 * 部门类别：3-直管县(市)劳动局
	 */
	public static final String ZGXWHJ = "3";

	/** 网站开始访问目录 */
	public static final String START_ROOT = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "golbal.webview.startroot", "/yunguan");

	/** WinRar安装路径 */
	public static final String WINRAR_PATH = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "winrar.path", "C:\\Program Files\\WinRAR");

	/**
	 * IP段状态，1-可以登录
	 */
	public static final String IPOPEN = "1";

	/**
	 * IP段状态，0-禁止登录
	 */
	public static final String IPCLOSE = "0";
	
}