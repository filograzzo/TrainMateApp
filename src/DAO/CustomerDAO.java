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
    public boolean usernameExists(String username)throws SQLException {//checkCredentials
        String query = "SELECT * FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }
    public String getNameCustomerbyId(int id)throws SQLException{
        String query = "SELECT username FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public boolean emailExists(String email)throws SQLException {//checkCredentials
        String query = "SELECT * FROM User WHERE email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean userExists(String username, String password, String email) throws SQLException {//checkCredentials
        String query = "SELECT * FROM User WHERE username = ? AND password = ? AND email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean insertUser(String username, String password, String email) throws SQLException {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean insertCustomer(int id)throws SQLException{
        String query = "INSERT INTO Customer (id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public int getIdUserCustomer(String username)throws SQLException{
        String query = "SELECT id FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1;
                }
            }
        }
    }
    public Customer getCustomer(String username, String password, String email) throws SQLException {
        // Explicitly select the columns to avoid ambiguity
        String query = "SELECT * " +
                "FROM User " +
                "JOIN Customer ON User.id = Customer.id " +
                "WHERE User.username = ? AND User.password = ? AND User.email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fetchedId = rs.getInt("id");
                    Customer c = new Customer(
                            fetchedId,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                    c.setHeight(rs.getFloat("height"));
                    c.setWeight(rs.getFloat("weight"));
                    c.setAge(rs.getInt("age"));
                    c.setGender(rs.getString("gender"));
                    c.setGoal(rs.getString("goal"));
                    return c;
                } else {
                    return null;
                }
            }
        }
    }


    public boolean deleteUserCustomer(String username, String password, String email) throws SQLException {
        String query = "DELETE FROM User WHERE username = ? AND password = ? AND email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deleteCustomer(int id) throws SQLException {
        String query = "DELETE FROM Customer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
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

    public boolean updateUsername(String oldUSername, String newUsername) throws SQLException {
        String query = "UPDATE User SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setString(2,oldUSername );
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(String username, String newPassword,String oldPassword) throws SQLException {
        String query = "UPDATE User SET password = ? WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setString(2, username);
            stmt.setString(3, oldPassword);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmail(String username, String newEmail) throws SQLException {
        String query = "UPDATE User SET email = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setString(2, username);
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                return true;
            } else {
                System.out.println("No rows updated. Username might not exist: " + username);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getPersonalData(int clientId) throws SQLException {
        String query = "SELECT * FROM Customer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Height: " + rs.getFloat("height"));
                    System.out.println("Weight: " + rs.getFloat("weight"));
                    System.out.println("Age: " + rs.getInt("age"));
                    System.out.println("Gender:" + rs.getString("gender"));
                    System.out.println("Goal:" + rs.getString("goal"));
                    return true;
                }
                else{
                    return false;
                }

            }
        }
    }

    public boolean insertData(int id, float height, float weight, int age, String gender, String goal) throws SQLException {
        String query = "INSERT INTO Customer (height, weight, age,gender,goal) VALUES ( ?, ?, ?, ?, ?) WHERE id = ? ";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, height);
            stmt.setFloat(2, weight);
            stmt.setFloat(3, age);
            stmt.setString(4, gender);
            stmt.setString(5,goal);
            stmt.setInt(6,id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePersonalData(int id, float height,float weight,int age,String gender,String goal) throws SQLException {
        String query = "UPDATE Customer SET height = ?, weight = ?, age = ?, gender = ?, goal = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setFloat(1, height);
            stmt.setFloat(2, weight);
            stmt.setInt(3, age);
            stmt.setString(4, gender);
            stmt.setString(5, goal);
            stmt.setInt(6, id);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deletePersonalData(int clientId) throws SQLException {
        String query = "UPDATE Customer SET height = null, weight = null, age = null, gender = null, goal = null WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, clientId);
            return stmt.executeUpdate() > 0;
        }
    }
    public Customer getCustomerByUsername(String username) throws SQLException {
        String query = "SELECT * " +
                "FROM User " +
                "JOIN Customer ON User.id = Customer.id " +
                "WHERE User.username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fetchedId = rs.getInt("id");
                    Customer customer = new Customer(
                            fetchedId,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                    customer.setHeight(rs.getFloat("height"));
                    customer.setWeight(rs.getFloat("weight"));
                    customer.setAge(rs.getInt("age"));
                    customer.setGender(rs.getString("gender"));
                    customer.setGoal(rs.getString("goal"));
                    return customer;
                } else {
                    return null; // Return null if no customer is found with the given username
                }
            }
        }
    }

    public Customer getCustomerByUsername(String username) throws SQLException {
        String query = "SELECT * " +
                "FROM User " +
                "JOIN Customer ON User.id = Customer.id " +
                "WHERE User.username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int fetchedId = rs.getInt("id");
                    Customer customer = new Customer(
                            fetchedId,
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                    customer.setHeight(rs.getFloat("height"));
                    customer.setWeight(rs.getFloat("weight"));
                    customer.setAge(rs.getInt("age"));
                    customer.setGender(rs.getString("gender"));
                    customer.setGoal(rs.getString("goal"));
                    return customer;
                } else {
                    return null; // Return null if no customer is found with the given username
                }
            }
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
