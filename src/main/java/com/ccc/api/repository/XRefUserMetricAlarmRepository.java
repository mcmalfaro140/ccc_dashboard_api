package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefUserMetricAlarm;

@Repository
public interface XRefUserMetricAlarmRepository extends JpaRepository<XRefUserMetricAlarm, Integer> {
}
