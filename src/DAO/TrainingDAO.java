package DAO;

import DomainModel.Training;

import java.sql.*;

/**
 * DAO class for Training:Il personal trainer aggiunge,rimuove,modifica i piani di allenamento dei clienti:add,remove e update training
 * il cliente deve poter accedere al suo piano di allenamento:get training
 */
public class TrainingDAO {
    private final Connection connection;

    public TrainingDAO(Connection connection) {
        this.connection = connection;
    }

    public Training getTraining(Date date) throws SQLException {
        String query = "SELECT * FROM Training WHERE date = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, date);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Training(rs.getInt("id"), rs.getDate("date"), rs.getTimestamp("startTime"), rs.getTimestamp("endTime"), rs.getString("note"), rs.getInt("scheduleId"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addTraining(Date date, Timestamp startTime, Timestamp endTime, String note, int scheduleId) throws SQLException {
        String query = "INSERT INTO Training (date, startTime, endTime, note, scheduleId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, date);
            stmt.setTimestamp(2, startTime);
            stmt.setTimestamp(3, endTime);
            stmt.setString(4, note);
            stmt.setInt(5, scheduleId);
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

    public boolean updateTraining(int id, Date newDate, Timestamp newStartTime, Timestamp newEndTime, String newNote) throws SQLException {
        String query = "UPDATE Training SET date = ?, startTine = ?, endTime = ?, note = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDate(1, newDate);
            stmt.setTimestamp(2, newStartTime);
            stmt.setTimestamp(3, newEndTime);
            stmt.setString(4, newNote);
            stmt.setInt(5, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
