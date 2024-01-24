package com.assetcd.service;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component(value="permissionEvaluator")
public class PermissionEvaluator {
	
	public boolean canChangePassword(int userId, Principal principal) {
		
		CustomUserDetailsService.UserRepositoryUserDetails ud = (CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal();
		return ud.getId() == userId;
	}


	public boolean canChangePassword(String userCode, Principal principal) {
		
		CustomUserDetailsService.UserRepositoryUserDetails ud = (CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal();
		return ud.getUserCode().equals(userCode);
	}


	public boolean canChangePassword(String userCode, CustomUserDetailsService.UserRepositoryUserDetails ud) {
		
		//CustomUserDetailsService.UserRepositoryUserDetails ud = (CustomUserDetailsService.UserRepositoryUserDetails)((Authentication)principal).getPrincipal();
		return ud.getUserCode().equals(userCode);
	}



}
