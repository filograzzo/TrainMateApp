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
    public AgendaService(PersonalTrainerDAO personalTrainerDAO,CourseDAO courseDAO, ScheduleDAO scheduleDAO, AppointmentDAO appointmentDAO) {
        this.personalTrainerDAO = personalTrainerDAO;
        this.courseDAO = courseDAO;
        this.appointmentDAO = appointmentDAO;

    }
    public void setPersonalTrainer(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public String getPTnamebyID(int id) throws SQLException {
        String name=personalTrainerDAO.getNamePersonalTrainerbyId(id);
        return name;
    }

    public List<Course> viewCourses() throws SQLException {
        return courseDAO.getCoursesByTrainerId(currentUser.getId());
    }

    public List<Appointment> viewAppointments() throws SQLException {
        return appointmentDAO.getAppointmentsPT(currentUser.getId());
    }

    public boolean deleteCourse(int courseId) throws SQLException {
        return courseDAO.deleteCourse(courseId);
    }
    public boolean updateCourse(int courseId, String name, int maxParticipants, int trainerID, String bodyPartsTrained,String day,Time time) throws SQLException {
        if(courseDAO.updateCourseValues(courseId, name, maxParticipants, trainerID, bodyPartsTrained,day,time)){
            Course course = ((PersonalTrainer)currentUser).getCoursebyId(courseId);
            course.setName(name);
            course.setMaxParticipants(maxParticipants);
            course.setTrainerID(trainerID);
            course.setBodyPartsTrained(bodyPartsTrained);
            course.setDay(day);
            course.setTime(time);
            return true;
        }else{
            return false;
        }

    }


    public boolean addCourse(String name, int maxParticipants, int trainerID, String bodyPartsTrained, String day,Time time) throws SQLException {
        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained,day,time);
        int id = courseDAO.getMaxCourseId();
        if(((PersonalTrainer)currentUser).addCourseToList(new Course(id, name, maxParticipants, trainerID, bodyPartsTrained,time,day))){
            return true;
        }else{
            return false;
        }
    }
    public boolean removeAppointment(int appointmentId) throws SQLException {
        return appointmentDAO.removeAppointment(appointmentId);
    }

}
