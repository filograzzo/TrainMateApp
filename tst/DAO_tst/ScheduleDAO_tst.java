package DAO_tst;

import DAO.ScheduleDAO;
import DomainModel.ExerciseDetail;
import DomainModel.Schedule;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import trainmate.DatabaseUtil;

public class ScheduleDAO_tst {
    private static Connection connection;
    private static ScheduleDAO scheduleDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        scheduleDAO = new ScheduleDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveSchedule() throws SQLException {
        String tempScheduleName = "Temporary Workout Plan";
        String customer = "test_customer";

        // Aggiungi un nuovo Schedule temporaneo e verifica l'inserimento
        assertTrue(scheduleDAO.addSchedule(tempScheduleName, customer), "Schedule should be added successfully.");

        // Recupera e verifica l'esistenza dello Schedule appena aggiunto
        Schedule schedule = scheduleDAO.getScheduleByName(tempScheduleName);
        assertNotNull(schedule, "Schedule should exist after being added.");
        assertEquals(tempScheduleName, schedule.getName(), "Schedule name should match.");
        assertEquals(customer, schedule.getCustomer(), "Schedule customer should match.");

        // Rimuove lo Schedule temporaneo e verifica la rimozione
        assertTrue(scheduleDAO.removeScheduleById(schedule.getId()), "Schedule should be removed successfully.");
        assertNull(scheduleDAO.getScheduleByName(tempScheduleName), "Schedule should not exist after removal.");
    }

    @Test
    public void testSetAndGetExercises() throws SQLException {
        String scheduleName = "Evening Plan";
        String customer = "customer3";

        // Aggiungi un nuovo Schedule temporaneo per il test
        assertTrue(scheduleDAO.addSchedule(scheduleName, customer), "Schedule should be added successfully.");
        Schedule schedule = scheduleDAO.getScheduleByName(scheduleName);

        // Aggiungi esercizi alla Schedule e verifica il settaggio
        List<ExerciseDetail> exercises = new ArrayList<>();
        exercises.add(new ExerciseDetail(1, 3, 10, 50, schedule.getId(), 1));
        exercises.add(new ExerciseDetail(2, 4, 8, 40, schedule.getId(), 1));

        schedule.setExercises(exercises);
        assertEquals(2, schedule.getExercises().size(), "Schedule should have two exercises.");
        assertEquals(exercises, schedule.getExercises(), "The exercise list should match the set exercises.");

        // Cleanup: rimuove lo Schedule
        assertTrue(scheduleDAO.removeScheduleById(schedule.getId()), "Temporary schedule should be removed after test.");
    }
}
