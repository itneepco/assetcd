package com.assetcd.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.assetcd.service.CustomUserDetailsService;
import com.assetcd.service.SvService;

@RestController
@RequestMapping("/sv")
public class SvController {
	
	@Autowired
	SvService svService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadassetcodes")
	public ResponseEntity<Map<String, String>> uploadAssetCodes(@RequestParam(value = "file", required = true) MultipartFile file, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		String uploadId = svService.uploadAssetCodes(file, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "Mapping codes uploaded successfully");
		} else {
			res.put("error", "Mapping codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloaderrors")
	public void downloadErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		svService.downloadErrors(response.getWriter(), uploadId, userCode);
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadnewassetcodes")
	public ResponseEntity<Map<String, String>> uploadNewAssetCodes(@RequestParam(value = "file", required = true) MultipartFile file, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		String uploadId = svService.uploadNewAssetCodes(file, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "New asset codes uploaded successfully");
		} else {
			res.put("error", "New asset codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloadnacerrors")
	public void downloadNewAssetCodeErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		svService.downloadNewAssetCodeErrors(response.getWriter(), uploadId, userCode);
		
	}
	
	
	@RequestMapping(method = RequestMethod.POST, value = "/uploadmappingnewcodes")
	public ResponseEntity<Map<String, String>> uploadMappingNewCodes(@RequestParam(value = "file", required = true) MultipartFile file, Principal principal) throws IOException {//, ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		String uploadId = svService.uploadMappingNewCodes(file, userCode);
		Map<String, String> res = new HashMap<>();
		if (uploadId == null) {
			res.put("message", "Mapping new codes uploaded successfully");
		} else {
			res.put("error", "Mapping newv codes uploaded with errors. The errors can be reviewed in report with upload id : " + uploadId);
			res.put("uploadId", uploadId);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/downloadmncerrors")
	public void downloadMappingNewCodeErrors(@RequestParam(value = "uploadid", required = true) String uploadId, HttpServletResponse response, Principal principal) throws IOException {
		
		String userCode = ((CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal()).getUserCode();
		prepareResponse(response);
		svService.downloadMappingNewCodeErrors(response.getWriter(), uploadId, userCode);
		
	}
	
	
	private void prepareResponse(HttpServletResponse response) {
        String headerKey = "Content-Disposition";
        String headerValue = String.format("attachment; filename=\"%s\"",
                "Errors.csv");
        response.setContentType("text/csv");
        response.setHeader(headerKey, headerValue);
    }

}
