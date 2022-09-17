package com.play.multithreading.common.repository;

import com.play.multithreading.common.entity.BatchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchRepository extends JpaRepository<BatchEntity, Long> {
}
