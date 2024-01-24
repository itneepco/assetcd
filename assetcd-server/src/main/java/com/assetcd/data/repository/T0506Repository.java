package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.T0506;

@RepositoryRestResource(path = "t0506")
public interface T0506Repository extends JpaRepository<T0506, Integer>{
	
	@RestResource(path = "all", rel = "all")
	@Query(value = "select t.* from t0506 t where t.status = '1'", nativeQuery = true)
	List<T0506> findAllRec();
	
	@RestResource(path = "bycode1", rel = "bycode1")
	@Query(value = "select t.* from t0506 t where t.status = '1' and t.code like ?1% order by t.code", nativeQuery = true)
	List<T0506> findbyCode1(@Param("code") String code);
	
	@RestResource(path = "byc", rel = "byc")
	@Query(value = "select t.* from t0506 t where t.status = '1' and t.code = ?1 limit 1", nativeQuery = true)
	T0506 findByC(@Param("code") String code);


}
