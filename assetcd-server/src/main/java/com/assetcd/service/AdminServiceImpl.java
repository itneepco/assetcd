package com.assetcd.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.assetcd.data.model.Role;
import com.assetcd.data.model.User;
import com.assetcd.data.repository.RoleRepository;
import com.assetcd.data.repository.UserRepository;
import com.assetcd.exception.ChangePasswordFailedException;
import com.assetcd.exception.UserNotFoundException;

@Service
public class AdminServiceImpl implements AdminService {
	
	@PersistenceContext 
	EntityManager entityManager;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	public User createUser(User user) {
		
		String pwd = user.getLastName().toLowerCase() + "1234";
		user.setPassword(passwordEncoder.encode(pwd));
		//user.setPassword(new BCryptPasswordEncoder().encode(pwd));
		//user.setPassword(pwd);
		Role r = roleRepository.findByName("USER");
		List<Role> roles = new ArrayList<Role>();
		if (r != null) roles.add(r);
		user.setRoles(roles);
		return userRepository.save(user);
	}
	
	
	@Transactional(value = TxType.REQUIRED)
	public void addRole(Integer userId, Integer roleId) {
		Query q = entityManager.createNativeQuery("insert into role_user (user_id, role_id) values (:userId, :roleId)");
		q.setParameter("userId", userId);
		q.setParameter("roleId", roleId);
		q.executeUpdate();
	}

	@Transactional(value = TxType.REQUIRED)
	public void removeRole(Integer userId, Integer roleId) {
		Query q = entityManager.createNativeQuery("delete from role_user where user_id = :userId and role_id = :roleId");
		q.setParameter("userId", userId);
		q.setParameter("roleId", roleId);
		q.executeUpdate();
		
	}

	
	public void changePassword(Integer userId, String oldPassword, String newPassword) throws ChangePasswordFailedException, UserNotFoundException {
		
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
			} else {
				throw new ChangePasswordFailedException("The current password is not correct.");
			}
		} else {
			throw new UserNotFoundException("User not found");
		}
		
	}


	public void changePassword(String userCode, String oldPassword, String newPassword) throws ChangePasswordFailedException, UserNotFoundException {
		
		User user = userRepository.findByUserCode(userCode);
		if (user != null) {
			if (passwordEncoder.matches(oldPassword, user.getPassword())) {
				user.setPassword(passwordEncoder.encode(newPassword));
				userRepository.save(user);
			} else {
				throw new ChangePasswordFailedException("The current password is not correct.");
			}
		} else {
			throw new UserNotFoundException("User not found");
		}
		
	}

	
	
	public void resetPassword(Integer userId) throws UserNotFoundException {
		
		User user = userRepository.findById(userId).orElse(null);
		if (user != null) {
			String pwd = user.getLastName().toLowerCase() + "1234";
			user.setPassword(passwordEncoder.encode(pwd));
			userRepository.save(user);
		} else {
			throw new UserNotFoundException("User not found");
		}
		
	}

}
