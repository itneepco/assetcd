package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the master_data_upload_error database table.
 * 
 */
@Entity
@Table(name="master_data_upload_error")
@NamedQuery(name="MasterDataUploadError.findAll", query="SELECT m FROM MasterDataUploadError m")
public class MasterDataUploadError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	private String code;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;

	private String descr;

	private String error;

	private String ntab1;

	private String ntab1d;

	@Column(name="RAW_REC")
	private String rawRec;

	@Column(name="REC_INDEX")
	private int recIndex;

	@Column(name="REF_ID")
	private String refId;

	@Column(name="`TABLE`")
	private String table;

	@Column(name="UPLOAD_ID")
	private String uploadId;

	@Column(name="USER_CODE")
	private String userCode;

	public MasterDataUploadError() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescr() {
		return descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getNtab1() {
		return ntab1;
	}

	public void setNtab1(String ntab1) {
		this.ntab1 = ntab1;
	}

	public String getNtab1d() {
		return ntab1d;
	}

	public void setNtab1d(String ntab1d) {
		this.ntab1d = ntab1d;
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

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
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

	

}