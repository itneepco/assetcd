package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.AssetCodeMasDesc;

@RepositoryRestResource(path = "acmd")
public interface AssetCodeMasDescRepository extends JpaRepository<AssetCodeMasDesc, Integer> {
	
	@RestResource(path = "bync", rel = "bync")
	List<AssetCodeMasDesc> findByNewCodeOrderByIdDesc(@Param("newcode") String newCode);
	
	@RestResource(path = "byd", rel = "byd")
	AssetCodeMasDesc findByNewCodeAndNewDescAndShortDesc(@Param("newcode") String newCode, @Param("newdesc") String newDesc, @Param("shortdesc") String shortDesc);
	//AssetCodeMasDesc findByNewCodeAndPreDescAndPostDescAndShortDesc(@Param("newcode") String newCode, @Param("predesc") String preDesc, @Param("postdesc") String postDesc, @Param("shortdesc") String shortDesc);

}
