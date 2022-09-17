package com.play.multithreading.common.model.gateway.updated;

import java.time.LocalDateTime;
import java.util.Date;

public class PaymentMessage {

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

    public PaymentMessage(Builder builder) {
        this.referenceId = builder.referenceId;
        this.amount = builder.amount;
        this.merchantId = builder.merchantId;
        this.processorId = builder.processorId;
        this.currencyCode = builder.currencyCode;
        this.cardNumber = builder.cardNumber;
        this.merchantName = builder.merchantName;
        this.transactedAt = builder.transactedAt;
        this.status = builder.status;
        this.settlementStatus = builder.settlementStatus;
        this.settledAt = builder.settledAt;
    }

    public Long getReferenceId() {
        return referenceId;
    }
    public double getAmount() {
        return amount;
    }
    public String getMerchantId() {
        return merchantId;
    }
    public String getProcessorId() {
        return processorId;
    }
    public String getCurrencyCode() {
        return currencyCode;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getMerchantName() {
        return merchantName;
    }
    public LocalDateTime getTransactedAt() {
        return transactedAt;
    }
    public String getStatus() {
        return status;
    }
    public SettlementStatus getSettlementStatus() {
        return settlementStatus;
    }
    public LocalDateTime getSettledAt() {
        return settledAt;
    }

    public static class Builder {
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

        public static Builder getInstance() {
            return new Builder();
        }
        public Builder withReferenceId(Long referenceId) {
            this.referenceId = referenceId;
            return this;
        }

        public Builder withAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder withMerchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder withProcessorId(String processorId) {
            this.processorId = processorId;
            return this;
        }

        public Builder withCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
            return this;
        }

        public Builder withCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
            return this;
        }

        public Builder withMerchantName(String merchantName) {
            this.merchantName = merchantName;
            return this;
        }
        public Builder withTransactedAt(LocalDateTime transactedAt) {
            this.transactedAt = transactedAt;
            return this;
        }
        public Builder withStatus(String status) {
            this.status = status;
            return this;
        }
        public Builder withSettlementStatus(SettlementStatus settlementStatus) {
            this.settlementStatus = settlementStatus;
            return this;
        }
        public Builder withSettledAt(LocalDateTime settledAt) {
            this.settledAt = settledAt;
            return this;
        }
        public PaymentMessage build() {
            return new PaymentMessage(this);
        }

        public static Builder from(PaymentMessage srcPM) {
            return getInstance().
                    withAmount(srcPM.getAmount()).
                    withCardNumber(srcPM.getCardNumber()).
                    withCurrencyCode(srcPM.getCurrencyCode()).
                    withMerchantId(srcPM.getMerchantId()).
                    withProcessorId(srcPM.getProcessorId()).
                    withMerchantName(srcPM.getMerchantName()).
                    withStatus(srcPM.getStatus()).
                    withReferenceId(srcPM.getReferenceId()).
                    withTransactedAt(srcPM.getTransactedAt());

        }
    }

    @Override
    public String toString() {
        return "PaymentMessage{" +
                "referenceId=" + referenceId +
                ", merchantId='" + merchantId + '\'' +
                ", transactedAt=" + transactedAt +
                '}';
    }
}
