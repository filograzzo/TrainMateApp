package DAO_tst;

import DAO.TrainingDAO;
import DomainModel.Training;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;
import trainmate.DatabaseUtil;

public class TrainingDAO_tst {
    private static Connection connection;
    private static TrainingDAO trainingDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        trainingDAO = new TrainingDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveTraining() throws SQLException {
        // Usa dati validi per il test
        int scheduleId = 2;
        String username = "Matilde";
        Date date = Date.valueOf("2023-10-10");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("11:00:00");
        String note = "Test training session";

        // Aggiunge una sessione di allenamento
        boolean added = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        assertTrue(added, "Training session should be added successfully.");

        // Recupera tutte le sessioni per verificare che l'aggiunta sia andata a buon fine
        List<Training> trainings = trainingDAO.getAllTrainingsByUsername(username);
        boolean trainingExists = trainings.stream().anyMatch(t ->
                t.getDate().equals(date) &&
                        t.getStartTime().equals(startTime) &&
                        t.getEndTime().equals(endTime) &&
                        t.getNote().equals(note) &&
                        t.getSchedule() == scheduleId &&
                        t.getUsername().equals(username)
        );
        assertTrue(trainingExists, "The added training should be retrievable from the database.");

        // Trova l'ID della sessione aggiunta per la pulizia
        Training trainingToRemove = trainings.stream().filter(t ->
                t.getDate().equals(date) &&
                        t.getStartTime().equals(startTime) &&
                        t.getEndTime().equals(endTime) &&
                        t.getSchedule() == scheduleId &&
                        t.getUsername().equals(username)
        ).findFirst().orElse(null);

        assertNotNull(trainingToRemove, "Training to be removed should be found.");
        boolean removed = trainingDAO.removeTraining(trainingToRemove.getId());
        assertTrue(removed, "Training session should be removed successfully.");
    }

    @Test
    public void testAddAndGetTrainingById() throws SQLException {
        // Dati della nuova sessione di allenamento
        Date date = Date.valueOf("2023-12-01");
        Time startTime = Time.valueOf("15:00:00");
        Time endTime = Time.valueOf("16:00:00");
        String note = "Sessione di test per recupero by ID";
        int scheduleId = 2; // ID di schedule esistente
        String username = "Matilde"; // Username esistente

        // Aggiunge la sessione di allenamento
        boolean added = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        assertTrue(added, "Training session should be added successfully.");

        // Recupera tutte le sessioni dell'utente per trovare l'ID appena aggiunto
        List<Training> trainings = trainingDAO.getAllTrainingsByUsername(username);
        Training trainingToRetrieve = trainings.stream()
                .filter(t -> t.getDate().equals(date) &&
                        t.getStartTime().equals(startTime) &&
                        t.getEndTime().equals(endTime) &&
                        t.getNote().equals(note) &&
                        t.getSchedule() == scheduleId)
                .findFirst()
                .orElse(null);

        assertNotNull(trainingToRetrieve, "The newly added training session should be retrievable.");

        // Recupera la sessione tramite il suo ID e verifica i dati
        int trainingId = trainingToRetrieve.getId();
        Training retrievedTraining = trainingDAO.getTrainingById(trainingId);
        assertNotNull(retrievedTraining, "Training with ID " + trainingId + " should exist.");
        assertEquals(trainingId, retrievedTraining.getId(), "Retrieved ID should match the added training ID.");
        assertEquals(date, retrievedTraining.getDate(), "Date should match.");
        assertEquals(startTime, retrievedTraining.getStartTime(), "Start time should match.");
        assertEquals(endTime, retrievedTraining.getEndTime(), "End time should match.");
        assertEquals(note, retrievedTraining.getNote(), "Note should match.");
        assertEquals(scheduleId, retrievedTraining.getSchedule(), "Schedule ID should match.");
        assertEquals(username, retrievedTraining.getUsername(), "Username should match.");

        // Cleanup: Rimuove la sessione di allenamento aggiunta
        boolean removed = trainingDAO.removeTraining(trainingId);
        assertTrue(removed, "Training session should be removed successfully after the test.");
    }


