package com.alphasta.cms.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import com.alphasta.common.core.annotation.LogTargetAnnotation;
import com.alphasta.common.core.model.BaseModel;
import com.alphasta.common.dept.model.Dept;
import com.alphasta.common.security.user.model.User;

/**
 * 员工表
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EMPLOYEES")
@LogTargetAnnotation(classDescribe="员工")
public class Employees extends BaseModel {

	/** 主键 */
	private Integer id;

	/** 乐观锁 */
	private Integer version;

	/** 所属部门 */
	private Dept dept;

	/** 对应登录用户 */
	private User user;

	/** 用户名称 */
	private String name;

	/** 住址 */
	private String address;

	/** 邮编 */
	private String zip;

	/** 传真 */
	private String fax;

	/** 手机 */
	private String mobil;
	
	/** 电子邮件 */
	private String email;

	/** 性别 */
	private String sex;

	/** 宅电 */
	private String hTel;

	/** qq号码 */
	private String qq;

	/** msn */
	private String msn;

	/** 学历 */
	private String degree;

	/** 民族 */
	private String folk;

	/** 毕业学校 */
	private String school;

	/** 生日 */
	private Date birthday;

	/** 证件号码 */
	private String credentialNo;

	/** 职位 */
	private String place;

	/** 相片 */
	private String photo;

	/** 婚姻状况 */
	private String married;

	/** 政治面貌 */
	private String political;

	/** 毕业时间 */
	private Date graduateDate;

	/** 专业 */
	private String speciality;

	/** 户口所在地 */
	private String registeredPos;

	/** 参加工作时间 */
	private Date jobDate;

	/** 排序id */
	private Integer orderId;

	/** 简介 * */
	private String descn;
	
	/** 是否兼职文案 */
	private String isDoDoc = "0";
	
	/** 是否部门主管 */
	private String isDeptAdmin = "0";
	
	public String getIsDeptAdmin() {
		return isDeptAdmin;
	}

	public void setIsDeptAdmin(String isDeptAdmin) {
		this.isDeptAdmin = isDeptAdmin;
	}

	@Column(name="ISDODOC")
	public String getIsDoDoc() {
		return isDoDoc;
	}

	public void setIsDoDoc(String isDoDoc) {
		this.isDoDoc = isDoDoc;
	}

	/** 构造方法 */
	public Employees() {
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

	@Version
	@Column(name = "VERSION", precision = 22, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPT_ID")
	public Dept getDept() {
		return this.dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "USER_ID")
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "NAME")
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ADDRESS")
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ZIP", length = 25)
	public String getZip() {
		return this.zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	@Column(name = "FAX", length = 50)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "MOBIL", length = 50)
	public String getMobil() {
		return this.mobil;
	}

	public void setMobil(String mobil) {
		this.mobil = mobil;
	}

	@Column(name = "SEX", length = 1)
	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@Column(name = "H_TEL", length = 50)
	public String getHTel() {
		return this.hTel;
	}

	public void setHTel(String hTel) {
		this.hTel = hTel;
	}

	@Column(name = "QQ", length = 50)
	public String getQq() {
		return this.qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	@Column(name = "MSN")
	public String getMsn() {
		return this.msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
	}

	@Column(name = "DEGREE", length = 50)
	public String getDegree() {
		return this.degree;
	}

	public void setDegree(String degree) {
		this.degree = degree;
	}

	@Column(name = "FOLK")
	public String getFolk() {
		return this.folk;
	}

	public void setFolk(String folk) {
		this.folk = folk;
	}

	@Column(name = "SCHOOL")
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BIRTHDAY", length = 7)
	public Date getBirthday() {
		return this.birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@Column(name = "CREDENTIAL_NO")
	public String getCredentialNo() {
		return this.credentialNo;
	}

	public void setCredentialNo(String credentialNo) {
		this.credentialNo = credentialNo;
	}

	@Column(name = "PLACE")
	public String getPlace() {
		return this.place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	@Column(name = "PHOTO")
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	@Column(name = "MARRIED")
	public String getMarried() {
		return this.married;
	}

	public void setMarried(String married) {
		this.married = married;
	}

	@Column(name = "POLITICAL")
	public String getPolitical() {
		return this.political;
	}

	public void setPolitical(String political) {
		this.political = political;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "GRADUATE_DATE", length = 7)
	public Date getGraduateDate() {
		return this.graduateDate;
	}

	public void setGraduateDate(Date graduateDate) {
		this.graduateDate = graduateDate;
	}

	@Column(name = "SPECIALITY")
	public String getSpeciality() {
		return this.speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	@Column(name = "REGISTERED_POS")
	public String getRegisteredPos() {
		return this.registeredPos;
	}

	public void setRegisteredPos(String registeredPos) {
		this.registeredPos = registeredPos;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "JOB_DATE", length = 7)
	public Date getJobDate() {
		return this.jobDate;
	}

	public void setJobDate(Date jobDate) {
		this.jobDate = jobDate;
	}

	@Column(name = "ORDER_ID", precision = 22, scale = 0)
	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	@Column(name = "DESCN")
	public String getDescn() {
		return descn;
	}

	public void setDescn(String descn) {
		this.descn = descn;
	}

	/**
	 * @see Object#equals(Object)
	 */
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof Employees)) {
			return false;
		}
		Employees castOther = (Employees) other;
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
	

	@Transient
	@Override
	public String getLogContent() {
		return this.getName();
	}

	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
