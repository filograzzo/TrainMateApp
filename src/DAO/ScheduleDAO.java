package DAO;
/**
 * DAO class for Schedule:Il personal trainer aggiunge,rimuove,modifica dati riguardo la sua agenda:get,add,remove
 * se deve tenre certi corsi, se deve fare certi allenamenti, se deve fare certi incontri
 */
import DomainModel.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    public Schedule getSchedule(int id) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Schedule(rs.getInt("id"), rs.getString("activity"), rs.getTimestamp("start_time"), rs.getTimestamp("end_time"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addSchedule(int id, String activity, Timestamp startTime, Timestamp endTime) throws SQLException {
        String query = "INSERT INTO Schedule (id, activity, start_time, end_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, activity);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeSchedule(int id) throws SQLException {
        String query = "DELETE FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
