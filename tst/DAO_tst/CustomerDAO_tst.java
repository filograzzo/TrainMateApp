package DAO_tst;

import DAO.CustomerDAO;
import DomainModel.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDAO_tst {
    private Connection connection;
    private CustomerDAO customerDAO;

    @BeforeEach
    void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        customerDAO = new CustomerDAO(connection);

        // Inserimento di dati di test per le verifiche
        try (Statement stmt = connection.createStatement()) {
            String insertUser = "INSERT INTO User (username, password, email) VALUES ('test', 'password', 'testuser@example.com')";
            stmt.execute(insertUser);

            int id = customerDAO.getIdUserCustomer("test");
            String insertCustomer = "INSERT INTO Customer (id, height, weight, age, gender, goal) VALUES (" + id + " , 180.5, 75.0, 25, 'Male', 'Lose weight')";
            stmt.execute(insertCustomer);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            // First, delete the customer entry that references the user
            stmt.execute("DELETE FROM Customer WHERE id = (SELECT id FROM User WHERE username = 'test')");

            // Then delete the user entry
            stmt.execute("DELETE FROM User WHERE username = 'test' AND password = 'password' AND email = 'testuser@example.com'");
        }
        connection.close();
    }

    @Test
    void testUsernameExists() throws SQLException {
        assertTrue(customerDAO.usernameExists("test"));
        assertFalse(customerDAO.usernameExists("nonexistent"));
    }

    @Test
    void testEmailExists() throws SQLException {
        assertTrue(customerDAO.emailExists("testuser@example.com"));
        assertFalse(customerDAO.emailExists("nonexistent@example.com"));
    }

    @Test
    void testInsertAndDeleteUser() throws SQLException {
        assertTrue(customerDAO.insertUser("newuser", "newpassword", "newuser@example.com"));
        assertTrue(customerDAO.usernameExists("newuser"));
        assertTrue(customerDAO.emailExists("newuser@example.com"));
        assertTrue(customerDAO.deleteUserCustomer("newuser", "newpassword", "newuser@example.com"));
    }

    @Test
    void testGetCustomerByUsername() throws SQLException {
        Customer customer = customerDAO.getCustomerByUsername("test");
        assertNotNull(customer);
        assertEquals("test", customer.getUsername());
        assertEquals("testuser@example.com", customer.getEmail());
    }

    @Test
    void testUpdateCustomer() throws SQLException {
        Customer customer = customerDAO.getCustomerByUsername("test");
        customer.setUsername("updatedTest");
        customer.setEmail("updated@example.com");
        assertTrue(customerDAO.updateCustomer(customer));

        Customer updatedCustomer = customerDAO.getCustomerByUsername("updatedTest");
        assertNotNull(updatedCustomer);
        assertEquals("updated@example.com", updatedCustomer.getEmail());

        customer.setUsername("test");
        customer.setEmail("testuser@example.com");
        customerDAO.updateCustomer(customer);
    }

    @Test
    void testInsertAndDeleteCustomer() throws SQLException {
        connection = DatabaseUtil.getConnection();
        customerDAO = new CustomerDAO(connection);

        // Inserimento di dati di test per le verifiche
        try (Statement stmt = connection.createStatement()) {
            String insertUser = "INSERT INTO User (username, password, email) VALUES ('test1', 'password1', 'testuser1@example.com')";
            stmt.execute(insertUser);

            int id = customerDAO.getIdUserCustomer("test1");
            assertTrue(customerDAO.insertCustomer(id));
        }

        assertTrue(customerDAO.deleteCustomer(customerDAO.getIdUserCustomer("test1")));
        assertTrue(customerDAO.deleteUserCustomer("test1", "password1", "testuser1@example.com"));

    }


    @Test
    void testUpdatePersonalData() throws SQLException {
        Customer customer = customerDAO.getCustomerByUsername("test");
        assertTrue(customerDAO.updatePersonalData(customer.getId(), 185.0f, 78.0f, 26, "Male", "Maintain weight"));

        Customer updatedCustomer = customerDAO.getCustomerByUsername("test");
        assertNotNull(updatedCustomer);
        assertEquals(185.0f, updatedCustomer.getHeight());
        assertEquals(78.0f, updatedCustomer.getWeight());
        assertEquals(26, updatedCustomer.getAge());
        assertEquals("Maintain weight", updatedCustomer.getGoal());
    }

}