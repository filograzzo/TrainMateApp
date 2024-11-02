package DAO_tst;

import DAO.PersonalTrainerDAO;
import DomainModel.PersonalTrainer;
import trainmate.DatabaseUtil;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;

import static org.junit.jupiter.api.Assertions.*;

public class PersonalTrainerDAO_tst {
    private static Connection connection;
    private static PersonalTrainerDAO personalTrainerDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        personalTrainerDAO = new PersonalTrainerDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testCreateAndDeleteUserPT() throws SQLException {
        String username = "pt_user";
        String password = "password";
        String email = "pt_user@example.com";

        assertTrue(personalTrainerDAO.create(username, password, email), "User should be created successfully.");
        assertTrue(personalTrainerDAO.usernameExists(username), "Username should exist in the database.");

        assertTrue(personalTrainerDAO.deleteUserPT(username, password, email), "User should be deleted successfully.");
        assertFalse(personalTrainerDAO.usernameExists(username), "Username should not exist after deletion.");
    }

    @Test
    public void testCreateAndDeletePersonalTrainer() throws SQLException {
        String username = "trainer2";
        String password = "password";
        String email = "trainer2@example.com";

        personalTrainerDAO.create(username, password, email);
        int id = personalTrainerDAO.getIdUserPT(username);

        assertTrue(personalTrainerDAO.createPT(id), "Personal Trainer should be created successfully.");
        assertTrue(personalTrainerDAO.isPersonalTrainer(new PersonalTrainer(id, username, password, email)), "Personal Trainer should be found in the database.");

        assertTrue(personalTrainerDAO.deletePT(id), "Personal Trainer should be deleted successfully.");
        personalTrainerDAO.deleteUserPT(username, password, email);  // Cleanup user
    }

    @Test
    public void testUpdateUsername() throws SQLException {
        String oldUsername = "trainer3";
        String newUsername = "newTrainer3";
        String password = "password";
        String email = "trainer3@example.com";

        personalTrainerDAO.create(oldUsername, password, email);
        assertTrue(personalTrainerDAO.updateUsername(oldUsername, newUsername), "Username should be updated successfully.");

        int id = personalTrainerDAO.getIdUserPT(newUsername);
        assertTrue(id > 0, "User ID should be found with the new username.");

        // Cleanup
        personalTrainerDAO.deleteUserPT(newUsername, password, email);
    }

    @Test
    public void testCheckIfFree() throws SQLException {
        String username = "trainer4";
        String password = "password";
        String email = "trainer4@example.com";
        String day = "2024-12-01";
        Time time = Time.valueOf("09:30:00");

        personalTrainerDAO.create(username, password, email);
        int id = personalTrainerDAO.getIdUserPT(username);
        personalTrainerDAO.createPT(id);

        // Check availability (assuming there is no overlapping appointment/course)
        assertTrue(personalTrainerDAO.checkIfFree(id, day, time), "Trainer should be free at the given time.");

        // Cleanup
        personalTrainerDAO.deletePT(id);
        personalTrainerDAO.deleteUserPT(username, password, email);
    }

    @Test
    public void testGetPersonalTrainer() throws SQLException {
        String username = "trainer5";
        String password = "password";
        String email = "trainer5@example.com";

        personalTrainerDAO.create(username, password, email);
        int id = personalTrainerDAO.getIdUserPT(username);
        personalTrainerDAO.createPT(id);

        PersonalTrainer pt = personalTrainerDAO.getPersonalTrainer(username, password, email);
        assertNotNull(pt, "Personal Trainer should be retrieved successfully.");
        assertEquals(username, pt.getUsername(), "Retrieved username should match.");
        assertEquals(email, pt.getEmail(), "Retrieved email should match.");

        // Cleanup
        personalTrainerDAO.deletePT(id);
        personalTrainerDAO.deleteUserPT(username, password, email);
    }

    @Test
    public void testGetAllPersonalTrainers() throws SQLException {
        String username1 = "trainerA";
        String username2 = "trainerB";
        String password = "password";
        String email1 = "trainerA@example.com";
        String email2 = "trainerB@example.com";

        // Create two personal trainers
        personalTrainerDAO.create(username1, password, email1);
        personalTrainerDAO.create(username2, password, email2);
        int id1 = personalTrainerDAO.getIdUserPT(username1);
        int id2 = personalTrainerDAO.getIdUserPT(username2);
        personalTrainerDAO.createPT(id1);
        personalTrainerDAO.createPT(id2);

        // Retrieve all personal trainers
        assertTrue(personalTrainerDAO.getAllPersonalTrainers().size() >= 2, "There should be at least two personal trainers in the database.");

        // Cleanup
        personalTrainerDAO.deletePT(id1);
        personalTrainerDAO.deletePT(id2);
        personalTrainerDAO.deleteUserPT(username1, password, email1);
        personalTrainerDAO.deleteUserPT(username2, password, email2);
    }
}
