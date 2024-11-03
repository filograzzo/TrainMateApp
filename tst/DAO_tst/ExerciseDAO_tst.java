package DAO_tst;

import DAO.ExerciseDAO;
import DAO.MachineDAO;
import DomainModel.Exercise;
import DomainModel.Machine;
import trainmate.DatabaseUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ExerciseDAO_tst {
    private static Connection connection;
    private static ExerciseDAO exerciseDAO;
    private static MachineDAO machineDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        exerciseDAO = new ExerciseDAO(connection);
        machineDAO = new MachineDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveExercise() throws SQLException {
        String name = "Squat";
        String category = "Legs";
        int machineId = 50; // Aggiornato con ID valido nel range
        String description = "A basic leg exercise";

        boolean added = exerciseDAO.addExercise(name, category, machineId, description);
        assertTrue(added, "Exercise should be added successfully.");

        Exercise exercise = exerciseDAO.getExerciseByName(name);
        assertNotNull(exercise, "Exercise should exist after being added.");
        assertEquals(name, exercise.getName());
        assertEquals(category, exercise.getCategory());

        boolean removed = exerciseDAO.removeExerciseById(exercise.getId());
        assertTrue(removed, "Exercise should be removed successfully.");
        assertNull(exerciseDAO.getExerciseByName(name), "Exercise should not exist after removal.");
    }

    @Test
    public void testGetExerciseById() throws SQLException {
        String name = "Bench Press";
        String category = "Chest";
        int machineId = 51; // Aggiornato con ID valido nel range
        String description = "Chest strengthening exercise";

        exerciseDAO.addExercise(name, category, machineId, description);
        Exercise exercise = exerciseDAO.getExerciseByName(name);

        Exercise retrievedExercise = exerciseDAO.getExerciseById(exercise.getId());
        assertNotNull(retrievedExercise, "Exercise should be retrieved by ID.");
        assertEquals(name, retrievedExercise.getName());
        assertEquals(category, retrievedExercise.getCategory());

        exerciseDAO.removeExerciseById(exercise.getId());
    }

    @Test
    public void testGetAllExercises() throws SQLException {
        List<Exercise> newExercises = new ArrayList<Exercise>();
        exerciseDAO.addExercise("Deadlift", "Back", 52, "Back and leg exercise"); // Aggiornato
        exerciseDAO.addExercise("Push-Up", "Chest", 53, "Bodyweight chest exercise"); // Aggiornato

        Exercise exercise1 = exerciseDAO.getExerciseByName("Deadlift");
        Exercise exercise2 = exerciseDAO.getExerciseByName("Push-Up");

        newExercises.add(exercise1);
        newExercises.add(exercise2);

        List<Exercise> exercises = exerciseDAO.getAllExercises();
        assertFalse(exercises.isEmpty(), "Exercise list should not be empty.");
        assertTrue(exercises.size() >= 2, "There should be at least two exercises.");

        for (Exercise exercise : newExercises) {
            exerciseDAO.removeExerciseById(exercise.getId());
        }
    }

    @Test
    public void testGetExercisesByCategory() throws SQLException {
        String category = "Arms";
        exerciseDAO.addExercise("Bicep Curl", category, 54, "Arm exercise"); // Aggiornato
        int id = exerciseDAO.getExerciseIdByName("Bicep Curl");

        List<Exercise> exercises = exerciseDAO.getExercisesByCategory(category);
        assertFalse(exercises.isEmpty(), "Exercises list should not be empty for the category.");
        assertTrue(exercises.stream().allMatch(e -> e.getCategory().equals(category)), "All exercises should belong to the specified category.");

        exerciseDAO.removeExerciseById(id);
    }

    @Test
    public void testGetExerciseIdByName() throws SQLException {
        String name = "Lats Pulldown";
        exerciseDAO.addExercise(name, "Back", 55, "Exercise for back muscles"); // Aggiornato

        int exerciseId = exerciseDAO.getExerciseIdByName(name);
        assertNotEquals(-1, exerciseId, "Exercise ID should be retrieved for the given name.");

        exerciseDAO.removeExerciseById(exerciseId);
    }

    @Test
    public void testGetExerciseNameById() throws SQLException {
        String name = "Shoulder Press with dumbells";
        exerciseDAO.addExercise(name, "Shoulders", 56, "Shoulder strengthening exercise"); // Aggiornato

        Exercise exercise = exerciseDAO.getExerciseByName(name);
        String retrievedName = exerciseDAO.getExerciseNameById(exercise.getId());
        assertEquals(name, retrievedName, "Retrieved exercise name should match the expected name.");

        exerciseDAO.removeExerciseById(exercise.getId());
    }

    @Test
    public void testGetExercisesByMachine() throws SQLException {
        int machineId = 57; // Aggiornato con ID valido nel range
        exerciseDAO.addExercise("Leg Press", "Legs", machineId, "Leg exercise using machine");
        int id = exerciseDAO.getExerciseIdByName("Leg Press");
        Machine machine = machineDAO.getMachineById(machineId);
        List<Exercise> exercises = exerciseDAO.getExercisesByMachine(machine);
        assertFalse(exercises.isEmpty(), "Exercises list should not be empty for the machine.");
        assertTrue(exercises.stream().allMatch(e -> e.getMachine() == machineId), "All exercises should belong to the specified machine.");

        exerciseDAO.removeExerciseById(id);
    }
}
