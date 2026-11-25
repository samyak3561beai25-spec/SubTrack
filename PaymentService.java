package com.subtrack.service;

import com.subtrack.dao.PaymentDAO;
import com.subtrack.model.Payment;

import java.math.BigDecimal;
import java.util.List;

public class PaymentService {

    private PaymentDAO paymentDAO = new PaymentDAO();

    // 1. Record Payment
    public boolean recordPayment(Payment payment) {

        if (payment.getAmount() == null ||
                payment.getAmount().compareTo(BigDecimal.ZERO) <= 0) {

            System.out.println("Amount must be greater than zero.");
            return false;
        }

        if (payment.getPaymentDate() == null) {
            System.out.println("Payment date is required.");
            return false;
        }

        if (payment.getPaymentMethod() == null ||
                payment.getPaymentMethod().isBlank()) {

            System.out.println("Payment method is required.");
            return false;
        }

        String status = payment.getPaymentStatus();

        if (status == null ||
                !(status.equalsIgnoreCase("SUCCESS") ||
                  status.equalsIgnoreCase("FAILED") ||
                  status.equalsIgnoreCase("PENDING"))) {

            System.out.println("Invalid payment status.");
            return false;
        }

        payment.setPaymentStatus(status.toUpperCase());

        boolean result = paymentDAO.addPayment(payment);

        if (result) {
            System.out.println("Payment recorded successfully!");
        } else {
            System.out.println("Payment could not be recorded.");
        }

        return result;
    }

    // 2. View Payment History
    public List<Payment> getPaymentHistory(int userId) {
        return paymentDAO.getPaymentHistory(userId);
    }

    // 3. Get Total Payments
    public BigDecimal getTotalPayments(int userId) {
        return paymentDAO.getTotalPayments(userId);
    }
}