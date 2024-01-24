package com.assetcd.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.assetcd.service.ReportService;
import com.assetcd.vo.AssetCodeReportCommand;
import com.assetcd.vo.ExportCodesCommand;
import com.assetcd.vo.ExportMappingNewCodesCommand;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

//@Controller
//@BasePathAwareController
@RestController
@RequestMapping("/report")
public class ReportController {
	
	@Autowired
    private ApplicationContext appContext;
	
	@Autowired
    DataSource datasource;
	
	@Value("${assetcd.report.dir:}")
	String reportDir;
	
	@Value("${assetcd.db:assetcd}")
	String db;
	
	@Autowired
	ReportService reportService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/exportmnc")
	public void exportMappingNewCodes(@RequestBody ExportMappingNewCodesCommand command, HttpServletResponse response) throws IOException {
		
		reportService.exportMappingNewCodes(response.getWriter(), command);
		
	}
	
	@RequestMapping(path = "/tabstats", method = RequestMethod.GET, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody tabStats() throws SQLException, JRException {
		
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Table_Details.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("db", db);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};

	}
	
	
	/**
	 * Sample client get request - http://localhost:8080/report/assetcode?projs=1000,2000
	 * 
	 * @param projs
	 * @return
	 * @throws SQLException
	 * @throws JRException 
	 */
	@RequestMapping(path = "/assetcode", method = RequestMethod.GET, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodes(@RequestParam String projs, @RequestParam String codeStart) throws SQLException, JRException {
		
		String cri = "("+projs+")";
		return generateReportMatchCode(cri, codeStart);

	}
	
	
	/**
	 * Sample client post request - http://localhost:8080/report/assetcode
	 * with request body as - [1000, 2000]
	 * 
	 * @param projs
	 * @return
	 * @throws SQLException
	 * @throws JRException 
	 */
	@RequestMapping(path = "/assetcode", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodes(@RequestBody AssetCodeReportCommand command) throws SQLException, JRException {
		
		String codeStart = command.getCodeStart();
		List<String> projs = command.getProjCodes();
		
		/*String cri = "(";
        for (int i=0; i < projs.size(); i++) {
        	String proj = projs.get(i);
        	cri = (i==0) ? cri + proj : cri + "," + proj;
        }
        cri += ")";
		System.out.println("cri:" + cri);*/
		String cri = listToString(projs);

        return generateReportMatchCode(cri, codeStart);

	}
	
	
	@RequestMapping(path = "/assetcodesd", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodesSdUmBlank(@RequestBody AssetCodeReportCommand command) throws SQLException, JRException {
		
		String codeStart = command.getCodeStart();
		List<String> projs = command.getProjCodes();
		
		String cri = listToString(projs);

        return generateReportMatchCodeSdUmBlank(cri, codeStart);

	}
	
	
	@RequestMapping(path = "/assetcodenvo", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodesNewVsOld(@RequestBody AssetCodeReportCommand command) throws SQLException, JRException {
		
		String codeStart = command.getCodeStart();
		List<String> projs = command.getProjCodes();
		String cri = listToString(projs);

        return generateReportMatchCodeNewVsOld(cri, codeStart);

	}
	
	@RequestMapping(path = "/assetcodesor", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodesSortedOnRefId(@RequestBody AssetCodeReportCommand command) throws SQLException, JRException {
		
		String codeStart = command.getCodeStart();
		List<String> projs = command.getProjCodes();
		String cri = listToString(projs);

        return generateReportMatchCodeSortedOnRefId(cri, codeStart);

	}
	
	@RequestMapping(path = "/assetcodegbu", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportMatchCodesGroupByUser(@RequestBody AssetCodeReportCommand command) throws SQLException, JRException {
		
		String codeStart = command.getCodeStart();
		List<String> projs = command.getProjCodes();
		String cri = listToString(projs);

        return generateReportMatchCodeGroupByUser(cri, codeStart);

	}
	
	
	/**
	 * Sample client post request - http://localhost:8080/report/rejstatus
	 * with request body as - [1000, 2000]
	 * 
	 * @param projs
	 * @return
	 * @throws SQLException
	 * @throws JRException 
	 */
	@RequestMapping(path = "/rejstatus", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportRejectionStatus(@RequestBody List<String> projs) throws SQLException, JRException {
		
		String cri = listToString(projs);
        return generateRejectionStatus(cri);

	}


	/**
	 * Sample client post request - http://localhost:8080/report/codemappingprogress
	 * with request body as - [1000, 2000]
	 * 
	 * @param projs
	 * @return
	 * @throws SQLException
	 * @throws JRException 
	 */
	@RequestMapping(path = "/codemappingprogress", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportCodeMappingProgress(@RequestBody List<String> projs) throws SQLException, JRException {
		
		String cri = listToString(projs);
        return generateCodeMappingProgress(cri);

	}


	/**
	 * Sample client post request - http://localhost:8080/report/coderprogress
	 * with request body as - [1000, 2000]
	 * 
	 * @param projs
	 * @return
	 * @throws SQLException
	 * @throws JRException 
	 */
	@RequestMapping(path = "/coderprogress", method = RequestMethod.POST, produces = "application/pdf")
	@ResponseBody
    public StreamingResponseBody reportCoderProgress() throws SQLException, JRException {
		
        return generateCoderProgress();

	}



	
	
	private StreamingResponseBody generateReportMatchCode(String cri, String ac) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Map_Detail.jrxml"));
		//JasperReport jr = JasperCompileManager.compileReport("src/main/resources/reports/Map_Detail.jrxml");
		//JasperReport jr = (JasperReport) JRLoader.loadObjectFromFile(reportDir + "/Map_Detail.jasper");
		//JasperReport jr = (JasperReport) JRLoader.loadObject(getClass().getResourceAsStream("/reports/Map_Detail.jasper"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con); 
        //params.put("cri", "(4000, 6000)");
        params.put("cri", cri);
        params.put("ac", ac);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}
	
	
	private StreamingResponseBody generateReportMatchCodeSdUmBlank(String cri, String ac) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Map_Detail_SD_UM_Blank.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        params.put("ac", ac);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}
	
	
	private StreamingResponseBody generateReportMatchCodeNewVsOld(String cri, String nac) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Map_Detail_NewVsOld.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        params.put("nac", nac);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}
	
	
	private StreamingResponseBody generateReportMatchCodeSortedOnRefId(String cri, String ac) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Map_Detail_Sortedon_RefId.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        params.put("ac", ac);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}
	
	
	private StreamingResponseBody generateReportMatchCodeGroupByUser(String cri, String ac) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Map_Detail_Groupby_User.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        params.put("ac", ac);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}
	
	
	private StreamingResponseBody generateRejectionStatus(String cri) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Rejection_Code_Stats.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}


	private StreamingResponseBody generateCodeMappingProgress(String cri) throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Code_Mapping_Progress.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        params.put("cri", cri);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}


	private StreamingResponseBody generateCoderProgress() throws SQLException, JRException {
		JasperReport jr = JasperCompileManager.compileReport(getClass().getResourceAsStream("/reports/Coder_Progress.jrxml"));
		
		Map<String, Object> params = new HashMap<>();
		Connection con = datasource.getConnection();
        params.put("REPORT_CONNECTION", con);
        
		JasperPrint jp = JasperFillManager.fillReport(jr, params);
		byte[] ba = JasperExportManager.exportReportToPdf(jp);
		con.close();
		
		return (os) -> {
			os.write(ba);
			os.close();
		};
		
	}


	
	private String listToString(List<String> projs) {
		
		String cri = "(";
        for (int i=0; i < projs.size(); i++) {
        	String proj = projs.get(i);
        	cri = (i==0) ? cri + proj : cri + "," + proj;
        }
        cri += ")";
        return cri;
		
	}
	
}
