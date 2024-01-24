package com.assetcd.data.repository;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.GlMfglClassCode;
import com.assetcd.data.model.T0506;
import com.assetcd.vo.MFGL;

@RepositoryRestResource(path = "gmcc")
public interface GlMfglClassCodeRepository extends JpaRepository<GlMfglClassCode, Integer> {
	
	@RestResource(path = "all", rel = "all")
	@Query(value = "select t.* from gl_mfgl_class_code t where t.status = '1' order by t.sub_class", nativeQuery = true)
	List<GlMfglClassCode> findAllRec();
	
	@RestResource(path = "all1", rel = "all1")
	@Query(value = "select t.id, t.status, t.user_code userCode, t.mfgl_code mfglCode, t.descr, t.sub_class subClass, t.gl_code glCode, g.descr glDesc " + 
			"from gl_mfgl_class_code t join gl_code g on (t.gl_code = g.gl_code) where t.status = '1' order by t.gl_code, t.sub_class", nativeQuery = true)
	List<MFGL> findAllRec1();
	
	@RestResource(path = "bycode1", rel = "bycode1")
	@Query(value = "select t.* from gl_mfgl_class_code t where t.status = '1' and t.sub_class like ?1% order by t.sub_class", nativeQuery = true)
	List<GlMfglClassCode> findbyCode1(@Param("code") String code);
	
	@RestResource(path = "byc", rel = "byc")
	@Query(value = "select t.* from gl_mfgl_class_code t where t.status = '1' and t.sub_class = ?1 limit 1", nativeQuery = true)
	GlMfglClassCode findByC(@Param("code") String code);
	
	@RestResource(path = "byc1", rel = "byc1")
	@Query(value = "select t.* from gl_mfgl_class_code t where t.status = '1' and t.gl_code like %?1 and t.sub_class = ?2 limit 1", nativeQuery = true)
	GlMfglClassCode findByC1(@Param("classcode") String classCode, @Param("subclass") String subclass);
	
	@RestResource(path = "bym", rel = "bym")
	@Query(value = "select t.*, g.descr glDesc from gl_mfgl_class_code t join gl_code g on (t.gl_code = g.gl_code) where t.status = '1' and t.mfgl_code = ?1 limit 1", nativeQuery = true)
	GlMfglClassCode findByM(@Param("code") Integer code);
	
	@RestResource(path = "bym1", rel = "bym1")
	@Query(value = "select t.id, t.status, t.user_code userCode, t.mfgl_code mfglCode, t.descr, t.sub_class subClass, t.gl_code glCode, g.descr glDesc " + 
				"from gl_mfgl_class_code t join gl_code g on (t.gl_code = g.gl_code) where t.status = '1' and t.mfgl_code = ?1 limit 1", nativeQuery = true)
	MFGL findByM1(@Param("code") Integer code);
	
	@RestResource(path = "byglcode", rel = "byglcode")
	List<GlMfglClassCode> findByGlCode(@Param("code") String code);

}


