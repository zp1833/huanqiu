package com.alphasta.common;

import java.util.ResourceBundle;

import com.alphasta.common.core.util.ResourceBundleUtil;

/**
 * 系统常量类.
 * 
 * @author Sam
 * 
 */
public final class Constants {
	/**
	 * Prevent from initializing.
	 * 
	 */
	private Constants() {
	}

	/**
	 * 字符串表示的true
	 */
	public static final String CHAR_TRUE = "Y";

	/**
	 * 字符串表示的false
	 */
	public static final String CHAR_FALSE = "N";

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
	 * 错误信息文件.
	 */
	public static final String ERROR_BUNDLE_KEY = "errors";

	/**
	 * 缺省的分页容量
	 */
	public static final int DEFAULT_PAGE_SIZE = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "default.pagesize", 20);

	/**
	 * 第一页的页码,缺省是0
	 */
	public static final int DEFAULT_FIRST_PAGE_NO = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "defalut.firstPageNo", 0);

	/**
	 * 状态可用
	 */
	public static final String STATUS_AVAILABLE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.available", "1");

	/**
	 * 状态不可用
	 */
	public static final String STATUS_UNAVAILABLE = ResourceBundleUtil
			.getString(RESOURCE_BUNDLE, "global.unavailable", "0");

	/**
	 * 用户信息在Session中的名字
	 */
	public static final String USER_IN_SESSION = "userInSession";

	/**
	 * captcha 显示的字符
	 */
	public static final String CAPTCHA_STRING = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "captcha.string", "023456789");

	/**
	 * captcha 最多显示多少个
	 */
	public static final int CAPTCHA_MAX_WORDS = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.maxWords", 5);

	/**
	 * captcha 最少显示多少个
	 */
	public static final int CAPTCHA_MIN_WORDS = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.minWords", 4);

	/**
	 * captcha 图片宽度
	 */
	public static final int CAPTCHA_IMG_WIDTH = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.imgWidth", 80);

	/**
	 * captcha 详细内容图片宽度
	 */
	public static final int GLOBAL_IMAGE_WIDTH = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "global.image.width", 1024);

	/**
	 * captcha 图片高度
	 */
	public static final int CAPTCHA_IMG_HEIGHT = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.imgHeight", 25);

	/**
	 * captcha 最大字体
	 */
	public static final int CAPTCHA_MAX_FONT_SIZE = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.maxFontSize", 22);

	/**
	 * captcha 最小字体
	 */
	public static final int CAPTCHA_MIN_FONT_SIZE = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "captcha.minFontSize", 20);

	/**
	 * YES
	 */
	public static final String YES = "1";

	/**
	 * NO
	 */
	public static final String NO = "0";

	/**
	 *  版权文件
	 */
	public static final String COPYRIGHT_IMAGE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.copyright.image", "");
	
	/**
	 * 操作类型：添加
	 */
	public static final String OPERATION_ADD = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.add", "添加");
	
	/**
	 * 操作类型：修改
	 */
	public static final String OPERATION_UPDATE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.update", "修改");
	
	/**
	 * 操作类型：删除
	 */
	public static final String OPERATION_DELETE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.delete", "删除");
	
	/**
	 * 操作类型：审核
	 */
	public static final String OPERATION_AUDIT = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.audit", "审核");
	
	/**
	 * 操作类型：冻结
	 */
	public static final String OPERATION_FREEZE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.audit", "冻结");

	/**
	 * 操作类型：解除冻结
	 */
	public static final String OPERATION_RESUME = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.audit", "解除冻结");

	/**
	 * 操作类型：驳回
	 */
	public static final String OPERATION_REFUSE = ResourceBundleUtil.getString(
			RESOURCE_BUNDLE, "global.operation.audit", "驳回");
	
	/**
	 * 咨询部ID
	 */
	public static final Integer ADV_DEPT_ID = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "adv.dept.id", 59342848);

	/**
	 * 文案部ID
	 */
	public static final Integer DOC_DEPT_ID = ResourceBundleUtil.getInt(
			RESOURCE_BUNDLE, "doc.dept.id", 59342849);
	
	/** 角色 */
	public static final String ROLE_ADMIN = ResourceBundleUtil.getString(RESOURCE_BUNDLE, "role_admin", "ROLE_ADMIN");

	/** 角色 */
	public static final String ROLE_ADV = ResourceBundleUtil.getString(RESOURCE_BUNDLE, "role_adv", "ROLE_ADV");

	/** 角色 */
	public static final String ROLE_DOC = ResourceBundleUtil.getString(RESOURCE_BUNDLE, "role_doc", "ROLE_DOC");

	/** 角色 */
	public static final String ROLE_ORADMIN = ResourceBundleUtil.getString(RESOURCE_BUNDLE, "role_oradmin", "ROLE_ORADMIN");
	
	/** 文案显示国家 */
	public static final String DOC_SHOW_COUNTRY = ResourceBundleUtil.getString(RESOURCE_BUNDLE, "doc_show_country", "美国,英国,加拿大,澳大利亚,新西兰,马来西亚,荷兰");
}
