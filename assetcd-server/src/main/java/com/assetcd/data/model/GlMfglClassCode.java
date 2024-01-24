package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the gl_mfgl_class_code database table.
 * 
 */
@Entity
@Table(name="gl_mfgl_class_code")
@NamedQuery(name="GlMfglClassCode.findAll", query="SELECT g FROM GlMfglClassCode g")
public class GlMfglClassCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="SUB_CLASS")
	private String subClass;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="deleted_at")
	private Date deletedAt;

	private String descr;

	@Column(name="GL_CODE")
	private String glCode;
	
	//@Transient
	//private String glDesc;

	@Column(name="MFGL_CODE")
	private int mfglCode;

	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;
	
	@Column(name="USER_CODE")
	private String userCode;

	public GlMfglClassCode() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubClass() {
		return this.subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
	}

	public Date getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getDeletedAt() {
		return this.deletedAt;
	}

	public void setDeletedAt(Date deletedAt) {
		this.deletedAt = deletedAt;
	}

	public String getDescr() {
		return this.descr;
	}

	public void setDescr(String descr) {
		this.descr = descr;
	}

	public String getGlCode() {
		return this.glCode;
	}

	public void setGlCode(String glCode) {
		this.glCode = glCode;
	}

	public int getMfglCode() {
		return this.mfglCode;
	}

	public void setMfglCode(int mfglCode) {
		this.mfglCode = mfglCode;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getUpdatedAt() {
		return this.updatedAt;
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
	
	/*public String getGlDesc() {
		return glDesc;
	}

	public void setGlDesc(String glDesc) {
		this.glDesc = glDesc;
	}*/

}