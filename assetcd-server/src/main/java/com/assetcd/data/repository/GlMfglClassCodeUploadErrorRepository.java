package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.GlMfglClassCodeUploadError;

@RepositoryRestResource(path = "gmccuploaderror")
public interface GlMfglClassCodeUploadErrorRepository extends JpaRepository<GlMfglClassCodeUploadError, Integer> {

	List<GlMfglClassCodeUploadError> findByUploadId(String uploadId);
}
