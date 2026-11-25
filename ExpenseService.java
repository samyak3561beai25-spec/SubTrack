package com.subtrack.service;

import com.subtrack.dao.ExpenseDAO;
import com.subtrack.model.Expense;

import java.math.BigDecimal;
import java.util.List;

public class ExpenseService {

    private ExpenseDAO expenseDAO = new ExpenseDAO();

    // 1. Add Expense
    public boolean addExpense(Expense expense) {

        if (expense.getAmount() == null ||
                expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("Amount must be greater than zero.");
            return false;
        }

        if (expense.getExpenseName() == null ||
                expense.getExpenseName().isBlank()) {

            System.out.println("Expense name is required.");
            return false;
        }

        if (expense.getCategory() == null ||
                expense.getCategory().isBlank()) {

            System.out.println("Category is required.");
            return false;
        }

        if (expense.getExpenseDate() == null) {
            System.out.println("Expense date is required.");
            return false;
        }

        boolean result = expenseDAO.addExpense(expense);

        if (result) {
            System.out.println("Expense added successfully!");
        }

        return result;
    }

    // 2. View Expenses
    public List<Expense> viewExpenses(int userId) {
        return expenseDAO.getExpenses(userId);
    }

    // 3. Update Expense
    public boolean updateExpense(Expense expense) {

        if (expense.getAmount() == null ||
                expense.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("Amount must be greater than zero.");
            return false;
        }

        if (expense.getExpenseName() == null ||
                expense.getExpenseName().isBlank()) {

            System.out.println("Expense name is required.");
            return false;
        }

        if (expense.getCategory() == null ||
                expense.getCategory().isBlank()) {

            System.out.println("Category is required.");
            return false;
        }

        if (expense.getExpenseDate() == null) {
            System.out.println("Expense date is required.");
            return false;
        }

        boolean result = expenseDAO.updateExpense(expense);

        if (result) {
            System.out.println("Expense updated successfully!");
        } else {
            System.out.println("Expense not found.");
        }

        return result;
    }

    // 4. Delete Expense
    public boolean deleteExpense(int expenseId, int userId) {

        boolean result = expenseDAO.deleteExpense(expenseId, userId);

        if (result) {
            System.out.println("Expense deleted successfully!");
        } else {
            System.out.println("Expense not found.");
        }

        return result;
    }

    // 5. Monthly Expense Total
    public BigDecimal getMonthlyExpenses(
            int userId, java.time.LocalDate month) {

        java.time.LocalDate start =
                month.withDayOfMonth(1);

        java.time.LocalDate end =
                start.plusMonths(1);

        return expenseDAO.getMonthlyExpenses(
            userId,
            java.sql.Date.valueOf(start),
            java.sql.Date.valueOf(end)
        );
    }
}


