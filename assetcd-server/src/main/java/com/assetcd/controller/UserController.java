package com.assetcd.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assetcd.data.repository.UserRepository;
import com.assetcd.exception.ChangePasswordFailedException;
import com.assetcd.exception.UserNotFoundException;
import com.assetcd.service.AdminService;

@RestController
@RequestMapping("/u")
public class UserController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserRepository userRepository;
	
	@PreAuthorize("@permissionEvaluator.canChangePassword(#userId, principal)")
	@RequestMapping(path = "/changepassword", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> changePassword(@RequestParam(value = "userid", required = true) int userId,
			@RequestParam(value = "currentpassword", required = true) String currentPwd,
			@RequestParam(value = "newpassword", required = true) String newPwd,
			Principal principal) {
		
		Map<String, String> res = new HashMap<>();
		try {
				adminService.changePassword(userId, currentPwd, newPwd);
		} catch (ChangePasswordFailedException e) {
			//e.printStackTrace();
			res.put("error", e.getMessage());
		} catch (UserNotFoundException e) {
			//e.printStackTrace();
			res.put("error", e.getMessage());
		}
		
		res.put("message", "Password successfully changed.");
		return new ResponseEntity<>(res, HttpStatus.OK);
	}


	@PreAuthorize("@permissionEvaluator.canChangePassword(#userCode, principal)")
	@RequestMapping(path = "/changepwd", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> changePassword(@RequestParam(value = "usercode", required = true) String userCode,
			@RequestParam(value = "currentpassword", required = true) String currentPwd,
			@RequestParam(value = "newpassword", required = true) String newPwd,
			Principal principal) {
		
		Map<String, String> res = new HashMap<>();
		try {
				adminService.changePassword(userCode, currentPwd, newPwd);
				res.put("message", "Password successfully changed.");
		} catch (ChangePasswordFailedException e) {
			//e.printStackTrace();
			res.put("error", e.getMessage());
		} catch (UserNotFoundException e) {
			//e.printStackTrace();
			res.put("error", e.getMessage());
		}
		
		return new ResponseEntity<>(res, HttpStatus.OK);
	}


}
