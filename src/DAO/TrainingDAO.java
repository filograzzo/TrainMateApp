package DAO;

import DomainModel.Training;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    ScheduleDAO scheduleDAO = new ScheduleDAO(connection);

                    return new Training(
                            rs.getInt("id"),
                            rs.getDate("date"),
                            rs.getTimestamp("start_time"),
                            rs.getTimestamp("end_time"),
                            rs.getString("note"),
                            rs.getInt("schedule_id")
                    );
                } else {
                    return null;
                }
            }
        }
    }


    public boolean addTraining(Training training) throws SQLException {
        String query = "INSERT INTO Training (id, date, start_time, end_time, note, schedule_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, training.getId());
            stmt.setDate(2, training.getDate());
            stmt.setTimestamp(3, training.getStartTime());
            stmt.setTimestamp(4, training.getEndTime());
            stmt.setString(5, training.getNote());
            stmt.setInt(6, training.getSchedule());

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


}
