package com.assetcd.controller;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assetcd.data.model.GlCode;
import com.assetcd.data.model.GlMfglClassCode;
import com.assetcd.data.repository.GlMfglClassCodeRepository;
import com.assetcd.data.repository.UserRepository;
import com.assetcd.exception.AlreadyProcessedException;
import com.assetcd.exception.CodeAlreadyExistsException;
import com.assetcd.service.CustomUserDetailsService;
import com.assetcd.service.MasterDataService;
import com.assetcd.vo.AddCodeCommand;
import com.assetcd.vo.ExportCodesCommand;
import com.assetcd.vo.MFGL;
import com.assetcd.vo.UploadCodesCommand;

@RestController
@RequestMapping("/masdata")
public class MasterDataController {
	
	@Autowired
	MasterDataService masterDataService;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	GlMfglClassCodeRepository glMfglClassCodeRepository;
	
	@RequestMapping(path = "/addglmfglclasscode", method = RequestMethod.POST)
	public Object addGlMfglClassCode(@RequestBody GlMfglClassCode glMfglClassCode, Principal principal) throws AlreadyProcessedException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return masterDataService.addGlMfglclassCode(glMfglClassCode, userCode);

	}
	
	@RequestMapping(path = "/updateglmfglclasscode/{id}", method = RequestMethod.POST)
	public Object updateGlMfglClassCode(@PathVariable(value="id") Integer id, @RequestBody Map map, Principal principal) throws AlreadyProcessedException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return masterDataService.updateGlMfglclassCode(id, map, userCode);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/exportglmfglclasscodes")
	public void exportGlMfglClassCodes(@RequestBody ExportCodesCommand command, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		
		masterDataService.exportGlMfglClassCodes(response.getWriter(), command);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadglmfglclasscodes")
	public ResponseEntity<Map<String, String>> uploadGlMfglClassCodes(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "ucc", required = true) UploadCodesCommand ucc, Principal principal) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		String uploadId = masterDataService.uploadGlMfglClassCodes(file, ucc, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "Codes uploaded successfully");
		} else {
			res.put("error", "Codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloadgmccerrors")
	public void downloadGMCCErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		masterDataService.downloadGMCCErrors(response.getWriter(), uploadId, userCode);
		
	}
	

	@RequestMapping(path = "/addglcode", method = RequestMethod.POST)
	public Object addGlCode(@RequestBody GlCode glCode, Principal principal) throws AlreadyProcessedException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return masterDataService.addGlCode(glCode, userCode);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/exportglcodes")
	public void exportGlCodes(@RequestBody ExportCodesCommand command, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		
		masterDataService.exportGlCodes(response.getWriter(), command);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadglcodes")
	public ResponseEntity<Map<String, String>> uploadGlCodes(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "ucc", required = true) UploadCodesCommand ucc, Principal principal) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		String uploadId = masterDataService.uploadGlCodes(file, ucc, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "Codes uploaded successfully");
		} else {
			res.put("error", "Codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloadgcerrors")
	public void downloadGCErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		masterDataService.downloadGLErrors(response.getWriter(), uploadId, userCode);
		
	}
	
	
	@RequestMapping(path = "/addcode", method = RequestMethod.POST)
	public Object addCode(@RequestBody AddCodeCommand command, Principal principal) throws AlreadyProcessedException, ClassNotFoundException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, CodeAlreadyExistsException, SQLException, InstantiationException, NoSuchFieldException {
		
		Map code = command.getCode();
		String table = command.getTable();
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		return masterDataService.addCode(table, code, userCode);

	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadcodes")
	public ResponseEntity<Map<String, String>> uploadCodes(@RequestPart(value = "file", required = true) MultipartFile file, @RequestPart(value = "ucc", required = true) UploadCodesCommand ucc, Principal principal) throws IOException, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		
		String uploadId = masterDataService.uploadCodes(file, ucc, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "Codes uploaded successfully");
		} else {
			res.put("error", "Codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloaderrors")
	public void downloadErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		masterDataService.downloadErrors(response.getWriter(), uploadId, userCode);
		
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/exportcodes")
	public void exportCodes(@RequestBody ExportCodesCommand command, HttpServletResponse response) throws IOException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, ClassNotFoundException {
		
		masterDataService.exportCodes(response.getWriter(), command);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/mfgl")
	public List<MFGL> fetchMfgl() {
		return glMfglClassCodeRepository.findAllRec1();
	}
	
	
	private void prepareResponse(HttpServletResponse response) {
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "Errors.csv");
        response.setContentType("text/csv");
        response.setHeader(headerKey, headerValue);
    }
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/test")
	public String test() {
		return "test";
	}

}
