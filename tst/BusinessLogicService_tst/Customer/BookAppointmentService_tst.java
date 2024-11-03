/*package BusinessLogicService_tst.Customer;

import BusinessLogic.Service.Customer.BookAppointmentService;
import DAO.AppointmentDAO;
import DAO.PersonalTrainerDAO;
import DAO.CustomerDAO;
import DomainModel.Appointment;
import DomainModel.Customer;
import DomainModel.PersonalTrainer;
import org.junit.jupiter.api.*;
import trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookAppointmentService_tst {
    private static Connection connection;
    private static AppointmentDAO appointmentDAO;
    private static PersonalTrainerDAO personalTrainerDAO;
    private static CustomerDAO customerDAO;
    private static BookAppointmentService bookAppointmentService;
    private static Customer testCustomer;
    private static PersonalTrainer testTrainer;
    private static int customerId;
    private static int trainerId;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        appointmentDAO = new AppointmentDAO(connection);
        personalTrainerDAO = new PersonalTrainerDAO(connection);
        customerDAO = new CustomerDAO(connection);
        bookAppointmentService = new BookAppointmentService(appointmentDAO, personalTrainerDAO);

        String customerUsername = "testCustomer";
        String trainerUsername = "testTrainer";

        customerDAO.insertUser(customerUsername, "password", "testUser@esample.com");
        customerId  = customerDAO.getIdUserCustomer(customerUsername);

        personalTrainerDAO.create(trainerUsername, "password", "testPT@example.com");
        trainerId  = personalTrainerDAO.getIdUserPT(trainerUsername);

        customerDAO.insertCustomer(customerId);
        personalTrainerDAO.createPT(trainerId);

        testCustomer = customerDAO.getCustomerByUsername(customerUsername);
        testTrainer = personalTrainerDAO.getPersonalTrainer(trainerUsername, "password","testPT@example.com");

        bookAppointmentService.setCurrentUser(testCustomer);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        customerDAO.deleteCustomer(customerId);
        customerDAO.deleteUserCustomer("testCustomer", "password", "testUser@esample.com");

        personalTrainerDAO.deletePT(trainerId);
        personalTrainerDAO.deleteUserPT("testTrainer", "password", "testPT@example.com");

        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testBookAppointmentSuccess() throws SQLException {
        String day = "2024-10-25";
        Time time = Time.valueOf("10:00:00");

        boolean result = bookAppointmentService.bookAppointment(customerId, trainerId, day, time);
        assertTrue(result, "Booking should be successful");

        // Verify appointment was added
        Appointment appointment = appointmentDAO.getAppointment(trainerId, customerId, day, time);
        assertNotNull(appointment, "Appointment should exist in database");

        // Verify appointment is in customer's list
        assertTrue(testCustomer.getAppointmentList().getAppointments().stream()
                .anyMatch(a -> a.getPersonalTrainerId() == trainerId &&
                        a.getCustomerId() == customerId));

        // Clean up
        bookAppointmentService.cancelAppointment(appointment.getId(), trainerId);
    }

    @Test
    public void testBookAppointmentOutsideHours() throws SQLException {
        String day = "2024-10-25";
        Time invalidTime = Time.valueOf("08:00:00"); // Before opening hours

        boolean result = bookAppointmentService.bookAppointment(customerId, trainerId, day, invalidTime);
        assertFalse(result, "Booking should fail outside business hours");

        // Verify no appointment was added
        assertNull(appointmentDAO.getAppointment(trainerId, customerId, day, invalidTime));
    }

    @Test
    public void testViewAppointments() throws SQLException {
        String day = "2024-10-26";
        Time time = Time.valueOf("14:00:00");

        // Add test appointment
        bookAppointmentService.bookAppointment(customerId, trainerId, day, time);

        // Test viewing appointments
        List<Appointment> appointments = bookAppointmentService.viewAppointments(customerId);
        assertFalse(appointments.isEmpty(), "Should have at least one appointment");

        // Verify the appointment details
        boolean found = appointments.stream()
                .anyMatch(a -> a.getPersonalTrainerId() == trainerId &&
                        a.getCustomerId() == customerId);
        assertTrue(found, "Added appointment should be in the list");

        // Clean up
        int appointmentId = bookAppointmentService.getAppointmentidByDayandTime(day, time);
        bookAppointmentService.cancelAppointment(appointmentId, trainerId);
    }

    @Test
    public void testCancelAppointment() throws SQLException {
        String day = "2024-10-27";
        Time time = Time.valueOf("15:00:00");

        // First book an appointment
        bookAppointmentService.bookAppointment(customerId, trainerId, day, time);
        int appointmentId = bookAppointmentService.getAppointmentidByDayandTime(day, time);

        // Test cancellation
        boolean cancelResult = bookAppointmentService.cancelAppointment(appointmentId, trainerId);
        assertTrue(cancelResult, "Cancellation should be successful");

        // Verify appointment was removed
        assertNull(appointmentDAO.getAppointment(trainerId, customerId, day, time));

        // Verify appointment is removed from customer's list
        assertTrue(testCustomer.getAppointmentList().getAppointments().stream()
                .noneMatch(a -> a.getId() == appointmentId));
    }

    @Test
    public void testGetPTidByAppointmentId() throws SQLException {
        String day = "2024-10-28";
        Time time = Time.valueOf("11:00:00");

        // Create appointment
        bookAppointmentService.bookAppointment(customerId, trainerId, day, time);
        int appointmentId = bookAppointmentService.getAppointmentidByDayandTime(day, time);

        // Test getting PT ID
        int retrievedTrainerId = bookAppointmentService.getPTidByAppointmentId(appointmentId);
        assertEquals(trainerId, retrievedTrainerId, "Retrieved trainer ID should match");

        // Clean up
        bookAppointmentService.cancelAppointment(appointmentId, trainerId);
    }
}*/