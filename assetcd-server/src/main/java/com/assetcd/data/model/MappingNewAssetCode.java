package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the mapping_new_asset__code database table.
 * 
 */
@Entity
@Table(name="mapping_new_asset_code")
@NamedQuery(name="MappingNewAssetCode.findAll", query="SELECT m FROM MappingNewAssetCode m")
public class MappingNewAssetCode implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Column(name="ASSET_CODE")
	private String assetCode;

	@Column(name="ASSET_DESC")
	private String assetDesc;

	@Column(name="ASSET_QTY")
	private int assetQty;

	@Column(name="CLASS_CODE")
	private String classCode;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="PROJ_CODE")
	private String projCode;

	private String rem1;

	private int slno;

	@Column(name="SUB_CLASS")
	private String subClass;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="updated_by")
	private String updatedBy;

	@Column(name="USER_CODE")
	private String userCode;

	public MappingNewAssetCode() {
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

	public int getAssetQty() {
		return this.assetQty;
	}

	public void setAssetQty(int assetQty) {
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

	public String getSubClass() {
		return this.subClass;
	}

	public void setSubClass(String subClass) {
		this.subClass = subClass;
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

}