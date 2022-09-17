package com.play.multithreading.common.repository;

import com.play.multithreading.common.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, String> {

    @Query("select a from TransactionEntity a where a.transactedAt BETWEEN :from AND :to")
   //@Query("select a from TransactionEntity a where a.transactedAt >= :from AND a.transactedAt < :to")
    List<TransactionEntity> findAllWithTransactedAtBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);
}
