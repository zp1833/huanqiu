package com.alphasta.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.alphasta.common.core.model.BaseModel;

/**
 * Operationlog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "STUINFO")
public class StuInfo extends BaseModel {

	// Fields

	private Integer id;

	private Date advDate;

	private String stuName;

	private String advCourse;

	private String source;

	private String sourceFrom;

	private String advType;

	private String recEmp;

	private String advisor;

	private String currentPlan;

	private String stuType;

	private Date nextVisitTime;

	private Date statusDefineTime;

	private String contact;

	private String sex;

	private Date birthday;

	private String isMerried;

	private String education;

	private String speciality;

	private String finishSchool;

	private Date finishTime;

	private String currentSchool;

	private String currentGrade;

	private String collegeScore;

	private String needScore;

	private String hopeDegree;

	private String enDegree;

	private String isExam;

	private String infoDesc;

	private String traceDesc;

	private Date recentTraceTime;

	private String introToAgency;

	private String signStatus;

	private String className;

	private String courseName;

	private String tuition;

	private String reportSchool;

	private String realMoney;

	private String realMoney1;

	private String realMoney2;

	private String realMoney3;
	
	private String realMoney4;
	
	private String realMoney5;

	private Date dealDate;

	private String dealPerson;

	private String schoolName;

	private Date studyTime;

	private String payMethod;

	private Date nextExamTime;

	private String examScore;

	private String isMember;

	private Date memberJoinTime;

	private String memberIntegral;

	private String memberTel;

	private String memberQq;

	private String memberEmail;

	private String memberSchool;

	private String memberGrade;

	private String memberSpeciality;

	private String memberLike;

	private String memberReason;

	private String memberHope;

	private String memberAdvisory;

	private String memberDescn;

	private Date memberLastIntegralTime;

	private String memberIntegralHistory;

	private Date memberLastActiveTime;

	private String memberActiveHistory;

	private Date memberLastChangeTime;

	private String memberChangeHistory;

	private Date memberLastSendTime;

	private String isStu;

	private String memberSendHistory;

	private String country;

	private String qq;
	
	private String city;
	
	private String contact1;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getIsMember() {
		return isMember;
	}

	public void setIsMember(String isMember) {
		this.isMember = isMember;
	}

	public Date getMemberJoinTime() {
		return memberJoinTime;
	}

	public void setMemberJoinTime(Date memberJoinTime) {
		this.memberJoinTime = memberJoinTime;
	}

	public String getMemberIntegral() {
		return memberIntegral;
	}

	public void setMemberIntegral(String memberIntegral) {
		this.memberIntegral = memberIntegral;
	}

	public String getMemberTel() {
		return memberTel;
	}

	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}

	public String getMemberQq() {
		return memberQq;
	}

	public void setMemberQq(String memberQq) {
		this.memberQq = memberQq;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getMemberSchool() {
		return memberSchool;
	}

	public void setMemberSchool(String memberSchool) {
		this.memberSchool = memberSchool;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	public String getMemberSpeciality() {
		return memberSpeciality;
	}

	public void setMemberSpeciality(String memberSpeciality) {
		this.memberSpeciality = memberSpeciality;
	}

	public String getMemberLike() {
		return memberLike;
	}

	public void setMemberLike(String memberLike) {
		this.memberLike = memberLike;
	}

	public String getMemberReason() {
		return memberReason;
	}

	public void setMemberReason(String memberReason) {
		this.memberReason = memberReason;
	}

	public String getMemberHope() {
		return memberHope;
	}

	public void setMemberHope(String memberHope) {
		this.memberHope = memberHope;
	}

	public String getMemberAdvisory() {
		return memberAdvisory;
	}

	public void setMemberAdvisory(String memberAdvisory) {
		this.memberAdvisory = memberAdvisory;
	}

	public String getMemberDescn() {
		return memberDescn;
	}

	public void setMemberDescn(String memberDescn) {
		this.memberDescn = memberDescn;
	}

	public Date getMemberLastIntegralTime() {
		return memberLastIntegralTime;
	}

	public void setMemberLastIntegralTime(Date memberLastIntegralTime) {
		this.memberLastIntegralTime = memberLastIntegralTime;
	}

	public String getMemberIntegralHistory() {
		return memberIntegralHistory;
	}

	public void setMemberIntegralHistory(String memberIntegralHistory) {
		this.memberIntegralHistory = memberIntegralHistory;
	}

	public Date getMemberLastActiveTime() {
		return memberLastActiveTime;
	}

	public void setMemberLastActiveTime(Date memberLastActiveTime) {
		this.memberLastActiveTime = memberLastActiveTime;
	}

	public String getMemberActiveHistory() {
		return memberActiveHistory;
	}

	public void setMemberActiveHistory(String memberActiveHistory) {
		this.memberActiveHistory = memberActiveHistory;
	}

	public Date getMemberLastChangeTime() {
		return memberLastChangeTime;
	}

	public void setMemberLastChangeTime(Date memberLastChangeTime) {
		this.memberLastChangeTime = memberLastChangeTime;
	}

	public String getMemberChangeHistory() {
		return memberChangeHistory;
	}

	public void setMemberChangeHistory(String memberChangeHistory) {
		this.memberChangeHistory = memberChangeHistory;
	}

	public Date getMemberLastSendTime() {
		return memberLastSendTime;
	}

	public void setMemberLastSendTime(Date memberLastSendTime) {
		this.memberLastSendTime = memberLastSendTime;
	}

	public String getMemberSendHistory() {
		return memberSendHistory;
	}

	public void setMemberSendHistory(String memberSendHistory) {
		this.memberSendHistory = memberSendHistory;
	}

	public String getExamScore() {
		return examScore;
	}

	public void setExamScore(String examScore) {
		this.examScore = examScore;
	}

	public Date getNextExamTime() {
		return nextExamTime;
	}

	public void setNextExamTime(Date nextExamTime) {
		this.nextExamTime = nextExamTime;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public Date getStudyTime() {
		return studyTime;
	}

	public void setStudyTime(Date studyTime) {
		this.studyTime = studyTime;
	}

	public Date getAdvDate() {
		return advDate;
	}

	public void setAdvDate(Date advDate) {
		this.advDate = advDate;
	}

	public String getStuName() {
		return stuName;
	}

	public void setStuName(String stuName) {
		this.stuName = stuName;
	}

	public String getAdvCourse() {
		return advCourse;
	}

	public void setAdvCourse(String advCourse) {
		this.advCourse = advCourse;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceFrom() {
		return sourceFrom;
	}

	public void setSourceFrom(String sourceFrom) {
		this.sourceFrom = sourceFrom;
	}

	public String getAdvType() {
		return advType;
	}

	public void setAdvType(String advType) {
		this.advType = advType;
	}

	public String getRecEmp() {
		return recEmp;
	}

	public void setRecEmp(String recEmp) {
		this.recEmp = recEmp;
	}

	public String getAdvisor() {
		return advisor;
	}

	public void setAdvisor(String advisor) {
		this.advisor = advisor;
	}

	public String getCurrentPlan() {
		return currentPlan;
	}

	public void setCurrentPlan(String currentPlan) {
		this.currentPlan = currentPlan;
	}

	public String getStuType() {
		return stuType;
	}

	public void setStuType(String stuType) {
		this.stuType = stuType;
	}

	public Date getNextVisitTime() {
		return nextVisitTime;
	}

	public void setNextVisitTime(Date nextVisitTime) {
		this.nextVisitTime = nextVisitTime;
	}

	public Date getStatusDefineTime() {
		return statusDefineTime;
	}

	public void setStatusDefineTime(Date statusDefineTime) {
		this.statusDefineTime = statusDefineTime;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getIsMerried() {
		return isMerried;
	}

	public void setIsMerried(String isMerried) {
		this.isMerried = isMerried;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSpeciality() {
		return speciality;
	}

	public void setSpeciality(String speciality) {
		this.speciality = speciality;
	}

	public String getFinishSchool() {
		return finishSchool;
	}

	public void setFinishSchool(String finishSchool) {
		this.finishSchool = finishSchool;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getCurrentSchool() {
		return currentSchool;
	}

	public void setCurrentSchool(String currentSchool) {
		this.currentSchool = currentSchool;
	}

	public String getCurrentGrade() {
		return currentGrade;
	}

	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}

	public String getCollegeScore() {
		return collegeScore;
	}

	public void setCollegeScore(String collegeScore) {
		this.collegeScore = collegeScore;
	}

	public String getNeedScore() {
		return needScore;
	}

	public void setNeedScore(String needScore) {
		this.needScore = needScore;
	}

	public String getHopeDegree() {
		return hopeDegree;
	}

	public void setHopeDegree(String hopeDegree) {
		this.hopeDegree = hopeDegree;
	}

	public String getEnDegree() {
		return enDegree;
	}

	public void setEnDegree(String enDegree) {
		this.enDegree = enDegree;
	}

	public String getInfoDesc() {
		return infoDesc;
	}

	public void setInfoDesc(String infoDesc) {
		this.infoDesc = infoDesc;
	}

	public String getTraceDesc() {
		return traceDesc;
	}

	public void setTraceDesc(String traceDesc) {
		this.traceDesc = traceDesc;
	}

	public Date getRecentTraceTime() {
		return recentTraceTime;
	}

	public void setRecentTraceTime(Date recentTraceTime) {
		this.recentTraceTime = recentTraceTime;
	}

	public String getIntroToAgency() {
		return introToAgency;
	}

	public void setIntroToAgency(String introToAgency) {
		this.introToAgency = introToAgency;
	}

	public String getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(String signStatus) {
		this.signStatus = signStatus;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getTuition() {
		return tuition;
	}

	public void setTuition(String tuition) {
		this.tuition = tuition;
	}

	public String getRealMoney() {
		return realMoney;
	}

	public void setRealMoney(String realMoney) {
		this.realMoney = realMoney;
	}

	public Date getDealDate() {
		return dealDate;
	}

	public void setDealDate(Date dealDate) {
		this.dealDate = dealDate;
	}

	public String getDealPerson() {
		return dealPerson;
	}

	public void setDealPerson(String dealPerson) {
		this.dealPerson = dealPerson;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/** default constructor */
	public StuInfo() {
	}

	// Property accessors
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

	public String getReportSchool() {
		return reportSchool;
	}

	public void setReportSchool(String reportSchool) {
		this.reportSchool = reportSchool;
	}

	public String getIsExam() {
		return isExam;
	}

	public void setIsExam(String isExam) {
		this.isExam = isExam;
	}

	public String getIsStu() {
		return isStu;
	}

	public void setIsStu(String isStu) {
		this.isStu = isStu;
	}

	public String getRealMoney1() {
		return realMoney1;
	}

	public void setRealMoney1(String realMoney1) {
		this.realMoney1 = realMoney1;
	}

	public String getRealMoney2() {
		return realMoney2;
	}

	public void setRealMoney2(String realMoney2) {
		this.realMoney2 = realMoney2;
	}

	public String getRealMoney3() {
		return realMoney3;
	}

	public void setRealMoney3(String realMoney3) {
		this.realMoney3 = realMoney3;
	}

	public String getContact1() {
		return contact1;
	}

	public void setContact1(String contact1) {
		this.contact1 = contact1;
	}

	public String getRealMoney4() {
		return realMoney4;
	}

	public void setRealMoney4(String realMoney4) {
		this.realMoney4 = realMoney4;
	}

	public String getRealMoney5() {
		return realMoney5;
	}

	public void setRealMoney5(String realMoney5) {
		this.realMoney5 = realMoney5;
	}
}