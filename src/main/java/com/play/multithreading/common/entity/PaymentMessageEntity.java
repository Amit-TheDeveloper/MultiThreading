package com.play.multithreading.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table
public class PaymentMessageEntity {

    @Id
    private String referenceId;
    @Column
    private double amount;
    @Column
    private String merchantId;
    @Column
    private String processorId;
    @Column
    private String currencyCode;
    @Column
    private String cardNumber;
    @Column
    private String merchantName;
    @Column
    private Date transactedAt;
    @Column
    private String status;
    @Column
    private String settlementStatus;
    @Column
    private Date settledAt;

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
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

    public Date getTransactedAt() {
        return transactedAt;
    }

    public void setTransactedAt(Date transactedAt) {
        this.transactedAt = transactedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSettlementStatus() {
        return settlementStatus;
    }

    public void setSettlementStatus(String settlementStatus) {
        this.settlementStatus = settlementStatus;
    }

    public Date getSettledAt() {
        return settledAt;
    }

    public void setSettledAt(Date settledAt) {
        this.settledAt = settledAt;
    }
}
