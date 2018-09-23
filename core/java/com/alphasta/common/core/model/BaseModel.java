package com.alphasta.common.core.model;

import javax.persistence.Transient;

/**
 * 域模型的基类
 * 
 * @author Sam Lee
 * 
 */
@SuppressWarnings("serial")
public class BaseModel implements java.io.Serializable {
	/**
	 * 排序方向：asc
	 */
	public static final String SORT_DIR_ASC = "asc";
	/**
	 * 排序方向：desc
	 */
	public static final String SORT_DIR_DESC = "desc";
	/**
	 * 用于标识对象的状态是否改变.
	 */
	private transient Boolean changed = Boolean.FALSE;
	/**
	 * 排序字段或属性名称
	 */
	private transient String sortProperty;
	/**
	 * 排序方向
	 */
	private transient String sortDirection;

	@Transient
	public Boolean getChanged() {
		return changed;
	}

	public void setChanged(Boolean changed) {
		this.changed = changed;
	}

	@Transient
	public String getSortDirection() {
		return sortDirection;
	}

	public void setSortDirection(String sortDirection) {
		this.sortDirection = sortDirection;
	}

	@Transient
	public String getSortProperty() {
		return sortProperty;
	}

	public void setSortProperty(String sortProperty) {
		this.sortProperty = sortProperty;
	}
	
	@Transient
	public String getLogContent(){
		return "";
	}

}
