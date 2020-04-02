package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogLevelCriteria;

@Repository
public interface LogLevelCriteriaRepository extends JpaRepository<LogLevelCriteria, Long> {
	@Query(
		value="INSERT IGNORE INTO LogLevelCriteria (LogLevel, Comparison) VALUES (:logLevel, :comparison)",
		nativeQuery=true
	)
	public void insertIgnore(@Param(value="logLevel") String logLevel, @Param(value="comparison") String comparison);
	
	@Query(
		value="SELECT LogLevelCriteriaId FROM LogLevelCriteria WHERE LogLevel = :logLevel AND Comparison = :copmarison",
		nativeQuery=true
	)
	public Long findId(@Param(value="logLevel") String logLevel, @Param(value="comparison") String comparison);
}
