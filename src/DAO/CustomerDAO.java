package DAO;

import DomainModel.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {
    private final Connection connection;

    public CustomerDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean userExists(String username, String password) throws SQLException {//checkCredentials
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean insertCustomer(String username, String password, String email) throws SQLException {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        }
    }

    public Customer getCustomer(String username, String password) throws SQLException {
        String query = "SELECT * FROM User WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean deleteCustomer(String username, String password) throws SQLException {
        String query = "DELETE FROM User WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateCustomer(Customer customer) throws SQLException {
        String query = "UPDATE User SET username = ?, password = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customer.getUsername());
            stmt.setString(2, customer.getPassword());
            stmt.setString(3, customer.getEmail());
            stmt.setInt(4, customer.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUsername(int id, String username) throws SQLException {
        String query = "UPDATE User SET username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(int id, String newPassword) throws SQLException {
        String query = "UPDATE User SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmail(int id, String newEmail) throws SQLException {
        String query = "UPDATE User SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean insertData(int id, int newHeight, int newWeight, String newGoal) throws SQLException {
        String query = "INSERT INTO Customer (height, weight, goal) VALUES (?, ?, ?) WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(newHeight));
            stmt.setString(2, String.valueOf(newWeight));
            stmt.setString(3, newGoal);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean modifyData(int id, int newHeight, int newWeight, String newGoal) throws SQLException {
        String query = "UPDATE Customer SET height = ?, weight = ?, goal = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, String.valueOf(newHeight));
            stmt.setString(2, String.valueOf(newWeight));
            stmt.setString(3, newGoal);
            stmt.setInt(4, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<Customer> getAllCustomers() throws SQLException {
        String query = "SELECT * FROM User";
        List<Customer> customers = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Customer customer = new Customer(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return customers;
    }

}
