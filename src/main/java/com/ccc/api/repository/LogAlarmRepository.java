package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogAlarm;

@Repository
public interface LogAlarmRepository extends JpaRepository<LogAlarm, Long> {
	@Modifying
	@Query(
		value="DELETE FROM LogAlarms WHERE LogAlarmId = :logAlarmId",
		nativeQuery=true
	)
	public void deleteByLogAlarmId(@Param("logAlarmId") Long logAlarmId);
}
