package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
}