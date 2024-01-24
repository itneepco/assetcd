package com.assetcd.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.assetcd.data.model.AssetCodeMas;
import com.assetcd.data.model.AssetCodeMasDesc;
import com.assetcd.data.model.MappingCode;
import com.assetcd.data.model.MappingNewCode;
import com.assetcd.data.model.MappingRejectedCode;
import com.assetcd.data.repository.AssetCodeMasDescRepository;
import com.assetcd.data.repository.AssetCodeMasRepository;
import com.assetcd.data.repository.MappingCodeRepository;
import com.assetcd.data.repository.MappingNewCodeRepository;
import com.assetcd.data.repository.MappingRejectedCodeRepository;
import com.assetcd.exception.AlreadyProcessedException;
import com.assetcd.exception.MappingNotFoundException;

@Service
public class TxServiceImpl implements TxService {
	
	@Autowired
	MappingCodeRepository mappingCodeRepository;
	
	@Autowired
	MappingNewCodeRepository mappingNewCodeRepository;
	
	@Autowired
	MappingRejectedCodeRepository mappingRejectedCodeRepository;
	
	@Autowired
	AssetCodeMasRepository assetCodeMasRepository;
	
	@Autowired
	AssetCodeMasDescRepository assetCodeMasDescRepository;
	
	
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public MappingNewCode create(MappingNewCode mappingNewCode, int mappingCodeId, String userCode) throws AlreadyProcessedException {
		
		String runNo = "000001";
		String nac = mappingNewCode.getNewAssetCode().substring(0, 6);
		
		MappingCode mc = mappingCodeRepository.getOne(mappingCodeId);
		if (mc == null) {
			throw new AlreadyProcessedException("The mapping code is already processed.");
		}
		
		MappingNewCode mncex = mappingNewCodeRepository.findByProjCodeAndAssetCode(mappingNewCode.getProjCode(), mappingNewCode.getAssetCode());
		if (mncex != null) {
			String error = "PROJ_CODE + ASSET_CODE already exists";
			throw new AlreadyProcessedException(error);
		}
		
		AssetCodeMas acm = assetCodeMasRepository.findByNewCode(nac);
		AssetCodeMasDesc acmd = assetCodeMasDescRepository.findByNewCodeAndNewDescAndShortDesc(nac, mappingNewCode.getNewAssetDesc(), mappingNewCode.getNewAssetShortDesc());
		if (acm != null) {
			runNo = prependZeros(acm.getNewRunno() + 1);
			acm.setNewRunno(Integer.parseInt(runNo));
		} else {
			acm = constructAssetCodeMas(mappingNewCode, Integer.parseInt(runNo), userCode);
		}
		String nac1 = nac + runNo;
		Date time = new Date();
		mappingNewCode.setNewAssetCode(nac1);
		mappingNewCode.setUserCode(userCode);
		mappingNewCode.setCreatedAt(time);
		mappingNewCode.setUpdatedAt(time);
		mappingNewCode.setUpdatedBy(userCode);
		MappingNewCode mnc = mappingNewCodeRepository.save(mappingNewCode);
		acm = assetCodeMasRepository.save(acm);
		//acmd = assetCodeMasDescRepository.save(acmd);
		if (acmd == null) {
			acmd = constructAssetCodeMasDesc(mappingNewCode.getMfglCode(), nac, mappingNewCode.getNewAssetDesc(), mappingNewCode.getNewAssetShortDesc(), userCode);
			acmd = assetCodeMasDescRepository.save(acmd);
		}
		mappingCodeRepository.deleteById(mappingCodeId);
		return mnc;
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public MappingRejectedCode reject(MappingRejectedCode mappingRejectedCode, int mappingCodeId, String userCode) throws AlreadyProcessedException {
		
		MappingCode mc = mappingCodeRepository.getOne(mappingCodeId);
		if (mc == null) {
			throw new AlreadyProcessedException("The mapping code is already processed.");
		}
		mappingRejectedCode.setUserCode(userCode);
		MappingRejectedCode mrc = mappingRejectedCodeRepository.save(mappingRejectedCode);
		mappingCodeRepository.deleteById(mappingCodeId);
		return mrc;
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public MappingNewCode update(MappingNewCode mappingNewCode, int mappingNewCodeId, String userCode) throws MappingNotFoundException {
		
		String runNo = "000001";
		String nac = mappingNewCode.getNewAssetCode().substring(0, 6);
		AssetCodeMas acm = null;
		AssetCodeMasDesc acmd = assetCodeMasDescRepository.findByNewCodeAndNewDescAndShortDesc(nac, mappingNewCode.getNewAssetDesc(), mappingNewCode.getNewAssetShortDesc());
		MappingNewCode mnc = mappingNewCodeRepository.getOne(mappingNewCodeId);
		if (mnc == null) {
			throw new MappingNotFoundException("The mapping new code not found: " + mappingNewCodeId);
		}
		if (mnc.getNewAssetCode().equals(mappingNewCode.getNewAssetCode())) {
			// sub asset code not changed, update desc
		} else {
			// sub asset code changed
			acm = assetCodeMasRepository.findByNewCode(nac);
			if (acm != null) {
				runNo = prependZeros(acm.getNewRunno() + 1);
				acm.setNewRunno(Integer.parseInt(runNo));
			} else {
				acm = constructAssetCodeMas(mappingNewCode, Integer.parseInt(runNo), userCode);
			}
			String nac1 = nac + runNo;
			mnc.setNewAssetCode(nac1);
			acm = assetCodeMasRepository.save(acm);
		}
		
		
		//mnc.setUserCode(userCode);
		mnc.setUpdatedBy(userCode);
		mnc.setUpdatedAt(new Date());
		mnc.setNewMfglCode(mappingNewCode.getNewMfglCode());
		mnc.setNewAssetDesc(mappingNewCode.getNewAssetDesc());
		mnc.setNewAssetShortDesc(mappingNewCode.getNewAssetShortDesc());
		mnc = mappingNewCodeRepository.save(mnc);
		//acm = assetCodeMasRepository.save(acm);
		//acmd = assetCodeMasDescRepository.save(acmd);
		if (acmd == null) {
			acmd = constructAssetCodeMasDesc(mappingNewCode.getMfglCode(), nac, mappingNewCode.getNewAssetDesc(), mappingNewCode.getNewAssetShortDesc(), userCode);
			acmd = assetCodeMasDescRepository.save(acmd);
		}
		return mnc;
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public MappingCode updateRejected(MappingCode mappingCode, int mappingRejectedCodeId, String userCode) throws MappingNotFoundException {
		
		MappingRejectedCode mrc = mappingRejectedCodeRepository.getOne(mappingRejectedCodeId);
		if (mrc == null) {
			throw new MappingNotFoundException("The mapping rejected code not found: " + mappingRejectedCodeId);
		}
		
		mappingCode.setUserCode(userCode);
		mappingCode.setUpdatedAt(new Date());
		MappingCode mc = mappingCodeRepository.save(mappingCode);
		
		mappingRejectedCodeRepository.deleteById(mappingRejectedCodeId);
		return mc;
	}
	
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public MappingCode resendRejected(MappingRejectedCode mappingRejectedCode, String userCode) throws MappingNotFoundException {
		
		MappingRejectedCode mrc = mappingRejectedCodeRepository.getOne(mappingRejectedCode.getId());
		if (mrc == null) {
			throw new MappingNotFoundException("The mapping rejected code not found: " + mappingRejectedCode.getId());
		}
		
		MappingCode mappingCode = mappingRejectedCodeToMappingCode(mrc);
		mappingCode.setUserCode(userCode);
		mappingCode.setUpdatedAt(new Date());
		MappingCode mc = mappingCodeRepository.save(mappingCode);
		
		mappingRejectedCodeRepository.deleteById(mappingRejectedCode.getId());
		return mc;
	}
	
	
	private MappingCode mappingRejectedCodeToMappingCode(MappingRejectedCode mrc) {
		
		MappingCode mc = new MappingCode();
		mc.setProjCode(mrc.getProjCode());
		mc.setMfglCode(mrc.getMfglCode());
		mc.setAssetCode(mrc.getAssetCode());
		mc.setAssetDesc(mrc.getAssetDesc());
		mc.setRem1(mrc.getRem1());
		mc.setSlno(mrc.getSlno());
		mc.setCreatedAt(mrc.getCreatedAt());
		mc.setUpdatedAt(mrc.getUpdatedAt());
		mc.setUserCode(mrc.getUserCode());
		
		return mc;
	}
	
	private AssetCodeMas constructAssetCodeMas(MappingNewCode mnc, int newRunno, String userCode) {
		Date time = new Date();
		AssetCodeMas acm = new AssetCodeMas();
		acm.setMfglCode(mnc.getMfglCode());
		acm.setNewCode(mnc.getNewAssetCode().substring(0, 6));
		acm.setNewRunno(newRunno);
		acm.setNewAssetDesc(mnc.getNewAssetCode());
		acm.setNewAssetShortDesc(mnc.getNewAssetShortDesc());
		acm.setUserCode(userCode);
		acm.setCreatedAt(time);
		acm.setUpdatedAt(time);
		return acm;
	}
	
	private AssetCodeMasDesc constructAssetCodeMasDesc(int mfglCode, String newCode, String newDesc, String shortDesc, String userCode) {
		Date time = new Date();
		AssetCodeMasDesc acmd = new AssetCodeMasDesc();
		acmd.setMfglCode(mfglCode);
		acmd.setNewCode(newCode);
		acmd.setNewDesc(newDesc);
		acmd.setShortDesc(shortDesc);
		acmd.setUserCode(userCode);
		acmd.setCreatedAt(time);
		acmd.setUpdatedAt(time);
		return acmd;
	}
	
	private String prependZeros(int no) {
		
		String nm = no + "";
		for (int i = nm.length(); i < 6; i++) {
			nm = "0" + nm;
		}
		return nm;
	}

}
