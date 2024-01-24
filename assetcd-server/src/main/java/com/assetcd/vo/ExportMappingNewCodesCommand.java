package com.assetcd.vo;

import java.util.List;

public class ExportMappingNewCodesCommand {
	
	private static final long serialVersionUID = 1L;
	
	private List<String> projCodes;
	private String assetCode;
	
	
	public List<String> getProjCodes() {
		return projCodes;
	}
	public void setProjCodes(List<String> projCodes) {
		this.projCodes = projCodes;
	}
	public String getAssetCode() {
		return assetCode;
	}
	public void setAssetCode(String assetCode) {
		this.assetCode = assetCode;
	}
	
	

}
