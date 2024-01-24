package com.assetcd.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.MappingNewAssetCode;

@RepositoryRestResource(path = "mappingnewassetcode")
public interface MappingNewAssetCodeRepository extends JpaRepository<MappingNewAssetCode, Integer> {

}
