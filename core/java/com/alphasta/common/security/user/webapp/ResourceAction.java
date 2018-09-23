package com.alphasta.common.security.user.webapp;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;

import com.alphasta.common.core.webapp.struts2.BaseECLimitQueryAction;
import com.alphasta.common.security.user.UserConstants;
import com.alphasta.common.security.user.model.Resource;
import com.alphasta.common.security.user.service.ResourceManager;

/**
 * 资源Action
 * 
 * @author Sam Lee
 */
@SuppressWarnings("serial")
public class ResourceAction extends
		BaseECLimitQueryAction<Resource, ResourceManager> {

	/**
	 * struts配置文件
	 */
	@SuppressWarnings("unchecked")
	private List strutsXmlFiles;

	/**
	 * 返回资源类型列表
	 */
	public Map<String, String> getResourceTypes() {
		return UserConstants.RESOURCE_TYPES;
	}

	/**
	 * 按资源名称和字符串执行的查询
	 * 
	 * @see BaseModelAction#query()
	 */
	@Override
	public String query() {
		if (StringUtils.isBlank(model.getName())
				&& StringUtils.isBlank(model.getResString())) {
			items = getManager().query("from Resource");
		} else {
			items = getManager().query(
					"from Resource r "
							+ "where r.name like ? and r.resString like ?",
					new Object[] {
							MatchMode.ANYWHERE.toMatchString(model.getName()),
							MatchMode.ANYWHERE.toMatchString(model
									.getResString()) });
		}
		return SUCCESS;
	}

	/**
	 * 解析Struts配置文件内容为url资源,并保存到数据库中
	 */
	@SuppressWarnings("unchecked")
	public boolean saveUrl() {
		List strutsXmls = getStrutsXmlFiles();
		getManager().saveResource(strutsXmls);
		return true;
	}

	@SuppressWarnings("unchecked")
	public List getStrutsXmlFiles() {
		return strutsXmlFiles;
	}

	@SuppressWarnings("unchecked")
	public void setStrutsXmlFiles(List strutsXmlFiles) {
		this.strutsXmlFiles = strutsXmlFiles;
	}
}
