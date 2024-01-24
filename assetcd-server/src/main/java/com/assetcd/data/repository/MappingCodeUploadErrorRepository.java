package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.MappingCodeUploadError;

@RepositoryRestResource(path = "mappingcodeuploaderror")
public interface MappingCodeUploadErrorRepository extends JpaRepository<MappingCodeUploadError, Integer> {

	List<MappingCodeUploadError> findByUploadId(String uploadId);
}
