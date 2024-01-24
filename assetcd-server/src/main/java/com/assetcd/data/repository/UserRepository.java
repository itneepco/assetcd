/*
 * Copyright 2014-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.assetcd.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.security.access.prepost.PreAuthorize;

import com.assetcd.data.model.User;
import com.assetcd.data.model.UserDefault;

//@Repository
@RepositoryRestResource(path = "ruser", excerptProjection = UserDefault.class)
public interface UserRepository extends CrudRepository<User, Integer> {

	//User findByLogin(String login);
	@RestResource(path = "byemail1", rel = "byemail1")
	User findByEmail(@Param("email") String login);
	
	@RestResource(path = "byuc1", rel = "byuc1")
	User findByUserCode(@Param("uc") String login);
	
	@RestResource(path = "byuc", rel = "byuc")
	List<User> findByUserCodeContaining(@Param("usercode") String userCode);
	
	@RestResource(path = "byemail", rel = "byemail")
	List<User> findByEmailContaining(@Param("email") String email);
	
	@RestResource(path = "byname", rel = "byname")
	@Query(value = "select u.* from users u where u.first_name like %?1% or u.last_name like %?1%", nativeQuery = true)
	List<User> findByName(@Param("name") String name);
	
	@Override
	@PreAuthorize("hasAuthority('Administrator') || #s.id == principal.id")
	public <S extends User> S save(@Param("s")S arg0);
}
