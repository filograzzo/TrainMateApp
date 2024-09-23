package DAO;

import DomainModel.ExerciseDetail;
import DomainModel.Exercise;
import DomainModel.Schedule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseDetailDAO {
    private final Connection connection;

    public ExerciseDetailDAO(Connection connection) {
        this.connection = connection;
    }

    // Ottiene un ExerciseDetail basato sul nome dell'esercizio
    public ExerciseDetail getExerciseDetail(String name) throws SQLException {
        String query = "SELECT ed.id, ed.serie, ed.reps, ed.weight, e.id as exerciseId, e.name, e.description, s.id as scheduleId, s.scheduleName " +
                "FROM ExerciseDetail ed " +
                "JOIN Exercise e ON ed.exerciseId = e.id " +
                "JOIN Schedule s ON ed.scheduleId = s.id " +
                "WHERE e.name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Exercise exercise = new Exercise(rs.getInt("exerciseId"), rs.getString("name"), rs.getString("description"));
                    Schedule schedule = new Schedule(rs.getInt("scheduleId"), rs.getString("scheduleName"));
                    return new ExerciseDetail(rs.getInt("id"), rs.getInt("serie"), rs.getInt("reps"), rs.getInt("weight"), schedule, exercise);
                } else {
                    return null;
                }
            }
        }
    }

    // Aggiunge un nuovo ExerciseDetail
    public boolean addExerciseDetail(int serie, int reps, int weight, Schedule schedule, Exercise exercise) throws SQLException {
        String query = "INSERT INTO ExerciseDetail (serie, reps, weight, scheduleId, exerciseId) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, serie);
            stmt.setInt(2, reps);
            stmt.setInt(3, weight);
            stmt.setInt(4, schedule.getId());
            stmt.setInt(5, exercise.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Rimuove un ExerciseDetail basato sull'id
    public boolean removeExerciseDetail(int id) throws SQLException {
        String query = "DELETE FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }
}
