package com.assetcd.vo;

import java.io.Serializable;

public class UploadCodesCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String table;
	private String parentCode;
	private String parentAbsCode;
	private String parentGrpId;
	private String parentFullCode;
	private int fullCodeLength;
	
	
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentAbsCode() {
		return parentAbsCode;
	}
	public void setParentAbsCode(String parentAbsCode) {
		this.parentAbsCode = parentAbsCode;
	}
	public String getParentGrpId() {
		return parentGrpId;
	}
	public void setParentGrpId(String parentGrpId) {
		this.parentGrpId = parentGrpId;
	}
	public String getParentFullCode() {
		return parentFullCode;
	}
	public void setParentFullCode(String parentFullCode) {
		this.parentFullCode = parentFullCode;
	}
	public int getFullCodeLength() {
		return fullCodeLength;
	}
	public void setFullCodeLength(int fullCodeLength) {
		this.fullCodeLength = fullCodeLength;
	}
	
	

}
