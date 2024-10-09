package DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import DomainModel.Appointment;
import DomainModel.PersonalTrainer;

//DAO where there is the personaltrainerid and the clientid and the day and time they have to meet
public class AppointmentDAO {
    private final Connection connection;

    public AppointmentDAO(Connection connection) {
        this.connection = connection;
    }

    public ArrayList<Appointment> getAppointmentsPT(int personalTrainerId) throws SQLException {
        String query = "SELECT * FROM Appointment WHERE trainer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalTrainerId);
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Appointment> appointments = new ArrayList<>();
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("id"),
                            rs.getInt("trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getString("appointment_day"),
                            rs.getTime("appointment_time")
                    ));
                }
                return appointments;
            }
        }
    }
    public int getAppointmentidByDayandTime(String day,Time time){
        String query = "SELECT id FROM Appointment WHERE appointment_day = ? AND appointment_time = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, day);
            stmt.setTime(2, time);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public int getPTidByAppointmentId(int id){
        String query = "SELECT trainer_id FROM Appointment WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("trainer_id");
                } else {
                    return -1;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return -1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
    public Appointment getAppointment(int personalTrainerId, int customerId, String day, Time time) throws SQLException {
        String query = "SELECT * FROM Appointment WHERE trainer_id = ? AND customer_id = ? AND appointment_day = ? AND appointment_time = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalTrainerId);
            stmt.setInt(2, customerId);
            stmt.setString(3, day);
            stmt.setTime(4, time);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Appointment(
                            rs.getInt("id"),
                            rs.getInt("trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getString("appointment_day"),
                            rs.getTime("appointment_time")
                    );
                } else {
                    return null;
                }
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
                            rs.getInt("trainer_id"),
                            rs.getInt("customer_id"),
                            rs.getString("appointment_day"),
                            rs.getTime("appointment_time"));
                    appointments.add(appointment);
                }
                return appointments;
            }
        }
    }


    public boolean addAppointment(int personalTrainerId, int customerId, String day, Time time) throws SQLException {
        String query = "INSERT INTO Appointment (trainer_id, customer_id, appointment_day, appointment_time) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, personalTrainerId);
            stmt.setInt(2, customerId);
            stmt.setString(3, day);
            stmt.setTime(4, time);
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
