package com.subtrack;

import java.util.Scanner;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.subtrack.service.UserService;
import com.subtrack.service.PaymentService;
import com.subtrack.model.User;
import com.subtrack.model.Payment;

public class Main {

    static Scanner sc = new Scanner(System.in);

    static UserService userService = new UserService();
    static PaymentService paymentService = new PaymentService();

    public static void main(String[] args) {

        while (true) {

            System.out.println("\n===== WELCOME TO SUBTRACK =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    User loggedInUser = userService.login(sc);

                    if (loggedInUser != null) {
                        dashboard(loggedInUser);
                    }
                    break;

                case 2:
                    userService.signup(sc);
                    break;

                case 3:
                    System.out.println("Thank you for using SubTrack!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // USER DASHBOARD

    public static void dashboard(User loggedInUser) {

        while (true) {

            System.out.println("\n===== SUBTRACK DASHBOARD =====");
            System.out.println("Welcome, " + loggedInUser.getName());

            System.out.println("1. Subscription Management");
            System.out.println("2. Record Payment");
            System.out.println("3. View Payment History");
            System.out.println("4. View Total Payments");
            System.out.println("5. Logout");

            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {

                case 1:
                    System.out.println("Subscription Management");
                    // Connect Subscription Management here
                    break;

                case 2:
                    recordPayment(loggedInUser.getUserId());
                    break;

                case 3:
                    viewPaymentHistory(loggedInUser.getUserId());
                    break;

                case 4:
                    viewTotalPayments(loggedInUser.getUserId());
                    break;

                case 5:
                    System.out.println("Logging out...");
                    return;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    // RECORD PAYMENT

    public static void recordPayment(int userId) {

        System.out.println("\n===== RECORD PAYMENT =====");

        System.out.print("Enter Subscription ID: ");
        int subscriptionId = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Amount: ");
        BigDecimal amount = new BigDecimal(sc.nextLine());

        System.out.print("Enter Payment Date (YYYY-MM-DD): ");
        LocalDate paymentDate = LocalDate.parse(sc.nextLine());

        System.out.print("Enter Payment Method (UPI/Card/Cash): ");
        String method = sc.nextLine();

        System.out.print("Enter Payment Status (SUCCESS/FAILED/PENDING): ");
        String status = sc.nextLine();

        System.out.print("Enter Transaction Reference: ");
        String reference = sc.nextLine();

        Payment payment = new Payment(
                userId,
                subscriptionId,
                null,
                amount,
                paymentDate,
                method,
                status,
                reference
        );

        paymentService.recordPayment(payment);
    }

    // VIEW PAYMENT HISTORY

    public static void viewPaymentHistory(int userId) {

        System.out.println("\n===== PAYMENT HISTORY =====");

        List<Payment> payments =
                paymentService.getPaymentHistory(userId);

        if (payments.isEmpty()) {
            System.out.println("No payment history found.");
            return;
        }

        for (Payment p : payments) {

            System.out.println("----------------------------");

            System.out.println("Payment ID: " + p.getPaymentId());
            System.out.println("Subscription: " + p.getServiceName());
            System.out.println("Amount: Rs. " + p.getAmount());
            System.out.println("Date: " + p.getPaymentDate());
            System.out.println("Method: " + p.getPaymentMethod());
            System.out.println("Status: " + p.getPaymentStatus());

            System.out.println("Reference: "
                    + p.getTransactionReference());
        }

        System.out.println("----------------------------");
    }

    // VIEW TOTAL PAYMENTS

    public static void viewTotalPayments(int userId) {

        BigDecimal total =
                paymentService.getTotalPayments(userId);

        System.out.println("\n===== TOTAL PAYMENTS =====");

        System.out.println("Total Successfully Paid: Rs. " + total);
    }
}