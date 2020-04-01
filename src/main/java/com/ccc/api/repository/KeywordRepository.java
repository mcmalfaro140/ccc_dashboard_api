package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
}
