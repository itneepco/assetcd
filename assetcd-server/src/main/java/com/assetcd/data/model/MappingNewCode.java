package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mapping_new_code database table.
 * 
 */
@Entity
@Table(name="mapping_new_code")
@NamedQuery(name="MappingNewCode.findAll", query="SELECT m FROM MappingNewCode m")
public class MappingNewCode implements Serializable {
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

	@Column(name="NEW_ASSET_CODE")
	private String newAssetCode;

	@Column(name="NEW_ASSET_DESC")
	private String newAssetDesc;

	@Column(name="NEW_ASSET_SHORT_DESC")
	private String newAssetShortDesc;
	
	@Column(name="NEW_MFGL_CODE")
	private Integer newMfglCode;

	@Column(name="PROJ_CODE")
	private String projCode;

	private String rem1;

	private int slno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="USER_CODE")
	private String userCode;

	public MappingNewCode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAssetCode() {
		return this.assetCode;
	}

	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}

	public String getAssetDesc() {
		return this.assetDesc;
	}

	public void setAssetDesc(String assetDesc) {
		this.assetDesc = assetDesc;
	}

	public Date getCreatedAt() {
		return this.createdAt;
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

	public String getNewAssetCode() {
		return this.newAssetCode;
	}

	public void setNewAssetCode(String newAssetCode) {
		this.newAssetCode = newAssetCode;
	}

	public String getNewAssetDesc() {
		return this.newAssetDesc;
	}

	public void setNewAssetDesc(String newAssetDesc) {
		this.newAssetDesc = newAssetDesc;
	}

	public String getNewAssetShortDesc() {
		return this.newAssetShortDesc;
	}

	public void setNewAssetShortDesc(String newAssetShortDesc) {
		this.newAssetShortDesc = newAssetShortDesc;
	}

	public String getProjCode() {
		return this.projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getRem1() {
		return this.rem1;
	}

	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}

	public int getSlno() {
		return this.slno;
	}

	public void setSlno(int slno) {
		this.slno = slno;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return this.updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public Integer getNewMfglCode() {
		return newMfglCode;
	}

	public void setNewMfglCode(Integer newMfglCode) {
		this.newMfglCode = newMfglCode;
	}

}