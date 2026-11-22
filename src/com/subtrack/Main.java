
package com.subtrack;

import java.util.Scanner;
import com.subtrack.service.UserService;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        UserService userService = new UserService();

        while (true) {

            System.out.println("\n===== WELCOME TO SUBTRACK =====");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    userService.login(sc);
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
}
