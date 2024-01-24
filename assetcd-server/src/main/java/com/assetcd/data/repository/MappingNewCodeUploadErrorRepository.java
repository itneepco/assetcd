package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.MappingNewCodeUploadError;

@RepositoryRestResource(path = "mappingnewcodeuploaderror")
public interface MappingNewCodeUploadErrorRepository extends JpaRepository<MappingNewCodeUploadError, Integer> {
	
	List<MappingNewCodeUploadError> findByUploadId(String uploadId);

}
