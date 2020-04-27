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
		value="SELECT * FROM SNSTopics WHERE TopicName = :topicName",
		nativeQuery=true
	)
	Optional<SNSTopic> findByTopicName(@Param(value="topicName") String topicName);
}
