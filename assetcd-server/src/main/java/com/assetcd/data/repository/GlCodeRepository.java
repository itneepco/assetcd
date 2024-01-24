package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.GlCode;

@RepositoryRestResource(path = "gc")
public interface GlCodeRepository extends JpaRepository<GlCode, Integer> {
	
	@RestResource(path = "all", rel = "all")
	@Query(value = "select t.* from gl_code t where t.status = '1'", nativeQuery = true)
	List<GlCode> findAllRec();
	
	@RestResource(path = "bycode1", rel = "bycode1")
	@Query(value = "select t.* from gl_code t where t.status = '1' and t.gl_code like ?1% order by t.gl_code", nativeQuery = true)
	List<GlCode> findbyCode1(@Param("code") String code);
	
	@RestResource(path = "byc", rel = "byc")
	@Query(value = "select t.* from gl_code t where t.status = '1' and t.gl_code = ?1 limit 1", nativeQuery = true)
	GlCode findByC(@Param("code") String code);

}
