package com.alphasta.cms.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "WORKPLAN")
public class WorkPlan extends BaseModel {

	// Fields

	private Integer id;
	private Date startDate;
	private Date endDate;
	private String content;
	private Employees employee;

	/** default constructor */
	public WorkPlan() {
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

	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@ManyToOne(cascade = {}, fetch = FetchType.LAZY)
	@JoinColumn(name = "EMPID")
	public Employees getEmployee() {
		return employee;
	}

	public void setEmployee(Employees employee) {
		this.employee = employee;
	}

	@Column(name="STARTDATE")
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	@Column(name="ENDDATE")
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

}