package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.MappingNewAssetCodeUploadError;

@RepositoryRestResource(path = "mappingnewassetcodeuploaderror")
public interface MappingNewAssetCodeUploadErrorRepository extends JpaRepository<MappingNewAssetCodeUploadError, Integer>{

	List<MappingNewAssetCodeUploadError> findByUploadId(String uploadId);
}
