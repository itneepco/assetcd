package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.MappingRejectedCode;

@RepositoryRestResource(path = "mappingrejcode")
public interface MappingRejectedCodeRepository extends JpaRepository<MappingRejectedCode, Integer> {

	@RestResource(path = "rejcoder", rel = "rejcoder")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code like ?2% and rej_code = 'R'", nativeQuery = true)
	List<MappingRejectedCode> findByRejCodeR(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "rejcodes", rel = "rejcodes")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code like ?2% and rej_code = 'S'", nativeQuery = true)
	List<MappingRejectedCode> findByRejCodeS(@Param("projcode") String projCode, @Param("assetcode") String assetCode);

	@RestResource(path = "rejcoderc", rel = "rejcoderc")
	@Query(value = "select * from mapping_rejected_code where asset_code like ?1% and rej_code = 'R'", nativeQuery = true)
	List<MappingRejectedCode> findByRejCodeRC(@Param("assetcode") String assetCode);
	
	@RestResource(path = "rejcodesc", rel = "rejcodesc")
	@Query(value = "select * from mapping_rejected_code where asset_code like ?1% and rej_code = 'S'", nativeQuery = true)
	List<MappingRejectedCode> findByRejCodeSC(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bypacr", rel = "bypacr")
	@Query(value = "select mrc.* from mapping_rejected_code mrc where mrc.proj_code = ?1 and  mrc.asset_code like concat(?2, '%') and rej_code = 'R' order by mrc.asset_code, mrc.proj_code", nativeQuery = true)
	List<MappingRejectedCode> findByPACR(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bypacr1", rel = "bypacr1")
	@Query(value = "select mrc.* from mapping_rejected_code mrc where  mrc.asset_code like concat(?1, '%') and rej_code = 'R' order by mrc.asset_code, mrc.proj_code", nativeQuery = true)
	List<MappingRejectedCode> findByPACR1(@Param("assetcode") String assetCode);
	
	@RestResource(path = "bypacs", rel = "bypacs")
	@Query(value = "select mrc.* from mapping_rejected_code mrc where mrc.proj_code = ?1 and  mrc.asset_code like concat(?2, '%') and rej_code = 'S' order by mrc.asset_code, mrc.proj_code", nativeQuery = true)
	List<MappingRejectedCode> findByPACS(@Param("projcode") String projCode, @Param("assetcode") String assetCode);
	
	@RestResource(path = "bypacs1", rel = "bypacs1")
	@Query(value = "select mrc.* from mapping_rejected_code mrc where  mrc.asset_code like concat(?1, '%') and rej_code = 'S' order by mrc.asset_code, mrc.proj_code", nativeQuery = true)
	List<MappingRejectedCode> findByPACS1(@Param("assetcode") String assetCode);
	
	@RestResource(path = "nextr", rel = "nextr")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code > ?2 and rej_code = 'R' order by asset_code limit 1", nativeQuery = true)
	MappingRejectedCode findNextR(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvr", rel = "prvr")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code < ?2 and rej_code = 'R' order by asset_code desc limit 1", nativeQuery = true)
	MappingRejectedCode findPreviousR(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "nextrc", rel = "nextrc")
	@Query(value = "select * from mapping_rejected_code where asset_code >= ?2 and (asset_code > ?2 or proj_code > ?1) and rej_code = 'R' order by asset_code, proj_code limit 1", nativeQuery = true)
	MappingRejectedCode findNextRC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvrc", rel = "prvrc")
	@Query(value = "select * from mapping_rejected_code where asset_code <= ?2 and (asset_code < ?2 or proj_code < ?1) and rej_code = 'R' order by asset_code desc, proj_code desc limit 1", nativeQuery = true)
	MappingRejectedCode findPreviousRC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);


	@RestResource(path = "nexts", rel = "nexts")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code > ?2 and rej_code = 'S' order by asset_code limit 1", nativeQuery = true)
	MappingRejectedCode findNextS(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvs", rel = "prvs")
	@Query(value = "select * from mapping_rejected_code where proj_code = ?1 and asset_code < ?2 and rej_code = 'S' order by asset_code desc limit 1", nativeQuery = true)
	MappingRejectedCode findPreviousS(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "nextsc", rel = "nextsc")
	@Query(value = "select * from mapping_rejected_code where asset_code >= ?2 and (asset_code > ?2 or proj_code > ?1) and rej_code = 'S' order by asset_code, proj_code limit 1", nativeQuery = true)
	MappingRejectedCode findNextSC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	@RestResource(path = "prvsc", rel = "prvsc")
	@Query(value = "select * from mapping_rejected_code where asset_code <= ?2 and (asset_code < ?2 or proj_code < ?1) and rej_code = 'S' order by asset_code desc, proj_code desc limit 1", nativeQuery = true)
	MappingRejectedCode findPreviousSC(@Param("projcode") String projCode, @Param("assetcode") String assetCode, @Param("mfglcode") Integer mfglCode);

	
	@RestResource(path = "bypcnmc1", rel = "bypcnmc1")
	MappingRejectedCode findByProjCodeAndAssetCode(@Param("projcode") String projCode, @Param("assetcode") String assetCode);

	
}
