package com.subtrack.dao;

import com.subtrack.database.DBConnection;
import com.subtrack.model.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaymentDAO {

    // 1. Add Payment
    public boolean addPayment(Payment payment) {

        String sql = "INSERT INTO payments " +
                "(user_id, subscription_id, amount, payment_date, " +
                "payment_method, payment_status, transaction_reference) " +
                "SELECT ?, s.subscription_id, ?, ?, ?, ?, ? " +
                "FROM subscriptions s " +
                "WHERE s.subscription_id = ? AND s.user_id = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, payment.getUserId());
            ps.setBigDecimal(2, payment.getAmount());
            ps.setDate(3, Date.valueOf(payment.getPaymentDate()));
            ps.setString(4, payment.getPaymentMethod());
            ps.setString(5, payment.getPaymentStatus());
            ps.setString(6, payment.getTransactionReference());
            ps.setInt(7, payment.getSubscriptionId());
            ps.setInt(8, payment.getUserId());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error adding payment: " + e.getMessage());
            return false;
        }
    }

    // 2. View Payment History
    public List<Payment> getPaymentHistory(int userId) {

        List<Payment> payments = new ArrayList<>();

        String sql = "SELECT p.*, s.service_name " +
                "FROM payments p " +
                "JOIN subscriptions s " +
                "ON p.subscription_id = s.subscription_id " +
                "WHERE p.user_id = ? " +
                "ORDER BY p.payment_date DESC, p.payment_id DESC";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Payment payment = new Payment(
                    rs.getInt("user_id"),
                    rs.getInt("subscription_id"),
                    rs.getString("service_name"),
                    rs.getBigDecimal("amount"),
                    rs.getDate("payment_date").toLocalDate(),
                    rs.getString("payment_method"),
                    rs.getString("payment_status"),
                    rs.getString("transaction_reference")
                );

                payment.setPaymentId(rs.getInt("payment_id"));

                payments.add(payment);
            }

        } catch (SQLException e) {
            System.out.println("Error fetching payment history: "
                    + e.getMessage());
        }

        return payments;
    }

    // 3. Calculate Total Successful Payments
    public java.math.BigDecimal getTotalPayments(int userId) {

        String sql = "SELECT COALESCE(SUM(amount), 0) AS total " +
                "FROM payments " +
                "WHERE user_id = ? AND payment_status = 'SUCCESS'";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, userId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBigDecimal("total");
            }

        } catch (SQLException e) {
            System.out.println("Error calculating total: " + e.getMessage());
        }

        return java.math.BigDecimal.ZERO;
    }
}