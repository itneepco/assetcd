package com.assetcd.vo;

import java.io.Serializable;

import com.assetcd.data.model.MappingCode;

public class UpdateRejectedCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int mappingRejectedCodeId;
	private MappingCode mappingCode;
	
	public int getMappingRejectedCodeId() {
		return mappingRejectedCodeId;
	}
	public void setMappingRejectedCodeId(int mappingRejectedCodeId) {
		this.mappingRejectedCodeId = mappingRejectedCodeId;
	}
	public MappingCode getMappingCode() {
		return mappingCode;
	}
	public void setMappingCode(MappingCode mappingCode) {
		this.mappingCode = mappingCode;
	}
	
	

}
