package com.assetcd.service;

import java.io.IOException;
import java.io.PrintWriter;

import com.assetcd.vo.ExportMappingNewCodesCommand;

public interface ReportService {
	
	public void exportMappingNewCodes(PrintWriter writer, ExportMappingNewCodesCommand emnc) throws IOException;

}
