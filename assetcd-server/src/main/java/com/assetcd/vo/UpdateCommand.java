package com.assetcd.vo;

import java.io.Serializable;

import com.assetcd.data.model.MappingNewCode;

public class UpdateCommand implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		private int mappingNewCodeId;
		private MappingNewCode mappingNewCode;
		private String preDesc;
		private String postDesc;
		
		
		public int getMappingNewCodeId() {
			return mappingNewCodeId;
		}
		public void setMappingNewCodeId(int mappingNewCodeId) {
			this.mappingNewCodeId = mappingNewCodeId;
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
