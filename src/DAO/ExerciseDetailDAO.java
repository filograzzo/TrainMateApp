package DAO;

import DomainModel.ExerciseDetail;

import java.sql.*;
public class ExerciseDetailDAO {
    private final Connection connection;

    public ExerciseDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addExerciseDetail(int serie, int reps, int weight, int schedule_id, int exercise_id) throws SQLException {
        String query = "INSERT INTO ExerciseDetail (serie, reps, weight, schedule_id, exercise_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, serie);
            stmt.setInt(2, reps);
            stmt.setInt(3, weight);
            stmt.setInt(4, schedule_id);
            stmt.setInt(5, exercise_id);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        String query = "UPDATE ExerciseDetail SET serie = ?, reps = ?, weight = ?, schedule_id = ?, exercise_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseDetail.getSerie());
            stmt.setInt(2, exerciseDetail.getReps());
            stmt.setInt(3, exerciseDetail.getWeight());
            stmt.setInt(4, exerciseDetail.getSchedule());
            stmt.setInt(5, exerciseDetail.getExercise());
            stmt.setInt(6, exerciseDetail.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteExerciseDetailById(int id) throws SQLException {
        String query = "DELETE FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }


}
