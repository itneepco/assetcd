package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.GlCodeUploadError;

@RepositoryRestResource(path = "gcuploaderror")
public interface GlCodeUploadErrorRepository extends JpaRepository<GlCodeUploadError, Integer> {
	
	List<GlCodeUploadError> findByUploadId(String uploadId);

}
