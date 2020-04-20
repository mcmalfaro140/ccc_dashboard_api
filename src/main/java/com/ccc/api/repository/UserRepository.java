package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	@Query(
		value="SELECT * FROM Users WHERE Username = :username",
		nativeQuery=true
	)
	User findByUsername(@Param(value="username") String username);
}
