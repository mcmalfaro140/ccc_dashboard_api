package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogGroup;

@Repository
public interface LogGroupRepository extends JpaRepository<LogGroup, Long> {
}
