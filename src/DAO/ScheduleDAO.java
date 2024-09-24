package DAO;

import DomainModel.Schedule;
import DomainModel.Training;
import DomainModel.ExerciseDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    public Schedule getScheduleById(int scheduleId) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Schedule schedule = new Schedule(rs.getString("name"));
                    return schedule;
                } else {
                    return null;
                }
            }
        }
    }


    public boolean addSchedule(Schedule schedule) throws SQLException {
        String query = "INSERT INTO Schedule (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, schedule.getId());
            stmt.setString(2, schedule.getName());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeSchedule(int scheduleId) throws SQLException {
        String query = "DELETE FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            return stmt.executeUpdate() > 0;
        }
    }


}
