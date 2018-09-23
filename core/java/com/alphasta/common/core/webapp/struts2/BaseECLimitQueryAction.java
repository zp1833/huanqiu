package com.alphasta.common.core.webapp.struts2;

import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableConstants;
import org.ecside.table.limit.Limit;
import org.ecside.table.limit.Sort;
import org.ecside.util.RequestUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.alphasta.common.Constants;
import com.alphasta.common.core.model.BaseModel;
import com.alphasta.common.core.service.Manager;

/**
 * 使用ECSide的limit方式查询的基类。子类可以方便的从ECSide的参数 中获得分页条件。
 * 
 * @author Sam Lee
 * 
 * @param <T>
 * @param <E>
 */
@SuppressWarnings( { "unchecked", "serial" })
public class BaseECLimitQueryAction<T extends BaseModel, E extends Manager>
		extends BaseModelAction<T, E> {
	/**
	 * ECSide读取的数据在Request中的name
	 */
	public static final String EC_DATA_NAME = "items";

	/**
	 * 最大行数，用于设置limit
	 */
	public int MAX_ROWS = 1000000000;
	
	protected int currentPageNo;

	/**
	 * @see BaseModelAction#getPageNo()
	 */
	@Override
	public int getPageNo() {
		int	pageNo = RequestUtils.getPageNo(getRequest());
		return pageNo;
	}

	/**
	 * @see BaseModelAction#getPageSize()
	 */
	@Override
	protected int getPageSize() {
		int pageSize = RequestUtils.getCurrentRowsDisplayed(getRequest());
		RequestUtils.getRowStartEnd(this.getRequest(), MAX_ROWS, Constants.DEFAULT_PAGE_SIZE);
		if(pageSize <= 0) {
			pageSize = Constants.DEFAULT_PAGE_SIZE;
		}
	    return pageSize;
	}

	/**
	 * @return <code>Sort</code> of ec.
	 */
	protected Sort getSort() {
		Limit limit = RequestUtils.getLimit(getRequest(),
				TableConstants.EXTREME_COMPONENTS, MAX_ROWS,
				Constants.DEFAULT_PAGE_SIZE);
		
		return limit.getSort();
	}

	/**
	 * 从ec的Order中取得排序信息，并用于设置DetachedCriteria对象
	 * 
	 * @param criteria
	 *            被设置的DetachedCriteria对象.
	 * @return 设置之后的DetachedCriteria对象.
	 */
	protected DetachedCriteria setupSort(DetachedCriteria criteria) {
		Sort sort = getSort();
		if (sort != null && criteria != null
				&& StringUtils.isNotBlank(sort.getProperty())) {
			if (sort.getSortOrder().equals("asc")) {
				criteria.addOrder(Property.forName(sort.getProperty()).asc());
			} else {
				criteria.addOrder(Property.forName(sort.getProperty()).desc());
			}
		}

		return criteria;
	}

	/**
	 * 从ec的Order中取得排序信息，并用于设置Model的sortProperty和sortDirection属性。 通常用于IBaits查询。
	 * FIXME：注意，这里设置的是property名称而非数据库字段名称 FIXME：目前不支持嵌套属性。
	 * 
	 * @see {@link BaseModel#getSortProperty()}
	 * @see {@link BaseModel#getSortDirtection()}
	 */
	protected void setupSort() {
		Sort sort = getSort();
		if (sort != null) {
			model.setSortProperty(sort.getProperty());
			model.setSortDirection(sort.getSortOrder());
		}
	}

	/**
	 * @see BaseModelAction#restorePageData()
	 */
	@Override
	protected void restorePageData() {
		RequestUtils.initLimit(getRequest(), TableConstants.EXTREME_COMPONENTS,
				page.getRows(), Constants.DEFAULT_PAGE_SIZE);
		getRequest().setAttribute(EC_DATA_NAME, page.getData());
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public int getCurrentPageNo() {
		return this.currentPageNo;
	}

}
