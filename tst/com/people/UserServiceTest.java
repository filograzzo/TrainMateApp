// File: tst/com/people/UserServiceTest.java
package com.people;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserServiceTest {
    private Connection connection;
    private UserService userService;

    @BeforeEach
    public void setUp() throws SQLException {
        // Configura la connessione al database di test
        connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        userService = new UserService(connection);

        // Crea la tabella User per il test
        connection.createStatement().execute("CREATE TABLE User (id INT PRIMARY KEY, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255))");
    }

    @Test
    public void testRegisterUser() throws SQLException {
        boolean result = userService.registerUser("testuser", "password123", "testuser@example.com");
        assertTrue(result, "User should be registered successfully");

        User user = userService.loginUser("testuser", "password123");
        assertNotNull(user, "User should be able to login after registration");
        assertEquals("testuser", user.getUsername());
        assertEquals("password123", user.getPassword());
        assertEquals("testuser@example.com", user.getEmail());
    }

    @Test
    public void testLoginUser() throws SQLException {
        userService.registerUser("testuser", "password123", "testuser@example.com");
        User user = userService.loginUser("testuser", "password123");
        assertNotNull(user, "User should be able to login with correct credentials");

        User invalidUser = userService.loginUser("wronguser", "wrongpassword");
        assertNull(invalidUser, "User should not be able to login with incorrect credentials");
    }

    @Test
    public void testRemoveUser() throws SQLException {
        userService.registerUser("testuser", "password123", "testuser@example.com");
        boolean removed = userService.removeUser("testuser", "password123");
        assertTrue(removed, "User should be removed successfully");

        User user = userService.loginUser("testuser", "password123");
        assertNull(user, "User should not be able to login after being removed");
    }
}
