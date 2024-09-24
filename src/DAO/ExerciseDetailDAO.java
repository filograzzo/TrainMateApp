package DAO;

import DomainModel.ExerciseDetail;
import DomainModel.Exercise;
import DomainModel.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ExerciseDetailDAO {
    private final Connection connection;

    public ExerciseDetailDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean addExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        String query = "INSERT INTO ExerciseDetail (serie, reps, weight, schedule_id, exercise_id) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseDetail.getSerie());
            stmt.setInt(2, exerciseDetail.getReps());
            stmt.setInt(3, exerciseDetail.getWeight());
            stmt.setInt(4, exerciseDetail.getSchedule().getId());
            stmt.setInt(5, exerciseDetail.getExercise().getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean updateExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        String query = "UPDATE ExerciseDetail SET serie = ?, reps = ?, weight = ?, schedule_id = ?, exercise_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseDetail.getSerie());
            stmt.setInt(2, exerciseDetail.getReps());
            stmt.setInt(3, exerciseDetail.getWeight());
            stmt.setInt(4, exerciseDetail.getSchedule().getId());
            stmt.setInt(5, exerciseDetail.getExercise().getId());
            stmt.setInt(6, exerciseDetail.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean deleteExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        String query = "DELETE FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseDetail.getId());
            return stmt.executeUpdate() > 0;
        }
    }

    public ExerciseDetail getExerciseDetail(ExerciseDetail exerciseDetail) throws SQLException {
        String query = "SELECT * FROM ExerciseDetail WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, exerciseDetail.getId());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToExerciseDetail(rs);//TODO: da cambiare, cosi mette solo l'id nell'esercise e nello schedule
                }
            }
        }
        return null;
    }

    // Metodo per ottenere tutti gli ExerciseDetail
    public List<ExerciseDetail> getAllExerciseDetails() throws SQLException {
        List<ExerciseDetail> exerciseDetails = new ArrayList<>();
        String query = "SELECT * FROM ExerciseDetail";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                exerciseDetails.add(mapResultSetToExerciseDetail(rs));
            }
        }
        return exerciseDetails;
    }

    // Metodo di mapping per convertire il ResultSet in un oggetto ExerciseDetail
    private ExerciseDetail mapResultSetToExerciseDetail(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int serie = rs.getInt("serie");
        int reps = rs.getInt("reps");
        int weight = rs.getInt("weight");

        // Utilizza ScheduleDAO per ottenere l'oggetto Schedule
        ScheduleDAO scheduleDAO = new ScheduleDAO(connection);
        Schedule schedule = scheduleDAO.getSchedule(rs.getInt("schedule_id"));

        // Utilizza ExerciseDAO per ottenere l'oggetto Exercise
        ExerciseDAO exerciseDAO = new ExerciseDAO(connection);
        Exercise exercise = exerciseDAO.getExercise(rs.getInt("exercise_id"));

        return new ExerciseDetail(id, serie, reps, weight, schedule, exercise);
    }
}
