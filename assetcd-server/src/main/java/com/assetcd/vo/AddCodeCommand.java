package com.assetcd.vo;

import java.io.Serializable;
import java.util.Map;

public class AddCodeCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String table;
	private Map code;
		
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
	}
	public Map getCode() {
		return code;
	}
	public void setCode(Map code) {
		this.code = code;
	}
	
}
