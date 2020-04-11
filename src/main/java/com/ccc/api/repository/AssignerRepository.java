package com.ccc.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ccc.api.model.Assigner;

@Repository
public interface AssignerRepository extends JpaRepository<Assigner, Long> {
}
