package com.ccc.api.controller;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer>,QueryByExampleExecutor<Users>  {
	@Query("select u from Users u where u.username = :username")
	  Users findByUsername(@Param("username") String username);
	
	}

