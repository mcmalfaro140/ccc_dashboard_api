package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.MetricAlarm;

@Repository
public interface MetricAlarmRepository extends JpaRepository<MetricAlarm, Integer> {
}
