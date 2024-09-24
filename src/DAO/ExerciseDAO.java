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

    public Exercise getExerciseById(int id) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CategoryDAO categoryDAO = new CategoryDAO(connection);
                    MachineDAO machineDAO = new MachineDAO(connection);

                    Category category = categoryDAO.getCategory(rs.getString("category_name"));
                    Machine machine = machineDAO.getMachine(rs.getInt("id"));

                    return new Exercise(rs.getInt("id"), rs.getString("name"), category, machine);
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addExercise(Exercise exercise) throws SQLException {
        String query = "INSERT INTO Exercise (id, name, category_name, machine_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exercise.getId());
            stmt.setString(2, exercise.getName());
            stmt.setString(3, exercise.getCategory().getName());
            stmt.setInt(4, exercise.getMachine().getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeExercise(Exercise exercise) throws SQLException {
        String query = "DELETE FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exercise.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    //TODO: forse da togliere
    public Exercise getExerciseByName(String name) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    CategoryDAO categoryDAO = new CategoryDAO(connection);
                    MachineDAO machineDAO = new MachineDAO(connection);

                    Category category = categoryDAO.getCategory(rs.getString("category_name"));
                    Machine machine = machineDAO.getMachine(rs.getInt("id"));

                    return new Exercise(rs.getInt("id"), rs.getString("name"), category, machine);
                } else {
                    return null;
                }
            }
        }
    }
}
