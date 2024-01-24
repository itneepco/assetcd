package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mapping_rejected_code database table.
 * 
 */
@Entity
@Table(name="mapping_rejected_code")
@NamedQuery(name="MappingRejectedCode.findAll", query="SELECT m FROM MappingRejectedCode m")
public class MappingRejectedCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ASSET_CODE")
	private String assetCode;

	@Column(name="ASSET_DESC")
	private String assetDesc;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="MFGL_CODE")
	private int mfglCode;

	@Column(name="PROJ_CODE")
	private String projCode;

	@Column(name="REJ_CODE")
	private String rejCode;

	@Column(name="REJ_REM")
	private String rejRem;

	private String rem1;

	private int slno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="USER_CODE")
	private String userCode;

	public MappingRejectedCode() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssetCode() {
		return assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetDesc() {
		return assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getMfglCode() {
		return mfglCode;
	}

	public void setMfglCode(int mfglCode) {
		this.mfglCode = mfglCode;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getRejCode() {
		return rejCode;
	}

	public void setRejCode(String rejCode) {
		this.rejCode = rejCode;
	}

	public String getRejRem() {
		return rejRem;
	}

	public void setRejRem(String rejRem) {
		this.rejRem = rejRem;
	}

	public String getRem1() {
		return rem1;
	}

	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}

	public int getSlno() {
		return slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
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

	
}