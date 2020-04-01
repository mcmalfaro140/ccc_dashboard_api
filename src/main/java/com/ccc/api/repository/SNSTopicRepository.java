package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.SNSTopic;

@Repository
public interface SNSTopicRepository extends JpaRepository<SNSTopic, Long> {
}
