package com.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Payment {

    private int paymentId;
    private int userId;
    private int subscriptionId;
    private String serviceName;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentMethod;
    private String paymentStatus;
    private String transactionReference;

    // Constructor
    public Payment(int userId, int subscriptionId,
            String serviceName, BigDecimal amount,
            LocalDate paymentDate, String paymentMethod,
            String paymentStatus, String transactionReference) {

        this.userId = userId;
        this.subscriptionId = subscriptionId;
        this.serviceName = serviceName;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.transactionReference = transactionReference;
    }

    // Getters
    public int getPaymentId() {
        return paymentId;
    }

    public int getUserId() {
        return userId;
    }

    public int getSubscriptionId() {
        return subscriptionId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    // Setters
    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}



