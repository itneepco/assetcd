package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.Project;

@RepositoryRestResource(path = "project")
public interface ProjectRepository extends JpaRepository<Project, Integer> {
	
	@RestResource(path = "byuser", rel = "byuser")
	@Query(value = "select p.* from projects p, users_project up, users u where p.id = up.project_id and u.id = up.user_id and u.status = 1 and u.id = ?1", nativeQuery = true)
	List<Project> findByUser(@Param("userid") int userId);
	
	@RestResource(path = "alls", rel = "alls")
	List<Project> findByStatus(@Param("status") String s);
	
	@RestResource(path = "all", rel = "all")
	@Query(value = "select p.* from projects p where p.status = '1' order by proj_code", nativeQuery = true)
	List<Project> findByActive();
	
	@RestResource(path = "al", rel = "al")
	@Query(value = "select p.* from projects p order by proj_code", nativeQuery = true)
	List<Project> findByA();

	@RestResource(path = "bycode", rel = "bycode")
	List<Project> findByProjCode(@Param("projcode") String projCode);

}
