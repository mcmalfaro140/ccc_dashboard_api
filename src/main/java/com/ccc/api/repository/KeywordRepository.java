package com.ccc.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	@Modifying
	@Query(
		value="SELECT * FROM Keywords WHERE Word = :word",
		nativeQuery=true
	)
	public Optional<Keyword> findByWord(@Param(value="word") String word);
}
