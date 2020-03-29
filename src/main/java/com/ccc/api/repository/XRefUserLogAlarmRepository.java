package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefUserLogAlarm;

@Repository
public interface XRefUserLogAlarmRepository extends JpaRepository<XRefUserLogAlarm, Integer> {
	@Query("DELETE FROM XRefUserLogAlarm WHERE UserId = :userId;")
	public void deleteByUserId(@Param("userId") Integer userId);
	
	@Query("SELECT COUNT(*) FROM XRefUserLogAlarm WHERE UserId = :userId;")
	public Integer countUserLogAlarms(@Param("userId") Integer userId);
}
