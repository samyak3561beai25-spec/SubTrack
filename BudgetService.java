package com.subtrack.service;

import com.subtrack.dao.BudgetDAO;
import com.subtrack.model.Budget;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

public class BudgetService {

    private BudgetDAO budgetDAO = new BudgetDAO();

    // 1. Set Monthly Budget
    public boolean setBudget(Budget budget) {

        if (budget.getBudgetAmount() == null ||
                budget.getBudgetAmount().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("Budget must be greater than zero.");
            return false;
        }

        if (budget.getCategory() == null ||
                budget.getCategory().isBlank()) {

            System.out.println("Category is required.");
            return false;
        }

        if (budget.getBudgetMonth() == null ||
                budget.getBudgetMonth().getDayOfMonth() != 1) {

            System.out.println("Budget month must be the first day of a month.");
            return false;
        }

        boolean result = budgetDAO.saveBudget(budget);

        if (result) {
            System.out.println("Budget saved successfully!");
        }

        return result;
    }

    // 2. View Budgets
    public List<Budget> viewBudgets(int userId) {
        return budgetDAO.getBudgets(userId);
    }

    // 3. Delete Budget
    public boolean deleteBudget(int budgetId, int userId) {

        boolean result = budgetDAO.deleteBudget(budgetId, userId);

        if (result) {
            System.out.println("Budget deleted successfully!");
        } else {
            System.out.println("Budget not found.");
        }

        return result;
    }

    // 4. Calculate Remaining Budget
    public BigDecimal getRemainingBudget(
            Budget budget, BigDecimal spent) {

        return budget.getBudgetAmount().subtract(spent);
    }

    // 5. Calculate Spending for a Budget
    public BigDecimal getBudgetSpending(Budget budget) {

        LocalDate start = budget.getBudgetMonth();
        LocalDate end = start.plusMonths(1);

        return budgetDAO.getCategoryExpenses(
            budget.getUserId(),
            budget.getCategory(),
            Date.valueOf(start),
            Date.valueOf(end)
        );
    }

    // 6. Get Remaining Budget
    public BigDecimal calculateRemainingBudget(Budget budget) {

        BigDecimal spent = getBudgetSpending(budget);

        return getRemainingBudget(budget, spent);
    }
}

