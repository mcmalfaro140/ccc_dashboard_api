package com.ccc.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.Keyword;

@Repository
public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	@Query(
		value="SELECT * FROM Keywords WHERE Word = :word",
		nativeQuery=true
	)
	Optional<Keyword> findByWord(@Param(value="word") String word);
}
