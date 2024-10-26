package DAO_tst;

import DAO.AppointmentDAO;
import DomainModel.Appointment;
import org.junit.jupiter.api.*;
import trainmate.DatabaseUtil;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentDAO_tst {
    private static Connection connection;
    private static AppointmentDAO appointmentDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        appointmentDAO = new AppointmentDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRemoveAppointment() throws SQLException {
        int trainerId = 1;
        int customerId = 2;
        String day = "2024-10-25";
        Time time = Time.valueOf("10:00:00");

        boolean added = appointmentDAO.addAppointment(trainerId, customerId, day, time);
        assertTrue(added, "Appointment should be added successfully.");

        Appointment appointment = appointmentDAO.getAppointment(trainerId, customerId, day, time);
        assertNotNull(appointment, "Appointment should exist after being added.");
        assertEquals(trainerId, appointment.getPersonalTrainerId());
        assertEquals(customerId, appointment.getCustomerId());

        boolean removed = appointmentDAO.removeAppointment(appointment.getId());
        assertTrue(removed, "Appointment should be removed successfully.");
        assertNull(appointmentDAO.getAppointment(trainerId, customerId, day, time), "Appointment should not exist after removal.");
    }

    @Test
    public void testGetAppointmentsByTrainerId() throws SQLException {
        int trainerId = 1;
        int customerId = 3;
        String day = "2024-11-10";
        Time time = Time.valueOf("14:00:00");

        appointmentDAO.addAppointment(trainerId, customerId, day, time);
        List<Appointment> appointments = appointmentDAO.getAppointmentsPT(trainerId);

        assertFalse(appointments.isEmpty(), "Appointments should be retrieved for the trainer.");
        assertTrue(appointments.stream().anyMatch(a -> a.getCustomerId() == customerId), "The appointment with the specified customer should be found.");

        appointmentDAO.removeAppointment(appointments.get(0).getId());
    }

    @Test
    public void testGetAppointmentsByCustomerId() throws SQLException {
        int trainerId = 4;
        int customerId = 6;
        String day = "2024-12-01";
        Time time = Time.valueOf("09:30:00");

        appointmentDAO.addAppointment(trainerId, customerId, day, time);
        List<Appointment> appointments = appointmentDAO.getAppointmentsCustomer(customerId);

        assertFalse(appointments.isEmpty(), "Appointments should be retrieved for the customer.");
        assertTrue(appointments.stream().anyMatch(a -> a.getPersonalTrainerId() == trainerId), "The appointment with the specified trainer should be found.");

        appointmentDAO.removeAppointment(appointments.getFirst().getId());
    }

    @Test
    public void testGetAppointmentIdByDayAndTime() throws SQLException {
        int trainerId = 5;
        int customerId = 6;
        String day = "2024-11-15";
        Time time = Time.valueOf("16:00:00");

        appointmentDAO.addAppointment(trainerId, customerId, day, time);
        int appointmentId = appointmentDAO.getAppointmentidByDayandTime(day, time);

        assertNotEquals(-1, appointmentId, "Appointment ID should be retrieved for the specific day and time.");

        appointmentDAO.removeAppointment(appointmentId);
    }

    @Test
    public void testGetPTIdByAppointmentId() throws SQLException {
        int trainerId = 7;
        int customerId = 8;
        String day = "2024-11-20";
        Time time = Time.valueOf("11:00:00");

        appointmentDAO.addAppointment(trainerId, customerId, day, time);
        int appointmentId = appointmentDAO.getAppointmentidByDayandTime(day, time);

        int retrievedTrainerId = appointmentDAO.getPTidByAppointmentId(appointmentId);
        assertEquals(trainerId, retrievedTrainerId, "Trainer ID should match the expected trainer ID for the appointment.");

        appointmentDAO.removeAppointment(appointmentId);
    }
}
