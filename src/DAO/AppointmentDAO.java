package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import DomainModel.Appointment;
//DAO where there is the personaltrainerid and the clientid and the day and time they have to meet
public class AppointmentDAO {
    private final Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Appointment> getAppointmentsPT(int personalTrainerId) throws SQLException {
        String query = "SELECT * FROM Appointment WHERE personal_trainer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalTrainerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Appointment> appointments = new ArrayList<>();
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("id"),
                            rs.getInt("personal_trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getString("day"),
                            rs.getString("time")
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
                    appointments.add(new Appointment(
                            rs.getInt("id"),
                            rs.getInt("personal_trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getString("day"),
                            rs.getString("time")
                    ));
                }
                return appointments;
            }
        }
    }


    public boolean addAppointment(int id,int personalTrainerId, int customerId, String day, String time) throws SQLException {
        String query = "INSERT INTO Appointment (id,personal_trainer_id, customer_id, day, time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.setInt(2, personalTrainerId);
            stmt.setInt(3, customerId);
            stmt.setString(4, day);
            stmt.setString(5, time);
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
