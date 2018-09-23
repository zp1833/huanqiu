package com.alphasta.common.security.user.model;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.acegisecurity.GrantedAuthority;
import org.acegisecurity.userdetails.UserDetails;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.annotations.GenericGenerator;

import com.alphasta.cms.model.Employees;
import com.alphasta.common.Constants;
import com.alphasta.common.core.annotation.LogTargetAnnotation;
import com.alphasta.common.core.model.BaseModel;

/**
 * 用户表 The persistent class for the users database table.
 * 
 * @author BEA Workshop
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USERS", uniqueConstraints = {})
@LogTargetAnnotation(classDescribe = "用户")
public class User extends BaseModel implements UserDetails, Serializable {
	/**
	 * Log
	 */
	private static Log log = LogFactory.getLog(User.class);

	/**
	 * 主键
	 */
	private Integer id;

	/**
	 * 用户描述
	 */
	private String descn;

	/**
	 * 是否禁用 1-禁用，0-未禁用
	 */
	private String type;

	/**
	 * 最后登录IP
	 */
	private String lastLoginIp;

	/**
	 * 最后登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 登录ID
	 */
	private String loginId;

	/**
	 * 登录次数
	 */
	private Integer loginTimes;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 确认密码
	 */
	private String confirmPwd;

	/**
	 * 状态（1缺省）
	 */
	private String status = "1";

	/**
	 * 类型
	 */
	private String userType;

	/**
	 * 版本
	 */
	private Integer version;

	/**
	 * 对应的职员
	 */
	private Employees employees;
	
	/** 是否为部门责任人 0-不是，1-是*/
	private String isManager;

	/**
	 * 所具有的角色
	 */
	private Set<Role> roles = new HashSet<Role>(0);

	/**
	 * 缺省构造器
	 */
	public User() {
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

	@Column(name = "DESCN")
	public String getDescn() {
		return this.descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	@Column(name = "TYPE")
	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "LAST_LOGIN_IP")
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}

	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}

	@Column(name = "LAST_LOGIN_TIME")
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	@Column(name = "LOGIN_ID")
	public String getLoginId() {
		return this.loginId;
	}

	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	@Column(name = "LOGIN_TIMES")
	public Integer getLoginTimes() {
		return this.loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "STATUS", length = 2)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "USER_TYPE")
	public String getUserType() {
		return this.userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Version
	@Column(name = "VERSION")
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@OneToOne(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY, mappedBy = "user")
	public Employees getEmployees() {
		return this.employees;
	}

	public void setEmployees(Employees employee) {
		this.employees = employee;
	}

	@ManyToMany(targetEntity = Role.class, cascade = {}, fetch = FetchType.LAZY)
	@JoinTable(name = "user_role", joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = { @JoinColumn(name = "role_id") })
	public Set<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof User)) {
			return false;
		}
		User castOther = (User) other;
		return new EqualsBuilder().append(this.getId(), castOther.getId())
				.isEquals();
	}

	/**
	 * @see Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	/**
	 * @see Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("id", getId()).toString();
	}

	// Methods from UserDetails
	/**
	 * 用户所具有的权限，可用通过角色-权限对应关系得到
	 */
	private transient GrantedAuthority[] authorities;

	/**
	 * @see {@link UserDetails#getAuthorities()}
	 */
	@Transient
	public GrantedAuthority[] getAuthorities() {
		return this.authorities;
	}

	/**
	 * @param authorities
	 *            the authorities to set
	 */
	public void setAuthorities(GrantedAuthority[] authorities) {
		log.info("Set GrantedAuthorities :" + Arrays.toString(authorities));
		this.authorities = authorities;
	}

	/**
	 * @see {@link UserDetails#getUsername()}
	 */
	@Transient
	public String getUsername() {
		return this.loginId;
	}

	/**
	 * @see {@link UserDetails#isAccountNonExpired()}
	 */
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	/**
	 * @see {@link UserDetails#isAccountNonLocked()}
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	/**
	 * {@link UserDetails#isCredentialsNonExpired()}
	 */
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/**
	 * {@link UserDetails#isEnabled()}
	 */
	@Transient
	public boolean isEnabled() {
		return StringUtils.equalsIgnoreCase(status, Constants.STATUS_AVAILABLE);
	}

	@Transient
	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	/**
	 * 是否有角色？
	 */
	@Transient
	public boolean getHasRoles() {
		return roles != null && roles.size() > 0;
	}

	@Transient
	public String getLogContent() {
		return this.getUsername();
	}

	@Column(name="IS_MANAGER")
	public String getIsManager() {
		return isManager;
	}

	public void setIsManager(String isManager) {
		this.isManager = isManager;
	}

}