package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.Role;
import com.assetcd.data.model.RoleDefault;

@RepositoryRestResource(path = "rrole", excerptProjection = RoleDefault.class)
public interface RoleRepository extends CrudRepository<Role, Integer> {
	
	Role findByName(String name);
	
	/*@RestResource(path = "all", rel = "all")
	List<Role> findAll();*/

}
