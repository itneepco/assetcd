package com.assetcd.service;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
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
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.assetcd.data.model.GlCode;
import com.assetcd.data.model.GlCodeUploadError;
import com.assetcd.data.model.GlMfglClassCode;
import com.assetcd.data.model.GlMfglClassCodeUploadError;
import com.assetcd.data.model.MasterDataUploadError;
import com.assetcd.data.repository.AttrTabRepository;
import com.assetcd.data.repository.GlCodeRepository;
import com.assetcd.data.repository.GlCodeUploadErrorRepository;
import com.assetcd.data.repository.GlMfglClassCodeRepository;
import com.assetcd.data.repository.GlMfglClassCodeUploadErrorRepository;
import com.assetcd.data.repository.MasterDataUploadErrorRepository;
import com.assetcd.exception.CodeAlreadyExistsException;
import com.assetcd.util.AssetcdUtil;
import com.assetcd.vo.ExportCodesCommand;
import com.assetcd.vo.UploadCodesCommand;

@Service
public class MasterDataServiceImpl implements MasterDataService {
	
	@Autowired
	ApplicationContext context;
	
	@PersistenceContext EntityManager entityManager;
	
	@Autowired
	AttrTabRepository attrTabRepository;
	
	@Autowired
	MasterDataUploadErrorRepository masterDataUploadErrorRepository;
	
	@Autowired
	GlMfglClassCodeRepository glMfglClassCodeRepository;
	
	@Autowired
	GlCodeRepository glCodeRepository;
	
	@Autowired
	GlMfglClassCodeUploadErrorRepository glMfglClassCodeUploadErrorRepository;
	
	@Autowired
	GlCodeUploadErrorRepository glCodeUploadErrorRepository; 
	
	
	
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public Object addGlCode(GlCode glCode, String userCode) throws CodeAlreadyExistsException {
		
		GlCode c = glCodeRepository.findByC(glCode.getGlCode());
		if (c != null) {
			throw new CodeAlreadyExistsException();
		}
		
		glCode.setStatus("1");
		//glCode.setUserCode(userCode);
		glCode.setCreatedAt(new Date());
		glCode.setUpdatedAt(new Date());
		
		return glCodeRepository.save(glCode);
		
	}
	
	
	public void exportGlCodes(PrintWriter writer, ExportCodesCommand ecc) throws IOException {
		
		//Integer parentCode = Integer.parseInt(ecc.getParentCode());
		List<GlCode> codes = glCodeRepository.findAllRec();
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("GL_CODE", "DESCR"));
		
