
package com.subtrack.service;

import java.util.Scanner;

import com.subtrack.model.User;
import com.subtrack.dao.UserDAO;

public class UserService {

    UserDAO userDAO = new UserDAO();

    public void signup(Scanner sc) {

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        User user = new User(name, email, password);

        userDAO.signup(user);
    }

    public void login(Scanner sc) {

        System.out.print("Enter your email: ");
        String email = sc.nextLine();

        System.out.print("Enter your password: ");
        String password = sc.nextLine();

        User user = userDAO.login(email, password);

        if (user != null) {

            System.out.println("Login successful!");
            System.out.println("Welcome " + user.getName());

        } else {

            System.out.println("Invalid email or password!");
        }
    }
}
