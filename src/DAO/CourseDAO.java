package DAO;

import DomainModel.Course;

import java.sql.*;
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
                    Course course=new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained"),
                            rs.getTime("time"),
                            rs.getString("day")
                    );
                    courses.add(course);
                    course.setParticipants(rs.getInt("participants"));
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
                    Course c=new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained"),
                            rs.getTime("time"),
                            rs.getString("day")
                    );
                    c.setParticipants(rs.getInt("participants"));
                    return c;
                } else {
                    return null;
                }
            }
        }
    }
    public boolean addCourse(String name, int maxParticipants, int trainerID, String bodyPartsTrained, String day, Time time) throws SQLException {
        String query = "INSERT INTO Course (name, max_participants,trainer_id,bodyPartsTrained,day,time ) VALUES (?, ?, ?, ?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, maxParticipants);
            stmt.setInt(3, trainerID);
            stmt.setString(4, bodyPartsTrained);
            stmt.setString(5, day);
            stmt.setTime(6, time);
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
                    Course c=new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained"),
                            rs.getTime("time"),
                            rs.getString("day")
                    );
                    c.setParticipants(rs.getInt("participants"));
                    courses.add(c);
                }
                return courses;
            }
        }
    }

    public void updateCourseParticipants(int participants,int id) throws SQLException {
        String query = "UPDATE course SET participants = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1,(participants+1));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public void updateCourseParticipantsCancel(int participants,int id) throws SQLException {
        String query = "UPDATE course SET participants = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1,(participants));
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }
    public boolean updateCourseValues(int courseId, String name, int maxParticipants, int trainerID, String bodyPartsTrained, String day, Time time) throws SQLException {
        String query = "UPDATE Course SET name = ?, trainer_id = ?, max_participants = ?, bodyPartsTrained = ?,day=?,time=? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, trainerID);
            stmt.setInt(3, maxParticipants);
            stmt.setString(4, bodyPartsTrained);
            stmt.setString(5, day);
            stmt.setTime(6, time);
            stmt.setInt(7, courseId);
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

    public int getMaxCourseId() throws SQLException {
        String query = "SELECT MAX(id) FROM Course";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                } else {
                    return 0;
                }
            }
        }
    }

    public Course getCourseByDayAndTime(String day, Time time) throws SQLException {
        String query = "SELECT * FROM Course WHERE day = ? AND time = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, day);
            stmt.setTime(2, time);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Course course = new Course(
                            rs.getInt("id"),
                            rs.getString("name"),
                            rs.getInt("max_participants"),
                            rs.getInt("trainer_id"),
                            rs.getString("bodyPartsTrained"),
                            rs.getTime("time"),
                            rs.getString("day")
                    );
                    course.setParticipants(rs.getInt("participants"));
                    return course;
                } else {
                    return null;  // Ritorna null se non esiste alcun corso con il giorno e l'ora specificati
                }
            }
        }
    }

}
