package com.subtrack.model;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Budget {

    private int budgetId;
    private int userId;
    private String category;
    private BigDecimal budgetAmount;
    private LocalDate budgetMonth;

    // Constructor
    public Budget(int userId, String category,
            BigDecimal budgetAmount, LocalDate budgetMonth) {

        this.userId = userId;
        this.category = category;
        this.budgetAmount = budgetAmount;
        this.budgetMonth = budgetMonth;
    }

    // Getters
    public int getBudgetId() {
        return budgetId;
    }

    public int getUserId() {
        return userId;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }

    public LocalDate getBudgetMonth() {
        return budgetMonth;
    }

    // Setters
    public void setBudgetId(int budgetId) {
        this.budgetId = budgetId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }

    public void setBudgetMonth(LocalDate budgetMonth) {
        this.budgetMonth = budgetMonth;
    }
}

