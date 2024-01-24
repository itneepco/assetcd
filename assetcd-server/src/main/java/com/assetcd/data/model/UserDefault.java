package com.assetcd.data.model;

import java.util.List;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "default", types = { User.class })
public interface UserDefault {
	
	int getId();
	String getFirstName();
	String getLastName();
	String getEmail();
	String getUserCode();
	byte getStatus();
	List<RoleDefault> getRoles();

}
