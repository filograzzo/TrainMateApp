package DAO;

import DomainModel.Schedule;
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
                    Schedule schedule = new Schedule(rs. getInt("id"), rs.getString("name"), rs.getString("customer"));
                    return schedule;
                } else {
                    return null;
                }
            }
        }
    }

    public Schedule getScheduleByName(String name) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Schedule schedule = new Schedule(rs. getInt("id"), rs.getString("name"), rs.getString("customer"));
                    return schedule;
                } else {
                    return null;
                }
            }
        }
    }

    public List<Schedule> getSchedulesByUsername(String username) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE customer = ?";
        List<Schedule> schedules = new ArrayList<>(); // Lista per contenere i risultati

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Schedule schedule = new Schedule(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("customer")
                    );
                    schedules.add(schedule); // Aggiungi alla lista
                }
            }
        }

        return schedules;
    }


    public boolean addSchedule(String name, String customerUser) throws SQLException {
        String query = "INSERT INTO Schedule (name, customer) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, customerUser);
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

    public List<Schedule> getAllSchedules() throws SQLException {
        String query = "SELECT * FROM Schedule";
        List<Schedule> schedules = new ArrayList<>(); // Lista per contenere i risultati

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Schedule schedule = new Schedule(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("customer")
                );
                schedules.add(schedule); // Aggiungi alla lista
            }
        }

        return schedules; // Restituisce la lista di schedule
    }



}