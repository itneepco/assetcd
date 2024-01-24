package com.assetcd.data.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CL_COMP")
	private String clComp;
	
	@Column(name="CL_COMP_SHORT")
	private String clCompShort;

	private String comp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name="LOC_CL_COMP")
	private String locClComp;

	private String packg;

	@Column(name="PACKG_SHORT")
	private String packgShort;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="USER_CODE")
	private String userCode;

	public Company() {
	}

	public String getClComp() {
		return this.clComp;
	}

	public void setClComp(String clComp) {
		this.clComp = clComp;
	}

	public String getClCompShort() {
		return this.clCompShort;
	}

	public void setClCompShort(String clCompShort) {
		this.clCompShort = clCompShort;
	}

	public String getComp() {
		return this.comp;
	}

	public void setComp(String comp) {
		this.comp = comp;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocClComp() {
		return this.locClComp;
	}

	public void setLocClComp(String locClComp) {
		this.locClComp = locClComp;
	}

	public String getPackg() {
		return this.packg;
	}

	public void setPackg(String packg) {
		this.packg = packg;
	}

	public String getPackgShort() {
		return this.packgShort;
	}

	public void setPackgShort(String packgShort) {
		this.packgShort = packgShort;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}