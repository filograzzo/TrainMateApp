package DAO;

import DomainModel.PersonalTrainer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersonalTrainerDAO {
    private final Connection connection;

    public PersonalTrainerDAO(Connection connection) {
        this.connection = connection;
    }


    public boolean exists(String username, String password) throws SQLException {
        String query = "SELECT * FROM PersonalTrainer WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }
        }
    }

    public boolean create(String username, String password, String email) throws SQLException {
        String query = "INSERT INTO PersonalTrainer (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            stmt.setString(3, email);
            return stmt.executeUpdate() > 0;
        }
    }

    public PersonalTrainer getPersonalTrainer(String username, String password) throws SQLException {
        String query = "SELECT * FROM PersonalTrainer WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new PersonalTrainer(rs.getInt("id"), rs.getString("username"), rs.getString("password"), rs.getString("email"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean deletePersonalTrainer(String username, String password) throws SQLException {
        String query = "DELETE FROM PersonalTrainer WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePersonalTrainer(PersonalTrainer personalTrainer) throws SQLException {
        String query = "UPDATE PersonalTrainer SET username = ?, password = ?, email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, personalTrainer.getUsername());
            stmt.setString(2, personalTrainer.getPassword());
            stmt.setString(3, personalTrainer.getEmail());
            stmt.setInt(4, personalTrainer.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateUsername(int id, String newUsername) throws SQLException {
        String query = "UPDATE PersonalTrainer SET username = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUsername);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updatePassword(int id, String newPassword) throws SQLException {
        String query = "UPDATE PersonalTrainer SET password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmail(int id, String newEmail) throws SQLException {
        String query = "UPDATE PersonalTrainer SET email = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newEmail);
            stmt.setInt(2, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public List<PersonalTrainer> getAllPersonalTrainers() throws SQLException {
        String query = "SELECT * FROM PersonalTrainer";
        List<PersonalTrainer> personalTrainers = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                PersonalTrainer personalTrainer = new PersonalTrainer(
                        rs.getInt("id"),
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("email")
                );
                personalTrainers.add(personalTrainer);  // Aggiunge ogni personal trainer alla lista
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;  // Rilancia l'eccezione per gestirla a livello superiore
        }

        return personalTrainers;  // Restituisce la lista di personal trainer
    }

    //queste due funzioni verranno attivate quando sarà creato Agenda nel model

    /*public Agenda viewCourseAgenda(int id) throws SQLException {
        String query = "SELECT * FROM Course  WHERE Pt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Agenda(rs.getDate("data"), rs.getTime("startTime"), rs.getTime("endTime"), rs.getString("name"), rs.getInt("maxNumber"));
                } else {
                    return null;
                }
            }
        }
    }

    public Agenda viewAppointmentAgenda(int id) throws SQLException {
        String query = "SELECT * FROM Appointment  WHERE Pt = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Agenda(rs.getDate("data"), rs.getTime("startTime"), rs.getTime("endTime"), rs.getString("client")); //fixme client non sarà una stringa ma un id, quindi dovrai prendere il nome del client e inserirlo
                } else {
                    return null;
                }
            }
        }
    }*/
}