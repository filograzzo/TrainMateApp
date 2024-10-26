package DAO_tst;

import DAO.CourseDAO;
import DomainModel.Course;
import org.junit.jupiter.api.*;
import trainmate.DatabaseUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CourseDAO_tst {
    private static Connection connection;
    private static CourseDAO courseDAO;

    @BeforeAll
    public static void setUp() throws SQLException {
        connection = DatabaseUtil.getConnection();
        courseDAO = new CourseDAO(connection);
    }

    @AfterAll
    public static void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testAddAndRetrieveCourse() throws SQLException {
        String name = "Yoga";
        int maxParticipants = 20;
        int trainerID = 1;
        String bodyPartsTrained = "Full Body";
        String day = "2024-10-26";
        Time time = Time.valueOf("09:00:00");

        boolean added = courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);
        assertTrue(added, "Course should be added successfully.");

        List<Course> courses = courseDAO.getAllCourses();
        assertFalse(courses.isEmpty(), "Courses should be retrieved from the database.");
        assertTrue(courses.stream().anyMatch(c -> c.getName().equals(name)), "The added course should be in the retrieved list.");

        // Cleanup
        int courseId = courses.stream().filter(c -> c.getName().equals(name)).findFirst().get().getId();
        courseDAO.deleteCourse(courseId);
    }

    @Test
    public void testGetCourseById() throws SQLException {
        String name = "Pilates";
        int maxParticipants = 15;
        int trainerID = 7;
        String bodyPartsTrained = "Core";
        String day = "2024-10-27";
        Time time = Time.valueOf("10:00:00");

        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);
        int courseId = courseDAO.getMaxCourseId();

        Course course = courseDAO.getCourseById(courseId);
        assertNotNull(course, "Course should be retrieved by ID.");
        assertEquals(name, course.getName(), "Retrieved course name should match.");

        // Cleanup
        courseDAO.deleteCourse(courseId);
    }

    @Test
    public void testGetCoursesByTrainerId() throws SQLException {
        String name = "Cardio";
        int maxParticipants = 10;
        int trainerID = 5;
        String bodyPartsTrained = "Legs";
        String day = "2024-10-28";
        Time time = Time.valueOf("11:00:00");

        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);

        List<Course> courses = courseDAO.getCoursesByTrainerId(trainerID);
        assertFalse(courses.isEmpty(), "Courses should be retrieved for the trainer.");
        assertTrue(courses.stream().anyMatch(c -> c.getName().equals(name)), "The added course should be in the trainer's list.");

        // Cleanup
        int courseId = courses.get(0).getId();
        courseDAO.deleteCourse(courseId);
    }

    @Test
    public void testUpdateCourseValues() throws SQLException {
        String name = "Strength Training";
        int maxParticipants = 25;
        int trainerID = 4;
        String bodyPartsTrained = "Arms";
        String day = "2024-10-29";
        Time time = Time.valueOf("12:00:00");

        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);
        int courseId = courseDAO.getMaxCourseId();

        // Update the course details
        boolean updated = courseDAO.updateCourseValues(courseId, "Advanced Strength", 30, trainerID, "Upper Body", "2024-10-30", Time.valueOf("13:00:00"));
        assertTrue(updated, "Course should be updated successfully.");

        Course updatedCourse = courseDAO.getCourseById(courseId);
        assertEquals("Advanced Strength", updatedCourse.getName(), "Course name should be updated.");
        assertEquals(30, updatedCourse.getMaxParticipants(), "Max participants should be updated.");

        // Cleanup
        courseDAO.deleteCourse(courseId);
    }

    @Test
    public void testDeleteCourse() throws SQLException {
        String name = "HIIT";
        int maxParticipants = 12;
        int trainerID = 5;
        String bodyPartsTrained = "Full Body";
        String day = "2024-10-31";
        Time time = Time.valueOf("14:00:00");

        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);
        int courseId = courseDAO.getMaxCourseId();

        boolean deleted = courseDAO.deleteCourse(courseId);
        assertTrue(deleted, "Course should be deleted successfully.");

        Course course = courseDAO.getCourseById(courseId);
        assertNull(course, "Course should no longer exist in the database.");
    }

    @Test
    public void testUpdateCourseParticipants() throws SQLException {
        String name = "Boxing";
        int maxParticipants = 8;
        int trainerID = 1;
        String bodyPartsTrained = "Upper Body";
        String day = "2024-11-01";
        Time time = Time.valueOf("15:00:00");

        courseDAO.addCourse(name, maxParticipants, trainerID, bodyPartsTrained, day, time);
        Course course = courseDAO.getCourseByDayAndTime(day, time);
        int initialParticipants = course.getParticipants();

        // Update participants
        courseDAO.updateCourseParticipants(initialParticipants, course.getId());
        Course updatedCourse = courseDAO.getCourseById(course.getId());
        assertEquals((initialParticipants + 1), updatedCourse.getParticipants(), "Participants should be incremented by 1.");

        // Cleanup
        courseDAO.deleteCourse(course.getId());
    }
}
