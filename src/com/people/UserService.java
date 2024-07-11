package com.people;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserService {
    private final Connection connection;

    public UserService(Connection connection) {
        this.connection = connection;
    }

    public boolean registerUser(String username, String password, String email) {
        String query = "SELECT * FROM User WHERE username = ? AND password = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.err.println("The user you are trying to create already exists.");
                    return false;
                } else {
                    int newId = UserIdCounter.getNextId();
                    String query1 = "INSERT INTO User (id, username, password, email) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmt1 = connection.prepareStatement(query1)) {
                        stmt1.setInt(1, newId);
                        stmt1.setString(2, username);
                        stmt1.setString(3, password);
                        stmt1.setString(4, email);
                        int rows = stmt1.executeUpdate();
                        if (rows > 0) {
                            System.out.println("The user has been registered successfully.");
                            return true;
                        } else {
                            return false;
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public User loginUser(String username, String password) {
        String query = "SELECT * FROM User WHERE username = ? AND password = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new User(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                } else {
                    return null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeUser(String username, String password) {
        String query = "DELETE FROM User WHERE username = ? AND password = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
                System.out.println("The user has been deleted successfully.");
                return true;
            } else {
                System.err.println("No user was removed. Incorrect username or password.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserUsername(int id, String newUsername) {
        String query = "UPDATE User SET username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserPassword(int id, String newPassword) {
        String query = "UPDATE User SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserEmail(int id, String newEmail) {
        String query = "UPDATE User SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

class UserIdCounter {
    private static int counter = 1;

    public static synchronized int getNextId() {
        return counter++;
    }
}
