package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mapping_new_asset_code_upload_error database table.
 * 
 */
@Entity
@Table(name="mapping_new_asset_code_upload_error")
@NamedQuery(name="MappingNewAssetCodeUploadError.findAll", query="SELECT m FROM MappingNewAssetCodeUploadError m")
public class MappingNewAssetCodeUploadError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ASSET_CODE")
	private String assetCode;

	@Column(name="ASSET_DESC")
	private String assetDesc;

	@Column(name="ASSET_QTY")
	private String assetQty;

	@Column(name="CLASS_CODE")
	private String classCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;

	private String error;

	@Column(name="PROJ_CODE")
	private String projCode;

	@Column(name="RAW_REC")
	private String rawRec;

	@Column(name="REC_INDEX")
	private int recIndex;

	private String rem1;

	private String slno;

	@Column(name="SUB_CLASS")
	private String subClass;

	@Column(name="UPLOAD_ID")
	private String uploadId;

	@Column(name="USER_CODE")
	private String userCode;

	public MappingNewAssetCodeUploadError() {
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

	public String getAssetQty() {
		return this.assetQty;
	}

	public void setAssetQty(String assetQty) {
		this.assetQty = assetQty;
	}

	public String getClassCode() {
		return this.classCode;
	}

	public void setClassCode(String classCode) {
		this.classCode = classCode;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getProjCode() {
		return this.projCode;
	}

	public void setProjCode(String projCode) {
		this.projCode = projCode;
	}

	public String getRawRec() {
		return this.rawRec;
	}

	public void setRawRec(String rawRec) {
		this.rawRec = rawRec;
	}

	public int getRecIndex() {
		return this.recIndex;
	}

	public void setRecIndex(int recIndex) {
		this.recIndex = recIndex;
	}

	public String getRem1() {
		return this.rem1;
	}

	public void setRem1(String rem1) {
		this.rem1 = rem1;
	}

	public String getSlno() {
		return this.slno;
	}

	public void setSlno(String slno) {
		this.slno = slno;
	}

	public String getSubClass() {
		return this.subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public String getUploadId() {
		return this.uploadId;
	}

	public void setUploadId(String uploadId) {
		this.uploadId = uploadId;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}