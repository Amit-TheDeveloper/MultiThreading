package com.play.multithreading.common.entity;

import com.play.multithreading.common.model.gateway.BatchStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class BatchEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long batchId;
    private int numberOfSettledRecords;
    private String batchNumber;
    private String processorId;
    private String merchantId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String batchStatus;

    public Long getBatchId() {
        return batchId;
    }

    public void setBatchId(Long batchId) {
        this.batchId = batchId;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getBatchStatus() {
        return batchStatus;
    }

    public void setBatchStatus(String batchStatus) {
        this.batchStatus = batchStatus;
    }

    public int getNumberOfSettledRecords() {
        return numberOfSettledRecords;
    }

    public void setNumberOfSettledRecords(int numberOfSettledRecords) {
        this.numberOfSettledRecords = numberOfSettledRecords;
    }
}
