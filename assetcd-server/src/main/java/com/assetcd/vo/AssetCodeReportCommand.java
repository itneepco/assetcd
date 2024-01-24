package com.assetcd.vo;

import java.io.Serializable;
import java.util.List;

public class AssetCodeReportCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String codeStart;
	private List<String> projCodes;
	
	
	public String getCodeStart() {
		return codeStart;
	}
	public void setCodeStart(String codeStart) {
		this.codeStart = codeStart;
	}
	public List<String> getProjCodes() {
		return projCodes;
	}
	public void setProjCodes(List<String> projCodes) {
		this.projCodes = projCodes;
	}
	
	

}
