package com.ccc.api.repository;

import java.util.List;

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
	
	@Query(
		value="SELECT * FROM LogAlarms NATURAL JOIN XRefUserLogAlarmSNSTopic WHERE UserId = :userId",
		nativeQuery=true
	)
	public List<LogAlarm> findByUserId(@Param(value="userId") Long userId);
}
