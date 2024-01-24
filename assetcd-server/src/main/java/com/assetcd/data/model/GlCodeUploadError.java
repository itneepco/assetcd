package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gl_code_upload_error database table.
 * 
 */
@Entity
@Table(name="gl_code_upload_error")
@NamedQuery(name="GlCodeUploadError.findAll", query="SELECT g FROM GlCodeUploadError g")
public class GlCodeUploadError implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATED_AT")
	private Date createdAt;

	private String descr;

	private String error;

	@Column(name="GL_CODE")
	private String glCode;

	@Column(name="RAW_REC")
	private String rawRec;

	@Column(name="REC_INDEX")
	private int recIndex;

	@Column(name="REF_ID")
	private String refId;

	@Column(name="UPLOAD_ID")
	private String uploadId;

	@Column(name="USER_CODE")
	private String userCode;

	public GlCodeUploadError() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getGlCode() {
		return this.glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
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

	public String getRefId() {
		return this.refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
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