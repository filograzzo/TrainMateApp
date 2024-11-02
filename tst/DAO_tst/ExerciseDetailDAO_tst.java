package DAO_tst;

import DAO.ExerciseDetailDAO;
import DomainModel.ExerciseDetail;
import trainmate.DatabaseUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseDetailDAO_tst {
    private static Connection connection;
    private static ExerciseDetailDAO exerciseDetailDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        exerciseDetailDAO = new ExerciseDetailDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndDeleteExerciseDetail() throws SQLException {
        int serie = 3;
        int reps = 10;
        int weight = 50;
        int scheduleId = 5;
        int exerciseId = 2;

        boolean added = exerciseDetailDAO.addExerciseDetail(serie, reps, weight, scheduleId, exerciseId);
        assertTrue(added, "Exercise detail should be added successfully.");

        // Retrieve the inserted detail
        List<ExerciseDetail> details = exerciseDetailDAO.getExerciseDetailsByScheduleId(scheduleId);
        assertFalse(details.isEmpty(), "Exercise details should exist for the given schedule ID.");

        details = exerciseDetailDAO.getAll();

        ExerciseDetail detail = details.getLast();
        assertEquals(serie, detail.getSerie());
        assertEquals(reps, detail.getReps());

        // Delete the detail and verify
        boolean deleted = exerciseDetailDAO.deleteExerciseDetailById(detail.getId());
        assertTrue(deleted, "Exercise detail should be deleted successfully.");
        assertNull(exerciseDetailDAO.getExerciseDetailById(detail.getId()), "Exercise detail should not exist after deletion.");
    }

    @Test
    public void testGetExerciseDetailById() throws SQLException {
        int serie = 4;
        int reps = 12;
        int weight = 60;
        int scheduleId = 5;
        int exerciseId = 2;

        exerciseDetailDAO.addExerciseDetail(serie, reps, weight, scheduleId, exerciseId);
        List<ExerciseDetail> details = exerciseDetailDAO.getAll();
        ExerciseDetail detail = details.getLast();
        ExerciseDetail retrievedDetail = exerciseDetailDAO.getExerciseDetailById(detail.getId());
        assertNotNull(retrievedDetail, "Exercise detail should be retrieved by ID.");
        assertEquals(serie, retrievedDetail.getSerie());
        assertEquals(reps, retrievedDetail.getReps());

        exerciseDetailDAO.deleteExerciseDetailById(detail.getId());
    }

    @Test
    public void testUpdateExerciseDetail() throws SQLException {
        int serie = 2;
        int reps = 8;
        int weight = 40;
        int scheduleId = 5;
        int exerciseId = 2;

        exerciseDetailDAO.addExerciseDetail(serie, reps, weight, scheduleId, exerciseId);
        List<ExerciseDetail> details = exerciseDetailDAO.getAll();
        ExerciseDetail detail = details.getLast();

        int updatedSerie = 5;
        int updatedReps = 15;
        int updatedWeight = 70;
        int updatedExerciseId = 5;

        boolean updated = exerciseDetailDAO.updateExerciseDetail(detail.getId(), updatedSerie, updatedReps, updatedWeight, updatedExerciseId);
        assertTrue(updated, "Exercise detail should be updated successfully.");

        ExerciseDetail updatedDetail = exerciseDetailDAO.getExerciseDetailById(detail.getId());
        assertEquals(updatedSerie, updatedDetail.getSerie(), "Updated series should match.");
        assertEquals(updatedReps, updatedDetail.getReps(), "Updated reps should match.");
        assertEquals(updatedWeight, updatedDetail.getWeight(), "Updated weight should match.");

        exerciseDetailDAO.deleteExerciseDetailById(detail.getId());
    }

    @Test
    public void testGetExerciseDetailsByScheduleId() throws SQLException {
        int scheduleId = 5;
        exerciseDetailDAO.addExerciseDetail(2, 8, 45, scheduleId, 3);

        ExerciseDetail ex = exerciseDetailDAO.getAll().getLast();
        List<ExerciseDetail> exs = new ArrayList<>();
        exs.add(ex);

        exerciseDetailDAO.addExerciseDetail(3, 10, 50, scheduleId, 3);

        ex = exerciseDetailDAO.getAll().getLast();
        exs.add(ex);

        List<ExerciseDetail> details = exerciseDetailDAO.getExerciseDetailsByScheduleId(scheduleId);
        assertTrue(details.size() >= 2, "There should be at least two exercise details for the specified schedule ID.");

        for (ExerciseDetail detail : exs) {
            assertEquals(scheduleId, detail.getSchedule(), "Schedule ID should match the expected value.");
            exerciseDetailDAO.deleteExerciseDetailById(detail.getId());
        }
    }
}
