package com.assetcd.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.assetcd.data.model.Company;

@RepositoryRestResource(path = "company")
public interface CompanyRepository extends JpaRepository<Company, Integer> {

}
