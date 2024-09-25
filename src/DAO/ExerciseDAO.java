package DAO;

import DomainModel.Exercise;
import DomainModel.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseDAO {
    private final Connection connection;

    public ExerciseDAO(Connection connection) {
        this.connection = connection;
    }

    public Exercise getExerciseById(int id) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    MachineDAO machineDAO = new MachineDAO(connection);
                    return new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category_name"),
                            rs.getString("machine_name")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addExercise(String name, String category, String machine) throws SQLException {
        String query = "INSERT INTO Exercise ( name, category_name, machine_name) VALUES ( ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setString(3, machine);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeExerciseById(int id) throws SQLException {
        String query = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeExerciseByName(String name) throws SQLException {
        String query = "DELETE FROM Exercise WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            return stmt.executeUpdate() > 0;
        }
    }
}
