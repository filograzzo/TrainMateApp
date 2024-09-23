package DAO;

import DomainModel.Schedule;
import DomainModel.Training;
import DomainModel.ExerciseDetail;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScheduleDAO {
    private final Connection connection;

    public ScheduleDAO(Connection connection) {
        this.connection = connection;
    }

    // Metodo per aggiungere un nuovo Schedule nel database
    public boolean addSchedule(Schedule schedule) throws SQLException {
        String query = "INSERT INTO Schedule (id, name) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, schedule.getId());
            stmt.setString(2, schedule.getName());

            if (stmt.executeUpdate() > 0) {
                // Aggiungi i Training associati allo Schedule
                for (Training training : schedule.getTrainings()) {
                    addScheduleTraining(schedule.getId(), training.getId());
                }

                // Aggiungi gli ExerciseDetail associati allo Schedule
                for (ExerciseDetail exercise : schedule.getExercises()) {
                    addScheduleExercise(schedule.getId(), exercise.getId());
                }
                return true;
            }
            return false;
        }
    }

    // Metodo per aggiornare uno Schedule esistente
    public boolean updateSchedule(Schedule schedule) throws SQLException {
        String query = "UPDATE Schedule SET name = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, schedule.getName());
            stmt.setInt(2, schedule.getId());

            if (stmt.executeUpdate() > 0) {
                // Aggiorna la relazione con i Training
                removeAllScheduleTrainings(schedule.getId());
                for (Training training : schedule.getTrainings()) {
                    addScheduleTraining(schedule.getId(), training.getId());
                }

                // Aggiorna la relazione con gli ExerciseDetail
                removeAllScheduleExercises(schedule.getId());
                for (ExerciseDetail exercise : schedule.getExercises()) {
                    addScheduleExercise(schedule.getId(), exercise.getId());
                }
                return true;
            }
            return false;
        }
    }

    // Metodo per rimuovere uno Schedule dal database
    public boolean removeSchedule(int scheduleId) throws SQLException {
        String query = "DELETE FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            removeAllScheduleTrainings(scheduleId);
            removeAllScheduleExercises(scheduleId);
            return stmt.executeUpdate() > 0;
        }
    }

    // Metodo per recuperare uno Schedule dal database
    public Schedule getSchedule(int scheduleId) throws SQLException {
        String query = "SELECT * FROM Schedule WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Schedule schedule = new Schedule(rs.getString("name"));
                    schedule.addTraining(getTrainingsForSchedule(scheduleId));
                    schedule.addExercise(getExercisesForSchedule(scheduleId));
                    return schedule;
                }
            }
        }
        return null;
    }

    // Metodi ausiliari per gestire le relazioni Training <-> Schedule

    private void addScheduleTraining(int scheduleId, int trainingId) throws SQLException {
        String query = "INSERT INTO schedule_training (schedule_id, training_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            stmt.setInt(2, trainingId);
            stmt.executeUpdate();
        }
    }

    private List<Training> getTrainingsForSchedule(int scheduleId) throws SQLException {
        String query = "SELECT * FROM Training t JOIN schedule_training st ON t.id = st.training_id WHERE st.schedule_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Training> trainings = new ArrayList<>();
                while (rs.next()) {
                    trainings.add(new Training(rs.getInt("id"), rs.getString("name"))); // Aggiusta i campi secondo il modello Training
                }
                return trainings;
            }
        }
    }

    private void removeAllScheduleTrainings(int scheduleId) throws SQLException {
        String query = "DELETE FROM schedule_training WHERE schedule_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            stmt.executeUpdate();
        }
    }

    // Metodi ausiliari per gestire le relazioni ExerciseDetail <-> Schedule

    private void addScheduleExercise(int scheduleId, int exerciseId) throws SQLException {
        String query = "INSERT INTO schedule_exercise_detail (schedule_id, exercise_detail_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            stmt.setInt(2, exerciseId);
            stmt.executeUpdate();
        }
    }

    private List<ExerciseDetail> getExercisesForSchedule(int scheduleId) throws SQLException {
        String query = "SELECT * FROM ExerciseDetail e JOIN schedule_exercise_detail se ON e.id = se.exercise_detail_id WHERE se.schedule_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<ExerciseDetail> exercises = new ArrayList<>();
                while (rs.next()) {
                    exercises.add(new ExerciseDetail(rs.getInt("id"), rs.getString("description"))); // Aggiusta i campi secondo il modello ExerciseDetail
                }
                return exercises;
            }
        }
    }

    private void removeAllScheduleExercises(int scheduleId) throws SQLException {
        String query = "DELETE FROM schedule_exercise_detail WHERE schedule_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, scheduleId);
            stmt.executeUpdate();
        }
    }
}
