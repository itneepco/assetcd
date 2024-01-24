package com.assetcd.data.model;

import org.springframework.data.rest.core.config.Projection;

@Projection(name = "default", types = { Role.class })
public interface RoleDefault {
	
	int getId();
	String getName();
	String getAuthority();

}
