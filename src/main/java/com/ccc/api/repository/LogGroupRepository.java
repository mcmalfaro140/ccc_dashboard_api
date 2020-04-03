package com.ccc.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.LogGroup;

@Repository
public interface LogGroupRepository extends JpaRepository<LogGroup, Long> {
	@Query(
		value="INSERT IGNORE INTO LogGroups (Name) VALUES (:name)",
		nativeQuery=true
	)
	public void insertIgnore(@Param(value="name") String name);
	
	@Query(
		value="SELECT * FROM LogGroups WHERE Name = :name",
		nativeQuery=true
	)
	public Optional<LogGroup> findByName(@Param(value="name") String name);
}
