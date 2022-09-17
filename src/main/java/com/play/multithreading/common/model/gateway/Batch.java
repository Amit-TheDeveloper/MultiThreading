package com.play.multithreading.common.model.gateway;

import com.play.multithreading.common.model.gateway.updated.PaymentMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Batch {

    private String batchNumber;
    private String processorId;
    private String merchantId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private BatchStatus batchStatus;
    private Collection<List<PaymentMessage>> settlementRecords;

    // Deviation from Builder pattern. This needs to be corrected as it becomes mutable instance.
    private List<PaymentMessage> settlementRecordsResponse = new ArrayList<>();

    public List<PaymentMessage> getSettlementRecordsResponse() {
        return settlementRecordsResponse;
    }

    // Deviation from Builder pattern. This needs to be corrected as it becomes mutable instance.
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // Deviation from Builder pattern. This needs to be corrected as it becomes mutable instance.
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setSettlementRecordsResponse(List<PaymentMessage> settlementRecordsResponse) {
        this.settlementRecordsResponse = settlementRecordsResponse;
    }
    // Deviation from Builder pattern. This needs to be corrected as it becomes mutable instance.
    public void setBatchStatus(BatchStatus batchStatus) {
        this.batchStatus = batchStatus;
    }

    public Batch(Builder builder) {
        this.batchNumber = builder.batchNumber;
        this.processorId = builder.processorId;
        this.merchantId = builder.merchantId;
        this.startTime = builder.startTime;
        this.endTime = builder.endTime;
        this.batchStatus = builder.batchStatus;
        this.settlementRecords = builder.settlementRecords;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public String getProcessorId() {
        return processorId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public Collection<List<PaymentMessage>> getSettlementRecords() {
        return settlementRecords;
    }

    public BatchStatus getBatchStatus() {
        return batchStatus;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public static class Builder {

        private String batchNumber;
        private String processorId;
        private String merchantId;
        private LocalDateTime startTime;
        private LocalDateTime endTime;
        private BatchStatus batchStatus;

        Collection<List<PaymentMessage>> settlementRecords;

        public static Builder getInstance() {
            return new Builder();
        }

        public Builder setBatchNumber(String batchNumber) {
            this.batchNumber = batchNumber;
            return this;
        }

        public Builder setProcessorId(String processorId) {
            this.processorId = processorId;
            return this;
        }

        public Builder setMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder setStartTime(LocalDateTime startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setEndTime(LocalDateTime endTime) {
            this.endTime = endTime;
            return this;
        }

        public Builder setBatchStatus(BatchStatus batchStatus) {
            this.batchStatus = batchStatus;
            return this;
        }

        public Builder setSettlementRecords(Collection<List<PaymentMessage>> settlementRecords) {
            this.settlementRecords = settlementRecords;
            return this;
        }

        public Batch build() {
            return new Batch(this);
        }
    }

    @Override
    public String toString() {
        return "Batch{" +
                "batchNumber='" + batchNumber + '\'' +
                ", settlementRecords=" + settlementRecords +
                '}';
    }
}
