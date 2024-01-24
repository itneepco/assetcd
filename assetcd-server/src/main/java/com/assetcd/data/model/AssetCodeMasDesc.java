package com.assetcd.data.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the asset_code_mas_desc database table.
 * 
 */
@Entity
@Table(name="asset_code_mas_desc")
@NamedQuery(name="AssetCodeMasDesc.findAll", query="SELECT a FROM AssetCodeMasDesc a")
public class AssetCodeMasDesc implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_at")
	private Date createdAt;

	@Column(name="MFGL_CODE")
	private int mfglCode;

	@Column(name="NEW_CODE")
	private String newCode;
	
	@Column(name="NEW_DESC")
	private String newDesc;

	/*@Column(name="POST_DESC")
	private String postDesc;

	@Column(name="PRE_DESC")
	private String preDesc;*/

	@Column(name="SHORT_DESC")
	private String shortDesc;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="updated_at")
	private Date updatedAt;

	@Column(name="USER_CODE")
	private String userCode;

	public AssetCodeMasDesc() {
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

	public String getNewCode() {
		return this.newCode;
	}

	public void setNewCode(String newCode) {
		this.newCode = newCode;
	}

	/*public String getPostDesc() {
		return this.postDesc;
	}

	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}

	public String getPreDesc() {
		return this.preDesc;
	}

	public void setPreDesc(String preDesc) {
		this.preDesc = preDesc;
	}*/

	public String getShortDesc() {
		return this.shortDesc;
	}

	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
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

	public String getNewDesc() {
		return newDesc;
	}

	public void setNewDesc(String newDesc) {
		this.newDesc = newDesc;
	}
	
	

}