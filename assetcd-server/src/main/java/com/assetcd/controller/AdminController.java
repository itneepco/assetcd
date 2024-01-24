package com.assetcd.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.assetcd.data.model.User;
import com.assetcd.data.repository.UserRepository;
import com.assetcd.exception.UserNotFoundException;
import com.assetcd.service.AdminService;

@RestController
@RequestMapping("/a")
public class AdminController {
	
	@Autowired
	AdminService adminService;
	
	@Autowired
	UserRepository userRepository;
	
	//@PreAuthorize("hasAuthority('ADMIN')")
	@RequestMapping(path = "/createuser", method = RequestMethod.POST)
	public User createUser(@RequestBody User user) {
		
		return adminService.createUser(user);
	}
	
	@RequestMapping("/addrole")
	public ResponseEntity addRole(@RequestParam(value = "userid", required = true) int userId, @RequestParam(value = "roleid", required = true) int roleId) {
		adminService.addRole(userId, roleId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping("/removerole")
	public ResponseEntity removeRole(@RequestParam(value = "userid", required = true) int userId, @RequestParam(value = "roleid", required = true) int roleId) {
		adminService.removeRole(userId, roleId);
		return new ResponseEntity(HttpStatus.OK);
	}
	
	@RequestMapping(path = "/resetpassword", method = RequestMethod.POST)
	public ResponseEntity<Map<String, String>> resetPassword(@RequestParam(value = "userid", required = true) int userId) {
		
		Map<String, String> res = new HashMap<>();
		try {
			adminService.resetPassword(userId);
			res.put("message", "password reset to default");
		} catch (UserNotFoundException e) {
			//e.printStackTrace();
			res.put("error", "user not found");
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
