package com.assetcd.service;

import com.assetcd.data.model.User;
import com.assetcd.exception.ChangePasswordFailedException;
import com.assetcd.exception.UserNotFoundException;

public interface AdminService {
	
	public User createUser(User user);
	public void addRole(Integer userId, Integer roleId);
	public void removeRole(Integer userId, Integer roleId);
	public void changePassword(Integer userId, String oldPassword, String newPassword) throws ChangePasswordFailedException, UserNotFoundException;
	public void changePassword(String userCode, String oldPassword, String newPassword) throws ChangePasswordFailedException, UserNotFoundException;
	public void resetPassword(Integer userId) throws UserNotFoundException;

}
