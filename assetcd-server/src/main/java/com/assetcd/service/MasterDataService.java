package com.assetcd.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.assetcd.data.model.GlCode;
import com.assetcd.data.model.GlMfglClassCode;
import com.assetcd.exception.CodeAlreadyExistsException;
import com.assetcd.vo.ExportCodesCommand;
import com.assetcd.vo.UploadCodesCommand;

public interface MasterDataService {
	
	public Object addGlCode(GlCode glCode, String userCode) throws CodeAlreadyExistsException;
	public void exportGlCodes(PrintWriter writer, ExportCodesCommand ecc) throws IOException;
	public Object addGlMfglclassCode(GlMfglClassCode glMfglClassCode, String userCode) throws CodeAlreadyExistsException;
	public void exportGlMfglClassCodes(PrintWriter writer, ExportCodesCommand ecc) throws IOException;
	public Object addCode(String table, Map code, String userCode)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException;
	
	public String uploadCodes(MultipartFile file, UploadCodesCommand ucc, String userCode) 
			throws UnsupportedEncodingException, IOException, ClassNotFoundException, 
			NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public void downloadErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;
	
	public void exportCodes(PrintWriter writer, ExportCodesCommand ecc) 
			throws NoSuchMethodException, IllegalAccessException, 
			InvocationTargetException, ClassNotFoundException, IOException;
	public Object updateGlMfglclassCode(Integer id, Map changes, String userCode) throws CodeAlreadyExistsException;
	void downloadGLErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;
	void downloadGMCCErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;
	String uploadGlMfglClassCodes(MultipartFile file, UploadCodesCommand ucc, String userCode)
			throws UnsupportedEncodingException, IOException;
	String uploadGlCodes(MultipartFile file, UploadCodesCommand ucc, String userCode)
			throws UnsupportedEncodingException, IOException;
}
