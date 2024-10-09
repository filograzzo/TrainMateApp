package BusinessLogic.Service.Customer;

import DAO.AppointmentDAO;
import DAO.PersonalTrainerDAO;
import DomainModel.BaseUser;
import DomainModel.Appointment;
import DomainModel.Customer;
import DomainModel.PersonalTrainer;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class BookAppointmentService {
    private AppointmentDAO appointmentDAO;
    private PersonalTrainerDAO personalTrainerDAO;
    private BaseUser currentUser;
    public BookAppointmentService(AppointmentDAO appointmentDAO, PersonalTrainerDAO personalTrainerDAO) {
        this.appointmentDAO = appointmentDAO;
        this.personalTrainerDAO = personalTrainerDAO;
    }

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public List<Appointment> viewAppointments(int id) throws SQLException {
        List<Appointment> appointments = appointmentDAO.getAppointmentsCustomer(id);
        ((Customer)currentUser).getAppointmentList().getAppointments().clear();//per mostrare solo i nuovi appuntamenti
        ((Customer)currentUser).getAppointmentList().getAppointments().addAll(appointments);
        return appointments;
    }

    public boolean bookAppointment(int customerId, int ptrainerId, String day, Time time) throws SQLException {
        // Check if the time is within the allowed range (9:00 to 17:00)
        Time startTime = Time.valueOf("09:00:00");
        Time endTime = Time.valueOf("17:00:00");
        if (time.before(startTime) || time.after(endTime)) {
            System.err.println("Gym not open at that time (from 9:00 to 17:00)");
            return false;
        }

        if (appointmentDAO.addAppointment( ptrainerId,customerId, day, time)) {
            ((Customer) currentUser).getAppointmentList().addAppointment(appointmentDAO.getAppointment(ptrainerId,customerId, day, time));
            ArrayList<Appointment> appointments = appointmentDAO.getAppointmentsPT(ptrainerId);
            PersonalTrainer pt = personalTrainerDAO.getPTbyId(ptrainerId);
            if (pt != null) {
                pt.getAppointmentList().clear();
                pt.getAppointmentList().addAll(appointments);
            } else {
                // Handle the case where the PersonalTrainer object is null
                System.err.println("PersonalTrainer with ID " + ptrainerId + " not found.");
                return false;
            }

            return true;
        } else {
            return false;
        }
    }
    public int getAppointmentidByDayandTime(String day,Time time){
        return appointmentDAO.getAppointmentidByDayandTime(day,time);
    }
    public int getPTidByAppointmentId(int id){
        return appointmentDAO.getPTidByAppointmentId(id);
    }

    public boolean cancelAppointment(int idAppointment, int PTid) throws SQLException {
        if (appointmentDAO.removeAppointment(idAppointment)) {
            ((Customer) currentUser).getAppointmentList().removeAppointment(idAppointment);
            PersonalTrainer pt = personalTrainerDAO.getPTbyId(PTid);
            if (pt != null) {
                return true;
            } else {
                System.err.println("Personal Trainer with ID " + PTid + " not found.");
            }
            return true;
        } else {
            return false;
        }
    }
}
