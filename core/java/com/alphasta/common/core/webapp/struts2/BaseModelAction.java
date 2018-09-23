package com.alphasta.common.core.webapp.struts2;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.util.FileCopyUtils;

import com.alphasta.common.Constants;
import com.alphasta.common.core.dao.support.Page;
import com.alphasta.common.core.exception.ApplicationException;
import com.alphasta.common.core.model.BaseModel;
import com.alphasta.common.core.service.Manager;
import com.alphasta.common.core.util.DateUtil;
import com.alphasta.common.core.util.GenericsUtil;
import com.alphasta.common.core.util.ReflectUtil;
import com.alphasta.common.security.user.model.User;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 支持Model驱动的Action，通过泛型的支持，简化客户端编程. <code>BaseModelAction</code>提供了常用的CRUD
 * action方法的实现<br>
 * 
 * 
 * @author Sam
 * 
 * @param <T>
 *            Model
 * @param <M>
 *            Manager
 * @version 2.0
 * 
 */

@SuppressWarnings( { "serial", "unchecked" })
public class BaseModelAction<T extends BaseModel, M extends Manager> extends
		BaseAction implements ModelDriven {
	/**
	 * Model的Class
	 */
	private Class<T> entityClass;

	/**
	 * Action的Model
	 */
	protected T model;

	/**
	 * Action所使用的Manager对象
	 */
	protected M manager;

	/**
	 * 用于保存查询操作所产生的结果
	 */
	protected Collection items = java.util.Collections.EMPTY_LIST;
	/**
	 * 用于保存分页查询的结果。
	 * 
	 * @see {@link Page}
	 */
	protected Page page = Page.EMPTY_PAGE;

	/**
	 * 用于批量操作时，记录请求中的ID
	 */
	protected Serializable[] selectedItems;

	/**
	 * 修改某个记录之前，必须先根据id查询该数据，然后再定位到编辑页面， <code>edit()</code> 方法实现了这个过程。
	 * 
	 * @return Result name of struts2.
	 */
	public String edit() {
		Serializable id = (Serializable) ReflectUtil.get(model, "id");
		if (id != null) {
			model = (T) getManager().get(getEntityClass(), id);
			if (log.isDebugEnabled()) {
				log.debug("Prepare editing object '" + model + "'");
			}

			return SUCCESS;
		}

		return INPUT;
	}
	
	/**
	 * @return 当前用户信息
	 */
	protected User getUser() {
		if (getPrincipal() != null) {
			return getManager().get(User.class, getPrincipal().getId());
		}
		return null;
	}

	/**
	 * 用于保存一个对象的Action方法.
	 * 
	 * @return Result name of struts2.
	 */
	public String save() {
		try {
			getManager().save(model);
		} catch (ApplicationException e) {
			addActionError(e.getMessage());
			return INPUT;
		}
		if (log.isDebugEnabled()) {
			log.debug("Save object '" + model + "'");
		}

		return SUCCESS;
	}

	/**
	 * 用于列出所有记录的convenient方法.
	 * 
	 * @return the result name of struts2
	 */
	public String list() {
		items = getManager().get(getEntityClass());
		if (items != null && items.size() > 0) {
			if (log.isDebugEnabled()) {
				log.debug("List all " + items.size() + " items listed.");
			}
		}
		return SUCCESS;
	}

	/**
	 * 用于删除页码上选择的多个数据（通过<code>selectedItems</code>属性传入） 或者一个数据<code>model</code>属性传入。
	 * 
	 * @see {@link #selectedItems}
	 * @return the result name of struts2
	 */
	public String remove() {
		if (selectedItems == null || selectedItems.length == 0) {
			if (model != null) {
				Serializable id = (Serializable) ReflectUtil.get(model, "id");
				if (id != null) {
					selectedItems = new Serializable[] { id };
				}
			}
		}
		if (selectedItems != null) {
			for (Serializable id : selectedItems) {
				if (id != null) {
					Object object = getManager().get(getEntityClass(),
							Integer.valueOf(id.toString()));
					getManager().remove(object);
				}
			}

		}

		return SUCCESS;
	}

	/**
	 * 执行查询的Action方法，子类必须实现{@link #setupDetachedCriteria()}方法, 或{@link #setupSQL()}和{@link retrieveQueryParameters()}方法。query()
	 * 会根据setupSQL返回的sql执行查询，如果返回null，这执行setupDetachedCriteria
	 * 返回的DetachedCriteria。
	 */
	public String query() {

		if (setupSQL() != null) {
			items = getManager().query(setupSQL(), retrieveQueryParameters());
		} else {
			items = getManager().query(setupDetachedCriteria());
		}

		return SUCCESS;
	}

	/**
	 * 执行分页查询的Action方法.
	 * 
	 * @return
	 */
	public String pageQuery() {
		DetachedCriteria criteria = null;
		// 设置查询条件
		try {
			criteria = setupDetachedCriteria();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (criteria == null) {
			page = getManager().query(setupSQL(), getPageNo(), getPageSize(),
					retrieveQueryParameters());
		} else { // 如果不使用Criteria查询，则使用HQL查询.
			page = getManager().query(criteria, getPageNo(), getPageSize());
		}

		restorePageData(); // 重新将分页查询结果放回到页面

		return SUCCESS;
	}

	/**
	 * 返回分页容量，缺省的是定义在application.properties中.子类可以覆盖， 以获取实际的分页容量.
	 */
	protected int getPageSize() {
		return Constants.DEFAULT_PAGE_SIZE;
	}

	/**
	 * 返回当前页码.子类必须实现本方法，以确定当前页数.
	 */
	protected int getPageNo() {
		return Page.FIRST_PAGE_INDEX;
	}

	/**
	 * 将分页数据重新设置到页面.
	 */
	protected void restorePageData() {
		// do nothing
	}

	/**
	 * 设置<code>DetachedCriteria</code>对象.具体的实现留给子类. 子类根据自身情况，创建并设置<code>DetachedCriteria</code>对象，
	 * <code>BaseModelAction</code>调用本方法。
	 */
	protected DetachedCriteria setupDetachedCriteria() {
		return DetachedCriteria.forClass(getEntityClass());
	}

	/**
	 * 设置查询所需的SQL，具体实现留给子类.必须和{@link #retrieveQueryParameters()} 方法 联合使用.
	 */
	protected String setupSQL() {
		return null;
	}

	/**
	 * 返回SQL查询所需的参数.具体实现由子类决定.通常与{@link #setupSQL()}联合使用.
	 * 
	 * @return Array of parameters or zero length array.
	 */
	protected Object[] retrieveQueryParameters() {
		return new Object[] {};
	}

	/**
	 * 处理上传文件，将上传的文件另存到某一目录下。
	 * 
	 * @param file
	 *            上传文件对象，struts2将上传的文件保存到一个临时目录。
	 * @param fileName
	 *            另存的文件名。
	 * @param folderName
	 *            另存的目录名。
	 * @param isReName
	 *            是否重命名
	 * @return 返回完整的文件名，如果失败，返回<code>null</code>
	 */
	protected String doUpload(File file, String fileName, String folderName,
			boolean isReName) {
		if (file == null || StringUtils.isBlank(fileName)) {
			return null;
		}

		String folderPath = getServletContext().getRealPath(folderName);
		File folder = new File(folderPath);
		if (!folder.exists()) {
			folder.mkdirs();
		}

		String destFileName;

		// 是否重命名
		if (isReName) {
			// 扩展名
			String ext = fileName.substring(fileName.lastIndexOf("."));
			// 重新命名
			final int randomLength = 3;
			destFileName = DateUtil.getDateTime("yyyyMMddhhmmss", new Date())
					+ RandomStringUtils.randomNumeric(randomLength) + ext;
		} else {
			destFileName = fileName;
		}

		File dest = new File(folderPath + File.separatorChar + destFileName);
		try {
			FileCopyUtils.copy(file, dest);
			return folderName + destFileName;
		} catch (IOException e) {
			e.printStackTrace();
			if (log.isErrorEnabled()) {
				log.error(e.getMessage());
			}
			return null;
		}
	}

	/**
	 * 处理上传文件，将上传的文件另存到某一目录下。
	 * 
	 * @param file
	 *            上传文件对象，struts2将上传的文件保存到一个临时目录。
	 * @param fileName
	 *            另存的文件名。
	 * @param folderName
	 *            另存的目录名。
	 * @return 返回完整的文件名，如果失败，返回<code>null</code>
	 */
	protected String doUpload(File file, String fileName, String folderName) {
		return doUpload(file, fileName, folderName, true);
	}

	/**
	 * Struts ModelDriven 接口方法, 此方法由interceptor:model-driven自动调用 用于获取Model对象。
	 */
	public final Object getModel() {
		if (model == null) {
			model = (T) ReflectUtil.newInstance(getEntityClass());
		}

		return model;
	}

	/**
	 * @return the manager
	 */
	public M getManager() {
		return manager;
	}

	/**
	 * @param manager
	 *            the manager to set
	 */
	public void setManager(M manager) {
		this.manager = manager;
	}

	/**
	 * @return the selectedItems
	 */
	public Serializable[] getSelectedItems() {
		return selectedItems;
	}

	/**
	 * @param selectedItems
	 *            the selectedItems to set
	 */
	public void setSelectedItems(Serializable[] selectedItems) {
		this.selectedItems = selectedItems;
	}

	/**
	 * @return the items
	 */
	public Collection getItems() {
		return items;
	}

	/**
	 * @return 泛型所指向的Class
	 */
	protected final Class<T> getEntityClass() {
		if (entityClass == null) {
			entityClass = GenericsUtil.getGenericClass(getClass());
		}
		return entityClass;
	}

	/**
	 * @return the page
	 */
	public Page getPage() {
		return page;
	}
}
