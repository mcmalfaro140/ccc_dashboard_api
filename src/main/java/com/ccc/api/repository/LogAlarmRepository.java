package com.ccc.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogAlarm;

@Repository
public interface LogAlarmRepository extends JpaRepository<LogAlarm, Integer> {
	@Query(value="SELECT LA FROM LogAlarms LA NATURAL JOIN XRefUserLogAlarm XRUAL NATURAL JOIN Users U WHERE U.UserId = :userId;")
	public List<LogAlarm> findByUserId(@Param("userId") Integer userId);
}
