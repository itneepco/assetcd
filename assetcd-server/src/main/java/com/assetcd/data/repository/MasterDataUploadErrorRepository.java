package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.MasterDataUploadError;

@RepositoryRestResource(path = "masterdatauploaderror")
public interface MasterDataUploadErrorRepository extends JpaRepository<MasterDataUploadError, Integer> {
	
	List<MasterDataUploadError> findByUploadId(String uploadId);

}
