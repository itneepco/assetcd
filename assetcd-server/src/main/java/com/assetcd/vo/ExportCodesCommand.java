package com.assetcd.vo;

import java.io.Serializable;

public class ExportCodesCommand implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private String table;
	private String parentCode;
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
	public int getFullCodeLength() {
		return fullCodeLength;
	}
	public void setFullCodeLength(int fullCodeLength) {
		this.fullCodeLength = fullCodeLength;
	}

}
