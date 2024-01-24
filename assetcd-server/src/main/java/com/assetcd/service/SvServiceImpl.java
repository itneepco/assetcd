package com.assetcd.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.assetcd.data.model.AssetCodeMas;
import com.assetcd.data.model.AssetCodeMasDesc;
import com.assetcd.data.model.GlMfglClassCode;
import com.assetcd.data.model.MappingCode;
import com.assetcd.data.model.MappingCodeUploadError;
import com.assetcd.data.model.MappingNewAssetCode;
import com.assetcd.data.model.MappingNewAssetCodeUploadError;
import com.assetcd.data.model.MappingNewCode;
import com.assetcd.data.model.MappingNewCodeUploadError;
import com.assetcd.data.model.MappingRejectedCode;
import com.assetcd.data.model.T0506;
import com.assetcd.data.repository.AssetCodeMasDescRepository;
import com.assetcd.data.repository.AssetCodeMasRepository;
import com.assetcd.data.repository.GlMfglClassCodeRepository;
import com.assetcd.data.repository.MappingCodeRepository;
import com.assetcd.data.repository.MappingCodeUploadErrorRepository;
import com.assetcd.data.repository.MappingNewAssetCodeRepository;
import com.assetcd.data.repository.MappingNewAssetCodeUploadErrorRepository;
import com.assetcd.data.repository.MappingNewCodeRepository;
import com.assetcd.data.repository.MappingNewCodeUploadErrorRepository;
import com.assetcd.data.repository.MappingRejectedCodeRepository;
import com.assetcd.data.repository.ProjectRepository;
import com.assetcd.data.repository.T0506Repository;
import com.assetcd.util.AssetcdUtil;


@Service
public class SvServiceImpl implements SvService {
	
	@Autowired
	ApplicationContext context;
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired
	MappingCodeRepository mappingCodeRepository;
	
	@Autowired
	MappingNewCodeRepository mappingNewCodeRepository;
	
	@Autowired
	MappingRejectedCodeRepository mappingRejectedCodeRepository;
	
	@Autowired
	MappingCodeUploadErrorRepository mappingCodeUploadErrorRepository;
	
	@Autowired
	MappingNewCodeUploadErrorRepository mappingNewCodeUploadErrorRepository;
	
	@Autowired
	MappingNewAssetCodeRepository mappingNewAssetCodeRepository;
	
	@Autowired
	MappingNewAssetCodeUploadErrorRepository mappingNewAssetCodeUploadErrorRepository;
	
	@Autowired
	AssetCodeMasRepository assetCodeMasRepository;
	
	@Autowired
	AssetCodeMasDescRepository assetCodeMasDescRepository;
	
	@Autowired
	GlMfglClassCodeRepository glMfglClassCodeRepository;
	
	@Autowired
	T0506Repository t0506REpository;
	
