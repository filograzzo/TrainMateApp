package DAO;

import DomainModel.Course;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//DAO dove devo poter prendere il codice del corso,prendere e aggiungere il suo orario e chi lo tiene,quali parti del corpo allena questo corso
public class CourseDAO {
    private final Connection connection;

    public CourseDAO(Connection connection) {
        this.connection = connection;
    }


    public ArrayList<Course> getAllCourses() throws SQLException {
        String query = "SELECT * FROM Course";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                ArrayList<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained")
                    ));
                }
                return courses;
            }
        }
    }
    public Course getCourseById(int courseId) throws SQLException {
        String query = "SELECT * FROM Course WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("maxParticipants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartTrained")
                    );
                } else {
                    return null;
                }
            }
        }
    }
    public boolean addCourse(Course course) throws SQLException {
        String query = "INSERT INTO Course (id,name, max_participants,trainer_id,bodyPartsTrained ) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1,course.getId());
            stmt.setString(2, course.getName());
            stmt.setInt(3, course.getMaxParticipants());
            stmt.setInt(4, course.getIDTrainer());
            stmt.setString(5, course.getBodyPartsTrained());
            return stmt.executeUpdate() > 0;
        }
    }
    public List<Course> getCoursesByTrainerId(int trainerId) throws SQLException {
        String query = "SELECT * FROM Course WHERE trainer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, trainerId);
            try (ResultSet rs = stmt.executeQuery()) {
                List<Course> courses = new ArrayList<>();
                while (rs.next()) {
                    courses.add(new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained")
                    ));
                }
                return courses;
            }
        }
    }
    public boolean updateCourse(Course course) throws SQLException {
        String query = "UPDATE Course SET name = ?, trainer_id = ?, maxParticipants = ?, bodyPartsTrained = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, course.getName());
            stmt.setInt(2, course.getIDTrainer());
            stmt.setInt(3, course.getMaxParticipants());
            stmt.setString(4, course.getBodyPartsTrained());
            stmt.setInt(5, course.getId());
            return stmt.executeUpdate() > 0;
        }
    }
    public boolean deleteCourse(int courseId) throws SQLException {
        String query = "DELETE FROM Course WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            return stmt.executeUpdate() > 0;
        }
    }

}
