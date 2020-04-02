package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogLevelCriteria;

@Repository
public interface LogLevelCriteriaRepository extends JpaRepository<LogLevelCriteria, Long> {
}
