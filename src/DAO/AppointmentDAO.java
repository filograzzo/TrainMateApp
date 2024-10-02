package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DomainModel.Appointment;
//DAO where there is the personaltrainerid and the clientid and the day and time they have to meet
public class AppointmentDAO {
    private final Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Appointment> getAppointmentsPT(int personalTrainerId) throws SQLException {
        String query = "SELECT * FROM Appointment WHERE trainer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalTrainerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Appointment> appointments = new ArrayList<>();
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("id"),
                            rs.getInt("personal_trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getDate("day"),
                            rs.getTime("time")
                    ));
                }
                return appointments;
            }
        }
    }

    public List<Appointment> getAppointmentsCustomer(int customerId) throws SQLException {
        String query = "SELECT * FROM Appointment WHERE customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Appointment> appointments = new ArrayList<>();
                while (rs.next()) {
                    Appointment appointment=new Appointment(
                            rs.getInt("id"),
                            rs.getInt("personal_trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getDate("day"),
                            rs.getTime("time"));
                    appointments.add(appointment);
                }
                return appointments;
            }
        }
    }


    public boolean addAppointment(int id, int personalTrainerId, int customerId, Date day, Time time) throws SQLException {
        String query = "INSERT INTO Appointment (id,trainer_id, customer_id, appointment_day, appointment_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            java.sql.Date sqlDate = new java.sql.Date(day.getTime());
            stmt.setInt(1, id);
            stmt.setInt(2, personalTrainerId);
            stmt.setInt(3, customerId);
            stmt.setDate(4, sqlDate);
            stmt.setTime(5, time);
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean removeAppointment(int id) throws SQLException {
        String query = "DELETE FROM Appointment WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }


}
