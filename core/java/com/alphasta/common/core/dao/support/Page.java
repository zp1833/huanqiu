package com.alphasta.common.core.dao.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.alphasta.common.Constants;


/**
 * 分页对象.包含数据及分页信息. (copy from springside)
 * 
 * @author Sam
 */

@SuppressWarnings("unchecked")
public final class Page implements Serializable {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 231152607479172128L;
  
  /**
   * 第一页的索引
   */
  public static final int FIRST_PAGE_INDEX = 1;

	/**
	 * 空Page对象.
	 */
	public static final Page EMPTY_PAGE = new Page();

	/**
	 * 当前页第一条数据的位置,从0开始
	 */
	private int startIndex;

	/**
	 * 每页的记录数
	 */
	private int pageSize = Constants.DEFAULT_PAGE_SIZE;

	/**
	 * 当前页中存放的记录
	 */
	private List data;

	/**
	 * 总记录数
	 */
	private int rows;

	/**
	 * 构造方法，只构造空页
	 */
	public Page() {
		this(0, 0, Constants.DEFAULT_PAGE_SIZE, Collections.EMPTY_LIST);
	}

	/**
	 * 默认构造方法
	 * 
	 * @param startIndex  本页数据在数据库中的起始位置
	 * @param rows 数据库中总记录条数
	 * @param pageSize 本页容量
	 * @param data 本页包含的数据
	 */
	public Page(int startIndex, int rows, int pageSize, List data) {
		this.pageSize = pageSize;
		this.startIndex = startIndex;
		this.rows = rows;
		this.data = data;
	}

	/**
	 * 取数据库中包含的总记录数
	 */
	public int getRows() {
		return this.rows;
	}

	/**
	 * 取总页数
	 */
	public int getPages() {
		if (rows % pageSize == 0) {
			return rows / pageSize;
		} else {
			return rows / pageSize + 1;
		}
	}

	/**
	 * 取每页数据容量
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取当前页码
	 * 
	 * @return 第一页是1，第二页是2...
	 */
	public int getPageNo() {
		return (startIndex / pageSize) + 1;
	}

	/**
	 * 是否有下一页
	 */
	public boolean getHasNextPage() {
		return this.getPageNo() < this.getPages();
	}

	/**
	 * 是否有上一页
	 */
	public boolean hasPreviousPage() {
		return (this.getPageNo() > 1);
	}

	/**
	 * 获取任一页第一条数据的位置,startIndex从0开始
	 */
	public static int getStartOfPage(int pageNo, int pageSize) {
		return (pageNo - 1) * pageSize;
	}

	/**
	 * @return the data
	 */
  public List getData() {
		return data;
	}
  
  public void setData(List data) {
    this.data = data;
  }

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}
}
