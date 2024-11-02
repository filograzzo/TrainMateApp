package DAO;

import DomainModel.Exercise;
import DomainModel.Machine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                            rs.getInt("machine_id"),
                            rs.getString("description")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addExercise(String name, String category, int machine, String description) throws SQLException {
        String query = "INSERT INTO Exercise ( name, category_name, machine_id, description) VALUES ( ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setString(2, category);
            stmt.setInt(3, machine);
            stmt.setString(4, description);
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

    public int getExerciseIdByName(String name) throws SQLException {
        String query = "SELECT id FROM Exercise WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }return -1;
    }

    public Exercise getExerciseByName(String name) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category_name"),
                            rs.getInt("machine_id"),
                            rs.getString("description")
                    );
                } else {
                    return null;
                }
            }
        }
    }

    public List<Exercise> getAllExercises() throws SQLException {
        String query = "SELECT * FROM Exercise";
        List<Exercise> exercises = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            // Itera sui risultati del ResultSet
            while (rs.next()) {
                // Crea l'oggetto Exercise per ogni riga e aggiungilo alla lista
                Exercise exercise = new Exercise(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("category_name"),
                        rs.getInt("machine_id"),
                        rs.getString("description")
                );
                exercises.add(exercise);
            }
        }
        return exercises;  // Restituisce la lista degli esercizi
    }

    public List<Exercise> getExercisesByCategory(String category) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE category_name = ?";
        List<Exercise> exercises = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, category);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    // Crea l'oggetto Exercise per ogni riga e aggiungilo alla lista
                    Exercise exercise = new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category_name"),
                            rs.getInt("machine_id"),
                            rs.getString("description")
                    );
                    exercises.add(exercise);
                }
            }
        }
        return exercises;  // Restituisce la lista degli esercizi filtrati per categoria
    }

    public List<Exercise> getExercisesByMachine(Machine machine) throws SQLException {
        String query = "SELECT * FROM Exercise WHERE machine_id = ?";
        List<Exercise> exercises = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, machine.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Exercise exercise = new Exercise(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getString("category_name"),
                            rs.getInt("machine_id"),
                            rs.getString("description")
                    );
                    exercises.add(exercise);
                }
            }
        }
        return exercises;
    }

    public String getExerciseNameById(int id) throws SQLException {
        String query = "SELECT name FROM Exercise WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name"); // Restituisce il nome dell'esercizio
                } else {
                    return null; // Restituisce null se l'ID non esiste
                }
            }
        }
    }
}