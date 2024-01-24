package com.assetcd.vo;

import java.io.Serializable;

import com.assetcd.data.model.MappingNewCode;

public class CreateCommand implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private int mappingCodeId;
	private MappingNewCode mappingNewCode;
	//private int mfglCode;
	private String preDesc;
	private String postDesc;
	
	/*public int getMfglCode() {
		return mfglCode;
	}
	public void setMfglCode(int mfglCode) {
		this.mfglCode = mfglCode;
	}*/
	public int getMappingCodeId() {
		return mappingCodeId;
	}
	public void setMappingCodeId(int mappingCodeId) {
		this.mappingCodeId = mappingCodeId;
	}
	public MappingNewCode getMappingNewCode() {
		return mappingNewCode;
	}
	public void setMappingNewCode(MappingNewCode mappingNewCode) {
		this.mappingNewCode = mappingNewCode;
	}
	public String getPreDesc() {
		return preDesc;
	}
	public void setPreDesc(String preDesc) {
		this.preDesc = preDesc;
	}
	public String getPostDesc() {
		return postDesc;
	}
	public void setPostDesc(String postDesc) {
		this.postDesc = postDesc;
	}
	
	

}
