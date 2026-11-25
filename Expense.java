package com.subtrack.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Expense {

    private int expenseId;
    private int userId;
    private String expenseName;
    private String category;
    private BigDecimal amount;
    private LocalDate expenseDate;
    private String paymentMethod;
    private String description;

    // Constructor
    public Expense(int userId, String expenseName,
            String category, BigDecimal amount,
            LocalDate expenseDate, String paymentMethod,
            String description) {

        this.userId = userId;
        this.expenseName = expenseName;
        this.category = category;
        this.amount = amount;
        this.expenseDate = expenseDate;
        this.paymentMethod = paymentMethod;
        this.description = description;
    }

    // Getters
    public int getExpenseId() {
        return expenseId;
    }

    public int getUserId() {
        return userId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getExpenseDate() {
        return expenseDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getDescription() {
        return description;
    }

    // Setters
    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setExpenseDate(LocalDate expenseDate) {
        this.expenseDate = expenseDate;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
