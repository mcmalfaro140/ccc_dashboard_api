package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogAlarm;

@Repository
public interface LogAlarmRepository extends JpaRepository<LogAlarm, Long> {
}
