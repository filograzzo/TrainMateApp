package DAO_tst;

import DAO.SignedDAO;
import DomainModel.Signed;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;
import trainmate.DatabaseUtil;

public class SignedDAO_tst {
    private static Connection connection;
    private static SignedDAO signedDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        signedDAO = new SignedDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveSigned() throws SQLException {
        int courseId = 1; // ID di corso esistente
        int customerId = 2; // ID di cliente esistente
        int maxParticipants = 10;

        // Aggiunge un'iscrizione
        boolean added = signedDAO.addSigned(customerId, courseId, maxParticipants);
        assertTrue(added, "Customer should be signed up successfully.");

        // Verifica che l'iscrizione esista
        Signed signed = signedDAO.getSigned(courseId, customerId);
        assertNotNull(signed, "Signed should exist after being added.");
        assertEquals(courseId, signed.getCourseId(), "Course ID should match.");
        assertEquals(customerId, signed.getCustomerId(), "Customer ID should match.");

        // Rimuove l'iscrizione
        boolean removed = signedDAO.removeSigned(courseId, customerId);
        assertTrue(removed, "Customer should be removed from the course.");
        assertNull(signedDAO.getSigned(courseId, customerId), "Signed should not exist after removal.");
    }

    @Test
    public void testSignedToCoursebyId() throws SQLException {
        int courseId = 8; // ID di corso esistente
        int customerId1 = 3; // ID di cliente esistente
        int customerId2 = 6; // Un altro ID di cliente esistente
        int maxParticipants = 10;

        // Aggiunge due iscrizioni al corso
        signedDAO.addSigned(customerId1, courseId, maxParticipants);
        signedDAO.addSigned(customerId2, courseId, maxParticipants);

        // Verifica il numero di iscrizioni per il corso
        int count = signedDAO.SignedToCoursebyId(courseId);
        assertTrue(count >= 2, "There should be at least two participants signed up for the course.");

        // Rimuove le iscrizioni
        signedDAO.removeSigned(courseId, customerId1);
        signedDAO.removeSigned(courseId, customerId2);
    }

    @Test
    public void testAddSignedExceedsMaxParticipants() throws SQLException {
        int courseId = 9; // ID di corso esistente
        int customerId1 = 8; // ID di cliente esistente
        int customerId2 = 9; // Un altro ID di cliente esistente
        int maxParticipants = 1; // Limite di partecipanti impostato a 1

        // Aggiunge un partecipante
        assertTrue(signedDAO.addSigned(customerId1, courseId, maxParticipants), "First participant should be added successfully.");

        // Tenta di aggiungere un secondo partecipante, che dovrebbe fallire
        assertFalse(signedDAO.addSigned(customerId2, courseId, maxParticipants), "Second participant should not be added due to max participants limit.");

        // Cleanup
        signedDAO.removeSigned(courseId, customerId1);
    }

    @Test
    public void testUserAlreadySigned() throws SQLException {
        int courseId = 10; // ID di corso esistente
        int customerId = 10; // ID di cliente esistente
        int maxParticipants = 5;

        // Aggiunge un'iscrizione
        signedDAO.addSigned(customerId, courseId, maxParticipants);

        // Verifica che l'utente sia iscritto
        assertTrue(signedDAO.userAlreadySigned(customerId, courseId), "User should be signed up for the course.");

        // Cleanup
        signedDAO.removeSigned(courseId, customerId);
    }

    @Test
    public void testRemoveSignedWhenNotSigned() throws SQLException {
        int courseId = 22; // ID di corso esistente
        int customerId = 6; // ID di cliente esistente ma non iscritto

        // Prova a rimuovere un'iscrizione che non esiste
        boolean removed = signedDAO.removeSigned(courseId, customerId);
        assertFalse(removed, "Removing a non-existent signed should return false.");
    }
}
