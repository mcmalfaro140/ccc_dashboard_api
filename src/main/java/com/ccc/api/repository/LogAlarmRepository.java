package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogAlarm;

@Repository
public interface LogAlarmRepository extends JpaRepository<LogAlarm, Long> {
	@Query(
		value="INSERT INTO LogAlarms (LogLevelCriteriaId, AlarmName, Comparison) VALUES (:logLevelCriteriaId, :alarmName, :keywordRelationship)",
		nativeQuery=true
	)
	public void insert(
			@Param(value="logLevelCriteriaId") Long logLevelCriteriaId,
			@Param(value="alarmName") String alarmName,
			@Param(value="keywordRelationship") String keywordRelationship
	);
}
