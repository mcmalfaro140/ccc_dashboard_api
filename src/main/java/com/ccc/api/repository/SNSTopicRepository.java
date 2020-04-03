package com.ccc.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.SNSTopic;

@Repository
public interface SNSTopicRepository extends JpaRepository<SNSTopic, Long> {
	@Query(
		value="INSERT IGNORE INTO SNSTopics (TopicName, TopicArn) VALUES (:topicName, :topicArn)",
		nativeQuery=true
	)
	public void insertIgnore(@Param(value="topicName") String topicName, @Param(value="topicArn") String topicArn);
	
	@Query(
		value="SELECT * FROM SNSTopics WHERE TopicName = :topicName AND TopicArn = :topicArn",
		nativeQuery=true
	)
	public Optional<SNSTopic> findByTopicNameAndTopicArn(@Param(value="topicName") String topicName, @Param(value="topicArn") String topicArn);
}
