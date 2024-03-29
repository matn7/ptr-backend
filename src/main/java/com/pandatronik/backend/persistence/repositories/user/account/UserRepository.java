package com.pandatronik.backend.persistence.repositories.user.account;

import com.pandatronik.backend.persistence.domain.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	Optional<UserEntity> findByUsername(String username);

	UserEntity findByEmail(String email);

	UserEntity getById(Long id);

	@Transactional
	@Modifying
	@Query("update UserEntity u set u.password = :password where u.id = :userId")
	void updateUserPassword(@Param("userId") long userId, @Param("password") String password);
	
	@Transactional
	@Modifying
	@Query("update UserEntity u set u.email = :email where u.id = :userId")
	void updateUserEmail(@Param("userId") long userId, @Param("email") String email);

	@Transactional
	@Modifying
	@Query("update UserEntity u set u.enabled = true where u.id = :userId")
	void activateUser(@Param("userId") long userId);

//	@Transactional
	@Override
	void deleteById(Long aLong);
}