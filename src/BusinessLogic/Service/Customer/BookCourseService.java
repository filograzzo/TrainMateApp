package BusinessLogic.Service.Customer;

import DAO.CourseDAO;
import DAO.SignedDAO;
import DomainModel.BaseUser;
import DomainModel.Course;

import java.sql.SQLException;
import java.util.ArrayList;

public class BookCourseService {
    //Todo aggiungere un modo affinchè il client possa visualizzare tutti i corsi a cui si è iscritto
    private CourseDAO courseDAO;
    private SignedDAO signedDAO;
    private BaseUser currentUser;

    public BookCourseService(CourseDAO courseDAO, SignedDAO signedDAO) {
        this.courseDAO = courseDAO;
        this.signedDAO = signedDAO;
    }

    public void setCurrentUser(BaseUser currentUser) {
        this.currentUser = currentUser;
    }

    public ArrayList<Course> viewAvailableCourses() throws SQLException {
        return courseDAO.getAllCourses();
    }

    public boolean bookCourse(int courseId) throws SQLException {
        Course course = courseDAO.getCourseById(courseId);
        if (course != null && course.getParticipants() < course.getMaxParticipants()) {
            signedDAO.addSigned(currentUser.getId(), courseId ,course.getMaxParticipants());
            courseDAO.updateCourseParticipants(course.getParticipants(),course.getId());
            course.setParticipants(course.getParticipants() + 1);
            return true;
        }
        return false;
    }

    public boolean cancelBooking(int courseId) throws SQLException {
        Course course = courseDAO.getCourseById(courseId);
        if (course != null && course.getParticipants() > 0) {
            course.setParticipants(course.getParticipants() - 1);
            return signedDAO.removeSigned(currentUser.getId(), courseId);
        }
        return false;
    }
}
