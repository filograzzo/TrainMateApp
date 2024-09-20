package DAO;
/**
 * DAO class for ExerciseDetail:Il personal trainer aggiunge,rimuove,modifica gli esercizi da assegnare ai clienti:get,add,remove
 */
import DomainModel.Exercise;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ExerciseDetailDAO {
    private final Connection connection;

    public ExerciseDetailDAO(Connection connection) {
        this.connection = connection;
    }
    public Exercise getExerciseDetail(String name) throws SQLException {
        String query = "SELECT * FROM ExerciseDetail WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Exercise(rs.getInt("id"), rs.getInt("serie"), rs.getInt("reps"), rs.getInt("weight"), rs.getInt("swcheduleId"), rs.getString("name"), rs.getString("description"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addExerciseDetail(int serie, int reps, int weight, int scheduleId, String name, String description) throws SQLException {
        String query = "INSERT INTO ExerciseDetail (serie, reps, weight, scheduleId, name, description) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, serie);
            stmt.setInt(2, reps);
            stmt.setInt(3, weight);
            stmt.setInt(4, scheduleId);
            stmt.setString(5, name);
            stmt.setString(6, description);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeExerciseDetail(int id) throws SQLException {
        String query = "DELETE FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
