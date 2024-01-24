package com.assetcd.vo;

import java.util.Date;

public interface MFGL {
	public Integer getId();
	public String getSubClass();
	public Date getCreatedAt();
	public Date getDeletedAt();
	public String getDescr();
	public String getGlCode();
	public Integer getMfglCode();
	public String getStatus();
	public Date getUpdatedAt();
	public String getUserCode();	
	public String getGlDesc();
}

/*public class MFGL {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String subClass;
	private Date createdAt;
	private Date deletedAt;
	private String descr;
	private String glCode;
	private Integer mfglCode;
	private String status;
	private Date updatedAt;
	private String userCode;
	private String glDesc;
	
	public MFGL() {}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSubClass() {
		return subClass;
	}
	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getDeletedAt() {
		return deletedAt;
	}
	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}
	public String getDescr() {
		return descr;
	}
	public void setDescr(String descr) {
		this.descr = descr;
	}
	public String getGlCode() {
		return glCode;
	}
	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}
	public Integer getMfglCode() {
		return mfglCode;
	}
	public void setMfglCode(Integer mfglCode) {
		this.mfglCode = mfglCode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public String getUserCode() {
		return userCode;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public String getGlDesc() {
		return glDesc;
	}
	public void setGlDesc(String glDesc) {
		this.glDesc = glDesc;
	}
	
	
}*/
