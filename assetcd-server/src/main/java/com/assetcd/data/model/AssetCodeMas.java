package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the asset_code_mas database table.
 * 
 */
@Entity
@Table(name="asset_code_mas")
@NamedQuery(name="AssetCodeMas.findAll", query="SELECT a FROM AssetCodeMas a")
public class AssetCodeMas implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="MFGL_CODE")
	private int mfglCode;

	@Column(name="NEW_ASSET_DESC")
	private String newAssetDesc;

	@Column(name="NEW_ASSET_SHORT_DESC")
	private String newAssetShortDesc;

	@Column(name="NEW_CODE")
	private String newCode;

	@Column(name="NEW_RUNNO")
	private int newRunno;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="USER_CODE")
	private String userCode;

	public AssetCodeMas() {
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

	public int getMfglCode() {
		return this.mfglCode;
	}

	public void setMfglCode(int mfglCode) {
		this.mfglCode = mfglCode;
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

	public String getNewCode() {
		return this.newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	public int getNewRunno() {
		return this.newRunno;
	}

	public void setNewRunno(int newRunno) {
		this.newRunno = newRunno;
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