/**
 * @author AymanAljohary
 * @IDE Spring Tool Suite
 */

package com.mondiamedia.tech.task.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import com.mondiamedia.tech.task.entities.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long> {

	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);
	
	@Query(value="select * from Users order by id desc", nativeQuery=true) 
	Page<UserEntity> findAllOrderByIdDesc(Pageable pageableRequestString);
	
	@Query(value="Select * From users u WHERE u.email = :email AND u.encrypted_password = :password", nativeQuery=true) 
	UserEntity findByEmailAndEncryptedPassword(String email, String password);
}
