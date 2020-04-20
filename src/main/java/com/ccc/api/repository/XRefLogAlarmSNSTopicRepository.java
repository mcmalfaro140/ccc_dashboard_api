package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefLogAlarmSNSTopic;

@Repository
public interface XRefLogAlarmSNSTopicRepository extends JpaRepository<XRefLogAlarmSNSTopic, Long> {	
	@Modifying
	@Query(
		value="DELETE FROM XRefLogAlarmSNSTopic WHERE UserId = :userId AND LogAlarmId = :logAlarmId AND SNSTopicId = :snsTopicId",
		nativeQuery=true
	)
	public void deleteByFields(@Param(value="userId") Long userId, @Param(value="logAlarmId") Long logAlarmId, @Param(value="snsTopicId") Long snsTopicId);
}
