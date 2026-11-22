
package com.subtrack.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.subtrack.database.DBConnection;
import com.subtrack.model.User;

public class UserDAO {

    // Register user
    public boolean signup(User user) {

        String checkQuery = "SELECT email FROM users WHERE email = ?";
        String insertQuery =
                "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return false;
            }

            // Check whether email already exists
            try (PreparedStatement check =
                    con.prepareStatement(checkQuery)) {

                check.setString(1, user.getEmail());

                ResultSet rs = check.executeQuery();

                if (rs.next()) {
                    System.out.println("Email already registered!");
                    return false;
                }
            }

            // Insert user into database
            try (PreparedStatement ps =
                    con.prepareStatement(insertQuery)) {

                ps.setString(1, user.getName());
                ps.setString(2, user.getEmail());
                ps.setString(3, user.getPassword());

                ps.executeUpdate();

                System.out.println("Registration successful!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Login user
    public User login(String email, String password) {

        String query =
                "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection con = DBConnection.getConnection()) {

            if (con == null) {
                return null;
            }

            try (PreparedStatement ps = con.prepareStatement(query)) {

                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {

                    return new User(
                            rs.getString("name"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
}
