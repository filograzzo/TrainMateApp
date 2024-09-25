package DAO;

import DomainModel.Training;

import java.sql.*;

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


    public boolean addTraining(Date date, Timestamp startTime, Timestamp endTime, String note, int scheduleId) throws SQLException {
        String query = "INSERT INTO Training (date, start_time, end_time, note, schedule_id) VALUES (?, ?, ?, ?, ?)";
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


}
