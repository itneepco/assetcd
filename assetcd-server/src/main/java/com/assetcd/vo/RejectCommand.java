package com.assetcd.vo;

import java.io.Serializable;

import com.assetcd.data.model.MappingRejectedCode;

public class RejectCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int mappingCodeId;
	private MappingRejectedCode mappingRejectedCode;
	
	public int getMappingCodeId() {
		return mappingCodeId;
	}
	public void setMappingCodeId(int mappingCodeId) {
		this.mappingCodeId = mappingCodeId;
	}
	public MappingRejectedCode getMappingRejectedCode() {
		return mappingRejectedCode;
	}
	public void setMappingRejectedCode(MappingRejectedCode mappingRejectedCode) {
		this.mappingRejectedCode = mappingRejectedCode;
	}
	
	

}
