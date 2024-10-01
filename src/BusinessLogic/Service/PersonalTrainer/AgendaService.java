package BusinessLogic.Service.PersonalTrainer;

import DAO.AppointmentDAO;
import DAO.CourseDAO;
import DAO.PersonalTrainerDAO;
import DAO.ScheduleDAO;
import DomainModel.Appointment;
import DomainModel.BaseUser;
import DomainModel.Course;
import DomainModel.PersonalTrainer;

import java.sql.*;
import java.util.List;

public class AgendaService {
    //TODO uan volta aggiunto un oggetto agenda come attributo di PT aggiungere anche le modifiche in locale dell'agenda del PT oltre alle modifiche sul database
    private PersonalTrainer personalTrainer;
    private PersonalTrainerDAO personalTrainerDAO;
    private CourseDAO courseDAO;
    private AppointmentDAO appointmentDAO;

    BaseUser currentUser;
    public AgendaService(PersonalTrainerDAO personalTrainerDAO,CourseDAO courseDAO, ScheduleDAO scheduleDAO) {
        this.personalTrainerDAO = personalTrainerDAO;
        this.courseDAO = courseDAO;
    }
    public void setPersonalTrainer(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public List<Course> viewCourses() throws SQLException {
        return courseDAO.getCoursesByTrainerId(currentUser.getId());
    }

    public List<Appointment> viewAppointments() throws SQLException {
        return appointmentDAO.getAppointmentsPT(currentUser.getId());
    }


    public boolean addOrUpdateCourse(Course course) throws SQLException {
        if (course.getId() == 0) {
            return courseDAO.addCourse(course);
        } else {
            return courseDAO.updateCourse(course);
        }
    }
    public boolean deleteCourse(int courseId) throws SQLException {
        return courseDAO.deleteCourse(courseId);
    }
    public boolean addAppointment(int id, int customerId, Date day, Time time) throws SQLException {
        return appointmentDAO.addAppointment(id, currentUser.getId(), customerId, day, time);
    }
    public boolean removeAppointment(int appointmentId) throws SQLException {
        return appointmentDAO.removeAppointment(appointmentId);
    }

}
