package com.assetcd.service;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

import org.springframework.web.multipart.MultipartFile;

public interface SvService {
	
	public String uploadAssetCodes(MultipartFile file, String userCode) throws IOException;
	public void downloadErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;
	public String uploadNewAssetCodes(MultipartFile file, String userCode) throws IOException;
	public void downloadNewAssetCodeErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;
	public String uploadMappingNewCodes(MultipartFile file, String userCode) throws IOException;//, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException;
	public void downloadMappingNewCodeErrors(PrintWriter writer, String uploadId, String userCode) throws IOException;

}
