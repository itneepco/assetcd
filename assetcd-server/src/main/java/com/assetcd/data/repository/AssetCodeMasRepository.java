package com.assetcd.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.AssetCodeMas;

@RepositoryRestResource(path = "acm")
public interface AssetCodeMasRepository extends JpaRepository<AssetCodeMas, Integer> {
	
	@RestResource(path = "bync", rel = "bync")
	AssetCodeMas findByNewCode(@Param("newcode") String newCode);
	
	@RestResource(path = "byncnmfgl", rel = "byncnmfgl")
	AssetCodeMas findByNewCodeAndMfglCode(@Param("newcode") String newCode, @Param("mfglcode") Integer mfglCode);

}
