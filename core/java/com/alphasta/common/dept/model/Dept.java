package com.alphasta.common.dept.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.alphasta.cms.model.Employees;
import com.alphasta.common.core.annotation.LogTargetAnnotation;
import com.alphasta.common.core.model.BaseModel;

/**
 * The persistent class for the depts database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "DEPTS", uniqueConstraints = {})
@LogTargetAnnotation(classDescribe="部门")
public class Dept extends BaseModel implements Serializable {
	/**
	 * id
	 */
	private Integer id;
	/**
	 * 打印查询码
	 */
	private String password;
	/**
	 * 部门描述
	 */
	private String descn;

	/** 排序ID */
	private Integer orderId;

	/**
	 * 部门名称
	 */
	private String name;

	/**
	 * 部门编号规则：上级部门编号+两位数字，从1自动排；
	 */
	private String serialNo;

	/**
	 * 部门类别
	 */
	private String deptSort = "0";

	/**
	 * 部门邮箱
	 */
	private String email;
	
	/**
	 * 部门电话
	 */
	private String tel;

	/**
	 * 上级部门
	 */
	private Dept parentDept;
	
	/**
	 * 部门是否对外显示 0-不显示，1-显示
	 */
	private String available = "1";

	/**
	 * 部门记录
	 */
	private Set<Dept> childDepts = new HashSet<Dept>(0);

	/**
	 * 部门下的员工
	 */
	private Set<Employees> employees = new HashSet<Employees>(0);

	/**
	 * 缺省构造
	 */
	public Dept() {
	}

	/**
	 * 构造方法
	 */
	public Dept(Integer id) {
		this.id = id;
	}

	@Id
	@GeneratedValue(generator = "hibseq")
	@GenericGenerator(name = "hibseq", strategy = "hilo")
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "descn")
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "ORDERID", precision = 22, scale = 0)
	public Integer getOrderId() {
		return this.orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "name")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "serial_no")
	public String getSerialNo() {
		return this.serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	@Column(name = "DEPT_SORT")
	public String getDeptSort() {
		return deptSort;
	}

	public void setDeptSort(String deptSort) {
		this.deptSort = deptSort;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "PARENT_ID")
	public Dept getParentDept() {
		return this.parentDept;
	}

	public void setParentDept(Dept parentDept) {
		this.parentDept = parentDept;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "parentDept")
	public Set<Dept> getChildDepts() {
		return this.childDepts;
	}

	public void setChildDepts(Set<Dept> childDepts) {
		this.childDepts = childDepts;
	}

	@OneToMany(cascade = {}, fetch = FetchType.LAZY, mappedBy = "dept")
	public Set<Employees> getEmployees() {
		return this.employees;
	}

	public void setEmployees(Set<Employees> employees) {
		this.employees = employees;
	}
	
	@Transient
	public boolean getHasChild() {
		return this.getChildDepts().size() > 0;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Dept)) {
			return false;
		}
		Dept castOther = (Dept) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}
	
	@Transient
	public String getLogContent(){
		return this.getName();
	}

	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name="TEL")
	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name="AVAILABLE")
	public String getAvailable() {
		return available;
	}

	public void setAvailable(String available) {
		this.available = available;
	}

	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}