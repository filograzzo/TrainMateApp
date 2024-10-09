package DAO;

import DomainModel.BaseUser;
import DomainModel.PersonalTrainer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalTrainerDAO {
    private final Connection connection;

    // Constructor to initialize the DAO with the DB connection
    public PersonalTrainerDAO(Connection connection) {
        this.connection = connection;
    }

    // Check if a username already exists in the User table
    public boolean usernameExists(String username) throws SQLException {
        String query = "SELECT 1 FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if a row is found
            }
        }
    }

    // Check if a user with specific username, password, and email exists
    public boolean exists(String username, String password, String email) throws SQLException {
        String query = "SELECT 1 FROM User WHERE username = ? AND password = ? AND email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Returns true if a matching user is found
            }
        }
    }

    // Create a new user in the User table
    public boolean create(String username, String password, String email) throws SQLException {
        String query = "INSERT INTO User (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;  // Returns true if insertion is successful
        }
    }

    // Get the ID of a user by their username
    public int getIdUserPT(String username) throws SQLException {
        String query = "SELECT id FROM User WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");  // Return the user ID if found
                }
                return -1;  // Return -1 if user not found
            }
        }
    }

    // Create a new PersonalTrainer entry in the PersonalTrainer table
    public boolean createPT(int id) throws SQLException {
        String query = "INSERT INTO PersonalTrainer (id) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;  // Returns true if insertion is successful
        }
    }

    public boolean isPersonalTrainer(BaseUser baseUser) {
        String query = "SELECT * FROM PersonalTrainer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, baseUser.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();  // Ritorna true se c'è almeno un risultato (id trovato)
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // In caso di errore, restituisce false
        }
    }


    // Get the username of a PersonalTrainer by their ID
    public String getNamePersonalTrainerById(int id) throws SQLException {
        String query = "SELECT username FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");  // Return the username if found
                }
                return null;  // Return null if not found
            }
        }
    }

    // Get a PersonalTrainer by their username, password, and email
    public PersonalTrainer getPersonalTrainer(String username, String password, String email) throws SQLException {
        String query = "SELECT * FROM User JOIN PersonalTrainer ON User.id = PersonalTrainer.id " +
                "WHERE username = ? AND password = ? AND email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PersonalTrainer(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                }
                return null;  // Return null if not found
            }
        }
    }
    public PersonalTrainer getPTbyId(int id)throws SQLException{
        String query = "SELECT * FROM User JOIN PersonalTrainer ON User.id = PersonalTrainer.id WHERE User.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PersonalTrainer(
                            rs.getInt("id"),
                            rs.getString("username"),
                            rs.getString("password"),
                            rs.getString("email")
                    );
                } else {
                    return null;
                }
            }
        }

    }

    // Delete a user from the User table
    public boolean deleteUserPT(String username, String password, String email) throws SQLException {
        String query = "DELETE FROM User WHERE username = ? AND password = ? AND email = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;  // Returns true if deletion is successful
        }
    }

    // Delete a PersonalTrainer from the PersonalTrainer table
    public boolean deletePT(int id) throws SQLException {
        String query = "DELETE FROM PersonalTrainer WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;  // Returns true if deletion is successful
        }
    }

    // Update a PersonalTrainer's information
    public boolean updatePersonalTrainer(PersonalTrainer personalTrainer) throws SQLException {
        String query = "UPDATE PersonalTrainer SET username = ?, password = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, personalTrainer.getUsername());
            stmt.setString(2, personalTrainer.getPassword());
            stmt.setString(3, personalTrainer.getEmail());
            stmt.setInt(4, personalTrainer.getId());
            return stmt.executeUpdate() > 0;  // Returns true if update is successful
        }
    }

    // Update a user's username
    public boolean updateUsername(String oldUsername, String newUsername) throws SQLException {
        String query = "UPDATE User SET username = ? WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setString(2, oldUsername);
            return stmt.executeUpdate() > 0;  // Returns true if update is successful
        }
    }

    // Update a user's password by ID
    public boolean updatePassword(int id, String newPassword, String oldPassword) throws SQLException {
        String query = "UPDATE User SET password = ? WHERE id = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            stmt.setString(3, oldPassword);
            return stmt.executeUpdate() > 0;  // Returns true if update is successful
        }
    }

    // Update a user's email by ID
    public boolean updateEmail(int id, String newEmail) throws SQLException {
        String query = "UPDATE User SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;  // Returns true if update is successful
        }
    }

    // Retrieve all personal trainers from the PersonalTrainer table
    public ArrayList<PersonalTrainer> getAllPersonalTrainers() throws SQLException {
        String query = "SELECT * FROM PersonalTrainer";
        ArrayList<PersonalTrainer> personalTrainers = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String username = getNamePersonalTrainerById(id);  // Get username from User table
                personalTrainers.add(new PersonalTrainer(id, username, null, null));  // Email/password not needed here
            }
        }
        return personalTrainers;
    }

    public String getNamePersonalTrainerbyId(int Id)throws SQLException{
        String query = "SELECT username FROM User WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, Id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("username");
                } else {
                    return null;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw e;
            }
        }
    }

    public boolean checkIfFree(int id, String day, Time time) throws SQLException {
        boolean result = false;
        boolean result2 = false;
        String query = "SELECT * FROM Appointment WHERE trainer_id = ? AND appointment_day = ? AND appointment_time = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, day);
            stmt.setTime(3, time);
            try (ResultSet rs = stmt.executeQuery()) {
                result = !rs.next();
            }
        }
        String query2 = "SELECT * FROM Course WHERE trainer_id = ? AND day = ? AND time = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query2)) {
            stmt.setInt(1, id);
            stmt.setString(2, day);
            stmt.setTime(3, time);
            try (ResultSet rs2 = stmt.executeQuery()) {
                result2 = !rs2.next();
            }
            return result && result2;
        }
    }
}