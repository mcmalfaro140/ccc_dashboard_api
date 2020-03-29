package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.XRefLogAlarmKeyword;

@Repository
public interface XRefLogAlarmKeywordRepository extends JpaRepository<XRefLogAlarmKeyword, Integer> {
}
