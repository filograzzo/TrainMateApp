package DAO;

import DomainModel.Training;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TrainingDAO {
    private final Connection connection;

    public TrainingDAO(Connection connection) {
        this.connection = connection;
    }

    public Training getTrainingById(int trainingId) throws SQLException {
        String query = "SELECT * FROM Training WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainingId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Training(
                            rs.getInt("id"),
                            rs.getDate("date"),
                            rs.getTimestamp("start_time"),
                            rs.getTimestamp("end_time"),
                            rs.getString("note"),
                            rs.getInt("schedule_id"),
                            rs.getString("username")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addTraining(Date date, Timestamp startTime, Timestamp endTime, String note, int scheduleId, String username) throws SQLException {
        String query = "INSERT INTO Training (date, start_time, end_time, note, schedule_id, username) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, date);
            stmt.setTimestamp(2, startTime);
            stmt.setTimestamp(3, endTime);
            stmt.setString(4, note);
            stmt.setInt(5, scheduleId);
            stmt.setString(6, username);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeTraining(int trainingId) throws SQLException {
        String query = "DELETE FROM Training WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainingId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Metodo per ottenere tutti i Training di un determinato utente (username)
    public List<Training> getAllTrainingsByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Training WHERE username = ?";
        List<Training> trainings = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Training training = new Training(
                            rs.getInt("id"),
                            rs.getDate("date"),
                            rs.getTimestamp("start_time"),
                            rs.getTimestamp("end_time"),
                            rs.getString("note"),
                            rs.getInt("schedule_id"),
                            rs.getString("username")
                    );
                    trainings.add(training);
                }
            }
        }
        return trainings;
    }

    // Metodo per ottenere tutti i Training di un determinato schedule
    public List<Training> getAllTrainingsBySchedule(int scheduleId) throws SQLException {
        String query = "SELECT * FROM Training WHERE schedule_id = ?";
        List<Training> trainings = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Training training = new Training(
                            rs.getInt("id"),
                            rs.getDate("date"),
                            rs.getTimestamp("start_time"),
                            rs.getTimestamp("end_time"),
                            rs.getString("note"),
                            rs.getInt("schedule_id"),
                            rs.getString("username")
                    );
                    trainings.add(training);
                }
            }
        }
        return trainings;
    }
}
