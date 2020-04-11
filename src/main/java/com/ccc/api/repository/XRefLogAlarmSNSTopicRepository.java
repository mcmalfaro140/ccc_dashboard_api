package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefLogAlarmSNSTopic;

@Repository
public interface XRefLogAlarmSNSTopicRepository extends JpaRepository<XRefLogAlarmSNSTopic, Long> {
}
