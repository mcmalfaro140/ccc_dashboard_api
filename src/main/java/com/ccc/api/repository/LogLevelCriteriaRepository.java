package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogLevelCriteria;

@Repository
public interface LogLevelCriteriaRepository extends JpaRepository<LogLevelCriteria, Integer> {
	@Query("INSERT IGNORE INTO (LogLevel, Comparison) VALUES (:logLevel, :comparison);")
	public void insertIfNotExists(@Param("logLevel") String logLevel, @Param("comparison") String comparison);
	
	@Query("SELECT LogLevelCriteriaId FROM LogLevelCriteria WHERE LogLevel = :logLevel AND Comparison = :comparison;")
	public Integer getId(@Param("logLevel") String logLevel, @Param("comparison") String comparison);
}
