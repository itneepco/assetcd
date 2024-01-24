package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.MappingNewCode;

@RepositoryRestResource(path = "mappingnewcode")
public interface MappingNewCodeRepository extends JpaRepository<MappingNewCode, Integer> {
	
	@RestResource(path = "byprojcoder", rel = "byprojcode")
	List<MappingNewCode> findByProjCode(@Param("projcode") String projCode);
	
	@RestResource(path = "bypcnac", rel = "bypcnac")
	List<MappingNewCode> findByProjCodeAndAssetCodeStartingWith(@Param("projcode") String projCode, @Param("assetcode") String assetCode);

	//@RestResource(path = "bypcnmc1", rel = "bypcnmc1")
	List<MappingNewCode> findByProjCodeInAndAssetCodeStartingWith(List<String> projCode, String assetCode);
	
	List<MappingNewCode> findByProjCodeInAndNewAssetCodeStartingWith(List<String> projCode, String assetCode);
	
	@RestResource(path = "bypcnanc", rel = "bypcnanc")
	List<MappingNewCode> findByProjCodeAndNewAssetCodeStartingWith(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "byanc", rel = "byanc")
	List<MappingNewCode> findByNewAssetCodeStartingWith(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bync", rel = "bync")
	@Query(value = "select mnc.* from mapping_new_code mnc where mnc.proj_code = ?1 and (mnc.new_asset_code like concat(?2, '%') or mnc.new_asset_desc like concat(concat('%',?2),'%')) order by mnc.mfgl_code, mnc.new_asset_desc, mnc.proj_code", nativeQuery = true)
	List<MappingNewCode> findBySearchStr1(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bync1", rel = "bync1")
	@Query(value = "select mnc.* from mapping_new_code mnc where (mnc.new_asset_code like concat(?1, '%') or mnc.new_asset_desc like concat(concat('%',?1),'%')) order by mnc.mfgl_code, mnc.new_asset_desc, mnc.proj_code", nativeQuery = true)
	List<MappingNewCode> findBySearchStr(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bypac", rel = "bypac")
	@Query(value = "select mnc.* from mapping_new_code mnc where mnc.proj_code = ?1 and  mnc.asset_code like concat(?2, '%') order by mnc.asset_code, mnc.proj_code", nativeQuery = true)
	List<MappingNewCode> findByPAC(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bypac1", rel = "bypac1")
	@Query(value = "select mnc.* from mapping_new_code mnc where mnc.asset_code like concat(?1, '%') order by mnc.asset_code, mnc.proj_code", nativeQuery = true)
	List<MappingNewCode> findByPAC1(@Param("assetcode") String assetCode);

	
	@RestResource(path = "byac", rel = "byac")
	@Query(value = "select mnc.* from mapping_new_code mnc where mnc.asset_code like ?1% order by mnc.asset_code, mnc.proj_code", nativeQuery = true)
	List<MappingNewCode> findByAC(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bypcnac1", rel = "bypcnac1")
	MappingNewCode findByProjCodeAndAssetCode(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "next", rel = "next")
	@Query(value = "select * from mapping_new_code where proj_code = ?1 and asset_code > ?2 order by asset_code limit 1", nativeQuery = true)
	MappingNewCode findNext(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prv", rel = "prv")
	@Query(value = "select * from mapping_new_code where proj_code = ?1 and asset_code < ?2 order by asset_code desc limit 1", nativeQuery = true)
	MappingNewCode findPrevious(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "nextc", rel = "nextc")
	@Query(value = "select * from mapping_new_code where asset_code >= ?2 and (asset_code > ?2 or proj_code > ?1) order by asset_code, proj_code limit 1", nativeQuery = true)
	MappingNewCode findNextC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvc", rel = "prvc")
	@Query(value = "select * from mapping_new_code where asset_code <= ?2 and (asset_code < ?2 or proj_code < ?1) order by asset_code desc, proj_code desc limit 1", nativeQuery = true)
	MappingNewCode findPreviousC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

}
