package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mapping_new_code_upload_error database table.
 * 
 */
@Entity
@Table(name="mapping_new_code_upload_error")
@NamedQuery(name="MappingNewCodeUploadError.findAll", query="SELECT m FROM MappingNewCodeUploadError m")
public class MappingNewCodeUploadError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ASSET_CODE")
	private String assetCode;

	@Column(name="ASSET_DESC")
	private String assetDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;

	private String error;

	@Column(name="MFGL_CODE")
	private int mfglCode;

	@Column(name="NEW_ASSET_CODE")
	private String newAssetCode;

	@Column(name="NEW_ASSET_DESC")
	private String newAssetDesc;

	@Column(name="NEW_ASSET_SHORT_DESC")
	private String newAssetShortDesc;
	
	@Column(name="NEW_MFGL_CODE")
	private int newMfglCode;

	@Column(name="PROJ_CODE")
	private String projCode;

	@Column(name="RAW_REC")
	private String rawRec;

	@Column(name="REC_INDEX")
	private int recIndex;

	private String rem1;

	private String slno;

	@Column(name="UPLOAD_ID")
	private String uploadId;

	@Column(name="USER_CODE")
	private String userCode;

	public MappingNewCodeUploadError() {
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public int getMfglCode() {
		return mfglCode;
	}

	public void setMfglCode(int mfglCode) {
		this.mfglCode = mfglCode;
	}

	public String getNewAssetCode() {
		return newAssetCode;
	}

	public void setNewAssetCode(String newAssetCode) {
		this.newAssetCode = newAssetCode;
	}

	public String getNewAssetDesc() {
		return newAssetDesc;
	}

	public void setNewAssetDesc(String newAssetDesc) {
		this.newAssetDesc = newAssetDesc;
	}

	public String getNewAssetShortDesc() {
		return newAssetShortDesc;
	}

	public void setNewAssetShortDesc(String newAssetShortDesc) {
		this.newAssetShortDesc = newAssetShortDesc;
	}

	public String getProjCode() {
		return projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getRawRec() {
		return rawRec;
	}

	public void setRawRec(String rawRec) {
		this.rawRec = rawRec;
	}

	public int getRecIndex() {
		return recIndex;
	}

	public void setRecIndex(int recIndex) {
		this.recIndex = recIndex;
	}

	public String getRem1() {
		return rem1;
	}

	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}

	public String getSlno() {
		return slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getUploadId() {
		return uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public int getNewMfglCode() {
		return newMfglCode;
	}

	public void setNewMfglCode(int newMfglCode) {
		this.newMfglCode = newMfglCode;
	}

	
}