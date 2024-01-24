package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.MappingCode;

@RepositoryRestResource(path = "mappingcode")
public interface MappingCodeRepository extends JpaRepository<MappingCode, Integer> {
	
	@RestResource(path = "byuser", rel = "byuser")
	@Query(value = "select mc.* from mapping_code mc join projects p on (mc.proj_code = p.PROJ_CODE) join users_project up on (p.id = up.project_id) where up.status = '1' and up.user_id = ?1", nativeQuery = true)
	List<MappingCode> findByUser(@Param("userid") int userId);
	
	// to be implemented
	/*@RestResource(path = "byusercode", rel = "byusercode")
	@Query(value = "select mc.* from mapping_code mc join projects p on (mc.proj_code = p.PROJ_CODE) join users_project up on (p.id = up.project_id) where up.status = '1' and up.user_id = ?1", nativeQuery = true)
	List<MappingCode> findByUserCode(@Param("userid") long userId);*/
	
	@RestResource(path = "byprojcode", rel = "byprojcode")
	List<MappingCode> findByProjCode(@Param("projcode") String projCode);
	
	@RestResource(path = "bypcnac", rel = "bypcnac")
	List<MappingCode> findByProjCodeAndAssetCodeStartingWith(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bypac", rel = "bypac")
	@Query(value = "select mc.* from mapping_code mc where mc.proj_code = ?1 and  (mc.asset_code like concat(?2, '%') or mc.asset_desc like concat(concat('%',?2),'%')) order by mc.mfgl_code, mc.asset_desc, mc.proj_code", nativeQuery = true)
	List<MappingCode> findByPAC(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bypac1", rel = "bypac1")
	@Query(value = "select mc.* from mapping_code mc where  (mc.asset_code like concat(?1, '%') or mc.asset_desc like concat(concat('%',?1),'%')) order by mc.mfgl_code, mc.asset_desc, mc.proj_code", nativeQuery = true)
	List<MappingCode> findByPAC1(@Param("assetcode") String assetCode);
	
	@RestResource(path = "byac", rel = "byac")
	@Query(value = "select mc.* from mapping_code mc where mc.asset_code like ?1% order by mc.asset_code, mc.proj_code", nativeQuery = true)
	List<MappingCode> findByAC(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bypcnmc1", rel = "bypcnmc1")
	MappingCode findByProjCodeAndAssetCode(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "next", rel = "next")
	@Query(value = "select * from mapping_code where proj_code = ?1 and asset_code > ?2 and mfgl_code >= ?3 order by mfgl_code, asset_code limit 1", nativeQuery = true)
	MappingCode findNext(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prv", rel = "prv")
	@Query(value = "select * from mapping_code where proj_code = ?1 and asset_code < ?2 and mfgl_code <= ?3 order by mfgl_code desc, asset_code desc limit 1", nativeQuery = true)
	MappingCode findPrevious(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "nextc", rel = "nextc")
	@Query(value = "select * from mapping_code where asset_code >= ?2 and (asset_code > ?2 or proj_code > ?1) and mfgl_code >= ?3 order by mfgl_code, asset_code, proj_code limit 1", nativeQuery = true)
	MappingCode findNextC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvc", rel = "prvc")
	@Query(value = "select * from mapping_code where asset_code <= ?2 and (asset_code < ?2 or proj_code < ?1) and mfgl_code <= ?3 order by mfgl_code desc, asset_code desc, proj_code desc limit 1", nativeQuery = true)
	MappingCode findPreviousC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);


}
