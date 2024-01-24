package com.assetcd.service;

import com.assetcd.data.model.MappingCode;
import com.assetcd.data.model.MappingNewCode;
import com.assetcd.data.model.MappingRejectedCode;
import com.assetcd.exception.AlreadyProcessedException;
import com.assetcd.exception.MappingNotFoundException;

public interface TxService {
	
	public MappingNewCode create(MappingNewCode mappingNewCode, int mappingCodeId, String userCode) throws AlreadyProcessedException;
	public MappingRejectedCode reject(MappingRejectedCode mappingRejectedCode, int mappingCodeId, String userCode) throws AlreadyProcessedException;
	public MappingNewCode update(MappingNewCode mappingNewCode, int mappingNewCodeId, String userCode) throws MappingNotFoundException;
	public MappingCode updateRejected(MappingCode mappingCode, int mappingRejectedCodeId, String userCode) throws MappingNotFoundException;
	public MappingCode resendRejected(MappingRejectedCode mappingRejectedCode, String userCode) throws MappingNotFoundException;

}
