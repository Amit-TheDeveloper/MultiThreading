package com.play.multithreading.common.entity;

import com.play.multithreading.common.model.gateway.updated.SettlementStatus;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long referenceId;

    private double amount;

    private String merchantId;

    private String processorId;

    private String currencyCode;

    private String cardNumber;

    private String merchantName;

    private LocalDateTime transactedAt;

    private String status;

    private SettlementStatus settlementStatus;

    private LocalDateTime settledAt;

    public Long getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(Long referenceId) {
        this.referenceId = referenceId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProcessorId() {
        return processorId;
    }

    public void setProcessorId(String processorId) {
        this.processorId = processorId;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }

    public LocalDateTime getTransactedAt() {
        return transactedAt;
    }

    public void setTransactedAt(LocalDateTime transactedAt) {
        this.transactedAt = transactedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public SettlementStatus getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(SettlementStatus settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public LocalDateTime getSettledAt() {
        return settledAt;
    }

    public void setSettledAt(LocalDateTime settledAt) {
        this.settledAt = settledAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransactionEntity that = (TransactionEntity) o;
        return referenceId.equals(that.referenceId);
    }

    @Override
    public String toString() {
        return "TransactionEntity{" +
                "referenceId='" + referenceId + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(referenceId);
    }
}
