package DAO;
/**
 * DAO class for ExerciseDetail:Il personal trainer aggiunge,rimuove,modifica gli esercizi da assegnare ai clienti:get,add,remove
 */
import DomainModel.ExerciseDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ExerciseDetailDAO {
    private final Connection connection;

    public ExerciseDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public ExerciseDetail getExerciseDetail(int id) throws SQLException {
        String query = "SELECT * FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new ExerciseDetail(rs.getInt("id"), rs.getString("name"), rs.getString("description"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addExerciseDetail(int id, String name, String description) throws SQLException {
        String query = "INSERT INTO ExerciseDetail (id, name, description) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setString(2, name);
            stmt.setString(3, description);
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
