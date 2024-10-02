package BusinessLogic.Service.Customer;

import DAO.AppointmentDAO;
import DomainModel.BaseUser;
import DomainModel.Appointment;
import DomainModel.Customer;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

public class BookAppointmentService {
    private AppointmentDAO appointmentDAO;
    private BaseUser currentUser;
    public BookAppointmentService(AppointmentDAO appointmentDAO) {
        this.appointmentDAO = appointmentDAO;
    }

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public List<Appointment> viewAppointments() throws SQLException {
        List<Appointment> appointments = appointmentDAO.getAppointmentsCustomer(currentUser.getId());
        ((Customer)currentUser).getAppointmentList().getAppointments().clear();//per mostrare solo i nuovi appuntamenti
        ((Customer)currentUser).getAppointmentList().getAppointments().addAll(appointments);
        return appointments;
    }

    public void bookAppointment(int courseId, int currentUserId, int PtrainerId, Date day, Time time) throws SQLException {
        appointmentDAO.addAppointment(courseId,currentUserId,PtrainerId,day,time);
        ((Customer)currentUser).getAppointmentList().addAppointment(new Appointment(courseId,currentUserId,PtrainerId,day,time));
    }

    public void cancelAppointment(int idAppointment) throws SQLException {
        appointmentDAO.removeAppointment(idAppointment);
        ((Customer)currentUser).getAppointmentList().removeAppointment(idAppointment);
    }
}
