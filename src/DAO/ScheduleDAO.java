package DAO;

import DomainModel.Schedule;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
                    Schedule schedule = new Schedule(rs. getInt("id"), rs.getString("name"), rs.getString("customer"));
                    //TODO: nel service dovrà essere aggiunta la lista di exerciseDetail che appaiono in questa scheda cercando nel database
                    //TODO: database di ExerciseDetail per scheduleId usando la foreign key (schedule_id).
                    return schedule;
                } else {
                    return null;
                }
            }
        }
    }

    public Schedule getScheduleByUsername(String name) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Schedule schedule = new Schedule(rs. getInt("id"), rs.getString("name"), rs.getString("customer"));
                    //TODO: nel service dovrà essere aggiunta la lista di exerciseDetail che appaiono in questa scheda cercando nel database
                    //TODO: database di ExerciseDetail per scheduleId usando la foreign key (schedule_id).
                    return schedule;
                } else {
                    return null;
                }
            }
        }
    }


    public boolean addSchedule(String name) throws SQLException {
        String query = "INSERT INTO Schedule (name) VALUES (?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeScheduleById(int scheduleId) throws SQLException {
        String query = "DELETE FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeScheduleByName(String name) throws SQLException {
        String query = "DELETE FROM Schedule WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean updateSchedule(Schedule schedule) throws SQLException {
        String query = "UPDATE Schedule SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, schedule.getName());
            stmt.setInt(2, schedule.getId());
            return stmt.executeUpdate() > 0;
        }
    }



}
