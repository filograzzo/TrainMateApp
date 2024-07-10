// File: tst/com/people/PersonalTrainerServiceTest.java
package com.people;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonalTrainerServiceTest {
    private Connection connection;
    private PersonalTrainerService personalTrainerService;

    @BeforeEach
    public void setUp() throws SQLException {
        // Configura la connessione al database di test
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        personalTrainerService = new PersonalTrainerService(connection);

        // Crea la tabella PersonalTrainer per il test
        connection.createStatement().execute("CREATE TABLE PersonalTrainer (id INT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255))");
    }

    @Test
    public void testRegisterPersonalTrainer() throws SQLException {
        boolean result = personalTrainerService.registerPersonalTrainer("ptuser", "password123", "ptuser@example.com", "correct_access_code");
        assertTrue(result, "Personal Trainer should be registered successfully");

        PersonalTrainer pt = personalTrainerService.loginPersonalTrainer("ptuser", "password123");
        assertNotNull(pt, "Personal Trainer should be able to login after registration");
        assertEquals("ptuser", pt.getUsername());
        assertEquals("password123", pt.getPassword());
        assertEquals("ptuser@example.com", pt.getEmail());
    }

    @Test
    public void testLoginPersonalTrainer() throws SQLException {
        personalTrainerService.registerPersonalTrainer("ptuser", "password123", "ptuser@example.com", "correct_access_code");
        PersonalTrainer pt = personalTrainerService.loginPersonalTrainer("ptuser", "password123");
        assertNotNull(pt, "Personal Trainer should be able to login with correct credentials");

        PersonalTrainer invalidPt = personalTrainerService.loginPersonalTrainer("wronguser", "wrongpassword");
        assertNull(invalidPt, "Personal Trainer should not be able to login with incorrect credentials");
    }

    @Test
    public void testRemovePersonalTrainer() throws SQLException {
        personalTrainerService.registerPersonalTrainer("ptuser", "password123", "ptuser@example.com", "correct_access_code");
        boolean removed = personalTrainerService.removePersonalTrainer("ptuser", "password123", "correct_access_code");
        assertTrue(removed, "Personal Trainer should be removed successfully");

        PersonalTrainer pt = personalTrainerService.loginPersonalTrainer("ptuser", "password123");
        assertNull(pt, "Personal Trainer should not be able to login after being removed");
    }
}