	@Autowired
	ProjectRepository projectRepository;
	
		
	public String uploadAssetCodes(MultipartFile file, String userCode) throws IOException {
		
		final String uploadId = RandomStringUtils.randomAlphanumeric(10);
		boolean isError = false;
		Date date = new Date();
		final Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
		//Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(reader);
		//Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("PROJ_CODE", "MFGL_CODE", "ASSET_CODE", "ASSET_DESC", "REM1", "SLNO").parse(reader);
		//for (CSVRecord record : records) {
		
		CSVParser csvParser = new CSVParser(reader, CSVFormat.RFC4180
                .withFirstRecordAsHeader()
                //.withIgnoreHeaderCase()
                .withTrim());
		Map<String, Integer> header = csvParser.getHeaderMap();
		// todo - validate minimum required columns
		//validateHeader(header);
		
		
		for (CSVRecord record : csvParser) {
			
			// validate data
			if ("".equals(record.get("SLNO")) || !StringUtils.isNumeric(record.get("SLNO"))) {
				String error = "SLNO is not numeric";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			} 
			if ("".equals(record.get("MFGL_CODE")) || !StringUtils.isNumeric(record.get("MFGL_CODE"))) {
				String error = "MFGL_CODE is not numeric";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (record.get("MFGL_CODE").length() != 6) {
				String error = "MFGL_CODE should be 6 digit number.";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.isBlank(record.get("ASSET_DESC"))) {
				String error = "ASSET_DESC should not be blank.";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.length(record.get("ASSET_DESC")) > 1000) {
				String error = "ASSET_DESC length should be <= 1000";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (projectRepository.findByProjCode(record.get("PROJ_CODE")).size() == 0){
				String error = "PROJ_CODE does not exist";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			MappingCode mc = mappingCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), record.get("ASSET_CODE"));
			if (mc != null) {
				String error = "PROJ_CODE + ASSET_CODE already exists (SLNO - " + mc.getSlno() + ")";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			MappingNewCode mnc = mappingNewCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), record.get("ASSET_CODE"));
			if (mnc != null) {
				String error = "PROJ_CODE + ASSET_CODE already exists in the mapped codes (SLNO - " + mnc.getSlno() + ")";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			MappingRejectedCode mrc = mappingRejectedCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), record.get("ASSET_CODE"));
			if (mrc != null) {
				String error = "Mapping for this code is under progress. PROJ_CODE + ASSET_CODE already exists in the rejected codes  (SLNO - " + mrc.getSlno() + ")";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			GlMfglClassCode glMfglClassCode = glMfglClassCodeRepository.findByM(Integer.parseInt(record.get("MFGL_CODE")));
			if (glMfglClassCode == null) {
				String error = "MFGL_CODE does not exist in gl_mfgl_class_code";
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			// save record
			try {
				MappingCode mc1 = constructMappingCode(record, userCode);
				mappingCodeRepository.save(mc1);
			} catch(Exception e) {
				String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
				mappingCodeUploadErrorRepository.save(constructError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
		}

		if (isError) {
			return uploadId;
		} 
		return null;
		
	}
	
	public void downloadErrors(PrintWriter writer, String uploadId, String userCode) throws IOException {
		
		List<MappingCodeUploadError> mcues = mappingCodeUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("PROJ_CODE", "MFGL_CODE", "ASSET_CODE", "ASSET_DESC", "REM1", "SLNO", "ERROR"));
		
		for (MappingCodeUploadError mcue : mcues) {
			csvPrinter.printRecord(
					mcue.getProjCode(),
					mcue.getMfglCode(),
					mcue.getAssetCode(),
					mcue.getAssetDesc(),
					mcue.getRem1(),
					mcue.getSlno(),
					mcue.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
	
	
	
	public String uploadNewAssetCodes(MultipartFile file, String userCode) throws IOException {
		
		final String uploadId = RandomStringUtils.randomAlphanumeric(10);
		boolean isError = false;
		Date date = new Date();
		final Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
		
		CSVParser csvParser = new CSVParser(reader, CSVFormat.RFC4180
                .withFirstRecordAsHeader()
                //.withIgnoreHeaderCase()
                .withTrim());
		Map<String, Integer> header = csvParser.getHeaderMap();
		
		for (CSVRecord record : csvParser) {
			
			// save original record
			try {
				MappingNewAssetCode mnac = constructMappingNewAssetCode(record, userCode);
				mappingNewAssetCodeRepository.save(mnac);
			} catch(Exception e) {
				String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			// validate data
			if ("".equals(record.get("SLNO")) || !StringUtils.isNumeric(record.get("SLNO"))) {
				String error = "SLNO is not numeric";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			/*if ("".equals(record.get("ASSET_SLNO")) || !StringUtils.isNumeric(record.get("ASSET_SLNO"))) {
				String error = "ASSET_SLNO is not numeric";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			/*if (!"0001".equals(record.get("ASSET_SLNO"))) {
				String error = "ASSET_SLNO should be 0001";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			if ("".equals(record.get("ASSET_QTY")) || !StringUtils.isNumeric(record.get("ASSET_QTY"))) {
				String error = "ASSET_QTY is not numeric. ASSET_QTY should be greater than 0 and less than 100000";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			int assetQty = Integer.parseInt(record.get("ASSET_QTY"));
			if (assetQty <= 0 || assetQty > 99999) {
				String error = "ASSET_QTY should be greater than 0 and less than 100000";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			String classCode = record.get("CLASS_CODE");
			if (classCode.length() != 4) {
				String error = "CLASS_CODE should be 4 character.";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			String subClass = record.get("SUB_CLASS");
			if (subClass.length() != 2) {
				String error = "SUB_CLASS should be 2 character.";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.isBlank(record.get("ASSET_DESC"))) {
				String error = "ASSET_DESC should not be blank.";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.length(record.get("ASSET_DESC")) > 1000) {
				String error = "ASSET_DESC length should be <= 1000";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (projectRepository.findByProjCode(record.get("PROJ_CODE")).size() == 0){
				String error = "PROJ_CODE does not exist";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			GlMfglClassCode gmcc = glMfglClassCodeRepository.findByC1(classCode, subClass);
			if (gmcc == null){
				String error = "MFGL CODE does not exist for CLASS_CODE + SUB_CLASS";
				mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			for (int i = 1; i <= assetQty; i++) {
				
				String aq = prependZeros(i, 4);
				String ac = record.get("ASSET_CODE") + aq;
				MappingCode mc = mappingCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), ac);
				if (mc != null) {
					String error = "PROJ_CODE + ASSET_CODE (" + ac + ") already exists (SLNO - " + mc.getSlno() + ")";
					mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				MappingNewCode mnc = mappingNewCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), ac);
				if (mnc != null) {
					String error = "PROJ_CODE + ASSET_CODE (" + ac + ") already exists in the mapped codes (SLNO - " + mnc.getSlno() + ")";
					mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				MappingRejectedCode mrc = mappingRejectedCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), ac);
				if (mrc != null) {
					String error = "Mapping for this code is under progress. PROJ_CODE + ASSET_CODE (" + ac + ") already exists in the rejected codes (SLNO - " + mrc.getSlno() + ")";
					mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				// save record
				try {
					MappingCode mc1 = constructMappingCodeFromNewAssetCode(record, gmcc.getMfglCode(), ac, userCode);
					mappingCodeRepository.save(mc1);
				} catch(Exception e) {
					String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
					mappingNewAssetCodeUploadErrorRepository.save(constructMappingNewAssetCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
			}
			
		}
		
		if (isError) {
			return uploadId;
		} 
		return null;
	}
	
	public void downloadNewAssetCodeErrors(PrintWriter writer, String uploadId, String userCode) throws IOException {
		
		List<MappingNewAssetCodeUploadError> mnacues = mappingNewAssetCodeUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("PROJ_CODE", "CLASS_CODE", "SUB_CLASS", "ASSET_CODE", "ASSET_DESC", "ASSET_QTY", "REM1", "SLNO", "ERROR"));
		
		for (MappingNewAssetCodeUploadError mnacue : mnacues) {
			csvPrinter.printRecord(
					mnacue.getProjCode(),
					mnacue.getClassCode(),
					mnacue.getSubClass(),
					mnacue.getAssetCode(),
					//mnacue.getAssetSlno(),
					mnacue.getAssetDesc(),
					mnacue.getAssetQty(),
					mnacue.getRem1(),
					mnacue.getSlno(),
					mnacue.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
	
	
	
	public String uploadMappingNewCodes(MultipartFile file, String userCode) throws IOException {//, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		final String uploadId = RandomStringUtils.randomAlphanumeric(10);
		boolean isError = false;
		Date date = new Date();
		final Reader reader = new InputStreamReader(file.getInputStream(), "UTF-8");
		
		CSVParser csvParser = new CSVParser(reader, CSVFormat.RFC4180
                .withFirstRecordAsHeader()
                //.withIgnoreHeaderCase()
                .withTrim());
		Map<String, Integer> header = csvParser.getHeaderMap();
		// todo - validate minimum required columns
		//validateHeader(header);
		
		
		for (CSVRecord record : csvParser) {
			
			// validate data
			if ("".equals(record.get("SLNO")) || !StringUtils.isNumeric(record.get("SLNO"))) {
				String error = "SLNO is not numeric";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			} 
			if ("".equals(record.get("MFGL_CODE")) || !StringUtils.isNumeric(record.get("MFGL_CODE"))) {
				String error = "MFGL_CODE is not numeric";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (record.get("MFGL_CODE").length() != 6) {
				String error = "MFGL_CODE should be 6 digit number.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if ("".equals(record.get("NEW_MFGL_CODE")) || !StringUtils.isNumeric(record.get("NEW_MFGL_CODE"))) {
				String error = "NEW_MFGL_CODE is not numeric";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (record.get("NEW_MFGL_CODE").length() != 6) {
				String error = "NEW_MFGL_CODE should be 6 digit number.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.isBlank(record.get("NEW_ASSET_DESC"))) {
				String error = "NEW_ASSET_DESC shoud not be blank";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.length(record.get("NEW_ASSET_DESC")) > 1000) {
				String error = "NEW_ASSET_DESC length should be <= 1000";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.isBlank(record.get("NEW_ASSET_SHORT_DESC"))) {
				String error = "NEW_ASSET_SHORT_DESC shoud not be blank";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.length(record.get("NEW_ASSET_SHORT_DESC")) > 50) {
				String error = "NEW_ASSET_SHORT_DESC length should be <= 50";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (StringUtils.length(record.get("NEW_ASSET_CODE")) != 6) {
				String error = "NEW_ASSET_CODE length should be = 6";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (projectRepository.findByProjCode(record.get("PROJ_CODE")).size() == 0){
				String error = "PROJ_CODE does not exist";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			MappingNewCode mnc = mappingNewCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), record.get("ASSET_CODE"));
			if (mnc != null) {
				String error = "PROJ_CODE + ASSET_CODE already exists (SLNO - " + mnc.getSlno() + ")";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			MappingCode mc = mappingCodeRepository.findByProjCodeAndAssetCode(record.get("PROJ_CODE"), record.get("ASSET_CODE"));
			if (mc == null) {
				String error = "PROJ_CODE + ASSET_CODE does not exist in old mapping codes.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			String newAssetCode = record.get("NEW_ASSET_CODE");
			if (newAssetCode.length() != 6) {
				String error = "NEW_ASSET_CODE should be 6 digits long.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			
			/*String nac = newAssetCode.substring(0, 6);
			String runNo = newAssetCode.substring(6);
			if (!NumberUtils.isDigits(runNo)) {
				String error = "NEW_ASSET_CODE not correct. Last 6 digits should be numeric.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			//int rn = Integer.parseInt(runNo);
			GlMfglClassCode glMfglClassCode = glMfglClassCodeRepository.findByM(Integer.parseInt(record.get("MFGL_CODE")));
			if (glMfglClassCode == null) {
				String error = "MFGL_CODE does not exist in gl_mfgl_class_code";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			glMfglClassCode = glMfglClassCodeRepository.findByM(Integer.parseInt(record.get("NEW_MFGL_CODE")));
			if (glMfglClassCode == null) {
				String error = "NEW_MFGL_CODE does not exist in gl_mfgl_class_code";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (!newAssetCode.substring(0, 4).equals(glMfglClassCode.getGlCode().substring(3))) {
				String error = "First 4 char in NEW_MFGL_CODE does not match with gl base in gl_mfgl_class_code";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (!newAssetCode.substring(4).equals(glMfglClassCode.getSubClass())) {
				String error = "Last 2 char in NEW_MFGL_CODE does not match with sub class in gl_mfgl_class_code";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			//if (!newAssetCode.substring(0, 4).equals(glMfglClassCode.getClassCode())) {
			//	String error = "MFGL_CODE and CLASS_CODE are not matching in gl_mfgl_class_code.";
			//	mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
			//	isError = true;
			//	continue;
			//}
			/*T0506 subAssetClass = t0506REpository.findByC(newAssetCode);
			if (subAssetClass == null) {
				String error = "Sub Asset Class does not exist in master.";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			AssetCodeMas acm = assetCodeMasRepository.findByNewCode(newAssetCode);
			int mRunNo = (acm == null) ? 1 : acm.getNewRunno() + 1; 
			/*if (acm != null && (NumberUtils.compare(rn, mRunNo) < 1)) {
				String error = "Running number should be greater then the sequence in master, which is " + mRunNo + ".";
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			
			String newAssetDesc = record.get("NEW_ASSET_DESC");
			/*String subAssetClassDesc = subAssetClass.getDescr().trim();
			String preDesc = "";
			String postDesc = "";
			if (!"misc".equals(subAssetClassDesc.toLowerCase().trim()) && !"miscellaneous".equals(subAssetClassDesc.toLowerCase().trim())) {
				if (!newAssetDesc.contains(subAssetClassDesc)) {
					String error = "NEW_ASSET_DESC does not contain the sub asset desc.";
					mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				String[] sa = newAssetDesc.split(subAssetClassDesc);
				preDesc = sa.length > 0 ? sa[0] : "";
				postDesc = sa.length > 1 ? sa[1] : "";
			} else {
				preDesc = newAssetDesc;
				postDesc = "";
			}*/
			
			
			String newAssetShortDesc = record.get("NEW_ASSET_SHORT_DESC");
			AssetCodeMasDesc acmd = assetCodeMasDescRepository.findByNewCodeAndNewDescAndShortDesc(newAssetCode, newAssetDesc, newAssetShortDesc);
			
			
			/*ValidationResult vr = validateNewMatCodeWithMasterScheme(record.get("NEW_MAT_CODE"));
			if (!vr.isValid()) {
				String error = vr.getTitle() + " " + vr.getMessage();
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			
			
			// save record
			try {
				MappingNewCode mnc1 = constructMappingNewCode(record, mRunNo, userCode);
				mappingNewCodeRepository.save(mnc1);
				if (acm == null) {
					acm = constructAssetCodeMas(mnc1, newAssetCode, mRunNo, userCode);
				} else {
					acm.setNewRunno(mRunNo);
				}
				assetCodeMasRepository.save(acm);
				if (acmd == null) {
					acmd = constructAssetCodeMasDesc(mnc1, newAssetCode, userCode);
					assetCodeMasDescRepository.save(acmd);
				}
				mappingCodeRepository.deleteById(mc.getId());
			} catch(Exception e) {
				String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
				mappingNewCodeUploadErrorRepository.save(constructMNCError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
		}

		if (isError) {
			return uploadId;
		} 
		return null;
		
	}
	
	public void downloadMappingNewCodeErrors(PrintWriter writer, String uploadId, String userCode) throws IOException {
		
		List<MappingNewCodeUploadError> mncues = mappingNewCodeUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("PROJ_CODE", "MFGL_CODE", "ASSET_CODE", "ASSET_DESC", "NEW_MFGL_CODE", "NEW_ASSET_CODE", "NEW_ASSET_DESC", "NEW_ASSET_SHORT_DESC", "REM1", "SLNO", "ERROR"));
		
		for (MappingNewCodeUploadError mncue : mncues) {
			csvPrinter.printRecord(
					mncue.getProjCode(),
					mncue.getMfglCode(),
					mncue.getAssetCode(),
					mncue.getAssetDesc(),
					mncue.getNewMfglCode(),
					mncue.getNewAssetCode(),
					mncue.getNewAssetDesc(),
					mncue.getNewAssetShortDesc(),
					mncue.getRem1(),
					mncue.getSlno(),
					mncue.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
	}
	
	
	
	
	private MappingCode constructMappingCode(CSVRecord record, String userCode) {
		
		Date time = new Date();
		MappingCode mc = new MappingCode();
		mc.setProjCode(record.get("PROJ_CODE"));
		mc.setMfglCode(Integer.parseInt(record.get("MFGL_CODE")));
		mc.setAssetCode(record.get("ASSET_CODE"));
		mc.setAssetDesc(record.get("ASSET_DESC"));
		mc.setRem1(record.get("REM1"));
		mc.setSlno(Integer.parseInt(record.get("SLNO")));
		mc.setStatus("1");
		mc.setUserCode(userCode);
		mc.setCreatedAt(time);
		mc.setUpdatedAt(time);
		mc.setUpdatedBy(userCode);
		return mc;
	}
	
	private MappingCodeUploadError constructError(CSVRecord record, String userCode, String uploadId, String error, Date date) {
		
		MappingCodeUploadError mcuerror = new MappingCodeUploadError();
		mcuerror.setUploadId(uploadId);
		mcuerror.setRecIndex((int)record.getRecordNumber());
		mcuerror.setProjCode(record.get("PROJ_CODE"));
		mcuerror.setMfglCode(Integer.parseInt(record.get("MFGL_CODE")));
		mcuerror.setAssetCode(record.get("ASSET_CODE"));
		mcuerror.setAssetDesc(record.get("ASSET_DESC"));
		mcuerror.setRem1(record.get("REM1"));
		mcuerror.setSlno(record.get("SLNO"));
		mcuerror.setRawRec(record.toString());
		mcuerror.setError(error);
		mcuerror.setUserCode(userCode);
		mcuerror.setCreatedAt(date);
		return mcuerror;
	}
	
	
	private MappingNewAssetCode constructMappingNewAssetCode(CSVRecord record, String userCode) {
		
		Date time = new Date();
		MappingNewAssetCode mnac = new MappingNewAssetCode();
		mnac.setProjCode(record.get("PROJ_CODE"));
		mnac.setClassCode(record.get("CLASS_CODE"));
		mnac.setSubClass(record.get("SUB_CLASS"));
		mnac.setAssetCode(record.get("ASSET_CODE"));
		//mnac.setAssetSlno(record.get("ASSET_SLNO"));
		mnac.setAssetDesc(record.get("ASSET_DESC"));
		mnac.setAssetQty(Integer.parseInt(record.get("ASSET_QTY")));
		mnac.setRem1(record.get("REM1"));
		mnac.setSlno(Integer.parseInt(record.get("SLNO")));
		mnac.setUserCode(userCode);
		mnac.setCreatedAt(time);
		mnac.setUpdatedAt(time);
		mnac.setUpdatedBy(userCode);
		return mnac;
	}
	
	private MappingNewCodeUploadError constructMNCError(CSVRecord record, String userCode, String uploadId, String error, Date date) {
		
		MappingNewCodeUploadError mncuerror = new MappingNewCodeUploadError();
		mncuerror.setUploadId(uploadId);
		mncuerror.setRecIndex((int)record.getRecordNumber());
		mncuerror.setProjCode(record.get("PROJ_CODE"));
		mncuerror.setMfglCode(Integer.parseInt(record.get("MFGL_CODE")));
		mncuerror.setAssetCode(record.get("ASSET_CODE"));
		mncuerror.setAssetDesc(record.get("ASSET_DESC"));
		mncuerror.setNewMfglCode(Integer.parseInt(record.get("NEW_MFGL_CODE")));
		mncuerror.setNewAssetCode(record.get("NEW_ASSET_CODE"));
		mncuerror.setNewAssetDesc(record.get("NEW_ASSET_DESC"));
		mncuerror.setNewAssetShortDesc(record.get("NEW_ASSET_SHORT_DESC"));
		mncuerror.setRem1(record.get("REM1"));
		mncuerror.setSlno(record.get("SLNO"));
		mncuerror.setRawRec(record.toString());
		mncuerror.setError(error);
		mncuerror.setUserCode(userCode);
		mncuerror.setCreatedAt(date);
		return mncuerror;
	}
	
	
	private MappingNewAssetCodeUploadError constructMappingNewAssetCodeError(CSVRecord record, String userCode, String uploadId, String error, Date date) {
		
		MappingNewAssetCodeUploadError mnacuerror = new MappingNewAssetCodeUploadError();
		mnacuerror.setUploadId(uploadId);
		mnacuerror.setRecIndex((int)record.getRecordNumber());
		mnacuerror.setProjCode(record.get("PROJ_CODE"));
		mnacuerror.setClassCode(record.get("CLASS_CODE"));
		mnacuerror.setSubClass(record.get("SUB_CLASS"));
		mnacuerror.setAssetCode(record.get("ASSET_CODE"));
		//mnacuerror.setAssetSlno(record.get("ASSET_SLNO"));
		mnacuerror.setAssetDesc(record.get("ASSET_DESC"));
		mnacuerror.setAssetQty(record.get("ASSET_QTY"));
		mnacuerror.setRem1(record.get("REM1"));
		mnacuerror.setSlno(record.get("SLNO"));
		mnacuerror.setRawRec(record.toString());
		mnacuerror.setError(error);
		mnacuerror.setUserCode(userCode);
		mnacuerror.setCreatedAt(date);
		return mnacuerror;
	}
	
	
	private MappingNewCode constructMappingNewCode(CSVRecord record, int runNo, String userCode) {
		
		Date time = new Date();
		MappingNewCode mnc = new MappingNewCode();
		mnc.setProjCode(record.get("PROJ_CODE"));
		mnc.setMfglCode(Integer.parseInt(record.get("MFGL_CODE")));
		mnc.setAssetCode(record.get("ASSET_CODE"));
		mnc.setAssetDesc(record.get("ASSET_DESC"));
		mnc.setNewMfglCode(Integer.parseInt(record.get("NEW_MFGL_CODE")));
		mnc.setNewAssetCode(record.get("NEW_ASSET_CODE") + prependZeros(runNo, 6));
		mnc.setNewAssetDesc(record.get("NEW_ASSET_DESC"));
		mnc.setNewAssetShortDesc(record.get("NEW_ASSET_SHORT_DESC"));
		mnc.setRem1(record.get("REM1"));
		mnc.setSlno(Integer.parseInt(record.get("SLNO")));
		mnc.setUserCode(userCode);
		mnc.setCreatedAt(time);
		mnc.setUpdatedAt(time);
		mnc.setUpdatedBy(userCode);
		return mnc;
	}
	
	private MappingCode constructMappingCodeFromNewAssetCode(CSVRecord record, int mfglCode, String ac, String userCode) {
		
		Date time = new Date();
		MappingCode mc = new MappingCode();
		mc.setProjCode(record.get("PROJ_CODE"));
		mc.setMfglCode(mfglCode);
		mc.setAssetCode(ac);
		mc.setAssetDesc(record.get("ASSET_DESC"));
		mc.setRem1(record.get("REM1"));
		mc.setSlno(Integer.parseInt(record.get("SLNO")));
		mc.setStatus("1");
		mc.setUserCode(userCode);
		mc.setCreatedAt(time);
		mc.setUpdatedAt(time);
		mc.setUpdatedBy(userCode);
		return mc;
	}

	
	
	private AssetCodeMas constructAssetCodeMas(MappingNewCode mnc, String newCode, int runNo, String userCode) {
		Date time = new Date();
		AssetCodeMas acm = new AssetCodeMas();
		acm.setMfglCode(mnc.getMfglCode());
		acm.setNewCode(newCode);
		acm.setNewRunno(runNo);
		acm.setNewAssetDesc(mnc.getNewAssetDesc());
		acm.setNewAssetShortDesc(mnc.getNewAssetShortDesc());
		acm.setUserCode(userCode);
		acm.setCreatedAt(time);
		acm.setUpdatedAt(time);
		return acm;
	}
	
	private AssetCodeMasDesc constructAssetCodeMasDesc(MappingNewCode mnc, String newCode, String userCode) {
		Date time = new Date();
		AssetCodeMasDesc acmd = new AssetCodeMasDesc();
		acmd.setMfglCode(mnc.getMfglCode());
		acmd.setNewCode(newCode);
		acmd.setNewDesc(mnc.getNewAssetDesc());
		acmd.setShortDesc(mnc.getNewAssetShortDesc());
		acmd.setUserCode(userCode);
		acmd.setCreatedAt(time);
		acmd.setUpdatedAt(time);
		return acmd;
	}
	
	private String prependZeros(int no, int length) {
		
		String nm = no + "";
		for (int i = nm.length(); i < length; i++) {
			nm = "0" + nm;
		}
		return nm;
	}

	
	/*private MatCodeMas constructMatCodeMas(MappingNewCode mnc, String userCode) {
		
		Date time = new Date();
		MatCodeMas mcm = new MatCodeMas();
		mcm.setProjCode("0001");
		mcm.setNewMatCode(mnc.getNewMatCode());
		mcm.setNewMatDesc(mnc.getNewMatDesc());
		mcm.setDrwg(mnc.getDrwg());
		mcm.setPart(mnc.getPart());
		mcm.setUserCode(userCode);
		mcm.setCreatedAt(time);
		mcm.setUpdatedAt(time);
		return mcm;
	}*/
	
	/*private ValidationResult validateNewMatCodeWithMasterScheme(String newMatCode) {// throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		try {
			return codeExistsInMasterScheme("t0103", newMatCode, "", "", "");
		} catch (Exception e) {
			e.printStackTrace();
			return new ValidationResult(false, AssetcdUtil.getExceptionMessageChain(e).toString(), "Technical Error. Contact admin.");
		} 
	}*/
	
	/*private ValidationResult codeExistsInMasterScheme(String table, String code, String parentCode, String parentAbsCode, String parentGroup) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		boolean exists = true;
		String message = "";
		String title = "";
		int tabs = Integer.parseInt(table.substring(1, 3));
		int tabe = Integer.parseInt(table.substring(3));
		int tablel = tabe - tabs + 1;
		
		
		
		String absCode = code.substring(0, tablel);
		
		String codeStart = (!StringUtils.isBlank(parentGroup)) ? parentCode.substring(0, parentCode.length() - parentAbsCode.length()) : parentCode;
		
		Class ccl = Class.forName("com.mccs.data.model."+table.toUpperCase());
		Class cl = Class.forName("com.mccs.data.repository." + table.toUpperCase() + "Repository");
		Object r = context.getBean(cl);
		
		Object o = null;
		Object ol = MethodUtils.invokeMethod(r, "findbyCode1", codeStart + absCode);
		
		if (ol != null) {
			if (((List)ol).size() == 1) {
				o = ((List)ol).get(0);
			} else if (((List)ol).size() > 1) {
				return new ValidationResult(false, "There are multiple codes " + absCode + " (" + codeStart + absCode + ") in table " + table + ".", "It is advised to map this code manually.");
			}
		}
		
		if (o == null) {
			if (code.length() == tablel && code.matches("^0+$")) {
				List l = (List)MethodUtils.invokeMethod(r, "findbyCode1", codeStart);
				if (l == null || l.size() == 0) {
					exists = true;
				} else {
					exists = false;
					message = "Code " + absCode + " does not exist in " + table + ".";
					title = "NEW_MAT_CODE does not exist in the master scheme.";
				}
			} else {
				exists = false;
				message = "Code " + absCode + " does not exist in " + table + ".";
				title = "NEW_MAT_CODE does not exist in the master scheme.";
			}
		} else {
			String ontab1 = (String) FieldUtils.readField(o, "ntab1", true);
			String ocode = (String) FieldUtils.readField(o, "code", true);
			String ogrpId = (String) FieldUtils.readField(o, "grpId", true);
			String next = code.substring(absCode.length());
			if (!StringUtils.isBlank(next)) {
				if (StringUtils.isBlank(ontab1) || ontab1.length() != 5 || !StringUtils.isNumeric(ontab1.substring(1))) {
					exists = false;
					message = "Incorrect value of ntab1 {" + ontab1 + "} for code " + absCode + " in table " + table + "."; 
					title = "NEW_MAT_CODE does not exist in the master scheme.";
				} else {
					ValidationResult vr = codeExistsInMasterScheme(ontab1, next, ocode, absCode, ogrpId);
					exists = vr.isValid();
					message = vr.getMessage();
					title = vr.getTitle();
				}
				
			}
		}
		
		//return new ValidationResult(exists, message, "NEW_MAT_CODE does not exist in the master scheme.");
		return new ValidationResult(exists, message, title);
	}*/
	
	/*private CSVRecord constructCSVRecord(MappingCodeUploadError mcue) {
		
		CSVRecord record = null;
		
		return record;
	}
	
	private void validateHeader(Map<String, Integer> header) {
		
		String str = "File does not contain required columns: ";
		List<String> columns = new ArrayList<String>();
		if (!header.containsKey("PROJ_CODE")) columns.add("PROJ_CODE");
		if (!header.containsKey("MAT_CODE")) columns.add("MAT_CODE");
		if (!header.containsKey("MAT_DESC")) columns.add("MAT_DESC");
		if (!header.containsKey("FORM_ID")) columns.add("FORM_ID");
		if (!header.containsKey("SLNO")) columns.add("SLNO");
		
		if (columns.size() > 0) {
			// throw exception
		}
		
	}*/
	
}

/*class ValidationResult {
	
	private boolean valid;
	private String message;
	private String title;
	
	public ValidationResult() {
	}
	public ValidationResult(boolean valid, String message, String title) {
		super();
		this.valid = valid;
		this.message = message;
		this.title = title;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	
}*/


