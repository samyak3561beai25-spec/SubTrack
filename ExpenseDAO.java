package com.subtrack.model;

import com.subtrack.database.DBConnection;
import com.subtrack.model.Expense;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class ExpenseDAO {

    // 1. Add Expense
    public boolean addExpense(Expense expense) {

        String sql = "INSERT INTO expenses " +
                "(user_id, expense_name, category, amount, " +
                "expense_date, payment_method, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expense.getUserId());
            ps.setString(2, expense.getExpenseName());
            ps.setString(3, expense.getCategory());
            ps.setBigDecimal(4, expense.getAmount());
            ps.setDate(5, Date.valueOf(expense.getExpenseDate()));
            ps.setString(6, expense.getPaymentMethod());
            ps.setString(7, expense.getDescription());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding expense: " + e.getMessage());
            return false;
        }
    }

    // 2. View Expenses
    public List<Expense> getExpenses(int userId) {

        List<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses " +
                "WHERE user_id = ? ORDER BY expense_date DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Expense expense = new Expense(
                    rs.getInt("user_id"),
                    rs.getString("expense_name"),
                    rs.getString("category"),
                    rs.getBigDecimal("amount"),
                    rs.getDate("expense_date").toLocalDate(),
                    rs.getString("payment_method"),
                    rs.getString("description")
                );

                expense.setExpenseId(rs.getInt("expense_id"));

                expenses.add(expense);
            }

        } catch (SQLException e) {
            System.out.println("Error viewing expenses: " + e.getMessage());
        }

        return expenses;
    }

    // 3. Update Expense
    public boolean updateExpense(Expense expense) {

        String sql = "UPDATE expenses SET " +
                "expense_name = ?, category = ?, amount = ?, " +
                "expense_date = ?, payment_method = ?, description = ? " +
                "WHERE expense_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, expense.getExpenseName());
            ps.setString(2, expense.getCategory());
            ps.setBigDecimal(3, expense.getAmount());
            ps.setDate(4, Date.valueOf(expense.getExpenseDate()));
            ps.setString(5, expense.getPaymentMethod());
            ps.setString(6, expense.getDescription());
            ps.setInt(7, expense.getExpenseId());
            ps.setInt(8, expense.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error updating expense: " + e.getMessage());
            return false;
        }
    }

    // 4. Delete Expense
    public boolean deleteExpense(int expenseId, int userId) {

        String sql = "DELETE FROM expenses " +
                "WHERE expense_id = ? AND user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, expenseId);
            ps.setInt(2, userId);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error deleting expense: " + e.getMessage());
            return false;
        }
    }

    // 5. Calculate Monthly Expenses
    public BigDecimal getMonthlyExpenses(
            int userId, Date start, Date end) {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total " +
                "FROM expenses " +
                "WHERE user_id = ? " +
                "AND expense_date >= ? AND expense_date < ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setDate(2, start);
            ps.setDate(3, end);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }

        } catch (SQLException e) {
            System.out.println("Error calculating monthly expenses: "
                    + e.getMessage());
        }

        return BigDecimal.ZERO;
    }
}


