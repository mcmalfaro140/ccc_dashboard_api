package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefUserLogAlarmSNSTopic;

@Repository
public interface XRefUserLogAlarmSNSTopicRepository extends JpaRepository<XRefUserLogAlarmSNSTopic, Long> {
	@Modifying
	@Query(
		value="DELETE FROM XRefUserLogAlarmSNSTopic WHERE UserId = :userId AND LogAlarmId = :logAlarmId AND SNSTopicId = :snsTopicId",
		nativeQuery=true
	)
	public void deleteByForeignKeys(
			@Param(value="userId") Long userId,
			@Param(value="logAlarmId") Long logAlarmId,
			@Param(value="snsTopicId") Long snsTopicId
	);
}
