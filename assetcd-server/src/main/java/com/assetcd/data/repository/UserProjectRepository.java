package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.UsersProject;

@RepositoryRestResource(path = "userproject")
public interface UserProjectRepository extends JpaRepository<UsersProject, Long> {
	
	

}
