package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefLogAlarmLogGroup;

@Repository
public interface XRefLogAlarmLogGroupRepository extends JpaRepository<XRefLogAlarmLogGroup, Integer> {
}
