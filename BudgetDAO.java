package com.subtrack.model;

import com.subtrack.database.DBConnection;
import com.subtrack.model.Budget;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    // 1. Add or Update Budget
    public boolean saveBudget(Budget budget) {

        String sql = "INSERT INTO budgets " +
                "(user_id, category, budget_amount, budget_month) " +
                "VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "budget_amount = VALUES(budget_amount)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, budget.getUserId());
            ps.setString(2, budget.getCategory());
            ps.setBigDecimal(3, budget.getBudgetAmount());
            ps.setDate(4, Date.valueOf(budget.getBudgetMonth()));

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error saving budget: " + e.getMessage());
            return false;
        }
    }

    // 2. View All Budgets
    public List<Budget> getBudgets(int userId) {

        List<Budget> budgets = new ArrayList<>();

        String sql = "SELECT * FROM budgets WHERE user_id = ? " +
                "ORDER BY budget_month DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Budget budget = new Budget(
                    rs.getInt("user_id"),
                    rs.getString("category"),
                    rs.getBigDecimal("budget_amount"),
                    rs.getDate("budget_month").toLocalDate()
                );

                budget.setBudgetId(rs.getInt("budget_id"));

                budgets.add(budget);
            }

        } catch (SQLException e) {
            System.out.println("Error viewing budgets: " + e.getMessage());
        }

        return budgets;
    }

    // 3. Delete Budget
    public boolean deleteBudget(int budgetId, int userId) {

        String sql = "DELETE FROM budgets " +
                "WHERE budget_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, budgetId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting budget: " + e.getMessage());
            return false;
        }
    }

    // 4. Calculate Category Expenses
    public BigDecimal getCategoryExpenses(
            int userId, String category, Date start, Date end) {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total " +
                "FROM expenses " +
                "WHERE user_id = ? AND category = ? " +
                "AND expense_date >= ? AND expense_date < ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, category);
            ps.setDate(3, start);
            ps.setDate(4, end);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }

        } catch (SQLException e) {
            System.out.println("Error calculating expenses: " + e.getMessage());
        }

        return BigDecimal.ZERO;
    }
}