		for (GlCode code : codes) {
			csvPrinter.printRecord(
					code.getGlCode(),
					code.getDescr()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}
	
	
	
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public Object addGlMfglclassCode(GlMfglClassCode glMfglClassCode, String userCode) throws CodeAlreadyExistsException {
		
		/*GlMfglClassCode c = glMfglClassCodeRepository.findByC(glMfglClassCode.getSubClass());
		if (c != null) {
			throw new CodeAlreadyExistsException("class code");
		}*/
		
		GlMfglClassCode c = glMfglClassCodeRepository.findByM(glMfglClassCode.getMfglCode());
		if (c != null) {
			throw new CodeAlreadyExistsException("mfgl code");
		}
		
		glMfglClassCode.setStatus("1");
		//glMfglClassCode.setUserCode(userCode);
		glMfglClassCode.setCreatedAt(new Date());
		glMfglClassCode.setUpdatedAt(new Date());
		
		return glMfglClassCodeRepository.save(glMfglClassCode);
		
	}
	
	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public Object updateGlMfglclassCode(Integer id, Map changes, String userCode) throws CodeAlreadyExistsException {
		
		int mfglCode = Integer.parseInt((String)changes.get("mfglCode"));
		String descr = (String)changes.get("descr");
		GlMfglClassCode c = glMfglClassCodeRepository.findById(id).orElse(null);
		if (c == null) {
			return null;
		}
		
		GlMfglClassCode c1 = glMfglClassCodeRepository.findByM(mfglCode);
		if (c1 != null && mfglCode != c1.getMfglCode()) {
			throw new CodeAlreadyExistsException("mfgl code");
		}
		
		c.setMfglCode(mfglCode);
		c.setDescr(descr);
		c.setStatus("1");
		//glMfglClassCode.setUserCode(userCode);
		c.setCreatedAt(new Date());
		c.setUpdatedAt(new Date());
		
		return glMfglClassCodeRepository.save(c);
		
	}
	
	
	public void exportGlMfglClassCodes(PrintWriter writer, ExportCodesCommand ecc) throws IOException {
		
		String parentCode = ecc.getParentCode();
		List<GlMfglClassCode> codes = glMfglClassCodeRepository.findByGlCode(parentCode);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("MFGL_CODE", "SUB_CLASS", "DESCR"));
		
		for (GlMfglClassCode code : codes) {
			csvPrinter.printRecord(
					code.getMfglCode(),
					code.getSubClass(),
					code.getDescr()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}
	
	
	@Override
	public String uploadGlMfglClassCodes(MultipartFile file, UploadCodesCommand ucc, String userCode) throws UnsupportedEncodingException, IOException {
		
		//String table = ucc.getTable();
		String parentCode = ucc.getParentCode();
		//int fullCodeLength = ucc.getFullCodeLength();
		String glCode = parentCode;
		
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
			String subClass = record.get("SUB_CLASS");
			String mfglCode = record.get("MFGL_CODE");
			String descr = record.get("DESCR");
			
			
			
			if (StringUtils.isBlank(subClass)) {
				String error = "SUB_CLASS is incorrect, should not be blank";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			/*if (!classCode.matches("^[a-zA-Z]{2}[0-9]{2}$")) {
				String error = "CLASS_CODE is incorrect, should be in XX00 format.";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}*/
			if (subClass.length() != 2) {
				String error = "SUB_CLASS is incorrect, should be of length 2.";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if ("".equals(record.get("MFGL_CODE")) || !StringUtils.isNumeric(record.get("MFGL_CODE"))) {
				String error = "MFGL_CODE is not numeric";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			if (record.get("MFGL_CODE").length() != 6) {
				String error = "MFGL_CODE should be 6 digit number.";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (StringUtils.isBlank(descr)) {
				String error = "DESCR is incorrect, should not be blank";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (descr != null && descr.length() > 300) {
				String error = "DESCR is incorrect, length should be <= 300";
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
			
			
			// save record
			try {
				GlMfglClassCode gmcc = constructGMCC(glCode, Integer.parseInt(mfglCode), subClass, descr, userCode);
				glMfglClassCodeRepository.save(gmcc);
			} catch(Exception e) {
				String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
				glMfglClassCodeUploadErrorRepository.save(constructGlMfglClassCodeError(record, userCode, uploadId, error, date));
				isError = true;
				continue;
			}
			
		}
		
		
		if (isError) {
			return uploadId;
		} 
		return null;
	}
	
	
	@Override
		public String uploadGlCodes(MultipartFile file, UploadCodesCommand ucc, String userCode) throws UnsupportedEncodingException, IOException {
			
			//String table = ucc.getTable();
			String parentCode = ucc.getParentCode();
			//int fullCodeLength = ucc.getFullCodeLength();
			//int glCode = Integer.parseInt(parentCode);
			
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
				String glCode = record.get("GL_CODE");
				String descr = record.get("DESCR");
				
				
				
				if ("".equals(record.get("GL_CODE")) || !StringUtils.isNumeric(record.get("GL_CODE"))) {
					String error = "GL_CODE is not numeric";
					glCodeUploadErrorRepository.save(constructGlCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				if (record.get("GL_CODE").length() != 7) {
					String error = "GL_CODE should be 7 digit number.";
					glCodeUploadErrorRepository.save(constructGlCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				if (StringUtils.isBlank(descr)) {
					String error = "DESCR is incorrect, should not be blank";
					glCodeUploadErrorRepository.save(constructGlCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				if (descr != null && descr.length() > 300) {
					String error = "DESCR is incorrect, length should be <= 300";
					glCodeUploadErrorRepository.save(constructGlCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
				
				
				// save record
				try {
					GlCode gc = constructGC(glCode, descr, userCode);
					glCodeRepository.save(gc);
				} catch(Exception e) {
					String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
					glCodeUploadErrorRepository.save(constructGlCodeError(record, userCode, uploadId, error, date));
					isError = true;
					continue;
				}
				
			}
			
			
			if (isError) {
				return uploadId;
			} 
			return null;
		}


	
	public void exportCodes(PrintWriter writer, ExportCodesCommand ecc) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException, IOException {
		
		String table = ecc.getTable();
		String parentCode = ecc.getParentCode();
		int fullCodeLength = ecc.getFullCodeLength();
		
		int tabs = Integer.parseInt(table.substring(1, 3));
		int tabe = Integer.parseInt(table.substring(3));
		int codel = tabe - tabs + 1;
		
		//code: (this.auc.parentGroup)? this.auc.parentCode.slice(0, -1* this.auc.parentAbsCode.length) + this.newCode : this.auc.parentCode + this.newCode,
		String codeStart = (parentCode == null) ? "" : parentCode;
		
		Class ccl = Class.forName("com.assetcd.data.model."+table.toUpperCase());
		Class cl = Class.forName("com.assetcd.data.repository." + table.toUpperCase() + "Repository");
		Object r = context.getBean(cl);
		//Object[] args = {code, grpId};
		Object oCodes = MethodUtils.invokeMethod(r, "findbyCode1", codeStart);
		List codes = (List) oCodes;
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("CODE", "DESCR"));
		
		for (Object code : codes) {
			csvPrinter.printRecord(
					FieldUtils.readField(code, "code", true),
					FieldUtils.readField(code, "descr", true)
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}
	
	
	@Override
	public String uploadCodes(MultipartFile file, UploadCodesCommand ucc, String userCode) throws UnsupportedEncodingException, IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		String table = ucc.getTable();
		String parentCode = ucc.getParentCode();
		int fullCodeLength = ucc.getFullCodeLength();
		
		int tabs = Integer.parseInt(table.substring(1, 3));
		int tabe = Integer.parseInt(table.substring(3));
		int codel = tabe - tabs + 1;
		
		String codeStart = (parentCode == null) ? "" : parentCode;
		
		
		Class ccl = Class.forName("com.assetcd.data.model."+table.toUpperCase());
		Class cl = Class.forName("com.assetcd.data.repository." + table.toUpperCase() + "Repository");
		Object r = context.getBean(cl);
		
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
			String descr = record.get("DESCR");
			String code = record.get("CODE");
			
			
			
			if (StringUtils.isBlank(code)) {
				String error = "CODE is incorrect, should not be blank";
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (StringUtils.isBlank(descr)) {
				String error = "DESCR is incorrect, should not be blank";
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (descr != null && descr.length() > 300) {
				String error = "DESCR is incorrect, length should be <= 300";
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}
			
			if (codel < code.length()) {
				String error = "CODE length is incorrect, should be <= " + codel;
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}
			
			// This validation is not required now as the code start is populated programmatically
			/*if (!code.startsWith(codeStart)) {
				String error = "CODE is incorrect, should start with " + codeStart;
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}*/
			
			
			/*Object[] args = {codeStart + prependZeros(code, codel)};
			Object c = MethodUtils.invokeMethod(r, "findbyCode1", args);
			if (c != null && ((List)c).size() > 0) {
				String error = "Duplicate, CODE already exists";
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}*/
			
			
			
			
			// save record
			try {
				Object o = constructCode(record, codeStart, table, codel, userCode);
				MethodUtils.invokeMethod(r, "save", o);
			} catch(Exception e) {
				String error = AssetcdUtil.getExceptionMessageChain(e).toString();//e.getMessage();
				masterDataUploadErrorRepository.save(constructError(record, userCode, table, uploadId, error, date));
				isError = true;
				continue;
			}
			
		}
		
		
		if (isError) {
			return uploadId;
		} 
		return null;
	}


	@Override
	public void downloadErrors(PrintWriter writer, String uploadId,	String userCode) throws IOException {

		List<MasterDataUploadError> mdues = masterDataUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("CODE", "DESCR", "ERROR"));
		
		for (MasterDataUploadError mdue : mdues) {
			csvPrinter.printRecord(
					mdue.getCode(),
					mdue.getDescr(),
					mdue.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}
	
	@Override
	public void downloadGMCCErrors(PrintWriter writer, String uploadId,	String userCode) throws IOException {

		List<GlMfglClassCodeUploadError> errs = glMfglClassCodeUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("MFGL_CODE", "SUB_CLASS", "DESCR", "ERROR"));
		
		for (GlMfglClassCodeUploadError err : errs) {
			csvPrinter.printRecord(
					err.getMfglCode(),
					err.getSubClass(),
					err.getDescr(),
					err.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}

	
	@Override
	public void downloadGLErrors(PrintWriter writer, String uploadId,	String userCode) throws IOException {

		List<GlCodeUploadError> errs = glCodeUploadErrorRepository.findByUploadId(uploadId);
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.RFC4180
                .withHeader("GL_CODE", "DESCR", "ERROR"));
		
		for (GlCodeUploadError err : errs) {
			csvPrinter.printRecord(
					err.getGlCode(),
					err.getDescr(),
					err.getError()
					);
		}
		csvPrinter.flush();
		csvPrinter.close();
		
	}

	

	@Override
	@Transactional(isolation=Isolation.SERIALIZABLE, rollbackFor = Exception.class)
	public Object addCode(String table, Map code, String userCode) throws ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException {
		
		Class ccl = Class.forName("com.assetcd.data.model."+table.toUpperCase());
		Class cl = Class.forName("com.assetcd.data.repository." + table.toUpperCase() + "Repository");
		Object r = context.getBean(cl);
		//Method m = cl.getMethod("findByCode", String.class);
		//Object c =  m.invoke(r, code.get("id"));
		String cd = (String)code.get("code");
		Object[] args1 = {cd};
		Object c = MethodUtils.invokeMethod(r, "findByC", args1);
		if (c != null) {
			throw new CodeAlreadyExistsException();
		}
		
		Object o = getCodeInstance(code, table);
		/*Field f = ccl.getDeclaredField("userCode");
		f.setAccessible(true);
		f.set(o, userCode);*/
		FieldUtils.writeField(o, "userCode", userCode, true);
		//code.setUserCode(userCode);
		
		//return r.save(o);
		return MethodUtils.invokeMethod(r, "save", o);
	}
	
	private Object getCodeInstance(Map code, String table) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		Class ccl = Class.forName("com.assetcd.data.model."+table.toUpperCase());
		Object o = ccl.newInstance();
		
		/*for (Field f : ccl.getDeclaredFields()) {
			if (f.getName().equals("serialVersionUID")) continue;
			if (f.getName().equals("id")) continue;
			f.setAccessible(true);
			f.set(o, code.get(f.getName()));
		}*/
		
		FieldUtils.writeField(o, "code", code.get("code"), true);
		FieldUtils.writeField(o, "descr", code.get("descr"), true);
		FieldUtils.writeField(o, "userCode", code.get("userCode"), true);
		FieldUtils.writeField(o, "status", code.get("status").toString(), true);
		FieldUtils.writeField(o, "createdAt", new Date(), true);
		FieldUtils.writeField(o, "updatedAt", new Date(), true);
		
		 return o;
		
	}
	
	
	private Object constructCode(CSVRecord record, String codeStart, String table, int codel, String userCode) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		
		Class ccl = Class.forName("com.assetcd.data.model."+table.toUpperCase());
		Object o = ccl.newInstance();
		
		FieldUtils.writeField(o, "code", codeStart + prependZeros(record.get("CODE"), codel), true);
		FieldUtils.writeField(o, "descr", record.get("DESCR"), true);
		FieldUtils.writeField(o, "userCode", userCode, true);
		FieldUtils.writeField(o, "status", "1", true);
		FieldUtils.writeField(o, "createdAt", new Date(), true);
		FieldUtils.writeField(o, "updatedAt", new Date(), true);
		
		 return o;
		
	}
	
	
	private MasterDataUploadError constructError(CSVRecord record, String userCode, String table, String uploadId, String error, Date date) {
		
		MasterDataUploadError mduerror = new MasterDataUploadError();
		mduerror.setUploadId(uploadId);
		mduerror.setTable(table);
		mduerror.setRecIndex((int)record.getRecordNumber());
		mduerror.setCode(record.get("CODE"));
		mduerror.setDescr(record.get("DESCR"));
		mduerror.setRawRec(record.toString());
		mduerror.setError(error);
		mduerror.setUserCode(userCode);
		mduerror.setCreatedAt(date);
		return mduerror;
	}
	
	private GlMfglClassCode constructGMCC(String glCode, int mfglCode, String subClass, String descr, String userCode) {
		Date time = new Date();
		GlMfglClassCode gmcc = new GlMfglClassCode();
		gmcc.setGlCode(glCode);
		gmcc.setSubClass(subClass);
		gmcc.setMfglCode(mfglCode);
		gmcc.setDescr(descr);
		gmcc.setUserCode(userCode);
		gmcc.setStatus("1");
		gmcc.setCreatedAt(time);
		gmcc.setUpdatedAt(time);
		return gmcc;
	}
	
	private GlCode constructGC(String glCode, String descr, String userCode) {
		Date time = new Date();
		GlCode gc = new GlCode();
		gc.setGlCode(glCode);
		gc.setDescr(descr);
		gc.setUserCode(userCode);
		gc.setStatus("1");
		gc.setCreatedAt(time);
		gc.setUpdatedAt(time);
		return gc;
	}
	
	private GlCodeUploadError constructGlCodeError(CSVRecord record, String userCode, String uploadId, String error, Date date) {
		
		GlCodeUploadError err = new GlCodeUploadError();
		err.setUploadId(uploadId);
		err.setRecIndex((int)record.getRecordNumber());
		err.setGlCode(record.get("GL_CODE"));
		err.setDescr(record.get("DESCR"));
		err.setRawRec(record.toString());
		err.setError(error);
		err.setUserCode(userCode);
		err.setCreatedAt(date);
		return err;
	}
	
	private GlMfglClassCodeUploadError constructGlMfglClassCodeError(CSVRecord record, String userCode, String uploadId, String error, Date date) {
		
		GlMfglClassCodeUploadError err = new GlMfglClassCodeUploadError();
		err.setUploadId(uploadId);
		err.setRecIndex((int)record.getRecordNumber());
		err.setMfglCode(record.get("MFGL_CODE"));
		err.setSubClass(record.get("SUB_CLASS"));
		err.setDescr(record.get("DESCR"));
		err.setRawRec(record.toString());
		err.setError(error);
		err.setUserCode(userCode);
		err.setCreatedAt(date);
		return err;
	}
	
	private String prependZeros(String str, int length) {
		
		String prefix = "";
		int l = str.length();
		while (l < length) {
			prefix =  prefix + "0";
			l++;
		}
		
		return prefix + str;
	}
	
	public static void main(String[] args) {
		System.out.println(Integer.parseInt(""));
	}
}
