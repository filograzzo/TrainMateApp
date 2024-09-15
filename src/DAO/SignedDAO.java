/**
 * DAO class for Signed:Il cliente si iscrive o disiscrive ad un corso,vuole sapere se non sono già pieni:get,add,remove subscribtion,check if full
 */
package DAO;

import DomainModel.Signed;
import DomainModel.Course;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SignedDAO {
    private final Connection connection;

    public SignedDAO(Connection connection) {
        this.connection = connection;
    }

    public Signed getSigned(int courseId, int customerId) throws SQLException {
        String query = "SELECT * FROM Signed WHERE course_id = ? AND customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Signed(rs.getInt("course_id"), rs.getInt("customer_id"));
                } else {
                    return null;
                }
            }
        }
    }

    public boolean addSigned(int courseId, int customerId) throws SQLException {
        if (checkIfFull(courseId)) {
            return false;
        }
        String query = "INSERT INTO Signed (course_id, customer_id) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean removeSigned(int courseId, int customerId) throws SQLException {
        String query = "DELETE FROM Signed WHERE course_id = ? AND customer_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            stmt.setInt(2, customerId);
            return stmt.executeUpdate() > 0;
        }
    }

    public boolean checkIfFull(int courseId) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM Signed WHERE course_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    int capacity = getCourseCapacity(courseId);
                    return count >= capacity;
                } else {
                    return false;
                }
            }
        }
    }

    private int getCourseCapacity(int courseId) throws SQLException {
        String query = "SELECT max_participants FROM Course WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, courseId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("max_participants");
                } else {
                    throw new SQLException("Course not found");
                }
            }
        }
    }
}