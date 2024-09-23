package DAO;

import DomainModel.Exercise;
import DomainModel.Category;
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

    // Metodo per ottenere un Exercise tramite id
    public Exercise getExerciseById(int id) throws SQLException {
        String query = "SELECT e.id, e.name, c.id as categoryId, c.name as categoryName, m.id as machineId, m.name as machineName " +
                "FROM Exercise e " +
                "JOIN Category c ON e.categoryId = c.id " +
                "JOIN Machine m ON e.machineId = m.id " +
                "WHERE e.id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Category category = new Category(rs.getInt("categoryId"), rs.getString("categoryName"));
                    Machine machine = new Machine(rs.getInt("machineId"), rs.getString("machineName"));
                    return new Exercise(rs.getInt("id"), rs.getString("name"), category, machine);
                } else {
                    return null;
                }
            }
        }
    }

    // Metodo per aggiungere un nuovo Exercise
    public boolean addExercise(Exercise exercise) throws SQLException {
        String query = "INSERT INTO Exercise (name, categoryId, machineId) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, exercise.getName());
            stmt.setInt(2, exercise.getCategory().getId());
            stmt.setInt(3, exercise.getMachine().getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Metodo per aggiornare un Exercise esistente
    public boolean updateExercise(Exercise exercise) throws SQLException {
        String query = "UPDATE Exercise SET name = ?, categoryId = ?, machineId = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, exercise.getName());
            stmt.setInt(2, exercise.getCategory().getId());
            stmt.setInt(3, exercise.getMachine().getId());
            stmt.setInt(4, exercise.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Metodo per eliminare un Exercise tramite id
    public boolean deleteExercise(int id) throws SQLException {
        String query = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
