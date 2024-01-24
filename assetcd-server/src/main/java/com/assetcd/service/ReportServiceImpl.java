package com.assetcd.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assetcd.data.model.MappingNewCode;
import com.assetcd.data.repository.MappingNewCodeRepository;
import com.assetcd.vo.ExportMappingNewCodesCommand;

@Service
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	MappingNewCodeRepository mappingNewCodeRepository;
	
	public void exportMappingNewCodes(PrintWriter writer, ExportMappingNewCodesCommand emnc) throws IOException {
		
		List<String> projCodes = emnc.getProjCodes();
		String assetCode = emnc.getAssetCode();
		System.out.println("===="+projCodes);
		System.out.println("====="+assetCode);
		List<MappingNewCode> mncs = mappingNewCodeRepository.findByProjCodeInAndNewAssetCodeStartingWith(projCodes, assetCode);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("PROJ_CODE", "MFGL_CODE", "ASSET_CODE", "ASSET_DESC", "NEW_MFGL_CODE", "NEW_ASSET_CODE", "NEW_ASSET_DESC", "NEW_ASSET_SHORT_DESC", "REM1", "SLNO", "USER_CODE"));
		
		for (MappingNewCode mnc : mncs) {
			csvPrinter.printRecord(
					mnc.getProjCode(),
					mnc.getMfglCode(),
					mnc.getAssetCode(),
					mnc.getAssetDesc(),
					mnc.getNewMfglCode(),
					mnc.getNewAssetCode(),
					mnc.getNewAssetDesc(),
					mnc.getNewAssetShortDesc(),
					mnc.getRem1(),
					mnc.getSlno(),
					mnc.getUserCode()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}

}
