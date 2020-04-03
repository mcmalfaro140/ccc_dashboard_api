package com.ccc.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogAlarm;

@Repository
public interface LogAlarmRepository extends JpaRepository<LogAlarm, Long> {
	@Query(
		value="SELECT * FROM LogAlarms NATURAL JOIN XRefUserLogAlarm NATURAL JOIN Users WHERE UserId = :userId",
		nativeQuery=true
	)
	public List<LogAlarm> findByUserId(@Param(value="userId") Long userId);
}
