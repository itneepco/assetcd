package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.assetcd.data.model.AttrTab;


@RepositoryRestResource(path = "attrtab")
public interface AttrTabRepository extends JpaRepository<AttrTab, AttrTab.AttrTabCompositeKey> {
	
	@RestResource(path = "all", rel = "all")
	@Query(value = "select * from attr_tab", nativeQuery = true)
	List<AttrTab> findAll1();
	
	AttrTab findByNtabAndNtabd(String ntab, int ntabd);

}