    @Test
    public void testGetAllTrainingsByUsername() throws SQLException {
        // Dettagli della sessione di allenamento per il test
        Date date = Date.valueOf("2023-12-03");
        Time startTime = Time.valueOf("10:00:00");
        Time endTime = Time.valueOf("11:00:00");
        String note = "Sessione di test per recupero per Username";
        int scheduleId = 5; // Schedule ID valido
        String username = "Nayla"; // Username noto

        // Aggiunge una sessione di allenamento per l'username specificato
        boolean added = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        assertTrue(added, "Training session should be added successfully.");

        // Recupera tutte le sessioni di allenamento per l'username specificato
        List<Training> trainings = trainingDAO.getAllTrainingsByUsername(username);

        // Verifica che esistano sessioni per l'username
        assertFalse(trainings.isEmpty(), "Trainings for username " + username + " should exist in the database.");
        assertTrue(trainings.stream().allMatch(t -> t.getUsername().equals(username)),
                "All retrieved trainings should match the username.");

        // Trova la sessione appena aggiunta e recupera il suo ID
        Training trainingToRemove = trainings.stream()
                .filter(t -> t.getDate().equals(date) &&
                        t.getStartTime().equals(startTime) &&
                        t.getEndTime().equals(endTime) &&
                        t.getNote().equals(note) &&
                        t.getSchedule() == scheduleId &&
                        t.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        assertNotNull(trainingToRemove, "The newly added training session should be retrievable.");

        // Cleanup: Rimuove la sessione di allenamento aggiunta
        boolean removed = trainingDAO.removeTraining(trainingToRemove.getId());
        assertTrue(removed, "Training session should be removed successfully after the test.");
    }


    @Test
    public void testGetAllTrainingsBySchedule() throws SQLException {
        // Dettagli della sessione di allenamento per il test
        Date date = Date.valueOf("2023-12-02");
        Time startTime = Time.valueOf("14:00:00");
        Time endTime = Time.valueOf("15:00:00");
        String note = "Sessione di test per recupero per Schedule ID";
        int scheduleId = 4; // Schedule ID noto
        String username = "Fausto"; // Username esistente

        // Aggiunge una sessione di allenamento con il schedule ID specificato
        boolean added = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        assertTrue(added, "Training session should be added successfully.");

        // Recupera tutte le sessioni di allenamento con lo schedule ID specificato
        List<Training> trainings = trainingDAO.getAllTrainingsBySchedule(scheduleId);

        // Verifica che esistano sessioni per il schedule ID
        assertFalse(trainings.isEmpty(), "Trainings for schedule ID " + scheduleId + " should exist in the database.");
        assertTrue(trainings.stream().allMatch(t -> t.getSchedule() == scheduleId),
                "All retrieved trainings should match the schedule ID.");

        // Trova la sessione appena aggiunta e recupera il suo ID
        Training trainingToRemove = trainings.stream()
                .filter(t -> t.getDate().equals(date) &&
                        t.getStartTime().equals(startTime) &&
                        t.getEndTime().equals(endTime) &&
                        t.getNote().equals(note) &&
                        t.getSchedule() == scheduleId &&
                        t.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        assertNotNull(trainingToRemove, "The newly added training session should be retrievable.");

        // Cleanup: Rimuove la sessione di allenamento aggiunta
        boolean removed = trainingDAO.removeTraining(trainingToRemove.getId());
        assertTrue(removed, "Training session should be removed successfully after the test.");
    }


    @Test
    public void testUpdateTraining() throws SQLException {
        // Usa dati di sessione esistenti
        int scheduleId = 5;
        String username = "Duccio";
        Date date = Date.valueOf("2023-11-10");
        Time startTime = Time.valueOf("09:00:00");
        Time endTime = Time.valueOf("10:00:00");
        String note = "Original note";

        // Aggiunge una sessione temporanea per il test
        boolean added = trainingDAO.addTraining(date, startTime, endTime, note, scheduleId, username);
        assertTrue(added, "Temporary training session should be added successfully.");

        // Recupera la sessione aggiunta
        Training trainingToUpdate = trainingDAO.getAllTrainingsByUsername(username).stream()
                .filter(t -> t.getDate().equals(date) && t.getStartTime().equals(startTime) && t.getEndTime().equals(endTime))
                .findFirst().orElse(null);
        assertNotNull(trainingToUpdate, "Training to update should be retrievable.");

        // Modifica i dettagli della sessione
        trainingToUpdate.setNote("Updated note");
        boolean updated = trainingDAO.updateTraining(trainingToUpdate);
        assertTrue(updated, "Training session should be updated successfully.");

        // Recupera la sessione modificata per verifica
        Training updatedTraining = trainingDAO.getTrainingById(trainingToUpdate.getId());
        assertNotNull(updatedTraining, "Updated training should still be retrievable.");
        assertEquals("Updated note", updatedTraining.getNote(), "Training note should be updated.");

        // Cleanup: Rimuove la sessione temporanea
        boolean removed = trainingDAO.removeTraining(trainingToUpdate.getId());
        assertTrue(removed, "Temporary training session should be removed after test.");
    }
}
